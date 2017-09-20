package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.House;
import com.wfkj.android.smartoffice.my_interface.DeleteUserInterface;
import com.wfkj.android.smartoffice.ui.activity.AddUserActivity;
import com.wfkj.android.smartoffice.util.view_util.CustomListView;

import java.util.List;

/**
 * Created by wangdongyang on 17/2/7.
 */
public class UserManagementAdapter extends BaseAdapter {


    private Context context;
    private List<House> houses;
    private UsersAdapter adapter;
    private DeleteUserInterface deleteUserInterface;

    public UserManagementAdapter(Context context, List<House> houses,DeleteUserInterface deleteUserInterface) {
        this.context = context;
        this.houses = houses;
        this.deleteUserInterface = deleteUserInterface;
    }

    @Override
    public int getCount() {
        return houses.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_management, null);

            holder.house_name_txt = (TextView) convertView.findViewById(R.id.house_name_txt);
            holder.address_txt = (TextView) convertView.findViewById(R.id.address_txt);
            holder.add_user_btn = (LinearLayout) convertView.findViewById(R.id.add_user_btn);
            holder.users_listview = (CustomListView) convertView.findViewById(R.id.users_listview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final House house = houses.get(position);

        holder.house_name_txt.setText(house.getName());
        holder.address_txt.setText(house.getAddress());

        adapter = new UsersAdapter(context, house, new DeleteUserInterface() {
            @Override
            public void getDeleteUser(int id, int uid) {
                deleteUserInterface.getDeleteUser(id,uid);
            }
        });
        holder.users_listview.setAdapter(adapter);

        String str = house.getRole();
        if (str == null) {
            holder.add_user_btn.setVisibility(View.GONE);
        } else {
            if (str.equals("owner")) {
                holder.add_user_btn.setVisibility(View.VISIBLE);
            } else {
                holder.add_user_btn.setVisibility(View.GONE);
            }
        }

        holder.add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, AddUserActivity.class);
                intent.putExtra("HOUSEID",house.getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView house_name_txt;
        TextView address_txt;
        LinearLayout add_user_btn;
        CustomListView users_listview;
    }

}
