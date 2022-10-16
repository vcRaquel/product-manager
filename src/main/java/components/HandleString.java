package components;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class HandleString {
    public String handleString(String string) {
        string= string.toLowerCase(Locale.ROOT);

        string = string.replaceAll("[\\s]", "");
        string = string.replaceAll("[´]", "");
        string = string.replaceAll("[\\^]", "");
        string = string.replaceAll("[~]", "");
        string = string.replaceAll("[`]", "");
        string = string.replaceAll("[']", "");

        string = string.replaceAll("[èéêë]", "e");
        string = string.replaceAll("[úûùü]", "u");
        string = string.replaceAll("[íïî]", "i");
        string = string.replaceAll("[áàâã]", "a");
        string = string.replaceAll("[òóô]", "o");
        string = string.replaceAll("[ç]", "c");
        string = string.replaceAll("[ñ]", "n");

        String temporary = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        //É uma propriedade de bloco Unicode.
        return pattern.matcher(temporary).replaceAll("");

    }
}
