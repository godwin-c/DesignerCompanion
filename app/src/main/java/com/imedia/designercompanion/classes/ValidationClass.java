package com.imedia.designercompanion.classes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class ValidationClass implements TextWatcher {

    private View view;
    private String viewName;

    public ValidationClass(View view, String viewName) {
        this.view = view;
        this.viewName = viewName;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        switch (viewName){
            case "email":
                validateEmail();
                break;
            case "password":
                validatePassword();
                break;
            default:
                validateInput();
        }
    }

    private boolean validatePassword() {
        if (((TextInputEditText)view).getText().toString().trim().isEmpty()) {
            ((TextInputEditText)view).setError("Password is required");
            view.requestFocus();
            return false;
        }else if(((TextInputEditText)view).getText().toString().length() < 6){
            ((TextInputEditText)view).setError("Password can't be less than 6 digit");
            view.requestFocus();
            return false;
        }
//        else {
//            ((TextInputEditText)view).setErrorEnabled(false);
//        }
        return true;
    }

    private boolean validateEmail() {
        if (((TextInputEditText)view).getText().toString().trim().isEmpty()) {
            ((TextInputEditText)view).setError("Email is required");
            view.requestFocus();
            return false;
        } else {
            String emailId = ((TextInputEditText)view).getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                ((TextInputEditText)view).setError("Invalid Email address, ex: abc@example.com");
                view.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean validateInput() {
        if (((TextInputEditText)view).getText().toString().trim().isEmpty()) {
            ((TextInputEditText)view).setError("Field is required");
            view.requestFocus();
            return false;
        }
        return true;
    }


}
