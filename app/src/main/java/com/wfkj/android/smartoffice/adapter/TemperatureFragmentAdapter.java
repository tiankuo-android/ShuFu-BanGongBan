package com.wfkj.android.smartoffice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Categorie;
import com.wfkj.android.smartoffice.model.sever_model.Device;
import com.wfkj.android.smartoffice.my_interface.TemperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.ui.activity.DetailedControlActivity;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangdongyang on 16/11/21.
 */
public class TemperatureFragmentAdapter extends BaseAdapter {

    private Context context;
    private List<com.wfkj.android.smartoffice.model.sever_model.Room> roomList;
    private TemperatureFragmentAdapterUpDate temperatureFragmentAdapterUpDate;
    private JSONObject jsonObject;

    public TemperatureFragmentAdapter(Context context, JSONObject jsonObject, List<com.wfkj.android.smartoffice.model.sever_model.Room> roomList, TemperatureFragmentAdapterUpDate temperatureFragmentAdapterUpDate) {
        this.context = context;
        this.roomList = roomList;
        this.temperatureFragmentAdapterUpDate = temperatureFragmentAdapterUpDate;
        this.jsonObject = jsonObject;
    }

    public void upDate(List<com.wfkj.android.smartoffice.model.sever_model.Room> roomList, JSONObject jsonObject) {
        this.roomList = roomList;
        this.jsonObject = jsonObject;
    }

    @Override
    public int getCount() {
        return roomList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_temperature, null);
            viewHolder.temperature_room_name = (TextView) convertView.findViewById(R.id.temperature_room_name);
            viewHolder.temperature_txt = (TextView) convertView.findViewById(R.id.temperature_txt);
            viewHolder.temperature_btn_refrigeration = (TextView) convertView.findViewById(R.id.temperature_btn_refrigeration);
            viewHolder.temperature_btn_heating = (TextView) convertView.findViewById(R.id.temperature_btn_heating);
            viewHolder.temperature_btn_off = (TextView) convertView.findViewById(R.id.temperature_btn_off);
            viewHolder.temperature_btn_reduce = (TextView) convertView.findViewById(R.id.temperature_btn_reduce);
            viewHolder.temperature_btn_add = (TextView) convertView.findViewById(R.id.temperature_btn_add);
            viewHolder.temperature_number = (TextView) convertView.findViewById(R.id.temperature_number);


