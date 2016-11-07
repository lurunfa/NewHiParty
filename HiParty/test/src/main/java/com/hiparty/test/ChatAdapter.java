package com.hiparty.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hiparty.Bean.ChatBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lurunfa on 2016/11/6.
 */

public class ChatAdapter extends BaseAdapter {

    private List<ChatBean> mDatas;
    private LayoutInflater mLayoutInflater;
    private String mUserId;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public ChatAdapter(Context context, String userId) {
        mDatas = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
        mUserId = userId;
    }

    public void addBean(ChatBean chatBean){
        if (chatBean !=null){
            mDatas.add(chatBean);
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

        return null;
    }
    private class ViewHolder{
        public TextView mTipTextView,mTimeTextView,mLeftTextView,mRightTextView;
        public FrameLayout mLeftFrameLayout,mRightFrameLayout;

        public ViewHolder(View view) {
            mTipTextView = (TextView) view.findViewById(R.id.tvTip);
            mTimeTextView= (TextView) view.findViewById(R.id.tvTime);
            mLeftTextView = (TextView) view.findViewById(R.id.tvLeft);
            mRightTextView = (TextView) view.findViewById(R.id.tvRight);
            mLeftFrameLayout = (FrameLayout) view.findViewById(R.id.framLeft);
            mRightFrameLayout = (FrameLayout) view.findViewById(R.id.framRight);
        }
        public void init(int position){
            ChatBean bean = mDatas.get(position);
            if (position == 0||position>0&&bean.time-mDatas.get(position-1).time > 60000){
                mTimeTextView.setVisibility(View.VISIBLE);
                mTimeTextView.setText(format.format(bean.time));
            }else {
                mTimeTextView.setVisibility(View.GONE);
            }
            mLeftFrameLayout.setVisibility(View.GONE);
            mRightFrameLayout.setVisibility(View.GONE);
            if (bean.content.equals("exit")){
                mTipTextView.setVisibility(View.VISIBLE);
                mTipTextView.setText(bean.name.equals(mUserId));
            }
        }
    }
}
