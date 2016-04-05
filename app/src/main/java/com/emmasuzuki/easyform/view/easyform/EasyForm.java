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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.emmasuzuki.easyform.R;

public class EasyForm extends RelativeLayout implements EasyFormTextWatcher.OnEasyFormTextListener {

    private Button submitButton;

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
                    fieldCheckList.put(easyFormEditText.getId(), false);
                }
            }
        }
    }

    private void setPropertyFromAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EasyForm);

        if (typedArray != null) {
            submitButtonId = typedArray.getResourceId(R.styleable.EasyForm_submitButton, -1);

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
