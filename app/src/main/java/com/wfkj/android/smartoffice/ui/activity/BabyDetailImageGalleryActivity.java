package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.EventBus_Account;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**

 */
public class BabyDetailImageGalleryActivity extends Activity
        implements OnPageChangeListener, OnClickListener {
    private ImageView delete_btn;
    private ViewPager mViewPager;
    private PagerAdapter mViewPageAdapter;
    private ArrayList<String> imgUrls;
    private HashMap<Integer, WeakReference<View>> mViewPagerCache;
    private LayoutInflater layInflater;
    private int index = 0;
    private TextView total_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail_image_gallery);

        init();
    }

    private void init() {
        total_page = (TextView) findViewById(R.id.page);
        //imgUrls = (String[]) getIntent().getStringArrayExtra("img_urls");
        imgUrls = getIntent().getStringArrayListExtra("imgUrls");
        index = getIntent().getIntExtra("index", 0);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        delete_btn = (ImageView) this.findViewById(R.id.delete_btn);
        mViewPagerCache = new HashMap<Integer, WeakReference<View>>();
        layInflater = LayoutInflater.from(this);
        mViewPageAdapter = new TicketCardPagerAdapter();
        mViewPager.setAdapter(mViewPageAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(index);
        delete_btn.setOnClickListener(this);

        total_page.setText(index + 1 + " " + "/"
                + " " + imgUrls.size());
    }


    public void onDestry() {
        super.onDestroy();
        if (mViewPagerCache != null) {
            mViewPagerCache.clear();
            mViewPagerCache = null;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
//		FixedShapeImageView fixedShapeImageView = viewList.get(arg0);
//		ImageLoader.getInstance().displayImage(imgUrls.get(arg0).img_url,
//				fixedShapeImageView, options);
        index = arg0;
        total_page.setText((arg0 + 1) + " " + "/"
                + " " + imgUrls.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onVgClick(View v) {
        finish();
        overridePendingTransition(0, R.anim.zoon_out_in);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.delete_btn:


                EventBus.getDefault().post(new EventBus_Account(Constants.EVENT_BUS_MODIFY_PERSONAL_DELETE_IMG_ID,imgUrls.get(index)));
                imgUrls.remove(index);
                if (imgUrls.size() > 0) {
                    mViewPageAdapter = new TicketCardPagerAdapter();
                    mViewPager.setAdapter(mViewPageAdapter);
//                    mViewPageAdapter.notifyDataSetChanged();
                    if (index == 0) {
                        mViewPager.setCurrentItem(index);
                        total_page.setText(index + 1 + " " + "/"
                                + " " + imgUrls.size());
                    } else {
                        if (index == imgUrls.size()) {
                            index = index - 1;
                            mViewPager.setCurrentItem(index);
                            total_page.setText(index + 1 + " " + "/"
                                    + " " + imgUrls.size());
                        } else {
                            mViewPager.setCurrentItem(index);
                            total_page.setText(index + 1 + " " + "/"
                                    + " " + imgUrls.size());
                        }
                    }
                } else {
                    this.finish();
                    overridePendingTransition(R.anim.zoon_out_in, 0);
                }

                break;
        }

    }

    class TicketCardPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {

            return imgUrls != null ? imgUrls.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View converView = null;
            if (mViewPagerCache.containsKey(position)) {
                WeakReference<View> wr = mViewPagerCache.get(position);
                converView = wr.get();
            }
            if (position >= imgUrls.size()) {
                return null;
            }

            ViewHolder holder = null;
            if (converView == null) {
                converView = layInflater.inflate(R.layout.gallery_item,
                        container, false);
                holder = new ViewHolder();
//				holder.image = (FixedShapeImageView) converView
//						.findViewById(R.id.img);
                holder.image = (ImageView) converView
                        .findViewById(R.id.img);
                holder.r1 = (RelativeLayout) converView.findViewById(R.id.r1);
                converView.setTag(holder);
            } else {
                holder = (ViewHolder) converView.getTag();
            }
            container.addView(converView);

//			holder.donut_progress.setFinishedStrokeColor(Color.alpha(R.color.bg_white));
//			holder.donut_progress.setUnfinishedStrokeColor(Color.alpha(R.color.bg_white));
//			holder.donut_progress.setDrawingCacheBackgroundColor(Color.alpha(R.color.bg_white));
//			holder.donut_progress.setFinishedStrokeWidth(3);
//			holder.donut_progress.setUnfinishedStrokeWidth(3);
//			holder.donut_progress.setMinimumWidth(3);
            final ViewHolder finalHolder = holder;

            holder.image.setImageBitmap(getLoacalBitmap(imgUrls.get(position)));

            mViewPagerCache.put(position, new WeakReference<View>(converView));
            converView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onVgClick(v);
                }
            });


            return converView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class ViewHolder {
        public RelativeLayout r1;
        public ImageView image;
    }


    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果按下的是返回键，并且没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(0, R.anim.zoon_out_in);
            return false;
        }
        return false;
    }
}
