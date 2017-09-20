package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wfkj.android.smartoffice.R;

/**
 * 日志详情
 * Created by wangdongyang on 17/2/13.
 */
public class JournalActivity extends Activity implements OnClickListener {

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;
    private XRecyclerView journal_list;
    private NewJournalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        init();
    }

    private void init() {

        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        journal_list = (XRecyclerView) this.findViewById(R.id.journal_list);


        head_title.setText("日志详情");
        head_img.setVisibility(View.GONE);
        head_back.setOnClickListener(this);

        adapter = new NewJournalAdapter(this);
        journal_list.setLayoutManager(new LinearLayoutManager(this));
        journal_list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_back:
                this.finish();
                break;
        }
    }
}
