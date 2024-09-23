package com.example.demo.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@SuperBuilder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Prices extends AggregateRoot<Prices,UUID> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private int productId;
    private int brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priceList;
    private int priority;
    private double price;
    private String curr;
}
