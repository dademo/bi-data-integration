/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.beans;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author dademo
 */
@Component
public class JooqExceptionTranslator extends DefaultExecuteListener {

    private static final long serialVersionUID = 2496937225729975461L;

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
