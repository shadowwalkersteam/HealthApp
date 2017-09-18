package com.example.zohai.healthapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivitySignup extends AppCompatActivity {

    private static final String TAG = "ActivitySignup" ;
    ImageView signupback;
    private EditText InputEmail, InputPassword;
    private TextInputLayout signupInputLayoutEmail, signupInputLayoutPassword;
    private ProgressDialog progressDialog;
    private TextView btn_signup;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        auth = FirebaseAuth.getInstance();
        signupInputLayoutEmail = (TextInputLayout) findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = (TextInputLayout) findViewById(R.id.signup_input_layout_password);
        InputEmail = (EditText) findViewById(R.id.email);
        InputPassword = (EditText) findViewById(R.id.password);
        btn_signup = (TextView) findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);
        signupback = (ImageView)findViewById(R.id.signupback);

        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivitySignup.this, MainActivity.class);
                startActivity(it);

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        String email = InputEmail.getText().toString().trim();
        String password = InputPassword.getText().toString().trim();
        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //createuser with firebase
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(ActivitySignup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());
                progressDialog.dismiss();

                // If sign in fails, Log the message to the LogCat. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(ActivitySignup.this, DatasourceID.class));
                    finish();
                }
            }
        });
        Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
    }

    //check for email
    private boolean checkEmail() {
        String email = InputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            signupInputLayoutEmail.setErrorEnabled(true);
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            InputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(InputEmail);
            return false;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    //check for password
    private boolean checkPassword(){
        String password = InputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            signupInputLayoutPassword.setError(getString(R.string.err_msg_password));
            InputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(InputPassword);
            return false;
        }
        signupInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
