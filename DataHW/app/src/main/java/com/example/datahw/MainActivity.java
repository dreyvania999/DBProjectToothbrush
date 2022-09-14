package com.example.datahw;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UpdateDB();
        buttong =findViewById(R.id.button);

    }

    public  void UpdateDB(){
        int i=0;
        TableLayout DBoutput = findViewById(R.id.xcvxcv);
        DBoutput.removeAllViews();
        do {
            TableRow DBoutputROW = new TableRow(this);
            DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

            TextView outputID= new TextView(this);
            params.weight = 1.0f;
            outputID.setLayoutParams(params);
            outputID.setText("1");
            DBoutputROW.addView(outputID);

            TextView outputName= new TextView(this);
            params.weight = 2.0f;
            outputName.setLayoutParams(params);
            outputName.setText("1");
            DBoutputROW.addView(outputName);

            TextView outputSurname= new TextView(this);
            params.weight = 2.0f;
            outputSurname.setLayoutParams(params);
            outputSurname.setText("1");
            DBoutputROW.addView(outputSurname);

            TextView outputPhone= new TextView(this);
            params.weight = 2.0f;
            outputPhone.setLayoutParams(params);
            outputPhone.setText("1");
            DBoutputROW.addView(outputPhone);

            TextView outputAddress= new TextView(this);
            params.weight = 2.0f;
            outputAddress.setLayoutParams(params);
            outputAddress.setText("1");
            DBoutputROW.addView(outputAddress);
            DBoutput.addView(DBoutputROW);
            i+=1;

        } while (i<=10);
    }
    @Override
    public void onClick(View view) {




        switch (view.getId()){

            case R.id.button:
                UpdateDB();
                break;

        }
    }
}