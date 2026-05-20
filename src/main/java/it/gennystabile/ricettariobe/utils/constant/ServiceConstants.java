package it.gennystabile.ricettariobe.utils.constant;

import java.util.List;
import java.util.Map;

public class ServiceConstants {

    public static final List<String> LISTA_STAGIONI = List.of("Inverno", "Primavera", "Estate", "Inverno");

    public static final Map<String, Long> STAGIONI = Map.of(
            "Inverno", 1L,
            "Primavera", 2L,
            "Estate", 3L,
            "Autunno", 4L,
            "Tutte", 5L);

}
