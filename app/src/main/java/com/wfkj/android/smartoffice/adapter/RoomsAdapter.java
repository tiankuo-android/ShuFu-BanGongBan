package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Room;
import com.wfkj.android.smartoffice.my_interface.ManagementInterface;

import java.util.List;

/**
 * Created by wangdongyang on 17/2/7.
 */
public class RoomsAdapter extends BaseAdapter{

    private Context context;
    private List<Room> rooms;
    private ManagementInterface managementInterface;

    public RoomsAdapter(Context context, List<Room> rooms,ManagementInterface managementInterfac) {
        this.context = context;
        this.rooms = rooms;
        this.managementInterface = managementInterfac;
    }

    @Override
    public int getCount() {
        return rooms.size();
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
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rooms,null);

            holder.user_name_txt = (TextView) convertView.findViewById(R.id.user_name_txt);
            holder.user_edit_img = (ImageView) convertView.findViewById(R.id.user_edit_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Room room = rooms.get(position);

        holder.user_name_txt.setText(room.getName());

        holder.user_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementInterface.getClick(2,room.getId()+"","");
            }
        });

        return convertView;
    }


    class ViewHolder{
        TextView user_name_txt;
        ImageView user_edit_img;
    }
}
