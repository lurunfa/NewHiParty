package com.hiparty.hiparty.Service;

import android.content.Context;
import android.view.View;

/**
 * ShiftListener     2017-03-25
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public interface ShiftListener {
    View.OnClickListener getHallListener(Context context);
    View.OnClickListener getRoomListener(Context context);
    View.OnClickListener getModuelListener(Context context);
    View.OnClickListener getMyListener(Context context);
}
