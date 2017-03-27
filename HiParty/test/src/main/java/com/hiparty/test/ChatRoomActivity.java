package com.hiparty.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hiparty.Bean.ChatBean;
import com.hiparty.Interface.ChatView;
import com.hiparty.socket.ChatSocketThread;

/**
 * Created by lurunfa on 2016/11/6.
 */

public class ChatRoomActivity extends Activity implements ChatView{

    private ChatAdapter mChatAdapter;
    private String host;
    private String port;

    private String userId = String.valueOf(System.currentTimeMillis());
    private ChatSocketThread mSocketThread;

    private ListView mListView;
    private EditText mEditText;
    private Button mButton;


    public static void actionStart(Context context,String host,String port){
        Intent intent = new Intent(context,ChatRoomActivity.class);
        intent.putExtra("host",host);
        intent.putExtra("port",port);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_view);
        initView();

        Intent intent = getIntent();
        host = intent.getStringExtra("host");
        port = intent.getStringExtra("port");

        mChatAdapter = new ChatAdapter(this,userId);
        mListView.setAdapter(mChatAdapter);

        mSocketThread = new ChatSocketThread(this);
        mSocketThread.start();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_content);
        mListView = (ListView) findViewById(R.id.list_view);
        mButton = (Button) findViewById(R.id.btn_send);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mEditText.getText().toString();
                mEditText.setText("");
                mSocketThread.sendMsg(msg);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocketThread.sendMsg("exit");
        mSocketThread.interrupt();
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getProt() {
        return port;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void showDiaolg(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        }).show();
    }

    @Override
    public void receiveMsg(ChatBean bean) {
        mChatAdapter.addBean(bean);
    }
}
