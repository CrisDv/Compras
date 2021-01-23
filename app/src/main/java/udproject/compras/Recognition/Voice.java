package udproject.compras.Recognition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Voice extends AppCompatActivity {


    private final int RCODE = 28;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        Button ha=findViewById(R.id.button);

        ha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturaDeVoz();
            }
        });
    }

    private void CapturaDeVoz()
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RCODE && resultCode == RESULT_OK && data != null)
        {
            ArrayList<String> result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            ConversionLetras as=new ConversionLetras();
            System.out.println(result.get(0)+"------------------------------------**************************------------*-*-*-*-");
            System.out.println(as.Convertir(result.get(0), false));
        }
    }
}