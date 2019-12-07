package com.example.deliverbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class MainScreen extends AppCompatActivity
{
    private static final String FILE_NAME = "delivIndex.txt";
    private static final String TITLE_MAIN = "Deliveries";

    private Delivery[] deliv = new Delivery[0];
    private int delivSel;
    private File file;
    private Toast noSel;

    private MainListAdapter adapter;
    private ListView mainList;
    private Toolbar toolbarMain;
    private TextView emptyMsg;
    private TextView extendName1;
    private TextView extendName2;
    private TextView extendAddress;
    private TextView extendPhone;
    private TextView extendTotal;
    private TextView extendNo;
    private View bg;
    private View divider;
    private View warnBg;
    private ImageView warnSym;
    private TextView warnMsg;
    private Button warnYes;
    private Button warnNo;

    //Writes all data to file
    public void save() {
        FileWriter writer = null;
        StringBuilder delivs = new StringBuilder();

        //Clears the file before writing
        file.delete();

        //Appends each entry to a StringBuilder
        for(Delivery i : deliv)
        {
            delivs.append(i.getNo().toString()).append("\n");
            delivs.append(i.getName1().toString()).append("\n");
            delivs.append(i.getName2().toString()).append("\n");
            delivs.append(i.getAddress().toString()).append("\n");
            delivs.append(i.getPhone().toString()).append("\n");
            delivs.append(i.getTotal()).append("\n");
        }

        //Writes the StringBuilder to file
        try {
            writer = new FileWriter(file);
            writer.write(delivs.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Loads data from file
    public void load() {
        FileReader reader = null;

        try {
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;

            //Loads data into any existing array locations before creating any new ones
            for (Delivery i : deliv)
            {
                if ((line = br.readLine()) != null)
                {
                    i.setNo(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setName1(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setName2(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setAddress(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setPhone(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setTotal(Double.parseDouble(line));
                }
            }

            //Loads remaining data into new array locations
            while((line = br.readLine()) != null)
            {
                deliv = newDelivery(deliv);

                deliv[deliv.length-1].setNo(line);

                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setName1(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setName2(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setAddress(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setPhone(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setTotal(Double.parseDouble(line));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Creates a new location at the end of the object array
    public static Delivery[] newDelivery(Delivery[] deliv)
    {
        Delivery[] temp = new Delivery[deliv.length + 1];
        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = new Delivery();

            if(temp.length == 1)
            {
                temp[0].setNo(1);
            }

            else if(i == temp.length - 1)
            {
                temp[i].setNo(Integer.parseInt(deliv[deliv.length-1].getNo().toString()) + 1);
            }
        }

        System.arraycopy(deliv, 0, temp, 0, deliv.length);

        return temp;
    }

    //Deletes an entry and fills in the gap left by the deleted entry
    public static Delivery[] delDelivery(Delivery[] deliv, int position)
    {
        Delivery[] temp = new Delivery[deliv.length - 1];

        System.arraycopy(deliv, 0, temp, 0, position);
        System.arraycopy(deliv, position + 1, temp, position, deliv.length - position - 1);

        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        delivSel = -1;
        file = new File(getFilesDir(), FILE_NAME);
        noSel = Toast.makeText(getApplicationContext(), "No delivery selected", Toast.LENGTH_SHORT); //Error message for when no entry is selected

        //Variable locations to access elements on the screen
        extendName1 = findViewById(R.id.extend_name1);
        extendName2 = findViewById(R.id.extend_name2);
        extendAddress = findViewById(R.id.extend_address);
        extendPhone = findViewById(R.id.extend_phone);
        extendTotal = findViewById(R.id.extend_total);
        extendNo = findViewById(R.id.extend_no);
        mainList = findViewById(R.id.main_list);
        toolbarMain = findViewById(R.id.toolbar_main);
        emptyMsg = findViewById(R.id.empty_msg);
        bg = findViewById(R.id.bg);
        divider = findViewById(R.id.divider);
        warnBg = findViewById(R.id.warn_bg);
        warnSym = findViewById(R.id.warn_sym);
        warnMsg = findViewById(R.id.warn_msg);
        warnYes = findViewById(R.id.warn_yes);
        warnNo = findViewById(R.id.warn_no);

        //Clears the current selection when the bottom of the screen is tapped
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainList.setChoiceMode(ListView.CHOICE_MODE_NONE);
                mainList.setAdapter(adapter);
                divider.setVisibility(View.INVISIBLE);
                extendName1.setText("");
                extendName2.setText("");
                extendAddress.setText("");
                extendPhone.setText("");
                extendTotal.setText("");
                extendNo.setText("");
            }
        });

        //Yes option on the delete all warning screen
        //Clears all data from array
        warnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliv = new Delivery[0];
                warnBg.setVisibility(View.INVISIBLE);
                warnSym.setVisibility(View.INVISIBLE);
                warnMsg.setVisibility(View.INVISIBLE);
                warnYes.setVisibility(View.INVISIBLE);
                warnNo.setVisibility(View.INVISIBLE);
                save();
                recreate();
            }
        });

        //No option on the delete all warning screen
        //Sets elements of the warning screen back to invisible
        warnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warnBg.setVisibility(View.INVISIBLE);
                warnSym.setVisibility(View.INVISIBLE);
                warnMsg.setVisibility(View.INVISIBLE);
                warnYes.setVisibility(View.INVISIBLE);
                warnNo.setVisibility(View.INVISIBLE);
            }
        });

        //Action bar setup
        setSupportActionBar(toolbarMain);
        setTitle(TITLE_MAIN);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Load call
        this.load();

        if(deliv.length != 0) //Displays a message if no entries are present
        {
            emptyMsg.setVisibility(View.INVISIBLE);
        }
        else
        {
            emptyMsg.setVisibility(View.VISIBLE);
        }

        //Uses MainListAdapter.class to populate the ListView with data
        adapter = new MainListAdapter(deliv, getApplicationContext());
        mainList.setAdapter(adapter);

        //When an item is clicked, display detailed data for that entry in the bottom panel
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String temp = String.format(Locale.ENGLISH,"%1$,.2f", deliv[position].getTotal());

                //delivSel = position;

                Intent viewDeliv = new Intent(getApplicationContext(), ViewDeliv.class);
                viewDeliv.putExtra("name1", deliv[position].getName1().toString());
                viewDeliv.putExtra("name2", deliv[position].getName2().toString());
                viewDeliv.putExtra("address", deliv[position].getAddress().toString());
                viewDeliv.putExtra("phone", deliv[position].getPhone().toString());
                viewDeliv.putExtra("no", deliv[position].getNo().toString());
                viewDeliv.putExtra("total", temp);
                viewDeliv.putExtra("position", position);
                startActivityForResult(viewDeliv, 2);
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        //Clears bottom panel
        mainList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        mainList.setAdapter(adapter);
        divider.setVisibility(View.INVISIBLE);
        extendName1.setText("");
        extendName2.setText("");
        extendAddress.setText("");
        extendPhone.setText("");
        extendTotal.setText("");
        extendNo.setText("");

        //Save call
        this.save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Makes a new array location and loads it with data passed back from NewDeliv
        if (requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                deliv = newDelivery(deliv);

                deliv[deliv.length - 1].setName1(data.getStringExtra("name1"));
                deliv[deliv.length - 1].setName2(data.getStringExtra("name2"));
                deliv[deliv.length - 1].setAddress(data.getStringExtra("address"));
                deliv[deliv.length - 1].setPhone(data.getStringExtra("phone"));
                deliv[deliv.length - 1].setTotal(Double.parseDouble(Objects.requireNonNull(data.getStringExtra("total"))));

                this.save();
            }
        }

        //Sets the data of the selected entry to the new edited data
        //Location is remembered by delivSel, which is returned to default value after its use
        if (requestCode == 2)
        {
            if(resultCode == ViewDeliv.EDIT)
            {
                delivSel = data.getIntExtra("position", -1);

                deliv[delivSel].setName1(data.getStringExtra("name1"));
                deliv[delivSel].setName2(data.getStringExtra("name2"));
                deliv[delivSel].setAddress(data.getStringExtra("address"));
                deliv[delivSel].setPhone(data.getStringExtra("phone"));
                deliv[delivSel].setTotal(Double.parseDouble(Objects.requireNonNull(data.getStringExtra("total"))));

                Toast edit = Toast.makeText(getApplicationContext(), "Entry updated.", Toast.LENGTH_SHORT);
                edit.show();
                delivSel = -1;

                this.save();
            }
            else if(resultCode == ViewDeliv.DELETE)
            {
                delivSel = data.getIntExtra("position", -1);

                deliv = delDelivery(deliv, delivSel);
                Toast del = Toast.makeText(getApplicationContext(), "Delivery #" + deliv[delivSel].getNo() + " deleted.", Toast.LENGTH_LONG);
                del.show();
                delivSel = -1;

                this.save();
                recreate();
            }
            /*else if(resultCode == ViewDeliv.NO_ACTION) For if no action was taken in ViewDeliv
            {

            }*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            //Opens the NewDeliv activity
            case R.id.main_action_add:
                Intent newDeliv = new Intent(getApplicationContext(), NewDeliv.class);
                startActivityForResult(newDeliv, 1);

                return true;

            //Sets elements of the delete all warning screen to visible
            case R.id.main_action_delete_all:
                if(deliv.length != 0)
                {
                    warnBg.setVisibility(View.VISIBLE);
                    warnSym.setVisibility(View.VISIBLE);
                    warnMsg.setVisibility(View.VISIBLE);
                    warnYes.setVisibility(View.VISIBLE);
                    warnNo.setVisibility(View.VISIBLE);
                }
                else //If no data exists, print an error
                {
                    Toast empty = Toast.makeText(getApplicationContext(), "No data to delete.", Toast.LENGTH_SHORT);
                    empty.show();
                }

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //More action bar setup
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }
}
