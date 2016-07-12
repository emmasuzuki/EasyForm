/*
 * Copyright 2016 Emma Suzuki <emma11suzuki@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emmasuzuki.easyformsample;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.emmasuzuki.easyformsample.ErrorTextMatcher.editTextIn;
import static com.emmasuzuki.easyformsample.ErrorTextMatcher.hasErrorText;
import static com.emmasuzuki.easyformsample.ErrorTextMatcher.hasNoErrorText;
import static org.hamcrest.Matchers.not;

public class TextInputLayoutFormFragmentTest {

    private Activity activity;

    private Matcher<View> emptyCheckInputMatcher = withId(R.id.empty_check_input);
    private Matcher<View> digitCheckInputMatcher = withId(R.id.digit_check_input);
    private Matcher<View> valueCheckInputMatcher = withId(R.id.value_check_input);

    private ViewInteraction submitButton = onView(withId(R.id.submit_button));
    private ViewInteraction emptyCheckEditText = onView(editTextIn(emptyCheckInputMatcher));
    private ViewInteraction digitCheckEditText = onView(editTextIn(digitCheckInputMatcher));
    private ViewInteraction valueCheckEditText = onView(editTextIn(valueCheckInputMatcher));

    public TextInputLayoutFormFragmentTest(Activity activity) {
        this.activity = activity;
    }

    public void openTextInputLayoutFormFragment() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(activity.getString(R.string.form_2))).perform(click());
    }

    public void testEmptyCheckField(String str, boolean valid) {
        emptyCheckEditText.perform(clearText(), typeText(str), closeSoftKeyboard());

        if (valid) {
            onView(emptyCheckInputMatcher).check(matches(hasNoErrorText()));
        } else {
            onView(emptyCheckInputMatcher).check(matches(hasErrorText(activity.getString(R.string.error_message_empty))));
        }
    }

    public void testDigitCheckField(String str, boolean valid) {
        digitCheckEditText.perform(clearText(), typeText(str), closeSoftKeyboard());

        if (valid) {
            onView(digitCheckInputMatcher).check(matches(hasNoErrorText()));
        } else {
            onView(digitCheckInputMatcher).check(matches(hasErrorText(activity.getString(R.string.error_message_digit))));
        }
    }

    public void testValueCheckField(String str, boolean valid) {
        valueCheckEditText.perform(clearText(), typeText(str), closeSoftKeyboard());

        if (valid) {
            onView(valueCheckInputMatcher).check(matches(hasNoErrorText()));
        } else {
            onView(valueCheckInputMatcher).check(matches(hasErrorText(activity.getString(R.string.error_message_value))));
        }
    }

    public void testSubmitButton(String emptyCheckStr, String digitCheckStr, String valueCheckStr, boolean valid) {
        emptyCheckEditText.perform(clearText(), typeText(emptyCheckStr));
        digitCheckEditText.perform(clearText(), typeText(digitCheckStr));
        valueCheckEditText.perform(clearText(), typeText(valueCheckStr), closeSoftKeyboard());

        if (valid) {
            submitButton.check(matches(isEnabled()));
        } else {
            submitButton.check(matches(not(isEnabled())));
        }
    }
}
