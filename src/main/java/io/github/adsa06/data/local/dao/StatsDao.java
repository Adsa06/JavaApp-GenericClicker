package io.github.adsa06.data.local.dao;

import java.util.List;

import io.github.adsa06.data.local.database.DatabaseConnection;

public class StatsDao implements BaseDao<Object> {
    private DatabaseConnection dbConfig;

    public StatsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    // Solo voy a necesitar findAll, deleteAll y update
    // Como son 2 datos puedo utilizar un record (DataClass) y un mapper (Con una interfaz) para pasarlo

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
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }
}
