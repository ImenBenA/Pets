package tn.esprit.pets.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import tn.esprit.pets.activity.LoginActivity;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.adapter.PostsAdapter;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    View root;
    TextView username, password, email, usernameTitle, phone;
    EditText usernameEdit, passwordEdit, emailEdit, usernameTitleEdit, phoneEdit;
    ImageView picture;
    Button edit;
    boolean editing = false;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);



        username = (TextView) root.findViewById(R.id.username);
        usernameTitle = (TextView) root.findViewById(R.id.usernameTitle);
        password = (TextView) root.findViewById(R.id.password);
        email = (TextView) root.findViewById(R.id.email);
        phone = (TextView) root.findViewById(R.id.phone);
        usernameEdit = (EditText) root.findViewById(R.id.usernameedit);
        passwordEdit = (EditText) root.findViewById(R.id.passwordedit);
        emailEdit = (EditText) root.findViewById(R.id.emailedit);
        phoneEdit = (EditText) root.findViewById(R.id.phoneedit);
        edit = (Button) root.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editing) {
                    username.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    usernameEdit.setVisibility(View.VISIBLE);
                    emailEdit.setVisibility(View.VISIBLE);
                    passwordEdit.setVisibility(View.VISIBLE);
                    phoneEdit.setVisibility(View.VISIBLE);
                    edit.setText("Save");
                    //update on the ws
                    editing = true;
                }
                else {
                    username.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    usernameEdit.setVisibility(View.GONE);
                    emailEdit.setVisibility(View.GONE);
                    passwordEdit.setVisibility(View.GONE);
                    phoneEdit.setVisibility(View.GONE);
                    edit.setText("Edit");
                    editing = false;

                }

            }
        });
        
        //username.setText(MainActivity.userConnected.getUsername());
        //password.setText(MainActivity.userConnected.getPassword());
        //email.setText(MainActivity.userConnected.getEmail());

        return root;
    }

}
