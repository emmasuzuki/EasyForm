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

import static com.emmasuzuki.easyform.FormValidator.INVALID_VALUE;

public class EasyTextInputLayout extends TextInputLayout implements View.OnFocusChangeListener {

    private static final String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";

    private EditText easyFormEditText;
    private FormValidator validator;
    private EasyFormErrorTextListener easyFormErrorTextListener;

    private int editTextInputType;
    private float editTextTextSize;
    private int editTextColor;
    private String errorMessage;

    private EasyFormErrorTextWatcher textWatcher = new EasyFormErrorTextWatcher(this) {

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            validate();
        }
    }

    void validate() {
        boolean isValid = validator.isValid(easyFormEditText.getText());
        setError(isValid ? null : errorMessage);
        setErrorEnabled(!isValid);

        if (isValid) {
            easyFormErrorTextListener.onFilled(this);
        } else {
            easyFormErrorTextListener.onError(this);
        }
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
        validator.setRegexPattern(regexPattern);
    }

    public void setMinValue(int minValue) {
        validator.setMinValue(minValue);
    }

    public void setMaxValue(int maxValue) {
        validator.setMaxValue(maxValue);
    }

    public void setMinChars(int minChars) {
        validator.setMinChars(minChars);
    }

    public void setMaxChars(int maxChars) {
        validator.setMaxChars(maxChars);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    void setShowErrorOn(ShowErrorOn showErrorOn) {
        if (validator.getErrorType() != ErrorType.NONE) {
            if (showErrorOn == ShowErrorOn.CHANGE) {
                easyFormEditText.addTextChangedListener(textWatcher);
                easyFormEditText.setOnFocusChangeListener(null);
            } else {
                easyFormEditText.removeTextChangedListener(textWatcher);
                easyFormEditText.setOnFocusChangeListener(this);
            }
        }
    }

    void setEasyFormEditTextListener(EasyFormErrorTextListener easyFormEditTextListener) {
        this.easyFormErrorTextListener = easyFormEditTextListener;
        textWatcher.setEasyFormErrorTextListener(easyFormEditTextListener);
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
            ErrorType errorType = ErrorType.valueOf(type);
            errorMessage = typedArray.getString(R.styleable.EasyFormEditText_errorMessage);
            String regexPattern = typedArray.getString(R.styleable.EasyFormEditText_regexPattern);
            float minValue = typedArray.getFloat(R.styleable.EasyFormEditText_minValue, INVALID_VALUE);
            float maxValue = typedArray.getFloat(R.styleable.EasyFormEditText_maxValue, INVALID_VALUE);
            int minChars = typedArray.getInt(R.styleable.EasyFormEditText_minChars, INVALID_VALUE);
            int maxChars = typedArray.getInt(R.styleable.EasyFormEditText_maxChars, INVALID_VALUE);

            editTextTextSize = typedArray.getDimensionPixelSize(R.styleable.EasyFormEditText_textSize, 0);
            editTextColor = typedArray.getColor(R.styleable.EasyFormEditText_textColor, 0);

            if (errorMessage == null) {
                errorMessage = "Error";
            }

            validator = new FormValidator(errorType, regexPattern, minValue, maxValue, minChars, maxChars);
            textWatcher.setValidator(validator);

            typedArray.recycle();
        }
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

        addView(easyFormEditText);
    }
}
