package com.firstapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.firstapp.MainApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    /**
     * 把图片压缩到200K
     *
     * @param oldPath 压缩前的图片路径
     * @param newPath 压缩后的图片路径
     * @return 压缩完的文件
     */
    public static File compressFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(newPath)) {
            return null;
        }

        File f = new File(oldPath);
        if (!f.exists()) {
            return null;
        }

        Bitmap compressBitmap = FileUtils.decodeFile(oldPath);
        Bitmap newBitmap = ratingImage(oldPath, compressBitmap);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        newBitmap.compress(CompressFormat.PNG, 100, os);
        byte[] bytes = os.toByteArray();

        File file = null;
        try {
            file = FileUtils.getFileFromBytes(bytes, newPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!newBitmap.isRecycled()) {
                newBitmap.recycle();
            }

            if (compressBitmap != null && !compressBitmap.isRecycled()) {
                compressBitmap.recycle();
            }
        }
        return file;
    }

    private static Bitmap ratingImage(String filePath, Bitmap bitmap) {
        int degree = readPictureDegree(filePath);
        return rotateImage(degree, bitmap);
    }

    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap bit
     * @return Bitmap
     */
    public static Bitmap rotateImage(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Log.d("angle2" , angle+"");
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 把字节数组保存为一个文件
     *
     * @param b          byte[] 数组
     * @param outputFile 输出文件路径
     * @return File
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fo = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fo);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * 图片压缩
     *
     * @param fPath 图片地址
     * @return Bitmap
     */
    public static Bitmap decodeFile(String fPath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inDither = false; // Disable Dithering mode
        opts.inPurgeable = true; // Tell to gc that whether it needs free
        opts.inInputShareable = true; // Which kind of reference will be used to
        BitmapFactory.decodeFile(fPath, opts);
        final int REQUIRED_SIZE = 600;
        int scale = 1;
        if (opts.outHeight > REQUIRED_SIZE || opts.outWidth > REQUIRED_SIZE) {
            final int heightRatio = Math.round((float) opts.outHeight
                    / (float) REQUIRED_SIZE);
            final int widthRatio = Math.round((float) opts.outWidth
                    / (float) REQUIRED_SIZE);
            scale = heightRatio < widthRatio ? heightRatio : widthRatio;//
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        Bitmap bm = BitmapFactory.decodeFile(fPath, opts).copy(Config.ARGB_8888, false);
        return bm;
    }


    /**
     * 创建目录
     *
     * @param path 目录
     */
    public static void setMkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            Log.d("file","file 目录不存在");
        } else {
            Log.d("file",path);
        }
    }

    /**
     * 获取目录名称
     *
     * @param url url地址
     * @return FileName
     */
    public static String getFileName(String url) {
        int lastIndexStart = url.lastIndexOf("/");
        if (lastIndexStart != -1) {
            return url.substring(lastIndexStart + 1, url.length());
        } else {
            return System.currentTimeMillis() + "";
        }
    }

    /**
     * 删除该目录下的文件
     *
     * @param path 文件路径
     */
    public static void delFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 删除该目录下的文件
     *
     * @param file 需要删除的文件
     */
    public static void delFile(File file) {
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 缓存文件是否存在
     * @param url 文件复制
     * @return boolean
     */
    public static boolean isExist(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        File file = new File(getCachePath(MainApplication.gedAppContext()) + "/" + getFileName(url));
        return file.exists();
    }


    /**
     * @param context context
     * @return 缓存路径
     */
    public static String getCachePath(@NonNull Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null != externalCacheDir) {
            String path = externalCacheDir.getAbsolutePath() + "/uploadImg/";
            setMkdirs(path);
            return path;
        } else {
            File cacheDir = context.getCacheDir();
            String path = cacheDir.getAbsolutePath() + "/uploadImg/";
            setMkdirs(path);
            return path;
        }
    }

    public static String getCachePath(@NonNull Context context, String fileLast) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null != externalCacheDir) {
            String path = externalCacheDir.getAbsolutePath() + "/" + fileLast + "/";
            setMkdirs(path);
            return path;
        } else {
            File cacheDir = context.getCacheDir();
            String path = cacheDir.getAbsolutePath() + "/" + fileLast + "/";
            setMkdirs(path);
            return path;
        }
    }

    public static String getImgName(String fileType) {
        return System.currentTimeMillis() + fileType;
    }

    public static File createTmpFile(Context context) {
        String state = Environment.getExternalStorageState();
        File dir;
        // 已挂载
        dir = state.equals(Environment.MEDIA_MOUNTED)
                ? context.getExternalCacheDir()
                : context.getCacheDir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.CHINA).format(new Date());
        String fileName = "qualitydev_" + timeStamp + ".jpg";
        return new File(dir, fileName);
    }

    public static void moveFile(File oldPath, File newPath) throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(oldPath);
            outputStream = new FileOutputStream(newPath);
            byte[] buff = new byte[1024];
            int len;
            while ((len = inputStream.read(buff)) != -1){
                outputStream.write(buff,0,len);
            }
            outputStream.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
