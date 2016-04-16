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

package com.emmasuzuki.easyform.view.easyform;

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
