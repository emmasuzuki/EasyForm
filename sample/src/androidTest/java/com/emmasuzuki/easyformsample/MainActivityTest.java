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

package com.emmasuzuki.easyformsample;

import android.support.test.rule.ActivityTestRule;

import com.emmasuzuki.easyform.app.MainActivity;

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
    public void testEditTextFormFragment_SubmitButton_AllFieldFilled() {
        editTextFormFragmentTest.testSubmitButton("test", "test@test.com", "test", true);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_AllRequiredFilled() {
        editTextFormFragmentTest.testSubmitButton("test", "test@test.com", "", true);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_NoTextOnEmptyField() {
        editTextFormFragmentTest.testSubmitButton("", "test@test.com", "", false);
    }

    @Test
    public void testEditTextFormFragment_SubmitButton_InvalidEmail() {
        editTextFormFragmentTest.testSubmitButton("test", "test@te", "", false);
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
    public void testTextInputLayoutFormFragmentTest_SubmitButton_Enable() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testSubmitButton("test", "12345", true);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_SubmitButton_NoTextOnEmptyField() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testSubmitButton("", "12345", false);
    }

    @Test
    public void testTextInputLayoutFormFragmentTest_SubmitButton_StringOnDigitField() {
        textInputLayoutFormFragmentTest.openTextInputLayoutFormFragment();
        textInputLayoutFormFragmentTest.testSubmitButton("test", "test", false);
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
    public void testLongFormFragmentTest_SubmitButton_Success() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("first name", "last name", "100", "account", "password", "password", true);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_MissingFirstName() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("", "last name", "100", "account", "password", "password", false);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_MissingLastName() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("first name", "", "100", "account", "password", "password", false);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_WrongEmployeeId() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("first name", "last name", "99", "account", "password", "password", false);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_WrongAccount() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("first name", "last name", "100", "acc", "password", "password", false);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_WrongPassword() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("first name", "last name", "100", "account", "password@", "password", false);
    }

    @Test
    public void testLongFormFragmentTest_SubmitButton_WrongConfirmPassword() {
        longFormFragmentTest.openLongFormFragment();
        longFormFragmentTest.testSubmitButton("first name", "last name", "100", "account", "password", "password@", false);
    }
}
