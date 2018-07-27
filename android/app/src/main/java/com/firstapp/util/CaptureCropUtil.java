package com.firstapp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午3:45
 */
import com.firstapp.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class CaptureCropUtil {

    public static final String CROP_IMAGE_PATH = "/crop/";
    public static final String CAPTURE_IMAGE_PATH = "/capture/";

    public static final int REQUEST_CODE_CAMERA = 0xa00;  //照相的request_code
    public static final int REQUEST_CODE_IMAGE = 0xa01;   //相册的request_code
    public static final int REQUEST_CODE_RESIZE = 0xa02;  //截取的request_code

    private BaseActivity mActivity;
    private long mCurrentTime;

    public CaptureCropUtil(BaseActivity activity) {
        mActivity = activity;
    }

    public void openCamera() {
        if (CheckPermissionUtils.hasCameraPermission(mActivity)) {
            mCurrentTime = System.currentTimeMillis();
            startCamera();

        } else {
            CheckPermissionUtils.checkCameraPermission((BaseActivity) mActivity, new CheckPermissionUtils.PermissionRequestCallback() {
                @Override
                public void hasPermission() {
                    mCurrentTime = System.currentTimeMillis();

                    startCamera();
                }

                @Override
                public void noPermission() {
                    Toast.makeText(mActivity,"请打开权限",Toast.LENGTH_LONG).show();
                }
            });
        }


    }


    @SuppressLint("ObsoleteSdkInt")
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mActivity, "com.aicai.apps.qualitydev.fileProvider", getTempCaptureImageFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempCaptureImageFile()));
        }
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        mActivity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


    public void openAlbum() {
        if (CheckPermissionUtils.hasStoragePermission(mActivity)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            mActivity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
        } else {
            CheckPermissionUtils.checkStoragePermission((BaseActivity) mActivity, new CheckPermissionUtils.PermissionRequestCallback() {
                @Override
                public void hasPermission() {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    mActivity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
                }

                @Override
                public void noPermission() {
                    Toast.makeText(mActivity,"请到系统设置里打开读取手机存储权限",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void resizeImage(Uri uri, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempCropImageUri());
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); //关闭人脸检测
        mActivity.startActivityForResult(intent, REQUEST_CODE_RESIZE);
    }

//    /**
//     * 裁剪图片方法实现
//     *
//     * @param path 保存路径
//     */
//    public void resizeImageNew(String path, int aspectX, int aspectY, int outputX) {
//        Intent intent = new Intent(mActivity, ClipImageActivity.class);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("maxWidth", outputX);
//        intent.putExtra("inputPath",path);
//        intent.putExtra("outputPath", getTempCropImageFile().getAbsolutePath());
//        mActivity.startActivityForResult(intent, REQUEST_CODE_RESIZE);
//    }

    /**
     * Uri转成Bitmap
     * */
    public Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            final InputStream is = mActivity.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 使用前提：用户已登陆
     * 获取照片存放的uri
     *
     * @return Uri
     */
    public Uri getTempCaptureImageUri() {
        return Uri.fromFile(getTempCaptureImageFile());
    }

    public File getTempCaptureImageFile() {
        final File path = new File(FileUtils.getCachePath(mActivity) + CAPTURE_IMAGE_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        return new File(path.getAbsolutePath(), "CAPTURE_TEMP_" + mCurrentTime);
    }


    public Uri getTempCropImageUri() {
        return Uri.fromFile(getTempCropImageFile());
    }

    public File getTempCropImageFile() {
        final File path = new File(FileUtils.getCachePath(mActivity) + CROP_IMAGE_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        return new File(path.getAbsolutePath(), "CROP_TEMP").getAbsoluteFile();
    }

    public File getNoResizeCropImageFile(Uri uri) {
        final File path = new File(FileUtils.getCachePath(mActivity) + CROP_IMAGE_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        return new File(path.getAbsolutePath(), uri.hashCode() + ".jpg").getAbsoluteFile();
    }
}
