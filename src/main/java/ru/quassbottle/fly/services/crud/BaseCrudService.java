package ru.quassbottle.fly.services;

import java.util.List;

public interface BaseCrudService<T, ID> {
    List<T> getAll();
    T getById(ID id);
    T save(T t);
    T update(T t, ID id);
    void deleteById(ID id);
}
