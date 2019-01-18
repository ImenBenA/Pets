package tn.esprit.pets.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.service.MySingleton;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    View root;
    User userConnected;
    String getURL = "http://" + MySingleton.getIp() + "/PetsWS/user/userById.php?id=";
    String updateUEL = "http://" + MySingleton.getIp() + "/PetsWS/user/updateUser.php";
    TextView username, password, email, phone;
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = root.getContext().getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String name = sharedPreferences.getString("username", "");
        String pass = sharedPreferences.getString("password", "");
        String emai = sharedPreferences.getString("email", "");
        String phon = sharedPreferences.getString("phone", "");

        username = (TextView) root.findViewById(R.id.username);
        //usernameTitle = (TextView) root.findViewById(R.id.usernameTitle);
        password = (TextView) root.findViewById(R.id.password);
        email = (TextView) root.findViewById(R.id.email);
        phone = (TextView) root.findViewById(R.id.phone);
        usernameEdit = (EditText) root.findViewById(R.id.usernameedit);
        passwordEdit = (EditText) root.findViewById(R.id.passwordedit);
        emailEdit = (EditText) root.findViewById(R.id.emailedit);
        phoneEdit = (EditText) root.findViewById(R.id.phoneedit);
        edit = (Button) root.findViewById(R.id.edit);

        if (!isNetworkAvailable()) {
            edit.setEnabled(false);
            username.setText(name);
            password.setText(pass);
            email.setText(emai);
            phone.setText(phon);

        } else {
            edit.setEnabled(true);
            getUserConnectedArray();

            System.out.println(userConnected.getId());
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
                        System.out.println("user id" + userConnected.getId());
                        updateUser(getContext(), usernameEdit.getText().toString(),
                                passwordEdit.getText().toString(),
                                emailEdit.getText().toString(),
                                userConnected.getId()+"",
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
        }
        return root;
    }

    public void getUserConnectedArray() {

                            userConnected = MainActivity.userConnected;

                            //usernameTitle.setText(userConnected.getUsername());
                            username.setText(userConnected.getUsername());
                            usernameEdit.setText(userConnected.getUsername());
                            password.setText(userConnected.getPassword());
                            passwordEdit.setText(userConnected.getPassword());
                            email.setText(userConnected.getEmail());
                            emailEdit.setText(userConnected.getEmail());
                            phone.setText(userConnected.getPhone());
                            phoneEdit.setText(userConnected.getPhone());

    }

    public void updateUser(Context context, final String usernamel, final String passwordl, final String emaill, final String id, final String phonel) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                updateUEL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("update resp", response.toString());
                        JSONObject jsonObject = response;
                        //usernameTitle.setText(usernamel);
                        username.setText(usernamel);
                        usernameEdit.setText(usernamel);
                        password.setText(passwordl);
                        passwordEdit.setText(passwordl);
                        email.setText(emaill);
                        emailEdit.setText(emaill);
                        phone.setText(phonel);
                        phoneEdit.setText(phonel);
                        editor.putString("username",usernamel);
                        editor.putString("password",passwordl);
                        editor.commit();
                        MainActivity.userConnected.setPassword(passwordl);
                        MainActivity.userConnected.setEmail(emaill);
                        MainActivity.userConnected.setPhone(phonel);

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

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
