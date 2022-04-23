/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerDto;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerStatisticsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DatabaseReplicationPeerService {

    Page<DataBackendDatabaseReplicationPeerDto> findDatabaseReplicationPeersForDataBackend(@Nonnull @Min(1) Long dataBackendId,
                                                                                           @Nonnull Pageable pageable);

    Optional<DataBackendDatabaseReplicationPeerDto> findDatabaseReplicationPeerById(@Nonnull @Min(1) Long id);

    List<DataBackendDatabaseReplicationPeerStatisticsDto> findDatabaseReplicationPeerStatisticsBetween(
        @Nonnull @Min(1) Long id, @Nonnull Date from, @Nonnull Date to
    );

    Optional<DataBackendDatabaseReplicationPeerStatisticsDto> findLatestDatabaseReplicationPeerStatistics(@Nonnull @Min(1) Long id);

    Boolean existsById(@Nonnull @Min(1) Long id);
}
