package tn.esprit.pets.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.Town;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.utils.Utils;

public class PostService {
    private String getAllURL = "http://"+MySingleton.getIp()+":18080/WSPets-web/api/post/all";
    //private List<User> users = new ArrayList<>();
    private boolean ok;
    private static ArrayList<Post> list = new ArrayList<>();

    public ArrayList<Post> getList(){
        return list;
    }


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
                //Post post = new Post(id, description, imageUrl, new User(), type);
                //posts.add(post);

            }
            Log.v("posts response", posts.toString());
            list = posts;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }

    /*public void getAllPosts(Context context, final PostsAdapter p) {
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
                           // LostFragment.clearList();
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Get the current student (json object) data
                                int id = jsonObject.getInt("id");
                                String description = jsonObject.getString("description");
                                String imageUrl = jsonObject.getString("petImage");
                                //User user = jsonObject.getJSONObject("user");
                                String type = jsonObject.getString("type");
                                Date date = (Date) jsonObject.get("date");
                                // Display the formatted json data in text view
                                Post post = new Post(id, description, imageUrl, new User(), type, date);
                               // LostFragment.fillList(post);
                            }


                            Log.v("posts response", posts.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        p.notifyDataSetChanged();
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
        //Log.v("posts", list.toString());
        //System.out.println("size : "+list.size());
        //return list;
    }*/

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
                    //Post post = new Post(id, description, imageUrl, new User(), type);
                    //posts.add(post);
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
        Log.v("size ",posts.size()+"");
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

    public void addPost(final Context context, final String description, final String imageUrl, final String type, final String town, final String petType){
        String url="http://"+MySingleton.getIp()+"/PetsWS/post/addPost.php";
        RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject jsonObject = response.getJSONObject(0);
                } catch (JSONException e) {
                    MainActivity.init(context);
                    System.out.println("poooooooooooooooooooo");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("description", description);
                params2.put("petImage", imageUrl);
                params2.put("type", type);
                params2.put("user_id", MainActivity.userConnected.getId()+"");
                params2.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                params2.put("petType", petType);
                params2.put("town", town);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        ;
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

}
