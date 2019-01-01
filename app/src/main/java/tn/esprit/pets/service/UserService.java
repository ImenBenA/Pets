package tn.esprit.pets.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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

import tn.esprit.pets.entity.User;

public class UserService {

    private String getAllURL = "http://" + MySingleton.getIp() + ":18080/WSPets-web/api/user/all";
    //private List<User> users = new ArrayList<>();
    private boolean ok;

    public UserService() {
    }

    /*public void getUsers(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getAllURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("json response", response.toString());
                        System.out.println("json response " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("json error", error.toString());
                    }
                }


        );
        requestQueue.add(objectRequest);
    }*/

    public void getAllUsers(Context context) {
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(context);


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

                        try {
                            // Loop through the array elements
                            List<User> users = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Get the current student (json object) data
                                int id = jsonObject.getInt("id");
                                String username = jsonObject.getString("username");
                                String password = jsonObject.getString("password");
                                String email = jsonObject.getString("email");
                                String phone = jsonObject.getString("phone");

                                // Display the formatted json data in text view
                                User user = new User(id, username, password, email, phone);
                                users.add(user);
                                Log.e("Array response", users.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        requestQueue.add(jsonArrayRequest);
    }

    public boolean isAuthentified(Context context, final String username, final String password) {
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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

                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String lusername = jsonObject.getString("username");
                                String lpassword = jsonObject.getString("password");

                                // Display the formatted json data in text view
                                if (lusername.equalsIgnoreCase(username) && lpassword.equalsIgnoreCase(password)) {
                                    ok = true;
                                    Log.e("is authentified", ok + "");
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("Array exception", ok + "");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.e("array error", error.toString());
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
        return ok;
    }

    public void addUser(Context context, final String username, final String password, final String email) {
        // Initialize a new RequestQueue instance
        String Url = "http://" + MySingleton.getIp() + "/PetsWS/user/addUser.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                Url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        //List<User> users = new ArrayList<>();
                        // Process the JSON
                        Log.e("json response", response.toString());
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String lusername = jsonObject.getString("username");
                                String lpassword = jsonObject.getString("password");

                            }
                        } catch (JSONException e) {
                            Log.e("Array exception", ok + "");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.e("json error", error.toString());
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", email);
                params2.put("username", username);
                params2.put("password", password);
                params2.put("phone", "2222");
                params2.put("token",FirebaseInstanceId.getInstance().getToken());
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
