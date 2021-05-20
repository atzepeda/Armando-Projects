package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * Step definitions for adding a user feature
 */
public class AddUserStepDefs extends CucumberTest {

    private final String baseUrl      = "http://localhost:8080/iTrust2";
    private final String jenkinsUname = "jenkins" + ( new Random() ).nextInt();
    private final String jenkinsOphthalmologistUname = "jenkins" + ( new Random() ).nextInt();
    private final String jenkinsOptometristUname = "jenkins" + ( new Random() ).nextInt();

    /**
     * Check for no user
     */
    @Given ( "The user does not already exist in the database" )
    public void noUser () {
        attemptLogout();

        final List<User> users = User.getUsers();
        for ( final User user : users ) {
            if ( user.getUsername().equals( jenkinsUname ) || user.getUsername().equals( jenkinsOphthalmologistUname ) ||  user.getUsername().equals( jenkinsOptometristUname )) {
                try {
                    user.delete();
                }
                catch ( final Exception e ) {
                    Assert.fail();
                }
            }
        }
    }

    /**
     * Admin log in
     */
    @When ( "I log in as admin" )
    public void loginAdmin () {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "admin" );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }

    /**
     * Navigate to add user page
     */
    @When ( "I navigate to the Add User page" )
    public void addUserPage () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('users').click();" );
    }

    /**
     * Fill in add user values
     */
    @When ( "I fill in the values in the Add User form" )
    public void fillFields () {
        final WebElement username = driver.findElement( By.id( "username" ) );
        username.clear();
        username.sendKeys( jenkinsUname );

        final WebElement password = driver.findElement( By.id( "password" ) );
        password.clear();
        password.sendKeys( "123456" );

        final WebElement password2 = driver.findElement( By.id( "password2" ) );
        password2.clear();
        password2.sendKeys( "123456" );

        final Select role = new Select( driver.findElement( By.id( "role" ) ) );
        role.selectByVisibleText( "Healthcare Provider" );

        final WebElement enabled = driver.findElement( By.name( "enabled" ) );
        enabled.click();

        driver.findElement( By.id( "submit" ) ).click();

    }

    /**
     * Fill in ophthalmologist values
     */
    @When ( "I fill in the ophthalmologist values in the Add User form" )
    public void fillOphthalmologistFields () {
        final WebElement username = driver.findElement( By.id( "username" ) );
        username.clear();
        username.sendKeys( jenkinsOphthalmologistUname );

        final WebElement password = driver.findElement( By.id( "password" ) );
        password.clear();
        password.sendKeys( "123456" );

        final WebElement password2 = driver.findElement( By.id( "password2" ) );
        password2.clear();
        password2.sendKeys( "123456" );

        final Select role = new Select( driver.findElement( By.id( "role" ) ) );
        role.selectByVisibleText( "Ophthalmologist" );

        final WebElement enabled = driver.findElement( By.name( "enabled" ) );
        enabled.click();

        driver.findElement( By.id( "submit" ) ).click();

    }

        /**
     * Fill in ophthalmologist values
     */
    @When ( "I fill in the optometrist values in the Add User form" )
    public void fillOptometristFields () {
        final WebElement username = driver.findElement( By.id( "username" ) );
        username.clear();
        username.sendKeys( jenkinsOptometristUname );

        final WebElement password = driver.findElement( By.id( "password" ) );
        password.clear();
        password.sendKeys( "123456" );

        final WebElement password2 = driver.findElement( By.id( "password2" ) );
        password2.clear();
        password2.sendKeys( "123456" );

        final Select role = new Select( driver.findElement( By.id( "role" ) ) );
        role.selectByVisibleText( "Optometrist" );

        final WebElement enabled = driver.findElement( By.name( "enabled" ) );
        enabled.click();

        driver.findElement( By.id( "submit" ) ).click();

    }

    /**
     * Create user
     */
    @Then ( "The user is created successfully" )
    public void createdSuccessfully () {
        assertTrue( driver.getPageSource().contains( "User added successfully" ) );
    }

    /**
     * User login
     */
    @Then ( "The new user can login" )
    public void tryLogin () {
        driver.findElement( By.id( "logout" ) ).click();

        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( jenkinsUname );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();

        /**
         * Not an assert statement in the typical sense, but we know that we can
         * log in if we can find the "iTrust" button in the top-left after
         * attempting to do so.
         */
        try {
        	waitForAngular();
            driver.findElement( By.linkText( "iTrust2" ) );
        }
        catch ( final Exception e ) {
            fail();
        }
    }

    /**
     * Ophthalmologist login
     */
    @Then ( "The new ophthalmologist can login" )
    public void tryOphthalmologistLogin () {
        driver.findElement( By.id( "logout" ) ).click();

        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( jenkinsOphthalmologistUname );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();

        /**
         * Not an assert statement in the typical sense, but we know that we can
         * log in if we can find the "iTrust" button in the top-left after
         * attempting to do so.
         */
        try {
        	waitForAngular();
            driver.findElement( By.linkText( "iTrust2" ) );
        }
        catch ( final Exception e ) {
            fail();
        }
    }

    /**
     * Optometrist login
     */
    @Then ( "The new optometrist can login" )
    public void tryOptometristLogin () {
        driver.findElement( By.id( "logout" ) ).click();

        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( jenkinsOptometristUname );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();

        /**
         * Not an assert statement in the typical sense, but we know that we can
         * log in if we can find the "iTrust" button in the top-left after
         * attempting to do so.
         */
        try {
        	waitForAngular();
            driver.findElement( By.linkText( "iTrust2" ) );
        }
        catch ( final Exception e ) {
            fail();
        }
    }
}
