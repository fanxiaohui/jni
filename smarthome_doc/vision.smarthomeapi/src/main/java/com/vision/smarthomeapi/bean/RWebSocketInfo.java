package com.vision.smarthomeapi.bean;

/**
 * 类的名称:RUserLogin</br>
 * 主要功能描述:用户登录</br>
 * 创建日期:2014-3-19
 *
 * @author ZhaoQing
 *         <table>
 *         <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
 *         <tr><td>0.1</td><td>2014-3-19</td><td>ZhaoQing</td><td>初始创建</td>
 *         </table>
 */
public class RWebSocketInfo extends Bean {

    private String proxyAddr;
    private String proxyKey;
    private String heartBeat;

    public RWebSocketInfo(String websocketAddr, String proxyKey, String heartBeat) {
        super();
        this.proxyAddr = websocketAddr;
        this.proxyKey = proxyKey;
        this.heartBeat = heartBeat;

    }

    public String getProxyKey() {
        return proxyKey;
    }

    public void setProxyKey(String proxyKey) {
        this.proxyKey = proxyKey;
    }

    public String getProxyAddr() {
        return proxyAddr;
    }

    public void setProxyAddr(String proxyAddr) {
        this.proxyAddr = proxyAddr;
    }

    public String getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        this.heartBeat = heartBeat;
    }

}

