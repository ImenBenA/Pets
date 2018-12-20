package tn.esprit.pets.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tn.esprit.pets.R;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    View root;
    User userConnected;
    String getURL = "http://" + MySingleton.getIp() + "/PetsWS/user/userById.php?id=";
    String updateUEL = "http://" + MySingleton.getIp() + "/PetsWS/user/updateUser.php";
    TextView username, password, email, usernameTitle, phone;
    EditText usernameEdit, passwordEdit, emailEdit, usernameTitleEdit, phoneEdit;
    ImageView picture;
    Button edit;
    int userId;
    boolean editing = false;
    boolean saving = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getContext().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getInt("id", 0);
        getUserConnectedArray(String.valueOf(userId));


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
                    editing = true;
                } else {
                    updateUser(getContext(), usernameEdit.getText().toString(),
                            passwordEdit.getText().toString(),
                            emailEdit.getText().toString(),
                            String.valueOf(userId),
                            phoneEdit.getText().toString());

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
        return root;
    }

    public void getUserConnectedArray(final String id) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getURL + id,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("json response", response.toString());
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            int userId = jsonObject.getInt("id");
                            String usernamel = jsonObject.getString("username");
                            String passwordl = jsonObject.getString("password");
                            String emaill = jsonObject.getString("email");
                            String phonel = jsonObject.getString("phone");
                            userConnected = new User(userId, usernamel, passwordl, emaill, phonel);

                            usernameTitle.setText(userConnected.getUsername());
                            username.setText(userConnected.getUsername());
                            usernameEdit.setText(userConnected.getUsername());
                            password.setText(userConnected.getPassword());
                            passwordEdit.setText(userConnected.getPassword());
                            email.setText(userConnected.getEmail());
                            emailEdit.setText(userConnected.getEmail());
                            phone.setText(userConnected.getPhone());
                            phoneEdit.setText(userConnected.getPhone());

                            Log.e("userfound", userConnected.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("array error", error.toString());
                    }
                }
        );
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    public void updateUser(Context context, final String usernamel, final String passwordl, final String emaill, final String id, final String phonel) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                updateUEL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("update resp", response.toString());
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                        } catch (JSONException e) {
                            Log.e("update exc", " ");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("json err upd", error.toString());
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", emaill);
                params2.put("username", usernamel);
                params2.put("password", passwordl);
                params2.put("phone", phonel);
                params2.put("id", id);
                usernameTitle.setText(usernamel);
                username.setText(usernamel);
                usernameEdit.setText(usernamel);
                password.setText(passwordl);
                passwordEdit.setText(passwordl);
                email.setText(emaill);
                emailEdit.setText(emaill);
                phone.setText(phonel);
                phoneEdit.setText(phonel);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }
}
