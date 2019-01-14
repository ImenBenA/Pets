package tn.esprit.pets.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tn.esprit.pets.entity.Notification;

public class DBHelper extends SQLiteOpenHelper {

    //	notification ====> id	title	body	date	user_id	post_id
    public static final String TABLE_NOTIFICATION = "notification";
    public static final String ID_NOTIFICATION = "id_notification";
    public static final String TITLE_NOTIFICATION = "title_notification";
    public static final String BODY_NOTIFICATION = "body_notification";
    public static final String DATE_NOTIFICATION = "date_notification";
    public static final String USER_ID_NOTIFICATION = "user_id_notification";
    public static final String POST_ID_NOTIFICATION = "post_id_notification";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + " (" +
            ID_NOTIFICATION + " TEXT, " +
            TITLE_NOTIFICATION + " TEXT, " +
            BODY_NOTIFICATION + " TEXT, " +
            DATE_NOTIFICATION + " TEXT, " +
            USER_ID_NOTIFICATION + " TEXT, " +
            POST_ID_NOTIFICATION + " TEXT);";

    public DBHelper(Context context) {
        super(context,TABLE_NOTIFICATION , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION + ";");
        onCreate(db);
    }

    public boolean insertNotification(Notification n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_NOTIFICATION, n.getId());
        contentValues.put(TITLE_NOTIFICATION, n.getTitle());
        contentValues.put(BODY_NOTIFICATION, n.getBody());
        contentValues.put(DATE_NOTIFICATION, n.getDate().toString());
        contentValues.put(USER_ID_NOTIFICATION, n.getUser().getId());
        contentValues.put(POST_ID_NOTIFICATION, n.getPost().getId());
        db.insert(TABLE_NOTIFICATION, null, contentValues);
        return true;
    }

    public ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NOTIFICATION, null);
        if (res.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(Integer.parseInt(res.getString(0)));
                notification.setTitle(res.getString(1));
                notification.setBody(res.getString(2));
                try {
                    notification.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(res.getString(3)));
                } catch (ParseException e) {
                    //e.printStackTrace();
                }
                //TODO
                notification.setUser(null);
                notification.setPost(null);
                notifications.add(notification);
            } while (res.moveToNext());
        }
        Log.e("LISTE", notifications.toString());
        return notifications;
    }

    public int removeAllNotifications() {
        return this.getWritableDatabase().delete(DBHelper.TABLE_NOTIFICATION, null,null);
    }

    public int removeNotification(String id) {
        return this.getWritableDatabase().delete(DBHelper.TABLE_NOTIFICATION,
                "'" + DBHelper.ID_NOTIFICATION + "'=?",
                new String[] {String.valueOf(id)});
    }

    public void deleteNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, ID_NOTIFICATION + " = ?",
                new String[] { String.valueOf(notification.getId()) });
        db.close();
    }
}

