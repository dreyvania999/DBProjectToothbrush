package com.example.dbprojecttoothbrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ToothbrushActivity extends AppCompatActivity implements View.OnClickListener{

    EditText TermOfUse_Day,Price,editNameToothbrush;
    Button AddButton,BackButton;
    Connection connection;

    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toothbrush);
        AddButton =findViewById(R.id.AddButton);
        TermOfUse_Day =findViewById(R.id.TermOfUse_Day);
        Price =findViewById(R.id.Price);
        editNameToothbrush =findViewById(R.id.editNameToothbrush);
        BackButton =findViewById(R.id.BackButton);
        if (DBHelper.id!=-1)
        {
            GetTextFormSql();

        }

    }
    public void UpdateTextFormSql( ) {
        try {
            DBHelper dbHelper = new DBHelper();
            connection = dbHelper.connectionClass();

            if (connection != null) {
                String query = "UPDATE Toothbrush SET NameOfTheToothbrush = '" + editNameToothbrush.getText().toString() +
                        "', TermOfUse_Day =" + Integer.parseInt(TermOfUse_Day.getText().toString()) + ", Price = " + Double.parseDouble(Price.getText().toString()) +
                        " WHERE Id = " + DBHelper.id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                Toast.makeText(this, "Данные успешно изменены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Возникла ошибка!", Toast.LENGTH_LONG).show();
        }
    }
    public void SetTextFormSql() {
        try {

            DBHelper DBHelper = new DBHelper();
            connection = DBHelper.connectionClass();

            if (connection != null) {
                String query = "INSERT INTO Toothbrush(NameOfTheToothbrush,TermOfUse_Day,Price) VALUES('"+(editNameToothbrush.getText().toString())+"',"+ Integer.parseInt(TermOfUse_Day.getText().toString())+","+Double.parseDouble(Price.getText().toString())+")";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                Toast.makeText(this, "Запись успешно добавлена", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
        }
    }
    public void GetTextFormSql( ) {
        try {

            DBHelper DBHelper = new DBHelper();
            connection = DBHelper.connectionClass();

            if (connection != null) {
                String query = "Select * FROM Toothbrush WHERE id = "+DBHelper.id;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    TermOfUse_Day.setText(resultSet.getString(3));
                    Price.setText(resultSet.getString(4));
                    editNameToothbrush.setText(resultSet.getString(2));
                }
            } else {
                Toast.makeText(this, "Проверьте подключение!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onClick(View view) {

            switch (view.getId()){
                case R.id.AddButton:
                    if (DBHelper.id!=-1)
                    {
                        UpdateTextFormSql( );

                    }else {
                        SetTextFormSql();
                    }
                    break;

                case R.id.BackButton:

                    DBHelper.id=-1;
                    Intent intent =new Intent(this,MainActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }
