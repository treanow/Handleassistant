package com.letv.handleassistant.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letv.handleassistant.framework.bean.SubBaseResponse;


public class CommonAdapter extends MyBaseAdapter<SubBaseResponse> {
    private Context mContext;
    private LayoutInflater mInflater;

    public CommonAdapter(Context context) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
//			convertView = mInflater.inflate(R.layout.lv_item_dietlist, null);
//			viewHolder.top_date = (TextView) convertView
//					.findViewById(R.id.top_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView top_date;
    }

}
