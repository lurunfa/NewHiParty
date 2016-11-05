package com.hiparty.Interface;

import com.hiparty.Bean.ChatBean;

/**
 * Created by lurunfa on 2016/11/5.
 */

public interface ChatView {
    public String getHost();
    public String getProt();
    public String getUserId();
    public void showDiaolg(String msg);
    public void receiveMsg(ChatBean bean);
}
