package com.wfkj.android.smartoffice.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.adapter.JournalInsideAdapter;
import com.wfkj.android.smartoffice.util.view_util.CustomListView;

/**
 * Created by wangdongyang on 17/2/15.
 */
public class NewJournalAdapter extends RecyclerView.Adapter<NewJournalAdapter.MyViewHolder> {

    private Context context;

    public NewJournalAdapter(Context context) {
        this.context = context;
    }

    @Override
    public NewJournalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_journal, null);
        MyViewHolder hodler = new MyViewHolder(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        JournalInsideAdapter adapter = new JournalInsideAdapter(context);
        holder.item_journal_listview.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_journal_day_txt;
        TextView item_journal_month_txt;
        TextView item_journal_week_txt;
        CustomListView item_journal_listview;


        public MyViewHolder(View view) {
            super(view);
            item_journal_day_txt = (TextView) view.findViewById(R.id.item_journal_day_txt);
            item_journal_month_txt = (TextView) view.findViewById(R.id.item_journal_month_txt);
            item_journal_week_txt = (TextView) view.findViewById(R.id.item_journal_week_txt);
            item_journal_listview = (CustomListView) view.findViewById(R.id.item_journal_listview);
        }
    }
}
