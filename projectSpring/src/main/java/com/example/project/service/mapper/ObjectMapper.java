package com.example.project.service.mapper;

import java.sql.SQLException;
import java.util.List;

public interface ObjectMapper<T> {
    List<T> extractFromResult(List<Object[]> result)throws SQLException;
}
