# EasyForm
![Image of Demo1](https://raw.githubusercontent.com/emmasuzuki/EasyForm/master/demo1.gif) 
![Image of Demo2](https://raw.githubusercontent.com/emmasuzuki/EasyForm/master/demo2.gif)

Inspired by AngularJS's `ng-pattern`, `required` etc., EasyForm makes field validation with just an xml and *easy*.
You set error properties and message in xml and EasyForm will show error message when it is needed. Optinally, a button can be disabled when there is an error and enabled when all fields have valid inputs.

### Disclaimer
> This project is still with minimal features. I am very happy to accept any feature requests, bug reports. 
> Please feel free to open Issues and I will try my best to prioritize it. I really like to ask for any additional feature ideas since a form varies for different product domains and I am bad at see all ascpects of those use cases.

#Installation
```
compile 'com.emmasuzuki:easyform:1.0.0'
```

#Example
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.emmasuzuki.easyform.EasyForm xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:submitButton="@+id/submit_button">

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

####EasyForm
EasyForm is extending RelativeLayout, you align any child views with just like you do for RelativeLayout.

**Properties**
A submit button can be disable/enable based on field condition.  
`app:submitButton="@+id/submit_button"` 
