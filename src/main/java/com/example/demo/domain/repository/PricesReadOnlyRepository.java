package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Prices;
import com.example.demo.infra.data.repository.ReadOnlyRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PricesReadOnlyRepository extends ReadOnlyRepository<Prices, UUID> {

    @Query("SELECT prices FROM Prices prices WHERE prices.productId = :product AND prices.brandId = :brand AND :date BETWEEN prices.startDate AND prices.endDate ORDER BY priority DESC LIMIT 1")
    Optional<Prices> findByProductAndDateAndBand(int product, LocalDateTime date,int brand);

    List<Prices> findByProductId(int productId);

    List<Prices> findByBrandId(int brandId);

    @Query("SELECT prices FROM Prices prices WHERE :date BETWEEN prices.startDate AND prices.endDate")
    List<Prices> findByDate(LocalDateTime date);
}