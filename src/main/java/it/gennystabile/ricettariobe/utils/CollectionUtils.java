package it.gennystabile.ricettariobe.utils;

import java.util.*;

/**
 * Utility class per la gestione e manipolazione di collezioni Java.
 * Fornisce metodi helper per gestire valori null e verifiche di stato.
 */
public class CollectionUtils {

    /**
     * Costruttore privato per prevenire l'istanziazione di una classe utility.
     */
    private CollectionUtils() {
    }

    /**
     * Restituisce una lista vuota se la lista passata in input è null,
     * altrimenti restituisce la lista stessa. Utile per evitare nullPointerException
     *
     * @param <T> il tipo degli elementi nella lista
     * @param col la lista da controllare
     * @return la lista originale se non nulla, altrimenti {@link Collections#emptyList()}
     */
    public static <T> List<T> emptyIfNull(List<T> col) {
        //se lista è nulla, restituisce una lista vuota, altrimenti lista stessa
        return col == null ? Collections.emptyList() : col;
    }

    /**
     * Restituisce un set vuoto se il set passato in input è null,
     * altrimenti restituisce il set stesso. Utile per evitare nullPointerException
     *
     * @param <T> il tipo degli elementi nel set
     * @param set il set da controllare
     * @return il set originale se non nullo, altrimenti {@link Collections#emptySet()}
     */
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null ? Collections.emptySet() : set;
    }

    /**
     * Verifica se una collezione è null o priva di elementi.
     * Utilizza il wildcard (?) poiché il tipo degli elementi non influenza la verifica.
     *
     * @param coll la collezione da verificare
     * @return {@code true} se la collezione è null o vuota, {@code false} altrimenti
     */
    public static boolean isEmpty(Collection<?> coll) {
        //ritorna true se la collezione è nulla o vuota
        return (coll == null || coll.isEmpty());
    }

    /**
     * Verifica se una collezione non è null e contiene almeno un elemento.
     * Utilizza il wildcard (?) poiché il tipo degli elementi non influenza la verifica.
     *
     * @param col la collezione da verificare
     * @return {@code true} se la collezione contiene elementi, {@code false} se è null o vuota
     */
    public static boolean isNotEmpty(Collection<?> col) {
        return !isEmpty(col);
    }

    /**
     * Estrae tutti gli elementi duplicati presenti in una lista.
     * <p>
     * L'algoritmo mantiene inalterato l'ordine cronologico in cui i duplicati
     * vengono rilevati per la prima volta. La complessità temporale è O(N).
     * </p>
     *
     * @param <T>  il tipo degli elementi contenuti nella lista
     * @param list la lista da analizzare (può essere null)
     * @return una nuova lista contenente esclusivamente gli elementi duplicati,
     * oppure una lista vuota se l'input è nullo o non vi sono duplicati
     */
    public static <T> List<T> getDuplicates(final List<T> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();

        Set<T> elementiDuplicati = new HashSet<>();
        Set<T> elementiVisti = new HashSet<>();

        list.forEach(element -> {
            if (!elementiVisti.add(element)) {
                elementiDuplicati.add(element);
            }
        });
        return new ArrayList<>(elementiDuplicati);
    }

}
