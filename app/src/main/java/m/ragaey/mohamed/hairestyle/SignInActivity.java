package m.ragaey.mohamed.hairestyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    EditText email_field, password_field;
    Button sign_in_btn2;

    String email, password;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_field = findViewById(R.id.email_field_signin);
        password_field = findViewById(R.id.password_field_signin);

        sign_in_btn2 = findViewById(R.id.sign_in_btn2);

        auth = FirebaseAuth.getInstance();

        progressBar =findViewById(R.id.progressbar);

        sign_in_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                email = email_field.getText().toString();
                password = password_field.getText().toString();

                if (email.length() ==0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Please Enter A Valid Data", Toast.LENGTH_SHORT).show();

                }else 
                    {
                        progressBar.setVisibility(View.VISIBLE);

                        SignIn(email,password);
                    }
            }
        });
    }

    public void SignIn (String email, String password)
    {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            user = auth.getCurrentUser();


                            if (user != null)
                            {
                                user.reload();

                                if (user.isEmailVerified())
                                {
                                    progressBar.setVisibility(View.INVISIBLE);

                                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                                    startActivity(intent);
                                } else
                                    {
                                        progressBar.setVisibility(View.INVISIBLE);

                                        Toast.makeText(getApplicationContext(), "Please Verify Your Emai", Toast.LENGTH_SHORT).show();
                                    }
                                    }
                            }else
                                {
                                    progressBar.setVisibility(View.INVISIBLE);


                                    Toast.makeText(getApplicationContext(), "Please Sign Up Firstly", Toast.LENGTH_SHORT).show();
                                }
                    }
                });
    }
}
