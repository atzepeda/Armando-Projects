package edu.ncsu.csc.coffee_maker.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import edu.ncsu.csc.coffee_maker.models.persistent.Inventory;
import edu.ncsu.csc.coffee_maker.models.persistent.Recipe;

/**
 * Perform a quick check of one of the API methods to ensure that the API
 * controller is up and receiving requests as it should be
 *
 * @author Kai Presler-Marshall
 *
 */
@RunWith ( SpringRunner.class )
@SpringBootTest ( properties = "logging.level.org.springframework.web=DEBUG" )
@AutoConfigureMockMvc
public class APITest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    /**
     * Tests that we are able to make a call to the REST API. If such a call
     * cannot be made, throws an exception instead
     *
     * @throws Exception
     *             if tests fail
     */
    @Test
    public void testApi () throws Exception {
        /*
         * Verify that we are able to make a request to the API endpoint and
         * that we get a 400 (OK) status in return
         */
        mvc.perform( get( "/api/v1/inventory" ) ).andExpect( status().isOk() );

        // Create a recipe and initialize all its attributes
        final Recipe recipe = new Recipe();
        recipe.setName( "coffee" );
        recipe.setPrice( 50 );
        recipe.setCoffee( 3 );
        recipe.setMilk( 1 );
        recipe.setSugar( 1 );
        recipe.setChocolate( 0 );

        // Add recipe to list
        mvc.perform(
                post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( recipe ) ) );
        // Check to see if the recipe is in the list
        mvc.perform( get( "/api/v1/recipes" ) ).andExpect( status().isOk() );
        // Try to add the recipe again and see if an error occurs
        mvc.perform(
                post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( recipe ) ) )
                .andExpect( status().isConflict() );
        // See if the recipe can be retrieved by its name
        mvc.perform( get( "/api/v1/recipes/coffee" ) ).andExpect( status().isOk() );
        // Check to see if retrieving using an invalid name will produce an
        // error
        mvc.perform( get( "/api/v1/recipes/toffee" ) ).andExpect( status().isNotFound() );
        // Try using wrong name to delete recipe and see if it produces an error
        mvc.perform( delete( "/api/v1/recipes/toffee" ) ).andExpect( status().isNotFound() );
        // Try to delete the recipe from the list
        mvc.perform( delete( "/api/v1/recipes/coffee" ) ).andExpect( status().isOk() );

        // Re-add recipe to the list and try to edit it
        mvc.perform(
                post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( recipe ) ) );
        // edit the recipe
        recipe.setPrice( 60 );
        // Edit the recipe in the list
        mvc.perform( put( "/api/v1/recipes/coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( recipe ) ) ).andExpect( status().isOk() );

        // Add 3 recipes to the list and see if a fourth addition will be denied
        recipe.setName( "mocha" );
        mvc.perform(
                post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( recipe ) ) );
        recipe.setName( "Hot Chocolate" );
        mvc.perform(
                post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( recipe ) ) );
        // See if fourth addition returns error
        recipe.setName( "Invalid" );
        mvc.perform(
                post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( recipe ) ) )
                .andExpect( status().isInsufficientStorage() );

        // Check to see all API endpoints respond to calls
        mvc.perform( get( "/api/v1/recipes" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/recipe" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/makecoffee" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/inventory" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/editrecipe" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/deleterecipe" ) ).andExpect( status().isOk() );

        mvc.perform( get( "/index" ) ).andExpect( status().isOk() );

        this.mvc.perform( get( "/api/v1/inventory" ) ).andExpect( status().isOk() );

        // Create inventory to update
        final Inventory inventory = new Inventory();
        inventory.addIngredients( 50, 50, 50, 50 );

        // Test check____() methods in Inventory

        // Test that Inventory can be updated
        mvc.perform( put( "/api/v1/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( inventory ) ) ).andExpect( status().isOk() );

        System.out.println( inventory.getChocolate() );
        System.out.println( inventory.getCoffee() );

        // Try to make a coffee
        mvc.perform( post( "/api/v1/makecoffee/coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( 100 ) ) ).andExpect( status().isOk() );
    }

    private static Gson gson = new Gson();

    /**
     * Uses Google's GSON parser to serialize a Java object to JSON. Useful for
     * creating JSON representations of our objects when calling API methods.
     *
     * @param obj
     *            to serialize to JSON
     * @return JSON string associated with object
     *
     * @author Kai Presler-Marshall
     */
    public static String asJsonString ( final Object obj ) {
        return gson.toJson( obj );
    }

}
