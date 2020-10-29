package com.example.myapplication;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {



    PosPlugin pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pos= new PosPlugin(this);

        ImageButton print= findViewById(R.id.Print);
        TestPrint(print);


        FloatingActionButton fab = findViewById(R.id.fab);
        TestPrint(fab);


    }

    private void TestPrint(ImageButton print) {
        print.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Printing data ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                StringBuffer receipts = new StringBuffer();
                receipts.append("POS Signed Order\n");
                pos.printText(receipts);
                receipts.setLength(0);
                receipts.append("The cardholder stub   \nPlease properly keep\n");
                receipts.append("-----------------------------------------------\n");
                receipts.append("Merchant Name:ABC\n");
                receipts.append("Merchant No.:846584000103052\n");
                receipts.append("Terminal No.:12345678\n");
                receipts.append("categories:visa card\n");
                receipts.append("Period of Validity:2018/04\n");
                receipts.append("Batch no:000101\n");
                receipts.append("Card Number:\n");
                receipts.append("622202400******0269\n");
                receipts.append("Trade Type:consumption\n");
                receipts.append("Serial No.:000024  \nAuthenticode:096706\n");
                receipts.append("Date/Time:2018/04/28 11:27:12\n");
                receipts.append("Ref.No.:123456789012345\n");
                receipts.append("Amount:$ 100.00\n");
                receipts.append("-----------------------------------------------\n");

                pos.printText(receipts);

                Snackbar.make(v, "Finish printing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
