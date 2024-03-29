package com.cwk.gps.service;

public interface Function<T, E> {

    T callback(E e);

}