package io.github.adsa06.data.local.dao;

import java.util.List;

import io.github.adsa06.data.local.database.DatabaseConnection;

public class UpgradesDao implements BaseDao<Object> {
    private DatabaseConnection dbConfig;

    public UpgradesDao(DatabaseConnection dbConfig) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }    
}
