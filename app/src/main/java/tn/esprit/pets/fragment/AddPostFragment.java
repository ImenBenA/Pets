package tn.esprit.pets.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import tn.esprit.pets.R;
import tn.esprit.pets.activity.MainActivity;
import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.service.PostService;

import static android.app.Activity.RESULT_OK;

public class AddPostFragment extends Fragment {

    Button add,addImage;
    EditText etDescription;
    RadioGroup radioGroup;
    Spinner townSpinner, petTypeSpinner;
    ImageView displayImage;
    Bitmap bitmap;
    PostService ps=new PostService();
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
        final View root = inflater.inflate(R.layout.fragment_add_post, container, false);
        //signup = (Button) findViewById(R.id.signup);
        add = root.findViewById(R.id.add);
        etDescription = root.findViewById(R.id.description);
        addImage = root.findViewById(R.id.addImage);
        displayImage= root.findViewById(R.id.displayImage);
        radioGroup = (RadioGroup) root.findViewById(R.id.type);
        townSpinner = (Spinner) root.findViewById(R.id.towns);
        petTypeSpinner = (Spinner) root.findViewById(R.id.pet_type);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"select Picture"),999);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    String radiovalue = ((RadioButton) root.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String type = "";
                    if (radiovalue.equals("Looking for a pet"))
                        type = "lost";
                    else if (radiovalue.equals("Found a pet"))
                        type = "found";

                    String townString = townSpinner.getSelectedItem().toString();
                    String petString = petTypeSpinner.getSelectedItem().toString();

                    String description = etDescription.getText().toString();
                    String imageUrl = getStringImage(bitmap);
                    imageUrl = imageUrl.replaceAll(System.getProperty("line.separator"), "");
                    ps.addPost(getContext(), description, imageUrl, type, townString, petString);
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
        return root;
    }

    public String getStringImage(Bitmap bm){
        ByteArrayOutputStream ba=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,ba);
        byte[] imageByte= ba.toByteArray();
        String encode=Base64.encodeToString(imageByte,Base64.DEFAULT);
        return  encode;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==999 && resultCode==RESULT_OK && data != null){
            Uri filepath= data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filepath);
                displayImage.setImageBitmap(bitmap);
                addImage.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
