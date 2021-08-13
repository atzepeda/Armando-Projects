# -*- coding: utf-8 -*-
#Components contained within project: Scrapy, Splash, XPath, Python, Docker, Anaconda, MySQL
import scrapy
import globals
import requests
import time
from twisted.internet import reactor, defer
from scrapy_splash import SplashRequest
from scrapy.crawler import CrawlerRunner
from scrapy.utils.log import configure_logging
from scrapy.utils.project import get_project_settings
import datetime

'''
Class Purpose: This class is intended to hit Google.com and type "ufc" into the search bar and press enter.
Once that has been accomplished, and the Google widget is loaded, a spider will run on the page and look the name
of the upcoming event.
'''
class EventSpider(scrapy.Spider):
    name = 'event'
    allowed_domains = ['www.google.com']
    nextCard = ''
    USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.93 Safari/537.36"
    

    #Script: This is the script that will execute with Splash in order to search UFC on Google ,load the widget, and return the page
    eventScript = '''
        function main(splash, args)
            splash.private_mode_enabled = false
            url = args.url
            assert(splash:go(url))
            assert(splash:wait(3))
            input_box = assert(splash:select(".gLFyf.gsfi"))
            input_box:focus()
            input_box:send_text("ufc")
            assert(splash:wait(3))
            btn = assert(splash:select(".gNO89b"))
            btn:mouse_click()
            assert(splash:wait(3))
            splash:set_viewport_full()
            return splash:html()
        end
    '''
    #Function: Initial function that executes the Splash script and then executes method "parse"
    def start_requests(self):
        yield SplashRequest(url="https://www.google.com", callback=self.parse, endpoint="execute", args={
            'lua_source': self.eventScript,
            'user-agent': self.USER_AGENT
        })

    #Function: This function parses the returned Google page and finds the name of the upcoming UFC event
    #           The name of the UFC event is stored into a global variable for use in the next class
    def parse(self, response):
        cardDate = response.xpath("//div[@class='tsp-cpd tsp-rpd tsp-flr']//span[@class='tsp-cp']/text()").get()
        newDate = cardDate.split(",")

        #Sometimes the widget will display a past card initially. So this initial if statement checks if the date of the
        #  displayed card is before or after the present date. If the date if before the present date, then it will select
        #  the next upcoming card and continue forward with that. If the date is after the present (which means its the upcoming
        #  card) then it will continue forward with the initial page
        if (newDate[0]=="today" or newDate[0]=="yesterday"):
            self.nextCard = response.xpath("//a[@tabindex='0']/following-sibling::a[1]/div/span/div/span/span/text()").get()
        else:
            dateObj = datetime.datetime.strptime(cardDate, '%a, %b %d, %I:%M %p')
            if(datetime.datetime.now() - dateObj).total_seconds() > 0:
                print((datetime.datetime.now() - dateObj).total_seconds())
                self.nextCard = response.xpath("//a[@aria-selected='true']/div/span/div/span/span/text()").get()
                globals.q.put(self.nextCard)

            else:
                print((datetime.datetime.now() - dateObj).total_seconds())
                self.nextCard = response.xpath("//a[@aria-selected='true']/div/span/div/span/span/text()").get()
                globals.q.put(self.nextCard)
        print(self.nextCard)


