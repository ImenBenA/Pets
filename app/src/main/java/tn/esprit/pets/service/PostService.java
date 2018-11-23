package tn.esprit.pets.service;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;

public class PostService {
    private String getAllURL = "http://10.0.2.2:18080/WSPets-web/api/post/all";
    //private List<User> users = new ArrayList<>();
    private boolean ok;
    private ArrayList<Post> list = new ArrayList<>();



    public PostService() {

    }

    private ArrayList<Post> extractList(JSONArray response) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                // Get current json object
                JSONObject jsonObject = response.getJSONObject(i);
                // Get the current student (json object) data
                int id = jsonObject.getInt("id");
                String description = jsonObject.getString("description");
                String imageUrl = jsonObject.getString("petImage");
                //User user = jsonObject.getJSONObject("user");
                String type = jsonObject.getString("type");
                // Display the formatted json data in text view
                Post post = new Post(id, description, imageUrl, new User(), type);
                posts.add(post);

            }
            Log.v("posts response", posts.toString());
            list = posts;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public ArrayList<Post> getAllPosts(Context context) {
        // Initialize a new RequestQueue instance
        //RequestQueue requestQueue = Volley.newRequestQueue(context);
        RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getAllURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        //List<User> users = new ArrayList<>();
                        // Process the JSON
                        Log.e("json response", response.toString());
                        ArrayList<Post> posts = new ArrayList<>();

                        try {
                            // Loop through the array elements

                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Get the current student (json object) data
                                int id = jsonObject.getInt("id");
                                String description = jsonObject.getString("description");
                                String imageUrl = jsonObject.getString("petImage");
                                //User user = jsonObject.getJSONObject("user");
                                String type = jsonObject.getString("type");
                                // Display the formatted json data in text view
                                Post post = new Post(id, description, imageUrl, new User(), type);
                                posts.add(post);

                            }
                            Log.v("posts response", posts.toString());
                            list = posts;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        list = posts;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.e("json error", error.toString());
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        //requestQueue.add(jsonArrayRequest);
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        Log.v("posts", list.toString());
        return list;
    }

    public ArrayList<Post> getAll(Context context) {
        ArrayList<Post> posts = new ArrayList<>();
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(getAllURL, future, future);
        //request.setRetryPolicy(RetryPolicyFactory.build());
        //RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
        MySingleton.getInstance(context).addToRequestQueue(request);
        try {
            JSONArray response = future.get();
            try {
                for (int i = 0; i < response.length(); i++) {
                    // Get current json object
                    JSONObject jsonObject = response.getJSONObject(i);
                    // Get the current student (json object) data
                    int id = jsonObject.getInt("id");
                    String description = jsonObject.getString("description");
                    String imageUrl = jsonObject.getString("petImage");
                    //User user = jsonObject.getJSONObject("user");
                    String type = jsonObject.getString("type");
                    // Display the formatted json data in text view
                    Post post = new Post(id, description, imageUrl, new User(), type);
                    posts.add(post);
                }
                Log.v("posts response", posts.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return FrontPageMarshaller.marshall(response);
        } catch (Exception e) {
            Log.v("posts response", "err");
            return null;
        }
        return posts;
    }

    /*
    RequestFuture<JSONObject> future = RequestFuture.newFuture();
JsonObjectRequest request = new JsonObjectRequest(URL, new JSONObject(), future, future);
requestQueue.add(request);

try {
  JSONObject response = future.get(); // this will block
} catch (InterruptedException e) {
  // exception handling
} catch (ExecutionException e) {
  // exception handling
}
     */


}
