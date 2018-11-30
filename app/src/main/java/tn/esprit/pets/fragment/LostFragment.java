package tn.esprit.pets.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tn.esprit.pets.R;
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.service.PostService;


public class LostFragment extends Fragment {
    //ArrayList<Post> posts = new ArrayList<>();
    private String getAllURL = "http://10.0.2.2:18080/WSPets-web/api/post/all";
    PostService ps = new PostService();
    static ArrayList<Post> posts = new ArrayList<>();
    public LostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);
        final PostsAdapter itemsAdapter = new PostsAdapter(root.getContext(), posts);
        ListView listView = (ListView) root.findViewById(R.id.posts);
        listView.setAdapter(itemsAdapter);
        ps.getAllPosts(root.getContext(),itemsAdapter);
/*
       RequestQueue queue = MySingleton.getInstance(root.getContext()).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getAllURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("json response", response.toString());
                        ArrayList<Post> t = new ArrayList<>();
                        try{
                            for(int i=0; i<response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String description = jsonObject.getString("description");
                                String imageUrl = jsonObject.getString("petImage");
                                //User user = jsonObject.getJSONObject("user");
                                String type = jsonObject.getString("type");
                                Post post = new Post(id, description, imageUrl, new User(), type);
                                posts.add(post);
                            }
                            Log.v("posts response", posts.toString());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        itemsAdapter.notifyDataSetChanged();
                    }

                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("json error", error.toString());
                    }
                }
        );

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
*/


        return root;
    }

    public static void fillList(Post p){
        posts.add(p);
    }
    public static void clearList (){
        posts.clear();
    }
}