package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wfkj.android.smartoffice.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * Created by wangdongyang on 16/8/25.
 */
public class PhotoAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

//    private DisplayImageOptions options = new DisplayImageOptions.Builder()
//            .build();
//    private ImageLoader imageLoader = ImageLoader.getInstance();

    public PhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int position = 0;
        if (list.size() >= 0 && list.size() < 3) {
            position = list.size() + 1;
        } else {
            if (list.size()>3){
                position = 3;
            }else{
                position = list.size();
            }

        }
        return position;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_photo, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.size()<3){
            if (position==list.size()){
                viewHolder.imageView.setImageResource(R.mipmap.icon_addpic_unfocused);
            }else{
                String photoUrl = list.get(position);
                System.out.println("-----"+photoUrl);
//                imageLoader.displayImage(
//                        "file://" +
//                                photoUrl, viewHolder.imageView
//                        , options);
                viewHolder.imageView.setImageBitmap(getLoacalBitmap(photoUrl));
            }
        }else{
            String photoUrl = list.get(position);
//            imageLoader.displayImage(
//                    "file://" + photoUrl, viewHolder.imageView
//                    , options);
            viewHolder.imageView.setImageBitmap(getLoacalBitmap(photoUrl));
        }

        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
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

}
