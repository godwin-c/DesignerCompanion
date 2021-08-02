package com.imedia.designercompanion.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.classes.ValidationClass;

import java.io.File;

public class CreateCustomerActivity extends AppCompatActivity {
    ImageView customer_picture_imv;
    Button customer_update_BTN;
    TextInputEditText customer_full_name, customer_email, customer_phone_number, customer_address;

    String file_path = "";
    String description = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        setActionsOnItems();

    }

    private void setActionsOnItems() {
        customer_picture_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateCustomerActivity.this, FetchImageActivity.class);
                launchSomeActivity.launch(intent);
            }
        });

        customer_update_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(new ValidationClass(customer_full_name, "").validateInput())) {
                    return;
                }

                if (!(new ValidationClass(customer_email, "email").validateEmail())) {
                    return;
                }

                if (!(new ValidationClass(customer_phone_number, "").validateInput())) {
                    return;
                }

                if (!(new ValidationClass(customer_address, "").validateInput())) {
                    return;
                }


            }
        });
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        file_path = data.getStringExtra("file_path");
                        description = data.getStringExtra("description");
                        displayImage(file_path);
                    }
                }
            });

    private void displayImage(String file_path) {

        File imgFile = new  File(file_path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            customer_picture_imv.setImageBitmap(myBitmap);

        }

//        Picasso.get()
//                .load(file_path)
//                .placeholder(R.drawable.female_model_1)
//                .error(R.drawable.female_model_2)
//                .into(customer_picture_imv);
    }


    private void initViews() {
        customer_full_name = findViewById(R.id.customer_full_name);
        customer_email = findViewById(R.id.customer_email);
        customer_phone_number = findViewById(R.id.customer_phone_number);
        customer_address = findViewById(R.id.customer_address);
        customer_picture_imv = findViewById(R.id.customer_picture_imv);
        customer_update_BTN = findViewById(R.id.customer_update_BTN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}