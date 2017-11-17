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
import static org.hamcrest.core.IsNot.not;

public class LongFormFragmentTest {

    private Activity activity;

    private Matcher<View> firstNameInputMatcher = withId(R.id.first_name_input);
    private Matcher<View> lastNameInputMatcher = withId(R.id.last_name_input);
    private Matcher<View> employeeIdInputMatcher = withId(R.id.employee_id_input);
    private Matcher<View> accountInputMatcher = withId(R.id.account_name_input);
    private Matcher<View> passwordInputMatcher = withId(R.id.password_input);
    private Matcher<View> confirmPasswordInputMatcher = withId(R.id.confirm_password_input);

    private ViewInteraction submitButton = onView(withId(R.id.submit_button));
    private ViewInteraction firstNameEditText = editTextIn(R.id.first_name_input);
    private ViewInteraction lastNameEditText = editTextIn(R.id.last_name_input);
    private ViewInteraction employeeIdEditText = editTextIn(R.id.employee_id_input);
    private ViewInteraction accountEditText = editTextIn(R.id.account_name_input);
    private ViewInteraction passwordEditText = editTextIn(R.id.password_input);
    private ViewInteraction confirmPasswordEditText = editTextIn(R.id.confirm_password_input);

    public LongFormFragmentTest(Activity activity) {
        this.activity = activity;
    }

    public void openLongFormFragment() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(activity.getString(R.string.form_3))).perform(click());
    }

    public void testFirstNameField(String str, boolean valid) {
        // NOTE: typeText("") doesn't touch view.
        // Add click before typeText to enforce focus
        firstNameEditText.perform(click(), clearText(), typeText(str));
        // Then unfocus firstNameEditText
        lastNameEditText.perform(click(), closeSoftKeyboard());
        checkError(firstNameInputMatcher, R.string.error_message_empty, valid);
    }

    public void testLastNameField(String str, boolean valid) {
        lastNameEditText.perform(click(), clearText(), typeText(str));
        firstNameEditText.perform(click(), closeSoftKeyboard());
        checkError(lastNameInputMatcher, R.string.error_message_empty, valid);
    }

    public void testEmployeeIdField(String str, boolean valid) {
        employeeIdEditText.perform(click(), clearText(), typeText(str));
        firstNameEditText.perform(click(), closeSoftKeyboard());
        checkError(employeeIdInputMatcher, R.string.error_message_employee_id, valid);
    }

    public void testAccountField(String str, boolean valid) {
        accountEditText.perform(click(), clearText(), typeText(str));
        firstNameEditText.perform(click(), closeSoftKeyboard());
        checkError(accountInputMatcher, R.string.error_message_account, valid);
    }

    public void testPasswordField(String str, boolean valid) {
        passwordEditText.perform(click(), clearText(), typeText(str));
        firstNameEditText.perform(click(), closeSoftKeyboard());
        checkError(passwordInputMatcher, R.string.error_message_password, valid);
    }

    public void testConfirmPasswordField(String str, boolean valid) {
        confirmPasswordEditText.perform(click(), clearText(), typeText(str));
        firstNameEditText.perform(click(), closeSoftKeyboard());
        checkError(confirmPasswordInputMatcher, R.string.error_message_password, valid);
    }

    public void testSubmitButton_LastField_Valid() {
        // Input everything except employeeId
        firstNameEditText.perform(clearText(), typeText("first name"), closeSoftKeyboard());
        lastNameEditText.perform(clearText(), typeText("last name"), closeSoftKeyboard());
        accountEditText.perform(clearText(), typeText("account"), closeSoftKeyboard());
        passwordEditText.perform(clearText(), typeText("password"), closeSoftKeyboard());
        confirmPasswordEditText.perform(clearText(), typeText("password"), closeSoftKeyboard());
        submitButton.check(matches(not(isEnabled())));

        // Last field to fill, when cursor is on the last field, submitButton should be enabled
        employeeIdEditText.perform(click());
        submitButton.check(matches(isEnabled()));

        // Fill a valid value in employeeId field and click submitButton. submitButton should be kept enabled
        employeeIdEditText.perform(clearText(), typeText("100"), closeSoftKeyboard());
        submitButton.perform(click());
        submitButton.check(matches(isEnabled()));
    }

    public void testSubmitButton_LastField_Invalid() {
        // Input everything except employeeId
        firstNameEditText.perform(clearText(), typeText("first name"), closeSoftKeyboard());
        lastNameEditText.perform(clearText(), typeText("last name"), closeSoftKeyboard());
        accountEditText.perform(clearText(), typeText("account"), closeSoftKeyboard());
        passwordEditText.perform(clearText(), typeText("password"), closeSoftKeyboard());
        confirmPasswordEditText.perform(clearText(), typeText("password"), closeSoftKeyboard());
        submitButton.check(matches(not(isEnabled())));

        // Last field to fill, when cursor is on the last field, submitButton should be enabled
        employeeIdEditText.perform(click());
        submitButton.check(matches(isEnabled()));

        // Fill a invalid value in employeeId field and click submitButton.
        employeeIdEditText.perform(clearText(), typeText("99"), closeSoftKeyboard());
        submitButton.perform(click());

        // submitButton should be kept enabled but employeeIdEditText should have error text
        checkError(employeeIdInputMatcher, R.string.error_message_employee_id, false);
        submitButton.check(matches(isEnabled()));
    }

    private void checkError(Matcher<View> matcher, @StringRes int errorMessageId, boolean valid) {
        if (valid) {
            onView(matcher).check(matches(hasNoErrorText()));
        } else {
            onView(matcher).check(matches(hasErrorText(activity.getString(errorMessageId))));
        }
    }
}
