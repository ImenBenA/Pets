package tn.esprit.pets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tn.esprit.pets.R;
import tn.esprit.pets.entity.Notification;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    public NotificationAdapter(Context context, ArrayList<Notification> contacts) {
        super(context, 0, contacts);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Notification notification = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.notification_title);
        TextView body = (TextView) convertView.findViewById(R.id.notification_body);
        title.setText(notification.getTitle());
        body.setText(notification.getBody());
        TextView date = (TextView) convertView.findViewById(R.id.notification_date);
        date.setText(new SimpleDateFormat("dd MM yyyy").format(notification.getDate()));
        //image.setImageResource(R.drawable.lost);
        //Picasso.get().load(notification.getImageUrl()).resize(50, 50).centerCrop().into(image);

        return convertView;
    }
}
