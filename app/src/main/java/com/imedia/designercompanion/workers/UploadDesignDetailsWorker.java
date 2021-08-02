package com.imedia.designercompanion.workers;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.imedia.designercompanion.network_operations.Rest_DB_Client;
import com.imedia.designercompanion.network_operations.Rest_DB_Interface;
import com.imedia.designercompanion.network_operations.models.MyDesignsModel;
import com.imedia.designercompanion.network_operations.models.MyDesignsModelResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDesignDetailsWorker extends Worker {

Context context;
private static final String TAG = UploadDesignDetailsWorker.class.getSimpleName();

    public UploadDesignDetailsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        String desc =getInputData().getString("description");
        String bus_name = getInputData().getString("business_name");
        String image_link = getInputData().getString("image_link");

        Log.d(TAG, "doWork: URL" + image_link);
        final boolean[] success = {false};

        MyDesignsModel myDesignsModel = new MyDesignsModel(desc,bus_name, image_link);
        Rest_DB_Interface rest_db_interface = Rest_DB_Client.getClient().create(Rest_DB_Interface.class);

        Call<MyDesignsModelResponse> call = rest_db_interface.postDesign(myDesignsModel);
        call.enqueue(new Callback<MyDesignsModelResponse>() {
            @Override
            public void onResponse(Call<MyDesignsModelResponse> call, Response<MyDesignsModelResponse> response) {
                Log.d(TAG, "doWork: code" + response.code());
                if (response.code() == 201){

                    success[0] = true;
                }
            }

            @Override
            public void onFailure(Call<MyDesignsModelResponse> call, Throwable t) {

            }
        });

        Log.d(TAG, "doWork: Done");

        if (success[0])
            return Result.success();
        else
            return Result.retry();
    }
}
