package androidtrainticket.yinda.com.androidtrainticket;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.Calendar;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_query);
        assignViews();
        queryDate.setOnClickListener(this);
        queryFrom.setOnClickListener(this);
        queryTo.setOnClickListener(this);
        queryChange.setOnClickListener(this);


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
                break ;
            case R.id.query_change:
                String nameF = queryFrom.getText().toString();
                String nameT = queryTo.getText().toString();
                queryFrom.setText(nameT);
                queryTo.setText(nameF);
                break;
        }
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
                queryDate.setText(getYear + "年" + getMonth + "月" + getDay+"日");
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
                String city  = cityname.getText().toString().trim();
                if (!TextUtils.isEmpty(city)){
                    queryFrom.setText(city);
                }   else {
                    Toast.makeText(QueryActivity.this,"请输入要查询的城市",Toast.LENGTH_SHORT).show();
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
                String city  = cityname.getText().toString().trim();
                if (!TextUtils.isEmpty(city)){
                    queryTo.setText(city);
                }  else {
                    Toast.makeText(QueryActivity.this,"请输入要到达的城市",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(dialog);
        builder.setIcon(R.mipmap.tabpage_icon);
        builder.show();
    }
}
