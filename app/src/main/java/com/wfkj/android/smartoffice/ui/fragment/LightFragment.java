package com.wfkj.android.smartoffice.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.adapter.LightFragmentAdapter;
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.util.Constants;

import org.json.JSONObject;

/**
 * 灯光窗帘
 * Created by wangdongyang on 16/11/21.
 */
@SuppressLint("ValidFragment")
public class LightFragment extends Fragment {

    private LightperatureFragmentAdapterUpDate lightperatureFragmentAdapterUpDate;

    private ListView fragment_light_listview;
    private TextView fragment_light_txt;
    private LightFragmentAdapter adapter;
    private JSONObject jsonObject;

    public LightFragment(JSONObject jsonObject,LightperatureFragmentAdapterUpDate lightperatureFragmentAdapterUpDate) {
        this.lightperatureFragmentAdapterUpDate = lightperatureFragmentAdapterUpDate;
        this.jsonObject = jsonObject;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        fragment_light_listview = (ListView) view.findViewById(R.id.fragment_light_listview);
        fragment_light_txt = (TextView) view.findViewById(R.id.fragment_light_txt);

        if (Constants.house.getRooms() != null && Constants.house.getRooms().size() > 0) {
            adapter = new LightFragmentAdapter(getActivity(), jsonObject, Constants.outHouse.getResult().getHouses().get(0).getRooms(), new LightperatureFragmentAdapterUpDate() {
                @Override
                public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                    lightperatureFragmentAdapterUpDate.upDate(roomPosition,deviceId,flg,tag,number,colorNumber);
                }
            });
            fragment_light_listview.setAdapter(adapter);
            fragment_light_txt.setVisibility(View.GONE);
        }


    }

    public void upDate(JSONObject jsonObject) {
        if (adapter!=null){
            adapter.upDate(Constants.outHouse.getResult().getHouses().get(0).getRooms(),jsonObject);
        }

    }

}
