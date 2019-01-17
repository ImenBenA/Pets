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
import java.util.List;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.adapter.NotificationAdapter;
import tn.esprit.pets.entity.Notification;
import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.Town;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.utils.Utils;

public class NotificationFragment extends Fragment {
    static List<Notification> notification = new ArrayList<>();
    View root;
    NotificationAdapter itemsAdapter;

    public void NotificationFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notification, container, false);
        notification = MainActivity.listNotification;
        itemsAdapter = new NotificationAdapter(root.getContext(), (ArrayList<Notification>) notification);
        ListView listView = (ListView) root.findViewById(R.id.notification);
        listView.setAdapter(itemsAdapter);
        return root;
    }

}
