package com.vision.smarthomeapi.util;

/**
 * Created by zhanglong on 2016/1/22.
 */
public class NotificationException extends RuntimeException {

    /**
     * Constructor of NotificationException.
     *
     * @param errorMessage
     *            the description of this NotificationException.
     */
    public NotificationException(String errorMessage) {
        super(errorMessage);
    }

}
