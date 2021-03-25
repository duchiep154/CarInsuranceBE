package com.c0920g1.c0920g1carinsurancebe.service;

import java.util.Optional;

public interface Service<E> {
    Iterable<E> getAll();
    Optional<E> findById(Long id);
    void save(E e);
    void update(E e);
    boolean remove(Long id);
}
