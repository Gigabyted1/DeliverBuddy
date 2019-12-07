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

public class ViewDeliv extends AppCompatActivity {

    static final int NO_ACTION = 0;
    static final int EDIT = 1;
    static final int DELETE = 2;

    Bundle mainExtras; //Storing parameters in a bundle

    public int action;
    private String tempName1;
    private String tempName2;
    private String tempAddress;
    private String tempPhone;
    private String tempTotal;

    private Toolbar toolbarView;
    private TextView name;
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

        action = NO_ACTION;

        toolbarView = findViewById(R.id.toolbar_view);
        name = findViewById(R.id.view_name);
        address = findViewById(R.id.view_address1);
        phone = findViewById(R.id.view_phone);
        total = findViewById(R.id.view_total);
        tempName1 = mainExtras.getString("name1");
        tempName2 = mainExtras.getString("name2");
        tempAddress = mainExtras.getString("address");
        tempPhone = mainExtras.getString("phone");
        tempTotal = mainExtras.getString("total");

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

        String tempTotalFormat = "$" + tempTotal;
        String tempNameFormat = tempName1 + " " + tempName2;
        name.setText(tempNameFormat);
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
                setResult(Activity.RESULT_OK);
                finish();

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
        returnIntent.putExtra("position", mainExtras.getInt("position"));

        if(action == EDIT)
        {
            returnIntent.putExtra("name1", tempName1);
            returnIntent.putExtra("name2", tempName2);
            returnIntent.putExtra("address", tempAddress);
            returnIntent.putExtra("phone", tempPhone);
            returnIntent.putExtra("total", tempTotal);
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
