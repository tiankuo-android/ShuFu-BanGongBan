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
import com.wfkj.android.smartoffice.adapter.SecurityFragmentAdapter;
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.util.Constants;

import org.json.JSONObject;

/**
 * 办公安防
 * Created by wangdongyang on 16/11/21.
 */
@SuppressLint("ValidFragment")
public class SecurityFragment extends Fragment{

    private LightperatureFragmentAdapterUpDate lightperatureFragmentAdapterUpDate;
    private SecurityFragmentAdapter adapter;
    private JSONObject jsonObject;

    private TextView fragment_security_txt;
    private ListView fragment_security_listview;

    public SecurityFragment(JSONObject jsonObject,LightperatureFragmentAdapterUpDate lightperatureFragmentAdapterUpDate) {
        this.lightperatureFragmentAdapterUpDate = lightperatureFragmentAdapterUpDate;
        this.jsonObject = jsonObject;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security,container,false);
        init(view);
        return  view;
    }

    private void init(View view){
        fragment_security_txt = (TextView) view.findViewById(R.id.fragment_security_txt);
        fragment_security_listview = (ListView) view.findViewById(R.id.fragment_security_listview);

        if (Constants.house.getRooms() != null && Constants.house.getRooms().size() > 0) {
            adapter = new SecurityFragmentAdapter(getActivity(), jsonObject, Constants.outHouse.getResult().getHouses().get(0).getRooms(), new LightperatureFragmentAdapterUpDate() {
                @Override
                public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                    lightperatureFragmentAdapterUpDate.upDate(roomPosition,deviceId,flg,tag,number,colorNumber);
                }
            });
            fragment_security_listview.setAdapter(adapter);
            fragment_security_txt.setVisibility(View.GONE);
        }
    }

    public void upDate(JSONObject jsonObject) {
        if (adapter!=null){
            adapter.upDate(Constants.outHouse.getResult().getHouses().get(0).getRooms(),jsonObject);
        }

    }

}
