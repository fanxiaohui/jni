package com.vision.smarthomeapi.net;

import com.vision.smarthomeapi.util.OutPutMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android_serialport_api.SerialPort;


public class SerialPortManager {
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private SendThread mSendThread;
    private ISerialPort mISerialPort;
    private BlockingQueue<byte[]> mMsesageList;
    private ArrayList<Byte> cacheList;
    private int readStatus = 0;
    private static long lastSendTime;

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[256];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    int next = -1;
                    if (size > 0) {

                        for (int i = 0; i < buffer.length; i++) {
                            byte b = buffer[i];
                            if (readStatus == 2) {
                                next = -1;
                                readStatus = 0;
                                int l = b & 0x00ff;
                                l += 3;
                                byte[] temp = new byte[l];
                                System.arraycopy(buffer, i - 2, temp, 0, l);
                                if (mISerialPort != null)
                                    mISerialPort.onDataReceived(temp, l);
                                i++;
                                i += l;
                                continue;
                            }
                            if (b == (byte) 0x6d) {
                                readStatus = 1;
                                next = i + 1;
                                continue;
                            }
                            if (i == next) {
                                next = -1;
                                if (readStatus == 1 && b == (byte) 0x64) {
                                    readStatus = 2;
                                    continue;
                                } else {
                                    readStatus = 0;
                                }
                            }

                        }

                    }
                } catch (IOException e) {
                    mISerialPort.onDataReceivedError(e);
                    return;
                }
            }
        }
    }

    private class SendThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                byte[] data = null;
                try {
                    data = mMsesageList.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (data == null) {
                    doWait();
                    continue;
                }
                if (Math.abs(System.currentTimeMillis() - lastSendTime) > (60 * 1000)) {
                    //发送唤醒指令
                    try {
                        mOutputStream.write(0x55);
                        OutPutMessage.LogCatInfo("唤醒", "发送唤醒---0x55");
                        sleep(50);
                        OutPutMessage.LogCatInfo("唤醒", "发送唤醒---sleep 50");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lastSendTime = System.currentTimeMillis();

                try {
                    mOutputStream.write(data);
                    mISerialPort.onDataDidSend(data, data.length);
                } catch (IOException e) {
                    mISerialPort.onDataSendError(e);
                }
                mMsesageList.remove(0);
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void doWait() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void doNotify() {
            notify();
        }

    }

    public SerialPortManager(ISerialPort serialPort) {
        this.mISerialPort = serialPort;
    }

    public void open() {
        try {
            if (mSerialPort == null) {
                /* Open the serial port */
                mSerialPort = new SerialPort(new File("/dev/tty_zigbee"), 115200, 0);
            }
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mMsesageList = new ArrayBlockingQueue<byte[]>(10, true);

            cacheList = new ArrayList<>();

            mReadThread = new ReadThread();
            mReadThread.start();
            mSendThread = new SendThread();
            mSendThread.start();


        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void closeSerialPort() {
        if (mReadThread != null)
            mReadThread.interrupt();
        mSerialPort = null;
        if (mSendThread != null)
            mSendThread.interrupt();
        mSendThread = null;
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
        this.mISerialPort = null;
        System.gc();

    }

    public void send(byte[] data) {


        boolean needNotify = false;
        if (mMsesageList == null || mMsesageList.size() <= 0) {
            if (mMsesageList == null)
                mMsesageList = new ArrayBlockingQueue<byte[]>(10, true);
            needNotify = true;
        }
        try {
            mMsesageList.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (needNotify)
            mSendThread.doNotify();

    }

    public String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;

        }
        return hex.toUpperCase(Locale.getDefault());

    }

    public String byteArraytoString(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(byteToHex(array[i]));
            if ((i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
