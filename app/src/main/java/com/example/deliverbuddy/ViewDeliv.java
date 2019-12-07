package com.example.deliverbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.*;

public class ViewDeliv extends AppCompatActivity {

    private Bundle delivExtras;

    private TextView name1;
    private TextView name2;
    private TextView address;
    private TextView phone;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deliv);
        delivExtras = getIntent().getExtras(); //Storing parameters in a bundle
        assert delivExtras != null;

        name1 = findViewById(R.id.view_name1);
        name2 = findViewById(R.id.view_name2);
        address = findViewById(R.id.view_address);
        phone = findViewById(R.id.view_phone);
        total = findViewById(R.id.view_total);

        //Setting up action with back button and title
        setSupportActionBar(findViewById(R.id.toolbar_edit));
        ActionBar back = getSupportActionBar();
        assert back != null;
        back.setDisplayHomeAsUpEnabled(true);
        setTitle("Delivery " + delivExtras.getString("no"));
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        name1.setText(delivExtras.getString("name1"));
        name2.setText(delivExtras.getString("name2"));
        address.setText(delivExtras.getString("address"));
        phone.setText(delivExtras.getString("phone"));
        total.setText(delivExtras.getString("total"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //More action bar setup
        getMenuInflater().inflate(R.menu.toolbar_view, menu);
        return true;
    }
}
