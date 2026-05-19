package it.gennystabile.ricettariobe.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    private static final DateTimeFormatter IT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private DateTimeUtils() {
    }

    /**
     * Calcola l'età in anni compiuti a partire da una specifica data di nascita
     * fino alla data di sistema odierna.
     *
     * @param birthDate la data di nascita (non deve essere null)
     * @return l'età calcolata in anni compiuti
     * @throws IllegalArgumentException se il parametro birthDate è nullo
     */
    public static int calculateAge(final LocalDateTime birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("La data di nascita non può essere nulla");
        }
        return Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears();
    }

    /**
     * Calcola il numero esatto di giorni di differenza tra due date.
     * L'ordine in cui vengono passate le date è ininfluente, poiché il risultato
     * è espresso sempre come valore assoluto.
     *
     * @param start la data di inizio (può essere null)
     * @param end   la data di fine (può essere null)
     * @return il numero di giorni di scarto, oppure 0 se una delle due date è nulla
     */
    public static long daysBetween(final LocalDateTime start, final LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return Math.abs(ChronoUnit.DAYS.between(start, end));
    }

    /**
     * Formatta un oggetto {@link LocalDateTime} nello standard italiano
     * (giorno/mese/anno ore:minuti).
     *
     * @param date la data e ora da formattare (può essere null)
     * @return la stringa formattata, oppure una stringa vuota se l'input è nullo
     */
    public static String formatItalianStandard(final LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(IT_FORMATTER);
    }
}
