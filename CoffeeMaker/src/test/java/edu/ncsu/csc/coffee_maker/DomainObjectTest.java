package edu.ncsu.csc.coffee_maker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import edu.ncsu.csc.coffee_maker.models.persistent.Inventory;
import edu.ncsu.csc.coffee_maker.models.persistent.Recipe;
import edu.ncsu.csc.coffee_maker.util.HibernateUtil;

public class DomainObjectTest {

    @Test
    public void test () {
        Recipe.deleteAll( Recipe.class );
        final Recipe recipe1 = new Recipe();
        recipe1.save();
        // final APIRecipeController ctrl = new APIRecipeController();
        // ctrl.createRecipe( recipe1 );
        System.out.println( "This is recipe id for recipe1" + recipe1.getId() );

        final Recipe recipe2 = new Recipe();
        recipe2.save();
        // for ( final Recipe recipe : Recipe.getAll() ) {
        // System.out.println( "This is id: " + recipe.getId() );
        // }

        final Inventory inventory1 = new Inventory();
        recipe2.setCoffee( 5 );

        recipe1.setName( "recipe1" );
        recipe1.save();
        // assertEquals( recipe1, Recipe.getById( Recipe.class, 1L ) );

        assertEquals( recipe1, Recipe.getBy( Recipe.class, "name", "recipe1" ) );
        assertEquals( recipe1, Recipe.getByName( "recipe1" ) );

        recipe2.setName( "recipe2" );
        recipe1.copyFrom( recipe2, false );

        assertEquals( new Integer( 5 ), recipe1.getCoffee() );
        assertEquals( "recipe2", recipe1.getName() );
        try {
            recipe1.copyFrom( inventory1, false );
            fail();
        }
        catch ( final IllegalArgumentException e ) {

        }

        recipe1.copyFrom( recipe2, true );

        assertEquals( recipe1.getId(), recipe2.getId() );

        // ctrl.createRecipe( recipe1 );
        // ctrl.createRecipe( recipe2 );
        recipe1.save();
        recipe2.save();
        System.out.println( "This is all:" + Recipe.getAll() );
        assertEquals( 2, Recipe.getAll().size() );
        final HibernateUtil util = new HibernateUtil();
        final SessionFactory factory = HibernateUtil.getSessionFactory();
        final Session session = factory.openSession();
        System.out.println( "This is name of recipe 1: " + recipe1.getName() );
        final Criterion criteria = Restrictions.isNotNull( "name" );
        final List<Criterion> criteriaList = new ArrayList<Criterion>();
        criteriaList.add( criteria );

        assertEquals( 2, Recipe.getWhere( criteriaList ).size() );
        recipe1.delete();
        assertEquals( 1, Recipe.getAll().size() );
        Recipe.deleteAll( Recipe.class );
        assertEquals( 0, Recipe.getAll().size() );

        // assertNotNull(Recipe.createCriterionList(criteria));
    }

}
