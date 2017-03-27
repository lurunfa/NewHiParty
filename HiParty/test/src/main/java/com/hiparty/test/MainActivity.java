package com.hiparty.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String URLTEST = "http://10.110.210.165:8080/spring_login/user/register";
    private EditText mAccountEdit;
    private EditText mPasswordEdit;
    private Button mLoginButton;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String result = (String) msg.obj;
                    Logger.i(result);
                    Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();

            }
        }
    };
    private Callback mCallback = new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
          //  Logger.i("TAG", response.body().string());
//            Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
            String result = response.body().string();
            Message message = new Message();
            message.what = 1;
            message.obj = result;
            mHandler.sendMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccountEdit.getText().toString();
                String psw = mPasswordEdit.getText().toString();
                JSONObject jsonObject = new JSONObject();
                
                try {
                    jsonObject.put("name",account);
                    jsonObject.put("psw",psw);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String json = jsonObject.toString();
                Logger.i(json);
                FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("json", json);
               // builder.add("password", psw);
                Request request = new Request.Builder()
                        .url(URLTEST)
                        .post(builder.build())
                        .build();
                Logger.i(request.body().toString());

                // OkHttpUtils.enqueue(request,mCallback);
                mOkHttpClient.newCall(request).enqueue(mCallback);
            }
        });
    }

    private void initView() {
        mAccountEdit = (EditText) findViewById(R.id.edit_account);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mPasswordEdit = (EditText) findViewById(R.id.edit_psw);
    }
}
