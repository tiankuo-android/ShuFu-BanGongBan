package com.wfkj.android.smartoffice.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 电量消耗
 * Created by wangdongyang on 16/11/21.
 */
@SuppressLint("ValidFragment")
public class ElectricityFragment extends Fragment{

    private TextView sum_number;
    private TextView none_txt;
    private LinearLayout ele_layout;

    private JSONObject jsonObject;
    private ArrayList<Device> devices;

    public ElectricityFragment(JSONObject jsonObject, ArrayList<Device> devices) {
        this.jsonObject = jsonObject;
        this.devices = devices;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electrictity,container,false);
        init(view);
        return  view;
    }

    private void init(View view){
        sum_number = (TextView) view.findViewById(R.id.sum_number);
        none_txt = (TextView) view.findViewById(R.id.none_txt);
        ele_layout = (LinearLayout) view.findViewById(R.id.ele_layout);
        getData();
    }

    private void getData(){
        if(devices!=null && devices.size()>0) {
            if(ele_layout!=null && none_txt!=null){
                ele_layout.setVisibility(View.VISIBLE);
                none_txt.setVisibility(View.GONE);

                DecimalFormat df = new DecimalFormat("0.00");
                double electricityNumber = 0;
                for (Device device : devices) {
                    try {
                        double number = (double)(Integer)jsonObject.get(device.getExtid())/100;
                        electricityNumber += number;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sum_number.setText(df.format(electricityNumber)+"");
            }


    }else{
            if(ele_layout!=null && none_txt!=null){
                ele_layout.setVisibility(View.GONE);
                none_txt.setVisibility(View.VISIBLE);
            }

        }
    }

    public void upDate(JSONObject jsonObject){
        this.jsonObject = jsonObject;
        getData();
    }

}
