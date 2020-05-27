package com.sam.employeerollcallapplication

import androidx.test.espresso.UiController


import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf

object Actions {
    fun clickButton(id: Int) {
        onView(withId(id)).perform(click())
    }

    fun tapOnBackButton() {
        onView(withContentDescription("Navigate up")).perform(click())
    }

    fun tapViewWithText(text: String) {
        onView(withText(text)).perform(click())
    }

    fun tapViewWithText(@StringRes text: Int) {
        onView(withText(text)).perform(click())
    }

//    fun tapOnViewWithIdAndWithText(@IdRes id: Int, text: String) {
//        Selectors.getViewInteractionWithIdAndText(id, text).perform(click())
//    }

    fun enterText(id: Int, text: String) {
        onView(withId(id)).perform(typeText(text))
    }

    fun scrollToIgnoringVisibility(): ViewAction {
        // adapted from https://stackoverflow.com/a/44850584/1016377
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(
                    withEffectiveVisibility(Visibility.VISIBLE), isDescendantOfA(
                        CoreMatchers.anyOf(
                            isAssignableFrom(ScrollView::class.java),
                            isAssignableFrom(NestedScrollView::class.java),
                            isAssignableFrom(HorizontalScrollView::class.java),
                            isAssignableFrom(RecyclerView::class.java)
                        )
                    )
                )
            }

            override fun getDescription(): String? {
                return "scroll to item in scrollable view (without display visibility constraint)"
            }

            override fun perform(uiController: UiController, view: View) {
                ScrollToAction().perform(uiController, view)
            }
        }
    }

    fun scrollToTextView(text: String) {
        onView(withText(text)).perform(scrollTo())
    }

    fun scrollToViewWithId(@IdRes id: Int) {
        onView(withId(id)).perform(scrollToIgnoringVisibility())
    }

    fun scrollToViewInsideRecyclerView(@IdRes recyclerViewId: Int, viewText: String) {
        onView(withId(recyclerViewId)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                withText(containsString(viewText))
            )
        )
    }

    fun scrollToRecyclerViewItemAtPosition(@IdRes recyclerViewId: Int, itemPosition: Int) {
        onView(withId(recyclerViewId)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                itemPosition
            )
        )
    }

    fun scrollToChildViewInsideRecyclerView(@IdRes recyclerViewId: Int, childText: String) {
        onView(withId(recyclerViewId)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(childText))
            )
        )
    }

    fun scrollToChildViewInsideRecyclerView(@IdRes recyclerViewId: Int, @StringRes childText: Int) {
        val childViewItemMatch = hasDescendant(withText(childText))
        onView(withId(recyclerViewId))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(childViewItemMatch))
    }

    fun scrollToChildViewInsideRecyclerView(
        @IdRes recyclerViewId: Int,
        @IdRes childId: Int,
        childText: String
    ) {
        onView(withId(recyclerViewId)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(allOf(withId(childId), withText(childText)))
            )
        )
    }

    fun scrollToChildViewInsideRecyclerView(
        @IdRes recyclerViewId: Int,
        @IdRes childId: Int,
        @StringRes childText: Int
    ) {
        onView(withId(recyclerViewId)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(allOf(withId(childId), withText(childText)))
            )
        )
    }

    fun closeSoftKeyboard(id: Int) {
        onView(withId(id)).perform(closeSoftKeyboard())
    }

    fun swipeLeftOnView(@IdRes id: Int) {
        onView(withId(id)).perform(swipeLeft())
    }

    fun scrollToTheBottom(@IdRes id: Int) {
        onView(withId(id)).perform(swipeUp())
    }

    fun scrollToTheBottomInViewPager(@IdRes id: Int) {
        onView(AllOf.allOf(withId(id), isDisplayed())).perform(swipeUp())

    }

}