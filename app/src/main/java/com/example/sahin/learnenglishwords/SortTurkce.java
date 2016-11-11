package com.example.sahin.learnenglishwords;

import java.util.Comparator;

/**
 * Created by Sahin on 12.11.2016.
 */

public class SortTurkce implements Comparator<Kelime> {
    @Override
    public int compare(Kelime o1, Kelime o2) {
        return o1.getTurkce().compareTo(o2.getTurkce());
    }
}