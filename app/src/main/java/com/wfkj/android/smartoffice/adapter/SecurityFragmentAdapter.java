package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Categorie;
import com.wfkj.android.smartoffice.model.sever_model.Device;
import com.wfkj.android.smartoffice.model.sever_model.Room;
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.util.view_util.CustomListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 办公安防
 * Created by wangdongyang on 17/1/16.
 */
public class SecurityFragmentAdapter extends BaseAdapter{


    private Context context;
    private List<Room> rooms;
    private LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate;

    private SecurityAdapter securityAdapter;
    private ArrayList<Device> devices;

    private com.wfkj.android.smartoffice.model.sever_model.Room room;
    private JSONObject jsonObject;


    public SecurityFragmentAdapter(Context context,JSONObject jsonObject, List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms, LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate) {
        this.context = context;
        this.rooms = rooms;
        this.lightFragmentAdapterUpDate = lightFragmentAdapterUpDate;
        this.jsonObject = jsonObject;

    }

    public void upDate(List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms,JSONObject jsonObject){
        this.rooms = rooms;
        this.jsonObject = jsonObject;
        notifyDataSetChanged();

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

        devices = new ArrayList<>();
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_security,null);
            holder.security_fragment_name = (TextView) convertView.findViewById(R.id.security_fragment_name);
            holder.security_list = (CustomListView) convertView.findViewById(R.id.security_list);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Room room = rooms.get(position);
        if(room != null){
            holder.security_fragment_name.setText(room.getName());
            if(room.getCategories()!=null && room.getCategories().size()>0){
                for(Categorie categorie:room.getCategories()){
                    if(categorie.getId() == 60){
                        if(categorie.getDevices() != null && categorie.getDevices().size()>0){
                            for(Device device : categorie.getDevices()){
                                if(device.getSub_category() == 60){
                                    devices.add(device);
                                }
                            }
                        }
                    }
                }
            }

        }

        if(devices.size()>0){
            securityAdapter = new SecurityAdapter(context,devices,jsonObject);
            holder.security_list.setAdapter(securityAdapter);
        }

        return convertView;
    }


    class ViewHolder{
        TextView security_fragment_name;
        CustomListView security_list;

    }
}
