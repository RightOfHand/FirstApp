package com.firstapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.PermissionListener;
import com.firstapp.service.MyTaskService;
import com.firstapp.util.FileUtils;
import com.firstapp.widget.dialog.Dialogs;
import com.firstapp.widget.dialog.KeyValueItem;
import com.firstapp.widget.dialog.OnSelectListener;
import com.firstapp.widget.pop.TextListPop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class MainActivity extends ReactActivity {
    private static final int IMAGE_PICKER_REQUEST = 200;
    private ReactApplicationContext mContext;
    private static TextListPop mPopWin;
    public static final String CROP_IMAGE_PATH = "/crop/";
    public static final String CAPTURE_IMAGE_PATH = "/capture/";
    public static final int REQUEST_CODE_CAMERA = 0xa00;  //照相的request_code
    public static final int REQUEST_CODE_IMAGE = 0xa01;   //相册的request_code
    private static long mCurrentTime=System.currentTimeMillis();
    private Dialog dialog;

    private final static String[] cameraPermissions = {Manifest.permission.CAMERA};//相机权限
    private final static String[] phonePermissions = {Manifest.permission.CALL_PHONE};//电弧权限
    private final static String[] necessaryPermissions = {Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE};//内存权限

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Nullable
    @Override
    protected String getMainComponentName() {
        return "FirstApp";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private void headlessTest(){
        Intent intent=new Intent(getApplicationContext(),MyTaskService.class);
        Bundle bundle=new Bundle();
        bundle.putString("songy","native service");
        intent.putExtras(bundle);
        getApplicationContext().startService(intent);
    }

    public void setSelect(){
        List<KeyValueItem> keyValueItems=new ArrayList<>();
        keyValueItems.add(new KeyValueItem("1","相机"));
        keyValueItems.add(new KeyValueItem("2","相册"));
        dialog=Dialogs.showListDialog(this, "", keyValueItems, new OnSelectListener<KeyValueItem>() {
            @Override
            public void onSelect(int position, KeyValueItem keyValueItem) {
                if (keyValueItem.getKey().equals("1")){
                    check();
                }else if (keyValueItem.getKey().equals("2")){
                    openAlbum();
                }
            }
        });
    }
    //原生请求权限
    private void check(){
        if (PermissionChecker.PERMISSION_GRANTED==PermissionChecker.checkCallingOrSelfPermission(MainActivity.this,Manifest.permission.CAMERA)){
            startCamera();
        }else {
            MainActivity.this.requestPermissions(cameraPermissions, 100, new PermissionListener() {
                @Override
                public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                    if (grantResults!=null && grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
                       startCamera();
                    }else {
                        Toast.makeText(MainActivity.this,"不给权限用不了",Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
        }
    }



    @SuppressLint("ObsoleteSdkInt")
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, "com.firstapp.fileProvider", getTempCaptureImageFile(this));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempCaptureImageFile(this)));
        }
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }
    public static File getTempCaptureImageFile(Activity mActivity) {
        final File path = new File(FileUtils.getCachePath(mActivity) + CAPTURE_IMAGE_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        return new File(path.getAbsolutePath(), "CAPTURE_TEMP_" + mCurrentTime);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ImagePickerModule.REQUEST_CODE_CAMERA) {
            File file=getTempCaptureImageFile(this);
            MainApplication.getImagePickerPackage().mPickerModule.sendMsgToJS(file.getAbsoluteFile().toString());

        }else if (requestCode==ImagePickerModule.REQUEST_CODE_IMAGE){
            if (resultCode == Activity.RESULT_OK) {
                Uri  uri = data.getData();
                Log.d("URI:", uri.toString());
                MainApplication.getImagePickerPackage().mPickerModule.sendMsgToJS(uri.toString());

            }
        }
    }

    @Override
    protected void onDestroy() {

       if (dialog!=null){
           dialog.dismiss();
           dialog=null;
       }
        super.onDestroy();
    }
}
