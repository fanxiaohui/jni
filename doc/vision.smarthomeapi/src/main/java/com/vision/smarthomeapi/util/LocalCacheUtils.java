package com.vision.smarthomeapi.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vision.smarthomeapi.dal.data.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 本地图片缓存工具类
 * Created by yangle on 2017/3/7.
 */
public class LocalCacheUtils {

    private static String path = Constant.Path.LOCAL_PATH + Constant.Path.PHOTO_PATH;

    /**
     * 从本地读取图片
     *
     * @param fileName 图片名称
     * @return 图片bitmap
     */
    public static Bitmap getBitmapFromLocal(String fileName) {
        try {
            File file = new File(path, fileName + ".jpg");
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向本地存图片
     *
     * @param bitmap   图片bitmap
     * @param fileName 图片名称
     */
    public static void putBitmapToLocal(Bitmap bitmap, String fileName) {
        try {
            File file = new File(path, fileName + ".jpg");
            File parent = file.getParentFile();
            // 创建父文件夹
            if (!parent.exists()) {
                parent.mkdirs();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地读取图片文件
     *
     * @param fileName 图片名称
     * @return 图片文件
     */
    public static File getServiceImageFileFromLocal(String fileName) {
        File file = new File(path, fileName + ".jpg");

        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }


    /**
     * 从本地系统读取图片文件
     *
     * @param fileName 图片名称
     * @return 图片文件
     */
    public static File getSystemImageFileFromLocal(String fileName) {
        File file = new File(fileName);

        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }
}
