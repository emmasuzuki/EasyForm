/*
 * Copyright (c) 2019. Emma Suzuki <emma11suzuki@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import android.util.Log;

public class EasyFormTextWatcher implements TextWatcher {

    private EasyFormType formType;
    private EasyFormTextListener listener;
    private boolean lock;
    private boolean deleted = false;
    private int cursorPos;

    public EasyFormTextWatcher(EasyFormType formType) {
        this.formType = formType;
    }

    void setListener(EasyFormTextListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        cursorPos = start + count;
        deleted = count == 0 && start > 0;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (lock || s.length() > 19) {
            return;
        }
        lock = true;

        switch (formType) {
            case CREDIT_CARD:
                formatCreditCard(s);
        }

        lock = false;
    }

    private void formatCreditCard(Editable original) {
        String raw = original.toString().replaceAll(" ", "");
        Editable formatted = new Editable.Factory().newEditable(raw);

        for (int i = 4; i < formatted.length(); i += 5) {
            if (formatted.toString().charAt(i) != ' ') {
                formatted.insert(i, " ");
            }
        }

        int curAdjust = 0;
        if (deleted) {
            curAdjust = (cursorPos > 4 && raw.length() % 4 == 0) ? curAdjust - 1 : 0;
        } else if (raw.length() > 1 && (raw.length() - 1) % 4 == 0) {
            curAdjust++;
        }

        listener.formatted(formatted.toString(), cursorPos + curAdjust);
    }
}
