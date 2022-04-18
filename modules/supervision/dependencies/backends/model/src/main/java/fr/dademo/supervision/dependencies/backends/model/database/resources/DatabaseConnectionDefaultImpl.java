/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.InetAddress;
import java.util.Date;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseConnectionDefaultImpl implements DatabaseConnection {

    @Nullable
    private DatabaseConnectionState connectionState;

    @Nullable
    @Min(1)
    private Long connectionPID;

    @Nullable
    @Size(min = 1, max = 255)
    private String connectedDatabaseName;

    @Nullable
    @Size(min = 1, max = 255)
    private String userName;

    @Nullable
    @Size(min = 1, max = 255)
    private String applicationName;

    @Nullable
    private InetAddress clientAddress;

    @Nullable
    @Size(min = 1, max = 255)
    private String clientHostName;

    @Nullable
    @Min(1)
    private Long clientPort;

    @Nullable
    private Date connectionStart;

    @Nullable
    private Date transactionStart;

    @Nullable
    private Date lastQueryStart;

    @Nullable
    private Date lastStateChange;

    @Nullable
    @Size(min = 1)
    private String waitEventType;

    @Nullable
    @Size(min = 1)
    private String waitEventName;

    @Nullable
    @Size(min = 1)
    private String lastQuery;

    @Nullable
    @Size(min = 1)
    private String backendTypeName;
}
