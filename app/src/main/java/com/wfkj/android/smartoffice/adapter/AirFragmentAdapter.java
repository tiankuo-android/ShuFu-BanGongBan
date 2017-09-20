package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Categorie;
import com.wfkj.android.smartoffice.model.sever_model.Device;
import com.wfkj.android.smartoffice.model.sever_model.Room;
import com.wfkj.android.smartoffice.my_interface.AirFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.ui.activity.DetailedControlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdongyang on 16/11/21.
 */
public class AirFragmentAdapter extends BaseAdapter {


    private Context context;
    private List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms;
    private AirFragmentAdapterUpDate airFragmentAdapterUpDate;
    private JSONObject jsonObject;

    public AirFragmentAdapter(Context context, JSONObject jsonObject, List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms, AirFragmentAdapterUpDate airFragmentAdapterUpDate) {
        this.context = context;
        this.rooms = rooms;
        this.airFragmentAdapterUpDate = airFragmentAdapterUpDate;
        this.jsonObject = jsonObject;
    }

    public void upDate(List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms,JSONObject jsonObject) {
        this.rooms = rooms;
        this.jsonObject = jsonObject;
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

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_air, null);
            viewHolder.air_room_name = (TextView) convertView.findViewById(R.id.air_room_name);
            viewHolder.air_room_pm = (TextView) convertView.findViewById(R.id.air_room_pm);
            viewHolder.air_room_btn1 = (TextView) convertView.findViewById(R.id.air_room_btn1);
            viewHolder.air_room_btn2 = (TextView) convertView.findViewById(R.id.air_room_btn2);
            viewHolder.air_room_btn3 = (TextView) convertView.findViewById(R.id.air_room_btn3);
            viewHolder.air_room_btn_off = (TextView) convertView.findViewById(R.id.air_room_btn_off);
            viewHolder.air_room_co2 = (TextView) convertView.findViewById(R.id.air_room_co2);
            viewHolder.air_room_voc = (TextView) convertView.findViewById(R.id.air_room_voc);
            viewHolder.air_room_jq = (TextView) convertView.findViewById(R.id.air_room_jq);
            viewHolder.air_room_pressure = (TextView) convertView.findViewById(R.id.air_room_pressure);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Room room = rooms.get(position);
        String pm = "0";
        viewHolder.air_room_name.setText(room.getName());

        ArrayList<Categorie> categories = rooms.get(position).getCategories();

        Categorie categorieAir = null;
        for (Categorie categorie : categories) {
            if (categorie.getId() == 20) {
                categorieAir = categorie;
            }
        }

        ArrayList<Device> devices = null;
        if (categorieAir != null) {
            devices = categorieAir.getDevices();
            viewHolder.air_room_btn1.setEnabled(true);
            viewHolder.air_room_btn2.setEnabled(true);
            viewHolder.air_room_btn3.setEnabled(true);
            viewHolder.air_room_btn_off.setEnabled(true);
        }else{
            System.out.println("------null-------");
            viewHolder.air_room_btn1.setEnabled(false);
            viewHolder.air_room_btn2.setEnabled(false);
            viewHolder.air_room_btn3.setEnabled(false);
            viewHolder.air_room_btn_off.setEnabled(false);
        }


        DecimalFormat df = new DecimalFormat("0.00");

