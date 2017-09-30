/********************************************************************
 * 文件名称：FileIO.java
 * 所属项目名称：PushReliabilityTest
 * 创建人：
 * 创建时间：2013-6-29 上午11:28:57
 * Copyright (c) 2013 BlackCrystal. All rights reserved.
 ********************************************************************/
package com.vision.smarthomeapi.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * 类的名称:FileIO</br> 主要功能描述:文件读写操作类</br> 创建日期:2013年8月7日
 *
 * @author 孟凡硕
 *         <table>
 *         <tr>
 *         <th>版本</th>
 *         <th>日期</th>
 *         <th>作者</th>
 *         <th>描述</th>
 *         <tr>
 *         <td>0.1</td>
 *         <td>2013年8月7日</td>
 *         <td>孟凡硕</td>
 *         <td>初始创建</td>
 *         </table>
 */
public class FileIO {

    private Context context;
    private static FileIO fileIO;

    public FileIO(Context context) {
        this.context = context;
    }

    /**
     * 获取FileIO对象
     *
     * @param context
     * @return
     */
    public static FileIO getShareFielIo(Context context) {
        if (fileIO == null) {
            fileIO = new FileIO(context);
        }
        return fileIO;
    }


    /**
     * 写文件到应用自有目录
     *
     * @param bytes    需要写入的数据
     * @param fileName 需要写入的文件名，不能包含路径分割符”/“
     * @param mode     写入模式
     */
    public void writeFile(byte[] bytes, String fileName, int mode) {
        try {

            FileOutputStream fout = context.openFileOutput(fileName, mode);
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件到应用自有目录
     *
     * @param writestr 需要写入的数据
     * @param fileName 需要写入的文件名，不能包含路径分割符”/“
     * @param mode     写入模式
     */
    public void writeFile(String writestr, String fileName, int mode) {
        byte[] bytes = new byte[0];
        try {
            bytes = writestr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writeFile(bytes, fileName, mode);
    }

    /**
     * 判断文件是否存在于自有目录下
     *
     * @param fileName 需要判断的文件名，可以是相对路径
     * @return 存在返回true
     */
    public boolean fileIsExists(String fileName) {
        return fileIsExistsByAbsolutePath(context.getFilesDir().getAbsolutePath() + "/" + fileName);
    }

    /**
     * 写文件到应用自有目录
     *
     * @param str      需要写入的数据
     * @param fileName 需要写入的文件名，可以是相对路径
     * @param append   是否追加
     * @return 是否写入成功
     */
    public boolean writeFile(String str, String fileName, boolean append) throws Exception {
        String path = context.getFilesDir().getAbsolutePath();
        return writeByAbsolutePath(path + "/" + fileName, str, append);
    }

    /**
     * 写文件到应用自有目录
     *
     * @param bytes    需要写入的数据
     * @param fileName 需要写入的文件名，可以是相对路径
     * @param append   是否追加
     * @return 是否写入成功
     */
    public boolean writeFile(byte[] bytes, String fileName, boolean append) throws IOException {
        String path = context.getFilesDir().getAbsolutePath();
        return writeByAbsolutePath(path + "/" + fileName, bytes, append);
    }

    /**
     * 读取程序自有目录文件内容
     *
     * @param fileName 需要读取的文件名，可以是相对路径
     * @return
     * @throws Exception
     */
    public byte[] readFileToByteArray(String fileName) throws IOException {
        String path = context.getFilesDir().getAbsolutePath();
        byte[] buffer = readByAbsolutePath(path + "/" + fileName);
        return buffer;
    }

    /**
     * 读取应用自有目录下文件
     *
     * @param fileName 需要读取的文件名，不能包含路径分隔符”/“
     * @return 读取到的字符串
     * @throws Exception
     */
    public String readFile(String fileName) throws Exception {
        String res = "";
        byte[] buffer = readFileToByteArray(fileName);
        if (buffer != null && buffer.length > 0) {
            res = new String(buffer);
        }
        return res;
    }

    /**
     * 删除应用自有目录下文件
     *
     * @param fileName 需要删除的文件名，可以是相对路径
     * @return 删除成功
     */
    public boolean delFile(String fileName) {
        String path = context.getFilesDir().getAbsolutePath();
        return delFileByAbsolutePath(path + "/" + fileName);
    }


    /**
     * 根据绝对路径删除文件
     *
     * @param path 绝对路径
     * @return 删除成功
     */
    public static boolean delFileByAbsolutePath(String path) {
        String finalPath = formattingAbsolutePath(path);
        File file = new File(finalPath);
        return file.delete();
    }

    /**
     * 写文件到绝对路径下
     *
     * @param path   绝对路径
     * @param str    内容
     * @param append 是否追加
     * @return 写入成功
     */
    public static boolean writeByAbsolutePath(String path, String str, boolean append) {
        return writeByAbsolutePath(path, str.getBytes(), append);
    }

    /**
     * 写文件到绝对路径下
     *
     * @param path   绝对路径
     * @param data   内容
     * @param append 是否追加
     * @return 写入成功
     */
    public static boolean writeByAbsolutePath(String path, byte[] data, boolean append) {
        FileOutputStream fos = null;
        String finalPath = formattingAbsolutePath(path);

        if (!fileIsExistsByFinalPath(finalPath)) {
            createFileByFinalPath(finalPath);
        }

        try {
            fos = new FileOutputStream(new File(finalPath), append);
            fos.write(data);
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    /**
     * 读取绝对路径下的文件内容
     *
     * @param path 路径
     * @return 字节数组内容
     * @throws Exception
     */
    public static byte[] readByAbsolutePath(String path) throws IOException {
        byte[] bytes = null;
        FileInputStream fis = null;
        String finalPath = formattingAbsolutePath(path);

        if (!fileIsExistsByFinalPath(finalPath)) {
            return null;
        }
        File file = new File(finalPath);
        if (file.length() > Integer.MAX_VALUE)
            throw new IOException("文件过大,请保证文件大小小于" + Integer.MAX_VALUE);

        try {
            bytes = new byte[(int) file.length()];

            fis = new FileInputStream(file);
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * 文件是否存在在这个路径下
     *
     * @param path 路径
     * @return 是否
     */
    public static boolean fileIsExistsByAbsolutePath(String path) {
        path = formattingAbsolutePath(path);
        return fileIsExistsByFinalPath(path);
    }

    /**
     * 文件是否存在最终路径下
     *
     * @param path 路径
     * @return true 存在
     */
    public static boolean fileIsExistsByFinalPath(String path) {
        long length = fileLength(path);
        if (length > 0) {//如果已存在，则检查长度是否大于0
            return true;
        }
        return false;
    }

    /**
     * 文件是否存在最终路径下
     *
     * @param path 路径
     * @return 是否
     */
    public static long fileLength(String path) {
        File file = new File(path);
        return file.length();
    }

    /**
     * 在绝对路径下创建文件
     *
     * @param path 路径
     * @return 创建成功
     */
    public static boolean createFileByAbsolutePath(String path) {
        return createFileByFinalPath(formattingAbsolutePath(path));
    }

    /**
     * 根据最终路径创建文件
     *
     * @param path 路径
     * @return 是否成功
     */
    public static boolean createFileByFinalPath(String path) {
        try {
            if (!fileIsExistsByFinalPath(path)) {
                if ("/".equals(path.substring(path.length() - 1))) {
                    createDirByFinalPath(path);
                } else {
                    int lastIndex = path.lastIndexOf("/");
                    createDirByFinalPath(path.substring(0, lastIndex));
                    File file = new File(path);
                    file.createNewFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 在绝对路径下创建目录
     *
     * @param path 路径
     * @return 是否成功
     */
    public static boolean createDirByAbsolutePath(String path) {
        return createDirByFinalPath(formattingAbsolutePath(path));
    }

    /**
     * 在最终路径下创建目录
     *
     * @param path 路径
     * @return 是否成功
     */
    public static boolean createDirByFinalPath(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    /**
     * 得出最终路径
     *
     * @param path 路径
     * @return 最终路径
     */
    public static String formattingAbsolutePath(String path) {
        path = path.replace("\\", "/");

        String[] strList = path.split("/");
        StringBuffer sb = new StringBuffer();
        for (String str : strList) {
            if (str != null && !"".equals(str)) {
                sb.append("/");
                sb.append(str);
            }
        }
        if ("/".equals(path.substring(path.length() - 1))) {
            sb.append("/");
        }
        return sb.toString();
    }
}
