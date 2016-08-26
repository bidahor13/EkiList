package com.ekilist.ekilist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Babatunde on 3/5/2016.
 */
public class display extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_display);
    }

    /* Button for the sign-up on login_display.xml */
        public void onClickButton2(View v) {
        if (v.getId() == R.id.Bsignup) {
            Intent i = new Intent(display.this, SignUp.class);
            startActivity(i);
           }
        }

   /*  public void buttonAddClick(View v) {
        String toDo = ((EditText) findViewById(R.id.editTextToDo)).getText().toString().trim();
        if(toDo.isEmpty()){
            return;

        }
    } */


    /* Button for the login on login_display.xml */
    public void onButtonClickLogin(View v) {
        if (v.getId() == R.id.logbutton)
        {
            /* for reading in the user name on the login_display.xml */
            EditText a = (EditText)findViewById(R.id.TFusername);
            String str = a.getText().toString();

            /* for reading in the password on the login_display.xml*/
            EditText b = (EditText)findViewById(R.id.TFpassword);
            String pass = b.getText().toString();


            String password = helper.searchPass(str);
            if (pass.equals(password))
            {
            /* Open up the new activity called new_list_activity*/
                Intent i = new Intent(display.this, NewListActivity.class);
                i.putExtra("Username", str);
                startActivity(i);
            }
            else
            {
                //popup msg
                Toast temp = Toast.makeText(display.this , "Username and Passwords don't match!" , Toast.LENGTH_SHORT);
                temp.show();
            }
        }

    }


}
