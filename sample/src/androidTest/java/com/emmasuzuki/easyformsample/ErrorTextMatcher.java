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

import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewInteraction;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

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

    public static ViewInteraction editTextIn(final @IdRes int textInputLayoutId) {
        return onView(allOf(isDescendantOfA(withId(textInputLayoutId)), isAssignableFrom(EditText.class)));
    }
}
