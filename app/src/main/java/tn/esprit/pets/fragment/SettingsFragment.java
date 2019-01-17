package tn.esprit.pets.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Set;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.LoginActivity;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.service.UserService;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {


    Button signout;
    Switch notifications;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);


        signout = (Button) root.findViewById(R.id.signout);
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
}
