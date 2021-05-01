package com.imedia.designercompanion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.classes.NetworkAvaillabilityClass;
import com.imedia.designercompanion.classes.ValidationClass;
import com.imedia.designercompanion.network_operations.CustomCallBack;
import com.imedia.designercompanion.network_operations.Rest_DB_Client;
import com.imedia.designercompanion.network_operations.Rest_DB_Interface;
import com.imedia.designercompanion.network_operations.models.RegisterUserModel;
import com.imedia.designercompanion.network_operations.models.RegisterUserModelResponse;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
TextInputEditText business_name_txt, fullname_txt, username_txt, email_txt, password_txt;
Button sign_up_BTN, link_to_login;
Switch premium_or_standard_switch;
String user_type = "standard";
private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        initViews();
        setActionOnItems();
    }

    private void setActionOnItems() {

        business_name_txt.addTextChangedListener(new ValidationClass(business_name_txt,""));
        fullname_txt.addTextChangedListener(new ValidationClass(fullname_txt,""));
        username_txt.addTextChangedListener(new ValidationClass(username_txt,""));
        password_txt.addTextChangedListener(new ValidationClass(password_txt,"password"));
        email_txt.addTextChangedListener(new ValidationClass(email_txt,"email"));

        premium_or_standard_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    new AndExAlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Account Type")
                            .setMessage("Benefits of a premium User:\n1. You get to use your logo. \n2. You can customize your App Look. \n3. You will be required to make payment for it")
                            .setPositiveBtnText("OK")
                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {
                                    user_type = "premium";
                                    Toast.makeText(RegisterActivity.this, "User type is Premium", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeBtnText("Cancel")
                            .OnNegativeClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {
                                    Toast.makeText(RegisterActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                                    premium_or_standard_switch.setChecked(false);
                                    user_type = "standard";
                                }
                            })
                            .build();
                }else {
                    user_type = "standard";
                }

            }
        });
        sign_up_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(new ValidationClass(fullname_txt,"").validateInput())){
                    return;
                }

                if (!(new ValidationClass(business_name_txt,"").validateInput())){
                    return;
                }

                if (!(new ValidationClass(username_txt,"").validateInput())){
                    return;
                }

                if (!(new ValidationClass(email_txt,"email").validateEmail())){
                    return;
                }

                if (!(new ValidationClass(fullname_txt,"password").validatePassword())){
                    return;
                }

                String business_name = business_name_txt.getText().toString();
                String fullname = fullname_txt.getText().toString();
                String username = username_txt.getText().toString();
                String password = password_txt.getText().toString();
                String email = email_txt.getText().toString();

                RegisterUserModel model = new RegisterUserModel(business_name,fullname,username,email,password, user_type);
                NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(RegisterActivity.this);

                if (networkAvaillabilityClass.hasNetwork()){
                    registerUser(model);
                }else{
                    showErrorMessage("you may not be connected to the internet");
                }
            }
        });

        link_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,SignInOptionsActivity.class));
                finish();
            }
        });
    }

    private void showErrorMessage(String s) {
        new AndExAlertDialog.Builder(RegisterActivity.this)
                .setTitle("Oops!!!")
                .setMessage("Sorry, something is not right \n\"" + s + "\"")
                .setPositiveBtnText("Ok")
                .OnPositiveClicked(new AndExAlertDialogListener() {
                    @Override
                    public void OnClick(String input) {
                        Toast.makeText(RegisterActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    private void showSuccessMessage(String message) {
        new AndExAlertDialog.Builder(RegisterActivity.this)
                .setTitle("Yippee!!!")
                .setMessage("We succeeded, \n\"" + message + "\"")
                .setPositiveBtnText("OK")
                .OnPositiveClicked(new AndExAlertDialogListener() {
                    @Override
                    public void OnClick(String input) {
                        Toast.makeText(RegisterActivity.this,"you may login now",Toast.LENGTH_SHORT).show();

                        moveOnToLogin();
                    }
                })
                .build();
    }

    private void moveOnToLogin() {
        startActivity(new Intent(this,SignInOptionsActivity.class));
        finish();
    }

    private void registerUser(RegisterUserModel model) {
        Rest_DB_Interface rest_db_interface = Rest_DB_Client.getClient().create(Rest_DB_Interface.class);
        Call<RegisterUserModelResponse> call = rest_db_interface.registerUser(model);

        call.enqueue(new CustomCallBack<>(RegisterActivity.this, new Callback<RegisterUserModelResponse>() {
            @Override
            public void onResponse(Call<RegisterUserModelResponse> call, Response<RegisterUserModelResponse> response) {
                if (response.code() == 201) {

                   // RegisterUserModelResponse registerUserModelResponse = response.body();
                    showSuccessMessage("User has been " + response.message().toString());

                }else {

                    Log.d(TAG, "onResponse: " + response.body());
                    Log.d(TAG, "onResponse: " + response.raw().toString());
                    showErrorMessage("Your email or Business name could be in use already");
                }
            }

            @Override
            public void onFailure(Call<RegisterUserModelResponse> call, Throwable t) {
                showErrorMessage(t.getMessage());
            }
        }));
    }

    private void initViews() {
        business_name_txt = findViewById(R.id.business_name_txt);
        fullname_txt = findViewById(R.id.fullname_txt);
        username_txt = findViewById(R.id.username_txt);
        email_txt = findViewById(R.id.email_txt);
        password_txt = findViewById(R.id.password_txt);
        premium_or_standard_switch = findViewById(R.id.premium_or_standard_switch);

        sign_up_BTN = findViewById(R.id.sign_up_BTN);
        link_to_login = findViewById(R.id.link_to_login);
    }
}