package io.github.adsa06.data.local.dao;

import java.util.List;

import io.github.adsa06.data.local.database.DatabaseConnection;

public class BuildingsDao {
    private DatabaseConnection dbConfig;

    public BuildingsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void saveAll(Object entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public List<Object> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }    
}
