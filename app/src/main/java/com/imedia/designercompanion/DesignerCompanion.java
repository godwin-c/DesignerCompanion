package com.imedia.designercompanion;

import android.app.Application;

import com.cloudinary.android.MediaManager;

public class DesignerCompanion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MediaManager.init(this);
    }
}
