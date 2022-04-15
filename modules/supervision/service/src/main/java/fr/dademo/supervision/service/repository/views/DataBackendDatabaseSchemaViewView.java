/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.repository.views;

import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author dademo
 */
@Getter
@Builder
@AllArgsConstructor
public class DataBackendDatabaseSchemaViewView {

    private final DataBackendDatabaseSchemaViewEntity dataBackendDatabaseSchemaViewEntity;

    private final Long dataBackendDatabaseSchemaId;

    private final Long statisticsCount;
}