package io.vn.nguyenduck.blocktopograph.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.vn.nguyenduck.blocktopograph.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);

        Fragment contentView = getSupportFragmentManager().findFragmentById(R.id.content_view);
        assert contentView != null;
        NavController navController = NavHostFragment.findNavController(contentView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        new Handler().post(() -> Toast.makeText(this, "Opening Demo GameActivity...", Toast.LENGTH_LONG).show());
        new Handler().postDelayed(() -> startActivity(new Intent(this, GameActivity.class)), 5000);
    }
}