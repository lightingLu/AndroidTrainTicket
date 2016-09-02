package androidtrainticket.yinda.com.androidtrainticket;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import http.DataBean;
import http.QueryLeftNewDTOBean;
import view.DividerItemDecoration;

public class ResultResponse extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    RelultAdapter adapter;
    ArrayList<DataBean> list;
    QueryLeftNewDTOBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_result_response);
        assignViews();


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
