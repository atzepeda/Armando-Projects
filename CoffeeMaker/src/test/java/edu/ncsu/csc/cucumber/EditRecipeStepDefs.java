package edu.ncsu.csc.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.coffee_maker.models.persistent.DomainObject;
import edu.ncsu.csc.coffee_maker.models.persistent.Recipe;
import edu.ncsu.csc.selenium.BrowserHandler;
import edu.ncsu.csc.test_utils.SharedRecipeData;

/**
 * Step definitions for automated black box tests using Cucumber and Selenium.
 * Automates the black box tests located in the GitHub wiki.
 *
 * @author Benjamin Seifert (beseifer@ncsu.edu)
 *
 */
public class EditRecipeStepDefs {

    /** The URL for CoffeeMaker - change as needed */
    private final String           baseUrl = "http://localhost:8080";

    private final SharedRecipeData recipeData;

    final static private String    OS      = System.getProperty( "os.name" );

    static private WebDriver       driver;

    /**
     * Constructor for the RecipeStepDefs. Used to keep track of CoffeeMaker's
     * state to ensure that the test completed successfully.
     *
     * @param srd
     *            SharedRecipeData; a backup of the recipes to ensure that the
     *            CoffeeMaker is behaving appropriately.
     */
    public EditRecipeStepDefs ( final SharedRecipeData srd ) {
        this.recipeData = srd;
    }

    /**
     * Creates a recipe with the specific parameters given
     *
     *
     * @param name
     *            name of the recipe
     * @param cost
     *            price of the recipe
     * @param coffeeAmt
     *            amount of coffee
     * @param milkAmt
     *            amount of milk
     * @param sugarAmt
     *            amount of sugar
     * @param chocolateAmt
     *            amount of chocolate
     * @throws Exception
     *             if there is an error
     */
    @Given ( "^the CoffeeMaker has a recipe with name: (.+); cost: (\\d+); and ingredients: (\\d+) coffee, (\\d+) milk, (\\d+) sugar, (\\d+) chocolate$" )
    public void addSpecificRecipe ( final String name, final int cost, final int coffeeAmt, final int milkAmt,
            final int sugarAmt, final int chocolateAmt ) throws Exception {

        setUp();

        final Recipe r = new Recipe();
        r.setName( name );
        r.setPrice( cost );
        r.setCoffee( coffeeAmt );
        r.setMilk( milkAmt );
        r.setSugar( sugarAmt );
        r.setChocolate( chocolateAmt );

        recipeData.currentRecipe = r;

        driver.get( baseUrl );
        driver.findElement( By.linkText( "Add a Recipe" ) ).click();

        // Enter the recipe information
        driver.findElement( By.name( "name" ) ).clear();
        driver.findElement( By.name( "name" ) ).sendKeys( r.getName() );
        driver.findElement( By.name( "price" ) ).clear();
        driver.findElement( By.name( "price" ) ).sendKeys( r.getPrice().toString() );
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( r.getCoffee().toString() );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( r.getMilk().toString() );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( r.getSugar().toString() );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( r.getChocolate().toString() );

        // Submit the recipe.
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

    }

    /**
     * Returns to the homepage and then navigates to the edit recipe page to
     * select the the recipe with the name provided for editing.
     *
     * @param name
     *            name of the recipe to select
     * @throws InterruptedException
     *             if thread sleep is interrupted
     */
    @When ( "^I select recipe (.+) for editing$" )
    public void selectRecipe ( final String name ) throws InterruptedException {

        // Return to the homepage
        driver.findElement( By.linkText( "Home" ) ).click();

        // To ensure that the page loads
        Thread.sleep( 3000 );

        driver.findElement( By.linkText( "Edit Recipe" ) ).click();

        waitForAngular();

        // To ensure that the page loads
        Thread.sleep( 2000 );

        // Clicks the element
        driver.findElement( By.cssSelector( "input[type=\"radio\"]" ) ).click();

    }

