package com.rt.walkway;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.rt.walkway.dataBase.UserDBAdapter;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String IMAGE_ID = "id";

    public static String USER;
    private TextView mUsernameTextField;

    private ConstraintLayout mLayout;

    UserDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mLayout = findViewById(R.id.profile_layout);
        setBackground();

        dbAdapter = new UserDBAdapter(this);


        mUsernameTextField = findViewById(R.id.username_profile);
        mUsernameTextField.setText(getUserName(USER));
    }

    public void openMenu(View view) {
        PopupMenu popup = new PopupMenu(ProfileActivity.this, view);
        popup.setOnMenuItemClickListener(ProfileActivity.this);
        popup.inflate(R.menu.main);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.i("tag", "yes we reach ths spot");

        switch (item.getItemId()) {
            case R.id.change_image_item:
                setContext(PreferenceActivity.class);
                return true;
            case R.id.logout_item:

                MainActivity.loggedIn = false;
                setContext(MainActivity.class);
                return true;
            default:
                return false;
        }
    }

    private void setContext(Class classContext) {
        Context context = ProfileActivity.this;
        Class destinationActivity = classContext;
        Intent startLoginActivityIntent = new Intent(context, destinationActivity);
        startActivity(startLoginActivityIntent);
    }

    public String getUserName(String userName) {
        String data = dbAdapter.getUserName(userName);
        Log.i("Email", "email: " + data);
        return data.toUpperCase();
    }

    public void goToPaths(View view) {
        setContext(ItemListActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackground();
        boolean loggedIn = MainActivity.loggedIn;
        if (loggedIn == false) {
            setContext(MainActivity.class);

        }
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
}
