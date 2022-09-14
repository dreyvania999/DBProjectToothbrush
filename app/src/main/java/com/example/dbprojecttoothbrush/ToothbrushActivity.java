package com.example.dbprojecttoothbrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ToothbrushActivity extends AppCompatActivity implements View.OnClickListener{

    EditText TermOfUse_Day,Price,editNameToothbrush;
    Button AddButton,BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toothbrush);
        AddButton =findViewById(R.id.AddButton);
        TermOfUse_Day =findViewById(R.id.TermOfUse_Day);
        Price =findViewById(R.id.Price);
        editNameToothbrush =findViewById(R.id.editNameToothbrush);
        BackButton =findViewById(R.id.BackButton);
    }
    public void GetTextFormSql()
    {
        try {

            DBHelper connectionHelper = new DBHelper();
            Connection connection = connectionHelper.connectionClass();

            if (connection!=null)
            {
                String query = "INSERT INTO Toothbrush()";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);


            }
            else
            {
                //ConnectionResult="Check Connection";
            }

        }
        catch (Exception ex)
        {


        }
    }


    @Override
    public void onClick(View view) {

            switch (view.getId()){

                case R.id.BackButton:
                    Intent intent =new Intent(this,MainActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }
