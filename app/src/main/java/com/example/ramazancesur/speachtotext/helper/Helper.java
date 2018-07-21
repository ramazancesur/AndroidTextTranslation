package com.example.ramazancesur.speachtotext.helper;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Helper {


    public TextToSpeech createTextToSpeech(final Locale locale, Context mContext){
         TextToSpeech tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    int result = tts.setLanguage(locale);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("Text2SpeechWidget", result + " is not supported");
                    }
                }
            }
        });

        return tts;
    }

}
