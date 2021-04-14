package com.imedia.designercompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedia.designercompanion.activities.SignInOptionsActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME = 5000;
    //Variables
    Animation topAnimation, bottomAnimation;
    ImageView imv_fem_1, imv_fem_2;
    TextView app_name_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imv_fem_1 = findViewById(R.id.imag_1);
        imv_fem_2 = findViewById(R.id.img_2);
        app_name_logo = findViewById(R.id.app_name_logo);

        imv_fem_1.setAnimation(topAnimation);
        imv_fem_2.setAnimation(bottomAnimation);
        app_name_logo.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // do something
                Intent intent = new Intent(MainActivity.this, SignInOptionsActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(imv_fem_2,"logo_image");
                pairs[1] = new Pair<View,String>(app_name_logo,"logo_text");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, activityOptions.toBundle());
                    finish();
                }
            }
        },SPLASH_SCREEN_TIME);
    }
}