package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityText = findViewById(R.id.text_city);
        Button backBtn = findViewById(R.id.button_back);

        String name = getIntent().getStringExtra("city_name");
        if (name != null) {
            cityText.setText(name);
        }

        // Back goes to MainActivity
        backBtn.setOnClickListener(v -> finish());
    }
}
