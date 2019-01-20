package com.rt.walkway;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rt.walkway.dataBase.UserDBAdapter;

public class RegisterActivity extends AppCompatActivity {
    EditText mFirstName, mLastName, mEmail, mUsername, mPassword, mConfirmPassword;
    TextInputLayout mFirstNameView, mLastNameView, mEmailView, mUsernameView, mPasswordView, mConfirmPasswordView;
    Button mRegister;
    UserDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mFirstName = findViewById(R.id.register_first_name_edit_text);
        mFirstNameView = findViewById(R.id.register_first_name_text_input_layout);
        mLastName = findViewById(R.id.register_last_name_edit_text);
        mLastNameView = findViewById(R.id.register_last_name_text_input_layout);
        mEmail = findViewById(R.id.register_email_edit_text);
        mEmailView = findViewById(R.id.register_email_text_input_layout);
        mUsername = findViewById(R.id.register_username_edit_text);
        mUsernameView = findViewById(R.id.register_username_text_input_layout);
        mPassword = findViewById(R.id.register_password_edit_text);
        mPasswordView = findViewById(R.id.register_password_text_input_layout);
        mConfirmPassword = findViewById(R.id.register_confirm_password_edit_text);
        mConfirmPasswordView = findViewById(R.id.register_confirm_password_text_input_layout);
        mRegister = findViewById(R.id.register_confirm);

        dbAdapter = new UserDBAdapter(this);

    }

    public void addUser(View view) {
        int passwordMatch = 0;
        if (mFirstName.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mFirstNameView, getString(R.string.empty_error));
        } else hideError(mFirstNameView);
        if (mLastName.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mLastNameView, getString(R.string.empty_error));
        } else hideError(mLastNameView);
        if (mEmail.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mEmailView, getString(R.string.empty_error));
        } else hideError(mEmailView);
        if (mUsername.getText().toString().isEmpty()) {
            passwordMatch =  this.showError(mUsernameView, getString(R.string.empty_error));
        } else hideError(mUsernameView);
        if (mPassword.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mPasswordView, getString(R.string.empty_error));
        } else hideError(mPasswordView);
        if (mConfirmPassword.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mConfirmPasswordView, getString(R.string.empty_error));
        } else hideError(mConfirmPasswordView);
        if ( !mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.incorrect_password_match_toast, Toast.LENGTH_SHORT);
            toast.show();
            this.showError(mConfirmPasswordView, getString(R.string.empty_error));
            this.showError(mPasswordView, getString(R.string.empty_error));
        } else  if (passwordMatch ==  0){
            Log.i("here", "before insertData");
            //long id = dbAdapter.insertData(mFirstName.getText().toString(), mLastName.getText().toString(), mEmail.getText().toString(), mUsername.getText().toString(), mPassword.getText().toString());
            long id = 1;
            Log.i("here", "addUser: " + id);
            if (id <= 0) {
                Log.i("here", "addUser: Insertion unsuccessful");
            } else {
                Log.i("here", "addUser: insert complete");
                viewData(view);
                Context context = RegisterActivity.this;
                Class destinationActivity = LoginActivity.class;
                Intent startLoginActivityIntent = new Intent(context, destinationActivity);
                startActivity(startLoginActivityIntent);
            }
        }
    }

    private int showError(TextInputLayout textInputLayout, String error) {
        TextInputLayout inputLayout = textInputLayout;
        inputLayout.setError(error); // show error
        return 1;
    }

    private void hideError(TextInputLayout textInputLayout) {
        TextInputLayout inputLayout = textInputLayout;
        inputLayout.setError(null);
    }

    public void viewData(View view) {
        String data = dbAdapter.getData();
        Log.i("data", "viewData: " + data);
    }


    /*
     mGhostAvatar = findViewById(R.id.avatar_image);
        mGhostAvatar.setVisibility((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? View.GONE : View.VISIBLE);

     */

}
