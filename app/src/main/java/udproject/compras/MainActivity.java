package udproject.compras;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import udproject.compras.fragments.Ingresar_Lista;
import udproject.compras.mainfragments.ListasFragment;
import udproject.compras.FrDialog.FragmentDialogPresupuesto;
import udproject.compras.mainfragments.HomeFragment;
import udproject.compras.mainfragments.CuentaFragment;

public class  MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setOnNavigationItemSelectedListener(this);
       /* NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//           NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
        
        //TOOLBAR
        Toolbar toolbar= findViewById(R.id.toolnarNavInterfaz);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_HOME, new Ingresar_Lista()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maintoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cantidad_presupuesto:
                /*Intent i=new Intent(this, FragmentDialogPresupuesto.class);
                startActivity(i);*/
                DialogFragment newFragment=new FragmentDialogPresupuesto();
                newFragment.show(getSupportFragmentManager(), "xde");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fmanager =getSupportFragmentManager();

        switch (menuItem.getItemId())
        {
            case R.id.navigation_principal:
                fmanager.beginTransaction().replace(R.id.nav_host_HOME, new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.navigation_listas:
                fmanager.beginTransaction().replace(R.id.nav_host_HOME, new ListasFragment()).addToBackStack(null).commit();
                break;
            case R.id.navigation_cuentas:
                fmanager.beginTransaction().replace(R.id.nav_host_HOME, new CuentaFragment()).addToBackStack(null).commit();
                break;

        }
        return true;
    }
}