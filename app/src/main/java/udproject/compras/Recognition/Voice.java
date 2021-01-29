package udproject.compras.Recognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

import udproject.compras.ConversionLetras;
import udproject.compras.R;
import udproject.compras.firebase.LocalDB;

public class Voice extends AppCompatActivity {


    String Nombre, Precio;
    private final int RCODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        Button ButtonVoiceNombre=findViewById(R.id.NombreVoz);

        ButtonVoiceNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturaDeVozNombre();
            }
        });

        Button VozPrecio=findViewById(R.id.PrecioVoz);
        VozPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturaDeVozPrecio();
            }
        });
        ValidarPermisos();
    }

    private void CapturaDeVozNombre()
    {
        Intent intent = new Intent(RecognizerIntent
                .ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, RCODE);
        } else
        {
            Log.e("ERROR","Su dispositivo no admite entrada de voz");
        }
    }

    private void CapturaDeVozPrecio(){
        Intent intent = new Intent(RecognizerIntent
                .ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, 2);
        } else
        {
            Log.e("ERROR","Su dispositivo no admite entrada de voz");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        ArrayList<String> result = data.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1:

                    /*ConversionLetras as=new ConversionLetras();
                    System.out.println(result.get(0)+"------------------------------------**************************------------*-*-*-*-");
                    System.out.println(as.Convertir(result.get(0), false));*/
                    Nombre=result.get(0);
                    break;
                case 2:
                    System.out.println("Caso 2");
                    Precio=result.get(0);
                    Agregar(Nombre, Precio);
                    /*ConversionLetras as=new ConversionLetras();
                    System.out.println(result.get(0)+"------------------------------------**************************------------*-*-*-*-");
                    System.out.println(as.Convertir(result.get(0), false));*/
                    break;

            }
        }
    }

    private void Agregar(String Nombre, String Precio){
        int IDRandom= (int) (Math.floor(Math.random() * (5000 - 1)) + 1);//Math.floor(Math.random() * (max - min)) + min;
        LocalDB localDB=new LocalDB(this);
        int preciofinal=Integer.parseInt(Precio);
        localDB.AgregarProducto(IDRandom, Nombre, preciofinal, 1);
    }

    private void ValidarPermisos()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RCODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==RCODE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){

                System.out.println("das");
            }
        }
    }
}