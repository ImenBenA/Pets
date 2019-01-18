package tn.esprit.pets.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tn.esprit.pets.R;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.fragment.SignupFragment;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.service.UserService;

public class LoginActivity extends AppCompatActivity {

    public static User userConnected;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RelativeLayout relativeLayout1, relativeLayout2;
    Button login, signup;
    boolean isConnected, isAuthentified;
    EditText username, password;
    private String getAllURL = "http://"+MySingleton.getIp()+"/PetsWS/user/userByUsernamePassword.php";
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
        sharedPreferences = this.getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isConnected = sharedPreferences.getBoolean("isConnect", false);
        if(isConnected) {
            finish();
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
                    isAuthentified(lusername, lpassword, intent);
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

    public void isAuthentified(final String username, final String password,final Intent intent) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                getAllURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("json response", response.toString());

                        try {
                            System.out.println(response.toString());
                            if(!response.toString().equals("{}")) {
                                JSONObject jsonObject = response;
                                int id = Integer.parseInt(jsonObject.getString("id"));
                                String lusername = jsonObject.getString("username");
                                String lpassword = jsonObject.getString("password");
                                String email = jsonObject.getString("email");
                                String phone = jsonObject.getString("phone");
                                String token= jsonObject.getString("token");

                                if (lusername.equalsIgnoreCase(username) && lpassword.equals(password)) {
                                    isAuthentified = true;
                                    userConnected = new User(id,lusername, lpassword, email, phone, token);
                                    editor.putString("username", lusername);
                                    editor.putString("password", lpassword);
                                    editor.putString("email", email);
                                    editor.putString("phone", phone);
                                    editor.putInt("id", id);
                                    editor.commit();
                                    Log.e("is authentified", isAuthentified + userConnected.toString());
                                    isConnected = true;
                                    editor.putBoolean("isConnect", true);
                                    editor.commit();
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("Array exception", isAuthentified + "");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("array error", error.toString());
                    }
                }
        ){
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("username", username);
                params2.put("password", password);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
