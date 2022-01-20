/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_connection_entity")
public class DataBackendDatabaseConnectionEntity implements Serializable {

    private static final long serialVersionUID = -6016932834303708425L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DATABASE")
    @ToString.Exclude
    private DataBackendGlobalDatabaseDescriptionEntity globalDatabase;

    @Nullable
    @Size(min = 1)
    private String connectionState;

    @Nullable
    @Min(1)
    private Long connectionPID;

    @Nullable
    @Size(min = 1)
    private String connectedDatabaseName;

    @Nullable
    @Size(min = 1)
    private String userName;

    @Nullable
    @Size(min = 1)
    private String applicationName;

    @Nullable
    private String clientAddress;

    @Nullable
    @Size(min = 1)
    private String clientHostname;

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