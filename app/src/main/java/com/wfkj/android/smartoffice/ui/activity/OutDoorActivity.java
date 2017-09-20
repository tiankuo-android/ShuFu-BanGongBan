package com.wfkj.android.smartoffice.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wfkj.android.smartoffice.MainActivity;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.weather.WeatherBean;
import com.wfkj.android.smartoffice.model.weather.sub.Daily_forecast;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.jzy_util.CmdCenter;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by wangdongyang on 16/11/27.
 */
public class OutDoorActivity extends BaseFragmentActivity implements OnClickListener {

    private WeatherBean weatherBean;

    private int weather_out_tag;
    private LoadingDialog loadingDialog;

    private ImageView head_back;//返回按钮
    private TextView head_title;//标题
    private ImageView head_img;

    private TextView outdoor_city;//城市
    private TextView outdoor_stareet;//具体地址
    private TextView outdoor_air_quality;//空气质量
    private TextView outdoor_temperature_txt;//当前温度
    private TextView outdoor_humidity_txt;//当前湿度
    private TextView outdoor_pm_txt;//当前pm
    private TextView doorout_week;//星期
    private TextView doorout_temperature_max;//最高温度
    private TextView doorout_temperature_min;//最低温度


    private ImageView list_img_1;//第一天天气图标
    private ImageView list_img_2;//第二天天气图标
    private ImageView list_img_3;//第三天天气图标
    private ImageView list_img_4;//第四天天气图标
    private ImageView list_img_5;//第五天天气图标
    private ImageView list_img_6;//第六天天气图标
    private ImageView list_img_7;//第七天天气图标

    private TextView list_week_1;//第一天星期
    private TextView list_week_2;//第二天星期
    private TextView list_week_3;//第三天星期
    private TextView list_week_4;//第四天星期
    private TextView list_week_5;//第五天星期
    private TextView list_week_6;//第六天星期
    private TextView list_week_7;//第七天星期

    private TextView list_max_1;//第一天最高温
    private TextView list_max_2;//第二天最高温
    private TextView list_max_3;//第三天最高温
    private TextView list_max_4;//第四天最高温
    private TextView list_max_5;//第五天最高温
    private TextView list_max_6;//第六天最高温
    private TextView list_max_7;//第七天最高温

