package com.hiparty.hiparty.UI;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.hiparty.hiparty.Bean.Constant;
import com.hiparty.hiparty.MinaHandler.MinaHandler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * MinaService     2017-03-17
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class MinaService extends Service {
    private static final String TAG = "MinaService";
    private boolean isStart = false;
    private ConnectFuture cf=null;
    private IoConnector connector;
    private MinaHandler mMinaHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mMinaHandler = new MinaHandler();
        mMinaBinder.startConnect(Constant.ADDRESS,Constant.PORT,new Handler());
    }



    public MinaService() {

    }

    private MinaBinder mMinaBinder = new MinaBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mMinaBinder;
    }

    class MinaBinder extends Binder {
        public void startConnect(final String address, final int port, final Handler handler) {
            if (!isStart) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "服务启动");
                        connector = new NioSocketConnector();
                        connector.setConnectTimeoutMillis(30000);
                        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new
                                TextLineCodecFactory(Charset.forName("utf-8"))));
                        connector.setHandler(mMinaHandler);
                        cf = connector.connect(new InetSocketAddress(address,port));
                        cf.awaitUninterruptibly();

                    }
                }).start();
            }
        }

        public void sendMsg(String msg){
            if (cf.isConnected()){
                IoSession ioSession = cf.getSession();
                String encode = "";
                try {
                    encode = URLEncoder.encode(msg, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ioSession.write(encode);

            }else{
                Toast.makeText(MinaService.this,"没有链接",Toast.LENGTH_SHORT).show();
            }
        }

        public void setHandler(Handler handler){
            mMinaHandler.setHandler(handler);
        }

        public boolean isConnect(){
            return connector.isActive();
        }

        public void disconnnect(){
            if (cf.isConnected()){
                cf.cancel();
                connector.dispose();
            }
        }
    }
}
