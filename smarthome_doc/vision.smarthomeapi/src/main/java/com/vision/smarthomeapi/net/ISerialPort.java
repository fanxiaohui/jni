package com.vision.smarthomeapi.net;

public interface ISerialPort {

    public void onDataReceived(final byte[] buffer, final int size);

    public void onDataDidSend(final byte[] buffer, final int size);

    public void onDataReceivedError(Exception e);

    public void onDataSendError(Exception e);

}
