package io.github.adsa06.data.local.dao;

import java.util.List;

import io.github.adsa06.data.local.database.DatabaseConnection;

public class AchievementsDao implements BaseDao<Object> {
    private DatabaseConnection dbConfig;

    public AchievementsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public void save(Object entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Object> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void update(Object entity) {
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }
}
