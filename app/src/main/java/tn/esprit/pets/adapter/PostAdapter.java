package tn.esprit.pets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tn.esprit.pets.R;
import tn.esprit.pets.entity.Post;

public class PostAdapter extends BaseAdapter {

    private List<Post> posts;
    private Context context;

    public PostAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.coverflow_item, null);
            TextView description = (TextView) rowView.findViewById(R.id.coverflow_description);
            ImageView image = (ImageView) rowView.findViewById(R.id.coverfow_image);
            Picasso.with(context).load(posts.get(position).getImageUrl()).into(image);
            description.setText(posts.get(position).getDescription());
        }
        return rowView;
    }
}
