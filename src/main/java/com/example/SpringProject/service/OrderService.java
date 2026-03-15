package com.example.SpringProject.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getOrdersByCustomerId(Integer customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = :customerId";
        MapSqlParameterSource params = new MapSqlParameterSource("customerId", customerId);
        return jdbcTemplate.queryForList(sql, params);
    }
}