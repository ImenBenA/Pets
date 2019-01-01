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
import tn.esprit.pets.fragment.ProfileFragment;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.service.UserService;

public class MainActivity extends AppCompatActivity {

    /*private FeatureCoverFlow coverFlow;
    private PostAdapter postAdapter;

    private TextSwitcher textSwitcher;*/
    public static User userConnected;
    //public static List<Post> posts = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String getUserURL = "http://"+MySingleton.getIp()+"/PetsWS/user/userById.php?id=";
    private String updateUEL = "http://" + MySingleton.getIp() + "/PetsWS/user/updateUser.php";
    int userId;
    GridLayout mainGrid;
    CardView lost, found, profile, settings, lostAndFound, messages;
    Runnable runnable;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationContext();
            /*textSwitcher = (TextSwitcher) findViewById(R.id.text_switcher);
            textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    TextView text = (TextView) inflater.inflate(R.layout.coverflow_title, null);
                    return text;
                }
            });
            Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
            Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
            textSwitcher.setInAnimation(in);
            textSwitcher.setOutAnimation(out);

            postAdapter = new PostAdapter(posts, this);
            coverFlow = (FeatureCoverFlow) findViewById(R.id.cover_flow);
            coverFlow.setAdapter(postAdapter);

            coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
                @Override
                public void onScrolledToPosition(int position) {
                    textSwitcher.setText(posts.get(position).getDescription());
                }

                @Override
                public void onScrolling() {

                }
            });*/

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        sharedPreferences = this.getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getInt("id", 0);
        String name=sharedPreferences.getString("username", "");
        String pass=sharedPreferences.getString("password", "");
        System.out.println(name + " and " + pass + " and id : "+userId);
        getUserConnected2(userId,name,pass);
        if (userConnected!=null)
        {
            System.out.println("mahouch null ye zebi");
            if(!userConnected.getToken().equals(FirebaseInstanceId.getInstance().getToken()))
                updateUser(getApplicationContext(),userConnected.getId()+"",FirebaseInstanceId.getInstance().getToken());
        }

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
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
        messages = (CardView) findViewById(R.id.messages);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, new MessageFragment()).commit();
                    }
                };
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().addToBackStack("fragment").add(R.id.drawer_layout, new AddPostFragment()).commit();
            }
        });

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    private void initData() {
        UserService us = new UserService();
        us.getAllUsers(this);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //if (id == R.id.nav_camera) {
            // Handle the camera action
            /*Runnable mPendingRunnable = new Runnable() {
                @Override
                public void run() {
                    // update the main content by replacing fragments
                    android.support.v4.app.Fragment fragment = getHomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            };

            // If mPendingRunnable is not null, then add to the message queue
            if (mPendingRunnable != null) {
                mHandler.post(mPendingRunnable);
                sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("posActualites", -1);
                editor.putInt("posCinema", -1);
                editor.commit();
            }

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


    public void getUserConnected2(final int id, final String name, final String pass) {
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
