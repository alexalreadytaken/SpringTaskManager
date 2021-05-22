package com.bestSpringApplication.taskManager.utils;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class VersionedList<T> {

    private final List<T> values;

    public VersionedList() {
        values = new ArrayList<>();
    }

    @SafeVarargs
    public static <R> VersionedList<R> of(R... values){
        VersionedList<R> versionedList = new VersionedList<>();
        for (R el:values) versionedList.put(el);
        return versionedList;
    }

    public boolean isEmpty(){
        return values.isEmpty();
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

    public T removeOldest(){
        return values.remove(0);
    }

    public T removeNewets(){
        return values.remove(values.size()-1);
    }

}
