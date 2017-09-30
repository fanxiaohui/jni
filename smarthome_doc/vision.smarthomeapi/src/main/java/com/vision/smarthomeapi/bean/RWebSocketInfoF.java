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
public class RWebSocketInfoF extends Bean {

    private RWebSocketInfo data;

    public RWebSocketInfoF(RWebSocketInfo data) {
        this.data = data;


    }

    public RWebSocketInfo getData() {
        return data;
    }

    public void setData(RWebSocketInfo data) {
        this.data = data;
    }
}

