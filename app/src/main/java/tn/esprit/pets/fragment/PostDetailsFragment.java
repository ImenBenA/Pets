package tn.esprit.pets.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    Button button;
    ImageView type;
    TextView text;
    Post post;

    String deletePostURL = "http://" + MySingleton.getIp() + "/pets/post/deletePost.php";

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

        button = (Button) root.findViewById(R.id.button);
        if (MainActivity.userConnected.getId() == post.getUser().getId()) {
            //delete.setVisibility(View.VISIBLE);
            button.setText("Delete this post");
            button.setBackgroundResource(R.drawable.btn_bg_danger);
            if (!isNetworkAvailable()) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to delete this post ?");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePost(root.getContext(),idPost);
                            MainActivity.listPost.remove(post);
                            LostFragment.lost.remove(post);
                            FoundFragment.found.remove(post);
                            MyPostsFragment.myPosts.remove(post);
                            System.out.print("listaaaaa " + MyPostsFragment.myPosts.toString());
                            MainActivity.init(getContext());
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }
                    });
                    builder.show();
                }
            });
        } else {
            button.setText("Call " + post.getUser().getUsername().toString());
            button.setOnClickListener(new View.OnClickListener() {
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
