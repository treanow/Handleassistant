package com.letv.handleassistant.utils;


import java.util.Comparator;

public class PinyinComparator implements Comparator<String> {

    @Override
    public int compare(String lhs, String rhs) {
        // TODO Auto-generated method stub
        if (lhs.equals("@") || rhs.equals("#")) {
            return -1;
        } else if (lhs.equals("#") || rhs.equals("@")) {
            return 1;
        } else {
            return lhs.compareTo(rhs);
        }
    }

}
