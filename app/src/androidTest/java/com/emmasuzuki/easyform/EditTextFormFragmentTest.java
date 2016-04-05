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
import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.emmasuzuki.easyform.ErrorTextMatcher.hasErrorText;
import static com.emmasuzuki.easyform.ErrorTextMatcher.hasNoErrorText;
import static org.hamcrest.Matchers.not;

public class EditTextFormFragmentTest {

    private Activity activity;

    private ViewInteraction emptyCheckField = onView(withId(R.id.empty_check_edittext));
    private ViewInteraction emailField = onView(withId(R.id.email_check_edittext));
    private ViewInteraction noCheckField = onView(withId(R.id.no_check_edittext));
    private ViewInteraction submitButton = onView(withId(R.id.submit_button));

    public EditTextFormFragmentTest(Activity activity) {
        this.activity = activity;
    }

    public void testEmptyCheckField(String str, boolean valid) {
        emptyCheckField.perform(clearText(), typeText(str), closeSoftKeyboard());

        if (valid) {
            emptyCheckField.check(matches(hasNoErrorText()));
        } else {
            emptyCheckField.check(matches(hasErrorText(activity.getString(R.string.error_message_empty))));
        }
    }

    public void testEmailField(String str, boolean valid) {
        emailField.perform(clearText(), typeText(str), closeSoftKeyboard());

        if (valid) {
            emailField.check(matches(hasNoErrorText()));
        } else {
            emailField.check(matches(hasErrorText(activity.getString(R.string.error_message_email))));
        }
    }

    public void testSubmitButton(String emptyCheckStr, String emailStr, String noCheckStr, boolean valid) {
        emptyCheckField.perform(clearText(), typeText(emptyCheckStr));
        emailField.perform(clearText(), typeText(emailStr));
        noCheckField.perform(clearText(), typeText(noCheckStr), closeSoftKeyboard());

        if (valid) {
            submitButton.check(matches(isEnabled()));
        } else {
            submitButton.check(matches(not(isEnabled())));
        }
    }
}
