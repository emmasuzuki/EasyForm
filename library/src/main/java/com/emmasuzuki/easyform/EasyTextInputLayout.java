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
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EasyTextInputLayout extends TextInputLayout implements View.OnFocusChangeListener {

    private static final String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    private static final int INVALID_VALUE = -1;

    private EditText easyFormEditText;
    private FormValidator validator;
    private EasyFormTextListener easyFormTextListener;

    private int editTextInputType;
    private float editTextTextSize;
    private int editTextColor;
    private ErrorType errorType;
    private String regexPattern = "";
    private float minValue = Float.MIN_VALUE;
    private float maxValue = Float.MAX_VALUE;
    private int minChars = 0;
    private int maxChars = Integer.MAX_VALUE;
    private String errorMessage;

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @NonNull
    @Override
    public EditText getEditText() {
        return easyFormEditText;
    }

    public ErrorType getErrorType() {
        return validator.getErrorType();
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
//        easyFormTextWatcher.setRegexPattern(regexPattern);
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
//        easyFormTextWatcher.setMinValue(minValue);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
//        easyFormTextWatcher.setMaxValue(maxValue);
    }

    public void setMinChars(int minChars) {
        this.minChars = minChars;
//        easyFormTextWatcher.setMinChars(minChars);
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
//        easyFormTextWatcher.setMaxChars(maxChars);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    void setEasyFormEditTextListener(EasyFormTextListener easyFormEditTextListener) {
//        easyFormTextWatcher.setEasyFormTextListener(easyFormEditTextListener);
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
            minValue = typedArray.getFloat(R.styleable.EasyFormEditText_minValue, Float.MIN_VALUE);
            maxValue = typedArray.getFloat(R.styleable.EasyFormEditText_maxValue, Float.MAX_VALUE);
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
//        if (minValue > Float.MIN_VALUE || maxValue < Float.MAX_VALUE) {
//            errorType = ErrorType.VALUE;
//            easyFormTextWatcher.setMinValue(minValue);
//            easyFormTextWatcher.setMaxValue(maxValue);
//        }
//
//        if (minChars != INVALID_VALUE || maxChars != INVALID_VALUE) {
//            errorType = ErrorType.CHARS;
//            easyFormTextWatcher.setMinChars(Math.max(0, minChars));
//            easyFormTextWatcher.setMaxChars(maxChars == INVALID_VALUE ? Integer.MAX_VALUE : maxChars);
//        }
//
//        if (regexPattern != null) {
//            errorType = ErrorType.PATTERN;
//            easyFormTextWatcher.setRegexPattern(regexPattern);
//        }
//
//        easyFormTextWatcher.setErrorType(errorType);
    }

    private void addEasyEditText() {
        easyFormEditText = new EditText(getContext());
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
//            easyFormEditText.addTextChangedListener(this);
        }

        addView(easyFormEditText);
    }
}
