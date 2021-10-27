package fr.dademo.bi.companies.tools;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import java.util.Objects;

public class JooqExceptionTranslator extends DefaultExecuteListener {

    @Override
    public void exception(ExecuteContext context) {

        SQLDialect dialect = context.configuration().dialect();

        SQLExceptionTranslator translator
                = new SQLErrorCodeSQLExceptionTranslator(dialect.name());

        context.exception(translator
                .translate(
                        "Access database using Jooq",
                        context.sql(),
                        Objects.requireNonNull(context.sqlException())
                ));
    }
}
