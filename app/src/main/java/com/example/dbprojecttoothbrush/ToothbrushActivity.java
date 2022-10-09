package com.example.dbprojecttoothbrush;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class ToothbrushActivity extends AppCompatActivity implements View.OnClickListener{

    EditText TermOfUse_Day,Price,editNameToothbrush;
    Button AddButton,BackButton;
    Connection connection;
    String Image;
    String ConnectionResult = "";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toothbrush);
        AddButton =findViewById(R.id.AddButton);
        TermOfUse_Day =findViewById(R.id.TermOfUse_Day);
        Price =findViewById(R.id.Price);
        editNameToothbrush =findViewById(R.id.editNameToothbrush);
        BackButton =findViewById(R.id.BackButton);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImg.launch(intent);
        });
        if (DBHelper.id!=-1)
        {
            GetTextFormSql();

        }

    }

    public final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bitmap);
                    Image = MainActivity.encodeImage(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });
    public void UpdateTextFormSql( ) {
        try {
            DBHelper dbHelper = new DBHelper();
            connection = dbHelper.connectionClass();

            if (connection != null) {
                String query = "UPDATE Toothbrush SET NameOfTheToothbrush = '" + editNameToothbrush.getText().toString() +
                        "', TermOfUse_Day =" + Integer.parseInt(TermOfUse_Day.getText().toString()) + ", Price = " + Double.parseDouble(Price.getText().toString()) + ", Image = '" +Image+
                        "' WHERE Id = " + DBHelper.id;
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
                String query = "INSERT INTO Toothbrush(NameOfTheToothbrush,TermOfUse_Day,Price,Image) VALUES('"+(editNameToothbrush.getText().toString())+"',"+ Integer.parseInt(TermOfUse_Day.getText().toString())+","+Double.parseDouble(Price.getText().toString())+", '"+ Image +"')";
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
    private Bitmap getImgBitmap(String encodedImg) {
        if (!encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(ToothbrushActivity.this.getResources(),
                R.drawable.toothbrush);
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
                    imageView.setImageBitmap(getImgBitmap(resultSet.getString(5)));
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
