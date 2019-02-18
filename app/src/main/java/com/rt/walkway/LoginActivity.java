package com.rt.walkway;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.walkway.dataBase.UserDBAdapter;

//TODO Hamburger menu implementation on login page
public class LoginActivity extends AppCompatActivity {
    private TextView mUsername;
    private TextView mPassword;
    private Button mLoginButton;
    private Button mRegisterButton;
    private ImageView mGhostAvatar;
    UserDBAdapter dbAdapter;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mUsername = findViewById(R.id.username_text_field);
        mPassword = findViewById(R.id.password_text_field);
        mGhostAvatar = findViewById(R.id.avatar_image);
        mGhostAvatar.setVisibility((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? View.GONE : View.VISIBLE);
        onClickRegister();


        dbAdapter = new UserDBAdapter(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    private void onClickRegister() {
        mRegisterButton = findViewById(R.id.login_register_button);
        mRegisterButton.setOnClickListener(v -> setContext(RegisterActivity.class));

    }


    private void setContext(Class classContext) {
        Context context = LoginActivity.this;
        Class destinationActivity = classContext;
        Intent startLoginActivityIntent = new Intent(context, destinationActivity);
        startActivity(startLoginActivityIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        boolean loggedIn = MainActivity.loggedIn;
        if (loggedIn == true) {
            setContext(MainActivity.class);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void login(View view) {
        if (!getCredentials(mUsername.getText().toString(), mPassword.getText().toString()).isEmpty()) {
            MainActivity.loggedIn = true;
            ProfileActivity.USER = mUsername.getText().toString();
            setContext(MainActivity.class);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.wrong_credentials_toast, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String getCredentials(String username, String password) {
        String data = dbAdapter.getCredentials(username, password);
        Log.i("Email", "email: " + data);
        return data.toUpperCase();
    }
}