    /**
     * From the Edit Recipe page, verifies that the form is filled in properly
     * according to the parameters passed in.
     *
     * @param name
     *            expected name in the form
     * @param cost
     *            expected cost in the form
     * @param coffeeAmt
     *            expected coffee amount in the form
     * @param milkAmt
     *            expected milk amount in the form
     * @param sugarAmt
     *            expected sugar amount in the form
     * @param chocolateAmt
     *            expected chocolate amount in the form
     */
    @Then ( "^the recipe displays correctly in the table with name: (.+); cost: (\\d+); and ingredients: (\\d+) coffee, (\\d+) milk, (\\d+) sugar, (\\d+) chocolate$" )
    public void checkTable ( final String name, final int cost, final int coffeeAmt, final int milkAmt,
            final int sugarAmt, final int chocolateAmt ) {

        assertEquals( name, driver.findElement( By.name( "name" ) ).getAttribute( "value" ) );
        assertEquals( "" + cost, driver.findElement( By.name( "price" ) ).getAttribute( "value" ) );
        assertEquals( "" + coffeeAmt, driver.findElement( By.name( "coffee" ) ).getAttribute( "value" ) );
        assertEquals( "" + milkAmt, driver.findElement( By.name( "milk" ) ).getAttribute( "value" ) );
        assertEquals( "" + sugarAmt, driver.findElement( By.name( "sugar" ) ).getAttribute( "value" ) );
        assertEquals( "" + chocolateAmt, driver.findElement( By.name( "chocolate" ) ).getAttribute( "value" ) );

        DomainObject.deleteAll( Recipe.class );

        // close();
    }

    /**
     * Selects a recipe to edit and edits it according to the parameters given.
     *
     * @param cost
     *            new cost for the recipe
     * @param coffeeAmt
     *            new coffee amount for the recipe
     * @param milkAmt
     *            new milk amount for the recipe
     * @param sugarAmt
     *            new sugar amount for the recipe
     * @param chocolateAmt
     *            new chocolate amount for the recipe
     * @throws InterruptedException
     *             if thread sleep encounters error
     */
    @When ( "^I edit that recipe to have new cost: (.+); and ingredients: (.+) coffee, (.+) milk, (.+) sugar, (.+) chocolate$" )
    public void editRecipe ( final String cost, final String coffeeAmt, final String milkAmt, final String sugarAmt,
            final String chocolateAmt ) throws InterruptedException {

        // Selects the right recipe
        selectRecipe( recipeData.currentRecipe.getName() );

        // Enter the recipe information
        driver.findElement( By.name( "price" ) ).clear();
        driver.findElement( By.name( "price" ) ).sendKeys( cost );
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( coffeeAmt );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( milkAmt );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( sugarAmt );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( chocolateAmt );

        Thread.sleep( 100 );

        // Submit the recipe.
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

        waitForAngular();

        // Update recipeData
        final Recipe r = recipeData.currentRecipe;
        try {
            r.setPrice( Integer.parseInt( cost ) );
            r.setCoffee( Integer.parseInt( coffeeAmt ) );
            r.setMilk( Integer.parseInt( milkAmt ) );
            r.setSugar( Integer.parseInt( sugarAmt ) );
            r.setChocolate( Integer.parseInt( chocolateAmt ) );
        }
        catch ( final Exception e ) {
            // Ignore it in case of invalid tests
        }

    }

    /**
     * Checks that the form is cleared after submission of a valid recipe and
     * the appropriate message is displayed.
     *
     * @throws Exception
     *             if edit fails
     */
    @Then ( "^the recipe is edited and the form is cleared$" )
    public void checkValidEdit () throws Exception {
        final Recipe r = recipeData.currentRecipe;

        Thread.sleep( 2000 );

        assertTextPresent( "Recipe successfully edited", driver );

        assertEquals( "", driver.findElement( By.name( "name" ) ).getAttribute( "value" ) );
        assertEquals( "", driver.findElement( By.name( "price" ) ).getAttribute( "value" ) );
        assertEquals( "", driver.findElement( By.name( "coffee" ) ).getAttribute( "value" ) );
        assertEquals( "", driver.findElement( By.name( "milk" ) ).getAttribute( "value" ) );
        assertEquals( "", driver.findElement( By.name( "sugar" ) ).getAttribute( "value" ) );
        assertEquals( "", driver.findElement( By.name( "chocolate" ) ).getAttribute( "value" ) );

        selectRecipe( r.getName() );

        checkTable( r.getName(), r.getPrice(), r.getCoffee(), r.getMilk(), r.getSugar(), r.getChocolate() );
    }

