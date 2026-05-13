package it.gennystabile.ricettario_be.utils;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * Utility class per la gestione e manipolazione di oggetti {@link Map}.
 * Fornisce metodi null-safe per operazioni comuni.
 */
public class MapUtils {

    private MapUtils() {
    }

    /**
     * Unisce due mappe in una nuova istanza di {@link HashMap}.
     * <p>
     * Se una delle mappe in input è {@code null}, viene trattata come una mappa vuota.
     * In caso di chiavi duplicate, viene applicata la funzione di merge fornita.
     * </p>
     *
     * @param <T>           il tipo delle chiavi
     * @param <V>           il tipo dei valori
     * @param map1          la prima mappa da unire (può essere {@code null})
     * @param map2          la seconda mappa da unire (può essere {@code null})
     * @param mergeFunction funzione per risolvere le collisioni tra valori con la stessa chiave
     * @return una nuova {@link HashMap} contenente l'unione delle due mappe
     * @throws NullPointerException se {@code mergeFunction} è {@code null}
     */
    public static <T, V> Map<T, V> mergeMap(Map<T, V> map1, Map<T, V> map2, BinaryOperator<V> mergeFunction) {
        //Inizializza la mappa dei risultati con i contenuti della prima mappa.
        // Viene usata emptyIfNull per evitare NullPointerException se map1 è null
        Map<T, V> result = new HashMap<>(emptyIfNull(map1));

        //Itera sulla seconda mappa.
        // Per ogni coppia chiave-valore la funzione merge gestisce la logica di controllo:
        // - Se la chiave non esiste in 'result', viene aggiunta.
        // - Se la chiave esiste già, viene applicata la 'mergeFunction' per decidere il valore finale.
        // Quando il metodo esegue result.merge, si sta chiedendo alla mappa se ha già una
        // chiave uguale a questa key al suo interno. Si ricorda che nelle mappe gli elementi non
        // sono ordinati con posizioni, ma tramite codice hash della chiave."
        emptyIfNull(map2).forEach((key, value) -> result.merge(key, value, mergeFunction));
        return result;
    }

    /**
     * Restituisce una mappa vuota immutabile se l'argomento è {@code null},
     * altrimenti restituisce la mappa stessa.
     *
     * @param <K> il tipo delle chiavi
     * @param <V> il tipo dei valori
     * @param map la mappa da controllare (può essere {@code null})
     * @return la mappa originale o {@link Collections#emptyMap()} se null
     */
    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    /**
     * Verifica in modo null-safe se la mappa specificata è vuota.
     * <p>
     * Un input {@code null} restituisce {@code true}.
     * </p>
     *
     * @param map la mappa da controllare (può essere {@code null})
     * @return {@code true} se la mappa è null o non contiene elementi
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Verifica in modo null-safe se la mappa specificata non è vuota.
     * <p>
     * Un input {@code null} restituisce {@code false}.
     * </p>
     *
     * @param map la mappa da controllare (può essere {@code null})
     * @return {@code true} se la mappa è valorizzata e contiene almeno un elemento
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

}
