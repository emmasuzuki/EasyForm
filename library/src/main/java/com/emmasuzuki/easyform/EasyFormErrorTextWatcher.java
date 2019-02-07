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

abstract class EasyFormErrorTextWatcher implements TextWatcher {

    private View delegateView;
    private FormValidator validator;
    private EasyFormErrorTextListener easyFormErrorTextListener;

    EasyFormErrorTextWatcher(View delegateView) {
        this.delegateView = delegateView;
    }

    void setEasyFormErrorTextListener(EasyFormErrorTextListener easyFormErrorTextListener) {
        this.easyFormErrorTextListener = easyFormErrorTextListener;
    }

    void setValidator(FormValidator validator) {
        this.validator = validator;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean isValid = validator.isValid(s);

        if (isValid) {
            clearError();

            if (easyFormErrorTextListener != null) {
                easyFormErrorTextListener.onFilled(delegateView);
            }
        } else {
            renderError();

            if (easyFormErrorTextListener != null) {
                easyFormErrorTextListener.onError(delegateView);
            }
        }
    }

    protected abstract void renderError();

    protected abstract void clearError();
}
