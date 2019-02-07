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
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.emmasuzuki.easyform.FormValidator.INVALID_VALUE;

public class EasyFormEditText extends AppCompatEditText implements View.OnFocusChangeListener, EasyFormTextListener {

    private FormValidator validator;
    private EasyFormErrorTextListener easyFormErrorTextListener;

    private String errorMessage;

    private EasyFormErrorTextWatcher errorTextWatcher = new EasyFormErrorTextWatcher(this) {

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            validate();
        }
    }

    @Override
    public void formatted(String formattedString, int cursorPos) {
        setText(formattedString);
        if (cursorPos <= formattedString.length()) {
            setSelection(cursorPos);
        }
    }

    void validate() {
        boolean isValid = validator.isValid(getText().toString());
        setError(isValid ? null : errorMessage);

        if (isValid) {
            easyFormErrorTextListener.onFilled(this);
        } else {
            easyFormErrorTextListener.onError(this);
        }
    }

    public ErrorType getErrorType() {
        return validator.getErrorType();
    }

    public void setErrorType(ErrorType errorType) {
        validator.setErrorType(errorType);
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
        if (showErrorOn == ShowErrorOn.CHANGE) {
            addTextChangedListener(errorTextWatcher);
            setOnFocusChangeListener(null);
        } else {
            removeTextChangedListener(errorTextWatcher);
            setOnFocusChangeListener(this);
        }
    }

    void setEasyFormEditTextListener(EasyFormErrorTextListener easyFormEditTextListener) {
        this.easyFormErrorTextListener = easyFormEditTextListener;
        errorTextWatcher.setEasyFormErrorTextListener(easyFormEditTextListener);
    }

    private void setPropertyFromAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyFormEditText);

        if (typedArray != null) {
            int type = typedArray.getInt(R.styleable.EasyFormEditText_errorType, -1);
            ErrorType errorType = ErrorType.valueOf(type);
            errorMessage = typedArray.getString(R.styleable.EasyFormEditText_errorMessage);
            String regexPattern = typedArray.getString(R.styleable.EasyFormEditText_regexPattern);
            float minValue = typedArray.getFloat(R.styleable.EasyFormEditText_minValue, INVALID_VALUE);
            float maxValue = typedArray.getFloat(R.styleable.EasyFormEditText_maxValue, INVALID_VALUE);
            int minChars = typedArray.getInt(R.styleable.EasyFormEditText_minChars, INVALID_VALUE);
            int maxChars = typedArray.getInt(R.styleable.EasyFormEditText_maxChars, INVALID_VALUE);
            int formType = typedArray.getInt(R.styleable.EasyFormEditText_type, -1);
            EasyFormType easyFormType = EasyFormType.valueOf(formType);
            if (easyFormType == EasyFormType.CREDIT_CARD) {
                EasyFormTextWatcher watcher = new EasyFormTextWatcher(easyFormType);
                watcher.setListener(this);
                addTextChangedListener(watcher);
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
                setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }

            if (errorMessage == null) {
                errorMessage = "Error";
            }

            validator = new FormValidator(errorType, regexPattern, minValue, maxValue, minChars, maxChars);

            errorTextWatcher.setValidator(validator);

            typedArray.recycle();
        }
    }
}
