package com.github.krystianmuchla.home.db.changelog;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.function.Function;

import com.github.krystianmuchla.home.Dao;
import com.github.krystianmuchla.home.InstantFactory;

import lombok.SneakyThrows;

public class ChangelogDao extends Dao {
    public static final ChangelogDao INSTANCE = new ChangelogDao();

    public boolean hasChangelog() {
        final var result = executeQuery("SHOW TABLES LIKE ?", stringMapper(), "changelog");
        return boolResult(result.size());
    }

    public void createChangelog() {
        executeUpdate("CREATE TABLE changelog (id INT PRIMARY KEY, executionTime TIMESTAMP(3) NOT NULL)");
    }

    public void addToChangelog(final int changeId) {
        final Instant time = InstantFactory.create();
        executeUpdate("INSERT INTO changelog VALUES (?, ?)", changeId, timestamp(time).toString());
    }

    public Integer getLastChangeId() {
        final var result = executeQuery("SELECT id FROM changelog ORDER BY id DESC LIMIT 1", intMapper());
        return singleResult(result);
    }

    private Function<ResultSet, String> stringMapper() {
        return new Function<>() {
            @Override
            @SneakyThrows
            public String apply(final ResultSet resultSet) {
                return resultSet.getString(1);
            }
        };
    }

    private Function<ResultSet, Integer> intMapper() {
        return new Function<>() {
            @Override
            @SneakyThrows
            public Integer apply(final ResultSet resultSet) {
                return resultSet.getInt(1);
            }
        };
    }
}
