package com.example.profbola.bakingtime;

import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.profbola.bakingtime.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Created by prof.BOLA on 7/6/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    public final String CAKE_NAME = "Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void CakeName_AppearInListing() {
        onData(anything()).inAdapterView(withId(R.id.recipe_list))
                .atPosition(0).onChildView(withId(R.id.recipe_title))
                .check(matches(withText(CAKE_NAME)));
    }

    @Test
    public void clickRecipeItem_OpensDetailActivity() {
        onData(anything()).inAdapterView(withId(R.id.recipe_list))
                .atPosition(0)
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.ingredients_listing))
                .atPosition(0)
                .check((ViewAssertion) not(doesNotExist()));
    }

}
