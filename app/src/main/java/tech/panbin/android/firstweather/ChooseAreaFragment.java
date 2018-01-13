package tech.panbin.android.firstweather;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.AdapterView;
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

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area, container,false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posion, long id) {

                if(currentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(posion);
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    selectedCity = cityList.get(posion);
                    queryCountries();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel == LEVEL_COUNTRY){
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    queryProvinces();
                }
            }
        });

        queryProvinces();
    }


//    查询所有的省，先查数据库，若没有再查服务器
    private void queryProvinces(){

    }

//    查询所有的市，先查数据库，若没有再查服务器
    private void queryCities(){

    }

//    查询所有的县区，先查数据库，若没有再查服务器
    private void queryCountries(){

    }

//    从服务器查询
    private void queryFromServer(String address, final String type){

    }

//    显示进度对话框
    private void showProgressDialog(){

    }

//    关闭进度对话框
    private void closeProgressDialog(){

    }
}
