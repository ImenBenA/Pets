package tn.esprit.pets.activity;

import android.content.Intent;
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

    RelativeLayout relativeLayout1, relativeLayout2;
    Button login, signup;
    EditText username, password;
    private String getAllURL = "http://10.0.2.2:18080/WSPets-web/api/user/all";
    UserService userService = new UserService();

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
        setContentView(R.layout.activity_login);

        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);

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
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        boolean ok = userService.isAuthentified(getApplicationContext(), lusername, lpassword);
                        if (ok) {
                            startActivity(intent);
                            Log.e("is authentified", ok+"");
                        }
                        else {
                            Log.e("is authentified", ok+"");
                        }
                    }
                };
                runnable.run();



                /*RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                // Initialize a new JsonArrayRequest instance
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        getAllURL,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.e("json response", response.toString());

                                try{
                                    // Loop through the array elements
                                    for(int i=0;i<response.length();i++){
                                        // Get current json object
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        // Get the current student (json object) data
                                        String username = jsonObject.getString("username");
                                        String password = jsonObject.getString("password");

                                        // Display the formatted json data in text view
                                        if (lusername.equalsIgnoreCase(username) && lpassword.equalsIgnoreCase(password)) {
                                            startActivity(intent);
                                            Log.e("Array taada", "ey");
                                            break;
                                        }

                                    }
                                }catch (JSONException e){
                                    Log.e("Array exception", "mataadech");
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                // Do something when error occurred
                                Log.e("array error", error.toString());
                            }
                        }
                );

                // Add JsonArrayRequest to the RequestQueue
                requestQueue.add(jsonArrayRequest);*/
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.login_layout, new SignupFragment()).commit();
            }
        });


    }
}
