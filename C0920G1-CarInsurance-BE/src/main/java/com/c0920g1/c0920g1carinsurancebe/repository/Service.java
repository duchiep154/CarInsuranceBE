package com.c0920g1.c0920g1carinsurancebe.repository;

import java.util.Optional;
@org.springframework.stereotype.Service
public interface Service<E> {
    Iterable<E> getAll();
    Optional<E> findById(Long id);
    void save(E e);
    void update(E e);
    boolean remove(Long id);

}
