package com.example.tipsplitcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Locale;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    EditText btwTax;
    EditText noPeople;
    TextView tAmount;
    TextView twTip;
    TextView tpPerson;
    TextView overage;
    RadioGroup rGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btwTax = findViewById(R.id.editTotal);
        noPeople = findViewById(R.id.editNumberPeople);
        tAmount = findViewById(R.id.tipAmountView);
        twTip = findViewById(R.id.tipTotalView);
        rGroup = findViewById(R.id.rGroup);
        tpPerson = findViewById(R.id.totalPerPersonView);
        overage = findViewById(R.id.overageView);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

//        Save history before rotation
        outState.putString("btwTax", btwTax.getText().toString());
        outState.putString("noPeople", noPeople.getText().toString());
        outState.putString("tAmount", tAmount.getText().toString());
        outState.putString("twTip", twTip.getText().toString());
        outState.putString("tpPerson", tpPerson.getText().toString());
        outState.putString("overage", overage.getText().toString());



//        Call Super Last
        super.onSaveInstanceState(outState);
    }

//    paste history in rotated device
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

//        super call first
        super.onRestoreInstanceState(savedInstanceState);

        btwTax.setText(savedInstanceState.getString("btwTax"));
        noPeople.setText(savedInstanceState.getString("noPeople"));
        tAmount.setText(savedInstanceState.getString("tAmount"));
        twTip.setText(savedInstanceState.getString("twTip"));
        tpPerson.setText(savedInstanceState.getString("tpPerson"));
        overage.setText(savedInstanceState.getString("overage"));
    }

    public void radioClicked(View v) {

        //        format vals as currency
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();

        String preTotal = btwTax.getText().toString();
        double tip = 0;

//        if buttons are unchecked, do nothing. Else change value of tip based on the checked button
        if (preTotal.isEmpty()){
            rGroup.clearCheck();
            return;
        }

        if (v.getId() == R.id.rButton1){
            tip = .12;
        } else if (v.getId() == R.id.rButton2){
            tip = .15;
        } else if (v.getId() == R.id.rButton3){
            tip = .18;
        } else if (v.getId() == R.id.rButton4) {
            tip = .20;
        }

//        calculate tip amount and new total
        double tot = (Double.parseDouble(preTotal))*100;
        double calcTip, calcTot;

        calcTip = (Math.ceil(tot*tip));
        calcTot = (tot+calcTip);

//        set screen vals
        tAmount.setText(defaultFormat.format(calcTip/100));
        twTip.setText(defaultFormat.format(calcTot/100));

    }

//    main calculations
    public void goButtonTapped(View v) {

        //        format vals as currency
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();

        String preTotal = btwTax.getText().toString();
        String numPeople = noPeople.getText().toString();

        String totalWithTip = twTip.getText().toString();
        String sr = "$";

//        If input is empty, dont do anything. Else, calculate
        if (preTotal.isEmpty() || numPeople.isEmpty()) {
            return;
        }

        if (numPeople.equals("0")){
           noPeople.setText("1");
           numPeople = noPeople.getText().toString();
        }

//        pull total with tip from the screen without the $
        totalWithTip = totalWithTip.substring(1, totalWithTip.length());


//        Convert string val to double and int
        double totalDoub = Double.parseDouble(totalWithTip)*100;
        int numpeep = Integer.parseInt(numPeople);


//        integer division, total per person
        double retd = Math.ceil(totalDoub/numpeep);
        double retO = (totalDoub/numpeep);
        double over = ((retd-retO)*numpeep);

//        updates total per person on screen
        tpPerson.setText(defaultFormat.format(retd/100));

//        updates overage on screen
        overage.setText(defaultFormat.format(over/100));

    }

    //        set all to default vals
    public void clearButtonTapped(View v) {

        String def = getString(R.string.amountDefault);
        tAmount.setText(def);
        twTip.setText(def);
        tpPerson.setText(def);
        overage.setText(def);

        btwTax.setText("");
        noPeople.setText("");
        rGroup.clearCheck();
    }
}