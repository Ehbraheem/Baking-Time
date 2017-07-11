package com.example.profbola.bakingtime;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.profbola.bakingtime.ui.MainActivity;
import com.example.profbola.bakingtime.utils.RecipeAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
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

    private final String CAKE_NAME = "Nutella Pie";
    private final String INGREDIENT_NAME = "heavy cream(cold)";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private Matcher<RecyclerView.ViewHolder> recipeViewHolderBoundedMatcher (final String text) {

        return new BoundedMatcher<RecyclerView.ViewHolder, RecipeAdapter.RecipeViewHolder>(RecipeAdapter.RecipeViewHolder.class) {
            @Override
            protected boolean matchesSafely(RecipeAdapter.RecipeViewHolder item) {
                TextView titleView = (TextView) item.itemView.findViewById(R.id.recipe_list);
                return titleView == null ? false : titleView.getText().toString().contains(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + text);
            }
        };
    }

    @Test
    public void CakeName_AppearInListing() {
        onView(withId(R.id.recipe_list))
                .check(matches(hasDescendant(withText(CAKE_NAME))));
//                .perform(RecyclerViewActions
//                        .scrollToHolder(recipeViewHolderBoundedMatcher(CAKE_NAME)));
    }

    @Test
    public void clickRecipeItem_OpensDetailActivity() {
        onView(withId(R.id.recipe_list))
                .perform(click())
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredients_listing))
                .check(matches(hasDescendant(withText(INGREDIENT_NAME))));
//        onView(withId(R.id.ingredients_listing))
//                .perform(RecyclerViewActions.scrollTo(withId(R.id.ingredient_name)))
//                .check((ViewAssertion) not(doesNotExist()));
    }

}
