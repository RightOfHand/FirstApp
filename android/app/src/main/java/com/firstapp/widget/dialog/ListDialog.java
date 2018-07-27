package com.firstapp.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.firstapp.R;

import java.util.List;
/**
 *Description:
 *creator: song
 *Date: 2018/7/6 下午3:40
 */
public abstract class ListDialog<T> extends Dialog implements AdapterView.OnItemClickListener {

    private ListAdapter mAdapter = null;
    private Context mContext;

    protected ListDialog(Context context, List<T> list) {
        super(context, R.style.Dialog);
        this.mContext=context;
        init(list);
    }

    private void init(List<T> list) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.dialog_list, null,false);
        setContentView(root);
        ListView mList = (ListView) root.findViewById(R.id.dialog_item_detail);
        mAdapter = new ListAdapter(mContext, list);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onSelected(mAdapter.getItem(position), position);
        dismiss();
    }

    public void setTitle(String title){

    }

    public abstract void onSelected(T object, int position);

    public abstract String getLabel(T object);

    private class ListAdapter extends BaseAdapter {
        private List<T> list = null;
        private Context context = null;

        private ListAdapter(Context context, List<T> list) {
            this.list = list;
            this.context = context;
        }

        public void setList(List<T> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public T getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            T object = getItem(position);
            String label = getLabel(object);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_simple_text, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.text)).setText(label);
            return convertView;
        }

    }


}
