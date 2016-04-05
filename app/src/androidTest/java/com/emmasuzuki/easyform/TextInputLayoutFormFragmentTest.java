/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 emmasuzuki <emma11suzuki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.emmasuzuki.easyform;

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
import static com.emmasuzuki.easyform.ErrorTextMatcher.editTextIn;
import static com.emmasuzuki.easyform.ErrorTextMatcher.hasErrorText;
import static com.emmasuzuki.easyform.ErrorTextMatcher.hasNoErrorText;
import static org.hamcrest.Matchers.not;

public class TextInputLayoutFormFragmentTest {

    private Activity activity;

    private Matcher<View> emptyCheckInputMatcher = withId(R.id.empty_check_input);
    private Matcher<View> digitCheckInputMatcher = withId(R.id.digit_check_input);

    private ViewInteraction submitButton = onView(withId(R.id.submit_button));
    private ViewInteraction emptyCheckEditText = onView(editTextIn(emptyCheckInputMatcher));
    private ViewInteraction digitCheckEditText = onView(editTextIn(digitCheckInputMatcher));

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

    public void testSubmitButton(String emptyCheckStr, String digitCheckStr, boolean valid) {
        emptyCheckEditText.perform(clearText(), typeText(emptyCheckStr));
        digitCheckEditText.perform(clearText(), typeText(digitCheckStr), closeSoftKeyboard());

        if (valid) {
            submitButton.check(matches(isEnabled()));
        } else {
            submitButton.check(matches(not(isEnabled())));
        }
    }
}
