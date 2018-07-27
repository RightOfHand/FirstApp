package com.firstapp.widget;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.facebook.react.ReactActivity;
import com.firstapp.R;
import com.firstapp.base.BaseActivity;
import com.firstapp.util.CaptureCropUtil;
import com.firstapp.util.CheckPermissionUtils;
import com.firstapp.util.CompressImageUtils;
import com.firstapp.util.FileUtils;

import java.io.File;

/**
 * Description:活动图片-（拍照-手机相册-海报模版）
 * Created by song on 2018/4/27.
 * email：bjay20080613@qq.com
 */

public class SelectPicturePop extends PopupWindow implements View.OnClickListener{

    private static final int REQ_CAMERA = 1;
    private static final int REQ_ALBUM = 2;
    private static final int REQ_PLACARD = 3;

    private Button mCamera, mAlbum,mCancel;
    private String imgUrl = "";
    private File mImagePath;
    private BaseActivity activity;
    private UploadCallBack callBack;
    private String newPath = "";
    private boolean selectEnable = false;
    private boolean canEdit = true;
    private boolean clearable = true;
    private boolean cropAble = false;
    private int aspectX = 1;
    private int aspectY = 1;
    private CaptureCropUtil cropUtil;

    public SelectPicturePop(BaseActivity activity) {
        this.activity = activity;
        cropUtil = new CaptureCropUtil(activity);
        showUploadWindow();
        newPath = FileUtils.getCachePath(activity) + FileUtils.getImgName(".jpg");
    }

    public void setCallBack(UploadCallBack callBack) {
        this.callBack = callBack;
    }

    public void setCameraEnable(boolean cameraEnable) {
        if (mCamera != null) {
            mCamera.setVisibility(cameraEnable ? View.VISIBLE : View.GONE);
        }
    }

    public void setCropAble(boolean cropAble, int aspectX, int aspectY) {
        this.cropAble = cropAble;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
    }

    public void showUploadWindow() {
        View view = LayoutInflater.from(activity).inflate(R.layout.npop_upload_image, null);
        mCamera = (Button) view.findViewById(R.id.btn_camera);
        mCancel = (Button) view.findViewById(R.id.btn_cancel);
        mAlbum = (Button) view.findViewById(R.id.btn_album);
        setOutsideTouchable(false);

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupBottomAnim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.transparent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
//        params.alpha = 0.8f;
        activity.getWindow().setAttributes(params);

        mCamera.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mAlbum.setOnClickListener(this);
        mCamera.setText("拍照");
        mAlbum.setText("相册");

    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 1f;
        activity.getWindow().setAttributes(params);
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_camera) {
            CheckPermissionUtils.checkCameraPermission(activity, new CheckPermissionUtils.PermissionRequestCallback() {
                @Override
                public void hasPermission() {
                    openCamera();
                }

                @Override
                public void noPermission() {

                }
            });

            dismiss();
        } else if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id ==R.id.btn_album) {
            openAlbum();
            dismiss();
        }
    }


    private void openAlbum() {
        if (CheckPermissionUtils.hasStoragePermission(activity)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            activity.startActivityForResult(intent, REQ_ALBUM);
        } else {
            CheckPermissionUtils.checkStoragePermission(activity, new CheckPermissionUtils.PermissionRequestCallback() {
                @Override
                public void hasPermission() {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    activity.startActivityForResult(intent, REQ_ALBUM);
                }

                @Override
                public void noPermission() {
                    Toast.makeText(activity,"请到系统设置里打开读取手机存储权限",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * todo 打开照相机
     */
    private void openCamera() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mImagePath = new File(newPath);

            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(activity,"com.aicai.apps.qualitydev.fileProvider", mImagePath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImagePath));
            }
            activity.startActivityForResult(intent, REQ_CAMERA);
        } else {
        }
    }


    private void toCropImage(String uri){
//        int width = UIHelper.getDeviceWidth(activity);
//        cropUtil.resizeImageNew(uri, aspectX, aspectY, width);
    }


    private String findPath(Uri originalUri){
        String path = originalUri.getPath();
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(originalUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }

        return path;
    }

    private void findPic(Uri originalUri) {
        if (originalUri != null) {
            String path = findPath(originalUri);
            if (!TextUtils.isEmpty(path)) {
                new CompressImageUtils().CompressImage(activity, path, newPath, new CompressImageUtils.CompressCallBack() {
                    @Override
                    public void success(File file) {
                        if (file != null) {
//                            processUpload(new UploadFile(file));
                        }
                    }
                });
            } else {
                callBack.onFail("图片获取异常");
            }
        }
    }

//    /**
//     * TODO 上传图片
//     *
//     * @param uploadFile upload
//     */
//    private void processUpload(final UploadFile uploadFile) {
//        Progress.show(activity,"上传中...");
//        final File file = uploadFile.getFile();
//        UploadImageManager.uploadFile(file,new HttpCallBack(new TypeReference<FileResult>(){}){
//            @Override
//            public void onResponse(ResultContainer result) {
//                if(result.isSuccess()){
//                    ToastHelper.makeToast("图片上传成功");
//                    FileResult fileResult=(FileResult) result.getData();
//                    if (fileResult!=null){
//                        if (callBack != null) {
//                            callBack.onSuccess(fileResult.getFileId() , fileResult.getFileURL());
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(ResultContainer result) {
//                super.onFailure(result);
//                if (Progress.isShowing()) Progress.dismiss();
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                if (Progress.isShowing()) Progress.dismiss();
//            }
//        });
//
//    }

    public interface UploadCallBack {
        void onSuccess(String imgId, String imgUrl);

        void onClear();

        void onFail(String error);

        void onType(int type);
    }

}
