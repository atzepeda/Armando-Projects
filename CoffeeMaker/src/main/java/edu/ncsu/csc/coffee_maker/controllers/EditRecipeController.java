package edu.ncsu.csc.coffee_maker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for the edit recipe page of CoffeeMaker. The controller
 * returns editrecipe.html in the /src/main/resources/templates folder. The page
 * also includes the associated form for entering more inventory.
 *
 * @author Carl Klier (caklier)
 */
@Controller
public class EditRecipeController {

    /**
     * Handles a GET request for edit recipe page. The GET request provides a
     * view to the client that includes the list of the current recipes in the
     * and a form where the client can edit a selected recipe
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( "/editrecipe" )
    public String editForm ( final Model model ) {
        return "editrecipe";
    }

}
