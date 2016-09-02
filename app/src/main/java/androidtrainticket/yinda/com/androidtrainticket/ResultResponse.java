package androidtrainticket.yinda.com.androidtrainticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import http.DataBean;
import http.QueryLeftNewDTOBean;
import utils.PriceComparator;
import utils.TimeComparator;
import utils.TimeUseComparator;
import view.DividerItemDecoration;

public class ResultResponse extends AppCompatActivity implements View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    RelultAdapter adapter;
    ArrayList<DataBean> list;
    QueryLeftNewDTOBean bean;
    TextView timeSort;
    TextView timeUseSort;
    TextView priceSort;
    TextView chose;
    TimeComparator timec;
    TimeUseComparator timeUsec;
    PriceComparator priceC;
    boolean isup =false;
    AlertDialog.Builder builder;
    private View dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_result_response);
        assignViews();
        timeSort.setOnClickListener(this);
        timeUseSort.setOnClickListener(this);
        priceSort.setOnClickListener(this);
        chose.setOnClickListener(this);

        Intent intent = getIntent();
        list = (ArrayList<DataBean>) intent.getSerializableExtra("queryResponse");

        adapter = new RelultAdapter();
        adapter = new RelultAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
    }

    private void assignViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        timeSort = (TextView) findViewById(R.id.time_sort);
        timeUseSort = (TextView) findViewById(R.id.usetime_sort);
        priceSort = (TextView) findViewById(R.id.price_sort);
        chose= (TextView) findViewById(R.id.chose);
        timec = new TimeComparator();
         timeUsec = new TimeUseComparator();
         priceC =new PriceComparator();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_sort:
                    Collections.sort(list,timec);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                break;
            case R.id.usetime_sort:
                Collections.sort(list,timeUsec);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                break;
            case R.id.price_sort:
                Collections.sort(list,priceC);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.chose:


                break;
        }

    }
    public void dateDialog() {
        builder = new AlertDialog.Builder(ResultResponse.this);
        LayoutInflater inflater = getLayoutInflater();
        dialog = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
        builder.setTitle("选择查询的日期");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(dialog);
        builder.setIcon(R.mipmap.tabpage_icon);
        builder.show();
    }
    class RelultAdapter extends RecyclerView.Adapter<RelultAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(ResultResponse.this).inflate(R.layout.recycleritem, parent,
                    false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (list.size() != 0) {
                bean = list.get(position).getQueryLeftNewDTO();
                holder.rcChename.setText(bean.getStation_train_code());
                holder.rcFromTime.setText(bean.getStart_time());
                holder.rcFromStatio.setText(bean.getFrom_station_name());
                holder.rcToStation.setText(bean.getTo_station_name());
                holder.rcToTime.setText(bean.getArrive_time());
                holder.rcLishi.setText(bean.getLishi().replace(":","小时")+"分钟");
                holder.rcPrice.setText("￥"+bean.getLishiValue());
                if (bean.getStart_station_name() !=bean.getFrom_station_name()){
                    holder.rcFromImg.setImageResource(R.mipmap.guoa);
                } else {
                    holder.rcFromImg.setImageResource(R.mipmap.start_station);
                }
                if (bean.getStart_station_name()!=bean.getFrom_station_name()){
                    holder.rcToImg.setImageResource(R.mipmap.guoa);
                } else {
                    holder.rcToImg.setImageResource(R.mipmap.end_startion);

                }
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView rcChename;
            private ImageView rcFromImg;
            private TextView rcFromTime;
            private TextView rcFromStatio;
            private ImageView rcToImg;
            private TextView rcToTime;
            private TextView rcToStation;
            private TextView rcLishi;
            private TextView rcPrice;

            private void assignViews(View view) {
                rcChename = (TextView) view.findViewById(R.id.rc_chename);
                rcFromImg = (ImageView) view.findViewById(R.id.rc_from_img);
                rcFromTime = (TextView) view.findViewById(R.id.rc_from_time);
                rcFromStatio = (TextView) view.findViewById(R.id.rc_from_statio);
                rcToImg = (ImageView) view.findViewById(R.id.rc_to_img);
                rcToTime = (TextView) view.findViewById(R.id.rc_to_time);
                rcToStation = (TextView) view.findViewById(R.id.rc_to_station);
                rcLishi = (TextView) view.findViewById(R.id.rc_lishi);
                rcPrice = (TextView) view.findViewById(R.id.rc_price);
            }

            public MyViewHolder(View itemView) {
                super(itemView);
                assignViews(itemView);
            }
        }
    }

}
