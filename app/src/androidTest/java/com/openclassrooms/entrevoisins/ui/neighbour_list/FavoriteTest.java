package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FavoriteTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNeighbourApiService();
    }

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    @Test
    public void favoriteTest() {
        //get RecyclerView
        ViewInteraction recyclerViewDisplayed = onView(allOf(withId(R.id.list_neighbours), isDisplayed()));


        //navigate into item 3
        recyclerViewDisplayed.perform(actionOnItemAtPosition(2, click()));
        //click on favorite
        onView(allOf(withId(R.id.favoriteButton), childAtPosition(childAtPosition(withClassName(is("android.support.v4.widget.NestedScrollView")), 0), 2), isDisplayed())).perform(click());
        //click on back
        onView(allOf(withId(R.id.backButton), childAtPosition(childAtPosition(withClassName(is("android.support.v4.widget.NestedScrollView")), 0), 1), isDisplayed())).perform(click());

        //navigate into item 5
        recyclerViewDisplayed.perform(actionOnItemAtPosition(4, click()));
        //click on favorite
        onView(allOf(withId(R.id.favoriteButton), childAtPosition(childAtPosition(withClassName(is("android.support.v4.widget.NestedScrollView")), 0), 2), isDisplayed())).perform(click());
        //click on back
        onView(allOf(withId(R.id.backButton), childAtPosition(childAtPosition(withClassName(is("android.support.v4.widget.NestedScrollView")), 0), 1), isDisplayed())).perform(click());

        //navigate into item 6
        recyclerViewDisplayed.perform(actionOnItemAtPosition(5, click()));
        //click on favorite
        onView(allOf(withId(R.id.favoriteButton), childAtPosition(childAtPosition(withClassName(is("android.support.v4.widget.NestedScrollView")), 0), 2), isDisplayed())).perform(click());
        //click on back
        onView(allOf(withId(R.id.backButton), childAtPosition(childAtPosition(withClassName(is("android.support.v4.widget.NestedScrollView")), 0), 1), isDisplayed())).perform(click());


        //navigate into favorites with tab 'Favorite'
        onView(allOf(withContentDescription("Favorites"), childAtPosition(childAtPosition(withId(R.id.tabs), 0), 1), isDisplayed())).perform(click());

        //verify number of favorites == 3
        recyclerViewDisplayed.check(RecyclerViewItemCountAssertion.withItemCount(3));

        //get names of neighbours putted favorites
        String textName0 = service.getNeighbours().get(2).getName();
        String textName1 = service.getNeighbours().get(4).getName();
        String textName2 = service.getNeighbours().get(5).getName();

        //compare names prints and names of list
        onView(allOf(withId(R.id.item_list_name), withText(textName0), isDisplayed())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.item_list_name), withText(textName1), isDisplayed())).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.item_list_name), withText(textName2), isDisplayed())).check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