'''
Class Purpose: This class is intended to hit sherdog.com in order to look up the upcoming UFC event and determine who
all the fighters on the card are. This class will use Splash to look up the UFC event in the "Event Finder" on the
homepage of the website and return the result. A spider will then run on the result in order to determine the names
of all fighters in the UFC event.
'''
class fighterSpider(scrapy.Spider):
    name = 'fighter'
    allowed_domains = ['sherdog.com']
    
    BASE_URL = 'https://sherdog.com'

    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'

    #Script: This is the script to be executed in Splash. This script looks up the UFC event on Sherdog.com
    #           and selects the first result. The following webpage is then returned in order to be parsed by
    #           a spider (webscraping module)
    fighterScript = '''
    function main(splash, args)
        splash.private_mode_enabled = false
        assert(splash:go(args.url))
            assert(splash:wait(1))
        
        input_box = assert(splash:select(".search.autocomplete"))
        input_box:focus()
        input_box:send_text(splash.args.event)
        assert(splash:wait(1))
        
        btn = assert(splash:select(".fight_finder > div > div > form > input:nth-child(2)"))
        btn:mouse_click()
        assert(splash:wait(1))
        
        splash:set_viewport_full()
        
        return splash:html()
    end
    '''

    #Script: A short script intended to just run the javascript of a given page
    defaultScript = '''
        function main(splash, args)
            assert(splash:go(args.url))
            assert(splash:wait(0.5))
            return splash:html()
        end
    '''

    #Function: This is the initial functions that runs and executes the splashy script. The result is forwarded 
    #           to function "getEVent"
    def start_requests(self):
        event = globals.q.get()
        yield SplashRequest(url="https://www.sherdog.com", callback=self.getEventPage, endpoint="execute", args={
            'lua_source': self.fighterScript,
            'event': event,
            'user-agent': self.user_agent
        })
    #Function: This function parses takes the returned page from the previous function and runs it in splashy with
    #           a short script in order to load the widget on the page. Result is forwarded to function 'parse'
    def getEventPage(self, response):
        event = response.xpath("//div[@class='content table']/table/tbody/tr[2]/td[2]/a/@href").get()
        total_url = self.BASE_URL + event
        yield SplashRequest(response.urljoin(event), callback=self.parse, headers={
            'User-Agent': self.user_agent,
            'lua_source': self.defaultScript
        })
    
    #Function: This function parses the returned Sherdog page and finds the names of all fighters on the card/event
    #           The name of the fighters is stored into a global variable for use in the next class
    def parse(self, response):
        fight_list = []
        fighters = response.xpath("//div[@class='content table']/table/tbody/tr[@class='table_head']/following-sibling::node()[@class='even' or @class='odd']")
        for fighter in fighters:
            fighterOne = fighter.xpath(".//td[2]/a[starts-with(@href, '/fighter/')]/span/text()").get()
            fighterTwo = fighter.xpath(".//td[4]/a[starts-with(@href, '/fighter/')]/span/text()").get()
            fight = {
                'fighterOne': fighterOne,
                'fighterTwo': fighterTwo
            }
            fight_list.append(fight)
        globals.q.put(fight_list)

