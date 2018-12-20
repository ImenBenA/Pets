package tn.esprit.pets.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tn.esprit.pets.R;
import tn.esprit.pets.service.UserService;

public class SignupFragment extends Fragment {

    EditText username,password,email;
    Button signup;
    UserService us=new UserService();

    public SignupFragment() {
    }

    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_signup, container, false);
        username= root.findViewById(R.id.username);
        password= root.findViewById(R.id.password);
        email= root.findViewById(R.id.email);
        signup= root.findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();
                //if (name.length()>5 && verifPassword(pass) && verifMail(mail)){
                    us.addUser(root.getContext(),name,pass,mail);
                    getFragmentManager().popBackStack();
               // }
                //else
                   // System.out.println("verifier vos données");
            }
        });
        return root;
    }
    public boolean verifMail(String a) {

        Boolean valide = false;
        int i, j, k;
        for (j = 1; j < a.length(); j++) {
            if (a.charAt(j) == '@') {
                if (j < a.length() - 4) {
                    for (k = j; k < a.length() - 2; k++) {
                        if (a.charAt(k) == '.') {
                            valide = true;
                        }
                    }
                }
            }
        }

        return valide;
    }
    public boolean verifPassword(String password)
    {
        boolean test=false;
        boolean test2=false;
        int i;
        for (i=0;i<password.length();i++)
        {
            if(Character.isDigit(password.charAt(i)))
            {
                test=true;
                i=password.length();
                //System.out.println("test 1 :" +test);
            }
        }
        if(password.length()>5)
        {
            test2=true;
            //System.out.println(test2);
        }
        if (test==test2)
            return test;
        return false;
    }
}
