package com.hiparty.socket;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hiparty.Bean.ChatBean;
import com.hiparty.Interface.ChatView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by lurunfa on 2016/11/5.
 */

public class ChatSocketThread extends Thread {



    private Socket mSocket;
    private BufferedReader in ;
    private ChatView mChatView;
    private PrintWriter out;

    public ChatSocketThread(ChatView chatView) {
        mChatView = chatView;
    }

    private void init(){
        try{
            mSocket = new Socket(mChatView.getHost(), Integer.parseInt(mChatView.getProt()));
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(mSocket.getOutputStream()),true);
            if (!mSocket.isInputShutdown()){
                ChatBean bean = new ChatBean("join",mChatView.getUserId());
                out.println(new Gson().toJson(bean));
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        ChatBean bean = new ChatBean(msg,mChatView.getUserId());
        if (!TextUtils.isEmpty(msg)&&mSocket.isConnected()&&mSocket != null){
            if (!mSocket.isInputShutdown()){
                out.println(new Gson().toJson(bean));
            }
        }

    }

    public Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String content = (String) msg.obj;
                    ChatBean chatBean = new Gson().fromJson(content,ChatBean.class);
                    mChatView.receiveMsg(chatBean);
                    break;

            }
        }
    };
    @Override
    public void run() {
        super.run();
        init();
        try{
            while (true){
                if (!mSocket.isClosed()){
                    if (mSocket.isConnected()){
                        if (!mSocket.isInputShutdown()){
                            String content = null;
                            if ((content =in.readLine())!=null){
                                content +="\n";
                                Message message = new Message();
                                message.what = 1;
                                message.obj = content;
                                mHandler.sendMessage(message);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
