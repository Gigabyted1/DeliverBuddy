package com.example.deliverbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import android.os.Bundle;

import java.util.Locale;

//Code for editing a data entry
public class EditDeliv extends AppCompatActivity
{
    private static final String TITLE_EDIT = "Edit Delivery";

    private TextView name1;
    private TextView name2;
    private TextView address;
    private TextView phone;
    private TextView total;
    private Toolbar toolbarEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deliv);
        Bundle editExtras = getIntent().getExtras();
        assert editExtras != null;

        name1 = findViewById(R.id.edit_name1);
        name2 = findViewById(R.id.edit_name2);
        address = findViewById(R.id.edit_address1);
        phone = findViewById(R.id.edit_phone);
        total = findViewById(R.id.edit_total);
        toolbarEdit = findViewById(R.id.toolbar_edit);

        //Action bar setup
        setSupportActionBar(toolbarEdit);
        setTitle(TITLE_EDIT);

        //Loading the data from the entry that is passed from the main activity
        name1.setText(editExtras.getString("name1"));
        name2.setText(editExtras.getString("name2"));
        address.setText(editExtras.getString("address"));
        phone.setText(editExtras.getString("phone"));
        total.setText(editExtras.getString("total"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //More action bar setup
        getMenuInflater().inflate(R.menu.toolbar_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch(item.getItemId())
        {
            //Returns to the main screen, passing the edited data
            case R.id.edit_action_done:
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
                else //If any fields are empty, push an error
                {
                    Toast incError = Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT);
                    incError.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        Toast noSave = Toast.makeText(getApplicationContext(), "Exited without saving.", Toast.LENGTH_SHORT);
        noSave.show();

        setResult(Activity.RESULT_CANCELED);
    }
}
