package com.example.ramazancesur.speachtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramazancesur.speachtotext.translation.TranslationText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
                implements View.OnClickListener, TextToSpeech.OnInitListener{

    private TextView txtSpeachingText;
    private ImageButton btnSpeak;
    private Button btnTextToSpeach;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSpeachingText = findViewById(R.id.txtTranslated);
        btnTextToSpeach= findViewById(R.id.btnTextToSpeach);

        tts= new TextToSpeech(this,this);
        btnSpeak=  findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(this);
        btnTextToSpeach.setOnClickListener(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSpeak:
                promptSpeechInput();
                break;
            case R.id.btnTextToSpeach:
                speakOut();
                break;
        }
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut() {
        String text = txtSpeachingText.getText().toString();
        tts.setLanguage(Locale.forLanguageTag("ru"));
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }



    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String speachingText= result.get(0);

                    try {
                        txtSpeachingText.setText(speachingText);;

                        String translatedText=
                                TranslationText.translateTextWithOptions(txtSpeachingText.getText().toString(), "ru");
                        txtSpeachingText.setText(translatedText);
                        Toast.makeText(getApplicationContext(),translatedText,Toast.LENGTH_SHORT).show();
                    } catch (Exception ex){
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(),"hata meydana geldi",Toast.LENGTH_LONG).show();
                    }

                }
                break;
            }

        }
    }
}
