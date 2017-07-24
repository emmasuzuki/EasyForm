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

package com.emmasuzuki.easyformsample;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emmasuzuki.easyform.EasyForm;
import com.emmasuzuki.easyform.EasyFormEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTextFormFragment extends Fragment {

    @Bind(R.id.email_check_edittext)
    EasyFormEditText emailEditText;

    @Bind(R.id.form)
    EasyForm easyForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_text_form, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        emailEditText.setRegexPattern(Patterns.EMAIL_ADDRESS.pattern());
    }

    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        // Make sure to call easyForm.validate() when using showErrorOn = UNFOCUS
        easyForm.validate();

        if (easyForm.isValid()) {
            Log.e(getClass().getSimpleName(), "All values are valid. Ready to submit.");
        } else {
            Log.e(getClass().getSimpleName(), "The last input was invalid");
        }
    }
}
