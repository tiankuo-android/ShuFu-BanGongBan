package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.util.view_util.CustomListView;

/**
 * Created by wangdongyang on 17/2/13.
 */
public class JournalAdapter extends BaseAdapter {

    private Context context;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_journal, null);
            holder.item_journal_day_txt = (TextView) convertView.findViewById(R.id.item_journal_day_txt);
            holder.item_journal_month_txt = (TextView) convertView.findViewById(R.id.item_journal_month_txt);
            holder.item_journal_week_txt = (TextView) convertView.findViewById(R.id.item_journal_week_txt);
            holder.item_journal_listview = (CustomListView) convertView.findViewById(R.id.item_journal_listview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder {

        TextView item_journal_day_txt;
        TextView item_journal_month_txt;
        TextView item_journal_week_txt;
        CustomListView item_journal_listview;

    }

}
