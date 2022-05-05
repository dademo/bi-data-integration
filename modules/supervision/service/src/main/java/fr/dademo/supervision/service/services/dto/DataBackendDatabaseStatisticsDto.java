/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.dto;

import lombok.*;

import java.util.Date;

/**
 * @author dademo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataBackendDatabaseStatisticsDto {

    // Primary key
    private Long id;

    // Primary values
    private Date timestamp;
    private Long memoryUsageBytes;
    private Long cpuUsageMilliCPU;
    private Long commitCounts;
    private Long rollbacksCounts;
    private Long bufferBlocksRead;
    private Long diskBlocksRead;
    private Long returnedRowsCount;
    private Long fetchedRowsCount;
    private Long insertedRowsCount;
    private Long updatedRowsCount;
    private Long deletedRowsCount;
    private Long conflictsCount;
    private Long deadlocksCount;
    private Long readTimeMilliseconds;
    private Long writeTimeMilliseconds;
}