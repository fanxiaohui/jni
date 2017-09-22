package com.vision.eveline;

import com.smarthome.body.MDProtocol;
import com.smarthome.body.SocketProtocolFactory;
import com.smarthome.head.SmartHomeConstant;
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
    	SmartHomeData smartHoneData = new SmartHomeData();
    	smartHoneData.isFin = false;
        smartHoneData.isRead = true;
        smartHoneData.isACK = true;
        smartHoneData.dataFormat = SmartHomeConstant.BINARY;
        smartHoneData.keyLevel=0x00;
        smartHoneData.encryptType = SmartHomeConstant.Encrypt.ENCRYPT_TYPE_AES;
        smartHoneData.opcode = 0x05;
        smartHoneData.msgID = 0x01;
        smartHoneData.dataSequ = 0x00;
        smartHoneData.sequence = 0x01;
        smartHoneData.time = System.currentTimeMillis()/1000;
        smartHoneData.dstID = 11L;
        smartHoneData.wsDstID = 11L;

        smartHoneData.srcID = 1L;
        MDProtocol o = SocketProtocolFactory.gen0x11(true);
        smartHoneData.data =o.end; 
//        System.out.println(MiscUtils.toHex(o.end));
        smartHoneData.code = 0;
        smartHoneData.datID = 0;
        byte[] sendData = SmartHomeHead.addHead(smartHoneData, null, null);
        System.out.println(MiscUtils.toHex(sendData));
    }
}
