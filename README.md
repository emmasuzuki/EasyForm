# EasyForm
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EasyForm-green.svg?style=true)](https://android-arsenal.com/details/1/3474)

![Image of Demo1](https://raw.githubusercontent.com/emmasuzuki/EasyForm/master/demo1.gif) 
![Image of Demo2](https://raw.githubusercontent.com/emmasuzuki/EasyForm/master/demo2.gif)

Inspired by AngularJS's `ng-pattern`, `required` etc., EasyForm makes field validation with just an xml and *easy*.
You set error properties and message in xml and EasyForm will show error message when it is needed. Optinally, a button can be disabled when there is an error and enabled when all fields have valid inputs.

### Disclaimer
This project is still with minimal features. I am very happy to accept any feature requests, bug reports. Please feel free to open Issues and I will try my best to prioritize it. I really like to ask for any additional feature ideas since a form varies for different product domains and I am bad at see all aspects of those use cases.

## Installation
```
compile 'com.emmasuzuki:easyform:1.1.0'
```

## Example
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.emmasuzuki.easyform.EasyForm xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:submitButton="@+id/submit_button"
    app:showErrorOn="unfocus">

    <com.emmasuzuki.easyform.EasyTextInputLayout
        android:id="@+id/empty_check_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:errorMessage="@string/error_message_empty"
        app:errorType="empty" />

    <com.emmasuzuki.easyform.EasyTextInputLayout
        android:id="@+id/digit_check_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/empty_check_input"
        android:layout_marginTop="4dp"
        app:errorMessage="@string/error_message_digit"
        app:regexPattern="[0-9]+" />

    <Button
        android:id="@id/submit_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/digit_check_input"
        android:background="@color/colorAccent"
        android:text="@string/submit"
        android:textColor="@android:color/white" />

</com.emmasuzuki.easyform.EasyForm>
```

## Custom Views
### EasyForm
EasyFrom is a ViewGroup which you wrap all form components inside. EasyForm is extending RelativeLayout, so you align any child views with just like you do for RelativeLayout.

#### Properties
submitButton: A submit button can be disable/enable based on field condition by setting view ID to submitButton property.  
```
app:submitButton="@+id/submit_button"
```
showErrorOn: unfocus/change (default: change). A field is validated and error will be displayed on unfocus of the field or on every keystroke. 
```
app:showErrorOn="unfocus"
```
NOTE: If showErrorOn is set to "unfocus", submitButton will be enabled when user correctly filled all fields except the last one, so that make sure to call `easyForm.validate();` on submitButton click in your application.
Check out [LongFormFragment](!https://github.com/emmasuzuki/EasyForm/blob/master/sample/src/main/java/com/emmasuzuki/easyformsample/LongFormFragment.java)
 for the usage.

#### APIs
`void validate()`: Validate all fields if the fields meet a criteria or not.

`boolean isValid()`: Return true if all fields have valid values.

### EasyFormEditText
EasyFormEditText is a extension of EditText that will apply an error message by built-in `setError()` based on input and validation criteria.

#### Properties
errorMessage: An error message to show when a validation criteria does not match.
```
app:errorMessage="This field is required"
```

errorType: Set empty to make a field required.
```
app:errorType="empty"
```

regexPattern: Set a regex to match against an input. If an input does not match with the regex, an error message will be displayed.

ex) Allow only digits.
```
app:regexPattern="[0-9]+"
```

minValue: Set a minimum value (inclusive).

ex) Allow value >= 100
```
app:minValue="100"
```

maxValue: Set a maximum value (inclusive). You can conbine with minValue.

ex) Allow -100 <= value <= 100
```
app:minValue="-100"
app:maxValue="100"
```

minChars: Set a minimum char length (inclusive).

ex) Allow input with minimum length of 5.
```
app:minChars="5"
```

maxChars: Set a maximum char length (inclusive). You can conbine with minChars.

ex) Allow input with a length between 5 to 10.
```
app:minChars="5"
app:maxChars="10"
```

You can also set these properties in java.

### EasyTextInputLayout
EasyTextInputLayout extends TextInputLayout. Similar to EasyFormEditText, this applies an error message by built-in `setError()`. EasyTextInputLayout adds EditText automatically, so unlike you do with TextInputLayout, you do not have to add EditText as a child. 

All properties from EasyFormEditText can be applied for EasyTextInputLayout and additionally there are a few more that you can set.

#### Properties
android:inputType: EasyTextInputLayout bypass android's inputType to EditText. 
```
android:inputType="number"
```

textSize: Set a textSize for EditText.
```
app:textSize="12sp"
```

textColor: Set a textColor for EditText.
```
app:textColor="@color/blue"
```

More example: <a href="https://github.com/emmasuzuki/EasyForm/tree/master/sample">Sample</a>

## Feature Request, Bug Report
I am very happy to hear all of these. To keep this project going, please file an issue for any requests. <a href="https://github.com/emmasuzuki/EasyForm/issues">File Issue</a>

## License
Copyright 2016 Emma Suzuki <emma11suzuki@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
