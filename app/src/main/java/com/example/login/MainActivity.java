package com.example.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button registrationBtn;
    private EditText loginEdTxt;
    private EditText passwordEdTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        loginEdTxt = findViewById(R.id.loginEdTxt);
        passwordEdTxt = findViewById(R.id.passwordEdTxt);

        registrationBtn = findViewById(R.id.regBtn);
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    registrationIn();
                    Toast.makeText(MainActivity.this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Введите данные", Toast.LENGTH_LONG).show();
                }
            }
        });
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    singIn();
                } else {
                    Toast.makeText(MainActivity.this, "Введите данные", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void registrationIn() {
        saveFile("login", loginEdTxt.getText().toString());
        saveFile("password", passwordEdTxt.getText().toString());
    }

    private void singIn() {
        if(readFile("login", loginEdTxt.getText().toString()) && readFile("password", passwordEdTxt.getText().toString())){
            Toast.makeText(MainActivity.this, "Вход выполнен", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this, "Введены неправильный логин или пароль", Toast.LENGTH_LONG).show();
        }
    }

    private boolean readFile(String fileName, String data) {

        try (FileInputStream fileInputStream = openFileInput(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            try {
                String line = reader.readLine();
                return line.equals(data);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void saveFile(String fileName, String data) {
        try (FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bw = new BufferedWriter(outputStreamWriter)) {
            bw.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkData() {
        return !TextUtils.isEmpty(loginEdTxt.getText().toString()) && !TextUtils.isEmpty(passwordEdTxt.getText().toString());
    }
}
