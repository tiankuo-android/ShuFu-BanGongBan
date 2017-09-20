package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Device;
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by wangdongyang on 16/12/15.
 */
public class ColorLightAdapter extends BaseAdapter{

    private Context context;
    private List<Device> lights;
    private JSONObject jsonObject;
    private LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate;


    public ColorLightAdapter(Context context,JSONObject jsonObject, List<Device> lights, LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate) {
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

        ViewHolder holder = null;
        if(convertView ==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_light_2,null);
            holder.item_light_2_state = (TextView) convertView.findViewById(R.id.item_light_2_state);
            holder.item_light_2_switch = (TextView) convertView.findViewById(R.id.item_light_2_switch);
            holder.item_light_2_btn_off = (TextView) convertView.findViewById(R.id.item_light_2_btn_off);
            holder.item_light_2_btn_on = (TextView) convertView.findViewById(R.id.item_light_2_btn_on);
            holder.item_light_2_btn_automatic = (TextView) convertView.findViewById(R.id.item_light_2_btn_automatic);
            holder.seekbar_star = (SeekBar) convertView.findViewById(R.id.seekbar_star);
            holder.seekbar_color = (SeekBar) convertView.findViewById(R.id.seekbar_color);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Device device = lights.get(position);
        if(device !=null){
            holder.item_light_2_state.setText(device.getName());
        }


        return convertView;
    }

    class ViewHolder{
        TextView item_light_2_state;//灯名
        TextView item_light_2_switch;//状态
        TextView item_light_2_btn_off;//关
        TextView item_light_2_btn_on;//开
        TextView item_light_2_btn_automatic;//自动
        SeekBar seekbar_star;//调节亮度
        SeekBar seekbar_color;//调节颜色
    }

}
