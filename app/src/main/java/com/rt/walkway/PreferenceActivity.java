package com.rt.walkway;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

//TODO implement pop-up on success
public class PreferenceActivity extends AppCompatActivity {
    private CheckBox checkBox_1, checkBox_2, checkBox_3;
    private LinearLayout linearLayout;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String IMAGE_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        linearLayout = findViewById(R.id.preference_activity_layout);
        checkBox_1 = findViewById(R.id.checkbox_1);
        checkBox_2 = findViewById(R.id.checkbox_2);
        checkBox_3 = findViewById(R.id.checkbox_3);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(IMAGE_ID)) {
            //name.setText(sharedpreferences.getString(Name, ""));
            Log.i("print", sharedpreferences.getString(IMAGE_ID, ""));
            switch (sharedpreferences.getString(IMAGE_ID, "")) {
                case "1":
                    linearLayout.setBackgroundResource(R.drawable.image);
                    checkBox_1.setChecked(true);
                    break;
                case "2":
                    linearLayout.setBackgroundResource(R.drawable.image2);
                    checkBox_2.setChecked(true);
                    break;
                case "3":
                    linearLayout.setBackgroundResource(R.drawable.image3);
                    checkBox_3.setChecked(true);
                    break;
            }
        }

    }

    private String ID;


    public void setBackground1(View view) {
        checkBox_2.setChecked(false);
        checkBox_3.setChecked(false);
        linearLayout.setBackgroundResource(R.drawable.image);

        ID = "1";
    }

    public void setBackground2(View view) {
        checkBox_1.setChecked(false);
        checkBox_3.setChecked(false);
        linearLayout.setBackgroundResource(R.drawable.image2);

        ID = "2";
    }

    public void setBackground3(View view) {
        checkBox_2.setChecked(false);
        checkBox_1.setChecked(false);
        linearLayout.setBackgroundResource(R.drawable.image3);

        ID = "3";
    }


    public void save(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(IMAGE_ID, ID);
        editor.commit();
        setContext(ProfileActivity.class);
    }

    public String read(String valueKey, String valueDefault) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        return prefs.getString(valueKey, valueDefault);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean loggedIn = MainActivity.loggedIn;
        if (loggedIn == false) {
            setContext(MainActivity.class);

        }
    }


    private void setContext(Class classString) {
        Context context = PreferenceActivity.this;
        Class destinationActivity = classString;
        Intent startLoginActivityIntent = new Intent(context, destinationActivity);
        startActivity(startLoginActivityIntent);
    }
}
