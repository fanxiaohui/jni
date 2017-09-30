package com.vision.smarthomeapi.bll.manage;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.vision.smarthomeapi.util.OutPutMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


/***
 * 升级App管理类
 */
public class UpgradeAppManage {

    private Context context;
    private static UpgradeAppManage instance = null;
    //检查升级
    private CheckServerVersionThread checkServerVersionThread;
    //回调接口,是否升级
    private IUpdateAppInfo updateAppInfo;

    private final String TAG = "Config";

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static UpgradeAppManage getInstance(Context context) {
        if (instance == null) { // line A
            instance = new UpgradeAppManage(context); // line B
        }
        return instance;
    }

    private UpgradeAppManage(Context context) {
        this.context = context;
    }

    /**
     * 检测是否升级
     *
     * @param serverPath    远程检测地址(绝对地址)
     * @param updateAppInfo 升级回调接口
     */
    public void checkServerVersion(String serverPath, IUpdateAppInfo updateAppInfo) {
        if (checkServerVersionThread == null) {
            checkServerVersionThread = new CheckServerVersionThread(context, serverPath);
            checkServerVersionThread.start();
            checkServerVersionThread.setName("CHECK");
        }
        this.updateAppInfo = updateAppInfo;
    }

    class CheckServerVersionThread extends Thread {
        private String serverPath;
        private Context context;

        public CheckServerVersionThread(Context context, String serverPath) {
            this.context = context;
            this.serverPath = serverPath;
        }

        @Override
        public void run() {
            super.run();
            try {
                StringBuilder newVerJSON = new StringBuilder();
                HttpClient httpClient = new DefaultHttpClient();//新建http客户端
                HttpParams httpParams = httpClient.getParams();
                List<BasicNameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("aId", "android"));
                params.add(new BasicNameValuePair("cur", getPackageInfo(context).versionName));
                String param = URLEncodedUtils.format(params, HTTP.UTF_8);
                HttpConnectionParams.setConnectionTimeout(httpParams, 3000);//设置连接超时范围
                HttpConnectionParams.setSoTimeout(httpParams, 5000);
                HttpResponse response = httpClient.execute(new HttpPost(serverPath + "?" + param));
                int responseCode = response.getStatusLine().getStatusCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(entity.getContent(), HTTP.UTF_8));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            newVerJSON.append(line + "\n");//按行读取放入StringBuilder中
                        }
                        reader.close();
                        VersionInfo versionInfo = checkVersionInfo(newVerJSON.toString());
                        if (versionInfo != null) {
                            versionInfo.msg = updateMsg(versionInfo);
                            updateAppInfo.checkVersionInfo(versionInfo);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private VersionInfo checkVersionInfo(String jsonVersion) {
            JSONObject jsonObject = null;
            VersionInfo versionInfo = new VersionInfo();
            try {
                jsonObject = new JSONObject(jsonVersion);
                versionInfo.desc = jsonObject.getString("desc");
                versionInfo.msg = jsonObject.getString("msg");
                versionInfo.status = jsonObject.getString("status");
                versionInfo.url = jsonObject.getString("url");
                versionInfo.ver = jsonObject.getString("ver");
                versionInfo.versionCode = getPackageInfo(context).versionCode;
                versionInfo.versionName = getPackageInfo(context).versionName;
                versionInfo.packageName = getPackageInfo(context).packageName;
                versionInfo.appName = getPackageInfo(context).applicationInfo.loadLabel(context.getPackageManager()).toString();
            } catch (JSONException e) {
                versionInfo = null;
                e.printStackTrace();
            }
            return versionInfo;
        }
    }

    /**
     * 获取当前App信息
     *
     * @param context
     * @return
     */
    public PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return info;
    }

    /**
     * 版本信息
     */
    public class VersionInfo {
        /**
         * 升级内容
         **/
        public String desc;
        /**
         * 升级提示
         **/
        public String msg;
        /**
         * 是否升级状态
         **/
        public String status;
        /**
         * 升级地址
         **/
        public String url;
        /**
         * 升级版本
         **/
        public String ver;
        /**
         * app版本编号
         **/
        public int versionCode;
        /**
         * app版本
         **/
        public String versionName;
        /**
         * app路径名
         **/
        public String packageName;
        /**
         * 下载名称
         **/
        public String appName;
        /**
         * 下载路径
         **/
        public String appPath;
    }

    /**
     * 升级接口回调
     */
    public interface IUpdateAppInfo {
        /**
         * 检查更新
         *
         * @param versionInfo 版本信息
         */
        public void checkVersionInfo(VersionInfo versionInfo);

        /**
         * 弹出对话框
         *
         * @param versionInfo 版本信息
         */
        public void dismissDiaLog(VersionInfo versionInfo);

        /**
         * 安装apk
         *
         * @param versionInfo 版本信息
         */
        public void installApk(VersionInfo versionInfo);
    }
//	{"desc":"必须更新","msg":"xxxx",
//			"status":3,
//			"url":"http://localhost:8080/backstage/apps/android_0.5.0_1436962983461_yongjinbao",
//			"ver":"0.5.0"}

    private String updateMsg(VersionInfo versionInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本：");
        sb.append(versionInfo.versionName);
        sb.append("\n");
        sb.append("发现新版本：");
        sb.append(versionInfo.ver);
        sb.append("\n");
        sb.append("内容:" + versionInfo.msg);
        sb.append("\n");
        sb.append("是否更新？");
        return sb.toString();
    }

    /**
     * 安装新的应用
     *
     * @param versionInfo 版本信息
     */
    public void installNewApk(final VersionInfo versionInfo) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(versionInfo.appPath);
            String[] command = {"chmod", "777", file.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 开始下载
     *
     * @param versionInfo 版本信息
     */
    public void downLoadApp(VersionInfo versionInfo) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
        Uri uri = Uri.parse(versionInfo.url);
        DownloadManager.Request request = new DownloadManager.Request(uri);//下载设置
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);//只能WIFI
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, versionInfo.appName + ".apk");//文件位置
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//Notification样式
        request.setTitle(versionInfo.appName);
        request.setMimeType("application/vnd.android.package-archive");
        downloadManager.enqueue(request);//放入队列
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);//添加Action
        DownLoadReceiver downLoadReceiver = new DownLoadReceiver(versionInfo, downloadManager);
        context.registerReceiver(downLoadReceiver, filter);//注册广播

    }

    //接受下载完成后的intent
    private class DownLoadReceiver extends BroadcastReceiver {
        private VersionInfo versionInfo;
        private DownloadManager downloadManager;

        public DownLoadReceiver(VersionInfo versionInfo, DownloadManager downloadManager) {
            this.versionInfo = versionInfo;
            this.downloadManager = downloadManager;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(this);
            //Log.i("OnReceive",intent.getAction() + "");
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager.Query downloadQuery = new DownloadManager.Query();
                downloadQuery.setFilterById(downId);
                Cursor cursor = downloadManager.query(downloadQuery);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int status = cursor.getInt(columnIndex);
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        int name = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                        String string = cursor.getString(name);
                        versionInfo.appPath = string;
                        updateAppInfo.installApk(versionInfo);
                    } else {
                        OutPutMessage.showToast(versionInfo.appName + ":下载失败,请稍后再试!");
                    }
                }
                cursor.close();
            }
        }
    }

}
