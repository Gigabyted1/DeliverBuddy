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
    private EditText address1;
    private EditText address2;
    private EditText city;
    private EditText zip;
    private EditText phone;
    private EditText subtotal;
    private EditText tip;
    private TextView total;
    private Toolbar toolbarNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdeliv);

        name1 = findViewById(R.id.new_name1);
        name2 = findViewById(R.id.new_name2);
        address1 = findViewById(R.id.new_address1);
        address2 = findViewById(R.id.new_address2);
        city = findViewById(R.id.new_city);
        zip = findViewById(R.id.new_zip);
        phone = findViewById(R.id.new_phone);
        subtotal = findViewById(R.id.new_subtotal);
        tip = findViewById(R.id.new_tip);
        total = findViewById(R.id.new_total);
        toolbarNew = findViewById(R.id.toolbar_new);

        //Action bar setup
        setSupportActionBar(toolbarNew);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                if(name1.length() != 0 && name2.length() != 0 && address1.length() != 0 && city.length() != 0 && phone.length() != 0 && subtotal.length() != 0)
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("name1", name1.getText().toString());
                    returnIntent.putExtra("name2", name2.getText().toString());
                    returnIntent.putExtra("address1", address1.getText().toString());
                    returnIntent.putExtra("address2", address2.getText().toString());
                    returnIntent.putExtra("city", city.getText().toString());
                    returnIntent.putExtra("zip", zip.getText().toString());
                    returnIntent.putExtra("phone", phone.getText().toString());
                    returnIntent.putExtra("subtotal", Double.parseDouble(subtotal.getText().toString()));
                    returnIntent.putExtra("tip", Double.parseDouble(tip.getText().toString()));
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


