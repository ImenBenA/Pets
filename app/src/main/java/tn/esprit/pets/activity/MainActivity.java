package tn.esprit.pets.activity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.pets.R;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.fragment.AddPostFragment;
import tn.esprit.pets.fragment.FoundFragment;
import tn.esprit.pets.fragment.LostAndFoundFragment;
import tn.esprit.pets.fragment.LostFragment;
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
    private String getUserURL = "http://10.0.2.2:18080/WSPets-web/api/user/find/";
    private String getAllURL = "http://10.0.2.2:18080/WSPets-web/api/user/all";
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
        //Log.e("user co" , LoginActivity.userConnected.toString());
        //initData();
            /*String getAllURL = "http://10.0.2.2:18080/WSPets-web/api/user/all";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    getAllURL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("rest response", response.toString());
                            System.out.println("json response " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("rest error", error.toString());
                        }
                    }
            );
            objectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            requestQueue.add(objectRequest);*/

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
        sharedPreferences = this.getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getInt("id", 0);
        getUserConnected2(userId);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.drawer_layout, new AddPostFragment()).commit();
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
        //boolean ok = us.isAuthentified(this, "imen", "kooo");
        /*posts.add(new Post("kiko", "https://www.petsafe.net/media/images/learn/spay-kittens-article-thumbnail.jpg"));
        posts.add(new Post("this cat is currently injured and hungry near cvs, huston texas", "https://i2.wp.com/consciouscat.net/wp-content/uploads/2014/10/catblog1-e1414762887270.png"));
        posts.add(new Post("this cat was found at bed, bath and beyond, boston", "https://www.cats.org.uk/uploads/branches/1/environment-faqs.jpg"));
        posts.add(new Post("9attous bahdha poubelle el khamja mta el cité", "https://www.aspcapetinsurance.com/media/1080/14.jpg?width=400&height=400"));
        posts.add(new Post("9atous 7ay lkhadhra", "https://pbs.twimg.com/profile_images/930937305686298629/TcUPwLQE_400x400.jpg"));
    */
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

    public void getUserConnected(final int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("requrl", getUserURL + String.valueOf(id));
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUserURL + String.valueOf(id),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("usercon", response.toString());
                        /*try {
                            int id = response.getInt("id");
                            String lusername = response.getString("username");
                            String lpassword = response.getString("password");
                            String email = response.getString("email");
                            String picture = response.getString("picture");
                            userConnected = new User(id,lusername,lpassword,email,picture);
                            Log.e("usercon", response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("usernoncon", error.toString());
                    }
                }
        );
        /*objectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 2500;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });*/
        //MySingleton.getInstance(this).addToRequestQueue(objectRequest);
        requestQueue.add(objectRequest);
    }

    public void getUserConnected2(final int id) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getAllURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("json response", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int userId = jsonObject.getInt("id");
                                if(id == userId) {
                                    String username = jsonObject.getString("username");
                                    String password = jsonObject.getString("password");
                                    String email = jsonObject.getString("email");
                                    String picture = jsonObject.getString("picture");
                                    userConnected = new User(id,username,password,email,picture);
                                    Log.e("userfound", userConnected.toString());
                                    break;
                                }

                            }
                        } catch (JSONException e) {
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
        );
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }
}
