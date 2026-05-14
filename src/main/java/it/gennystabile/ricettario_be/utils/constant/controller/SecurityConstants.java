package it.gennystabile.ricettario_be.utils.constant.controller;

public class SecurityConstants {

    public static final String ALL_PROFILES = "hasAnyRole('ADMIN','USER')";
    public static final String USER = "hasRole('ROLE_USER')";
    public static final String ADMIN = "hasRole('ROLE_ADMIN')";

}
