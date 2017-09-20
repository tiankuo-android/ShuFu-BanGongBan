package com.wfkj.android.smartoffice.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.google.gson.Gson;
import com.wfkj.android.smartoffice.MainActivity;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.Categorie;
import com.wfkj.android.smartoffice.model.sever_model.Device;
import com.wfkj.android.smartoffice.model.sever_model.Room;
import com.wfkj.android.smartoffice.my_interface.AirFragmentUpDateInterface;
import com.wfkj.android.smartoffice.my_interface.LightperatureFragmentAdapterUpDate;
import com.wfkj.android.smartoffice.my_interface.TemperatureFragmentUpDate;
import com.wfkj.android.smartoffice.ui.fragment.AirFragment;
import com.wfkj.android.smartoffice.ui.fragment.ElectricityFragment;
import com.wfkj.android.smartoffice.ui.fragment.LightFragment;
import com.wfkj.android.smartoffice.ui.fragment.SecurityFragment;
import com.wfkj.android.smartoffice.ui.fragment.TemperatureFragment;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.jzy_util.CmdCenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 详细控制页
 * Created by wangdongyang on 16/11/21.
 */
public class DetailedControlActivity extends BaseFragmentActivity implements OnPageChangeListener, OnClickListener {

    public static ArrayList<Integer> tempertaure_set_numbers;
    public static ArrayList<Integer> humidity_set_numbers;
    public static ArrayList<String> tempertaure_set_btn;
    public static ArrayList<String> humidity_set_btn;
    public static ArrayList<String> air_set_numbers;

    private int bannerTag;

    public static ArrayList<Device> devices;
    private ArrayList<Device> eleDevices;

    private Device co2Device = null;
    private Device pm25Device = null;
    private Device vocDevice = null;
    private Device HCHODevice = null;

    private ArrayList<Device> airDevices = null;
    private ArrayList<Device> temperatureDevices = null;
    private ArrayList<Device> LightDevices = null;
    private ArrayList<Device> securityDevices = null;
    private ArrayList<Device> electricityDevices = null;

    private JSONObject jsonObject;

    private NavigationTabStrip navigationTabStrip;
    private ViewPager detailed_viewpager;
    private List<Fragment> fragments;
    private AirFragment airFragment;
    private TemperatureFragment temperatureFragment;
    private ElectricityFragment electricityFragment;
    private LightFragment lightFragment;
    private SecurityFragment securityFragment;
    private Gson gson = new Gson();

    private TextView title;
    private ImageView head_back;//返回按钮
    private ImageView head_img;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int position = (int) msg.getData().get("position");
            com.wfkj.android.smartoffice.model.sever_model.Room room = Constants.outHouse.getResult().getHouses().get(0).getRooms().get(position);
            ArrayList<Categorie> categories = room.getCategories();

            Categorie categorieAir = null;
            Categorie categorieTemperature = null;
            Categorie categorieLight = null;
            Categorie categorieSecurity = null;
            Categorie categorieElectricity = null;
            for (Categorie categorie : categories) {
                switch (categorie.getId()) {
                    case 20://空气净化
                        categorieAir = categorie;
                        airDevices = categorieAir.getDevices();
                        break;
                    case 30://温度湿度
                        categorieTemperature = categorie;
                        temperatureDevices = categorieTemperature.getDevices();
                        break;
                    case 40://电量消耗
                        categorieElectricity = categorie;
                        electricityDevices = categorieElectricity.getDevices();
                        break;
                    case 50://灯光窗帘
                        categorieLight = categorie;
                        LightDevices = categorieLight.getDevices();
                        break;
                    case 60: //办公安放
                        categorieSecurity = categorie;
                        securityDevices = categorieSecurity.getDevices();
                        break;
                    case 70://综合策略
                        break;
                }
            }

            if (airDevices != null) {
                for (Device device : airDevices) {
                    if (device.getId() == 16) {
                        co2Device = device;
                    }
                    if (device.getId() == 17) {
                        HCHODevice = device;
                    }
                    if (device.getId() == 18) {
                        pm25Device = device;
                    }
                    if (device.getId() == 19) {
                        vocDevice = device;
                    }
                }
            }

