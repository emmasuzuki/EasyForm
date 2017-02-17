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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class EasyFormEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private static final int INVALID_VALUE = -1;

    private FormValidator validator;
    private EasyFormTextListener easyFormTextListener;

    private String regexPattern = "";
    private float minValue = Float.MIN_VALUE;
    private float maxValue = Float.MAX_VALUE;
    private int minChars = 0;
    private int maxChars = Integer.MAX_VALUE;
    private String errorMessage;
    private ShowErrorOn showErrorOn;

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean hasError = validator.isValid(s);

        if (hasError) {
            setError(errorMessage);

            if (easyFormTextListener != null) {
                easyFormTextListener.onError(this);
            }
        } else {
            setError(null);

            if (easyFormTextListener != null) {
                easyFormTextListener.onFilled(this);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            EditText editText = (EditText) v;
            setError(validator.isValid(editText.getText()) ? null : errorMessage);
        }
    }

    public ErrorType getErrorType() {
        return validator.getErrorType();
    }

    public void setErrorType(ErrorType errorType) {
        validator.setErrorType(errorType);
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
        validator.setRegexPattern(regexPattern);
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        validator.setMinValue(minValue);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        validator.setMaxValue(maxValue);
    }

    public void setMinChars(int minChars) {
        this.minChars = minChars;
        validator.setMinChars(minChars);
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
        validator.setMaxChars(maxChars);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setShowErrorOn(ShowErrorOn showErrorOn) {
        this.showErrorOn = showErrorOn;
    }

    void setEasyFormEditTextListener(EasyFormTextListener easyFormEditTextListener) {
        this.easyFormTextListener = easyFormEditTextListener;
    }

    private void setPropertyFromAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyFormEditText);

        ErrorType errorType = ErrorType.NONE;
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

            setUpErrorProperties(errorType);

            typedArray.recycle();
        }

        if (errorType != ErrorType.NONE) {
            if (showErrorOn == ShowErrorOn.CHANGE) {
                addTextChangedListener(this);
            } else {
                setOnFocusChangeListener(this);
            }
        }
    }

    private void setUpErrorProperties(ErrorType errorType) {
        validator = new FormValidator();

        if (errorType == ErrorType.EMPTY) {
            validator.setErrorType(errorType);
        }

        if (minValue > Float.MIN_VALUE || maxValue < Float.MAX_VALUE) {
            validator.setMinValue(minValue);
            validator.setMaxValue(maxValue);
        }

        if (minChars != INVALID_VALUE || maxChars != INVALID_VALUE) {
            validator.setMinChars(Math.max(0, minChars));
            validator.setMaxChars(maxChars == INVALID_VALUE ? Integer.MAX_VALUE : maxChars);
        }

        if (regexPattern != null) {
            validator.setRegexPattern(regexPattern);
        }
    }
}
