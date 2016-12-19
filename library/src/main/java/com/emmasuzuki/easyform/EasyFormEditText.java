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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

public class EasyFormEditText extends EditText {

    private static final int INVALID_VALUE = -1;

    private ErrorType errorType;
    private String regexPattern = "";
    private float minValue = Float.MIN_VALUE;
    private float maxValue = Float.MAX_VALUE;
    private int minChars = 0;
    private int maxChars = Integer.MAX_VALUE;
    private String errorMessage;
    private ShowErrorOn showErrorOn;

    private EasyFormTextWatcher easyFormTextWatcher = new EasyFormTextWatcher(this) {

        @Override
        protected void renderError() {
            setError(errorMessage);
        }

        @Override
        protected void clearError() {
            setError(null);
        }
    };

    public EasyFormEditText(Context context) {
        super(context);
    }

    public EasyFormEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setPropertyFromAttributes(attrs);
        }
    }

    public EasyFormEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            setPropertyFromAttributes(attrs);
        }
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
        setErrorType(ErrorType.PATTERN);
        easyFormTextWatcher.setRegexPattern(regexPattern);
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        setErrorType(ErrorType.VALUE);
        easyFormTextWatcher.setMinValue(minValue);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        setErrorType(ErrorType.VALUE);
        easyFormTextWatcher.setMaxValue(maxValue);
    }

    public void setMinChars(int minChars) {
        this.minChars = minChars;
        setErrorType(ErrorType.CHARS);
        easyFormTextWatcher.setMinChars(minChars);
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
        setErrorType(ErrorType.CHARS);
        easyFormTextWatcher.setMaxChars(maxChars);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setShowErrorOn(ShowErrorOn showErrorOn) {
        this.showErrorOn = showErrorOn;
    }

    void setEasyFormEditTextListener(EasyFormTextWatcher.OnEasyFormTextListener easyFormEditTextListener) {
        easyFormTextWatcher.setEasyFormTextListener(easyFormEditTextListener);
    }

    private void setPropertyFromAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyFormEditText);

        if (typedArray != null) {
            int type = typedArray.getInt(R.styleable.EasyFormEditText_errorType, -1);
            errorType = ErrorType.valueOf(type);
            errorMessage = typedArray.getString(R.styleable.EasyFormEditText_errorMessage);
            regexPattern = typedArray.getString(R.styleable.EasyFormEditText_regexPattern);
            minValue = typedArray.getFloat(R.styleable.EasyFormEditText_minValue, Float.MIN_VALUE);
            maxValue = typedArray.getFloat(R.styleable.EasyFormEditText_maxValue, Float.MAX_VALUE);
            minChars = typedArray.getInt(R.styleable.EasyFormEditText_minChars, INVALID_VALUE);
            maxChars = typedArray.getInt(R.styleable.EasyFormEditText_maxChars, INVALID_VALUE);

            if (errorMessage == null) {
                errorMessage = "Error";
            }

            setUpErrorProperties();

            typedArray.recycle();
        }

        if (errorType != ErrorType.NONE) {
            addTextChangedListener(easyFormTextWatcher);
        }
    }

    private void setUpErrorProperties() {
        if (minValue > Float.MIN_VALUE || maxValue < Float.MAX_VALUE) {
            errorType = ErrorType.VALUE;
            easyFormTextWatcher.setMinValue(minValue);
            easyFormTextWatcher.setMaxValue(maxValue);
        }

        if (minChars != INVALID_VALUE || maxChars != INVALID_VALUE) {
            errorType = ErrorType.CHARS;
            easyFormTextWatcher.setMinChars(Math.max(0, minChars));
            easyFormTextWatcher.setMaxChars(maxChars == INVALID_VALUE ? Integer.MAX_VALUE : maxChars);
        }

        if (regexPattern != null) {
            errorType = ErrorType.PATTERN;
            easyFormTextWatcher.setRegexPattern(regexPattern);
        }

        easyFormTextWatcher.setErrorType(errorType);
    }

    private void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
        easyFormTextWatcher.setErrorType(errorType);
    }
}
