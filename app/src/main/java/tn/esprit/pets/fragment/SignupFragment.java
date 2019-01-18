package tn.esprit.pets.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import tn.esprit.pets.R;
import tn.esprit.pets.service.MySingleton;
import tn.esprit.pets.service.UserService;

public class SignupFragment extends Fragment {

    EditText username, password, email, phoneNumber;
    Button signup;

    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_signup, container, false);
        username= root.findViewById(R.id.username);
        password= root.findViewById(R.id.password);
        email= root.findViewById(R.id.email);
        signup= root.findViewById(R.id.login);
        phoneNumber = root.findViewById(R.id.phone);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();
                String phone = phoneNumber.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Warning");
                builder.setMessage("Make sure that :\n - Your username contains 5 letters at least" +
                        "\n - Your password contains at least 1 digit " +
                        "\n - Your email format is correct" +
                        "\n - You inserted a phone correct phone number");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //TODO
                if (name.length()>4 && verifPassword(pass) && verifMail(mail) && verifPhoneNumber(phone)){
                    addUser(root.getContext(), name, pass, mail, phone);
                    getFragmentManager().popBackStack();
                }
                else
                   builder.show();
            }
        });
        return root;
    }

    public boolean verifPhoneNumber(String phoneNumber) {
        System.out.println("phone" +phoneNumber.length());
        if (phoneNumber.length()==8)
        return true;
        return false;
    }

    public boolean verifMail(String mail) {
        Boolean valide = false;
        int i, j, k;
        for (j = 1; j < mail.length(); j++) {
            if (mail.charAt(j) == '@') {
                if (j < mail.length() - 4) {
                    for (k = j; k < mail.length() - 2; k++) {
                        if (mail.charAt(k) == '.') {
                            valide = true;
                        }
                    }
                }
            }
        }
        return valide;
    }

    public boolean verifPassword(String password) {
        boolean test=false;
        boolean test2=false;
        int i;
        for (i=0;i<password.length();i++) {
            if(Character.isDigit(password.charAt(i))) {
                test=true;
                i=password.length();
                //System.out.println("test 1 :" +test);
            }
        }
        if(password.length()>4) {
            test2=true;
            //System.out.println(test2);
        }
        if (test==test2) {
            return test;
        }
        return false;
    }

    public void addUser(Context context, final String username, final String password, final String email, final String phone) {
        String Url = "http://" + MySingleton.getIp() + "/PetsWS/user/addUser.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                Url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", email);
                params2.put("username", username);
                params2.put("password", password);
                params2.put("phone", phone);
                params2.put("token", FirebaseInstanceId.getInstance().getToken());
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
