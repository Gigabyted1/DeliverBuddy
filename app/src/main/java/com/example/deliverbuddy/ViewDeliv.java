package com.example.deliverbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class ViewDeliv extends AppCompatActivity {

    static final int NO_ACTION = 0;
    static final int EDIT = 1;
    static final int DELETE = 2;

    private Bundle mainExtras;
    private int action;
    private String tempName1;
    private String tempName2;
    private String tempAddress;
    private String tempPhone;
    private String tempTotal;

    private TextView name1;
    private TextView name2;
    private TextView address;
    private TextView phone;
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

        name1 = findViewById(R.id.view_name1);
        name2 = findViewById(R.id.view_name2);
        address = findViewById(R.id.view_address);
        phone = findViewById(R.id.view_phone);
        total = findViewById(R.id.view_total);
        tempName1 = mainExtras.getString("name1");
        tempName2 = mainExtras.getString("name2");
        tempAddress = mainExtras.getString("address");
        tempPhone = mainExtras.getString("phone");
        tempTotal = mainExtras.getString("total");

        //Setting up action with back button and title
        setSupportActionBar(findViewById(R.id.toolbar_edit));
        ActionBar back = getSupportActionBar();
        assert back != null;
        back.setDisplayHomeAsUpEnabled(true);
        setTitle("Delivery " + mainExtras.getString("no"));
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        String tempTotalFormat = "$" + tempTotal;
        name1.setText(tempName1);
        name2.setText(tempName2);
        address.setText(tempAddress);
        phone.setText(tempPhone);
        total.setText(tempTotalFormat);

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.view_action_delete:
                action = DELETE;

                return true;

            case R.id.view_action_edit:

                Intent editDeliv = new Intent(getApplicationContext(), EditDeliv.class);
                editDeliv.putExtra("name1", tempName1);
                editDeliv.putExtra("name2", tempName2);
                editDeliv.putExtra("address", tempAddress);
                editDeliv.putExtra("phone", tempPhone);
                editDeliv.putExtra("total", tempTotal);
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
                tempAddress = data.getStringExtra("address");
                tempPhone = data.getStringExtra("phone");
                tempTotal = data.getStringExtra("total");
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
        // TODO: Pass text fields back as extras if action = edit
        setResult(action, returnIntent);
        super.onBackPressed();
    }

}
