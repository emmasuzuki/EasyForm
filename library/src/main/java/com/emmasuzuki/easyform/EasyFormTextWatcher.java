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
import android.text.TextWatcher;
import android.view.View;

abstract class EasyFormTextWatcher implements TextWatcher {

    private View delegateView;
    private FormValidator formValidator;

    private OnEasyFormTextListener easyFormTextListener;

    interface OnEasyFormTextListener {
        void onFilled(View view);

        void onError(View view);
    }

    public EasyFormTextWatcher(View delegateView) {
        this.delegateView = delegateView;
    }

    public void setFormValidator(FormValidator formValidator) {
        this.formValidator = formValidator;
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
        boolean hasError = formValidator.isValid(s);

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
