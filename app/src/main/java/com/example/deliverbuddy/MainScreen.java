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

//TODO: Make all tip and subtotal doubles

public class MainScreen extends AppCompatActivity
{
    private static final String FILE_NAME = "delivIndex.txt";
    private static final String TITLE_MAIN = "Deliveries";

    private Delivery[] deliv = new Delivery[0];
    private int delivSel;
    private File file;

    private MainListAdapter adapter;
    private ListView mainList;
    private Toolbar toolbarMain;
    private TextView emptyMsg;
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
            delivs.append(i.getAddress1().toString()).append("\n");
            delivs.append(i.getAddress2().toString()).append("\n");
            delivs.append(i.getCity().toString()).append("\n");
            delivs.append(i.getZip().toString()).append("\n");
            delivs.append(i.getPhone().toString()).append("\n");
            delivs.append(i.getSubtotal()).append("\n");
            delivs.append(i.getTip()).append("\n");
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
                    i.setAddress1(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setAddress2(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setCity(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setZip(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setPhone(line);
                }
                if ((line = br.readLine()) != null)
                {
                    i.setSubtotal(Double.parseDouble(line));
                }
                if ((line = br.readLine()) != null)
                {
                    i.setTip(Double.parseDouble(line));
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
                    deliv[deliv.length-1].setAddress1(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setAddress2(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setCity(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setZip(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setPhone(line);
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setSubtotal(Double.parseDouble(line));
                }
                if ((line = br.readLine()) != null) {
                    deliv[deliv.length-1].setTip(Double.parseDouble(line));
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

        //Variable locations to access elements on the screen
        mainList = findViewById(R.id.main_list);
        toolbarMain = findViewById(R.id.toolbar_main);
        emptyMsg = findViewById(R.id.empty_msg);
        warnBg = findViewById(R.id.warn_bg);
        warnSym = findViewById(R.id.warn_sym);
        warnMsg = findViewById(R.id.warn_msg);
        warnYes = findViewById(R.id.warn_yes);
        warnNo = findViewById(R.id.warn_no);

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
                delivSel = position;

                Intent viewDeliv = new Intent(getApplicationContext(), ViewDeliv.class);
                viewDeliv.putExtra("name1", deliv[position].getName1().toString());
                viewDeliv.putExtra("name2", deliv[position].getName2().toString());
                viewDeliv.putExtra("address1", deliv[position].getAddress1().toString());
                viewDeliv.putExtra("address2", deliv[position].getAddress2().toString());
                viewDeliv.putExtra("city", deliv[position].getCity().toString());
                viewDeliv.putExtra("zip", deliv[position].getZip().toString());
                viewDeliv.putExtra("phone", deliv[position].getPhone().toString());
                viewDeliv.putExtra("no", deliv[position].getNo().toString());
                viewDeliv.putExtra("subtotal", deliv[position].getSubtotal());
                viewDeliv.putExtra("tip", deliv[position].getTip());

                startActivityForResult(viewDeliv, 2);
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();

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
                deliv[deliv.length - 1].setAddress1(data.getStringExtra("address1"));
                deliv[deliv.length - 1].setAddress2(data.getStringExtra("address2"));
                deliv[deliv.length - 1].setCity(data.getStringExtra("city"));
                deliv[deliv.length - 1].setZip(data.getStringExtra("zip"));
                deliv[deliv.length - 1].setPhone(data.getStringExtra("phone"));
                deliv[deliv.length - 1].setSubtotal(data.getDoubleExtra("subtotal", 0));
                deliv[deliv.length - 1].setTip(data.getDoubleExtra("tip", 0));
                deliv[deliv.length - 1].calcTotal();

                this.save();
            }
        }

        //Sets the data of the selected entry to the new edited data
        //Location is remembered by delivSel, which is returned to default value after its use
        if (requestCode == 2)
        {
            if(resultCode == ViewDeliv.EDIT)
            {
                deliv[delivSel].setName1(data.getStringExtra("name1"));
                deliv[delivSel].setName2(data.getStringExtra("name2"));
                deliv[delivSel].setAddress1(data.getStringExtra("address1"));
                deliv[delivSel].setAddress2(data.getStringExtra("address2"));
                deliv[delivSel].setCity(data.getStringExtra("city"));
                deliv[delivSel].setZip(data.getStringExtra("zip"));
                deliv[delivSel].setPhone(data.getStringExtra("phone"));
                deliv[delivSel].setSubtotal(data.getDoubleExtra("subtotal", 0));
                deliv[delivSel].setTip(data.getDoubleExtra("tip", 0));

                Toast edit = Toast.makeText(getApplicationContext(), "Entry updated.", Toast.LENGTH_SHORT);
                edit.show();
                delivSel = -1;

                this.save();
            }
            else if(resultCode == ViewDeliv.DELETE)
            {
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
