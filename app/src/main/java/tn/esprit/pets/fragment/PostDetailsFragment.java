package tn.esprit.pets.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import tn.esprit.pets.R;
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;

public class PostDetailsFragment extends Fragment {

    public PostDetailsFragment() {
    }

    View root;
    int id_post;
    TextView description;
    TextView date;
    ImageView image;

    String findPostByIdURL = "http://" + MySingleton.getIp() + "/PetsWS/post/postById.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post_details, container, false);
        Bundle extras = getArguments();
        id_post = extras.getInt("id_post");



        description = (TextView) root.findViewById(R.id.post_description);
        image = (ImageView) root.findViewById(R.id.post_image);
        date = (TextView) root.findViewById(R.id.post_date);

        findPostById(root.getContext(), id_post);

        return root;
    }

    public void findPostById(Context context, int id_post) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                findPostByIdURL + "?id=" +  id_post,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("found post", response.toString());

                        try {
                            System.out.println(response.toString());
                            if(!response.toString().equals("{}")) {
                                JSONObject jsonObject = response;
                                //int id = Integer.parseInt(jsonObject.getString("id"));
                                String descriptionl = jsonObject.getString("description");
                                String petImage = jsonObject.getString("petImage");
                                String type = jsonObject.getString("type");
                                //String user_id = jsonObject.getString("user_id");
                                User user = new User();
                                String datel= jsonObject.getString("date");

                                date.setText(datel);
                                description.setText(descriptionl);
                                Picasso.with(getContext()).load("http://" + MySingleton.getIp() + "/PetsWS/post/"+petImage).into(image);
                            }
                        } catch (JSONException e) {
                            Log.e("found p exc",   "");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("obj error", error.toString());
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    /*Intent callIntent = new Intent(Intent.ACTION_CALL);
callIntent.setData(Uri.parse("tel:123456789"));
startActivity(callIntent);
<uses-permission android:name="android.permission.CALL_PHONE" />*/

}
