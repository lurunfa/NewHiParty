package com.hiparty.hiparty.MinaHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hiparty.hiparty.Bean.BeanLab;
import com.hiparty.hiparty.Bean.Chater;
import com.hiparty.hiparty.Bean.Constant;
import com.hiparty.hiparty.Utils.Json2Chater;
import com.orhanobut.logger.Logger;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**
 * MinaHandler     2017-03-17
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class MinaHandler extends IoHandlerAdapter {
    private final static String TAG = "MinaHandler";
    private Handler mHandler;

    public void setHandler(Handler handler) {
        mHandler = handler;
    }


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Logger.i(session.getServiceAddress() + "服务器连接已经创建");
        BeanLab.getBeanLab().setConnectTypeEnum(Constant.ConnectTypeEnum.CONNECT);
    }


    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Logger.i(session.getServiceAddress() + "服务器连接已经关闭");
        BeanLab.getBeanLab().setConnectTypeEnum(Constant.ConnectTypeEnum.DISCONNECT);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Logger.d(message.toString());
        Message msg = new Message();
        Chater chater = Json2Chater.json2Chater(message.toString());
        if (chater != null) {
            if (chater.getOrder().equals("talk") || chater.getOrder().equals("talk_in") || chater
                    .equals("talk_out")) {
                handlerTalk(chater, session, msg);
            } else {
                switch (chater.getMessage()) {
                    case Constant.SUCCEED:
                        if (chater.getOrder().equals(Constant.Order.create.name())) {
                            handlerCreate(chater, session, msg);
                        } else if (chater.getOrder().equals(Constant.Order.in.name())) {
                            handlerIn(chater, session, msg);
                        }else if (chater.getOrder().equals(Constant.Order.punishment.name())){
                            Logger.d("punishment");
                            handlerPunishment(chater, session, msg);
                        }else if (chater.getOrder().equals(Constant.Order.ensure_punishment.name())){
                            handlerEnsurePunishment(chater, session, msg);
                        }else if (chater.getOrder().equals(Constant.Order.rank.name())){
                            handlerRank(chater, session, msg);
                        }
                        break;

                    default:
                        Logger.e("错误");
                        Logger.e(chater.getMessage());
                        break;
                }
            }
        }
    }

    private void handlerRank(Chater chater, IoSession session, Message msg) {
        msg.what = Constant.Order.rank.getIndex();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chater", chater);
        msg.obj = bundle;
        if (mHandler != null) {
            BeanLab.getBeanLab().getHandler().sendMessage(msg);
        }
    }

    private void handlerEnsurePunishment(Chater chater, IoSession session, Message msg) {
        msg.what = Constant.Order.ensure_punishment.getIndex();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chater", chater);
        msg.obj = bundle;
        if (mHandler != null) {
            BeanLab.getBeanLab().getHandler().sendMessage(msg);
        }
    }

    private void handlerPunishment(Chater chater, IoSession session, Message msg) {
        msg.what = Constant.Order.punishment.getIndex();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chater", chater);
        msg.obj = bundle;
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    private void handlerTalk(Chater chater, IoSession session, Message msg) {

        msg.what = Constant.Order.talk.getIndex();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chater", chater);
        msg.obj = bundle;
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    private void handlerIn(Chater chater, IoSession session, Message msg) {

        msg.what = Constant.Order.in.getIndex();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chater", chater);
        msg.obj = bundle;
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    private void handlerCreate(Chater chater, IoSession session, Message msg) {
        msg.what = Constant.Order.create.getIndex();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chater", chater);
        msg.obj = bundle;
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Logger.i("本地发送消息：" + message.toString());
    }
}
