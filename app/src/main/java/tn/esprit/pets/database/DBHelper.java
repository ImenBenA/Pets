package tn.esprit.pets.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tn.esprit.pets.entity.Notification;
import tn.esprit.pets.entity.Post;
import tn.esprit.pets.entity.User;
import tn.esprit.pets.utils.Utils;

public class DBHelper extends SQLiteOpenHelper {

    //	notification ====> id	title	body	date	user_id	post_id
    public static final String TABLE_NOTIFICATION = "notification";
    public static final String ID_NOTIFICATION = "id_notification";
    public static final String TITLE_NOTIFICATION = "title_notification";
    public static final String BODY_NOTIFICATION = "body_notification";
    public static final String DATE_NOTIFICATION = "date_notification";
    public static final String USER_ID_NOTIFICATION = "user_id_notification";
    public static final String POST_ID_NOTIFICATION = "post_id_notification";
    // POST DB
    public static final String TABLE_POST = "post";
    public static final String ID_POST = "id_post";
    public static final String DESCRIPTION_POST = "description_post";
    public static final String TYPE_POST = "type_post";
    public static final String DATE_POST = "date_post";
    public static final String PET_TYPE_POST = "pet_type_post";
    public static final String TOWN_POST = "town_post";
    public static final String PET_IMAGE_POST = "pet_image_post";
    public static final String USER_ID_POST = "user_id_post";
    public static final String USER_NAME_POST = "user_name_post";
    public static final String USER_PHONE_POST = "user_phone_post";

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pets.db";
    private static final String CREATE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + " (" +
            ID_NOTIFICATION + " TEXT, " +
            TITLE_NOTIFICATION + " TEXT, " +
            BODY_NOTIFICATION + " TEXT, " +
            DATE_NOTIFICATION + " TEXT, " +
            USER_ID_NOTIFICATION + " TEXT, " +
            POST_ID_NOTIFICATION + " TEXT);";
    private static final String CREATE_POST = "CREATE TABLE " + TABLE_POST + " (" +
            ID_POST + " TEXT, " +
            DESCRIPTION_POST + " TEXT, " +
            TYPE_POST + " TEXT, " +
            PET_TYPE_POST + " TEXT, " +
            USER_ID_POST + " TEXT, " +
            PET_IMAGE_POST + " TEXT, " +
            USER_NAME_POST + " TEXT, " +
            USER_PHONE_POST + " TEXT, " +
            DATE_POST + " TEXT, " +
            TOWN_POST + " TEXT);";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTIFICATION);
        db.execSQL(CREATE_POST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST + ";");
        onCreate(db);
    }

    // NOTIFICATION METHODS
    public boolean insertNotification(Notification n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_NOTIFICATION, n.getId());
        contentValues.put(TITLE_NOTIFICATION, n.getTitle());
        contentValues.put(BODY_NOTIFICATION, n.getBody());
        contentValues.put(DATE_NOTIFICATION, new SimpleDateFormat("yyyy-MM-dd").format( n.getDate()));
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
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date=new SimpleDateFormat("yyyy-MM-dd").parse(res.getString(3));
                    notification.setDate(date);
                } catch (ParseException e) {
                    //e.printStackTrace();
                }
                //TODO
                notification.setUser(null);
                notification.setPost(null);
                notifications.add(notification);
            } while (res.moveToNext());
        }
        //Log.e("LISTE", notifications.toString());
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
    // POST METHODS
    public boolean insertPost(Post n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_POST, n.getId());
        contentValues.put(DESCRIPTION_POST, n.getDescription());
        contentValues.put(TYPE_POST, n.getType());
        //System.out.println("fel ajout " +new SimpleDateFormat("yyyy-MM-dd").format( n.getDate()));
        contentValues.put(DATE_POST,new SimpleDateFormat("yyyy-MM-dd").format( n.getDate()) );
        contentValues.put(PET_TYPE_POST, n.getPetType().toString());
        contentValues.put(TOWN_POST, n.getTown().toString());
        contentValues.put(PET_IMAGE_POST, n.getImageUrl());
        contentValues.put(USER_ID_POST, n.getUser().getId());
        contentValues.put(USER_NAME_POST, n.getUser().getUsername());
        contentValues.put(USER_PHONE_POST, n.getUser().getPhone());
        db.insert(TABLE_POST, null, contentValues);
        return true;
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_POST, null);
        if (res.moveToFirst()) {
            do {
                Post post = new Post();
                post.setId(Integer.parseInt(res.getString(0)));
                post.setDescription(res.getString(1));
                post.setType(res.getString(2));
                post.setPetType(new Utils().stringToPetType(res.getString(3)));
                post.setImageUrl(res.getString(5));
                post.setTown(new Utils().stringToTown(res.getString(9)));
                User user = new User();
                user.setId(Integer.parseInt(res.getString(4)));
                user.setPhone(res.getString(7));
                user.setUsername(res.getString(6));
                post.setUser(user);
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //System.out.println("date fel bd " +res.getString(8));
                Date date = null;
                try {
                    //System.out.println("fel get " +res.getString(8));
                    date=new SimpleDateFormat("yyyy-MM-dd").parse(res.getString(8));
                } catch (ParseException e) {
                    System.out.println("date erreur");
                }
                post.setDate(date);

                //TODO
                posts.add(post);
            } while (res.moveToNext());
        }
        //Log.e("LISTE", notifications.toString());
        return posts;
    }

    public int removeAllPosts() {
        return this.getWritableDatabase().delete(DBHelper.TABLE_POST, null,null);
    }

    public int removePost(String id) {
        return this.getWritableDatabase().delete(DBHelper.TABLE_POST,
                "'" + DBHelper.ID_POST + "'=?",
                new String[] {String.valueOf(id)});
    }

    public void deletePost(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POST, ID_POST + " = ?",
                new String[] { String.valueOf(notification.getId()) });
        db.close();
    }
}

