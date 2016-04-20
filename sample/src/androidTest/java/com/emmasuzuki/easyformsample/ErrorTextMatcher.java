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

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;

public final class ErrorTextMatcher {

    public static Matcher<View> hasErrorText(final String expectedErrorText) {
        checkNotNull(expectedErrorText);
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: " + expectedErrorText);
            }

            @Override
            protected boolean matchesSafely(View view) {
                if(view instanceof EditText) {
                    EditText editText = (EditText) view;
                    return expectedErrorText.equals(editText.getError());

                } else if (view instanceof TextInputLayout) {
                    TextInputLayout textInputLayout = (TextInputLayout) view;
                    return expectedErrorText.equals(textInputLayout.getError());
                }

                return false;
            }
        };
    }

    public static Matcher<View> hasNoErrorText() {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with no error");
            }

            @Override
            protected boolean matchesSafely(View view) {
                if(view instanceof EditText) {
                    EditText editText = (EditText) view;
                    return editText.getError() == null;

                } else if (view instanceof TextInputLayout) {
                    TextInputLayout textInputLayout = (TextInputLayout) view;
                    return textInputLayout.getError() == null;
                }

                return false;
            }
        };
    }

    public static Matcher<View> editTextIn(final Matcher<View> parentMatcher) {
        checkNotNull(parentMatcher);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("editText in the parentView");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                if (view instanceof EditText) {
                    ViewGroup group = (ViewGroup) view.getParent();
                    return parentMatcher.matches(view.getParent()) && group.getChildAt(0).equals(view);
                }

                return false;
            }
        };
    }
}
