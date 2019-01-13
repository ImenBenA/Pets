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

import java.util.ArrayList;

import tn.esprit.pets.R;
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
        TextView date = (TextView) convertView.findViewById(R.id.post_date);
        date.setText(post.getDate().toString());
        Picasso.with(getContext()).load(post.getImageUrl()).into(image);
        //image.setImageResource(R.drawable.lost);
        //Picasso.get().load(post.getImageUrl()).resize(50, 50).centerCrop().into(image);

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

