package androidtrainticket.yinda.com.androidtrainticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class QueryCityActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView qureyCityImg;
    private EditText queryCity;
    private ImageButton find;

    private void assignViews() {
        qureyCityImg = (ImageView) findViewById(R.id.qurey_city_img);
        queryCity = (EditText) findViewById(R.id.query_city);
        find = (ImageButton) findViewById(R.id.find);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_query_city);
        assignViews();
        find.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find:
                String cityName = queryCity.getText().toString().trim();
                if (cityName.equals("")){
                    Toast.makeText(QueryCityActivity.this,"请输入要查询的城市",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
