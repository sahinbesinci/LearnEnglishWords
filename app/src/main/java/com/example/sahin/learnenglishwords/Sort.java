package com.example.sahin.learnenglishwords;

import java.util.Comparator;

/**
 * Created by sahin on 26.10.2016.
 */
public class Sort implements Comparator<Kelime> {

    @Override
    public int compare(Kelime o1, Kelime o2) {
        return o1.getIngilizce().compareTo(o2.getIngilizce());
    }
}
