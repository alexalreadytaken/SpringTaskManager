package com.bestSpringApplication.taskManager.handlers;

import java.util.HashMap;
import java.util.Map;

public class ModifiableSingletonMap<T,S> extends HashMap<T,S> {

    private static final int SIZE = 1;

    @Override
    public S put(T key, S value) {
        if (isFull()){
            clear();
        }
        return super.put(key, value);
    }

    @Override
    //fixme
    public void putAll(Map<? extends T, ? extends S> m) {
        throw new UnsupportedOperationException();
    }

    public boolean updateKey(T key){
        if(!super.isEmpty()){
            S value = super.values().iterator().next();
            clear();
            put(key,value);
            return true;
        }
        return false;
    }
    public boolean updateValue(S value){
        if (!super.isEmpty()){
            T key = super.keySet().iterator().next();
            clear();
            put(key,value);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        super.clear();
    }

    public ModifiableSingletonMap() {
        super(SIZE);
    }

    public boolean isFull(){
        return super.size() == SIZE;
    }

    public int getSIZE() {
        return SIZE;
    }
}
