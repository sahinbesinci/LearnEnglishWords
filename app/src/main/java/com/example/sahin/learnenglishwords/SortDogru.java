package com.example.sahin.learnenglishwords;

import java.util.Comparator;

/**
 * Created by Sahin on 12.11.2016.
 */

public class SortDogru implements Comparator<Kelime> {
    @Override
    public int compare(Kelime o1, Kelime o2) {
        return o2.getDogru()-o1.getDogru();
    }
}