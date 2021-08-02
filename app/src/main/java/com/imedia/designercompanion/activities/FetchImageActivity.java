package com.imedia.designercompanion.activities;

import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.imagepickerlibrary.ImagePickerActivityClass;
import com.app.imagepickerlibrary.ImagePickerBottomsheet;
import com.google.android.material.textfield.TextInputEditText;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.classes.FileToUpload;
import com.imedia.designercompanion.databases.LocalDatabase;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;

public class FetchImageActivity extends AppCompatActivity implements ImagePickerBottomsheet.ItemClickListener, ImagePickerActivityClass.OnResult {

    private static final String TAG = FetchImageActivity.class.getSimpleName();
    Button take_pics, done_with_image, cancel_with_image;
    ImagePickerActivityClass imagePicker;
    ImagePickerBottomsheet fragment;
    AppCompatImageView selected_image;
    TextInputEditText selected_image_description_txt;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_image);

        fragment = new ImagePickerBottomsheet();
        selected_image = findViewById(R.id.selected_image);

        imagePicker = new ImagePickerActivityClass(this, this, this, getActivityResultRegistry());
        //set to true if you want all features(crop,rotate,zoomIn,zoomOut)
        //by Default it's value is set to false (only crop feature is enabled)
        imagePicker.cropOptions(true);

        take_pics = findViewById(R.id.take_pics_btn);
        take_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.show(getSupportFragmentManager(), "bottomSheetActionFragment");
            }
        });

        done_with_image = findViewById(R.id.done_with_image);
        done_with_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    String desc = selected_image_description_txt.getText().toString();

                    if (desc.equals("")) {
                        new AndExAlertDialog.Builder(FetchImageActivity.this)
                                .setTitle("Oops!!!")
                                .setMessage("You need to provide a little description for this Image")
                                .setPositiveBtnText("Ok")
                                .OnPositiveClicked(new AndExAlertDialogListener() {
                                    @Override
                                    public void OnClick(String input) {
                                        Toast.makeText(FetchImageActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .build();
                        return;
                    }
//                    LocalDatabase.OtherDataBase otherDataBase = new LocalDatabase.OtherDataBase(FetchImageActivity.this);
//                    FileToUpload fileToUpload = new FileToUpload(uri.getPath(), desc);
//                    otherDataBase.storeFileToUpload(fileToUpload);

                    Intent data = new Intent();
                    data.putExtra("file_path",uri.getPath());
                    data.putExtra("description",desc);
                    setResult(RESULT_OK,data);
                    finish();

                    // otherDataBase.storeInfoTemp(getString(R.string.temp_picture_path), uri.getPath());Toast.makeText(FetchImageActivity.this, "Gotten " + uri.getPath(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(FetchImageActivity.this, "No Image selected ", Toast.LENGTH_SHORT).show();

                }
                finish();


            }
        });

        cancel_with_image = findViewById(R.id.cancel_with_image);
        cancel_with_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FetchImageActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();

                Intent data = new Intent();
                setResult(RESULT_CANCELED,data);
                finish();
            }
        });

        selected_image_description_txt = findViewById(R.id.selected_image_description_txt);
    }

    @Override
    public void returnString(Uri uri) {
        this.uri = uri;
        selected_image.setImageURI(uri);
    }

    @Override
    public void doCustomisations(ImagePickerBottomsheet imagePickerBottomsheet) {
        //Customize button text
        imagePickerBottomsheet.setButtonText("Take a Picture", "Select from Gallery", "Cancel");

        //Customize button text color
        imagePickerBottomsheet.setButtonColors(ContextCompat.getColor(this, R.color.teal_700), ContextCompat.getColor(this, R.color.teal_200), ContextCompat.getColor(this, R.color.colorAccent));

        //For more customization make a style in your styles xml and pass it to this method. (This will override above method result).
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            imagePickerBottomsheet.setTextAppearance(R.style.TextAppearance_AppCompat_Button);
        }

        //To customize bottomsheet style
        imagePickerBottomsheet.setBottomSheetBackgroundStyle(R.drawable.drawable_bottom_sheet_dialog);
    }

    @Override
    public void onItemClick(String s) {
        Log.d(TAG, "onItemClick: " + s.toString());
        if (s.equals("Camera")) {
            imagePicker.takePhotoFromCamera();
        } else if (s.equals("Gallery")) {
            imagePicker.choosePhotoFromGallery();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(requestCode, resultCode, data);
    }

}