package com.ekilist.ekilist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EkiList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eki_list);
    }


    /* Tunde added this code below for button activity*/
    public void onClickButton(View v) {
        if (v.getId() == R.id.createBtn) {
            Intent i = new Intent(EkiList.this, display.class);
            startActivity(i);
        } else if (v.getId() == R.id.shareBtn) {
            Intent createFileActivity = new Intent(EkiList.this, CreateFileActivity.class);
            startActivity(createFileActivity);
        }

    }


    /* Tunde added this code above for  button activity*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eki_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

