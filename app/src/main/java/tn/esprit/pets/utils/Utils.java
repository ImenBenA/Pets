package tn.esprit.pets.utils;

import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Town;

public class Utils {

    public Utils() {

    }

    public Town stringToTown(String townString) {
        Town town;
        if (townString.toUpperCase().equals("ARIANA")) {
            town = Town.ARIANA;
        } else if (townString.toUpperCase().equals("BEJA")) {
            town = Town.BEJA;
        } else if (townString.toUpperCase().equals("BENAROUS")) {
            town = Town.BENAROUS;
        } else if (townString.toUpperCase().equals("BIZERTE")) {
            town = Town.BIZERTE;
        } else if (townString.toUpperCase().equals("GABES")) {
            town = Town.GABES;
        } else if (townString.toUpperCase().equals("GAFSA")) {
            town = Town.GAFSA;
        } else if (townString.toUpperCase().equals("JENDOUBA")) {
            town = Town.JENDOUBA;
        } else if (townString.toUpperCase().equals("KAIROUAN")) {
            town = Town.KAIROUAN;
        } else if (townString.toUpperCase().equals("KASSERINE")) {
            town = Town.KASSERINE;
        } else if (townString.toUpperCase().equals("KEBILI")) {
            town = Town.KEBILI;
        } else if (townString.toUpperCase().equals("KEF")) {
            town = Town.KEF;
        } else if (townString.toUpperCase().equals("MAHDIA")) {
            town = Town.MAHDIA;
        } else if (townString.toUpperCase().equals("MANOUBA")) {
            town = Town.MANOUBA;
        } else if (townString.toUpperCase().equals("MEDENINE")) {
            town = Town.MEDENINE;
        } else if (townString.toUpperCase().equals("MONASTIR")) {
            town = Town.MONASTIR;
        } else if (townString.toUpperCase().equals("NABEUL")) {
            town = Town.NABEUL;
        } else if (townString.toUpperCase().equals("SFAX")) {
            town = Town.SFAX;
        } else if (townString.toUpperCase().equals("SIDIBOUZID")) {
            town = Town.SIDIBOUZID;
        } else if (townString.toUpperCase().equals("SILIANA")) {
            town = Town.SILIANA;
        } else if (townString.toUpperCase().equals("SOUSSE")) {
            town = Town.SOUSSE;
        } else if (townString.toUpperCase().equals("TATAOUINE")) {
            town = Town.TATAOUINE;
        } else if (townString.toUpperCase().equals("TOZEUR")) {
            town = Town.TOZEUR;
        } else if (townString.toUpperCase().equals("TUNIS")) {
            town = Town.TUNIS;
        } else {
            town = Town.ZAGHOUAN;
        }
        return town;
    }

    public PetType stringToPetType( String petTypeString) {
        PetType petType;
        if (petTypeString.toUpperCase().equals("CAT")) {
            petType = PetType.CAT;
        } else if (petTypeString.toUpperCase().equals("DOG")) {
            petType = PetType.DOG;
        } else {
            petType = PetType.OTHER;
        }
        return petType;
    }
}
