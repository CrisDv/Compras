package udproject.compras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import udproject.compras.FrDialog.IngresarPresupuesto;
import udproject.compras.mainfragments.CuentaFragment;
import udproject.compras.menubar.ComportamientoPresupuestos;
import udproject.compras.menubar.ConfiguracionDeCuenta;
import udproject.compras.menubar.ListasGuardadas;

public class CreacionLista extends AppCompatActivity implements View.OnClickListener {

    Button CrearLista, ListaYaCreada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_lista);
        ImageView img=findViewById(R.id.ImagenInicio2);

        img.setImageResource(R.drawable.iconoapp);
        CrearLista=findViewById(R.id.CrearLista);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.ToolbarCreacionLista);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        CrearLista.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        menu.getItem(0).setVisible(false);

        FirebaseAuth mFirebaseAuth=FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser()==null){
            menu.getItem(4).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.Presupuestos:
                intent=new Intent(this, ComportamientoPresupuestos.class);
                startActivity(intent);
                break;
            case R.id.Listas:
                intent=new Intent(this, ListasGuardadas.class);
                startActivity(intent);
                break;
            case R.id.About:

                break;
            case R.id.CuentaOptionMenu:
                intent=new Intent(this, ConfiguracionDeCuenta.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.CrearLista:
                DialogFragment newFragment=new IngresarPresupuesto();
                newFragment.show(getSupportFragmentManager(), "xde");
                break;
        }
    }

    private boolean ifExist()
    {
        SharedPreferences sharedPref = this.getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
        String idl=sharedPref.getString("IDlista", "NO HAY NADA");

        if (idl!="NO HAY NADA")
        {
            return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ifExist()){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);

            finish();
        }
    }
}