    /**
     * Checks that the error message appeared in case of an invalid edit.
     */
    @Then ( "^an error message appears$" )
    public void checkError () {

        assertTextPresent( "Error while editing recipe", driver );

    }

    /**
     * Checks that the form has not been cleared in case of an invalid edit.
     *
     * @throws InterruptedException
     *             if thread sleep fails
     */
    @Then ( "^the form is unchanged$" )
    public void checkTableUnchanged () throws InterruptedException {

        assertNotEquals( "", driver.findElement( By.name( "name" ) ).getAttribute( "value" ) );
        assertNotEquals( "", driver.findElement( By.name( "price" ) ).getAttribute( "value" ) );
        assertNotEquals( "", driver.findElement( By.name( "coffee" ) ).getAttribute( "value" ) );
        assertNotEquals( "", driver.findElement( By.name( "milk" ) ).getAttribute( "value" ) );
        assertNotEquals( "", driver.findElement( By.name( "sugar" ) ).getAttribute( "value" ) );
        assertNotEquals( "", driver.findElement( By.name( "chocolate" ) ).getAttribute( "value" ) );

        DomainObject.deleteAll( Recipe.class );

    }

    /**
     * Sets up the driver for testing
     *
     * @throws Exception
     *             if driver cannot be retrieved
     */
    private void setUp () throws Exception {
        driver = BrowserHandler.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
    }

    /**
     * Returns true if the program runs on a Mac.
     *
     * @return true if host is a Mac
     */
    static private boolean Mac () {
        return OS.contains( "Mac OS X" );
    }

    /**
     * Returns true if the program runs on Linux.
     *
     * @return true if host is Linux
     */
    static private boolean Linux () {
        return OS.contains( "Linux" );
    }

    /**
     * Returns true if the program runs on Windows.
     *
     * @return true if host is windows
     */
    static private boolean Windows () {
        return OS.contains( "Windows" );
    }

    /**
     * Closes driver after each feature run.
     */
    @After
    private void close () {
        driver.close();
        driver.quit();

        if ( Windows() ) {
            windowsKill();
        }
        else if ( Linux() || Mac() ) {
            unixKill();
        }

    }

    /**
     * Kills driver on Windows
     */
    static private void windowsKill () {
        try {
            Runtime.getRuntime().exec( "taskkill /f /im chrome.exe" );
            Runtime.getRuntime().exec( "taskkill /f /im chromedriver.exe" );
        }
        catch ( final Exception e ) {
        }
    }

    /**
     * Kills driver on unix
     */
    static private void unixKill () {
        try {
            Runtime.getRuntime().exec( "pkill -f chromium-browser" );
            Runtime.getRuntime().exec( "pkill -f chrome" );
            Runtime.getRuntime().exec( "pkill -f chromedriver" );
        }
        catch ( final Exception e ) {
        }

    }

    /**
     * Asserts that the text is on the page
     *
     * @param text
     *            text to check
     * @param driver
     *            web driver
     */
    public void assertTextPresent ( final String text, final WebDriver driver ) {
        final List<WebElement> list = driver.findElements( By.xpath( "//*[contains(text(),'" + text + "')]" ) );
        assertTrue( "Text not found!", list.size() > 0 );
    }

    /**
     * Asserts that the text is not on the page. Does not pause for text to
     * appear.
     *
     * @param text
     *            text to check
     * @param driver
     *            web driver
     */
    public void assertTextNotPresent ( final String text, final WebDriver driver ) {
        assertFalse( "Text should not be found!",
                driver.findElement( By.cssSelector( "BODY" ) ).getText().contains( text ) );
    }

    /**
     * wait method that will let angular finish loading before continuing
     */
    protected void waitForAngular () {
        new NgWebDriver( (ChromeDriver) driver ).waitForAngularRequestsToFinish();
    }

}
