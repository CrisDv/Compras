package udproject.compras.Recognition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import udproject.compras.R;

public class Voice extends AppCompatActivity {

    private TextToSpeech ttspeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        TextToSpeech.OnInitListener ttsListener=new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status==TextToSpeech.SUCCESS){
                    ttspeech.setLanguage(Locale.getDefault());
                }
            }
        };

        ttspeech=new TextToSpeech(this, ttsListener);
    }
}