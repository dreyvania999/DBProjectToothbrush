package com.example.dbprojecttoothbrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button AddToothbrush;
    Connection connection;
    String ConnectionResult = "";

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout DBoutput = findViewById(R.id.DataTable);
        AddToothbrush =findViewById(R.id.AddToothbrush);
            GetTextFormSql(DBoutput);


    }

        public void GetTextFormSql( TableLayout DBoutput) {
            DBoutput.removeAllViews();
            try {

                DBHelper DBHelper = new DBHelper();
                connection = DBHelper.connectionClass();

                if (connection != null) {
                    String query = "Select * FROM Toothbrush";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);


                    while (resultSet.next()) {
                        UpdateDB(DBoutput, resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                    }
                } else {
                    Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
            }
        }
    public  void UpdateDB(TableLayout DBoutput, String a,String b, String c,String d){

            TableRow DBoutputROW = new TableRow(this);
            DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

            TextView outputID= new TextView(this);
            params.weight = 1.0f;
            outputID.setLayoutParams(params);
            outputID.setText(a);
            DBoutputROW.addView(outputID);

            TextView NameOfTheToothbrushT= new TextView(this);
            params.weight = 2.0f;
            NameOfTheToothbrushT.setLayoutParams(params);
            NameOfTheToothbrushT.setText(b);
            DBoutputROW.addView(NameOfTheToothbrushT);

            TextView TermOfUse_DayT= new TextView(this);
            params.weight = 2.0f;
            TermOfUse_DayT.setLayoutParams(params);
            TermOfUse_DayT.setText(c);
            DBoutputROW.addView(TermOfUse_DayT);

            TextView Pricet= new TextView(this);
            params.weight = 2.0f;
            Pricet.setLayoutParams(params);
            Pricet.setText(d);
            DBoutputROW.addView(Pricet);

            DBoutput.addView(DBoutputROW);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.AddToothbrush:
                Intent intent =new Intent(this,ToothbrushActivity.class);
                startActivity(intent);
                break;

        }
    }
}