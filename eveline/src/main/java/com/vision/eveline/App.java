package com.vision.eveline;

import com.smarthome.body.MDProtocol;
import com.smarthome.body.SocketProtocolFactory;
import com.smarthome.head.SmartHomeData;
import com.smarthome.head.SmartHomeHead;
import com.vision.util.MiscUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws Exception
    {
    	System.out.println(System.getProperty("java.library.path"));
    	MDProtocol o = SocketProtocolFactory.gen0x11(true);
    	SmartHomeData smartHoneData = new SmartHomeData(1,(short) 1,1,-1,o.end);
//        System.out.println(MiscUtils.toHex(o.end));
        byte[] sendData = SmartHomeHead.addHead(smartHoneData, null, null);
        System.out.println("最终报文："+MiscUtils.toHex(sendData));
    }
}
