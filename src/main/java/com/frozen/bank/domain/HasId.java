package com.frozen.bank.domain;

public interface HasId<T> {

    T getId();

    void setId(T id);

}
