package tn.esprit.pets.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Set;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.LoginActivity;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.service.UserService;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {


    Button signout, deleteAccount;
    Switch notifications;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String deleteUserURL = "http://" + MySingleton.getIp() + "/PetsWS/user/deleteUser.php";

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);


        signout = (Button) root.findViewById(R.id.signout);
        deleteAccount = (Button) root.findViewById(R.id.delete_account);
        notifications = (Switch) root.findViewById(R.id.notification_switch);
        final Intent intent = new Intent(getActivity(), LoginActivity.class);
        sharedPreferences = getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isConnect", false);
                editor.commit();
                startActivity(intent);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete your account? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putBoolean("isConnect", false);
                        editor.commit();
                        deleteUser(root.getContext(), sharedPreferences.getInt("id", 0));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();



            }
        });

        if(sharedPreferences.getBoolean("notifsOn", true)) {
            notifications.setText("Disable push notifications");
        } else {
            notifications.setText("Enable push notifications");
        }

        notifications.setChecked(sharedPreferences.getBoolean("notifsOn", true));

        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifications.setText("Disable push notifications");
                    editor.putBoolean("notifsOn", true);
                    editor.commit();
                } else {
                    notifications.setText("Enable push notifications");
                    editor.putBoolean("notifsOn", false);
                    editor.commit();
                }
            }
        });

        return root;
    }

    public void deleteUser(Context context, int id_user) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                deleteUserURL + "?id=" +  id_user,
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
