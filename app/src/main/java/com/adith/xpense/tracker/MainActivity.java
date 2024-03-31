package com.adith.xpense.tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adith.xpense.tracker.models.User;
import com.adith.xpense.tracker.utils.FireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class MainActivity extends AppCompatActivity {
    TextView welcome;
    private FirebaseAuth mAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBase.init();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }

        welcome = findViewById(R.id.welcome);
        FireBase.getUser(mAuth.getCurrentUser().getUid(), task -> {
            if (task.isSuccessful()) {
                User user = ((DataSnapshot) task.getResult()).getValue(User.class);
                assert user != null;
                welcome.setText("Welcome " + user.name + "!");
            }
        });
    }

    public void expenseEntry(View view) {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(this, expenseEntry.class);
        startActivity(intent);
    }

    public void expenseList(View view) {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(this, expenseList.class);
        startActivity(intent);
    }

    public void logout(View view) {
        mAuth.signOut();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}