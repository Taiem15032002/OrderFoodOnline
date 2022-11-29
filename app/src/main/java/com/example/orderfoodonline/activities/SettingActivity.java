package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.models.Cart;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.paperdb.Paper;

public class SettingActivity extends AppCompatActivity {

    public TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Paper.init(this);
        Anhxa();
        testchuyenhuong();
    }

    private void testchuyenhuong() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().delete("username");
                Paper.book().delete("sdt");
                List<Cart> cartList = Paper.book().read("cart");
                cartList.clear();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Anhxa() {
        btnLogout = findViewById(R.id.btnlogout);
    }

}