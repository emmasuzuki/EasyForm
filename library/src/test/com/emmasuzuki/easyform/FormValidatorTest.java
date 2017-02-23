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

package com.emmasuzuki.easyform;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.emmasuzuki.easyform.FormValidator.INVALID_VALUE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by miyukisuzuki on 6/2/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class FormValidatorTest {

    private FormValidator formValidator = new FormValidator();

    @Before
    public void setUpBeforeEach() {
        mockStatic(TextUtils.class);
    }

    @Test
    public void testConstructor_SetErrorType() {
        FormValidator validator = new FormValidator(ErrorType.EMPTY, null, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE);
        assertEquals(validator.getErrorType(), ErrorType.EMPTY);
    }

    @Test
    public void testConstructor_Set_RegexPatternError() {
        FormValidator validator = new FormValidator(null, "[0-9]+", INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE);
        assertEquals(validator.getErrorType(), ErrorType.PATTERN);
    }

    @Test
    public void testConstructor_Set_ValueError_MinValue() {
        FormValidator validator = new FormValidator(null, null, 0.3f, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE);
        assertEquals(validator.getErrorType(), ErrorType.VALUE);
    }

    @Test
    public void testConstructor_Set_ValueError_MaxValue() {
        FormValidator validator = new FormValidator(null, null, INVALID_VALUE, 0.6f, INVALID_VALUE, INVALID_VALUE);
        assertEquals(validator.getErrorType(), ErrorType.VALUE);
    }

    @Test
    public void testConstructor_Set_CharError_MinChar() {
        FormValidator validator = new FormValidator(null, null, INVALID_VALUE, INVALID_VALUE, 1, INVALID_VALUE);
        assertEquals(validator.getErrorType(), ErrorType.CHARS);
    }

    @Test
    public void testConstructor_Set_CharError_MaxChar() {
        FormValidator validator = new FormValidator(null, null, INVALID_VALUE, INVALID_VALUE, INVALID_VALUE, 5);
        assertEquals(validator.getErrorType(), ErrorType.CHARS);
    }

    @Test
    public void testAfterTextChanged_Empty_EmptyString() {
        when(TextUtils.isEmpty("")).thenReturn(true);
        formValidator.setErrorType(ErrorType.EMPTY);
        assertFalse(formValidator.isValid(""));
    }

    @Test
    public void testAfterTextChanged_Empty_NonEmptyString() {
        String testString = "test";
        when(TextUtils.isEmpty(testString)).thenReturn(false);
        formValidator.setErrorType(ErrorType.EMPTY);
        assertTrue(formValidator.isValid(testString));
    }

    @Test
    public void testAfterTextChanged_Pattern_Valid() {
        formValidator.setErrorType(ErrorType.PATTERN);
        formValidator.setRegexPattern("[0-9]+");
        assertTrue(formValidator.isValid("123"));
    }

    @Test
    public void testAfterTextChanged_Pattern_Invalid() {
        formValidator.setErrorType(ErrorType.PATTERN);
        formValidator.setRegexPattern("[0-9]+");
        assertFalse(formValidator.isValid("12*3"));
    }

    @Test
    public void testAfterTextChanged_Value_LessThanMin() {
        formValidator.setErrorType(ErrorType.VALUE);
        formValidator.setMinValue(1);
        assertFalse(formValidator.isValid("0.9"));
    }

    @Test
    public void testAfterTextChanged_Value_EqualToMin() {
        formValidator.setErrorType(ErrorType.VALUE);
        formValidator.setMinValue(1);
        formValidator.setMaxValue(Float.MAX_VALUE);
        assertTrue(formValidator.isValid("1"));
    }

    @Test
    public void testAfterTextChanged_Value_EqualToMax() {
        formValidator.setErrorType(ErrorType.VALUE);
        formValidator.setMinValue(Float.MIN_VALUE);
        formValidator.setMaxValue(100);
        assertTrue(formValidator.isValid("100"));
    }

    @Test
    public void testAfterTextChanged_Value_MoreThanMax() {
        formValidator.setErrorType(ErrorType.VALUE);
        formValidator.setMinValue(Float.MIN_VALUE);
        formValidator.setMaxValue(100);
        assertFalse(formValidator.isValid("100.1"));
    }

    @Test
    public void testAfterTextChanged_Value_NotNumber() {
        formValidator.setErrorType(ErrorType.VALUE);
        assertFalse(formValidator.isValid("100.&1"));
    }

    @Test
    public void testAfterTextChanged_Chars_LessThanMin() {
        formValidator.setErrorType(ErrorType.CHARS);
        formValidator.setMinChars(5);
        formValidator.setMaxChars(Integer.MAX_VALUE);
        assertFalse(formValidator.isValid("abcd"));
    }

    @Test
    public void testAfterTextChanged_Chars_EqualToMin() {
        formValidator.setErrorType(ErrorType.CHARS);
        formValidator.setMinChars(4);
        formValidator.setMaxChars(Integer.MAX_VALUE);
        assertTrue(formValidator.isValid("abcd"));
    }

    @Test
    public void testAfterTextChanged_Chars_EqualToMax() {
        formValidator.setErrorType(ErrorType.CHARS);
        formValidator.setMinChars(0);
        formValidator.setMaxChars(5);
        assertTrue(formValidator.isValid("abcde"));
    }

    @Test
    public void testAfterTextChanged_Chars_MoreThanMax() {
        formValidator.setErrorType(ErrorType.CHARS);
        formValidator.setMinChars(0);
        formValidator.setMaxChars(5);
        assertFalse(formValidator.isValid("abcdef"));
    }
}
