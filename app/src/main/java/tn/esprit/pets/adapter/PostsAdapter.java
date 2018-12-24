package tn.esprit.pets.adapter;

import android.content.Context;
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

public class PostsAdapter extends ArrayAdapter<Post>{

    public PostsAdapter(Context context, ArrayList<Post> contacts) {
        super(context, 0, contacts);
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

        return convertView;
    }
}

