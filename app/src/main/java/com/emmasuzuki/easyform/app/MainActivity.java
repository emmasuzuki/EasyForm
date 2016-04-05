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

package com.emmasuzuki.easyform.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.emmasuzuki.easyform.R;

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
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.form, fragment, tag)
                .commitAllowingStateLoss();
    }
}