    private TextView list_min_1;//第一天最低温
    private TextView list_min_2;//第二天最低温
    private TextView list_min_3;//第三天最低温
    private TextView list_min_4;//第四天最低温
    private TextView list_min_5;//第五天最低温
    private TextView list_min_6;//第六天最低温
    private TextView list_min_7;//第七天最低温

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        init();

    }

    private void init() {
        weather_out_tag = 1;
        loadingDialog = new LoadingDialog(OutDoorActivity.this);
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);

        outdoor_city = (TextView) this.findViewById(R.id.outdoor_city);
        outdoor_stareet = (TextView) this.findViewById(R.id.outdoor_stareet);
        outdoor_air_quality = (TextView) this.findViewById(R.id.outdoor_air_quality);
        outdoor_temperature_txt = (TextView) this.findViewById(R.id.outdoor_temperature_txt);
        outdoor_humidity_txt = (TextView) this.findViewById(R.id.outdoor_humidity_txt);
        outdoor_pm_txt = (TextView) this.findViewById(R.id.outdoor_pm_txt);
        doorout_week = (TextView) this.findViewById(R.id.doorout_week);
        doorout_temperature_max = (TextView) this.findViewById(R.id.doorout_temperature_max);
        doorout_temperature_min = (TextView) this.findViewById(R.id.doorout_temperature_min);

        list_img_1 = (ImageView) this.findViewById(R.id.list_img_1);
        list_img_2 = (ImageView) this.findViewById(R.id.list_img_2);
        list_img_3 = (ImageView) this.findViewById(R.id.list_img_3);
        list_img_4 = (ImageView) this.findViewById(R.id.list_img_4);
        list_img_5 = (ImageView) this.findViewById(R.id.list_img_5);
        list_img_6 = (ImageView) this.findViewById(R.id.list_img_6);
        list_img_7 = (ImageView) this.findViewById(R.id.list_img_7);

        list_week_1 = (TextView) this.findViewById(R.id.list_week_1);
        list_week_2 = (TextView) this.findViewById(R.id.list_week_2);
        list_week_3 = (TextView) this.findViewById(R.id.list_week_3);
        list_week_4 = (TextView) this.findViewById(R.id.list_week_4);
        list_week_5 = (TextView) this.findViewById(R.id.list_week_5);
        list_week_6 = (TextView) this.findViewById(R.id.list_week_6);
        list_week_7 = (TextView) this.findViewById(R.id.list_week_7);

        list_max_1 = (TextView) this.findViewById(R.id.list_max_1);
        list_max_2 = (TextView) this.findViewById(R.id.list_max_2);
        list_max_3 = (TextView) this.findViewById(R.id.list_max_3);
        list_max_4 = (TextView) this.findViewById(R.id.list_max_4);
        list_max_5 = (TextView) this.findViewById(R.id.list_max_5);
        list_max_6 = (TextView) this.findViewById(R.id.list_max_6);
        list_max_7 = (TextView) this.findViewById(R.id.list_max_7);

        list_min_1 = (TextView) this.findViewById(R.id.list_min_1);
        list_min_2 = (TextView) this.findViewById(R.id.list_min_2);
        list_min_3 = (TextView) this.findViewById(R.id.list_min_3);
        list_min_4 = (TextView) this.findViewById(R.id.list_min_4);
        list_min_5 = (TextView) this.findViewById(R.id.list_min_5);
        list_min_6 = (TextView) this.findViewById(R.id.list_min_6);
        list_min_7 = (TextView) this.findViewById(R.id.list_min_7);

        head_img.setVisibility(View.GONE);
        head_title.setText("户外状况");
        head_back.setOnClickListener(this);
        loadingDialog.show();
        getWeather("haidian");

        if (mXpgWifiDevice == null) {
            Intent intent = new Intent();
            intent.setClass(OutDoorActivity.this, LogInActivity.class);
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


    private void getWeather(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.WEATHER_URL_START)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(RetrofitAPIManager.getOkHttpClient())
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<JsonObject> call = httpInterface.getWeather(cityName, Constants.HeWeatherApiKey);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("----weather--success----");
                loadingDialog.dismiss();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
//                    JsonArray jsonArray = response.body().getAsJsonArray("HeWeather data service 3.0");
                    JsonArray jsonArray = response.body().getAsJsonArray("HeWeather5");
                    List<WeatherBean> list = gson.fromJson(jsonArray.toString(), new TypeToken<List<WeatherBean>>() {
                    }.getType());
                    weatherBean = list.get(0);
                    FileUtil.saveString(OutDoorActivity.this, Constants.SP_WEATHER, weatherBean.toString());
                    getUpDateUi();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loadingDialog.dismiss();
                System.out.println("---@--failure--@---" + t.getMessage());
            }
        });
    }


    private void getUpDateUi() {

        if (weatherBean == null || weatherBean.getSuggestion() == null ||
                weatherBean.getNow() == null ||
                weatherBean.getAqi() == null) {
            return;
        }

        outdoor_air_quality.setText("空气质量：" + weatherBean.getSuggestion().getAir().getBrf());
        outdoor_temperature_txt.setText(weatherBean.getNow().getTmp() + "");
        outdoor_humidity_txt.setText(weatherBean.getNow().getHum() + "");
        outdoor_pm_txt.setText(weatherBean.getAqi().getCity().getPm25() + "");
        doorout_week.setText(AppUtils.getWeekL(weatherBean.getBasic().getUpdate().getLoc()));
        doorout_temperature_max.setText("最高 " + weatherBean.getDaily_forecast().get(0).getTmp().getMax());
        doorout_temperature_min.setText("最低 " + weatherBean.getDaily_forecast().get(0).getTmp().getMin());

        for (int i = 0; i < weatherBean.getDaily_forecast().size(); i++) {
            Daily_forecast daily_forecast = weatherBean.getDaily_forecast().get(i);

            switch (i) {
                case 0:
                    list_week_1.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_1.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_1.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_1, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
                case 1:
                    list_week_2.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_2.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_2.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_2, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
                case 2:
                    list_week_3.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_3.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_3.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_3, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
                case 3:
                    list_week_4.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_4.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_4.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_4, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
                case 4:
                    list_week_5.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_5.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_5.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_5, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
                case 5:
                    list_week_6.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_6.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_6.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_6, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
                case 6:
                    list_week_7.setText(AppUtils.getWeek(daily_forecast.getDate()));
                    list_max_7.setText("最高 " + daily_forecast.getTmp().getMax());
                    list_min_7.setText("最低 " + daily_forecast.getTmp().getMin());
                    setWeatherImg(list_img_7, Integer.parseInt(daily_forecast.getCond().getCode_d()));
                    break;
            }
        }
    }

    private void setWeatherImg(ImageView view, int weatherStr) {
        if (weatherStr == 100) {
            view.setImageResource(R.mipmap.sunny_status_ic);
        } else if (weatherStr >= 101 && weatherStr <= 103) {
            view.setImageResource(R.mipmap.cloudy_status_ic);
        } else if (weatherStr == 104) {
            view.setImageResource(R.mipmap.cloudy_sky_status_ic);
        } else if (weatherStr >= 200 && weatherStr <= 213) {
            view.setImageResource(R.mipmap.haze_status_ic);
        } else if (weatherStr >= 300 && weatherStr <= 313) {
            view.setImageResource(R.mipmap.rain_status_ic);
        } else if (weatherStr >= 400 && weatherStr <= 407) {
            view.setImageResource(R.mipmap.snow_status_ic);
        } else if (weatherStr >= 500 && weatherStr <= 508) {
            view.setImageResource(R.mipmap.haze_status_ic);
        } else {
            view.setImageResource(R.mipmap.cloudy_status_ic);
        }
    }


    @Override
    protected void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> dataMap, int sn) {
        super.didReceiveData(result, device, dataMap, sn);
        if (isFinishing())
            return;
        if (dataMap != null) {
            if (weather_out_tag++ > 10) {
                weather_out_tag = 1;
                getWeather("haidian");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_back://返回
                this.finish();
                break;
        }
    }
}
