package com.hiparty.Utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by lurunfa on 2016/11/5.
 */

public class ChatUtils {
    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(Context context,@StringRes int msgId) {
        Toast.makeText(context, context.getText(msgId), Toast.LENGTH_SHORT).show();
    }
}
