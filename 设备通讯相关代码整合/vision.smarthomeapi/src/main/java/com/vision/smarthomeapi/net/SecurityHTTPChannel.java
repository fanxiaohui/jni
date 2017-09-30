package com.vision.smarthomeapi.net;

import android.database.Observable;

import com.vision.smarthomeapi.bean.RBean;
import com.vision.smarthomeapi.bean.RSecurityUpdateCode;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.net.http.AsyncHttpClient;
import com.vision.smarthomeapi.net.http.AsyncHttpResponseHandler;
import com.vision.smarthomeapi.net.http.RequestParams;
import com.vision.smarthomeapi.util.JsonUtil;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;

import org.apache.http.Header;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * http通道
 * Created by yangle on 2015/12/10.
 */
public class SecurityHTTPChannel {

    /**
     * 使用http发送消息
     * get方式
     */
    public static void sendMessageGet(final String urlOrigin, RequestParams requestParams, final String token) {
        //获取客户端
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        OutPutMessage.LogCatInfo("http发送", Controller.SERVER_URL + urlOrigin + "?" + requestParams.toString());

        asyncHttpClient.get(Controller.SERVER_URL + urlOrigin, requestParams,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        OutPutMessage.LogCatInfo("http接收", "返回码:" + statusCode);
                        if (statusCode == 200) {
                            try {
                                String content = new String(responseBody, "utf-8");
                                //解析Http返回内容
                                OutPutMessage.LogCatInfo("http接收", "返回内容:" + content);
                                NetworkMessage networkMessage = new NetworkMessage(responseBody, urlOrigin);
                                //为避免请求的token与当前token不一致频繁刷新的问题
                                if (token != null) {
                                    if (SecurityUserManage.token != null && token.equals(SecurityUserManage.token)) {
                                        SecurityUserManage.getShare().httpResponse(networkMessage);
                                    }
                                } else {
                                    SecurityUserManage.getShare().httpResponse(networkMessage);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            OutPutMessage.showToast("网络异常");
                            OutPutMessage.LogCatInfo("http接收", "网络访问失败___statusCode：" + statusCode + "___urlOrigin：" + urlOrigin);
                            NotificationManager.defaultManager().postNotification(Constant.NotificationType.SECURITY_HTTP_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        OutPutMessage.showToast("网络异常");
                        OutPutMessage.LogCatInfo("http接收", "网络访问失败___statusCode：" + statusCode + "___urlOrigin：" + urlOrigin);
                        NotificationManager.defaultManager().postNotification(Constant.NotificationType.SECURITY_HTTP_ERROR);
                    }
                });
    }

    /**
     * 使用http发送消息
     * post方式
     */
    public static void sendMessagePost(final String urlOrigin, RequestParams requestParams, final String token) {
        //获取客户端
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        OutPutMessage.LogCatInfo("http发送", Controller.SERVER_URL + urlOrigin + "?" + requestParams.toString());

        asyncHttpClient.post(Controller.SERVER_URL + urlOrigin, requestParams,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        OutPutMessage.LogCatInfo("http接收", "返回码:" + statusCode);
                        if (statusCode == 200) {
                            try {
                                String content = new String(responseBody, "utf-8");
                                //解析Http返回内容
                                OutPutMessage.LogCatInfo("http接收", "返回内容:" + content);
                                NetworkMessage networkMessage = new NetworkMessage(responseBody, urlOrigin);
                                if (token != null) {
                                    if (SecurityUserManage.token != null && token.equals(SecurityUserManage.token)) {
                                        SecurityUserManage.getShare().httpResponse(networkMessage);
                                    }
                                } else {
                                    SecurityUserManage.getShare().httpResponse(networkMessage);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            OutPutMessage.LogCatInfo("http接收", "网络访问失败___statusCode：" + statusCode + "___urlOrigin：" + urlOrigin);
                            OutPutMessage.showToast("网络异常");
                            NotificationManager.defaultManager().postNotification(Constant.NotificationType.SECURITY_HTTP_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        OutPutMessage.LogCatInfo("http接收", "网络访问失败___statusCode：" + statusCode + "___urlOrigin：" + urlOrigin);
                        OutPutMessage.showToast("网络异常");
                        NotificationManager.defaultManager().postNotification(Constant.NotificationType.SECURITY_HTTP_ERROR);
                    }
                });
    }

    /**
     * 上传文件
     *
     * @param urlOrigin 网络访问接口
     * @param token     访问令牌
     * @param file      文件
     */
    public static void fileUpload(final String urlOrigin, String token, File file) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"token\""),
                        RequestBody.create(null, token))
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"uploadFile\"; filename=\"" + file.getName() + "\""), fileBody)
                .build();

        final Request request = new Request.Builder()
                .url(Controller.SERVER_URL + urlOrigin)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OutPutMessage.LogCatInfo("http接收", "文件上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                String content = reader.readLine();
                reader.close();
                OutPutMessage.LogCatInfo("http接收", "文件上传：" + content);

                RBean rBean = JsonUtil.fromJsonString(content, RBean.class);
                RSecurityUpdateCode rSecurityUpdateCode = JsonUtil.fromJsonString(
                        JsonUtil.toJsonString(rBean.getData()), RSecurityUpdateCode.class);
                NotificationManager.defaultManager().postNotification(
                        Constant.NotificationType.SECURITY_UPDATE_CODE, null, rSecurityUpdateCode, rBean);
            }
        });
    }


//    public static void okhttp(String urlOrigin, MultipartBody requestBody, final String notificationName) {
//
//        OkHttpClient client = new OkHttpClient();
//
//        //构建请求
//        Request request = new Request.Builder()
//                .url(Controller.SERVER_URL + urlOrigin)//地址
//                .post(requestBody)//添加请求体
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
//                String content = reader.readLine();
//                reader.close();
//                OutPutMessage.LogCatInfo("http接收", "文件上传：" + content);
//
//                RBean rBean = JsonUtil.fromJsonString(content, RBean.class);
//                NotificationManager.defaultManager().postNotification(
//                        notificationName, null, null, rBean);
//
//            }
//        });
//
//    }


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private static List<File> file = new ArrayList<>();

    /**
     * 上传多张图片及参数
     *
     * @param reqUrl  URL地址
     * @param params  参数
     * @param pic_key 上传图片的关键字
     * @param files   图片路径
     */
    public static void sendMultipart(String reqUrl, Map<String, String> params, String pic_key, List<File> files, final String notificationName) {

        OkHttpClient client = new OkHttpClient();


        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);

        file = files;
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (files != null) {
            for (File file : files) {
                multipartBodyBuilder.addFormDataPart(pic_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();

        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(Controller.SERVER_URL + reqUrl);// 添加URL地址
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                String content = reader.readLine();
                reader.close();
                OutPutMessage.LogCatInfo("http接收", "文件上传：" + content);

                RBean rBean = JsonUtil.fromJsonString(content, RBean.class);
                NotificationManager.defaultManager().postNotification(
                        notificationName, null, null, rBean);


                call.cancel();
            }
        });
    }
}
