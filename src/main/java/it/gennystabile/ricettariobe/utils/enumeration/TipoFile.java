package it.gennystabile.ricettariobe.utils.enumeration;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TipoFile {
    IMMAGINE(List.of("jpg", "jpeg", "png", "svg")),
    VIDEO(List.of("avi", "mp4")),
    AUDIO(List.of("mp3")),
    DOCUMENTO(List.of("pdf", "doc")),
    GIF(List.of("gif"));
    private List<String> extensions;

    //costruttore
    TipoFile(List<String> extensions) {
        this.extensions = extensions;
    }

    public static TipoFile fromExtensions(@NotNull String val) {
        Map<String, TipoFile> extensionMap = new HashMap<>();
        val = val.trim().toLowerCase();

        //Ciclo tutti i valori dell'enum, da Immagine a Gif. TipoFile.values() restituisce un array: [IMMAGINE, VIDEO, AUDIO, DOCUMENTO, GIF]
        for (TipoFile tipo : TipoFile.values()) {
            //Per ogni valore dell'enum, ciclo la lista di stringhe
            for (String s : tipo.extensions) {
                //popolo la mappa, con chiave l'elemento della lista e valore il Tipo
                extensionMap.put(s, tipo);
            }
        }

        return extensionMap.get(val);
    }
}
