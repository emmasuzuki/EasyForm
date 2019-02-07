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
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class EasyForm extends RelativeLayout implements EasyFormErrorTextListener {

    private Button submitButton;
    private ShowErrorOn showErrorOn = ShowErrorOn.CHANGE;

    private SparseArray<FormInputs> fieldCheckList;
    private int submitButtonId;

    private static class FormInputs {

        private View view;
        boolean isValid;

        FormInputs(View view, boolean isValid) {
            this.view = view;
            this.isValid = isValid;
        }

        public View getView() {
            return view;
        }
    }

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

        fieldCheckList = new SparseArray<>(getChildCount());
        initializeFieldCheckList(this);

        enableSubmitButton((fieldCheckList.size() < 2 && showErrorOn == ShowErrorOn.UNFOCUS) || isValid());
    }

    @Override
    public void onFilled(View view) {
        fieldCheckList.get(view.getId()).isValid = true;

        if (showErrorOn == ShowErrorOn.CHANGE) {
            if (isValid()) {
                enableSubmitButton(true);
            }
        } else {
            if (isLastFieldToFill()) {
                enableSubmitButton(true);
            }
        }
    }

    @Override
    public void onError(View view) {
        fieldCheckList.get(view.getId()).isValid = false;

        if (showErrorOn == ShowErrorOn.CHANGE || !isLastFieldToFill()) {
            enableSubmitButton(false);
        }
    }


    // For unfocus case, validate on button click because button will be enabled
    // before the last field becomes valid.
    public void validate() {
        for (int i = 0; i < fieldCheckList.size(); i++) {
            FormInputs formInputs = fieldCheckList.get(fieldCheckList.keyAt(i));
            View view = formInputs.getView();
            if (view instanceof EasyFormEditText) {
                EasyFormEditText editText = (EasyFormEditText) view;
                editText.validate();
            } else if (view instanceof EasyAutoCompleteTextView) {
                EasyAutoCompleteTextView autoCompleteTextView = (EasyAutoCompleteTextView) view;
                autoCompleteTextView.validate();
            } else {
                EasyTextInputLayout textInputLayout = (EasyTextInputLayout) view;
                textInputLayout.validate();
            }
        }
    }

    public boolean isValid() {
        for (int i = 0; i < fieldCheckList.size(); i++) {
            FormInputs formInputs = fieldCheckList.get(fieldCheckList.keyAt(i));
            if (!formInputs.isValid) {
                return false;
            }
        }

        return true;
    }

    private void initializeFieldCheckList(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof EasyTextInputLayout) {
                EasyTextInputLayout easyTextInputLayout = (EasyTextInputLayout) view;
                if (easyTextInputLayout.getErrorType() != ErrorType.NONE) {
                    easyTextInputLayout.setEasyFormEditTextListener(this);
                    easyTextInputLayout.setShowErrorOn(showErrorOn);
                    fieldCheckList.put(easyTextInputLayout.getId(), new FormInputs(easyTextInputLayout, false));
                }

            } else if (view instanceof ViewGroup) {
                initializeFieldCheckList((ViewGroup) view);

            } else if (view instanceof EasyFormEditText) {
                EasyFormEditText easyFormEditText = (EasyFormEditText) view;
                if (easyFormEditText.getErrorType() != ErrorType.NONE) {
                    easyFormEditText.setEasyFormEditTextListener(this);
                    easyFormEditText.setShowErrorOn(showErrorOn);
                    fieldCheckList.put(easyFormEditText.getId(), new FormInputs(easyFormEditText, false));
                }
            } else if (view instanceof EasyAutoCompleteTextView) {
                EasyAutoCompleteTextView easyAutoCompleteTextView = (EasyAutoCompleteTextView) view;
                if (easyAutoCompleteTextView.getErrorType() != ErrorType.NONE) {
                    easyAutoCompleteTextView.setEasyFormEditTextListener(this);
                    easyAutoCompleteTextView.setShowErrorOn(showErrorOn);
                    fieldCheckList.put(easyAutoCompleteTextView.getId(), new FormInputs(easyAutoCompleteTextView, false));
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

    private boolean isLastFieldToFill() {
        int filled = 0;

        for (int i = 0; i < fieldCheckList.size(); i++) {
            FormInputs formInputs = fieldCheckList.get(fieldCheckList.keyAt(i));
            if (formInputs.isValid) {
                filled++;
            }
        }

        return filled >= fieldCheckList.size() - 1;
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
