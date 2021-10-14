package fr.dademo.bi.companies.tools;

import lombok.SneakyThrows;
import org.jooq.SQLDialect;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Optional;

public class DatabaseSQLDialectProvider {

    private final DataSource dataSource;
    private SQLDialect cachedSqlDialect = null;

    public DatabaseSQLDialectProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SQLDialect get() {

        return Optional.ofNullable(cachedSqlDialect)
                .orElseGet(this::getAndCacheSqlDialect);
    }

    // https://stackoverflow.com/questions/9320200/inline-blob-binary-data-types-in-sql-jdbc/58736912#58736912
    @SneakyThrows
    private SQLDialect getAndCacheSqlDialect() {

        try (var connection = dataSource.getConnection()) {
            final var databaseProductName = connection.getMetaData().getDatabaseProductName().toLowerCase();
            cachedSqlDialect = Arrays.stream(SQLDialect.values())
                    .filter(v -> !v.getNameLC().isBlank())
                    .filter(v -> databaseProductName.startsWith(v.getNameLC()))
                    .findFirst().orElse(SQLDialect.DEFAULT);
            return cachedSqlDialect;
        }
    }
}
