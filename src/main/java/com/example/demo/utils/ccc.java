package com.example.demo.utils;

import java.util.Comparator;
import java.util.TreeSet;

public class ccc {

    public static void main(String[] args) {
        Comparator<Integer> cpt2 = (x,y) -> Integer.compare(2,22222);
        TreeSet<Integer> set2 = new TreeSet<>(cpt2);
    }
}
