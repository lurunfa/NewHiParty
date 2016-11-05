package com.hiparty.Bean;

/**
 * Created by lurunfa on 2016/11/5.
 */

public class ChatBean {
    public String content;
    public String name;
    public long time;

    public ChatBean(String content, String name) {
        this.content = content;
        this.name = name;
        time = System.currentTimeMillis();
    }
}
