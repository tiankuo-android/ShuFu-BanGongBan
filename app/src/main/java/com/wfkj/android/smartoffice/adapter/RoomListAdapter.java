package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Room;

import java.util.List;

/**
 * Created by wangdongyang on 2017/5/15.
 */

public class RoomListAdapter extends BaseAdapter {

    private Context context;
    private List<Room> rooms;


    public RoomListAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        if(rooms==null){
            return 0;
        }else{
            return rooms.size();
        }

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

        RoomListViewHolder holder;

        if(convertView == null){
            holder = new RoomListViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room_list,null);
            holder.room_name = (TextView) convertView.findViewById(R.id.room_name);
            convertView.setTag(holder);
        }else{
            holder = (RoomListViewHolder) convertView.getTag();
        }

        Room room = rooms.get(position);
        holder.room_name.setText(room.getName());

        return convertView;
    }


    class RoomListViewHolder{
        TextView room_name;
    }
}
