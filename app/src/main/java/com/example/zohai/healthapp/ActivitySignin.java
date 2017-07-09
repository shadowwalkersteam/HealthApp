package com.example.zohai.healthapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class ActivitySignin extends AppCompatActivity {


    ImageView signinback;
    TextView forgot;
    private static final String TAG = "ActivitySignin";
    private TextView btn_sign;
    private EditText InputEmail, InputPassword;
    private TextInputLayout signinInputLayoutEmail, signinInputLayoutPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        auth = FirebaseAuth.getInstance();
        signinback = (ImageView)findViewById(R.id.signinback);
        btn_sign = (TextView)findViewById(R.id.signin);
        signinInputLayoutEmail = (TextInputLayout) findViewById(R.id.signin_input_layout_email);
        signinInputLayoutPassword = (TextInputLayout) findViewById(R.id.signin_input_layout_passsword);
        forgot = (TextView)findViewById(R.id.forgt);
        InputEmail = (EditText) findViewById(R.id.username);
        InputPassword = (EditText) findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent it = new Intent(ActivitySignin.this,Dashboard2.class);
//                it.putExtra("Datasource",datasource.getText().toString());
//                startActivity(it);
                submitForm();
            }
        });
        signinback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivitySignin.this,MainActivity.class);
                startActivity(it);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySignin.this,ForgotPassword.class);
                startActivity(intent);
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
        signinInputLayoutEmail.setErrorEnabled(false);
        signinInputLayoutPassword.setErrorEnabled(false);

        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(ActivitySignin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(TAG,"signinUserWithEmail:onComplete:" + task.isSuccessful());
                progressDialog.dismiss();
                // If sign in fails, Log a message to the LogCat. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    // there was an error
                    Toast.makeText(ActivitySignin.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(ActivitySignin.this, Dashboard2.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
    private boolean checkEmail() {
        String email = InputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {
            signinInputLayoutEmail.setErrorEnabled(true);
            signinInputLayoutEmail.setError(getString(R.string.err_msg_email));
            InputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(InputEmail);
            return false;
        }
        signinInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword(){
        String password = InputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            signinInputLayoutPassword.setError(getString(R.string.err_msg_password));
            InputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(InputPassword);
            return false;
        }
        signinInputLayoutPassword.setErrorEnabled(false);
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