            switch (msg.what) {
                case 0:
                    setAir(9998, 998, 9998, 9998);
                    break;
                case 1:
                    setAir(700, 50, 50, 8);
                    break;
                case 2:
                    setAir(700, 30, 30, 5);
                    break;
                case 3:
                    setAir(500, 8, 20, 3);
                    break;
                case 11:
                    Device tDevice = null;
                    for (Device device : temperatureDevices) {
                        if (device.getId() == 21) {
                            tDevice = device;
                        }
                    }
                    if (tDevice != null) {
                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), tDevice.getExtid(), msg.arg1 * 10);
                    }
                    break;
                case 12:
                    Device hDevice = null;
                    for (Device device : temperatureDevices) {
                        if (device.getId() == 20) {
                            hDevice = device;
                        }
                    }
                    if (hDevice != null) {
                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), hDevice.getExtid(), msg.arg1 * 10);
                    }

                    break;
                case 21://tag: 0-关闭 1-制冷 2-制热
                    Device temperaturePatternDevice = null;
                    for (Device device : temperatureDevices) {
                        if (device.getId() == 57) {
                            temperaturePatternDevice = device;
                        }
                    }
                    switch (msg.arg1) {
                        case 0:
//                            CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), "event_temperature", 998);
                            Device t2Device = null;
                            for (Device device : temperatureDevices) {
                                if (device.getId() == 20) {
                                    t2Device = device;
                                }
                            }
                            if (t2Device != null) {
                                CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), t2Device.getExtid(), 998);
                            }
                            break;
                        case 1:
                            if (temperaturePatternDevice != null) {
                                CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), temperaturePatternDevice.getExtid(), true);
                            }
                            break;
                        case 2:
                            if (temperaturePatternDevice != null) {
                                CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), temperaturePatternDevice.getExtid(), false);
                            }
                            break;
                    }
                    break;
                case 22://tag: 0-关闭 1-除湿 2-加湿
                    switch (msg.arg1) {
                        case 0:
//                            CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), "event_humidity", 998);
                            Device h2Device = null;
                            for (Device device : temperatureDevices) {
                                if (device.getId() == 20) {
                                    h2Device = device;
                                }
                            }
                            if (h2Device != null) {
                                CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), h2Device.getExtid(), 998);
                            }
                            break;
                        case 1:
                            CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), "infrared_air_conditioner", 53);
                            break;
                        case 2:
                            CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), "modbus_humidifier", 1);
                            break;
                    }
                    break;

                case 30://灯光窗帘
                    System.out.println("--收到回调--____________-----------------");
                    int roomPosition = (int) msg.getData().get("roomPosition");
                    int deviceId = (int) msg.getData().get("deviceId");
                    int flg = (int) msg.getData().get("flg");
                    int tag = (int) msg.getData().get("tag");
                    int number = (int) msg.getData().get("number");
                    int colorNumber = (int) msg.getData().get("colorNumber");


                    Room roomLight = Constants.outHouse.getResult().getHouses().get(0).getRooms().get(roomPosition);
                    ArrayList<Categorie> categoriesLights = roomLight.getCategories();
                    Categorie categorieL = null;
                    if (categoriesLights != null && categoriesLights.size() > 0) {
                        for (Categorie categorie : categoriesLights) {
                            if (categorie.getId() == 50) {
                                categorieL = categorie;
                            }
                        }
                    }
                    Device deviceLight = null;
                    if (categorieL != null && categorieL.getDevices() != null && categorieL.getDevices().size() > 0) {
                        for (Device device : categorieL.getDevices()) {
                            if (device.getId() == deviceId) {
                                deviceLight = device;
                            }
                        }

                    }
                    switch (flg) {//1-普通灯  2-彩灯  3-窗帘 4-窗户
                        case 1:

                            switch (tag) {//tag 1-关    2-开  3-自动 4-关暂停   5-开暂停
                                case 1:
                                    if (deviceLight != null) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), deviceLight.getExtid(), false);
                                    }
                                    break;
                                case 2:
                                    if (deviceLight != null) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), deviceLight.getExtid(), true);
                                    }
                                    break;
                                case 3:
                                    break;
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            JSONObject jsonObjectW = null;
                            String openStrW = null;
                            String closeStrW = null;
                            if (deviceLight != null) {
                                try {
                                    jsonObject = new JSONObject(deviceLight.getDescription());
                                    if (jsonObject != null) {
                                        openStrW = jsonObject.getString("open");
                                        closeStrW = jsonObject.getString("close");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            switch (tag) {
                                case 1:
                                    if (closeStrW != null && !closeStrW.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), closeStrW, true);

                                    }
                                    break;
                                case 2:
                                    if (openStrW != null && !openStrW.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), openStrW, true);

                                    }
                                    break;
                                case 4:
                                    if (closeStrW != null && !closeStrW.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), closeStrW, false);

                                    }

                                    break;
                                case 5:
                                    if (openStrW != null && !openStrW.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), openStrW, false);

                                    }

                                    break;


                            }

                            break;
                        case 4:
                            JSONObject jsonObject = null;
                            String openStr = null;
                            String closeStr = null;
                            if (deviceLight != null) {
                                try {
                                    jsonObject = new JSONObject(deviceLight.getDescription());
                                    if (jsonObject != null) {
                                        openStr = jsonObject.getString("open");
                                        closeStr = jsonObject.getString("close");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            switch (tag) {
                                case 1:
                                    if (closeStr != null && !closeStr.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), closeStr, true);

                                    }
                                    break;
                                case 2:
                                    if (openStr != null && !openStr.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), openStr, true);

                                    }
                                    break;
                                case 4:
                                    if (closeStr != null && !closeStr.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), closeStr, false);

                                    }

                                    break;
                                case 5:
                                    if (openStr != null && !openStr.equals("")) {
                                        CmdCenter.getInstance(getApplicationContext()).cWrite(CmdCenter.getInstance(getApplicationContext()).getXpgWifiDevice(), openStr, false);

                                    }

                                    break;

                            }


                    }

                    break;
            }
        }
    };


    private void setAir(int co2Number, int pm25, int voc, int hcho) {
        if (co2Device != null) {
            CmdCenter.getInstance(DetailedControlActivity.this).cWrite(CmdCenter.getInstance(DetailedControlActivity.this).getXpgWifiDevice(), co2Device.getExtid(), co2Number);
        }
        if (pm25Device != null) {
            CmdCenter.getInstance(DetailedControlActivity.this).cWrite(CmdCenter.getInstance(DetailedControlActivity.this).getXpgWifiDevice(), pm25Device.getExtid(), pm25);
        }
        if (vocDevice != null) {
            CmdCenter.getInstance(DetailedControlActivity.this).cWrite(CmdCenter.getInstance(DetailedControlActivity.this).getXpgWifiDevice(), vocDevice.getExtid(), voc);
        }
        if (HCHODevice != null) {
            CmdCenter.getInstance(DetailedControlActivity.this).cWrite(CmdCenter.getInstance(DetailedControlActivity.this).getXpgWifiDevice(), HCHODevice.getExtid(), hcho);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_contorl);
        init();
    }

    private void init() {
        bannerTag = getIntent().getIntExtra("BANNERTAG",0);
        eleDevices = (ArrayList<Device>) getIntent().getBundleExtra("ELEBUNDLE").getSerializable("ELECTRICITYDEVICES");
        devices = new ArrayList<>();

        for (Room room : Constants.outHouse.getResult().getHouses().get(0).getRooms()) {
            if (room.getCategories() != null && room.getCategories().size() > 0) {
                for (Categorie categorie : room.getCategories()) {
                    if (categorie.getId() == 50) {
                        if (categorie.getDevices() != null && categorie.getDevices().size() > 0) {
                            for (Device device : categorie.getDevices()) {
                                if (device.getSub_category() == 53) {
                                    devices.add(device);
                                }
                            }
                        }
                    }
                }
            }
        }

        tempertaure_set_numbers = new ArrayList<>();
        humidity_set_numbers = new ArrayList<>();
        tempertaure_set_btn = new ArrayList<>();
        humidity_set_btn = new ArrayList<>();
        air_set_numbers = new ArrayList<>();
        for (com.wfkj.android.smartoffice.model.sever_model.Room room : Constants.outHouse.getResult().getHouses().get(0).getRooms()) {
            tempertaure_set_numbers.add(22);
            humidity_set_numbers.add(50);
            tempertaure_set_btn.add("-1");
            humidity_set_btn.add("-1");
            air_set_numbers.add("-1");
        }

        try {
            jsonObject = new JSONObject(FileUtil.loadString(getApplication(), Constants.JZYSTR)).getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        navigationTabStrip = (NavigationTabStrip) this.findViewById(R.id.tab_layout);
        detailed_viewpager = (ViewPager) this.findViewById(R.id.detailed_viewpager);
        title = (TextView) this.findViewById(R.id.head_title);
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_img = (ImageView) this.findViewById(R.id.head_img);

        head_img.setVisibility(View.GONE);
        head_back.setOnClickListener(this);
        title.setText("详细控制");

        setTab();

        fragments = new ArrayList<Fragment>();
        airFragment = new AirFragment(jsonObject, new AirFragmentUpDateInterface() {
            @Override
            public void update(int position, int tag) {
                switch (tag) {
                    case 0://关闭
                        Message msg = new Message();
                        msg.what = 0;
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);

                        break;
                    case 1://良
                        Message msg1 = new Message();
                        msg1.what = 1;
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("position", position);
                        msg1.setData(bundle1);
                        mHandler.sendMessage(msg1);

                        break;
                    case 2://优
                        Message msg2 = new Message();
                        msg2.what = 2;
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("position", position);
                        msg2.setData(bundle2);
                        mHandler.sendMessage(msg2);

                    case 3://极优
                        Message msg3 = new Message();
                        msg3.what = 3;
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("position", position);
                        msg3.setData(bundle3);
                        mHandler.sendMessage(msg3);

                        break;
                }
            }
        });
        temperatureFragment = new TemperatureFragment(jsonObject, new TemperatureFragmentUpDate() {
            @Override
            public void upDate(int position, int flg, int tag, int number) {
                switch (flg) {
                    case 1://温度
                        Message msg1 = new Message();
                        msg1.what = 11;
                        msg1.arg1 = number;
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        msg1.setData(bundle);
                        mHandler.sendMessage(msg1);
                        break;
                    case 2://湿度
                        Message msg2 = new Message();
                        msg2.what = 12;
                        msg2.arg1 = number;
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("position", position);
                        msg2.setData(bundle2);
                        mHandler.sendMessage(msg2);
                        break;
                }
            }

            @Override
            public void upDateBtn(int position, int flg, int tag) {

                switch (flg) {
                    case 1://温度
                        Message msg1 = new Message();
                        msg1.what = 21;
                        msg1.arg1 = tag;
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("position", position);
                        msg1.setData(bundle3);
                        mHandler.sendMessage(msg1);
                        break;
                    case 2://湿度
                        Message msg2 = new Message();
                        msg2.what = 22;
                        msg2.arg1 = tag;
                        Bundle bundle4 = new Bundle();
                        bundle4.putInt("position", position);
                        msg2.setData(bundle4);
                        mHandler.sendMessage(msg2);
                        break;
                }

            }
        });
        electricityFragment = new ElectricityFragment(jsonObject, eleDevices);
        lightFragment = new LightFragment(jsonObject, new LightperatureFragmentAdapterUpDate() {
            @Override
            public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {
                Message msg1 = new Message();
                msg1.what = 30;
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position", 0);
                bundle1.putInt("roomPosition", roomPosition);
                bundle1.putInt("deviceId", deviceId);
                bundle1.putInt("flg", flg);
                bundle1.putInt("tag", tag);
                bundle1.putInt("number", number);
                bundle1.putInt("colorNumber", colorNumber);
                msg1.setData(bundle1);
                mHandler.sendMessage(msg1);
            }
        });
        securityFragment = new SecurityFragment(jsonObject, new LightperatureFragmentAdapterUpDate() {
            @Override
            public void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber) {

            }
        });

        fragments.add(airFragment);
        fragments.add(temperatureFragment);
        fragments.add(electricityFragment);
        fragments.add(lightFragment);
        fragments.add(securityFragment);


        detailed_viewpager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(), fragments));
        detailed_viewpager.setCurrentItem(0);
        detailed_viewpager.setOnPageChangeListener(this);
        detailed_viewpager.setCurrentItem(bannerTag);

        if (mXpgWifiDevice == null) {
            Intent intent = new Intent();
            intent.setClass(DetailedControlActivity.this, LogInActivity.class);
            startActivity(intent);
            if (MainActivity.mainActivity != null) {
                MainActivity.mainActivity.finish();
            }
            this.finish();
        } else {
            mXpgWifiDevice.setListener(deviceListener);
            CmdCenter.getInstance(this).cGetStatus(mXpgWifiDevice);
        }


    }

    /**
     * 设置tab属性
     */
    private void setTab() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int W = mDisplayMetrics.widthPixels;
        int H = mDisplayMetrics.heightPixels;

        if (H >= 1910) {
            navigationTabStrip.setTitleSize(40);
        } else if (H >= 1270 && H < 1910) {
            navigationTabStrip.setTitleSize(30);
        } else if (H < 1270 && H >= 850) {
            navigationTabStrip.setTitleSize(20);
        }

        navigationTabStrip.setTitles("空气净化", "温度湿度", "电量消耗", "灯光窗帘", "办公安防");
        navigationTabStrip.setTabIndex(0, true);

        navigationTabStrip.setStripColor(Color.WHITE);
        navigationTabStrip.setStripWeight(6);
        navigationTabStrip.setStripFactor(2);
        navigationTabStrip.setStripType(NavigationTabStrip.StripType.LINE);
        navigationTabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        navigationTabStrip.setCornersRadius(3);
        navigationTabStrip.setAnimationDuration(0);
        navigationTabStrip.setInactiveColor(Color.WHITE);
        navigationTabStrip.setActiveColor(Color.WHITE);

        navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {

            }

            @Override
            public void onEndTabSelected(String title, int index) {
                detailed_viewpager.setCurrentItem(index);

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        navigationTabStrip.setTabIndex(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_back://返回
                this.finish();
                break;
        }
    }


    public class MyFragmentPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    protected void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> dataMap, int sn) {
        super.didReceiveData(result, device, dataMap, sn);

        if (isFinishing())
            return;
//        System.out.println("-------datamap------2----" + dataMap.toString());


        if (dataMap != null && !dataMap.toString().equals("{}")) {

//            System.out.println("-----sn------" + sn);

            String str = gson.toJson(dataMap);

//            System.out.println("-------str---2------" + str);
            try {
                if (str != null && !str.equals("")) {

                    jsonObject = new JSONObject(str).getJSONObject("data");
//                    for (Room room : Constants.house.getRooms()) {
//                        for (Equipment equipment : room.getEquipments()) {
//                            equipment.setNumber(jsonObject.get(equipment.getTag()).toString());
//                        }
//                    }

//                    AppUtils.upDateHouse(jsonObject);

//                    System.out.println("-----sys-----" + Constants.house.toString());
                    FileUtil.saveString(DetailedControlActivity.this, Constants.JZYSTR, str);
                    airFragment.upDate(jsonObject);
                    temperatureFragment.upDate(jsonObject);
                    lightFragment.upDate(jsonObject);
                    securityFragment.upDate(jsonObject);
                    electricityFragment.upDate(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
        super.didDiscovered(result, deviceList);
    }
}
