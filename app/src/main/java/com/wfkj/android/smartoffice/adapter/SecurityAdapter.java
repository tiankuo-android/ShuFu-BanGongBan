package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sevenheaven.iosswitch.ShSwitchView;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangdongyang on 17/1/16.
 */
public class SecurityAdapter extends BaseAdapter{

    private Context context;
    private boolean flg = false;
    private ArrayList<Device> securitys = null;
    private JSONObject jsonObject;

    public SecurityAdapter(Context context, ArrayList<Device> securitys, JSONObject jsonObject) {
        this.context = context;
        this.securitys = securitys;
        this.jsonObject = jsonObject;
    }

    @Override
    public int getCount() {
        return securitys.size();
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

        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_security_1,null);
            holder.security_1_name = (TextView) convertView.findViewById(R.id.security_1_name);
            holder.security_1_state = (TextView) convertView.findViewById(R.id.security_1_state);
            holder.security_1_switch = (ShSwitchView) convertView.findViewById(R.id.security_1_switch);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Device device = securitys.get(position);
        if(device !=null){

            holder.security_1_name.setText(device.getName());

            try {
                if(jsonObject.has(device.getExtid())){
                    flg = (boolean) jsonObject.get(device.getExtid());
                    System.out.println("----------0000------"+jsonObject.get(device.getExtid().toString()));
                    System.out.println("++++++++++++++++++++++++++"+jsonObject.toString());
                    System.out.println("========================"+device.getExtid());
                    System.out.println("------------------------"+flg);
                    if(flg){
                        holder.security_1_state.setText("开");
                        holder.security_1_switch.setOn(true);
                    }else{
                        holder.security_1_state.setText("关");
                        holder.security_1_switch.setOn(false);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        return convertView;
    }

    class ViewHolder{
        TextView security_1_name;
        TextView security_1_state;
        ShSwitchView security_1_switch;
    }
}
