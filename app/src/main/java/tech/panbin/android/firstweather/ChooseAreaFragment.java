package tech.panbin.android.firstweather;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.panbin.android.firstweather.db.City;
import tech.panbin.android.firstweather.db.Country;
import tech.panbin.android.firstweather.db.Province;

/**
 * Created by PanBin on 2018/01/13.
 */

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTRY = 2;

    private ProgressDialog progressDialog;

    private TextView titleText;

    private Button backButton;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

//    省列表
    private List<Province> provinceList;

//    市列表
    private List<City> cityList;

//    县区列表
    private List<Country> countryList;

//    选中的省份
    private  Province selectedProvince;

//    选中的城市
    private City selectedCity;

//    选中的县区
    private Country selectedCountry;

//    当前选中的级别
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    
}
