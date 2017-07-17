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
import android.util.AttributeSet;
import android.view.View;

import static com.emmasuzuki.easyform.FormValidator.INVALID_VALUE;

public class EasyFormEditText extends AppCompatEditText implements View.OnFocusChangeListener {

    private FormValidator validator;
    private EasyFormTextListener easyFormTextListener;

    private String errorMessage;

    private EasyFormTextWatcher textWatcher = new EasyFormTextWatcher(this) {

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

    void validate() {
        boolean isValid = validator.isValid(getText().toString());
        setError(isValid ? null : errorMessage);

        if (isValid) {
            easyFormTextListener.onFilled(this);
        } else {
            easyFormTextListener.onError(this);
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
        if (validator.getErrorType() != ErrorType.NONE) {
            if (showErrorOn == ShowErrorOn.CHANGE) {
                addTextChangedListener(textWatcher);
                setOnFocusChangeListener(null);
            } else {
                removeTextChangedListener(textWatcher);
                setOnFocusChangeListener(this);
            }
        }
    }

    void setEasyFormEditTextListener(EasyFormTextListener easyFormEditTextListener) {
        this.easyFormTextListener = easyFormEditTextListener;
        textWatcher.setEasyFormTextListener(easyFormEditTextListener);
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

            if (errorMessage == null) {
                errorMessage = "Error";
            }

            validator = new FormValidator(errorType, regexPattern, minValue, maxValue, minChars, maxChars);

            textWatcher.setValidator(validator);

            typedArray.recycle();
        }
    }
}
