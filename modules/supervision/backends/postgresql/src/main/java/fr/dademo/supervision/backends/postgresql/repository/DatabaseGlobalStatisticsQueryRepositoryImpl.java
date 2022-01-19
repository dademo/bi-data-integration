/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static fr.dademo.supervision.backends.postgresql.configuration.ModuleBeans.MODULE_JDBC_TEMPLATE_BEAN_NAME;

/**
 * @author dademo
 */
@Repository
public class DatabaseGlobalStatisticsQueryRepositoryImpl implements DatabaseGlobalStatisticsQueryRepository {

    private static final String QUERY = "SELECT PG_POSTMASTER_START_TIME()";

    @Qualifier(MODULE_JDBC_TEMPLATE_BEAN_NAME)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Date getDatabaseStartTime() {
        return jdbcTemplate.queryForObject(QUERY, new RowDateMapper());
    }

    private static class RowDateMapper implements RowMapper<Date> {

        @Override
        public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getTimestamp(1);
        }
    }
}
