package com.example.dbprojecttoothbrush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttong;
    Connection connection;
    String ConnectionResult = "";
    String NameOfTheToothbrush,TermOfUse_Day,Price,id;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout DBoutput = findViewById(R.id.DataTable);
        UpdateDB(DBoutput);
        buttong =findViewById(R.id.AddToothbrush);

    }

        public void GetTextFormSql()
    {


        try {

            DBHelper connectionHelper = new DBHelper();
            connection = connectionHelper.connectionClass();

            if (connection!=null)
            {
                String query = "Select + From Masks";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);


                while (resultSet.next())
                {

                    id=resultSet.getString(1);
                    NameOfTheToothbrush= resultSet.getString(2);
                    TermOfUse_Day=resultSet.getString(3);
                    Price=resultSet.getString(4);

                }
            }
            else
            {
                ConnectionResult="Check Connection";
            }

        }
        catch (Exception ex)
        {


        }
    }


    public  void UpdateDB(TableLayout DBoutput){
        int i=0;
        GetTextFormSql();
        DBoutput.removeAllViews();
        do {
            TableRow DBoutputROW = new TableRow(this);
            DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

            TextView outputID= new TextView(this);
            params.weight = 1.0f;
            outputID.setLayoutParams(params);
            outputID.setText(id);
            DBoutputROW.addView(outputID);

            TextView NameOfTheToothbrushT= new TextView(this);
            params.weight = 2.0f;
            NameOfTheToothbrushT.setLayoutParams(params);
            NameOfTheToothbrushT.setText(NameOfTheToothbrush);
            DBoutputROW.addView(NameOfTheToothbrushT);

            TextView TermOfUse_DayT= new TextView(this);
            params.weight = 2.0f;
            TermOfUse_DayT.setLayoutParams(params);
            TermOfUse_DayT.setText(TermOfUse_Day);
            DBoutputROW.addView(TermOfUse_DayT);

            TextView Pricet= new TextView(this);
            params.weight = 2.0f;
            Pricet.setLayoutParams(params);
            Pricet.setText(Price);
            DBoutputROW.addView(Pricet);

            DBoutput.addView(DBoutputROW);
            i+=1;

        } while (i<=10);
    }

    @Override
    public void onClick(View view) {
        TableLayout DBoutput = findViewById(R.id.DataTable);



        switch (view.getId()){

            case R.id.AddToothbrush:
                UpdateDB(DBoutput);
                break;

        }
    }
}