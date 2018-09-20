package com.firstapp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.firstapp.reactutils.UpdateAndroidPackage;
import com.firstapp.widget.manager.CirclePackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class MainApplication extends Application implements ReactApplication {
  private static final String FILE_NAME = "index.android";
  private static final ImagePickerPackage mImagePickerPackage=new ImagePickerPackage();
  private static  Context context;

  public static  ImagePickerPackage getImagePickerPackage(){
       return  mImagePickerPackage;
  }
  public static Context gedAppContext(){
    return context;
  }

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }


    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new CustomToastPackage(),
              mImagePickerPackage,
          new NativePagePackage(),
          new CirclePackage(),
          new UpdateAndroidPackage()
      );
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }

    @Nullable
    @Override
    protected String getJSBundleFile() {
      File file = new File(getExternalCacheDir(), FILE_NAME);
      if (file != null && file.length() > 0) {
        return file.getAbsolutePath();
      }
      return super.getJSBundleFile();
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    copyBundle();
    SoLoader.init(this, /* native exopackage */ false);
    context=getApplicationContext();
  }

  private void copyBundle(){
    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return;
    }
    File file = new File(getExternalCacheDir(), FILE_NAME);
    if (file != null && file.length() > 0) {
      return;
    }
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      bis = new BufferedInputStream(getAssets().open("index.android.bundle_1.0"));
      bos = new BufferedOutputStream(new FileOutputStream(file));
      int len = -1;
      byte[] buffer = new byte[512];
      while ((len = bis.read(buffer)) != -1) {
        bos.write(buffer, 0, len);
        bos.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (bis != null) {
          bis.close();
        }
        if (bos != null) {
          bos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
