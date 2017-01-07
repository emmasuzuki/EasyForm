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

public class FormValidator {

    private ErrorType errorType;
    private String regexPattern;
    private float minValue;
    private float maxValue;
    private int minChars;
    private int maxChars;

    public boolean isValid(CharSequence s) {
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

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinChars(int minChars) {
        this.minChars = minChars;
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }
}
