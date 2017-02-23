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

import java.util.regex.Pattern;

class FormValidator {

    static final int INVALID_VALUE = -1;

    private ErrorType errorType = ErrorType.NONE;
    private String regexPattern = "";
    private float minValue = -Float.MAX_VALUE;
    private float maxValue = Float.MAX_VALUE;
    private int minChars = 0;
    private int maxChars = Integer.MAX_VALUE;

    FormValidator() {}

    FormValidator(ErrorType errorType, String regexPattern, float minValue, float maxValue, int minChars, int maxChars) {
        this.errorType = errorType;

        if (minValue != INVALID_VALUE || maxValue != INVALID_VALUE) {
            this.errorType = ErrorType.VALUE;
            this.minValue = Math.max(-Float.MAX_VALUE, minValue);
            this.maxValue = maxValue == INVALID_VALUE ? Float.MAX_VALUE : maxValue;
        }

        if (minChars != INVALID_VALUE || maxChars != INVALID_VALUE) {
            this.errorType = ErrorType.CHARS;
            this.minChars = Math.max(0, minChars);
            this.maxChars = maxChars == INVALID_VALUE ? Integer.MAX_VALUE : maxChars;
        }

        if (regexPattern != null) {
            this.errorType = ErrorType.PATTERN;
            this.regexPattern = regexPattern;
        }
    }

    boolean isValid(CharSequence s) {
        boolean hasError = false;

        switch (errorType) {
            case EMPTY:
                hasError = TextUtils.isEmpty(s.toString());
                break;

            case PATTERN:
                hasError = !Pattern.compile(regexPattern).matcher(s.toString()).matches();
                break;

            case VALUE:
                try {
                    float value = Float.parseFloat(s.toString());
                    hasError = value < minValue || value > maxValue;
                } catch (Exception e) {
                    hasError = true;
                }
                break;

            case CHARS:
                hasError = s.length() < minChars || s.length() > maxChars;
                break;

            default:
                break;
        }

        return !hasError;
    }

    ErrorType getErrorType() {
        return errorType;
    }

    void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    void setRegexPattern(String regexPattern) {
        errorType = ErrorType.PATTERN;
        this.regexPattern = regexPattern;
    }

    void setMinValue(float minValue) {
        errorType = ErrorType.VALUE;
        this.minValue = minValue;
    }

    void setMaxValue(float maxValue) {
        errorType = ErrorType.VALUE;
        this.maxValue = maxValue;
    }

    void setMinChars(int minChars) {
        errorType = ErrorType.CHARS;
        this.minChars = minChars;
    }

    void setMaxChars(int maxChars) {
        errorType = ErrorType.CHARS;
        this.maxChars = maxChars;
    }
}
