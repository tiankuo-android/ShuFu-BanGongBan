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
import com.wfkj.android.smartoffice.adapter.AirFragmentAdapter;
import com.wfkj.android.smartoffice.model.sever_model.Categorie;
import com.wfkj.android.smartoffice.model.sever_model.Room;
import com.wfkj.android.smartoffice.my_interface.AirFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.my_interface.AirFragmentUpDateInterface;
import com.wfkj.android.smartoffice.util.Constants;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

/**
 * 空气净化
 * Created by wangdongyang on 16/11/21.
 */
@SuppressLint("ValidFragment")
public class AirFragment extends Fragment {

    private static final int DEFAULT_ITEMS_COUNT = 25;
    private static final int DEFAULT_RANDOM_VALUE_MIN = 10;
    private static final int DEFAULT_RANDOM_VALUE_MAX = 500;

    private float[] values;


    private CharterLine charterLine;
    private ListView air_listview;
    private TextView null_text;
    private  boolean flg = false;

    private AirFragmentAdapter adapter;
    private JSONObject jsonObject;


    private AirFragmentUpDateInterface airFragmentUpDateInterface;

    public AirFragment() {
    }

    public AirFragment(JSONObject jsonObject, AirFragmentUpDateInterface airFragmentUpDateInterface) {
        this.airFragmentUpDateInterface = airFragmentUpDateInterface;
        this.jsonObject = jsonObject;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_air, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        air_listview = (ListView) view.findViewById(R.id.air_listview);
        charterLine = (CharterLine) view.findViewById(R.id.air_fragment_charterline);
        null_text = (TextView) view.findViewById(R.id.null_text);

       List<com.wfkj.android.smartoffice.model.sever_model.Room> rooms = Constants.outHouse.getResult().getHouses().get(0).getRooms();

        for(Room room : rooms){
            if (room.getCategories()!=null){
                for(Categorie categorie:room.getCategories()){
                    if (categorie.getId() == 20){
                        flg = true;
                    }
                }
            }
        }

        if(flg){
            air_listview.setVisibility(View.VISIBLE);
            null_text.setVisibility(View.GONE);
            adapter = new AirFragmentAdapter(getActivity(),jsonObject,Constants.outHouse.getResult().getHouses().get(0).getRooms(), new AirFragmentAdapterUpDate() {
                @Override
                public void upDate(int position,int tag) {
                    airFragmentUpDateInterface.update(position,tag);
                }
            });
            air_listview.setAdapter(adapter);
        }else{
            air_listview.setVisibility(View.GONE);
            null_text.setVisibility(View.VISIBLE);
        }


    }

    public void upDate(JSONObject jsonObject) {
        if (flg){
            adapter.upDate(Constants.outHouse.getResult().getHouses().get(0).getRooms(),jsonObject);
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
