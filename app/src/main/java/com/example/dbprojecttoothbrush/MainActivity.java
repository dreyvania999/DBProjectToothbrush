package com.example.dbprojecttoothbrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText NameToothbrush;
    Button AddToothbrush;
    Connection connection;
    String ConnectionResult = "";
    Switch SActivateSort;
    Spinner spinner2,spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout DBoutput = findViewById(R.id.DataTable);
        NameToothbrush = findViewById(R.id.SearchNameTB);
        AddToothbrush = findViewById(R.id.AddToothbrush);
        SActivateSort= findViewById(R.id.SActivateSort);
        spinner2 = findViewById(R.id.spinner2);
        spinner= findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        GetTextFormSql(DBoutput);
        DBHelper.id=-1;
        SActivateSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableLayout DBoutput = findViewById(R.id.DataTable);
                GetTextFormSql(DBoutput);
            }
        });

        NameToothbrush.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetTextFormSql(DBoutput);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetTextFormSql(DBoutput);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                GetTextFormSql(DBoutput);
            }
        });
    }
    private String SelectedStrigOrder(int g)
    {
        String d = "";
        switch (g)
        {
            case 0 :
                d= " ORDER BY NameOfTheToothbrush";
                break;
            case 1 :
                d= " ORDER BY TermOfUse_Day";
                break;
            case 2 :
                d= " ORDER BY Price";
                break;
        }
        return d;
    }
    private String SelectedASCDESCOrder(int g)
    {
        String d="";
        switch (g)
        {
            case 0 :
                d= "ASC";
                break;
            case 1 :
                d= "DESC";
                break;
        }
        return d;
    }

    public void GetTextFormSql(TableLayout DBoutput) {
        DBoutput.removeAllViews();
        try {

            DBHelper DBHelper = new DBHelper();
            connection = DBHelper.connectionClass();
            addfirst( DBoutput);
            if (connection != null) {
                String query = "Select * FROM Toothbrush";
                query += " WHERE NameOfTheToothbrush LIKE '%" + NameToothbrush.getText().toString() + "%'";
                if (SActivateSort.isChecked()==true)
                {
                    query += SelectedStrigOrder(spinner.getSelectedItemPosition())+" "+ SelectedASCDESCOrder(spinner2.getSelectedItemPosition());
                }

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    UpdateDB(DBoutput, resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5));
                }
            } else {
                Toast.makeText(this, "?????????????????? ??????????????????????!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "????????????!", Toast.LENGTH_LONG).show();
        }
    }

    public static String encodeImage(Bitmap bitmap) {
        int prevW = 1000;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        int a = bitmap.getHeight();
        int c = bitmap.getWidth();

        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if (!encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(MainActivity.this.getResources(),
                R.drawable.toothbrush);
    }

    public void UpdateDB(TableLayout DBoutput, String ID, String TheToothbrush, String TermOfUse, String Price,String img) {

        TableRow DBoutputROW = new TableRow(this);
        DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = new TableRow.LayoutParams(175, 175);

        ImageView imageView = new ImageView(this);

        params.weight = 1.0f;
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(getImgBitmap(img));
        DBoutputROW.addView(imageView);


        TextView Id = new TextView(this);
        params.weight = 1.0f;
        Id.setLayoutParams(params);
        Id.setText(ID);
        Id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        DBoutputROW.addView(Id);

        TextView NameOfTheToothbrushT = new TextView(this);
        params.weight = 2.0f;
        NameOfTheToothbrushT.setLayoutParams(params);
        NameOfTheToothbrushT.setText(TheToothbrush);
        DBoutputROW.addView(NameOfTheToothbrushT);

        TextView TermOfUse_DayT = new TextView(this);
        params.weight = 2.0f;
        TermOfUse_DayT.setLayoutParams(params);
        TermOfUse_DayT.setText(TermOfUse);
        DBoutputROW.addView(TermOfUse_DayT);

        TextView Pricet = new TextView(this);
        params.weight = 2.0f;
        Pricet.setLayoutParams(params);
        Pricet.setText(Price);
        DBoutputROW.addView(Pricet);

        Button deleteButton = new Button(this);
        deleteButton.setOnClickListener(this);

        deleteButton.setText("X");
        deleteButton.setId(Integer.parseInt(ID));
        DBoutputROW.addView(deleteButton);
        DBoutputROW.setId(Integer.parseInt(ID));
        DBoutputROW.setOnClickListener((view -> {
            DBHelper.id = Integer.parseInt(ID);
            startActivity(new Intent(MainActivity.this, ToothbrushActivity.class));
        }));
        DBoutput.addView(DBoutputROW);


    }
    public void addfirst(TableLayout DBoutput) {

        TableRow DBoutputROW = new TableRow(this);
        DBoutputROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = new TableRow.LayoutParams(175, 175);

        TextView img = new TextView(this);
        params.weight = 1.0f;
        img.setLayoutParams(params);
        img.setText("????????");
        img.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        DBoutputROW.addView(img);


        TextView Id = new TextView(this);
        params.weight = 1.0f;
        Id.setLayoutParams(params);
        Id.setText("ID");
        Id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        DBoutputROW.addView(Id);

        TextView NameOfTheToothbrushT = new TextView(this);
        params.weight = 2.0f;
        NameOfTheToothbrushT.setLayoutParams(params);
        NameOfTheToothbrushT.setText("??????????");
        DBoutputROW.addView(NameOfTheToothbrushT);

        TextView TermOfUse_DayT = new TextView(this);
        params.weight = 2.0f;
        TermOfUse_DayT.setLayoutParams(params);
        TermOfUse_DayT.setText("???????? ????????????");
        DBoutputROW.addView(TermOfUse_DayT);

        TextView Pricet = new TextView(this);
        params.weight = 2.0f;
        Pricet.setLayoutParams(params);
        Pricet.setText("????????");
        DBoutputROW.addView(Pricet);

        Button deleteButton = new Button(this);
        deleteButton.setOnClickListener(this);

        TextView del = new TextView(this);
        params.weight = 2.0f;
        del.setLayoutParams(params);
        del.setText("??????????????");
        DBoutputROW.addView(del);
        DBoutput.addView(DBoutputROW);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        TableLayout DBoutput = findViewById(R.id.DataTable);
        GetTextFormSql(DBoutput);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        TableLayout DBoutput = findViewById(R.id.DataTable);
        GetTextFormSql(DBoutput);
    }

    @Override
    public void onClick(View view) {
        TableLayout DBoutput = findViewById(R.id.DataTable);
        switch (view.getId()) {

            case R.id.AddToothbrush:
                Intent intent = new Intent(this, ToothbrushActivity.class);
                startActivity(intent);
                break;
            case R.id.SActivateSort:
                GetTextFormSql(DBoutput);
                break;
            default:
                try {
                    DBHelper dbHelper = new DBHelper();
                    connection = dbHelper.connectionClass();
                    String id = String.valueOf((view.getId()));
                    if (connection != null) {
                        String query = "DELETE FROM Toothbrush WHERE id = " + id;
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(query);

                        Toast.makeText(this, "?????????? ??????????????", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "?????????????????? ??????????????????????", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "???????????????? ????????????", Toast.LENGTH_LONG).show();
                }
                GetTextFormSql(DBoutput);
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
        TableLayout DBoutput = findViewById(R.id.DataTable);
        GetTextFormSql(DBoutput);
    }
}