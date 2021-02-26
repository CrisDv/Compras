package udproject.compras.menubar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import udproject.compras.CreacionLista;
import udproject.compras.R;

public class ConfiguracionDeCuenta extends AppCompatActivity {

    Button Cerrar, Eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_de_cuenta);

        Cerrar=findViewById(R.id.CerrarSesion);
        Eliminar=findViewById(R.id.EliminarCuenta);

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth ma=FirebaseAuth.getInstance();

                ma.getCurrentUser().delete();
                //Intent intent=new Intent(this, CreacionLista.class);
                //startActivity(intent);
            }
        });

        Cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth ma=FirebaseAuth.getInstance();

                ma.signOut();
                //Intent intent=new Intent(this, CreacionLista.class);
                //startActivity(intent);
            }
        });
    }
}