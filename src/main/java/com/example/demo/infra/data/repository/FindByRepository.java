package com.example.demo.infra.data.repository;

import com.example.demo.domain.entity.Entity;
import java.io.Serializable;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;



@NoRepositoryBean
public interface FindByRepository<T extends Entity<T, I>, I extends Serializable> extends Repository<T, I> {
    @Cacheable(
            cacheResolver = "classMethodCacheResolver",
            unless = "#result == null"
    )
    Optional<T> findById(I id);
}
