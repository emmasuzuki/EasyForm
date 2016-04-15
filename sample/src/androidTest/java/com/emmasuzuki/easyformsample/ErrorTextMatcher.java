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
