package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SpeechSynthesis extends AsyncTask<Void, Integer, Boolean> {
    private TextToSpeech mTTS;
    public Context context;

    @Override
    protected Boolean doInBackground(Void... arg0) {
        mTTS = new TextToSpeech(context,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            mTTS.setLanguage(Locale.FRANCE);
                           // mTTS.speak("bonjour", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
        return true;
    }

    public void speek(String t) {
        mTTS.speak(t, TextToSpeech.QUEUE_FLUSH, null);
    }


    public String colorToSpeech(String tagColorHexa) {

        if(tagColorHexa.equals("#ff000000"))
            return "noir";
        else if(tagColorHexa.equals("#ffcc22c3"))
            return "violet";
        else if(tagColorHexa.equals("#ff8d33cc"))
            return "indigo";
        else if(tagColorHexa.equals("#ff288fcc"))
            return "bleu";
        else if(tagColorHexa.equals("#ff35cc42"))
            return "vert";
        else if(tagColorHexa.equals("#fffcff21"))
            return "jaune";
        else if(tagColorHexa.equals("#ffffb23a"))
            return "orange";
        else if(tagColorHexa.equals("#ffff1a1e"))
            return "rouge";
        else if(tagColorHexa.equals("#ffffffff"))
            return "blanc";

        return "couleur " + tagColorHexa;
    }
}