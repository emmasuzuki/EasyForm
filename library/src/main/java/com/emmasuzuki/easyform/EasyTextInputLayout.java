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
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;

public class EasyTextInputLayout extends TextInputLayout {

    private static final String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    private static final int INVALID_VALUE = -1;

    private int editTextInputType;
    private float editTextTextSize;
    private int editTextColor;
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
            setErrorEnabled(true);
        }

        @Override
        protected void clearError() {
            setError(null);
            setErrorEnabled(false);
        }
    };

    public EasyTextInputLayout(Context context) {
        super(context);
    }

    public EasyTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setPropertyFromAttributes(attrs);
        }
    }

    public EasyTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addEasyEditText();
    }

    private void setPropertyFromAttributes(AttributeSet attrs) {
        editTextInputType = attrs.getAttributeIntValue(ANDROID_RES_NAMESPACE, "inputType", -1);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyFormEditText);

        if (typedArray != null) {
            int type = typedArray.getInt(R.styleable.EasyFormEditText_errorType, INVALID_VALUE);
            errorType = ErrorType.valueOf(type);
            errorMessage = typedArray.getString(R.styleable.EasyFormEditText_errorMessage);
            regexPattern = typedArray.getString(R.styleable.EasyFormEditText_regexPattern);
            minValue = typedArray.getInt(R.styleable.EasyFormEditText_minValue, INVALID_VALUE);
            maxValue = typedArray.getInt(R.styleable.EasyFormEditText_maxValue, INVALID_VALUE);
            minChars = typedArray.getInt(R.styleable.EasyFormEditText_minChars, INVALID_VALUE);
            maxChars = typedArray.getInt(R.styleable.EasyFormEditText_maxChars, INVALID_VALUE);

            editTextTextSize = typedArray.getDimensionPixelSize(R.styleable.EasyFormEditText_textSize, 0);
            editTextColor = typedArray.getColor(R.styleable.EasyFormEditText_textColor, 0);

            if (errorMessage == null) {
                errorMessage = "Error";
            }

            setUpErrorProperties();

            typedArray.recycle();
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

    private void addEasyEditText() {
        EditText easyFormEditText = new EditText(getContext());
        easyFormEditText.setSingleLine();

        if (editTextInputType != -1) {
            easyFormEditText.setInputType(editTextInputType);
        }

        if (editTextTextSize > 0) {
            easyFormEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextTextSize);
        }

        if (editTextColor != 0) {
            easyFormEditText.setTextColor(editTextColor);
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        easyFormEditText.setLayoutParams(params);

        if (errorType != ErrorType.NONE) {
            easyFormEditText.addTextChangedListener(easyFormTextWatcher);
        }

        addView(easyFormEditText);
    }
}
