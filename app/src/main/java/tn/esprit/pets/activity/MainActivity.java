package tn.esprit.pets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import tn.esprit.pets.R;
import tn.esprit.pets.adapter.PostAdapter;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.fragment.AddPostFragment;
import tn.esprit.pets.service.UserService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FeatureCoverFlow coverFlow;
    private PostAdapter postAdapter;
    public static List<Post> posts = new ArrayList<>();
    private TextSwitcher textSwitcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean ok = true;

        if (ok == true) {
            setContentView(R.layout.activity_main);

            initData();
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

            textSwitcher = (TextSwitcher) findViewById(R.id.text_switcher);
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
            });


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    getSupportFragmentManager().beginTransaction().add(R.id.drawer_layout, new AddPostFragment()).commit();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initData() {
        UserService us = new UserService();
        us.getAllUsers(this);
        //boolean ok = us.isAuthentified(this, "imen", "kooo");
        posts.add(new Post("kiko", "https://www.petsafe.net/media/images/learn/spay-kittens-article-thumbnail.jpg"));
        posts.add(new Post("this cat is currently injured and hungry near cvs, huston texas", "https://i2.wp.com/consciouscat.net/wp-content/uploads/2014/10/catblog1-e1414762887270.png"));
        posts.add(new Post("this cat was found at bed, bath and beyond, boston", "https://www.cats.org.uk/uploads/branches/1/environment-faqs.jpg"));
        posts.add(new Post("9attous bahdha poubelle el khamja mta el cité", "https://www.aspcapetinsurance.com/media/1080/14.jpg?width=400&height=400"));
        posts.add(new Post("9atous 7ay lkhadhra", "https://pbs.twimg.com/profile_images/930937305686298629/TcUPwLQE_400x400.jpg"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
