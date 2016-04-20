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

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import java.util.regex.Pattern;

public abstract class EasyFormTextWatcher implements TextWatcher {

    private View delegateView;

    private ErrorType errorType;
    private String regexPattern;
    private int minValue;
    private int maxValue;
    private int minChars;
    private int maxChars;
    private OnEasyFormTextListener easyFormTextListener;

    interface OnEasyFormTextListener {
        void onFilled(View view);

        void onError(View view);
    }

    public EasyFormTextWatcher(View delegateView) {
        this.delegateView = delegateView;
    }

    void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    void setMinChars(int minChars) {
        this.minChars = minChars;
    }

    void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }

    void setEasyFormTextListener(OnEasyFormTextListener easyFormTextListener) {
        this.easyFormTextListener = easyFormTextListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
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
                    int value = Integer.parseInt(s.toString());
                    hasError = value < minValue || value > maxValue;
                } catch (Exception e) {
                    hasError = true;
                }
                break;

            case CHARS:
                hasError = s.length() < minChars || s.length() > maxChars;
                break;
        }

        if (hasError) {
            renderError();

            if (easyFormTextListener != null) {
                easyFormTextListener.onError(delegateView);
            }
        } else {
            clearError();

            if (easyFormTextListener != null) {
                easyFormTextListener.onFilled(delegateView);
            }
        }
    }

    protected abstract void renderError();

    protected abstract void clearError();
}
