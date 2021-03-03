package udproject.compras.menubar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import udproject.compras.BD.Realtimepst;
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

        Realtimepst realtimepst=new Realtimepst(this);
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth ma=FirebaseAuth.getInstance();

                ma.getCurrentUser().delete();
                //Intent intent=new Intent(this, CreacionLista.class);
                //startActivity(intent);
            }
        });
        FirebaseAuth ma=FirebaseAuth.getInstance();
        Cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ma.signOut();
                finish();
                //Intent intent=new Intent(this, CreacionLista.class);
                //startActivity(intent);
            }
        });

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realtimepst.BorrarUsuario();
                ma.signOut();
                finish();
            }
        });
    }

    private void Eliminar()
    {
        new AlertDialog.Builder(this).setTitle("ELIMINAR USUARIO?")
                .setMessage("Quieres eliminar tu usuario?, Tus listas quedaran guardadas" +
                        "en el dispositivo, pero no podras recuperarlas si las borras").show();
    }
}