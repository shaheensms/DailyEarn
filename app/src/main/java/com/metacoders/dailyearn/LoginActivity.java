package com.metacoders.dailyearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    TextInputEditText passin , gmailin ;
    String pass, mail;
ProgressBar pbar ;
TextView signUp ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            getSupportActionBar().hide();



        loginBtn = (Button) findViewById(R.id.login_btn);
        passin = findViewById(R.id.passwordin) ;
        gmailin = findViewById(R.id.emailInput) ;
        pbar = findViewById(R.id.progreebar) ;
        signUp  = findViewById(R.id.signUp) ;

        pbar.setVisibility(View.GONE);




        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mail = gmailin.getText().toString();
                pass = passin.getText().toString() ;



                if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass))
                {
                    pbar.setVisibility(View.VISIBLE);
                    goforSignin(mail , pass) ;

                }




            }
        });
    }

    private void goforSignin(String mail, String pass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();




        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d(TAG, "signInWithEmail:success");
                            pbar.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginActivity.this, FundTransferActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            pbar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                          //  Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(getApplicationContext(), "Error : "+ task.getException().getMessage()  , Toast.LENGTH_SHORT)
                            .show();
                        }

                        // ...
                    }
                });






    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}
