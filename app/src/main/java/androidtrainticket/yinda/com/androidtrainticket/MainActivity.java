package androidtrainticket.yinda.com.androidtrainticket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;

import http.HttpRequestUrl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.HTTPSUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginName;
    private EditText loginPassword;
    private ImageView loginAbout;
    private ImageView loginPasscode;
    private Button loginLoginin;
    private TextView loginLoginup;
    private TextView loginForget;
    private Response response;
    private InputStream inputStream;
    private Bitmap bitmap;
    private HTTPSUtils utils;
    String url = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&0.012066099559888244";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        utils = new HTTPSUtils(this);
        initViews();
        loginLoginin.setOnClickListener(this);
        loginForget.setOnClickListener(this);
        loginLoginup.setOnClickListener(this);
        loginPasscode.setOnClickListener(this);

    }

    //获取控件id
    private void initViews() {
        loginName = (EditText) findViewById(R.id.login_name);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginPasscode = (ImageView) findViewById(R.id.login_passcode);
        loginLoginin = (Button) findViewById(R.id.login_loginin);
        loginLoginup = (TextView) findViewById(R.id.login_loginup);
        loginForget = (TextView) findViewById(R.id.login_forget);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget:
                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_loginin:

                getPasswordCode();
                break;
            case R.id.login_loginup:
//                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_passcode:
//                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    public void getPasswordCode2(){
        OkHttpClient client = utils.getInstance(getApplication());
        Request request = new Request.Builder().url(HttpRequestUrl.PASSWOR_CODE_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                  Toast.makeText(getApplication(),"请检查您的网络",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginPasscode.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });
    }
    public void getPasswordCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = utils.getInstance(getApplication());
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginPasscode.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
//    public void getPassworCode() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url(HttpRequestUrl.PASSWOR_CODE_URL).build();
//                try {
//                    response = client.newCall(request).execute();
//                    if (response.isSuccessful()) {
//                        inputStream = response.body().byteStream();
//                        System.out.println("====" + inputStream);
//                        bitmap = BitmapFactory.decodeStream(inputStream);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                loginPasscode.setImageBitmap(bitmap);
//                            }
//                        });
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
}
