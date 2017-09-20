package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.House;
import com.wfkj.android.smartoffice.model.sever_model.UserHouse;
import com.wfkj.android.smartoffice.my_interface.DeleteUserInterface;
import com.wfkj.android.smartoffice.ui.activity.ModifyActivity;

/**
 * Created by wangdongyang on 17/2/7.
 */
public class UsersAdapter extends BaseAdapter {

    private Context context;
    private House house;
    private DeleteUserInterface deleteUserInterface;

    public UsersAdapter(Context context, House house,DeleteUserInterface deleteUserInterface) {
        this.context = context;
        this.house = house;
        this.deleteUserInterface = deleteUserInterface;
    }

    @Override
    public int getCount() {
        return house.getUserHouses().size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_users,null);
            holder.user_name_txt = (TextView) convertView.findViewById(R.id.user_name_txt);
            holder.user_edit_img = (ImageView) convertView.findViewById(R.id.user_edit_img);
            holder.user_delete_img = (ImageView) convertView.findViewById(R.id.user_delete_img);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final UserHouse userHouse = house.getUserHouses().get(position);

        holder.user_name_txt.setText(userHouse.getUsername());

        final String roleUser = userHouse.getRole();
        if(roleUser!=null){
            if(roleUser.equals("owner")){
                holder.user_delete_img.setImageResource(R.mipmap.administratora_small);
            }else{
                holder.user_delete_img.setImageResource(R.mipmap.delete_btn);
            }
        }

        final String roleHouse = house.getRole();

        if(roleHouse != null){
            if(roleHouse.equals("owner")){
                holder.user_delete_img.setVisibility(View.VISIBLE);
            }else{
                if(roleUser.equals("owner")){
                    holder.user_delete_img.setVisibility(View.GONE);
                }else{
                    holder.user_delete_img.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.user_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, ModifyActivity.class);
                intent.putExtra("TAG",0);
                intent.putExtra("ID",house.getId()+"");
                intent.putExtra("UID",userHouse.getUser_id()+"");
                context.startActivity(intent);

            }
        });

        holder.user_delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!roleUser.equals("owner")){
                    deleteUserInterface.getDeleteUser(house.getId(),userHouse.getUser_id());
                }
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView user_name_txt;
        ImageView user_edit_img;
        ImageView user_delete_img;
    }
}
