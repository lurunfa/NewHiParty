package com.hiparty.hiparty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hiparty.hiparty.Bean.Punishment;
import com.hiparty.hiparty.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * punishmentAdapter     2017-03-26
 * Copyright (c) 2017 Kevin L. All Rights Reserved.
 */

public class PunishmentAdapter extends BaseAdapter {

    private List<Punishment> mDatas;
    private LayoutInflater mLayoutInflater;
    private Map<Integer, Boolean> mStateMap = new HashMap<>();

    public PunishmentAdapter(Context context, List<Punishment> mDatas) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public void setData(Punishment punishment) {
        if (mDatas != null) {
            mDatas.add(punishment);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mDatas != null) {
            mDatas = new ArrayList<>();
            mStateMap = new HashMap<>();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Punishment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.lv_punishment, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.init(position);
        return convertView;
    }

    class ViewHolder {
        public TextView mTextView;
        public CheckBox mCheckBox;

        public ViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.lv_punishment_tv);
            mCheckBox = (CheckBox) view.findViewById(R.id.lv_punishment_checkbox);

        }

        public void init(final int position) {
            mTextView.setText(mDatas.get(position).getPunishment());
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mStateMap.clear();
                        mStateMap.put(position, isChecked);
                    } else {
                        mStateMap.remove(position);
                    }
                }
            });
        }
    }

    public int getSelect() {
        Set<Integer> integers = mStateMap.keySet();
        int index = -1;
        for (int i:integers){
            if (mStateMap.get(i)){
                index = i;
            }
        }
        return index;
    }
}
