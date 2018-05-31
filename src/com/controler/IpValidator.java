package com.controler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Permet de vérifier si une adresse ip a le format d'une adresse ip existante
 */
public class IpValidator {

    private Pattern pattern ;
    private Matcher matcher;

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public void IPAddressValidator(){
        pattern = Pattern.compile(IPADDRESS_PATTERN);
    }


    /**
     * Vérifie si l'adresse ip a le bon format
     * @param ip adresse ip
     * @return renvoei vrai si l'adresse a le bon format
     */
    public boolean validate(final String ip){
        IPAddressValidator();
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
