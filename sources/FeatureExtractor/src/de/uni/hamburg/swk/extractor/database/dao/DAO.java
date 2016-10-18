package de.uni.hamburg.swk.extractor.database.dao;

import java.util.List;

public interface DAO<T>
{
    List<T> getAll();
    
    T getById(int id);
}
