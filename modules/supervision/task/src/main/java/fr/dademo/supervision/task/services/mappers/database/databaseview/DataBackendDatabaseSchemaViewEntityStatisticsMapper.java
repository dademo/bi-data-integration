/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database.databaseview;

import fr.dademo.supervision.backends.model.database.resources.DatabaseView;
import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.entities.database.databaseview.DataBackendDatabaseSchemaViewStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaViewEntityStatisticsMapper {

    DataBackendDatabaseSchemaViewEntityStatisticsMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaViewEntityStatisticsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "view", ignore = true)
    @Mapping(source = "backendStateExecution", target = "backendStateExecution")
    DataBackendDatabaseSchemaViewStatisticsEntity toDataBackendDatabaseViewStatisticsEntity(
        DatabaseView source,
        DataBackendStateExecutionEntity backendStateExecution
    );
}