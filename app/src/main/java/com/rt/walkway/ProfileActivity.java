package com.rt.walkway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
                // do your code
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

}