            viewHolder.humidity_room_name = (TextView) convertView.findViewById(R.id.humidity_room_name);
            viewHolder.humidity_txt = (TextView) convertView.findViewById(R.id.humidity_txt);
            viewHolder.humidity_btn_humidification = (TextView) convertView.findViewById(R.id.humidity_btn_humidification);
            viewHolder.humidity_btn_dehumidification = (TextView) convertView.findViewById(R.id.humidity_btn_dehumidification);
            viewHolder.humidity_btn_off = (TextView) convertView.findViewById(R.id.humidity_btn_off);
            viewHolder.humidity_btn_reduce = (TextView) convertView.findViewById(R.id.humidity_btn_reduce);
            viewHolder.humidity_btn_add = (TextView) convertView.findViewById(R.id.humidity_btn_add);
            viewHolder.humidity_number = (TextView) convertView.findViewById(R.id.humidity_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        com.wfkj.android.smartoffice.model.sever_model.Room room = roomList.get(position);

        viewHolder.temperature_room_name.setText(room.getName());
        viewHolder.humidity_room_name.setText(room.getName());


        DecimalFormat df = new DecimalFormat("0.0");

        ArrayList<Categorie> categories = room.getCategories();
        ArrayList<Device> devices = null;
        for (Categorie categorie : categories) {
            if (categorie.getId() == 30) {
                devices = categorie.getDevices();
            }
        }

        Device temperatureDevice = null;
        Device humidityDevice = null;

        if (devices != null && devices.size() > 0) {
            for (Device device : devices) {
                if (device.getId() == 25) {//温度检测
                    temperatureDevice = device;
                } else if (device.getId() == 22) {//湿度检测
                    humidityDevice = device;
                }
            }
        }
        if (temperatureDevice == null || humidityDevice == null) {


            if(temperatureDevice == null){
                viewHolder.temperature_btn_refrigeration.setSelected(false);
                viewHolder.temperature_btn_heating.setSelected(false);
                viewHolder.temperature_btn_off.setSelected(false);
                viewHolder.temperature_btn_reduce.setSelected(false);
                viewHolder.temperature_btn_add.setSelected(false);
            }
            if(humidityDevice == null){
                viewHolder.humidity_btn_humidification.setSelected(false);
                viewHolder.humidity_btn_dehumidification.setSelected(false);
                viewHolder.humidity_btn_off.setSelected(false);
                viewHolder.humidity_btn_reduce.setSelected(false);
                viewHolder.humidity_btn_add.setSelected(false);
            }

        } else {

            viewHolder.temperature_btn_refrigeration.setSelected(true);
            viewHolder.temperature_btn_heating.setSelected(true);
            viewHolder.temperature_btn_off.setSelected(true);
            viewHolder.temperature_btn_reduce.setSelected(true);
            viewHolder.temperature_btn_add.setSelected(true);
            viewHolder.humidity_btn_humidification.setSelected(true);
            viewHolder.humidity_btn_dehumidification.setSelected(true);
            viewHolder.humidity_btn_off.setSelected(true);
            viewHolder.humidity_btn_reduce.setSelected(true);
            viewHolder.humidity_btn_add.setSelected(true);

            if (temperatureDevice != null) {
                List<Integer> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_NUMBER_TEMPERATURE), new TypeToken<List<Integer>>() {
                }.getType());
                double number = 0;
                try {
                    number = (double) (Integer) (jsonObject.get(temperatureDevice.getExtid())) / 10;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                viewHolder.temperature_txt.setText(df.format(number) + "");
                viewHolder.temperature_number.setText(DetailedControlActivity.tempertaure_set_numbers.get(position) + "");
            }

            if (humidityDevice != null) {

                double number = 0;
                try {
                    number = (double) (Integer) (jsonObject.get(humidityDevice.getExtid())) / 10;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                viewHolder.humidity_txt.setText(df.format(number) + "");
                viewHolder.humidity_number.setText(DetailedControlActivity.humidity_set_numbers.get(position) + "");
            }


//            if (FileUtil.loadString(context, Constants.SP_BTN_TEMPERATURE) == null || FileUtil.loadString(context, Constants.SP_BTN_TEMPERATURE).equals("")) {
//                getBtnUpdate(0, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
//            } else {
//                List<String> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_BTN_TEMPERATURE), new TypeToken<List<String>>() {
//                }.getType());
            String numberT = DetailedControlActivity.tempertaure_set_btn.get(position);
            if (numberT.equals("0")) {
                getBtnUpdate(0, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
            } else if (numberT.equals("1")) {
                getBtnUpdate(1, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
            } else if (numberT.equals("2")) {
                getBtnUpdate(2, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
            } else if (numberT.equals("-1")) {
                getBtnUpdate(-1, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
            }
//            }
//            if (FileUtil.loadString(context, Constants.SP_BTN_HUMIDITY) == null || FileUtil.loadString(context, Constants.SP_BTN_HUMIDITY).equals("")) {
//                getBtnUpdate(0, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
//            } else {
//                List<String> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_BTN_HUMIDITY), new TypeToken<List<String>>() {
//                }.getType());
            String numberH = DetailedControlActivity.humidity_set_btn.get(position);
            if (numberH.equals("0")) {
                getBtnUpdate(0, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
            } else if (numberH.equals("1")) {
                getBtnUpdate(1, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
            } else if (numberH.equals("2")) {
                getBtnUpdate(2, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
            } else if (numberH.equals("-1")) {
                getBtnUpdate(-1, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
            }
//            }


            viewHolder.temperature_btn_refrigeration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnUpdate(2, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
                    DetailedControlActivity.tempertaure_set_btn.set(position, "2");
                    temperatureFragmentAdapterUpDate.upDateBtn(position, 1, 2);
                }
            });
            viewHolder.temperature_btn_heating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnUpdate(1, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
                    DetailedControlActivity.tempertaure_set_btn.set(position, "1");
                    temperatureFragmentAdapterUpDate.upDateBtn(position, 1, 1);
                }
            });
            viewHolder.temperature_btn_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnUpdate(0, viewHolder.temperature_btn_off, viewHolder.temperature_btn_heating, viewHolder.temperature_btn_refrigeration);
                    DetailedControlActivity.tempertaure_set_btn.set(position, "0");
                    temperatureFragmentAdapterUpDate.upDateBtn(position, 1, 0);
                }
            });
            viewHolder.temperature_btn_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = DetailedControlActivity.tempertaure_set_numbers.get(position);
                    if (number > 0) {
                        number = number - 1;
                        viewHolder.temperature_number.setText(number + "");
                        DetailedControlActivity.tempertaure_set_numbers.set(position, Integer.parseInt(viewHolder.temperature_number.getText().toString()));
                        temperatureFragmentAdapterUpDate.upDate(position, 1, 2, number);
                    }
                }
            });
            viewHolder.temperature_btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Integer> list = new Gson().fromJson(FileUtil.loadString(context, Constants.SP_NUMBER_TEMPERATURE), new TypeToken<List<Integer>>() {
                    }.getType());
                    int number = DetailedControlActivity.tempertaure_set_numbers.get(position);
                    if (number < 40) {
                        number = number + 1;
                        viewHolder.temperature_number.setText(number + "");
                        DetailedControlActivity.tempertaure_set_numbers.set(position, Integer.parseInt(viewHolder.temperature_number.getText().toString()));
                        temperatureFragmentAdapterUpDate.upDate(position, 1, 1, number);
                    }
                }
            });


            viewHolder.humidity_btn_humidification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnUpdate(2, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
                    DetailedControlActivity.humidity_set_btn.set(position, "2");
                    temperatureFragmentAdapterUpDate.upDateBtn(position, 2, 2);
                }
            });
            viewHolder.humidity_btn_dehumidification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnUpdate(1, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
                    DetailedControlActivity.humidity_set_btn.set(position, "1");
                    temperatureFragmentAdapterUpDate.upDateBtn(position, 2, 1);
                }
            });
            viewHolder.humidity_btn_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBtnUpdate(0, viewHolder.humidity_btn_off, viewHolder.humidity_btn_dehumidification, viewHolder.humidity_btn_humidification);
                    DetailedControlActivity.humidity_set_btn.set(position, "0");
                    temperatureFragmentAdapterUpDate.upDateBtn(position, 2, 0);
                }
            });

            viewHolder.humidity_btn_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = DetailedControlActivity.humidity_set_numbers.get(position);
                    if (number > 0) {
                        number = number - 1;
                        viewHolder.humidity_number.setText(number + "");
                        DetailedControlActivity.humidity_set_numbers.set(position, Integer.parseInt(viewHolder.humidity_number.getText().toString()));
                        temperatureFragmentAdapterUpDate.upDate(position, 2, 2, number);
                    }
                }
            });
            viewHolder.humidity_btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = DetailedControlActivity.humidity_set_numbers.get(position);
                    if (number <= 100) {
                        number = number + 1;
                        viewHolder.humidity_number.setText(number + "");
                        DetailedControlActivity.humidity_set_numbers.set(position, Integer.parseInt(viewHolder.humidity_number.getText().toString()));
                        temperatureFragmentAdapterUpDate.upDate(position, 2, 1, number);
                    }
                }
            });
        }
        return convertView;
    }

    private void getBtnUpdate(int tag, TextView t_off, TextView t1, TextView t2) {
        switch (tag) {
            case -1:
                t_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t_off.setTextColor(Color.parseColor("#585858"));
                t1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t1.setTextColor(Color.parseColor("#585858"));
                t2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t2.setTextColor(Color.parseColor("#585858"));
                break;
            case 0:
                t_off.setBackgroundResource(R.drawable.radio_text_red_shape);
                t_off.setTextColor(Color.parseColor("#ffffff"));
                t1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t1.setTextColor(Color.parseColor("#585858"));
                t2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t2.setTextColor(Color.parseColor("#585858"));
                break;
            case 1:
                t_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t_off.setTextColor(Color.parseColor("#585858"));
                t1.setBackgroundResource(R.drawable.radio_text_blue_shape);
                t1.setTextColor(Color.parseColor("#ffffff"));
                t2.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t2.setTextColor(Color.parseColor("#585858"));
                break;
            case 2:
                t_off.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t_off.setTextColor(Color.parseColor("#585858"));
                t1.setBackgroundResource(R.drawable.radio_text_grap_shape);
                t1.setTextColor(Color.parseColor("#585858"));
                t2.setBackgroundResource(R.drawable.radio_text_blue_shape);
                t2.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    class ViewHolder {
        TextView temperature_room_name;//温度区域显示的房间名
        TextView temperature_txt;//显示当前温度
        TextView temperature_btn_refrigeration;//制冷按钮
        TextView temperature_btn_heating;//制热按钮
        TextView temperature_btn_off;//关闭温度调节按钮
        TextView temperature_btn_reduce;//温度减按钮
        TextView temperature_btn_add;//温度加按钮
        TextView temperature_number;//温度调节展示

        TextView humidity_room_name;//湿度区域显示的房间名
        TextView humidity_txt;//显示当前湿度
        TextView humidity_btn_humidification;//加湿按钮
        TextView humidity_btn_dehumidification;//除湿按钮
        TextView humidity_btn_off;//关闭湿度调节按钮
        TextView humidity_btn_reduce;//湿度减按钮
        TextView humidity_btn_add;//湿度加按钮
        TextView humidity_number;//湿度调节展示
    }

}
