package udproject.compras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private final int DURACION=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView img=findViewById(R.id.ImagenInicio);

        img.setImageResource(R.drawable.iconoapp);

        new Handler().postDelayed(() -> {

            inicia();
            finish();
        }, DURACION);
    }


    protected void inicia() {


        Intent intent=new Intent(this, CreacionLista.class);
        startActivity(intent);

    }
}