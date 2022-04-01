package com.example.babydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BaseActivity extends AppCompatActivity {

    EditText descEt;
    EditText idEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        descEt = findViewById(R.id.main_name_et);
        idEt = findViewById(R.id.main_id_et);
        Button saveBtn = findViewById(R.id.main_save_btn);
        Button cancelBtn = findViewById(R.id.main_cancel_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        String name = descEt.getText().toString();
        String id = idEt.getText().toString();
        Log.d("TAG","saved name:" + name + " id:" + id + " uri:" );
    }
}