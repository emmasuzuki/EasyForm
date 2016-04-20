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
import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
            emptyCheckField.check(matches(ErrorTextMatcher.hasNoErrorText()));
        } else {
            emptyCheckField.check(matches(ErrorTextMatcher.hasErrorText(activity.getString(R.string.error_message_empty))));
        }
    }

    public void testEmailField(String str, boolean valid) {
        emailField.perform(clearText(), typeText(str), closeSoftKeyboard());

        if (valid) {
            emailField.check(matches(ErrorTextMatcher.hasNoErrorText()));
        } else {
            emailField.check(matches(ErrorTextMatcher.hasErrorText(activity.getString(R.string.error_message_email))));
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
