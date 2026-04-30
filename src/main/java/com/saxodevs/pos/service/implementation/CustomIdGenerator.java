package com.saxodevs.pos.service.implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomIdGenerator {

    private final JdbcTemplate jdbcTemplate;

    public CustomIdGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public String generate(String prefix) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        int updated = jdbcTemplate.update(
                "UPDATE sequence_table SET seq = seq + 1 WHERE prefix = ? AND date = ?",
                prefix, today);

        Long seq;
        if (updated == 0) {
            jdbcTemplate.update(
                    "INSERT INTO sequence_table (prefix, date, seq) VALUES (?, ?, 1)",
                    prefix, today);
            seq = 1L;
        } else {
            seq = jdbcTemplate.queryForObject(
                    "SELECT seq FROM sequence_table WHERE prefix = ? AND date = ?",
                    Long.class,
                    prefix, today);
            if (seq == null)
                seq = 1L;
        }

        return String.format("%s-%s-%05d", prefix, today, seq);
    }
}