        if (devices != null && devices.size() > 0) {

            for (Device device : devices) {
                if (device.getId() == 78) {//二氧化碳检测
                    try {
                        int number = (Integer) (jsonObject.get(device.getExtid()));
                        viewHolder.air_room_co2.setText(number + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (device.getId() == 79) {//甲醛检测
                    try {
                        double number = (double) (Integer) (jsonObject.get(device.getExtid())) / 100;
                        viewHolder.air_room_jq.setText(df.format(number) + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (device.getId() == 80) {//pm25检测
                    try {
                        int number = (Integer) (jsonObject.get(device.getExtid()));
                        viewHolder.air_room_pm.setText(number + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (device.getId() == 81) {//voc检测
                    try {
                        double number = (double) (Integer) (jsonObject.get(device.getExtid())) / 100;
                        viewHolder.air_room_voc.setText(df.format(number) + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }else {

        }

        String airBtn = DetailedControlActivity.air_set_numbers.get(position);
        if (airBtn.equals("-1")) {
            getBtnClick(-1, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
        } else if (airBtn.equals("0")) {
            getBtnClick(0, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
        } else if (airBtn.equals("1")) {
            getBtnClick(1, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
        } else if (airBtn.equals("2")) {
            getBtnClick(2, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
        }
            viewHolder.air_room_btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getBtnClick(3, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
                    update_fragment(position, 3);
//                    List<String> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_BTN_AIR), new TypeToken<List<String>>() {
//                    }.getType());

                    DetailedControlActivity.air_set_numbers.set(position, "3");
//                    FileUtil.saveString(context, Constants.SP_BTN_AIR, list.toString());
                }
            });
            viewHolder.air_room_btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnClick(2, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
                    update_fragment(position, 2);
//                    List<String> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_BTN_AIR), new TypeToken<List<String>>() {
//                    }.getType());
                    DetailedControlActivity.air_set_numbers.set(position, "2");
//                    FileUtil.saveString(context, Constants.SP_BTN_AIR, list.toString());


                }
            });

            viewHolder.air_room_btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnClick(1, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
                    update_fragment(position, 1);
//                    List<String> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_BTN_AIR), new TypeToken<List<String>>() {
//                    }.getType());
                    DetailedControlActivity.air_set_numbers.set(position, "1");
//                    FileUtil.saveString(context, Constants.SP_BTN_AIR, list.toString());
                }
            });
            viewHolder.air_room_btn_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getBtnClick(0, viewHolder.air_room_btn1, viewHolder.air_room_btn2, viewHolder.air_room_btn3, viewHolder.air_room_btn_off);
                    update_fragment(position, 0);
//                    List<String> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_BTN_AIR), new TypeToken<List<String>>() {
//                    }.getType());
                    DetailedControlActivity.air_set_numbers.set(position, "0");
//                    FileUtil.saveString(context, Constants.SP_BTN_AIR, list.toString());

                }
            });


        return convertView;
    }

    private void getBtnClick(int tag, TextView air_room_btn1, TextView air_room_btn2, TextView air_room_btn3, TextView air_room_btn_off) {
        switch (tag) {
            case -1:
                air_room_btn1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn3.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);


                air_room_btn1.setTextColor(Color.parseColor("#585858"));
                air_room_btn2.setTextColor(Color.parseColor("#585858"));
                air_room_btn3.setTextColor(Color.parseColor("#585858"));
                air_room_btn_off.setTextColor(Color.parseColor("#585858"));
                break;
            case 0:
                air_room_btn1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn3.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn_off.setBackgroundResource(R.drawable.radio_text_red_shape);


                air_room_btn1.setTextColor(Color.parseColor("#585858"));
                air_room_btn2.setTextColor(Color.parseColor("#585858"));
                air_room_btn3.setTextColor(Color.parseColor("#585858"));
                air_room_btn_off.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                air_room_btn1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn3.setBackgroundResource(R.drawable.radio_text_blue_shape);
                air_room_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);


                air_room_btn1.setTextColor(Color.parseColor("#585858"));
                air_room_btn2.setTextColor(Color.parseColor("#585858"));
                air_room_btn3.setTextColor(Color.parseColor("#ffffff"));
                air_room_btn_off.setTextColor(Color.parseColor("#585858"));
                break;
            case 2:

                air_room_btn1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn2.setBackgroundResource(R.drawable.radio_text_blue_shape);
                air_room_btn3.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);

                air_room_btn1.setTextColor(Color.parseColor("#585858"));
                air_room_btn2.setTextColor(Color.parseColor("#ffffff"));
                air_room_btn3.setTextColor(Color.parseColor("#585858"));
                air_room_btn_off.setTextColor(Color.parseColor("#585858"));
                break;
            case 3:
                air_room_btn1.setBackgroundResource(R.drawable.radio_text_blue_shape);
                air_room_btn2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn3.setBackgroundResource(R.drawable.radio_text_grap_shape);
                air_room_btn_off.setBackgroundResource(R.drawable.radio_text_grap_shape);

                air_room_btn1.setTextColor(Color.parseColor("#ffffff"));
                air_room_btn2.setTextColor(Color.parseColor("#585858"));
                air_room_btn3.setTextColor(Color.parseColor("#585858"));
                air_room_btn_off.setTextColor(Color.parseColor("#585858"));
                break;
        }
    }

    private void update_fragment(int position, int tag) {
        airFragmentAdapterUpDate.upDate(position, tag);
    }

    class ViewHolder {
        TextView air_room_name;//房间名称
        TextView air_room_pm;//当前pm2.5
        TextView air_room_btn1;//极优按钮
        TextView air_room_btn2;//优按钮
        TextView air_room_btn3;//良按钮
        TextView air_room_btn_off;//关闭按钮
        TextView air_room_co2;//显示co2
        TextView air_room_voc;//显示voc
        TextView air_room_jq;//显示甲醛
        TextView air_room_pressure;//显示压差
    }


}
