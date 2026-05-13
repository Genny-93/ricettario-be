package it.gennystabile.ricettario_be.utils.stringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class per la manipolazione e l'analisi di stringhe.
 * Offre metodi ottimizzati per il padding, la conversione di case, il partizionamento e la gestione dei null.
 */
public final class StringUtils {

    /**
     * Stringa vuota costante.
     */
    public static final String EMPTY = "";

    private StringUtils() {
    }

    /**
     * Aggiunge caratteri di padding a sinistra fino al raggiungimento della lunghezza desiderata.
     *
     * @param value     La stringa originale.
     * @param length    La lunghezza totale finale desiderata.
     * @param character Il carattere da utilizzare per il padding.
     * @return La stringa con padding a sinistra o la stringa originale se già sufficientemente lunga.
     */
    public static String leftPad(String value, int length, char character) {
        return padString(value, length, character, Direction.LEFT);
    }

    /**
     * Aggiunge caratteri di padding a destra fino al raggiungimento della lunghezza desiderata.
     *
     * @param value     La stringa originale.
     * @param length    La lunghezza totale finale desiderata.
     * @param character Il carattere da utilizzare per il padding.
     * @return La stringa con padding a destra o la stringa originale se già sufficientemente lunga.
     */
    public static String rightPad(String value, int length, char character) {
        return padString(value, length, character, Direction.RIGHT);
    }

    /**
     * Metodo interno per gestire il padding direzionale utilizzando {@link String#repeat(int)}.
     */
    private static String padString(String value, int length, char character, Direction direction) {
        // 1. Se l'input è null, viene trasformato in una stringa vuota ("").
        // L'uso di 'final' garantisce che il riferimento 's' non venga mai cambiato nel metodo.
        final String s = (value == null) ? EMPTY : value;

        // 2. calcola quanti caratteri di padding sono necessari.
        final int paddingLength = length - s.length();

        // 3. Se la stringa ha già la lunghezza desiderata o è più lunga, viene restituita immediatamente.
        if (paddingLength <= 0) return s;

        // 4.'String.valueOf(character)' trasforma il char in String.
        // '.repeat(n)' è un metodo da Java 11+ altamente ottimizzato.
        // Esso crea una stringa ripetendo il carattere scelto per il numero di volte necessario..
        final String padding = String.valueOf(character).repeat(paddingLength);

        // 5. In base alla direzione scelta nell'Enum, concatena il padding a sinistra o a destra.
        // Se LEFT: [padding]+[testo] -> il testo viene spinto a destra.
        // Se RIGHT: [testo]+[padding] -> il testo resta a sinistra.
        return (direction == Direction.LEFT) ? padding + s : s + padding;
    }

    /**
     * Converte una stringa da camelCase (o PascalCase) a snake_case.
     *
     * @param s La stringa da convertire.
     * @return La stringa convertita in snake_case.
     */
    public static String fromCamelToSnakeCase(String s) {

        if (s == null) return null;
        //StringBuilder è più efficace di concatenazione stringhe
        // perché modifica un unico contenitore invece di creare nuovi oggetti testo ogni volta
        StringBuilder stringFinale = new StringBuilder();
        //Ciclo for per analizzare ogni singolo carattere della stringa originale.
        for (int i = 0; i < s.length(); i++) {
            // Estrae il carattere alla posizione corrente
            char c = s.charAt(i);
            //se il carattere è una lettera maiuscola ed i>0
            if (Character.isUpperCase(c) && i > 0) {
                stringFinale.append('_');
            }
            //trasforma il carattere in minuscolo e lo aggiunge al risultato.
            stringFinale.append(Character.toLowerCase(c));
        }
        return stringFinale.toString();
    }

