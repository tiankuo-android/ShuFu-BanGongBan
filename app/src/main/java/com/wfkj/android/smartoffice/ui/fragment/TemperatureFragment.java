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

import com.hrules.charter.CharterLine;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.adapter.TemperatureFragmentAdapter;
import com.wfkj.android.smartoffice.model.sever_model.Categorie;
import com.wfkj.android.smartoffice.model.sever_model.Room;
import com.wfkj.android.smartoffice.my_interface.TemperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.my_interface.TemperatureFragmentUpDate;
import com.wfkj.android.smartoffice.util.Constants;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

/**
 * 温度湿度
 * Created by wangdongyang on 16/11/21.
 */
@SuppressLint("ValidFragment")
public class TemperatureFragment extends Fragment {
    private static final int DEFAULT_ITEMS_COUNT = 25;
    private static final int DEFAULT_RANDOM_VALUE_MIN = 10;
    private static final int DEFAULT_RANDOM_VALUE_MAX = 500;
    private CharterLine charterLine;
    private boolean flg = false;
    private float[] values;

    private ListView temperature_listview;
    private TextView null_text;
    private TemperatureFragmentAdapter adapter;

    private TemperatureFragmentUpDate temperatureFragmentUpDate;
    private JSONObject jsonObject;

    public TemperatureFragment(JSONObject jsonObject, TemperatureFragmentUpDate temperatureFragmentUpDate) {
        this.temperatureFragmentUpDate = temperatureFragmentUpDate;
        this.jsonObject = jsonObject;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        charterLine = (CharterLine) view.findViewById(R.id.temperature_fragment_charterline);
        temperature_listview = (ListView) view.findViewById(R.id.temperature_listview);
        null_text = (TextView) view.findViewById(R.id.null_text);

        List<Room> rooms = Constants.outHouse.getResult().getHouses().get(0).getRooms();

        for (Room room : rooms) {
            if (room.getCategories() != null) {
                for (Categorie categorie : room.getCategories()) {
                    if (categorie.getId() == 30) {
                        flg = true;
                    }
                }
            }
        }

        if (flg) {
            temperature_listview.setVisibility(View.VISIBLE);
            null_text.setVisibility(View.GONE);
            adapter = new TemperatureFragmentAdapter(getActivity(), jsonObject, Constants.outHouse.getResult().getHouses().get(0).getRooms(), new TemperatureFragmentAdapterUpDate() {
                @Override
                public void upDate(int position, int flg, int tag, int number) {
                    temperatureFragmentUpDate.upDate(position, flg, tag, number);
                }

                @Override
                public void upDateBtn(int position, int flg, int tag) {
                    temperatureFragmentUpDate.upDateBtn(position, flg, tag);
                }
            });
            temperature_listview.setAdapter(adapter);
        } else {
            temperature_listview.setVisibility(View.GONE);
            null_text.setVisibility(View.VISIBLE);
        }


    }

    public void upDate(JSONObject jsonObject) {
        if (flg) {
            adapter.upDate(Constants.outHouse.getResult().getHouses().get(0).getRooms(), jsonObject);
            adapter.notifyDataSetChanged();
        }
    }

    private float[] fillRandomValues(int length, int max, int min) {
        Random random = new Random();

        float[] newRandomValues = new float[length];
        for (int i = 0; i < newRandomValues.length; i++) {
            newRandomValues[i] = random.nextInt(max - min + 1) - min;
        }
        return newRandomValues;
    }

}
