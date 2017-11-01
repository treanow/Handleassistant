package com.letv.handleassistant.framework.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> itemList;

    public Context getContext() {
        return context;
    }

    public MyBaseAdapter(Context context) {
        this.context = context;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    /**
     * 添加一个集合
     *
     * @param itemList
     */
    public void addList(List<T> itemList) {
        if (this.itemList == null) {
            this.itemList = itemList;
        } else {
            this.itemList.addAll(itemList);
        }
    }

    /**
     * 添加一个item
     *
     * @param t
     */
    public void addItem(T t) {
        if (itemList == null) {
            itemList = new ArrayList<T>();
        } else {
            itemList.add(t);
        }
        notifyDataSetChanged();
    }

    public void reSetData(List<T> itemList) {
        setItemList(itemList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (itemList != null && itemList.size() > 0)
            return itemList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
