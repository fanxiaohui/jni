package com.vision.smarthomeapi.net;


import com.vision.smarthomeapi.net.http.RequestParams;

import java.util.Arrays;

/**
 * Created by Lal on 2015/1/28.
 */
public class NetworkMessage {
    /**
     * 报文类型，true需要发送
     */
    private boolean isSend;

    /**
     * 二进制报文
     */
    private byte[] binaryMsg;

    /**
     * HTTP请求参数
     */
    private RequestParams params;

    /**
     * 设备MAC地址
     */
    private String mac;

    /**
     * http接口地址
     */
    private String uri;

    /**
     * 报文通道
     */
    private int netWorkType;

    /**
     * ip
     */
    private String ip;

    /**
     * 请求头
     */
    private String cookie;

    private String httpUrl;
    /**
     * http是否是get通道
     */
    private boolean httpGet;

    /**
     * 当前请求的token
     */
    private String token;

//    public enum MsgType
//    {
//        MSG_TYPE_UDP,
//        MSG_TYPE_TCP,
//        MSG_TYPE_HTTP,
//        MSG_TYPE_WEBSOCKET,
//        MSG_TYPE_ZIGBEE;
//    }

    /**
     * 构建报文包装类
     *
     * @param isSend    是否是发送指令
     * @param binaryMsg 二进制报文(HTTP发送报文不会使用此参数请使用 params参数)
     * @param mac       设备MAC
     * @param ip        设备ip地址
     */
    public NetworkMessage(boolean isSend, byte[] binaryMsg, String mac, int netWorkType, String ip) {
        this.isSend = isSend;
        this.binaryMsg = binaryMsg;
        this.mac = mac;
        this.netWorkType = netWorkType;
        this.ip = ip;
    }

    /**
     * 构建报文包装类
     *
     * @param binaryMsg 二进制报文(HTTP发送报文不会使用此参数请使用 params参数)
     * @param uri       地址路径（不包含服务地址）
     */
    public NetworkMessage(byte[] binaryMsg, String uri) {
        this.binaryMsg = binaryMsg;
        this.uri = uri;

    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean isSend) {
        this.isSend = isSend;
    }

    public byte[] getBinaryMsg() {
        return binaryMsg;
    }

    public void setBinaryMsg(byte[] binaryMsg) {
        this.binaryMsg = binaryMsg;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getNetWorkType() {
        return netWorkType;
    }

    public boolean isHttpGet() {
        return httpGet;
    }

    public void setHttpGet(boolean httpGet) {
        this.httpGet = httpGet;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "NetworkMessage{" +
                "isSend=" + isSend +
                ", binaryMsg=" + Arrays.toString(binaryMsg) +
                ", params=" + params +
                ", mac='" + mac + '\'' +
                ", uri='" + uri + '\'' +
                //      ", mMsgType=" + mMsgType +
                ", ip='" + ip + '\'' +
                '}';
    }
}
