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

import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by miyukisuzuki on 6/2/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class EasyFormTextWatcherTest {

    private EasyFormTextWatcher easyFormTextWatcher;
    private SpannableStringBuilder stringBuilder;

    private boolean renderErrorCalled, clearErrorCalled;

    @Before
    public void setUpBeforeEach() {
        renderErrorCalled = clearErrorCalled = false;

        easyFormTextWatcher = new EasyFormTextWatcher(null) {
            @Override
            protected void renderError() {
                renderErrorCalled = true;
            }

            @Override
            protected void clearError() {
                clearErrorCalled = true;
            }
        };

        mockStatic(TextUtils.class);
        stringBuilder = Mockito.mock(SpannableStringBuilder.class);
    }

    @Test
    public void testAfterTextChanged_Empty_EmptyString() {
        when(TextUtils.isEmpty("")).thenReturn(true);
        easyFormTextWatcher.setErrorType(ErrorType.EMPTY);
        when(stringBuilder.toString()).thenReturn("");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Empty_NonEmptyString() {
        String testString = "test";
        when(TextUtils.isEmpty(testString)).thenReturn(false);
        easyFormTextWatcher.setErrorType(ErrorType.EMPTY);
        when(stringBuilder.toString()).thenReturn(testString);

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertFalse(renderErrorCalled);
        assertTrue(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Pattern_Valid() {
        easyFormTextWatcher.setErrorType(ErrorType.PATTERN);
        easyFormTextWatcher.setRegexPattern("[0-9]+");
        when(stringBuilder.toString()).thenReturn("123");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertFalse(renderErrorCalled);
        assertTrue(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Pattern_Invalid() {
        easyFormTextWatcher.setErrorType(ErrorType.PATTERN);
        easyFormTextWatcher.setRegexPattern("[0-9]+");
        when(stringBuilder.toString()).thenReturn("12*3");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Value_LessThanMin() {
        easyFormTextWatcher.setErrorType(ErrorType.VALUE);
        easyFormTextWatcher.setMinValue(1);
        when(stringBuilder.toString()).thenReturn("0.9");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Value_EqualToMin() {
        easyFormTextWatcher.setErrorType(ErrorType.VALUE);
        easyFormTextWatcher.setMinValue(1);
        easyFormTextWatcher.setMaxValue(Float.MAX_VALUE);
        when(stringBuilder.toString()).thenReturn("1");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertFalse(renderErrorCalled);
        assertTrue(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Value_EqualToMax() {
        easyFormTextWatcher.setErrorType(ErrorType.VALUE);
        easyFormTextWatcher.setMinValue(Float.MIN_VALUE);
        easyFormTextWatcher.setMaxValue(100);
        when(stringBuilder.toString()).thenReturn("100");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertFalse(renderErrorCalled);
        assertTrue(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Value_MoreThanMax() {
        easyFormTextWatcher.setErrorType(ErrorType.VALUE);
        easyFormTextWatcher.setMinValue(Float.MIN_VALUE);
        easyFormTextWatcher.setMaxValue(100);
        when(stringBuilder.toString()).thenReturn("100.1");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Value_NotNumber() {
        easyFormTextWatcher.setErrorType(ErrorType.VALUE);
        when(stringBuilder.toString()).thenReturn("100.&1");

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Chars_LessThanMin() {
        easyFormTextWatcher.setErrorType(ErrorType.CHARS);
        easyFormTextWatcher.setMinChars(5);
        easyFormTextWatcher.setMaxChars(Integer.MAX_VALUE);
        when(stringBuilder.length()).thenReturn(4);

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Chars_EqualToMin() {
        easyFormTextWatcher.setErrorType(ErrorType.CHARS);
        easyFormTextWatcher.setMinChars(4);
        easyFormTextWatcher.setMaxChars(Integer.MAX_VALUE);
        when(stringBuilder.length()).thenReturn(4);

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertFalse(renderErrorCalled);
        assertTrue(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Chars_EqualToMax() {
        easyFormTextWatcher.setErrorType(ErrorType.CHARS);
        easyFormTextWatcher.setMinChars(0);
        easyFormTextWatcher.setMaxChars(5);
        when(stringBuilder.length()).thenReturn(5);

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertFalse(renderErrorCalled);
        assertTrue(clearErrorCalled);
    }

    @Test
    public void testAfterTextChanged_Chars_MoreThanMax() {
        easyFormTextWatcher.setErrorType(ErrorType.CHARS);
        easyFormTextWatcher.setMinChars(0);
        easyFormTextWatcher.setMaxChars(5);
        when(stringBuilder.length()).thenReturn(6);

        easyFormTextWatcher.afterTextChanged(stringBuilder);

        assertTrue(renderErrorCalled);
        assertFalse(clearErrorCalled);
    }
}
