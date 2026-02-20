package com.example.padelscore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.padelscore.ui.custom.CustomBottomNavigationView;
import com.example.padelscore.ui.login.LoginActivity;
import com.example.padelscore.util.TokenManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        // Si no hay token, ir a LoginActivity
        if (TokenManager.getToken(this) == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment (R.id.nav_host_fragment) no encontrado en activity_main.xml");
        }
        NavController navController = navHostFragment.getNavController();
        CustomBottomNavigationView customBottomNav = findViewById(R.id.custom_bottom_navigation);
        if (customBottomNav == null) {
            throw new IllegalStateException("CustomBottomNavigationView (R.id.custom_bottom_navigation) no encontrado en activity_main.xml");
        }
        // Navega a Torneos al iniciar la app
        navController.navigate(R.id.tournamentListFragment);
        customBottomNav.setOnTabSelectedListener(index -> {
            switch (index) {
                case 0:
                    navController.navigate(R.id.tournamentListFragment);
                    break;
                case 1:
                    navController.navigate(R.id.matchesFragment);
                    break;
                case 2:
                    navController.navigate(R.id.favoritesFragment);
                    break;
                case 3:
                    navController.navigate(R.id.profileFragment);
                    break;
            }
        });
    }

    private void setHeaderTitle(String title) {
        View header = findViewById(R.id.header);
        if (header != null) {
            TextView titleView = header.findViewById(R.id.header_title);
            if (titleView != null) {
                titleView.setText(title);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // No forzar título por defecto aquí
    }
}
