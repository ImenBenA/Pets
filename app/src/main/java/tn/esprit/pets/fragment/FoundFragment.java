package tn.esprit.pets.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tn.esprit.pets.R;
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;

public class FoundFragment extends Fragment {

    private String getAllURL = "http://"+MySingleton.getIp()+"/PetsWS/post/allPosts.php";
    static ArrayList<Post> found = new ArrayList<>();
    View root;
    PostsAdapter itemsAdapter;

    public FoundFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);
        itemsAdapter = new PostsAdapter(root.getContext(), found);
        ListView listView = (ListView) root.findViewById(R.id.posts);
        listView.setAdapter(itemsAdapter);
        getPosts(root.getContext());
        return root;
    }

    public void getPosts(Context context) {
        //RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getAllURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("json response", response.toString());
                        try {
                            found.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String type = jsonObject.getString("type");
                                if (type.equals("found")) {
                                    int id = jsonObject.getInt("id");
                                    String description = jsonObject.getString("description");
                                    String imageUrl = jsonObject.getString("petImage");
                                    String link ="http://"+MySingleton.getIp()+"/PetsWS/post/"+imageUrl;
                                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = null;
                                    try {
                                        date = (Date) simpleDateFormat.parse(jsonObject.getString("date"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                   // JSONObject userObject = (JSONObject) jsonObject.get("user_id");
                                    //User user = new User(userObject.getInt("id"), userObject.getString("username"), userObject.getString("password"))
                                    Post post = new Post(id, description, link, new User(), type, date);
                                    found.add(post);
                                }

                            }
                            Log.v("posts response", found.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        itemsAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("json error", error.toString());
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


}
