package com.hiparty.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hiparty.Utils.OkHttpUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String URLTEST = "";
    private EditText mAccountEdit;
    private EditText mPasswordEdit;
    private Button mLoginButton;

    private Callback mCallback = new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
            Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
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
                FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("account",account);
                builder.add("password",psw);
                Request request = new Request.Builder()
                        .url(URLTEST)
                        .post(builder.build())
                        .build();

                OkHttpUtils.enqueue(request,mCallback);
            }
        });
    }

    private void initView() {
        mAccountEdit = (EditText) findViewById(R.id.edit_account);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mPasswordEdit = (EditText) findViewById(R.id.edit_psw);
    }
}
