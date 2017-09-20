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
import com.wfkj.android.smartoffice.ui.activity.DetailedControlActivity;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * 开窗器
 * Created by wangdongyang on 16/12/15.
 */
public class CurtainAdapter extends BaseAdapter{

    private int deviceFlg = 0;
    private Context context;
    private List<Device> curtains;
    private JSONObject jsonObject;
    private LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate;

    public CurtainAdapter(Context context,JSONObject jsonObject, List<Device> curtains, LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate) {
        this.context = context;
        this.curtains = curtains;
        this.lightFragmentAdapterUpDate = lightFragmentAdapterUpDate;
    }

    public void upDate(List<Device> curtains,JSONObject jsonObject){
        this.curtains = curtains;
        this.jsonObject = jsonObject;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return curtains.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_light_4,null);
            holder.item_light_4_state = (TextView) convertView.findViewById(R.id.item_light_4_state);
            holder.item_light_4_btn_on = (TextView) convertView.findViewById(R.id.item_light_4_btn_on);
            holder.item_light_4_btn_off = (TextView) convertView.findViewById(R.id.item_light_4_btn_off);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Device device = curtains.get(position);
        if(device!=null){
            holder.item_light_4_state.setText(device.getName());
        }

//        for(int i=0;i<DetailedControlActivity.devices.size();i++){
//            if(DetailedControlActivity.devices.get(i).getId() == device.getId()){
//                device1 = DetailedControlActivity.devices.get(i);
//                deviceFlg = i;
//            }
//        }

        if(device.getOpenFlg()==1){
            holder.item_light_4_btn_on.setBackgroundResource(R.drawable.radio_text_blue_shape);
            holder.item_light_4_btn_on.setTextColor(Color.parseColor("#ffffff"));
        }else{
            holder.item_light_4_btn_on.setBackgroundResource(R.drawable.radio_text_grap_shape);
            holder.item_light_4_btn_on.setTextColor(Color.parseColor("#585858"));
        }
        if(device.getCloseFlg()==1){
            holder.item_light_4_btn_off.setBackgroundResource(R.drawable.radio_text_blue_shape);
            holder.item_light_4_btn_off.setTextColor(Color.parseColor("#ffffff"));
        }else{
            holder.item_light_4_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
            holder.item_light_4_btn_off.setTextColor(Color.parseColor("#585858"));
        }

        final ViewHolder finalHolder = holder;
        holder.item_light_4_btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(device.getOpenFlg()==0){
                    device.setOpenFlg(1);
                    finalHolder.item_light_4_btn_on.setBackgroundResource(R.drawable.radio_text_blue_shape);
                    finalHolder.item_light_4_btn_on.setTextColor(Color.parseColor("#ffffff"));
                    lightFragmentAdapterUpDate.upDate(position,device.getId(),4,2,0,0);
                }else{
                    device.setOpenFlg(0);
                    finalHolder.item_light_4_btn_on.setBackgroundResource(R.drawable.radio_text_grap_shape);
                    finalHolder.item_light_4_btn_on.setTextColor(Color.parseColor("#585858"));
                    lightFragmentAdapterUpDate.upDate(position,device.getId(),4,5,0,0);
                }


            }
        });

        holder.item_light_4_btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(device.getCloseFlg()==0){
                    device.setCloseFlg(1);
                    DetailedControlActivity.devices.set(deviceFlg,device);
                    finalHolder.item_light_4_btn_off.setBackgroundResource(R.drawable.radio_text_blue_shape);
                    finalHolder.item_light_4_btn_off.setTextColor(Color.parseColor("#ffffff"));
                    lightFragmentAdapterUpDate.upDate(position,device.getId(),4,1,0,0);
                }else{
                    device.setCloseFlg(0);
                    DetailedControlActivity.devices.set(deviceFlg,device);
                    finalHolder.item_light_4_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
                    finalHolder.item_light_4_btn_off.setTextColor(Color.parseColor("#585858"));
                    lightFragmentAdapterUpDate.upDate(position,device.getId(),4,4,0,0);
                }


            }
        });


        return convertView;
    }

    class ViewHolder{
        TextView item_light_4_state;//开窗器名
        TextView item_light_4_btn_on;//开
        TextView item_light_4_btn_off;//合
    }

}
