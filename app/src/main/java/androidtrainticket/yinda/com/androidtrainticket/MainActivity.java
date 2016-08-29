package androidtrainticket.yinda.com.androidtrainticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.login_name)
    EditText loginName;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.login_about)
    ImageView loginAbout;
    @Bind(R.id.login_passcode)
    ImageView loginPasscode;
    @Bind(R.id.login_loginin)
    Button loginLoginin;
    @Bind(R.id.login_loginup)
    TextView loginLoginup;
    @Bind(R.id.login_forget)
    TextView loginForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
