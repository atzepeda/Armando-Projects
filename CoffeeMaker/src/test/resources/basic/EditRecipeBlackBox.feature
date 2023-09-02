#Author: Benjamin Seifert (beseifer)

Feature: Edit a recipe
	As a user
	I want to be able to edit recipes in the CoffeeMaker
	So that we can tweak recipes as customers' tastes change
	


Scenario Outline: Valid Table Display
	Given the CoffeeMaker has a recipe with name: <existingRecipe>; cost: <price>; and ingredients: <amtCoffee> coffee, <amtMilk> milk, <amtSugar> sugar, <amtChocolate> chocolate
	When I select recipe <existingRecipe> for editing
	Then the recipe displays correctly in the table with name: <existingRecipe>; cost: <price>; and ingredients: <amtCoffee> coffee, <amtMilk> milk, <amtSugar> sugar, <amtChocolate> chocolate
	
Examples:
	| existingRecipe | price | amtCoffee | amtMilk | amtSugar | amtChocolate |
	| Coffee         | 50    | 3         | 1       | 1        | 0            |
	



Scenario Outline: Valid Edit
	Given the CoffeeMaker has a recipe with name: <existingRecipe>; cost: <price>; and ingredients: <amtCoffee> coffee, <amtMilk> milk, <amtSugar> sugar, <amtChocolate> chocolate
	When I edit that recipe to have new cost: <newPrice>; and ingredients: <newAmtCoffee> coffee, <newAmtMilk> milk, <newAmtSugar> sugar, <newAmtChocolate> chocolate
	Then the recipe is edited and the form is cleared
	
Examples:
	
	| existingRecipe | price | amtCoffee | amtMilk | amtSugar | amtChocolate | newPrice | newAmtCoffee | newAmtMilk | newAmtSugar | newAmtChocolate |
	| Coffee         | 50    | 3         | 1       | 1        | 0            | 100      | 1            | 1          | 1           | 0               |



Scenario Outline: Invalid Edit
	Given the CoffeeMaker has a recipe with name: <existingRecipe>; cost: <price>; and ingredients: <amtCoffee> coffee, <amtMilk> milk, <amtSugar> sugar, <amtChocolate> chocolate
    And I select recipe <existingRecipe> for editing
	When I edit that recipe to have new cost: <newPrice>; and ingredients: <newAmtCoffee> coffee, <newAmtMilk> milk, <newAmtSugar> sugar, <newAmtChocolate> chocolate
	Then an error message appears
	And the form is unchanged
	
Examples:
	| existingRecipe | price | amtCoffee | amtMilk | amtSugar | amtChocolate | newPrice | newAmtCoffee | newAmtMilk | newAmtSugar | newAmtChocolate |
	| Coffee         | 50    | 3         | 1       | 1        | 0            | -50      | 3            | 1          | 1           | 0               |
	| Coffee         | 50    | 3         | 1       | 1        | 0            | aaa      | 3            | 1          | 1           | 0               |
	| Coffee         | 50    | 3         | 1       | 1        | 0            | 50       | -3           | 1          | 1           | 0               |
	| Coffee         | 50    | 3         | 1       | 1        | 0            | 50       | aaa          | 1          | 1           | 0               |
	