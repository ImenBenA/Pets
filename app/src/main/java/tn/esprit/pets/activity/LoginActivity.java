package tn.esprit.pets.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import tn.esprit.pets.R;
import tn.esprit.pets.fragment.AddPostFragment;
import tn.esprit.pets.fragment.SignupFragment;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout relativeLayout1, relativeLayout2;
    Button login, signup;
    EditText username, password;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relativeLayout1.setVisibility(View.VISIBLE);
            relativeLayout2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relative_layout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relative_layout2);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);



        handler.postDelayed(runnable, 2000);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.login_layout, new SignupFragment()).commit();
            }
        });


    }
}
