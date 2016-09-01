package androidtrainticket.yinda.com.androidtrainticket;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDataLoadProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import db.MyPoint;
import http.HttpRequestUrl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.AssetsDatabaseManager;
import utils.HTTPSUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginName;
    private EditText loginPassword;
    private ImageView loginAbout;
    private ImageView loginPasscode;
    private Button loginLoginin;
    private TextView loginLoginup;
    private TextView loginForget;
    private Button loginRefresh;
    private Bitmap bitmap;
    private HTTPSUtils utils;
    private ByteArrayOutputStream baos;
    private Bitmap bitmapYanzheng;
    private Bitmap bitmapCopy;
    private Paint paint;
    private Canvas canvas;
    int startX;
    int startY;
    byte[] bytes;
    StringBuilder sb=null ;
    MyPoint points=null;
    private static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
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
        getPasswordCode2();
        sb = new StringBuilder();
        points = new MyPoint();
        loginLoginin.setOnClickListener(this);
        loginForget.setOnClickListener(this);
        loginLoginup.setOnClickListener(this);
//        loginPasscode.setOnClickListener(this);
        loginRefresh.setOnClickListener(this);
        loginPasscode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) motionEvent.getX();
                        startY = (int) motionEvent.getY();
                        System.out.println(startX + ":"+startY + "------------------------------");
                         points.setX(startX);
                         points.setY(startY) ;
                         points.toString();
                         sb.append(points.getX() + "," + points.getY());
                         sb.append(",");
                         System.out.println(sb.toString());
                        break;
                    //用户手指正在滑动
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        //每次绘制完毕之后，本次绘制的结束坐标变成下一次绘制的初始坐标
//                        canvas.drawCircle(startX, startY,20,paint);
                        startX = x;
                        startY = y;
                        break;
                    //用户手指离开屏幕
                    case MotionEvent.ACTION_UP:
                        canvas.drawBitmap(bitmapYanzheng, startX, startY, paint);
                        loginPasscode.setImageBitmap(bitmapCopy);
                        break;
                }
                return true;
            }
        });
    }

    //获取控件id
    private void initViews() {
        loginName = (EditText) findViewById(R.id.login_name);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginPasscode = (ImageView) findViewById(R.id.login_passcode);
        loginLoginin = (Button) findViewById(R.id.login_loginin);
        loginLoginup = (TextView) findViewById(R.id.login_loginup);
        loginForget = (TextView) findViewById(R.id.login_forget);
        loginRefresh = (Button) findViewById(R.id.login_refresh);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget:
                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_loginin:
                loginIn();
                break;
            case R.id.login_loginup:
//                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_passcode:
//                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_refresh:
                getPasswordCode2();
                break;

        }
    }

    /**
     * 登录操作
     */
    private void loginIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String passCode = sb.toString();
//                String path = HttpRequestUrl.PASSWOR_YANZ_URL+"randCode="+passCode+"&rand=sjrand";
                OkHttpClient clientYanz = utils.getInstance(getApplication());
                FormBody body = new FormBody.Builder()
                        .add("randCode", passCode)
                        .add("rand", "sjrand")
                        .build();
                Request requestYanz = new Request.Builder().url(HttpRequestUrl.PASSWOR_YANZ_URL).post(body).build();

                try {
                    Response response = clientYanz.newCall(requestYanz).execute();
                    if (response.isSuccessful()){
                        System.out.println("___________"+response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
//        String password =loginPassword.getText().toString().trim();
//        String username= loginName.getText().toString().trim();
//        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
//            Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_LONG).show();
//        }else {
            Intent intent = new Intent(MainActivity.this,QueryActivity.class);
            startActivity(intent);
//        }
    }

    public void getPasswordCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = utils.getInstance(getApplication());
                Request request = new Request.Builder().url(HttpRequestUrl.PASSWOR_CODE_URL).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        bytes = baos.toByteArray();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(getApplication())
                                        .load(bytes)
                                        .into(loginPasscode);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 本部分逻辑：
     *  1.获取网络图片的bitmap对象 （不然imageview内部位置绘制错误，如果设置wrap显示的图片又太小）
     *  2.把bitmap的对象设置给imageview
     *  3.获取imageview的bitmap对象，然后在此对象上绘制
     */
    public void getPasswordCode2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = utils.getInstance(getApplication());
                Request request = new Request.Builder().url(HttpRequestUrl.PASSWOR_CODE_URL).build();
                 if (sb.length()!=0){
                     sb.delete(0,sb.length());
                 }
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginPasscode.setImageBitmap(bitmap);
                                loginPasscode.buildDrawingCache();
                                Bitmap bitmap1= loginPasscode.getDrawingCache();
                                bitmapYanzheng = BitmapFactory.decodeResource(getResources(), R.mipmap.yanzheng);
                                bitmapCopy = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
                                paint = new Paint();
                                canvas = new Canvas(bitmapCopy);
                                canvas.drawBitmap(bitmap1, new Matrix(), paint);
                                loginPasscode.setImageBitmap(bitmapCopy);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
