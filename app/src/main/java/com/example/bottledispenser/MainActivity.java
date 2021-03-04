package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button cashout, Buy, addmoney, getreceipt;
    String drink, size, receipt;
    Spinner drinks, sizes;
    TextView moneybox, console;
    EditText choiceInput;
    SeekBar sb;
    Context context = null;
    BottleDispenser bd = BottleDispenser.getInstance();
    String file = "Kuitti.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        console = (TextView)findViewById(R.id.Console);
        moneybox = (TextView)findViewById(R.id.money);

        sb = (SeekBar)findViewById(R.id.seekBar);
        cashout = (Button) findViewById(R.id.cashout);
        addmoney = (Button) findViewById(R.id.addmoney);
        Buy = (Button) findViewById(R.id.Buy);
        getreceipt = (Button) findViewById(R.id.recbutton);

        Spinner drinks = findViewById(R.id.Drinks);
        Spinner sizes = findViewById(R.id.sizes);


        ArrayAdapter<CharSequence> sizeadapter = ArrayAdapter.createFromResource(this, R.array.Sizes, android.R.layout.simple_spinner_item );
        sizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizes.setAdapter(sizeadapter);

        ArrayAdapter<CharSequence> drinkadapter = ArrayAdapter.createFromResource(this, R.array.Beverages, android.R.layout.simple_spinner_item );
        drinkadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drinks.setAdapter(drinkadapter);

        drinks.setOnItemSelectedListener(this);
        sizes.setOnItemSelectedListener(this);


        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for ( int i = sb.getProgress(); 0 < i; --i) {
                    addMoney();
                }
                sb.setProgress(0);
            }
        });


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                addmoney.setText("Add money " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        context = MainActivity.this;
    }

    public void addMoney() {
        bd.addMoney();
        checkMoney();
    }

    public void checkMoney(){
        String money = Double.toString(bd.getMoney());
        moneybox.setText("Money added: " +money+"€");
    }

    public void returnMoney(View v){
        String text = bd.returnMoney();
        console.setText(text);
        String money = Double.toString(bd.getMoney());
        moneybox.setText("Money added: " +money+"€");
    }



    public void buyBottle(View v){
        Double s = Double.parseDouble(size);
        receipt = bd.buyBottle(drink, s);
        console.setText(receipt);
        writeReceipt(bd.getReceipt());
        String money = Double.toString(bd.getMoney());
        moneybox.setText("Money added: " +money+"€");
        }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sizes = (Spinner)parent;
        drinks = (Spinner)parent;
        if(sizes.getId()==R.id.sizes){
            size = parent.getItemAtPosition(position).toString();

        }
        if(drinks.getId()==R.id.Drinks){
            drink = parent.getItemAtPosition(position).toString();

        }
        }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void writeReceipt(String receipt){
        try{
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            ows.write(receipt+"\n");
            ows.close();
        } catch (IOException e) {
            Log.e("IOException", "Kuitin kirjoituksessa virhe.");
        }
    }

    public void getReceipt(View v){
        try{
            InputStream ins = context.openFileInput(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String s = null;
            s = br.readLine();
            console.setText(s);
            ins.close();
        } catch (IOException e) {
            Log.e("IOException", "Virhe kuitin lukemisessa.");
        }
    }



}




