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
import android.support.annotation.StringRes;
import android.support.test.espresso.ViewInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.emmasuzuki.easyformsample.ErrorTextMatcher.hasErrorText;
import static com.emmasuzuki.easyformsample.ErrorTextMatcher.hasNoErrorText;
import static org.hamcrest.Matchers.not;

public class EditTextFormFragmentTest {

    private Activity activity;

    private Matcher<View> emptyFieldMatcher = withId(R.id.empty_check_edittext);
    private Matcher<View> emailFieldMatcher = withId(R.id.email_check_edittext);
    private Matcher<View> noCheckFieldMatcher = withId(R.id.no_check_edittext);
    private Matcher<View> autoCompleteFieldMatcher = withId(R.id.auto_complete_textview);

    private ViewInteraction emptyCheckField = onView(emptyFieldMatcher);
    private ViewInteraction emailField = onView(emailFieldMatcher);
    private ViewInteraction noCheckField = onView(noCheckFieldMatcher);
    private ViewInteraction autoCompleteField = onView(autoCompleteFieldMatcher);
    private ViewInteraction submitButton = onView(withId(R.id.submit_button));

    public EditTextFormFragmentTest(Activity activity) {
        this.activity = activity;
    }

    public void testEmptyCheckField(String str, boolean valid) {
        emptyCheckField.perform(clearText(), typeText(str));
        emailField.perform(click(), closeSoftKeyboard());
        checkError(emptyFieldMatcher, R.string.error_message_empty, valid);
    }

    public void testEmailField(String str, boolean valid) {
        emailField.perform(click(), clearText(), typeText(str));
        emptyCheckField.perform(click(), closeSoftKeyboard());
        checkError(emailFieldMatcher, R.string.error_message_email, valid);
    }

    public void testAutoCompleteField(String str, boolean valid) {
        autoCompleteField.perform(click(), clearText(), typeText(str));
        emptyCheckField.perform(click(), closeSoftKeyboard());
        checkError(autoCompleteFieldMatcher, R.string.error_message_empty, valid);
    }

    public void testSubmitButton(String emptyCheckStr, String emailStr, String noCheckStr, String autoCompleteStr, boolean valid) {
        emptyCheckField.perform(clearText(), typeText(emptyCheckStr));
        emailField.perform(clearText(), typeText(emailStr));
        noCheckField.perform(clearText(), typeText(noCheckStr));
        autoCompleteField.perform(clearText(), typeText(autoCompleteStr), closeSoftKeyboard());

        if (valid) {
            submitButton.check(matches(isEnabled()));
        } else {
            submitButton.check(matches(not(isEnabled())));
        }
    }

    private void checkError(Matcher<View> matcher, @StringRes int errorMessageId, boolean valid) {
        if (valid) {
            onView(matcher).check(matches(hasNoErrorText()));
        } else {
            onView(matcher).check(matches(hasErrorText(activity.getString(errorMessageId))));
        }
    }
}
