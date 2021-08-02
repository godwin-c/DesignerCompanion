package com.imedia.designercompanion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.classes.AppUser;
import com.imedia.designercompanion.classes.NetworkAvaillabilityClass;
import com.imedia.designercompanion.classes.ValidationClass;
import com.imedia.designercompanion.databases.LocalDatabase;
import com.imedia.designercompanion.network_operations.CustomCallBack;
import com.imedia.designercompanion.network_operations.Rest_DB_Client;
import com.imedia.designercompanion.network_operations.Rest_DB_Interface;
import com.imedia.designercompanion.network_operations.models.LoginUserModel;
import com.imedia.designercompanion.network_operations.models.LoginUserResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInOptionsActivity extends AppCompatActivity {

    Button link_to_sign_up, login_button, forgot_password_btn;

    TextView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    TextInputEditText password_login,username_login;

    private static final String TAG = SignInOptionsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_options);

        link_to_sign_up = findViewById(R.id.link_to_sign_up);

        image = findViewById(R.id.logo_text);
        password_login = findViewById(R.id.password_login);
        username_login = findViewById(R.id.username_login);
        logoText = findViewById(R.id.welcome_message);
        sloganText = findViewById(R.id.sign_in_text);
        username = findViewById(R.id.sign_in_username);
        password = findViewById(R.id.sign_in_password);
        login_button = findViewById(R.id.login_button);
        forgot_password_btn = findViewById(R.id.forgot_password_btn);

        setActionsOnViewItems();
    }

    private void setActionsOnViewItems() {
        link_to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInOptionsActivity.this, RegisterActivity.class);
                startActivity(intent);

                //create pairs for animation
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image"); //1st one is the element and 2nd is the transition name of animation.
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_button, "button_tran");
                pairs[6] = new Pair<View, String>(link_to_sign_up, "login_signup_tran");

                //Call next activity by attaching the animation with it.
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInOptionsActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(new ValidationClass(username_login,"").validateInput())){
                    return;
                }

                if (!(new ValidationClass(password_login,"password").validatePassword())){
                    return;
                }

                String username_txt = username_login.getText().toString();
                String password_txt = password_login.getText().toString();

               // {"$or": [{"name": "Jane"}, {"name": "Donald"}]}
                JSONObject orSection = new JSONObject();
                JSONObject emailSection = new JSONObject();
                JSONObject usernameSection = new JSONObject();
                JSONArray comparison = new JSONArray();
                try {
                    emailSection.accumulate("email",username_txt);
                    usernameSection.accumulate("username",username_txt);
                    comparison.put(emailSection);
                    comparison.put(usernameSection);
                    orSection.accumulate("$or",comparison);

                    Log.d(TAG, "onClick: " + orSection.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //{"$and": [{"name": "Jane"}, {"last-name": "Cassidy"}]}
                JSONObject passwordSection = new JSONObject();
                JSONArray joinTwoSection = new JSONArray();
                JSONObject andSection = new JSONObject();

                try {
                    passwordSection.accumulate("password",password_txt);
                    joinTwoSection.put(orSection);
                    joinTwoSection.put(passwordSection);
                    andSection.accumulate("$and",joinTwoSection);
                    Log.d(TAG, "onClick: " + andSection.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NetworkAvaillabilityClass networkAvaillabilityClass = new NetworkAvaillabilityClass(SignInOptionsActivity.this);
                if (networkAvaillabilityClass.hasNetwork()){
                    loginUser(andSection);
                }else{
                    showErrorMessage("you may not be connected to the internet");
                }


            }
        });
    }

    private void showErrorMessage(String s) {
        new AndExAlertDialog.Builder(SignInOptionsActivity.this)
                .setTitle("Oops!!!")
                .setMessage("Sorry, something is not right \n\"" + s + "\"")
                .setPositiveBtnText("Ok")
                .OnPositiveClicked(new AndExAlertDialogListener() {
                    @Override
                    public void OnClick(String input) {
                        Toast.makeText(SignInOptionsActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    private void showSuccessMessage(String message) {
        new AndExAlertDialog.Builder(SignInOptionsActivity.this)
                .setTitle("Yippee!!!")
                .setMessage("We succeeded, \n\"" + message + "\"")
                .setPositiveBtnText("OK")
                .OnPositiveClicked(new AndExAlertDialogListener() {
                    @Override
                    public void OnClick(String input) {
                        Toast.makeText(SignInOptionsActivity.this,"you may login now",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignInOptionsActivity.this, DashboardActivity.class));
                       // moveOnToLogin();
                    }
                })
                .build();
    }

    private void loginUser(JSONObject andSection) {
        Rest_DB_Interface rest_db_interface = Rest_DB_Client.getClient().create(Rest_DB_Interface.class);
        Call<ArrayList<LoginUserResponseModel>> call = rest_db_interface.loginUser(andSection);

        call.enqueue(new CustomCallBack<>(SignInOptionsActivity.this, new Callback<ArrayList<LoginUserResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LoginUserResponseModel>> call, Response<ArrayList<LoginUserResponseModel>> response) {
                if (response.code() == 200) {
                    ArrayList<LoginUserResponseModel> loginUserResponseModels = response.body();
                    if (loginUserResponseModels.size() > 0){
                        LoginUserResponseModel loginUserResponseModel = loginUserResponseModels.get(0);

                        AppUser appUser = new AppUser(loginUserResponseModel.getFullname(),loginUserResponseModel.getUsername(),loginUserResponseModel.getEmail(),loginUserResponseModel.getBusiness_name(),loginUserResponseModel.getUser_type());
                        LocalDatabase.UserDatabase userDatabase = new LocalDatabase.UserDatabase(SignInOptionsActivity.this);
                        userDatabase.storeUserData(appUser);
                        userDatabase.setUserLoggedIn(true);

                        Toast.makeText(SignInOptionsActivity.this,"Welcome back",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignInOptionsActivity.this, DashboardActivity.class));

                       // showSuccessMessage("You are welcome ");
                    }else {

                        Log.d(TAG, "onResponse: " + response.body());
                        Log.d(TAG, "onResponse: " + response.raw().toString());
                        showErrorMessage("Incorrect username or password");
                    }

                }else {

                    Log.d(TAG, "onResponse: " + response.body());
                    Log.d(TAG, "onResponse: " + response.raw().toString());
                    showErrorMessage("Incorrect username or password");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoginUserResponseModel>> call, Throwable t) {
                showErrorMessage(t.getLocalizedMessage());
            }
        }));
    }
}