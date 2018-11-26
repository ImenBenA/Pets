package tn.esprit.pets.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tn.esprit.pets.R;
import tn.esprit.pets.fragment.AddPostFragment;
import tn.esprit.pets.fragment.SignupFragment;
import tn.esprit.pets.service.UserService;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RelativeLayout relativeLayout1, relativeLayout2;
    Button login, signup;
    boolean isConnected, isAuthentified;
    EditText username, password;
    private String getAllURL = "http://10.0.2.2:18080/WSPets-web/api/user/all";
    UserService userService = new UserService();

    Handler mHandler = new Handler();
    Runnable mRunnable;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relativeLayout1.setVisibility(View.VISIBLE);
            relativeLayout2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        sharedPreferences = this.getSharedPreferences("isConnect", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isConnected = sharedPreferences.getBoolean("isConnect", false);
        if(isConnected) {
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_login);
            relativeLayout1 = (RelativeLayout) findViewById(R.id.relative_layout1);
            relativeLayout2 = (RelativeLayout) findViewById(R.id.relative_layout2);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);
            login = (Button) findViewById(R.id.login);
            signup = (Button) findViewById(R.id.signup);

            handler.postDelayed(runnable, 2000);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String lusername = username.getText().toString();
                    final String lpassword = password.getText().toString();
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            isAuthentified = userService.isAuthentified(getApplicationContext(), lusername, lpassword);
                            if (isAuthentified) {
                                isConnected = true;
                                editor.putBoolean("isConnect", true);
                                editor.commit();
                                startActivity(intent);
                                Log.e("is auth and is co", isAuthentified + " "+ isConnected + " ");
                            }
                            else {
                                Log.e("is neither", isAuthentified +"");
                            }
                        }
                    };
                    if (mRunnable != null) {
                        mHandler.post(mRunnable);
                    }
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            getSupportFragmentManager().beginTransaction().addToBackStack("fragment").add(R.id.login_layout, new SignupFragment()).commit();
                        }
                    };
                    if (mRunnable != null) {
                        mHandler.post(mRunnable);
                    }
                }
            });
        }
    }
}
