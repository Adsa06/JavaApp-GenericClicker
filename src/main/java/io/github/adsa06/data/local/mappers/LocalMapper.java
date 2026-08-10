package io.github.adsa06.data.local.mappers;

public interface LocalMapper<T, K> {
    T toDomain(K entity);
    K toEntity(T domain);
}
