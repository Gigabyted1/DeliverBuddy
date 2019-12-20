package com.example.deliverbuddy;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Locale;

public class ViewDeliv extends AppCompatActivity {

    static final int NO_ACTION = 0;
    static final int EDIT = 1;
    static final int DELETE = 2;

    Bundle mainExtras; //Storing parameters in a bundle

    public int action;
    private String tempName1;
    private String tempName2;
    private String tempAddress1;
    private String tempAddress2;
    private String tempCity;
    private String tempZip;
    private String tempPhone;
    private double tempSubtotal;
    private double tempTip;
    private double tempTotal;

    private Toolbar toolbarView;
    private TextView name;
    private TextView address1;
    private TextView address2;
    private TextView city;
    private TextView zip;
    private TextView phone;
    private TextView subtotal;
    private TextView tip;
    private TextView total;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //More action bar setup
        getMenuInflater().inflate(R.menu.toolbar_view, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deliv);
        mainExtras = getIntent().getExtras(); //Storing parameters in a bundle
        assert mainExtras != null;

        action = NO_ACTION;

        toolbarView = findViewById(R.id.toolbar_view);
        name = findViewById(R.id.view_name);
        address1 = findViewById(R.id.view_address1);
        address2 = findViewById(R.id.view_address2);
        city = findViewById(R.id.view_city);
        zip = findViewById(R.id.view_zip);
        phone = findViewById(R.id.view_phone);
        subtotal = findViewById(R.id.view_subtotal);
        tip = findViewById(R.id.view_tip);
        total = findViewById(R.id.view_total);

        tempName1 = mainExtras.getString("name1");
        tempName2 = mainExtras.getString("name2");
        tempAddress1 = mainExtras.getString("address1");
        tempAddress2 = mainExtras.getString("address2");
        tempCity = mainExtras.getString("city");
        tempZip = mainExtras.getString("zip");
        tempPhone = mainExtras.getString("phone");
        tempSubtotal = mainExtras.getDouble("subtotal");
        tempTip = mainExtras.getDouble("tip");

        //Setting up action with back button and title
        setSupportActionBar(toolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Delivery " + mainExtras.getString("no"));

        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        String tempNameFormat = tempName1 + " " + tempName2;
        tempTotal = tempSubtotal + tempTip;
        String tempTotalFormat = "$" + String.format(Locale.ENGLISH,"%1$,.2f", tempTotal);
        String tempSubtotalFormat = "$" + String.format(Locale.ENGLISH,"%1$,.2f", tempSubtotal);
        String tempTipFormat = "$" + String.format(Locale.ENGLISH,"%1$,.2f", tempTip);

        name.setText(tempNameFormat);
        address1.setText(tempAddress1);
        address2.setText(tempAddress2);
        city.setText(tempCity);
        zip.setText(tempZip);
        phone.setText(tempPhone);
        subtotal.setText(tempSubtotalFormat);
        tip.setText(tempTipFormat);
        total.setText(tempTotalFormat);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.view_action_delete:

                action = DELETE;
                setResult(Activity.RESULT_OK);
                finish();

                return true;

            case R.id.view_action_edit:

                Intent editDeliv = new Intent(getApplicationContext(), EditDeliv.class);
                editDeliv.putExtra("name1", tempName1);
                editDeliv.putExtra("name2", tempName2);
                editDeliv.putExtra("address1", tempAddress1);
                editDeliv.putExtra("address2", tempAddress2);
                editDeliv.putExtra("city", tempCity);
                editDeliv.putExtra("zip", tempZip);
                editDeliv.putExtra("phone", tempPhone);
                editDeliv.putExtra("subtotal", tempSubtotal);
                editDeliv.putExtra("tip", tempTip);
                startActivityForResult(editDeliv, 1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                action = EDIT;
                tempName1 = data.getStringExtra("name1");
                tempName2 = data.getStringExtra("name2");
                tempAddress1 = data.getStringExtra("address1");
                tempAddress2 = data.getStringExtra("address2");
                tempCity = data.getStringExtra("city");
                tempZip = data.getStringExtra("zip");
                tempPhone = data.getStringExtra("phone");
                tempSubtotal = data.getDoubleExtra("subtotal", 0);
                tempTip = data.getDoubleExtra("tip", 0);
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                action = NO_ACTION;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();

        if(action == EDIT)
        {
            returnIntent.putExtra("name1", tempName1);
            returnIntent.putExtra("name2", tempName2);
            returnIntent.putExtra("address1", tempAddress1);
            returnIntent.putExtra("address2", tempAddress2);
            returnIntent.putExtra("city", tempCity);
            returnIntent.putExtra("zip", tempZip);
            returnIntent.putExtra("phone", tempPhone);
            returnIntent.putExtra("subtotal", tempSubtotal);
            returnIntent.putExtra("tip", tempTip);
            setResult(EDIT, returnIntent);
        }
        else if(action == DELETE)
        {
            setResult(DELETE, returnIntent);
        }
        else if(action == NO_ACTION)
        {
            setResult(NO_ACTION);
        }

        super.onBackPressed();
    }

}
