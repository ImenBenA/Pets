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
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.adapter.NotificationAdapter;
import tn.esprit.pets.entity.Notification;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;

public class NotificationFragment extends Fragment {
    private String getAllURL = "http://"+MySingleton.getIp()+"/PetsWS/notification/allNotification.php?id="+MainActivity.userConnected.getId();
    static ArrayList<Notification> notification = new ArrayList<>();
    View root;
    NotificationAdapter itemsAdapter;

    public void NotificationFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notification, container, false);
        itemsAdapter = new NotificationAdapter(root.getContext(), notification);
        ListView listView = (ListView) root.findViewById(R.id.notification);
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
                            notification.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                    String title = jsonObject.getString("title");
                                    int id = jsonObject.getInt("id");
                                    String body = jsonObject.getString("body");
                                    //String type = jsonObject.getString("type");
                                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = null;
                                    try {
                                        date = (Date) simpleDateFormat.parse(jsonObject.getString("date"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject userObject = (JSONObject) jsonObject.get("user_id");
                                    JSONObject postObject = (JSONObject) jsonObject.get("post_id");
                                    Post post = new Post(postObject.getInt("id"),postObject.getString("description"),postObject.getString("petImage"), null, postObject.getString("type"), null);
                                    //User user = new User(userObject.getInt("id"), userObject.getString("username"), userObject.getString("password"),userObject.getString("phone"));
                                    User user = new User(userObject.getInt("id"), userObject.getString("username"), userObject.getString("password"),"");
                                    Notification notif = new Notification(id,title,body,date,user,post);
                                    notification.add(notif);


                            }
                            Log.v("posts response", notification.toString());

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
