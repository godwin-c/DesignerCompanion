package com.imedia.designercompanion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.imedia.designercompanion.R;

public class SignInOptionsActivity extends AppCompatActivity {

    Button link_to_sign_up, login_button;

    TextView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_options);

        link_to_sign_up = findViewById(R.id.link_to_sign_up);

        image = findViewById(R.id.logo_text);
        logoText = findViewById(R.id.welcome_message);
        sloganText = findViewById(R.id.sign_in_text);
        username = findViewById(R.id.sign_in_username);
        password = findViewById(R.id.sign_in_password);
        login_button = findViewById(R.id.login_button);

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
                startActivity(new Intent(SignInOptionsActivity.this, DashboardActivity.class));
            }
        });
    }
}