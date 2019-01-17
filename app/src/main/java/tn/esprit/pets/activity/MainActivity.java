package tn.esprit.pets.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tn.esprit.pets.R;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.fragment.AddPostFragment;
import tn.esprit.pets.fragment.FoundFragment;
import tn.esprit.pets.fragment.LostAndFoundFragment;
import tn.esprit.pets.fragment.LostFragment;
import tn.esprit.pets.fragment.MessageFragment;
import tn.esprit.pets.fragment.MyPostsFragment;
import tn.esprit.pets.fragment.NotificationFragment;
import tn.esprit.pets.fragment.ProfileFragment;
import tn.esprit.pets.fragment.SettingsFragment;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.service.UserService;

public class MainActivity extends AppCompatActivity {


    public static User userConnected;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String getUserURL = "http://"+MySingleton.getIp()+"/PetsWS/user/userById.php?id=";
    private String updateUEL = "http://" + MySingleton.getIp() + "/PetsWS/user/updateUser.php";
    int userId;
    GridLayout mainGrid;
    CardView lost, found, profile, settings, notifications, addPost, myPosts;
    Runnable runnable;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationContext();
        sharedPreferences = this.getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getInt("id", 0);
        String name = sharedPreferences.getString("username", "");
        String pass = sharedPreferences.getString("password", "");
        //System.out.println(name + " and " + pass + " and id : "+userId);
        getUserConnected(userId, name, pass);
        if (userConnected!=null) {
            System.out.println("mahouch null ye zebi");
            if(!userConnected.getToken().equals(FirebaseInstanceId.getInstance().getToken()))
                updateUser(getApplicationContext(),userConnected.getId()+"",FirebaseInstanceId.getInstance().getToken());
        }

        //mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        lost = (CardView) findViewById(R.id.lost);
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new LostFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        found = (CardView) findViewById(R.id.found);
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new FoundFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        profile = (CardView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new ProfileFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });

        myPosts = (CardView) findViewById(R.id.myPosts);
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new MyPostsFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });

        notifications = (CardView) findViewById(R.id.notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new NotificationFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        settings = (CardView) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new SettingsFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        addPost = (CardView) findViewById(R.id.addPost);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").add(R.id.drawer_layout, new AddPostFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().addToBackStack("fragment").add(R.id.drawer_layout, new AddPostFragment()).commit();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getUserConnected(final int id, final String name, final String pass) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUserURL+id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("json response", response.toString());

                        try {
                            if(!response.toString().equals("{}")){
                                JSONObject jsonObject = response;
                                int id = Integer.parseInt(jsonObject.getString("id"));
                                String username = jsonObject.getString("username");
                                String password = jsonObject.getString("password");
                                String email = jsonObject.getString("email");
                                String phone = jsonObject.getString("phone");
                                String token= jsonObject.getString("token");
                                if (username.equals(name) && password.equals(pass)){
                                    userConnected = new User(id,username,password, email, phone,token);
                                    System.out.println("yeeess");
                                }
                                    //Log.e("userfound", userConnected.toString());
                                }

                            } catch (JSONException e1) {
                            Log.e("can't bound", " error bounding");
                            e1.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("array error", error.toString());
                    }
                }
        );
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    public void updateUser(Context context, final String token, final String id) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                updateUEL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("update resp", response.toString());
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                        } catch (JSONException e) {
                            Log.e("update exc", " ");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("json err upd", error.toString());
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("token", token);
                params2.put("id", id);

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }
    public static void setUserConnected(User user){
        userConnected=user;
    }
}
