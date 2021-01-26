package udproject.compras;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import udproject.compras.FrDialog.EditarPresupuesto;
import udproject.compras.FrDialog.IngresarPresupuesto;
import udproject.compras.firebase.LocalDB;
import udproject.compras.mainfragments.ListasFragment;
import udproject.compras.mainfragments.HomeFragment;
import udproject.compras.mainfragments.CuentaFragment;

public class  MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView TXTpresupuesto, presupuestoGastado;
    ConversionLetras conversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because eachA
        // menu should be considered as top level destinations.

        navView.setOnNavigationItemSelectedListener(this);
       /* NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//           NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
        
        //TOOLBAR
        Toolbar toolbar= findViewById(R.id.toolnarNavInterfaz);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        conversion=new ConversionLetras();

        TXTpresupuesto=findViewById(R.id.presupuesto);
        presupuestoGastado=findViewById(R.id.presupuestoGastado);
        SharedPreferences sharedPref =getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String Valor=sharedPref.getString("Presupuesto", "0");
        TXTpresupuesto.setText("$ "+conversion.SeparadorFormat(Valor));

        ActualizarPresupuestoGastadio();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_HOME, new HomeFragment()).commit();
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
                /*Intent i=new Intent(this, IngresarPresupuesto.class);
                startActivity(i);*/
                DialogFragment newFragment=new EditarPresupuesto();
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


    public void ActualizarPresupuestoGastadio(){
        LocalDB localDB=new LocalDB(this);
        String PresupuestoGastado=String.valueOf(localDB.CantidadGastada());

        SharedPreferences sharedPref =getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String Valor=sharedPref.getString("Presupuesto", "0");

        int Gastadoint=Integer.parseInt(PresupuestoGastado);
        //int PresupuestoOriginal=Integer.parseInt(String.valueOf(TXTpresupuesto.getText()));
        int PresupuestoOriginal=Integer.parseInt(Valor);
        presupuestoGastado.setText("$ "+conversion.SeparadorFormat(PresupuestoGastado));
        if (PresupuestoOriginal>=Gastadoint){
            presupuestoGastado.setTextColor(getResources().getColor(R.color.Verde));
        }
        else{
            presupuestoGastado.setTextColor(getResources().getColor(R.color.Rojo));
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref =getSharedPreferences("CREDENCIALES",Context.MODE_PRIVATE);
        String valor=sharedPref.getString("IDlista", "NO HAY NADA");


        System.out.println(valor+"presupuesto: "+sharedPref.getString("Presupuesto", ""));
    }
}