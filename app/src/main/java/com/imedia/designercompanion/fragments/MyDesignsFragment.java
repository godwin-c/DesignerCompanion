package com.imedia.designercompanion.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.imedia.designercompanion.R;
import com.imedia.designercompanion.activities.DashboardActivity;
import com.imedia.designercompanion.activities.FetchImageActivity;
import com.imedia.designercompanion.classes.AppUser;
import com.imedia.designercompanion.classes.FileToUpload;
import com.imedia.designercompanion.databases.LocalDatabase;
import com.imedia.designercompanion.network_operations.models.MyDesignsModel;
import com.imedia.designercompanion.workers.UploadDesignDetailsWorker;

import java.util.HashMap;
import java.util.Map;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;


public class MyDesignsFragment extends Fragment {

    private static final String TAG = MyDesignsFragment.class.getSimpleName();
    private static final int IMAGE_PICKER_SELECT = 11;
    private ProgressDialog mProgressDialog;

    SwipeRefreshLayout refresh_view;
    ViewPager viewpager;

    AppUser appUser;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    private FloatingActionButton fab;
    String file_path = "";
    String description = "";
    Map config = new HashMap();

    public MyDesignsFragment() {
    }

    private void configCloudinary() {
        config.put("cloud_name", getContext().getString(R.string.cloudinary_name));
        config.put("api_key", getContext().getString(R.string.cloudinary_api_key));
        config.put("api_secret", getContext().getString(R.string.cloudinary_api_secret));
        MediaManager.init(getContext(), config);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalDatabase.UserDatabase userDatabase = new LocalDatabase.UserDatabase(getContext());
        appUser = userDatabase.getLoggedInUser();

        //configCloudinary();
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

                        Log.d(TAG, "onActivityResult: " + description);

                        FileToUpload fileToUpload = new FileToUpload(file_path, description);
                        uploadImageToCDNY(fileToUpload);
                    }
                }
            });

    private void uploadImageToCDNY(FileToUpload fileToUpload) {

        MediaManager.get().upload(fileToUpload.getUrl()).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                mProgressDialog = new ProgressDialog(getContext());
                ((Activity) getContext()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("please wait...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                mProgressDialog.setMessage("uploading design...");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                MyDesignsModel myDesignsModel = new MyDesignsModel(fileToUpload.getDescription(),appUser.getBusiness_name(), resultData.get("url").toString());
                callUploadWorker(myDesignsModel);

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        }).dispatch();
    }

    private void callUploadWorker(MyDesignsModel fileToUpload) {

        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);

        // Passing params
        Data.Builder data = new Data.Builder();
        data.putString("description", fileToUpload.getDescription());
        data.putString("business_name", fileToUpload.getBusiness_name());
        data.putString("image_link", fileToUpload.getImage_link());

        OneTimeWorkRequest syncWorkRequest =
                new OneTimeWorkRequest.Builder(UploadDesignDetailsWorker.class)
                        .addTag("Sync")
                        .setInputData(data.build())
                        .setConstraints(builder.build())
                        .build();

        WorkManager workManager = WorkManager.getInstance(getContext());
        workManager.enqueue(syncWorkRequest);

        Toast.makeText(getContext(), "your design upload is being finalized", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_designs, container, false);
        fab = view.findViewById(R.id.fab);
        refresh_view = view.findViewById(R.id.refresh_view);
        viewpager = view.findViewById(R.id.viewpager);


        setActionOnViewItems();
        return view;
    }

    private void setActionOnViewItems() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AndExAlertDialog.Builder(getContext())
                        .setTitle("Add Image")
                        .setMessage("Would you like to select image for Upload?")
                        .setPositiveBtnText("Yes")
                        .OnPositiveClicked(new AndExAlertDialogListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                            @Override
                            public void OnClick(String input) {
                                // user_type = "premium";
                                Toast.makeText(getContext(), "Selecting Image", Toast.LENGTH_SHORT).show();
                                //callImagePicker();
                                Intent intent = new Intent(getContext(), FetchImageActivity.class);
                                launchSomeActivity.launch(intent);
                                // startActivityForResult(new Intent(getContext(), FetchImageActivity.class), 101);
                                // startActivity(new Intent(getContext(), FetchImageActivity.class));
                            }
                        })
                        .setNegativeBtnText("No")
                        .OnNegativeClicked(new AndExAlertDialogListener() {
                            @Override
                            public void OnClick(String input) {
                                Toast.makeText(getContext(), "canceled", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .build();
            }
        });

        refresh_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalDatabase.OtherDataBase otherDataBase = new LocalDatabase.OtherDataBase(getContext());
        String path = otherDataBase.getInfoTemp(getString(R.string.temp_picture_path));
        if (!path.equals(""))
            Toast.makeText(getContext(), path, Toast.LENGTH_SHORT).show();

        // otherDataBase.storeInfoTemp(getString(R.string.temp_picture_path), uri.getPath());Toast.makeText(FetchImageActivity.this, "Gotten " + uri.getPath(), Toast.LENGTH_SHORT).show();
    }
}