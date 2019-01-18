package tn.esprit.pets.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.Town;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.utils.Utils;

public class PostDetailsFragment extends Fragment {

    public PostDetailsFragment() {
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    View root;
    int idPost;
    TextView description;
    TextView date;
    ImageView image;
    Button call, delete;
    ImageView type;
    TextView text;
    Post post;

    String deletePostURL = "http://" + MySingleton.getIp() + "/PetsWS/post/deletePost.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post_details, container, false);
        Bundle extras = getArguments();
        idPost = extras.getInt("id_post");

        for (Post p : MainActivity.listPost) {
            if(p.getId() == idPost) {
                post = p;
            }
        }

        description = (TextView) root.findViewById(R.id.post_description);
        description.setText(post.getDescription());

        image = (ImageView) root.findViewById(R.id.post_image);
        Picasso.with(getContext()).load(post.getImageUrl()).into(image);

        date = (TextView) root.findViewById(R.id.post_date);
        date.setText(new SimpleDateFormat("dd MM yyyy").format(post.getDate()));

        call = (Button) root.findViewById(R.id.call);
        call.setText("Call " + post.getUser().getUsername().toString());
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ post.getUser().getPhone()));
                try {
                    startActivity(callIntent);
                } catch (SecurityException se) {
                }
            }
        });

        delete = (Button) root.findViewById(R.id.delete);

        if (MainActivity.userConnected.getId() == post.getUser().getId()) {
            delete.setVisibility(View.VISIBLE);
            if (!isNetworkAvailable()) {
                delete.setEnabled(false);
            } else {
                delete.setEnabled(true);
            }
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                    deletePost(root.getContext(),idPost);
                    MainActivity.listPost.remove(post);
                    LostFragment.lost.remove(post);
                    FoundFragment.found.remove(post);
                }
            });
        } else {
            delete.setVisibility(View.INVISIBLE);
        }

        type = (ImageView) root.findViewById(R.id.type_image);
        text = (TextView) root.findViewById(R.id.type_text);
        if (post.getPetType().equals(PetType.CAT) && post.getType().equals("lost")) {
            text.setText("Lost cat near " + post.getTown());
            type.setImageResource(R.drawable.l);
            type.setBackgroundResource(R.drawable.tag_pink);
        } else if (post.getPetType().equals(PetType.CAT) && post.getType().equals("found")) {
            text.setText("Cat found near " + post.getTown());
            type.setImageResource(R.drawable.f);
            type.setBackgroundResource(R.drawable.tag_green);
        } else if (post.getPetType().equals(PetType.DOG) && post.getType().equals("lost")) {
            text.setText("Lost dog near " + post.getTown());
            type.setImageResource(R.drawable.l);
            type.setBackgroundResource(R.drawable.tag_pink);
        } else if (post.getPetType().equals(PetType.DOG) && post.getType().equals("found")) {
            text.setText("Dog found near " + post.getTown());
            type.setImageResource(R.drawable.f);
            type.setBackgroundResource(R.drawable.tag_green);
        } else if (post.getPetType().equals(PetType.OTHER) && post.getType().equals("lost")) {
            text.setText("Lost pet near " + post.getTown());
            type.setImageResource(R.drawable.l);
            type.setBackgroundResource(R.drawable.tag_pink);
        } else if (post.getPetType().equals(PetType.OTHER) && post.getType().equals("found")) {
            text.setText("Pet found near " + post.getTown());
            type.setImageResource(R.drawable.f);
            type.setBackgroundResource(R.drawable.tag_green);
        }

        return root;
    }

    public void deletePost(Context context, int id_post) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                deletePostURL + "?id=" +  id_post,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("obj error", error.toString());
                    }
                } );
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
