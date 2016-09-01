package androidtrainticket.yinda.com.androidtrainticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import http.DataBean;
import http.HttpRequestUrl;
import http.QueryResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.AssetsDatabaseManager;
import utils.HTTPSUtils;

public class QueryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button queryFrom;
    private Button queryTo;
    private ImageView queryChange;
    private Button queryDate;
    private Button queryQr;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private Button btn;
    private View dialog;
    private int getYear;
    private int getMonth;
    private int getDay;
    AlertDialog.Builder builder;
    EditText cityname;
    String fromCity;
    String toCity;
    String nameF;
    String nameT;
    public static SQLiteDatabase stationDb;
    AssetsDatabaseManager mg;
    Cursor cursor;
    String qrDate;
    HTTPSUtils utilsq;
    String getQRURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_query);
        mg = AssetsDatabaseManager.getManager();
        utilsq = new HTTPSUtils(QueryActivity.this);
        stationDb = mg.getDatabase("station12306s.sqlite");
        assignViews();
        queryDate.setOnClickListener(this);
        queryFrom.setOnClickListener(this);
        queryTo.setOnClickListener(this);
        queryChange.setOnClickListener(this);
        queryQr.setOnClickListener(this);

    }

    private String getSTCodeFromSQL(String name) {
        String city = null;
        // 通过管理对象获取数据库
        cursor = stationDb.rawQuery("select st_code from st12306 where st_name = ?;", new String[]{name});
        while (cursor.moveToNext()) {
            city = cursor.getString(0);
        }
        if (cursor != null) {
            cursor.close();
        }
        if (!TextUtils.isEmpty(city)) {
            return city;
        } else {
            Toast.makeText(QueryActivity.this, "您要查询的不存在", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void assignViews() {
        queryFrom = (Button) findViewById(R.id.query_from);
        queryTo = (Button) findViewById(R.id.query_to);
        queryChange = (ImageView) findViewById(R.id.query_change);
        queryDate = (Button) findViewById(R.id.query_date);
        queryQr = (Button) findViewById(R.id.query_qr);
        calendar = Calendar.getInstance();
        // 获取当前对应的年、月、日的信息
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stationDb.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_date:
                dateDialog();
                break;
            case R.id.query_from:
                cityFromDialog();
                break;
            case R.id.query_to:
                cityToDialog();
                break;
            case R.id.query_change:
                getCityName();
                queryFrom.setText(nameT);
                queryTo.setText(nameF);
                break;
            case R.id.query_qr:
                getCityName();
                fromCity = getSTCodeFromSQL(nameF);
                toCity = getSTCodeFromSQL(nameT);
                qrDate = getQRDate();
                getQRURL = getQueryUrl();
                getQueryResponse(getQRURL);
                break;
        }
    }

    private void getQueryResponse(String url) {
        OkHttpClient client = utilsq.getInstance(this);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    parseJson(result);
                }
            }
        });


    }

    private void parseJson(String result) {
        Gson gson = new Gson();
        QueryResponse queryResponse = gson.fromJson(result, QueryResponse.class);
        ArrayList<DataBean> list = (ArrayList<DataBean>) queryResponse.getData();
        Intent intent = new Intent(QueryActivity.this,ResultResponse.class);
        intent.putExtra("queryResponse",  list);
        startActivity(intent);

    }

    public String getQueryUrl() {
        StringBuilder burl = new StringBuilder();
        burl.append("https://kyfw.12306.cn/otn/leftTicket/queryT?leftTicketDTO.train_date=");
        burl.append(qrDate);
        burl.append("&leftTicketDTO.from_station=");
        burl.append(fromCity);
        burl.append("&leftTicketDTO.to_station=");
        burl.append(toCity);
        burl.append("&purpose_codes=ADULT");
        return burl.toString();
    }

    private String getQRDate() {
        return queryDate.getText().toString().trim();
    }

    public void getCityName() {
        nameF = queryFrom.getText().toString().trim();
        nameT = queryTo.getText().toString().trim();
    }

    public void dateDialog() {
        builder = new AlertDialog.Builder(QueryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        dialog = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
        datePicker = (DatePicker) dialog.findViewById(R.id.myDatePicker);
        builder.setTitle("选择查询的日期");
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                getYear = year;
                getMonth = monthOfYear + 1;
                getDay = dayOfMonth;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getMonth < 10) {
                    if (getDay < 10) {
                        queryDate.setText(getYear + "-" + "0" + getMonth + "-" + "0" + getDay);
                    } else {
                        queryDate.setText(getYear + "-" + "0" + getMonth + "-" + getDay);
                    }
                } else {
                    if (getDay < 10) {
                        queryDate.setText(getYear + "-" + getMonth + "-" + "0" + getDay);
                    } else {
                        queryDate.setText(getYear + "-" + getMonth + "-" + getDay);
                    }
                }
            }
        });
        builder.setView(dialog);
        builder.setIcon(R.mipmap.tabpage_icon);
        builder.show();
    }

    public void cityFromDialog() {
        builder = new AlertDialog.Builder(QueryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        dialog = inflater.inflate(R.layout.dialogcity, (ViewGroup) findViewById(R.id.dialog));
        cityname = (EditText) dialog.findViewById(R.id.city);
        builder.setTitle("请输入出发的城市");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = cityname.getText().toString().trim();
                if (!TextUtils.isEmpty(city)) {
                    queryFrom.setText(city);
                } else {
                    Toast.makeText(QueryActivity.this, "请输入要查询的城市", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(dialog);
        builder.setIcon(R.mipmap.tabpage_icon);
        builder.show();
    }

    public void cityToDialog() {
        builder = new AlertDialog.Builder(QueryActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        dialog = inflater.inflate(R.layout.dialogcity, (ViewGroup) findViewById(R.id.dialog));
        cityname = (EditText) dialog.findViewById(R.id.city);
        builder.setTitle("请输要到达的城市");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = cityname.getText().toString().trim();
                if (!TextUtils.isEmpty(city)) {
                    queryTo.setText(city);
                } else {
                    Toast.makeText(QueryActivity.this, "请输入要到达的城市", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(dialog);
        builder.setIcon(R.mipmap.tabpage_icon);
        builder.show();
    }
}
