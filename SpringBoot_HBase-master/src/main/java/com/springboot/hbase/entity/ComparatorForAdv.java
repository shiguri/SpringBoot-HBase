package com.springboot.hbase.entity;

import java.util.Comparator;

public class ComparatorForAdv implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Advertisement advertisement1 = (Advertisement)o1;
        Advertisement advertisement2 = (Advertisement)o2;

        int advId1 = Integer.parseInt(advertisement1.getAdvId());
        int advId2 = Integer.parseInt(advertisement2.getAdvId());

        if (advId1 < advId2){
            return -1;
        }else if (advId1 == advId2){
            return -1;
        }else {
            return 1;
        }
    }
}
