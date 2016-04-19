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

package com.emmasuzuki.easyform;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

public class EasyFormEditText extends EditText {

    private static final int INVALID_VALUE = -1;

    private ErrorType errorType;
    private String regexPattern = "";
    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;
    private int minChars = 0;
    private int maxChars = Integer.MAX_VALUE;
    private String errorMessage;

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
        easyFormTextWatcher.setRegexPattern(regexPattern);
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        easyFormTextWatcher.setMinValue(minValue);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        easyFormTextWatcher.setMaxValue(maxValue);
    }

    public void setMinChars(int minChars) {
        this.minChars = minChars;
        easyFormTextWatcher.setMinChars(minChars);
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
        easyFormTextWatcher.setMaxChars(maxChars);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
            minValue = typedArray.getInt(R.styleable.EasyFormEditText_minValue, INVALID_VALUE);
            maxValue = typedArray.getInt(R.styleable.EasyFormEditText_maxValue, INVALID_VALUE);
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
        if (minValue != INVALID_VALUE || maxValue != INVALID_VALUE) {
            errorType = ErrorType.VALUE;
            easyFormTextWatcher.setMinValue(Math.max(0, minValue));
            easyFormTextWatcher.setMaxValue(maxValue == INVALID_VALUE ? Integer.MAX_VALUE : maxValue);
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
}
