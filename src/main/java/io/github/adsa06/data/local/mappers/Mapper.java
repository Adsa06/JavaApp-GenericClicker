package io.github.adsa06.data.local.mappers;

public interface Mapper<T, K> {
    T toDomain(K entity);
    K toEntity(T domain);
}
