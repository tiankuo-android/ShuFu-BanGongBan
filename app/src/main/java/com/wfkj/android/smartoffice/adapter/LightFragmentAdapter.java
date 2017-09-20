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
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.util.view_util.CustomListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdongyang on 16/12/15.
 */
public class LightFragmentAdapter extends BaseAdapter{

    private Context context;
    private List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms;
    private LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate;

    private com.wfkj.android.smartoffice.model.sever_model.Room room;
    private JSONObject jsonObject;

    private LightAdaper lightAdaper;
    private ColorLightAdapter colorLightAdapter;
    private CurtainAdapter curtainAdapter;
    private WindowCurtainAdapter windowCurtainAdapter;

    private ArrayList<Device> lights = null;
    private ArrayList<Device> colorLights = null;
    private ArrayList<Device> curtains = null;
    private ArrayList<Device> windows = null;


    public LightFragmentAdapter(Context context,JSONObject jsonObject, List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms, LightperatureFragmentAdapterUpDate lightFragmentAdapterUpDate) {
        this.context = context;
        this.rooms = rooms;
        this.lightFragmentAdapterUpDate = lightFragmentAdapterUpDate;
        this.jsonObject = jsonObject;

    }

    public void upDate(List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms,JSONObject jsonObject){
        this.rooms = rooms;
        this.jsonObject = jsonObject;
        notifyDataSetChanged();

        if(lightAdaper!=null){
//            if(room!=null){
//                lightAdaper.upDate(room.getLights());
//            }

        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_light,null);
            holder = new ViewHolder();
            holder.light_room_name = (TextView) convertView.findViewById(R.id.light_room_name);
            holder.light_room_lights = (CustomListView) convertView.findViewById(R.id.light_room_lights);
            holder.light_room_colorlights = (CustomListView) convertView.findViewById(R.id.light_room_colorlights);
            holder.light_room_curtains = (CustomListView) convertView.findViewById(R.id.light_room_curtains);
            holder.light_room_window = (CustomListView) convertView.findViewById(R.id.light_room_window);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
         room = rooms.get(position);
        if (room!=null){
            holder.light_room_name.setText(room.getName());
            if(room.getCategories()!=null && room.getCategories().size()>0){
                lights = new ArrayList<>();
                colorLights = new ArrayList<>();
                curtains = new ArrayList<>();
                windows = new ArrayList<>();
                Categorie lightCategorie = null;

                for(Categorie categorie : room.getCategories()){
                    if(categorie.getId() == 50){
                        lightCategorie = categorie;
                    }
                }

                if(lightCategorie!=null && lightCategorie.getDevices()!=null && lightCategorie.getDevices().size()>0){
                    for(Device device : lightCategorie.getDevices()){
                        if(device.getSub_category() == 50){//普通灯
                            lights.add(device);
                        }else if(device.getSub_category() == 51){
                            colorLights.add(device);
                        }else if(device.getSub_category() == 52){//窗帘
                            windows.add(device);
                        }else if(device.getSub_category() == 53){//开窗器
                            curtains.add(device);
                        }
                    }
                }

                if(lights.size()>0){
                    lightAdaper = new LightAdaper(context, jsonObject, lights, new LightperatureFragmentAdapterUpDate() {
                        @Override
                        public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                            lightFragmentAdapterUpDate.upDate(position,deviceId,flg,tag,number,colorNumber);
                        }
                    });
                    holder.light_room_lights.setAdapter(lightAdaper);
                }
                if(curtains.size()>0){
                    curtainAdapter = new CurtainAdapter(context, jsonObject, curtains, new LightperatureFragmentAdapterUpDate() {
                        @Override
                        public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                            lightFragmentAdapterUpDate.upDate(position,deviceId,flg,tag,number,colorNumber);
                        }
                    });
                    holder.light_room_window.setAdapter(curtainAdapter);
                }
                if(windows.size()>0){
                    windowCurtainAdapter = new WindowCurtainAdapter(context, jsonObject, windows, new LightperatureFragmentAdapterUpDate() {
                        @Override
                        public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                            lightFragmentAdapterUpDate.upDate(position,deviceId,flg,tag,number,colorNumber);
                        }
                    });
                    holder.light_room_curtains.setAdapter(windowCurtainAdapter);
                }
                if(colorLights.size()>0){
                    colorLightAdapter = new ColorLightAdapter(context, jsonObject, colorLights, new LightperatureFragmentAdapterUpDate() {
                        @Override
                        public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                            lightFragmentAdapterUpDate.upDate(position,deviceId,flg,tag,number,colorNumber);
                        }
                    });
                    holder.light_room_colorlights.setAdapter(colorLightAdapter);
                }


//                if(lightAdaper==null){
//                    lightAdaper = new LightAdaper(context, room.getLights(), new LightFragmentAdapterUpDate() {
//                        @Override
//                        public void upDate(int position, int flg, int tag, int number, int colorNumber) {
//
//                        }
//                    });
//                    holder.light_room_lights.setAdapter(lightAdaper);
//                }
            }

        }



        return convertView;
    }


    class ViewHolder{

        TextView light_room_name;//房间名称
        CustomListView light_room_lights;//灯列表
        CustomListView light_room_colorlights;//彩灯列表
        CustomListView light_room_curtains;//开窗器列表
        CustomListView light_room_window;//窗帘列表

    }


}
