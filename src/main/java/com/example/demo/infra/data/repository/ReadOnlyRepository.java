package com.example.demo.infra.data.repository;

import com.example.demo.domain.entity.Entity;
import java.io.Serializable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.NoRepositoryBean;



@NoRepositoryBean
public interface ReadOnlyRepository<T extends Entity<T, I>, I extends Serializable> extends FindByRepository<T, I> {
    @Cacheable(
            cacheResolver = "classMethodCacheResolver"
    )
    boolean existsById(I id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<I> ids);

   long count();
}
