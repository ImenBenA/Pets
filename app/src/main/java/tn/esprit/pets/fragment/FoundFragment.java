package tn.esprit.pets.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.Town;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.utils.Utils;

public class FoundFragment extends Fragment {

    static ArrayList<Post> found = new ArrayList<>();
    View root;
    PostsAdapter itemsAdapter;

    public FoundFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);
        found.clear();
        for (Post p: MainActivity.listPost) {
            if (p.getType().equals("found")){
                found.add(p);
            }
        }
        System.out.println(found.size());
        itemsAdapter = new PostsAdapter(root.getContext(), found);
        ListView listView = (ListView) root.findViewById(R.id.posts);
        listView.setAdapter(itemsAdapter);

        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.message_layout);
        TextView message = (TextView) root.findViewById(R.id.message);

        if (found.size() == 0) {
            linearLayout.setVisibility(View.VISIBLE);
            message.setText("There are currently no found posts");
        } else {
            linearLayout.setVisibility(View.GONE);
        }

        return root;
    }
}
