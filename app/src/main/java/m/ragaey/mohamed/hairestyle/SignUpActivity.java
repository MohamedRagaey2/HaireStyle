package m.ragaey.mohamed.hairestyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText email_field, password_field;
    Button sign_up2;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email_field = findViewById(R.id.email_field_signup);
        password_field = findViewById(R.id.password_field_signup);
        sign_up2 = findViewById(R.id.sign_up_btn2);

        auth = FirebaseAuth.getInstance();

        sign_up2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = email_field.getText().toString();
                password = password_field.getText().toString();

                if (email.length()==0 || password.length() ==0)
                {
                    Toast.makeText(getApplicationContext(), "Please Enter A Valid Data", Toast.LENGTH_SHORT).show();
                }else
                {
                    SignUp(email, password);
                }
            }
        });

    }

    public void SignUp(String email,String password)
    {
        Task<AuthResult> task = auth.createUserWithEmailAndPassword(email, password);

        task.addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseUser user =auth.getCurrentUser();

                    if (user !=null)
                    {
                        user.isEmailVerified();

                        Intent intent = new Intent(getApplicationContext(), EmailVerActivity.class);
                        startActivity(intent);
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "this email has been signed up", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
