package tn.esprit.pets.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Set;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.LoginActivity;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.service.UserService;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {


    Button signout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);


        signout = root.findViewById(R.id.signout);
        final Intent intent = new Intent(getActivity(), LoginActivity.class);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putBoolean("isConnect", false);
                editor.commit();
                startActivity(intent);
            }
        });
        return root;
    }
}
