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

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private EditTextFormFragmentTest editTextFormFragmentTest;
    private TextInputLayoutFormFragmentTest textInputLayoutFormFragmentTest;
    private LongFormFragmentTest longFormFragmentTest;

    @Before
    public void setUp() {
        MainActivity activity = activityTestRule.getActivity();
        editTextFormFragmentTest = new EditTextFormFragmentTest(activity);
        textInputLayoutFormFragmentTest = new TextInputLayoutFormFragmentTest(activity);
        longFormFragmentTest = new LongFormFragmentTest(activity);
    }

    @Test
    public void testEditTextFormFragment_EmptyCheckField_Success() {
        editTextFormFragmentTest.testEmptyCheckField("test", true);
    }

    @Test
    public void testEditTextFormFragment_EmptyCheckField_Empty() {
        editTextFormFragmentTest.testEmptyCheckField("", false);
    }

    @Test
    public void testEditTextFormFragment_EmailField_Success() {
        editTextFormFragmentTest.testEmailField("test@test.com", true);
    }

    @Test
    public void testEditTextFormFragment_EmailField_Empty() {
        editTextFormFragmentTest.testEmailField("", false);
    }

    @Test
    public void testEditTextFormFragment_EmailField_NotValidFormat() {
        editTextFormFragmentTest.testEmailField("test@t", false);
    }

    @Test
    public void testEditTextFormFragment_AutoCompleteField_Success() {
        editTextFormFragmentTest.testAutoCompleteField("test", true);
    }

    @Test
    public void testEditTextFormFragment_AutoCompleteField_Empty() {
        editTextFormFragmentTest.testAutoCompleteField("", false);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_AllFieldFilled() {
        editTextFormFragmentTest.testSubmitButton("test", "test@test.com", "test", "test", true);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_AllRequiredFilled() {
        editTextFormFragmentTest.testSubmitButton("test", "test@test.com", "", "test", true);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_NoTextOnEmptyField() {
        editTextFormFragmentTest.testSubmitButton("", "test@test.com", "", "test", false);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_InvalidEmail() {
        editTextFormFragmentTest.testSubmitButton("test", "test@te", "", "test", false);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_NoTextOnAutoCompleteField() {
        editTextFormFragmentTest.testSubmitButton("test", "test@test.com", "", "", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_EmptyCheckInput_Success() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testEmptyCheckField("test", true);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_EmptyCheckInput_Empty() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testEmptyCheckField("", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_DigitCheckInput_Success() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testDigitCheckField("12345", true);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_DigitCheckInput_Empty() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testDigitCheckField("", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_DigitCheckInput_String() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testDigitCheckField("test", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_ValueCheckInput_Success() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testValueCheckField("-100", true);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_ValueCheckInput_String() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testValueCheckField("string", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_ValueCheckInput_BelowMin() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testValueCheckField("-100.1", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_SubmitButton_Enable() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testSubmitButton("test", "12345", "0", true);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_SubmitButton_NoTextOnEmptyField() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testSubmitButton("", "12345", "0", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_SubmitButton_StringOnDigitField() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testSubmitButton("test", "test", "0", false);
    }

    @Test
    public void testLongFormFragmentTest_FirstNameField_Success() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testFirstNameField("test", true);
    }

    @Test
    public void testLongFormFragmentTest_FirstNameField_Empty() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testFirstNameField("", false);
    }

    @Test
    public void testLongFormFragmentTest_LastNameField_Success() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testLastNameField("test", true);
    }

    @Test
    public void testLongFormFragmentTest_LastNameField_Empty() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testLastNameField("", false);
    }

    @Test
    public void testLongFormFragmentTest_EmployeeIdField_Success_100() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testEmployeeIdField("100", true);
    }

    @Test
    public void testLongFormFragmentTest_EmployeeIdField_Failure_99() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testEmployeeIdField("99", false);
    }

    @Test
    public void testLongFormFragmentTest_AccountField_Failure_5Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testAccountField("abcde", false);
    }

    @Test
    public void testLongFormFragmentTest_AccountField_Success_6Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testAccountField("abcdef", true);
    }

    @Test
    public void testLongFormFragmentTest_AccountField_Success_12Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testAccountField("abcdefghijkl", true);
    }

    @Test
    public void testLongFormFragmentTest_AccountField_Failure_13Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testAccountField("abcdefghijklm", false);
    }

    @Test
    public void testLongFormFragmentTest_PasswordField_Failure_SpecialChars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testPasswordField("te!st@", false);
    }

    @Test
    public void testLongFormFragmentTest_PasswordField_Failure_5Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testPasswordField("abcde", false);
    }

    @Test
    public void testLongFormFragmentTest_PasswordField_Success_6Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testPasswordField("abcdef", true);
    }

    @Test
    public void testLongFormFragmentTest_PasswordField_Success_12Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testPasswordField("abcdefghijkl", true);
    }

    @Test
    public void testLongFormFragmentTest_PasswordField_Failure_13Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testPasswordField("abcdefghijklm", false);
    }

    @Test
    public void testLongFormFragmentTest_ConfirmPasswordField_Failure_SpecialChars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testConfirmPasswordField("te!st@", false);
    }

    @Test
    public void testLongFormFragmentTest_ConfirmPasswordField_Failure_5Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testConfirmPasswordField("abced", false);
    }

    @Test
    public void testLongFormFragmentTest_ConfirmPasswordField_Success_6Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testConfirmPasswordField("abcdef", true);
    }

    @Test
    public void testLongFormFragmentTest_ConfirmPasswordField_Success_12Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testConfirmPasswordField("abcdefghijkl", true);
    }

    @Test
    public void testLongFormFragmentTest_ConfirmPasswordField_Failure_13Chars() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testConfirmPasswordField("abcdefghijklm", false);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_LastField_Valid() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton_LastField_Valid();
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_LastField_Invalid() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton_LastField_Invalid();
    }
}
