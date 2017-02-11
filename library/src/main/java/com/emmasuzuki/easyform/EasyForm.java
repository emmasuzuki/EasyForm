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
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class EasyForm extends RelativeLayout implements EasyFormTextListener {

    private Button submitButton;
    private ShowErrorOn showErrorOn;

    private SparseBooleanArray fieldCheckList;
    private int submitButtonId;

    public EasyForm(Context context) {
        super(context);
    }

    public EasyForm(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPropertyFromAttributes(attrs);
    }

    public EasyForm(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setPropertyFromAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        submitButton = (Button) findViewById(submitButtonId);

        initializeFieldCheckList(this);

        enableSubmitButton(isFieldCheckListAllTrue());
    }

    @Override
    public void onFilled(View view) {
        fieldCheckList.put(view.getId(), true);

        if (isFieldCheckListAllTrue()) {
            enableSubmitButton(true);
        }
    }

    @Override
    public void onError(View view) {
        fieldCheckList.put(view.getId(), false);

        enableSubmitButton(false);
    }

    private void initializeFieldCheckList(ViewGroup viewGroup) {
        fieldCheckList = new SparseBooleanArray(getChildCount());
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof EasyTextInputLayout) {
                EasyTextInputLayout easyTextInputLayout = (EasyTextInputLayout) view;
                if (easyTextInputLayout.getErrorType() != ErrorType.NONE) {
                    easyTextInputLayout.setEasyFormEditTextListener(this);
                    fieldCheckList.put(easyTextInputLayout.getId(), false);
                }

            } else if (view instanceof ViewGroup) {
                initializeFieldCheckList((ViewGroup) view);

            } else if (view instanceof EasyFormEditText) {
                EasyFormEditText easyFormEditText = (EasyFormEditText) view;
                if (easyFormEditText.getErrorType() != ErrorType.NONE) {
                    easyFormEditText.setEasyFormEditTextListener(this);
                    easyFormEditText.setShowErrorOn(showErrorOn);
                    fieldCheckList.put(easyFormEditText.getId(), false);
                }
            }
        }
    }

    private void setPropertyFromAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyForm);

        if (typedArray != null) {
            submitButtonId = typedArray.getResourceId(R.styleable.EasyForm_submitButton, -1);
            int type = typedArray.getInt(R.styleable.EasyForm_showErrorOn, -1);
            showErrorOn = ShowErrorOn.valueOf(type);

            typedArray.recycle();
        }
    }

    private boolean isFieldCheckListAllTrue() {
        for (int i = 0; i < fieldCheckList.size(); i++) {
            if (!fieldCheckList.get(fieldCheckList.keyAt(i), true)) {
                return false;
            }
        }

        return true;
    }

    private void enableSubmitButton(boolean enable) {
        if (submitButton != null) {
            if (enable) {
                submitButton.setEnabled(true);
                submitButton.setAlpha(1f);
            } else {
                submitButton.setEnabled(false);
                submitButton.setAlpha(0.5f);
            }
        }
    }
}
