package com.hiparty.hiparty.Bean;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanLab     2017-03-19
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 * 单例，保存数据
 */

public class BeanLab {
    private String userId;
    private volatile static BeanLab sBeanLab;
    private BeanLab(){}
    private Map<String,Object> saveMap = new HashMap<>();
    private boolean isInRoom =false;
    private Constant.ShitTypeEnum state;
    private Constant.ConnectTypeEnum mConnectTypeEnum;
    private Handler mHandler;
    private List<Chater> mChaters = new ArrayList<>();

    public static BeanLab getBeanLab(){
        if (sBeanLab == null){
            synchronized (BeanLab.class){
                sBeanLab = new BeanLab();
            }
        }
        return sBeanLab;
    }
    public Map<String,Object> getSaveMap(){
       return saveMap;
    }
    public Object getFromMap(String key){
        return saveMap.get(key);
    }
    public void setAttribute(String key,String value){
       saveMap.put(key,value);
    }
    public void clearMap(){
        saveMap = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isInRoom() {
        return isInRoom;
    }

    public void setInRoom(boolean inRoom) {
        isInRoom = inRoom;
    }

    public Constant.ShitTypeEnum getState() {
        return state;
    }

    public void setState(Constant.ShitTypeEnum state) {
        this.state = state;
    }

    public Constant.ConnectTypeEnum getConnectTypeEnum() {
        return mConnectTypeEnum;
    }

    public void setConnectTypeEnum(Constant.ConnectTypeEnum connectTypeEnum) {
        mConnectTypeEnum = connectTypeEnum;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    public List<Chater> getChaters() {
        return mChaters;
    }

    public void setChaters(List<Chater> chaters) {
        mChaters = chaters;
    }
}
