package tn.esprit.pets.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.internal.Util;
import tn.esprit.pets.R;
import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.fragment.PostDetailsFragment;

public class PostsAdapter extends ArrayAdapter<Post>{

    FragmentManager fragmentManager;

    public PostsAdapter(Context context, ArrayList<Post> contacts) {
        super(context, 0, contacts);
        fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Post post = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }

        TextView description = (TextView) convertView.findViewById(R.id.post_description);
        description.setText(post.getDescription());

        ImageView image = (ImageView) convertView.findViewById(R.id.post_image);
        Picasso.with(getContext()).load(post.getImageUrl()).into(image);

        TextView date = (TextView) convertView.findViewById(R.id.post_date);
        date.setText(new SimpleDateFormat("dd MM yyyy").format(post.getDate()));

        ImageView type = (ImageView) convertView.findViewById(R.id.type_image);
        TextView text = (TextView) convertView.findViewById(R.id.type_text);
        if (post.getPetType().equals(PetType.CAT) && post.getType().equals("lost")) {
            text.setText("Lost cat near " + post.getTown());
            type.setImageResource(R.drawable.l);
            //type.setBackground(R.drawable.tag_pink);
        } else if (post.getPetType().equals(PetType.CAT) && post.getType().equals("found")) {
            text.setText("Cat found near " + post.getTown());
            type.setImageResource(R.drawable.f);
        } else if (post.getPetType().equals(PetType.DOG) && post.getType().equals("lost")) {
            text.setText("Lost dog near " + post.getTown());
            type.setImageResource(R.drawable.l);
        } else if (post.getPetType().equals(PetType.DOG) && post.getType().equals("found")) {
            text.setText("Dog found near " + post.getTown());
            type.setImageResource(R.drawable.f);
        } else if (post.getPetType().equals(PetType.OTHER) && post.getType().equals("lost")) {
            text.setText("Lost pet near " + post.getTown());
            type.setImageResource(R.drawable.l);
        } else if (post.getPetType().equals(PetType.OTHER) && post.getType().equals("found")) {
            text.setText("Pet found near " + post.getTown());
            type.setImageResource(R.drawable.f);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new PostDetailsFragment();
                Bundle b = new Bundle();
                b.putInt("id_post", post.getId());
                fragment.setArguments(b);
                fragmentManager.beginTransaction().addToBackStack("fragment").replace(R.id.drawer_layout, fragment).commit();
            }
        });
        return convertView;
    }
}

