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
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String EDIT_TEXT_FORM_TAG = "edit_text_form";
    private static final String TEXT_LAYOUT_INPUT_FORM_TAG = "text_layout_input_form";
    private static final String LONG_FORM_TAG = "long_form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.form, new EditTextFormFragment(), EDIT_TEXT_FORM_TAG)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        replaceForm(item.getItemId());

        return true;
    }

    private void replaceForm(@IdRes int menuId) {
        Fragment fragment = null;
        String tag = null;

        switch (menuId) {
            case R.id.form_1:
                fragment = new EditTextFormFragment();
                tag = EDIT_TEXT_FORM_TAG;
                break;

            case R.id.form_2:
                fragment = new TextInputLayoutFormFragment();
                tag = TEXT_LAYOUT_INPUT_FORM_TAG;
                break;

            case R.id.form_3:
                fragment = new LongFormFragment();
                tag = LONG_FORM_TAG;
                break;

            default:
                break;
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.form, fragment, tag)
                .commitAllowingStateLoss();
    }
}
