package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.OutPutMessage;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityLogin extends Bean{
    private String token;
    private String userName;
    private String password;
    private String loginIp;

    public CSecurityLogin(String token,String userName,String password){

        this.token = token;
        this.userName = userName;
        this.password = password;
        this.loginIp = getLocalHostIp();
        this.urlOrigin = Constant.UrlOrigin.security_login;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLocalHostIp()
    {
        String ipaddress = "";
        try
        {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements())
            {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements())
                {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ip
                            .getHostAddress()))
                    {
                        ipaddress = ip.getHostAddress();
                        OutPutMessage.LogCatInfo("ip","本机的ip地址是："+ipaddress);
                        return ipaddress;
                    }
                }

            }
        }
        catch (SocketException e)
        {
            OutPutMessage.LogCatInfo("ip", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;

    }
}