'''
Class Purpose: This class is intended to hit ufcstats.com in order to look up the stats for all fighters on the upcoming UFC event
'''
class statsSpider(scrapy.Spider):
    name = 'stats'
    allowed_domains = ['ufcstats.com']
    list_of_fights = []
    
    BASE_URL = 'http://ufcstats.com'

    user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'

    #Script: This script is intended to look up the last name of given fighter and return the page listing the results
    firstStatScript = '''
        function main(splash, args)
            splash.private_mode_enabled = false
            assert(splash:go(args.url))
            assert(splash:wait(1))
            
            input_box = assert(splash:select(".b-statistics__search-field"))
            input_box:focus()
            input_box:send_text(splash.args.lastName)
            assert(splash:wait(1))
            
            btn = assert(splash:select(".b-statistics__search-btn"))
            btn:mouse_click()
            assert(splash:wait(1))
            
            splash:set_viewport_full()
                    
            return splash:html()
        end
    '''

    #Script: A short script intended to just run the javascript of a given page
    defaultScript = '''
        function main(splash, args)
            assert(splash:go(args.url))
            assert(splash:wait(1))
            return splash:html()
        end
    '''

    #firstName = ""
    fakeLastName = ""
    numberOne = 2
    numberTwo = 2

    #Function: This is the initial function that runs for the class and goes through each fight stored in the global variable
    #           For each fighter listed, it will call the function 'retrieve_fighter_page
    def start_requests(self):
        fighterOneFirstName = []
        fighterOneLastName = ""
        fighterTwoFirstName = []
        fighterTwoLastName = ""
        fighterOneCount = 0
        fighterTwoCount = 0

        #Function: The intention of this function is to determine, after we have looked up a fighter on the website, which
        #           fight of the event (first, second, third, etc) this fighter is in and forward that number position to 
        #           the function 'retrieve_fighter_page'
        def my_callback_one(response):
            if fighterOneCount == len(fighterOneFirstName):
                name = len(fighterOneFirstName) - self.numberOne
                self.numberOne -= 1
            else:
                name = len(fighterOneFirstName) - 2
            return self.retrieve_fighter_page(response, fighterOneFirstName[name])

        #Function: Same as above but I needed to functions so both fighters could be looked up simultaneously    
        def my_callback_two(response):
            if fighterTwoCount == len(fighterTwoFirstName):
                name = len(fighterTwoFirstName) - self.numberTwo
                self.numberTwo -= 1
            else:
                name = len(fighterTwoFirstName) - 2
            return self.retrieve_fighter_page(response, fighterTwoFirstName[name])

        # Determines the number of fighters on the card (fighterOneCount represents the 'red corner' and fighterTwoCount represents the 'blue corner')
        list_of_fights = globals.q.get()
        fighterOneCount = len(list_of_fights)
        fighterTwoCount = len(list_of_fights)

        #Parses through all the fights stored in the global variable, hits ufcstats.com, and calls either 'my_callback' function
        # to determine the placement of the fight on the card/event
        for fight in list_of_fights:
            time.sleep(1)
            fighterOneFirstName.append(fight.get('fighterOne').split()[0])
            fighterOneLastName = fight.get('fighterOne').split()[1]
            yield SplashRequest(url="http://ufcstats.com/statistics/fighters", callback=my_callback_one, endpoint="execute", args={
                'lua_source': self.firstStatScript,
                'lastName': fighterOneLastName,
                'user-agent': self.user_agent
            })

            fighterTwoFirstName.append(fight.get('fighterTwo').split()[0])
            fighterTwoLastName = fight.get('fighterTwo').split()[1]
            yield SplashRequest(url="http://ufcstats.com/statistics/fighters", callback=my_callback_two, endpoint="execute", args={
                'lua_source': self.firstStatScript,
                'lastName': fighterTwoLastName,
                'user-agent': self.user_agent
            })

    #Function: This function is intended to run the defaultScript in order to load the webpage listing the results and passing
    # it onto the function 'retrieve_fighter_stats'. If no fighter is found, then the 'Status' field will be set to 'NOT_FOUND' for
    # the fighter in the JSON
    def retrieve_fighter_page(self, response, firstName):
        table = response.xpath("//table[@class='b-statistics__table']/tbody/tr")
        lastName = response.xpath("//table[@class='b-statistics__table']/tbody/tr[2]/td[2]/a/text()").get()
        count = 0
        for row in table:
            fighterName = row.xpath(".//td/a/text()").get()
            if(fighterName==firstName):
                link = row.xpath(".//td/a/@href").get()
                count += 1
                yield SplashRequest(url=link, callback=self.retrieve_fighter_stats, endpoint="execute", args={
                    'lua_source': self.defaultScript,
                    'user-agent': self.user_agent
                })
        if count == 0:
            name = firstName + " " + lastName
            replacement = {
                'Name': name,
                'Status': "NOT_FOUND"
            }
            yield replacement
    
    #Function: If a fighter was found then this function will scrape the given webpage and store all the stats info for that fighter
    def retrieve_fighter_stats(self, response):
        stats = {
            'Name': response.xpath("//span[@class='b-content__title-highlight']/text()").get().strip(),
            'Status': "FOUND"
        }
        fighterStatCols = response.xpath("//div[@class='b-list__info-box-left clearfix']/div/ul")
        counter = 0
        for stat in fighterStatCols:
            if counter == 0:
                stats[stat.xpath(".//li[1]/i/text()").get().strip()] = stat.xpath(".//li[1]/i/following-sibling::text()").get().strip()
                stats[stat.xpath(".//li[2]/i/text()").get().strip()] = stat.xpath(".//li[2]/i/following-sibling::text()").get().strip()
                stats[stat.xpath(".//li[3]/i/text()").get().strip()] = stat.xpath(".//li[3]/i/following-sibling::text()").get().strip()
                stats[stat.xpath(".//li[4]/i/text()").get().strip()] = stat.xpath(".//li[4]/i/following-sibling::text()").get().strip()
            else:
                stats[stat.xpath(".//li[2]/i/text()").get().strip()] = stat.xpath(".//li[2]/i/following-sibling::text()").get().strip()
                stats[stat.xpath(".//li[3]/i/text()").get().strip()] = stat.xpath(".//li[3]/i/following-sibling::text()").get().strip()
                stats[stat.xpath(".//li[4]/i/text()").get().strip()] = stat.xpath(".//li[4]/i/following-sibling::text()").get().strip()
                stats[stat.xpath(".//li[5]/i/text()").get().strip()] = stat.xpath(".//li[5]/i/following-sibling::text()").get().strip()
            counter += 1
        yield stats

settings = get_project_settings()        
settings.set('FEED_FORMAT', 'json')
settings.set('FEED_URI', 'result.json')
configure_logging()

runner = CrawlerRunner(settings)

@defer.inlineCallbacks
def crawl():
    yield runner.crawl(EventSpider)
    yield runner.crawl(fighterSpider)
    yield runner.crawl(statsSpider)

    reactor.stop()

crawl()
reactor.run()