package udproject.compras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import udproject.compras.FrDialog.IngresarPresupuesto;
import udproject.compras.firebase.LocalDB;

public class CreacionLista extends AppCompatActivity implements View.OnClickListener {

    Button CrearLista, ListaYaCreada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_lista);
        ImageView img=findViewById(R.id.ImagenInicio2);

        img.setImageResource(R.drawable.photo4951865291495811277);
        CrearLista=findViewById(R.id.CrearLista);

        CrearLista.setOnClickListener(this);
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