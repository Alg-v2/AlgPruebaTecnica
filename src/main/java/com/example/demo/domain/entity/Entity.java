package com.example.demo.domain.entity;

import java.io.Serializable;

public interface Entity<E, I extends Serializable> {
    boolean sameIdentityAs(E other);

    I getId();

    void setId(I id);
}
