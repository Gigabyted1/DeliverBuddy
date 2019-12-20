package com.example.deliverbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import android.os.Bundle;

import java.util.Locale;

//Code for editing a data entry
public class EditDeliv extends AppCompatActivity
{
    private static final String TITLE_EDIT = "Edit Delivery";

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
    private Toolbar toolbarEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deliv);
        Bundle editExtras = getIntent().getExtras();
        assert editExtras != null;

        name1 = findViewById(R.id.edit_name1);
        name2 = findViewById(R.id.edit_name2);
        address1 = findViewById(R.id.edit_address1);
        address2 = findViewById(R.id.edit_address2);
        city = findViewById(R.id.edit_city);
        zip = findViewById(R.id.edit_zip);
        phone = findViewById(R.id.edit_phone);
        subtotal = findViewById(R.id.edit_subtotal);
        tip = findViewById(R.id.edit_tip);
        total = findViewById(R.id.edit_total);
        toolbarEdit = findViewById(R.id.toolbar_edit);

        //Action bar setup
        setSupportActionBar(toolbarEdit);
        setTitle(TITLE_EDIT);

        //Loading the data from the entry that is passed from the parent activity
        name1.setText(editExtras.getString("name1"));
        name2.setText(editExtras.getString("name2"));
        address1.setText(editExtras.getString("address1"));
        address2.setText(editExtras.getString("address2"));
        city.setText(editExtras.getString("city"));
        zip.setText(editExtras.getString("zip"));
        phone.setText(editExtras.getString("phone"));

        String tempSubtotal = String.format(Locale.ENGLISH,"%1$,.2f", editExtras.getDouble("subtotal"));
        String tempTip = String.format(Locale.ENGLISH,"%1$,.2f", editExtras.getDouble("tip"));
        String tempTot = String.format(Locale.ENGLISH, "%1$,.2f", editExtras.getDouble("subtotal")+ editExtras.getDouble("tip"));
        subtotal.setText(tempSubtotal);
        tip.setText(tempTip);
        total.setText(tempTot);

        subtotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Nothing
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                double subVal = 0;
                double tipVal = 0;

                if(subtotal.length() != 0)
                {
                    subVal = Double.parseDouble(subtotal.getText().toString());
                }
                if(tip.length() != 0)
                {
                    tipVal = Double.parseDouble(tip.getText().toString());
                }

                String newTot = String.format(Locale.ENGLISH, "%1$,.2f", subVal + tipVal);

                total.setText(newTot);
            }
        });

        tip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Nothing
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                double subVal = 0;
                double tipVal = 0;

                if(subtotal.length() != 0)
                {
                    subVal = Double.parseDouble(subtotal.getText().toString());
                }
                if(tip.length() != 0)
                {
                    tipVal = Double.parseDouble(tip.getText().toString());
                }

                String newTot = String.format(Locale.ENGLISH, "%1$,.2f", subVal + tipVal);

                total.setText(newTot);
            }
        });

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
                else //If any fields are empty, push an error
                {
                    Toast incError = Toast.makeText(getApplicationContext(), "Please fill out required fields.", Toast.LENGTH_SHORT);
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
