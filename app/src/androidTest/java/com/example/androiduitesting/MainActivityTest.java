package com.example.androiduitesting;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity(){
        // Click on Add City button
        onView(withId(R.id.button_add)).perform(click());

        // Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"), ViewActions.closeSoftKeyboard());
        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if text "Edmonton" is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity(){
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
        //Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
        //Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView(){
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if in the Adapter view (given id of that adapter view),there is a data
        // (which is an instance of String) located at position zero.
        // If this data matches the text we provided then Voila! Our test should pass
        // You can also use anything() in place of is(instanceOf(String.class))
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0)
                .check(matches(isDisplayed()));
    }

    // ---------- NEW TESTS FOR ShowActivity ----------

    @Test
    public void testSwitchesToShowActivity() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Edmonton"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Click first row in the list
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Check whether the activity correctly switched
        onView(withId(R.id.text_city)).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameIsConsistentInShowActivity() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Vancouver"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Click first row
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Test whether the city name is consistent
        onView(withId(R.id.text_city)).check(matches(withText("Vancouver")));
    }

    @Test
    public void testBackButtonReturnsToMainActivity() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Calgary"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Click first row -> go to ShowActivity
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Test the "back" button
        onView(withId(R.id.button_back)).perform(click());

        // Verify we're back in MainActivity (the list is visible again)
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
