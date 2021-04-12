package com.bestSpringApplication.taskManager.utils;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// TODO: 3/24/2021 may will better
@NoArgsConstructor
@ToString
public class VersionedList<T> {
    private final List<T> values = new ArrayList<>();

    public static <R> VersionedList<R> of(R... values){
        VersionedList<R> versionedList = new VersionedList<>();
        for (R el:values) versionedList.put(el);
        return versionedList;
    }

    public T getNewest(){
        return values.get(values.size()-1);
    }

    public T getOldest(){
        return values.get(0);
    }

    public T get(int index){
        return values.get(index);
    }

    public T remove(int index){
        return values.remove(index);
    }

    public boolean put(T elem){
        return values.add(elem);
    }

}
