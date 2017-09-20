package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Device;
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wangdongyang on 16/12/15.
 */
public class LightAdaper extends BaseAdapter {

    private Context context;
    private List<Device> lights;
    private JSONObject jsonObject;
    private LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate;


    public LightAdaper(Context context,JSONObject jsonObject, List<Device> lights, LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate) {
        this.context = context;
        this.lights = lights;
        this.lightFragmentAdapterUpDate = lightFragmentAdapterUpDate;
        this.jsonObject = jsonObject;
    }

    public void upDate(List<Device> lights,JSONObject jsonObject) {
        this.lights = lights;
        this.jsonObject = jsonObject;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lights.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_light_1, null);
            holder = new ViewHolder();
            holder.item_light_1_state = (TextView) convertView.findViewById(R.id.item_light_1_state);
            holder.item_light_1_switch = (TextView) convertView.findViewById(R.id.item_light_1_switch);
            holder.item_light_1_btn_off = (TextView) convertView.findViewById(R.id.item_light_1_btn_off);
            holder.item_light_1_on = (TextView) convertView.findViewById(R.id.item_light_1_on);
            holder.item_light_1_automatic = (TextView) convertView.findViewById(R.id.item_light_1_automatic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Device device = lights.get(position);

        if (device!=null){
            holder.item_light_1_state.setText(device.getName()+"状态");
            try {
                boolean flg = (boolean)jsonObject.get(device.getExtid());
                holder.item_light_1_switch.setText(flg?"ON":"OFF");

                if(flg){
                    getBtnback(1,holder.item_light_1_automatic,holder.item_light_1_on,holder.item_light_1_btn_off);
                }else{
                    getBtnback(2,holder.item_light_1_automatic,holder.item_light_1_on,holder.item_light_1_btn_off);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        holder.item_light_1_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightFragmentAdapterUpDate.upDate(0,device.getId(),1,2,0,0);
            }
        });
        holder.item_light_1_btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightFragmentAdapterUpDate.upDate(0,device.getId(),1,1,0,0);
            }
        });


        return convertView;
    }


    private void getBtnback(int flg,TextView item_light_1_automatic,TextView item_light_1_on,TextView item_light_1_btn_off){
        switch (flg){
            case 0://自动
                item_light_1_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
                item_light_1_on.setBackgroundResource(R.drawable.radio_text_grap_shape);
                item_light_1_automatic.setBackgroundResource(R.drawable.radio_text_blue_shape);

                item_light_1_btn_off.setTextColor(Color.parseColor("#585858"));
                item_light_1_on.setTextColor(Color.parseColor("#585858"));
                item_light_1_automatic.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1://开
                item_light_1_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
                item_light_1_on.setBackgroundResource(R.drawable.radio_text_blue_shape);
                item_light_1_automatic.setBackgroundResource(R.drawable.radio_text_grap_shape);

                item_light_1_btn_off.setTextColor(Color.parseColor("#585858"));
                item_light_1_on.setTextColor(Color.parseColor("#ffffff"));
                item_light_1_automatic.setTextColor(Color.parseColor("#585858"));

                break;
            case 2://关
                item_light_1_btn_off.setBackgroundResource(R.drawable.radio_text_red_shape);
                item_light_1_on.setBackgroundResource(R.drawable.radio_text_grap_shape);
                item_light_1_automatic.setBackgroundResource(R.drawable.radio_text_grap_shape);

                item_light_1_btn_off.setTextColor(Color.parseColor("#ffffff"));
                item_light_1_on.setTextColor(Color.parseColor("#585858"));
                item_light_1_automatic.setTextColor(Color.parseColor("#585858"));

                break;
        }
    }


    class ViewHolder {
        TextView item_light_1_state;//灯的状态
        TextView item_light_1_switch;//灯的开关状态
        TextView item_light_1_btn_off;//关闭按钮
        TextView item_light_1_on;//开按钮
        TextView item_light_1_automatic;//自动按钮
    }

}
