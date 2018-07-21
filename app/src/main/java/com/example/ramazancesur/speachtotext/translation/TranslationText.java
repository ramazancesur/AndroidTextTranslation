package com.example.ramazancesur.speachtotext.translation;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.TranslateOptions;


public class TranslationText {


    /**
     * Create Google Translate API Service.
     *
     * @return Google Translate Service
     */
    private static Translate createTranslateService() {
        return TranslateOptions.newBuilder().setApiKey("key").build().getService();
    }

    /**
     * Translate the source text from source to target language.
     *
     * @param sourceText source text to be translated
     * @param targetLang target language of translated text
     */
    public static String translateTextWithOptions(
            String sourceText,
            String targetLang) {

        Translate translate = createTranslateService();
        TranslateOption tgtLang = TranslateOption.targetLanguage(targetLang);

        Translation translation = translate.translate(sourceText, tgtLang );
        String result= translation.getTranslatedText();
        return result;
    }


/*

    public String translationSentence(String sentence) throws GeneralSecurityException, IOException {
        String translatedText= "";
        Translate t = new Translate.Builder(
                GoogleNetHttpTransport.newTrustedTransport()
                , GsonFactory.getDefaultInstance(), null)
                // Set your application name
                .setApplicationName("Stackoverflow-Example")
                .build();
        Translate.Translations.List list = t.new Translations().list(
                Arrays.asList(
                        // Pass in list of strings to be translated
                        sentence),
                // Target language
                "TR");

        // TODO: Set your API-Key from https://console.developers.google.com/
        list.setKey("AIzaSyBc-7Drg7behX-22_uJ-c_iJaWDhcJOyFU");
        TranslationsListResponse response = list.execute();
        for (TranslationsResource translationsResource : response.getTranslations()) {
            translatedText+="\n" + translationsResource.getTranslatedText();
        }

        return translatedText;
    }
*/


}
