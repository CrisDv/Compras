package udproject.compras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import udproject.compras.FrDialog.IngresarPresupuesto;
import udproject.compras.firebase.LocalDB;

public class CreacionLista extends AppCompatActivity implements View.OnClickListener {

    Button CrearLista, ListaYaCreada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_lista);

        CrearLista=findViewById(R.id.CrearLista);
        ListaYaCreada=findViewById(R.id.ListaYaCreada);

        CrearLista.setOnClickListener(this);
        ListaYaCreada.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.CrearLista:
                DialogFragment newFragment=new IngresarPresupuesto();
                newFragment.show(getSupportFragmentManager(), "xde");
                break;
            case R.id.ListaYaCreada:

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalDB localDB=new LocalDB(this);
        if (localDB.ifExist()){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}