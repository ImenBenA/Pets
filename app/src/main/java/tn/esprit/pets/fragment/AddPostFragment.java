package tn.esprit.pets.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.entity.Post;

public class AddPostFragment extends Fragment {

    Button add;
    EditText etDescription, etImageUrl;
    RadioButton radioButtonL;
    RadioButton radioButtonF;

    public AddPostFragment() {
    }

    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_post, container, false);
        //signup = (Button) findViewById(R.id.signup);
        add = root.findViewById(R.id.add);
        etDescription = root.findViewById(R.id.description);
        etImageUrl = root.findViewById(R.id.imageUrl);
        radioButtonL = root.findViewById(R.id.looking);
        radioButtonF = root.findViewById(R.id.found);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                String imageUrl = etImageUrl.getText().toString();
                /*String type;
                if (radioButtonF.isChecked()) {
                    type = "found";
                } else {
                    type = "looking for";
                }*/
                MainActivity.posts.add(new Post(description,imageUrl));
            }
        });
        return root;
    }
}
