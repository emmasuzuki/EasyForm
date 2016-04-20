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

public class LongFormFragmentTest {

    private Activity activity;

    private Matcher<View> firstNameInputMatcher = withId(R.id.first_name_input);
    private Matcher<View> lastNameInputMatcher = withId(R.id.last_name_input);
    private Matcher<View> employeeIdInputMatcher = withId(R.id.employee_id_input);
    private Matcher<View> accountInputMatcher = withId(R.id.account_name_input);
    private Matcher<View> passwordInputMatcher = withId(R.id.password_input);
    private Matcher<View> confirmPasswordInputMatcher = withId(R.id.confirm_password_input);

    private ViewInteraction submitButton = onView(withId(R.id.submit_button));
    private ViewInteraction firstNameEditText = onView(editTextIn(firstNameInputMatcher));
    private ViewInteraction lastNameEditText = onView(editTextIn(lastNameInputMatcher));
    private ViewInteraction employeeIdEditText = onView(editTextIn(employeeIdInputMatcher));
    private ViewInteraction accountEditText = onView(editTextIn(accountInputMatcher));
    private ViewInteraction passwordEditText = onView(editTextIn(passwordInputMatcher));
    private ViewInteraction confirmPasswordEditText = onView(editTextIn(confirmPasswordInputMatcher));

    public LongFormFragmentTest(Activity activity) {
        this.activity = activity;
    }

    public void openLongFormFragment() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(activity.getString(R.string.form_3))).perform(click());
    }

    public void testFirstNameField(String str, boolean valid) {
        firstNameEditText.perform(clearText(), typeText(str), closeSoftKeyboard());
        checkError(firstNameInputMatcher, R.string.error_message_empty, valid);
    }

    public void testLastNameField(String str, boolean valid) {
        lastNameEditText.perform(clearText(), typeText(str), closeSoftKeyboard());
        checkError(lastNameInputMatcher, R.string.error_message_empty, valid);
    }

    public void testEmployeeIdField(String str, boolean valid) {
        employeeIdEditText.perform(clearText(), typeText(str), closeSoftKeyboard());
        checkError(employeeIdInputMatcher, R.string.error_message_employee_id, valid);
    }

    public void testAccountField(String str, boolean valid) {
        accountEditText.perform(clearText(), typeText(str), closeSoftKeyboard());
        checkError(accountInputMatcher, R.string.error_message_account, valid);
    }

    public void testPasswordField(String str, boolean valid) {
        passwordEditText.perform(clearText(), typeText(str), closeSoftKeyboard());
        checkError(passwordInputMatcher, R.string.error_message_password, valid);
    }

    public void testConfirmPasswordField(String str, boolean valid) {
        confirmPasswordEditText.perform(clearText(), typeText(str), closeSoftKeyboard());
        checkError(confirmPasswordInputMatcher, R.string.error_message_password, valid);
    }

    public void testSubmitButton(String firstName, String lastName, String employeeId, String account,
                                  String password, String confirmPassword, boolean valid) {
        firstNameEditText.perform(clearText(), typeText(firstName));
        lastNameEditText.perform(clearText(), typeText(lastName));
        employeeIdEditText.perform(clearText(), typeText(employeeId));
        accountEditText.perform(clearText(), typeText(account));
        passwordEditText.perform(clearText(), typeText(password));
        confirmPasswordEditText.perform(clearText(), typeText(confirmPassword), closeSoftKeyboard());

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
