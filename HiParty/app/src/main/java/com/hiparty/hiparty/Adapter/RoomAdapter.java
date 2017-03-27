package com.hiparty.hiparty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiparty.hiparty.Bean.BeanLab;
import com.hiparty.hiparty.Bean.Chater;
import com.hiparty.hiparty.R;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * RoomAdapter     2017-03-25
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class RoomAdapter extends BaseAdapter {

    private List<Chater> mDatas;
    private String mUserId;
    private LayoutInflater mLayoutInflater;

    public RoomAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = BeanLab.getBeanLab().getChaters();
        mUserId = BeanLab.getBeanLab().getUserId();
    }

    public void addChater(Chater chater) {
        if (chater != null) {
            Logger.d("add");
            Logger.d(chater.toString());
            mDatas.add(chater);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.dialog, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.init(position);
        return convertView;
    }

    class ViewHolder {
        public TextView tvRight, tvLeft, tvTime, tvUser;
        public LinearLayout leftLayout, rightLayout, midLayout;
        public ImageView mImageViewLeft,mImageViewRight;

        public ViewHolder(View view) {
            tvRight = (TextView) view.findViewById(R.id.dialog_tv_usernews);
            tvLeft = (TextView) view.findViewById(R.id.dialog_tv_sysnews);
            tvTime = (TextView) view.findViewById(R.id.dialog_tv_time);
            tvUser = (TextView) view.findViewById(R.id.dialog_tv_prompt);

            tvRight.setBackground(view.getResources().getDrawable(R.drawable.dialog_right));
            tvLeft.setBackground(view.getResources().getDrawable(R.drawable.dialog_left));

            leftLayout = (LinearLayout) view.findViewById(R.id.dialog_framLeft);
            midLayout = (LinearLayout) view.findViewById(R.id.dialog_framMid);
            rightLayout = (LinearLayout) view.findViewById(R.id.dialog_framRight);

            mImageViewLeft = (ImageView) view.findViewById(R.id.dialog_im_sys);
            mImageViewRight = (ImageView) view.findViewById(R.id.dialog_im_user);
        }

        public void init(int position) {
            mImageViewLeft.setImageResource(R.mipmap.ic_launcher);
            mImageViewRight.setImageResource(R.mipmap.ic_launcher);
            Logger.d("init");
            Chater chater = mDatas.get(position);
            if (position == 0) {
                midLayout.setVisibility(View.VISIBLE);
                tvTime.setText(chater.getDate());
            } else {
                midLayout.setVisibility(View.GONE);
            }
            leftLayout.setVisibility(View.GONE);
            rightLayout.setVisibility(View.GONE);

            if (chater.getOrder().equals("out")) {
                midLayout.setVisibility(View.VISIBLE);
                tvUser.setVisibility(View.VISIBLE);
                tvUser.setText(chater.getUserId().equals(mUserId) ? "你已经退出房间" : chater.getMessage
                        ());
            } else if (chater.getOrder().equals("talk_in")) {
                midLayout.setVisibility(View.VISIBLE);
                tvUser.setVisibility(View.VISIBLE);
                tvUser.setText(chater.getUserId().equals(mUserId) ? "你已经加入了房间" : chater
                        .getMessage());
            } else if (chater.getOrder().equals("rank")) {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                leftLayout.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                Logger.d("msg"+chater.getOrder());
                Map<String, Object> map = (Map<String, Object>) chater.getObject();
                String rankMessage = (String) map.get("rank");
                tvLeft.setText(rankMessage);
            }  else if (chater.getOrder().equals("ensure_punishment")) {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                leftLayout.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                Logger.d("msg"+chater.getOrder());
                Map<String, Object> map = (Map<String, Object>) chater.getObject();
                String rankMessage = (String) map.get("punishment");
                tvLeft.setText(rankMessage);
            } else {
                midLayout.setVisibility(View.GONE);
                tvUser.setVisibility(View.GONE);
                tvTime.setVisibility(View.GONE);

                if (chater.getUserId().equals(mUserId)) {
                    rightLayout.setVisibility(View.VISIBLE);
                    tvRight.setVisibility(View.VISIBLE);
                    tvRight.setText(chater.getMessage());
                } else {
                    leftLayout.setVisibility(View.VISIBLE);
                    tvLeft.setVisibility(View.VISIBLE);
                    tvLeft.setText(chater.getMessage());
                }
            }
        }
    }
}
