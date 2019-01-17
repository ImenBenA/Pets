package tn.esprit.pets.utils;

import tn.esprit.pets.entity.PetType;
import tn.esprit.pets.entity.Town;

public class Utils {

    public Utils() {

    }

    public Town stringToTown(String townString) {
        Town town;
        if (townString.equals("ARIANA")) {
            town = Town.ARIANA;
        } else if (townString.equals("BEJA")) {
            town = Town.BEJA;
        } else if (townString.equals("BENAROUS")) {
            town = Town.BENAROUS;
        } else if (townString.equals("BIZERTE")) {
            town = Town.BIZERTE;
        } else if (townString.equals("GABES")) {
            town = Town.GABES;
        } else if (townString.equals("GAFSA")) {
            town = Town.GAFSA;
        } else if (townString.equals("JENDOUBA")) {
            town = Town.JENDOUBA;
        } else if (townString.equals("KAIROUAN")) {
            town = Town.KAIROUAN;
        } else if (townString.equals("KASSERINE")) {
            town = Town.KASSERINE;
        } else if (townString.equals("KEBILI")) {
            town = Town.KEBILI;
        } else if (townString.equals("KEF")) {
            town = Town.KEF;
        } else if (townString.equals("MAHDIA")) {
            town = Town.MAHDIA;
        } else if (townString.equals("MANOUBA")) {
            town = Town.MANOUBA;
        } else if (townString.equals("MEDENINE")) {
            town = Town.MEDENINE;
        } else if (townString.equals("MONASTIR")) {
            town = Town.MONASTIR;
        } else if (townString.equals("NABEUL")) {
            town = Town.NABEUL;
        } else if (townString.equals("SFAX")) {
            town = Town.SFAX;
        } else if (townString.equals("SIDIBOUZID")) {
            town = Town.SIDIBOUZID;
        } else if (townString.equals("SILIANA")) {
            town = Town.SILIANA;
        } else if (townString.equals("SOUSSE")) {
            town = Town.SOUSSE;
        } else if (townString.equals("TATAOUINE")) {
            town = Town.TATAOUINE;
        } else if (townString.equals("TOZEUR")) {
            town = Town.TOZEUR;
        } else if (townString.equals("TUNIS")) {
            town = Town.TUNIS;
        } else {
            town = Town.ZAGHOUAN;
        }
        return town;
    }

    public PetType stringToPetType( String petTypeString) {
        PetType petType;
        if (petTypeString.equals("CAT")) {
            petType = PetType.CAT;
        } else if (petTypeString.equals("DOG")) {
            petType = PetType.DOG;
        } else {
            petType = PetType.OTHER;
        }
        return petType;
    }
}
