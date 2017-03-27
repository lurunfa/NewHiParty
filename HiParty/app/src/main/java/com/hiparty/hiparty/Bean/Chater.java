package com.hiparty.hiparty.Bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Chater     2017-03-15
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class Chater implements Serializable{
    private String message ;
    private String order;
    private String date;
    private Object object;
    private String userId;
    private String roomId;



    public Chater(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.format(new Date());
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }



    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Chater{" +
                "message='" + message + '\'' +
                ", order='" + order + '\'' +
                ", date='" + date + '\'' +
                ", object=" + object +
                ", userId='" + userId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
