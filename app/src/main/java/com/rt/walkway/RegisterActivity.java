package com.rt.walkway;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.rt.walkway.dataBase.UserDBAdapter;

//TODO implement pop-up on success
public class RegisterActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String IMAGE_ID = "id";

    EditText mFirstName, mLastName, mEmail, mUsername, mPassword, mConfirmPassword;
    TextInputLayout mFirstNameView, mLastNameView, mEmailView, mUsernameView, mPasswordView, mConfirmPasswordView;
    Button mRegister;
    UserDBAdapter dbAdapter;

    private ScrollView mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mLayout = findViewById(R.id.register_scroll);

        setBackground();
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

    private Toast toast;

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
        } else if (!getEmail(mEmail.getText().toString().toUpperCase()).isEmpty()) {
            Log.i("sut", "addUser: ");
            toast = Toast.makeText(getApplicationContext(), R.string.email_exists_toast, Toast.LENGTH_SHORT);
            toast.show();
            passwordMatch = this.showError(mEmailView, getString(R.string.empty_error));
        } else hideError(mEmailView);
        if (mUsername.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mUsernameView, getString(R.string.empty_error));
        } else if (!getUser(mUsername.getText().toString().toUpperCase()).isEmpty()) {
            if (!mUsername.getText().toString().isEmpty()) {
                toast = Toast.makeText(getApplicationContext(), R.string.username_exists_toast, Toast.LENGTH_SHORT);
                toast.show();
            }
            passwordMatch = this.showError(mUsernameView, getString(R.string.empty_error));
        } else hideError(mUsernameView);
        if (mPassword.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mPasswordView, getString(R.string.empty_error));
        } else hideError(mPasswordView);
        if (mConfirmPassword.getText().toString().isEmpty()) {
            passwordMatch = this.showError(mConfirmPasswordView, getString(R.string.empty_error));
        } else hideError(mConfirmPasswordView);
        if (!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            toast = Toast.makeText(getApplicationContext(), R.string.incorrect_password_match_toast, Toast.LENGTH_SHORT);
            toast.show();
            this.showError(mConfirmPasswordView, getString(R.string.empty_error));
            this.showError(mPasswordView, getString(R.string.empty_error));
        } else if (passwordMatch == 0) {
            Log.i("here", "before insertData");
            long id = dbAdapter.insertData(mFirstName.getText().toString().toUpperCase(), mLastName.getText().toString().toUpperCase(), mEmail.getText().toString().toUpperCase(), mUsername.getText().toString().toUpperCase(), mPassword.getText().toString());
            //long id = 1;
            Log.i("here", "addUser: " + id);
            if (id <= 0) {
                Log.i("here", "addUser: Insertion unsuccessful");
            } else {
                Log.i("here", "addUser: insert complete");
                viewData(view);
                addNotification();
                Context context = RegisterActivity.this;
                Class destinationActivity = LoginActivity.class;
                Intent startLoginActivityIntent = new Intent(context, destinationActivity);
                startActivity(startLoginActivityIntent);
            }
        }
    }

    private void addNotification() {
        Log.i("nofitificatinon", "test");
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Registration success! You can now login.");
        bigText.setBigContentTitle("Welcome " + getUserName(mUsername.getText().toString()));
        bigText.setSummaryText("Something new is going on!");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.tree);
        mBuilder.setAutoCancel(true);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.tree));
        mBuilder.setContentTitle("Registration success!");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
        Log.i("nofitificatinon", "test");
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

    public String getUser(String username) {
        String data = dbAdapter.getUsername(username);
        Log.i("Username", "username: " + data);
        return data.toUpperCase();
    }

    public String getEmail(String email) {
        String data = dbAdapter.getEmail(email);
        Log.i("Email", "email: " + data);
        return data.toUpperCase();
    }

    public String getUserName(String userName) {
        String data = dbAdapter.getUserName(userName);
        Log.i("Email", "email: " + data);
        return data.toUpperCase();
    }

    private void setBackground() {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(IMAGE_ID)) {
            Log.i("print", sharedpreferences.getString(IMAGE_ID, ""));
            switch (sharedpreferences.getString(IMAGE_ID, "")) {
                case "1":
                    mLayout.setBackgroundResource(R.drawable.image);
                    break;
                case "2":
                    mLayout.setBackgroundResource(R.drawable.image2);
                    break;
                case "3":
                    mLayout.setBackgroundResource(R.drawable.image3);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackground();
    }
}
