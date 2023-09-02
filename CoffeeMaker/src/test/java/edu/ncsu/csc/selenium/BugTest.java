package edu.ncsu.csc.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.coffee_maker.models.persistent.DomainObject;
import edu.ncsu.csc.coffee_maker.models.persistent.Inventory;
import edu.ncsu.csc.coffee_maker.models.persistent.Recipe;

/**
 * Tests for bugs fixed in the project.
 *
 * @author Ben Seifert (beseifer@ncsu.edu)
 */

public class BugTest extends SeleniumTest {

    /** The URL for CoffeeMaker - change as needed */
    private String             baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();

    @Override
    @Before
    protected void setUp () throws Exception {
        super.setUp();

        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );

    }

    private void addRecipeHelper ( String name, String price, String coffee, String milk, String sugar,
            String chocolate ) {
        driver.get( baseUrl );
        driver.findElement( By.linkText( "Add a Recipe" ) ).click();

        // Enter the recipe information
        driver.findElement( By.name( "name" ) ).clear();
        driver.findElement( By.name( "name" ) ).sendKeys( name );
        driver.findElement( By.name( "price" ) ).clear();
        driver.findElement( By.name( "price" ) ).sendKeys( price );
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( coffee );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( milk );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( sugar );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( chocolate );

        // Submit the recipe.
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();
    }

    /**
     * Test for adding an invalid recipe. Verifies that the form is not cleared
     * after the invalid recipe is submitted.
     *
     * @throws Exception
     *             if adding invalid recipe fails
     */
    @Test
    public void testInvalidAddRecipeFormNotCleared () throws Exception {
        // Add valid recipe
        addRecipeHelper( "Coffee", "50", "3", "1", "1", "0" );

        // Make sure the proper message was displayed.
        assertTextPresent( "Recipe Created", driver );

        // Try to add recipe with the same name
        addRecipeHelper( "Coffee", "50", "3", "1", "1", "0" );

        // Verify that the fields were not cleared
        assertEquals( "Coffee", driver.findElement( By.name( "name" ) ).getAttribute( "value" ) );
        assertEquals( "50", driver.findElement( By.name( "price" ) ).getAttribute( "value" ) );
        assertEquals( "3", driver.findElement( By.name( "coffee" ) ).getAttribute( "value" ) );
        assertEquals( "1", driver.findElement( By.name( "milk" ) ).getAttribute( "value" ) );
        assertEquals( "1", driver.findElement( By.name( "sugar" ) ).getAttribute( "value" ) );
        assertEquals( "0", driver.findElement( By.name( "chocolate" ) ).getAttribute( "value" ) );

        System.out.println( "Recipe created" );

        // Delete the recipe
        DomainObject.deleteAll( Recipe.class );
    }

    /**
     * Tests that the delete button appears even when there are no recipes.
     */
    @Test
    public void testDeleteButtonPresent () {
        // Navigate to the main page
        driver.get( baseUrl );

        // Navigate to the delete recipe page
        driver.findElement( By.linkText( "Delete Recipe" ) ).click();

        assertTrue( driver.findElements( By.cssSelector( "input[type=\"submit\"]" ) ).size() > 0 );
    }

    /**
     * Tests that the initial state is 15 units of each ingredient.
     */
    @Test
    public void testInitialState () {

        // Navigate to the main page
        driver.get( baseUrl );

        // Navigate to the update inventory page
        driver.findElement( By.linkText( "Update Inventory" ) ).click();

        waitForAngular();

        // Check the current inventory state
        assertEquals( "15", driver.findElement( By.id( "currentCoffee" ) ).getText() );

    }

    /**
     * Tests that the webpage updates when the inventory is updated.
     *
     * @throws InterruptedException
     *             if thread sleep fails
     */
    @Test
    public void testUpdateInventoryState () throws InterruptedException {

        // Make sure the inventory is initialized to 15
        final Inventory i = Inventory.getInventory();
        i.setCoffee( 15 );
        i.setMilk( 15 );
        i.setSugar( 15 );
        i.setChocolate( 15 );

        i.save();

        // Navigate to the main page
        driver.get( baseUrl );

        // Navigate to the update inventory page
        driver.findElement( By.linkText( "Update Inventory" ) ).click();

        waitForAngular();

        // Check the current inventory state
        assertEquals( "15", driver.findElement( By.id( "currentCoffee" ) ).getText() );

        // Enter the amount of each ingredient
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( "5" );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( "3" );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( "7" );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( "2" );

        // Submit the inventory.
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

        waitForAngular();

        Thread.sleep( 2000 );

        // Check the current inventory state
        assertEquals( "20", driver.findElement( By.id( "currentCoffee" ) ).getText() );
        assertEquals( "18", driver.findElement( By.id( "currentMilk" ) ).getText() );
        assertEquals( "22", driver.findElement( By.id( "currentSugar" ) ).getText() );
        assertEquals( "17", driver.findElement( By.id( "currentChocolate" ) ).getText() );

        // Reset inventory state
        i.setCoffee( 15 );
        i.setMilk( 15 );
        i.setSugar( 15 );
        i.setChocolate( 15 );

        i.save();

    }

    /**
     * Tests that the correct error message is printed when attempting to
     * purchase coffee with insufficient funds.
     *
     * @throws InterruptedException
     *             if thread sleep fails
     */
    @Test
    public void testInsufficientFundsMessage () throws InterruptedException {

        // Add valid recipe
        addRecipeHelper( "Coffee", "50", "3", "1", "1", "0" );

        // Navigate to the main page
        driver.get( baseUrl );

        // Navigate to the recipe purchase page
        driver.findElement( By.linkText( "Make Coffee" ) ).click();

        waitForAngular();

        // Select the recipe
        selectRecipe( "Coffee" );

        // Fill in the payment field with insufficient funds
        driver.findElement( By.name( "amtPaid" ) ).clear();
        driver.findElement( By.name( "amtPaid" ) ).sendKeys( "40" );

        // Submit and check the error message
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();
        Thread.sleep( 5000 );

        // Make sure the proper message was displayed.
        assertTextPresent( "Coffee cannot be made. Your change is 40.", driver );

        DomainObject.deleteAll( Recipe.class );

    }

    /**
     * Tests that the correct error message is printed when attempting to
     * purchase coffee with non-integer funds.
     *
     * @throws InterruptedException
     *             if thread sleep fails
     */
    @Test
    public void testNonIntegerFundsMessage () throws InterruptedException {

        // Add valid recipe
        addRecipeHelper( "Coffee", "50", "3", "1", "1", "0" );

        // Navigate to the main page
        driver.get( baseUrl );

        // Navigate to the recipe purchase page
        driver.findElement( By.linkText( "Make Coffee" ) ).click();

        waitForAngular();

        // Select the recipe
        selectRecipe( "Coffee" );

        // Fill in the payment field with insufficient funds
        driver.findElement( By.name( "amtPaid" ) ).clear();
        driver.findElement( By.name( "amtPaid" ) ).sendKeys( "5.5" );

        // Submit and check the error message
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();
        Thread.sleep( 5000 );

        // Make sure the proper message was displayed.
        assertTextPresent( "Coffee cannot be made. Your change is 5.5.", driver );

        DomainObject.deleteAll( Recipe.class );

    }

    /**
     * Tests that the correct error message is printed when attempting to
     * purchase coffee with insufficient ingredients in the CoffeeMaker.
     *
     * @throws InterruptedException
     *             if thread sleep fails
     */
    @Test
    public void testInsufficientIngredientsMessage () throws InterruptedException {

        // Remove all coffee from the system
        final Inventory i = Inventory.getInventory();
        i.setCoffee( 0 );

        i.save();

        // Add valid recipe
        addRecipeHelper( "Coffee", "50", "3", "1", "1", "0" );

        // Navigate to the main page
        driver.get( baseUrl );

        // Navigate to the recipe purchase page
        driver.findElement( By.linkText( "Make Coffee" ) ).click();

        waitForAngular();

        // Select the recipe
        selectRecipe( "Coffee" );

        // Fill in the payment field with insufficient funds
        driver.findElement( By.name( "amtPaid" ) ).clear();
        driver.findElement( By.name( "amtPaid" ) ).sendKeys( "50" );

        // Submit and check the error message
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();
        Thread.sleep( 5000 );

        // Make sure the proper message was displayed.
        assertTextPresent( "Coffee cannot be made. Your change is 50.", driver );

        DomainObject.deleteAll( Recipe.class );

        // Restore the system
        i.setCoffee( 15 );
        i.save();

    }

    /**
     * Tests that the correct message is printed when attempting to purchase
     * coffee with sufficient funds.
     *
     * @throws InterruptedException
     *             if thread sleep fails
     */
    @Test
    public void testValidPurchase () throws InterruptedException {

        // Add valid recipe
        addRecipeHelper( "Coffee", "50", "3", "1", "1", "0" );

        // Navigate to the main page
        driver.get( baseUrl );

        waitForAngular();

        Thread.sleep( 3000 );

        // Navigate to the recipe purchase page
        driver.findElement( By.linkText( "Make Coffee" ) ).click();

        // Select the recipe
        while ( !selectRecipe( "Coffee" ) ) {
            Thread.sleep( 1000 );
        }

        // Fill in the payment field with insufficient funds
        driver.findElement( By.name( "amtPaid" ) ).clear();
        driver.findElement( By.name( "amtPaid" ) ).sendKeys( "60" );

        Thread.sleep( 1000 );

        // Submit and check the message
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

        waitForAngular();

        Thread.sleep( 5000 );

        // Make sure the proper message was displayed.
        assertEquals( "Coffee was made. Your change is 10.",
                driver.findElement( By.name( "successmessage" ) ).getText() );

        // Delete all recipes
        DomainObject.deleteAll( Recipe.class );

        // Reset inventory
        final Inventory i = Inventory.getInventory();

        i.addIngredients( i.getCoffee() - 15, i.getMilk() - 15, i.getSugar() - 15, 0 );

        i.save();

    }

    /**
     * Looks through the list of available recipes and selects the specified
     * recipe
     *
     * @param name
     * @return true if found and selected, false if not
     * @throws InterruptedException
     */
    private boolean selectRecipe ( final String name ) throws InterruptedException {
        final List<WebElement> list = driver.findElements( By.name( "name" ) );
        Thread.sleep( 5000 );

        // Select the recipe
        for ( final WebElement we : list ) {
            if ( name.equals( we.getAttribute( "value" ) ) ) {
                we.click();
                // Wait for thread to perform operation
                while ( !we.isSelected() ) {
                    Thread.sleep( 5000 );
                }

                return true;
            }
        }

        return false;
    }

    @Override
    @After
    public void tearDown () {
        final String verificationErrorString = verificationErrors.toString();
        if ( !"".equals( verificationErrorString ) ) {
            fail( verificationErrorString );
        }
    }

    @AfterClass
    @Override
    public void close () {
        super.close();
    }

}
