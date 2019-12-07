package com.example.deliverbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.Intent;

public class NewDeliv extends AppCompatActivity {

    private static final String TITLE_NEW = "New Delivery";

    private EditText name1;
    private EditText name2;
    private EditText address;
    private EditText phone;
    private EditText total;
    private Toolbar toolbarNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdeliv);

        name1 = findViewById(R.id.new_name1);
        name2 = findViewById(R.id.new_name2);
        address = findViewById(R.id.new_address);
        phone = findViewById(R.id.new_phone);
        total = findViewById(R.id.new_total);
        toolbarNew = findViewById(R.id.toolbar_new);

        //Action bar setup
        setSupportActionBar(toolbarNew);
        ActionBar back = getSupportActionBar();
        back.setDisplayHomeAsUpEnabled(true);
        setTitle(TITLE_NEW);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //More action bar setup
        getMenuInflater().inflate(R.menu.toolbar_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            //Sends data that is entered in the boxes to the main activity and returns to the main activity
            case R.id.new_action_done:
                if(name1.length() != 0 && name2.length() != 0 && address.length() != 0 && phone.length() != 0 && total.length() != 0)
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("name1", name1.getText().toString());
                    returnIntent.putExtra("name2", name2.getText().toString());
                    returnIntent.putExtra("address", address.getText().toString());
                    returnIntent.putExtra("phone", phone.getText().toString());
                    returnIntent.putExtra("total", total.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                else //Print an error if any fields are empty
                {
                    Toast incError = Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT);
                    incError.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}