    /**
     * Verifica se una sequenza di caratteri è nulla, vuota o composta solo da spazi bianchi.
     *
     * @param cs La sequenza da controllare.
     * @return true se nulla, vuota o composta da spazi.
     */
    //In input viene dato CharSequence è un'interfaccia implementata da String ed anche altre classi, come StringBuilder
    //etc... rendendo il metodo flessibile usando il polimorfismo.
    public static boolean isBlank(CharSequence cs) {
        //controlla se cs è null.
        if (cs == null) return true;
        //Se l'input è proprio una stringa, si usa il metodo delle stringhe isBlank che è più veloce.
        //si fa il casting del charsequence a string.
        if (cs instanceof String) return ((String) cs).isBlank();

        int lunghezzaStringa = cs.length();
        for (int i = 0; i < lunghezzaStringa; i++) {
            // Se si trova solo un carattere che NON è uno spazio, allora non è blank.
            //Si usa metodo statico di Character
            if (!Character.isWhitespace(cs.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Verifica se una sequenza di caratteri non è né nulla, né vuota, né composta solo da spazi bianchi.
     *
     * @param cs La sequenza da controllare.
     * @return true se contiene caratteri non bianchi.
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * Restituisce una stringa vuota se l'input è null, altrimenti l'input stesso.
     *
     * @param cs La sequenza da valutare.
     * @return La stringa o {@link #EMPTY}.
     */
    public static String emptyIfNull(CharSequence cs) {
        return (cs == null) ? EMPTY : cs.toString();
    }

    /**
     * Converte una stringa da kebab-case a PascalCase.
     *
     * @param s La stringa da convertire.
     * @return La stringa convertita in PascalCase.
     */
    public static String fromKebabToPascalCase(String s) {
        if (isBlank(s)) return s;
        return firstCharToUpperCase(fromKebabToCamelCase(s));
    }

    /**
     * Converte una stringa da kebab-case a camelCase.
     *
     * @param s La stringa da convertire.
     * @return La stringa convertita in camelCase.
     */
    public static String fromKebabToCamelCase(String s) {
        // se l'input è nullo, restituisce nullo per evitare errori.
        if (s == null) return null;

        // Crea un stringbuilder con la stessa lunghezza della stringa originale.
        // Questo ottimizza la memoria evitando che il buffer debba allargarsi durante il lavoro.
        StringBuilder sb = new StringBuilder(s.length());

        // Variabile "interruttore" dice se il prossimo carattere deve
        // essere trasformato in maiuscolo. Parte come falso.
        boolean upperNext = false;

        // Ciclo for-each: trasforma la stringa in un array di caratteri e li analizza uno per uno.
        for (char c : s.toCharArray()) {

            // Se il carattere corrente è un trattino.
            if (c == '-') {
                // Se sb è vuoto, si ignora il trattino iniziale.
                // Se non è vuoto, si "accende" l'interruttore per la maiuscola.
                upperNext = !sb.isEmpty();

            } else {
                // Se il carattere NON è un trattino, dobbiamo scriverlo.
                // se upperNext è vero, si scrive il carattere in MAIUSCOLO.
                sb.append(upperNext ? Character.toUpperCase(c) : c);

                // Dopo aver scritto una lettera, "spegne" sempre l'interruttore della maiuscola.
                upperNext = false;
            }
        }

        // 8. Converte il buffer finale nella stringa definitiva e la restituisce.
        return sb.toString();
    }

    /**
     * Unisce gli elementi di un Iterable in una singola stringa utilizzando un separatore.
     * Gli elementi null vengono ignorati.
     *
     * @param iterable  La collezione di elementi da unire.
     * @param separator Il separatore da inserire tra gli elementi.
     * @return La stringa risultante o null se l'iterable è null.
     */
    /*public static String join(final Iterable<?> iterable, final CharSequence separator) {
        if (iterable == null) return null;

        // Trasformiamo l'Iterable in uno Stream
        return StreamSupport.stream(iterable.spliterator(), false)
                // Invece di Objects::nonNull, usiamo la lambda esplicita
                .filter(obj -> obj != null)
                // Invece di Object::toString, chiamiamo il metodo sull'oggetto 'obj'
                .map(obj -> obj.toString())
                // Raccogliamo tutto in una stringa usando il separatore
                .collect(Collectors.joining(separator == null ? "" : separator));
    }*/

    /**
     * Unisce gli elementi di una collezione in una singola stringa, separati da un delimitatore.
     *
     * @param collection La sorgente dei dati (List, Set, ecc.)
     * @param separator  Il testo da inserire tra gli elementi
     * @return La stringa unita o null se la collezione è null
     */
    public static String join(Collection<?> collection, String separator) {
        //se la collezione è nulla, restituisce null
        if (collection == null) return null;

        //Apre un flusso (Stream) di dati dalla collezione.
        return collection.stream()
                // esamina ogni oggetto (obj) e lo lascia passare solo se non è nullo.
                //Quindi prende un elemento e controlla se rispetta una condizione
                .filter(obj -> obj != null)

                // converte ogni oggetto superstite in una stringa.
                // quindi prende l'elemento che sta passando e lo cambia in qualcos'altro.
                .map(obj -> obj.toString())

                //"incolla" tutte le stringhe ottenute.
                // Se il separatore fornito è null, usa una stringa vuota ""
                .collect(Collectors.joining(separator == null ? "" : separator));
    }

    /**
     * Tronca una stringa se supera la lunghezza massima specificata.
     *
     * @param cs     La sequenza di caratteri da troncare.
     * @param length Lunghezza massima consentita.
     * @return La stringa troncata o l'originale.
     */
    public static String truncate(CharSequence cs, int length) {
        if (cs == null) return null;
        return (cs.length() > length) ? cs.subSequence(0, length).toString() : cs.toString();
    }

    /**
     * Rende maiuscola la prima lettera della stringa. Supporta correttamente caratteri Unicode non-BMP.
     *
     * @param str La sequenza di caratteri da capitalizzare.
     * @return La stringa con la prima lettera maiuscola.
     */
    public static String firstCharToUpperCase(CharSequence str) {
        if (isBlank(str)) return emptyIfNull(str);

        final String s = str.toString();
        //Estrae il "Code Point" del primo carattere.
        // Si usa codePointAt invece di charAt perché alcuni caratteri (come le emoji o simboli rari)
        // occupano due posizioni (char) nella memoria di Java.
        final int firstCodePoint = s.codePointAt(0);
        //Converte il Code Point appena estratto nella sua versione maiuscola.
        final int newCodePoint = Character.toUpperCase(firstCodePoint);

        //Se la versione maiuscola è identica all'originale
        //restituisce la stringa originale senza creare nuovi oggetti in memoria.
        if (firstCodePoint == newCodePoint) {
            return s;
        }

        //Calcola quanto spazio occupa il primo carattere
        final int firstCodePointLength = Character.charCount(firstCodePoint);
        //ricostruisce la stringa finale
        return new StringBuilder(s.length())
                // Inserisce la nuova versione maiuscola del primo carattere
                .appendCodePoint(newCodePoint)
                // Incolla il resto della stringa originale, partendo da dopo il primo carattere
                .append(s.substring(firstCodePointLength))
                .toString();
    }

    /**
     * Esegue il padding a sinistra o tronca la stringa alla dimensione esatta specificata.
     *
     * @param str     La stringa di input.
     * @param size    La dimensione finale richiesta.
     * @param charPad Il carattere di padding.
     * @return Stringa di lunghezza pari a size.
     */
    public static String leftPadOrTruncate(String str, int size, char charPad) {
        final String s = emptyIfNull(str);
        return (s.length() > size) ? s.substring(0, size) : leftPad(s, size, charPad);
    }

    /**
     * Esegue il padding a destra o tronca la stringa alla dimensione esatta specificata.
     *
     * @param str     La stringa di input.
     * @param size    La dimensione finale richiesta.
     * @param charPad Il carattere di padding.
     * @return Stringa di lunghezza pari a size.
     */
    public static String rightPadOrTruncate(String str, int size, char charPad) {
        final String s = emptyIfNull(str);
        return (s.length() > size) ? s.substring(0, size) : rightPad(s, size, charPad);
    }

    /**
     * Divide una stringa in una lista di sottostringhe di dimensione fissa.
     *
     * @param text Il testo da dividere.
     * @param size La dimensione di ogni blocco.
     * @return Una lista di sottostringhe.
     * @throws IllegalArgumentException se size <= 0.
     */
    public static List<String> splitBySize(String text, int size) {
        if (text == null) return new ArrayList<>();
        //la dimensione dei pezzi deve essere almeno 1.
        if (size <= 0) throw new IllegalArgumentException("Size must be positive");

        final int length = text.length();
        //La formula (length + size - 1) / size serve per arrotondare sempre per eccesso la divisione
        List<String> ret = new ArrayList<>((length + size - 1) / size);
        //parte da 0 e "salta" in avanti di 'size' caratteri ogni volta.
        for (int start = 0; start < length; start += size) {
            //// Math.min(length, start + size) serve a non andare oltre la fine della stringa
            // quando l'ultimo pezzo è più corto della dimensione richiesta.
            ret.add(text.substring(start, Math.min(length, start + size)));
        }
        return ret;
    }

    /**
     * Divide una stringa in un array di sottostringhe di dimensione fissa.
     *
     * @param str  La stringa da dividere.
     * @param size La dimensione di ogni blocco.
     * @return Un array di sottostringhe.
     */
    public static String[] splitAtIndex(String str, int size) {
        //Chiama il metodo splitBySize che abbiamo visto prima e poi converte la List in un Array di stringhe.
        return splitBySize(str, size).toArray(new String[0]);
    }

    /**
     * Estrae una sottostringa con indice di inizio gestendo in sicurezza indici fuori limite.
     *
     * @param stringa La stringa originale.
     * @param start   L'indice di inizio.
     * @return La sottostringa o la stringa originale/vuota a seconda della validità dell'indice.
     */
    public static String subString(String stringa, int start) {
        if (stringa == null) return null;

        final int len = stringa.length();
        if (start < 0) return stringa;
        if (start >= len) return EMPTY;
        return stringa.substring(start);
    }

    /**
     * Estrae una sottostringa tra due indici normalizzandoli rispetto alla lunghezza della stringa.
     *
     * @param stringa La stringa originale.
     * @param start   L'indice di inizio (minimo 0).
     * @param end     L'indice di fine (massimo lunghezza stringa).
     * @return La sottostringa estratta in sicurezza.
     */
    public static String subString(String stringa, int start, int end) {
        if (stringa == null) return null;
        final int len = stringa.length();

        //controllo per i numeri negativi, altrimenti il metodo standard substring va in crash
        int actualStart = start;
        if (actualStart < 0) {
            actualStart = 0;
        }

        // controlla che la fine non va oltre la stringa
        int actualEnd = end;
        if (actualEnd > len) {
            actualEnd = len;
        }

        return (actualStart > actualEnd) ? EMPTY : stringa.substring(actualStart, actualEnd);
    }

    /**
     * Direzione per le operazioni di padding.
     */
    public enum Direction {
        /**
         * Allineamento a sinistra (padding a destra).
         */
        LEFT,
        /**
         * Allineamento a destra (padding a sinistra).
         */
        RIGHT
    }
}