/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.vfs.configuration;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "vfs.authentication")
@Data
public class VFSConfiguration {

    private static final String TEMP_PREFIX = normalizedName(VFSConfiguration.class.getName());
    @Nullable
    private String tempResourcesPath = null;
    private boolean randomTempPath = true;
    @Nullable
    private String cacheResourcesPath = null;
    @Nullable
    private VFSAuthenticationConfiguration authentication;

    private static String normalizedName(String string) {
        return string.replace(File.separator, "_");
    }

    @SneakyThrows
    private static URI toURI(String uri) {
        return URI.create(uri);
    }

    @Nonnull
    @SneakyThrows
    public URI getTempResourcesPath() {

        return Optional.ofNullable(tempResourcesPath)
            .map(VFSConfiguration::toURI)
            .orElse(Files.createTempDirectory(TEMP_PREFIX).toUri());
    }

    @Nullable
    @SneakyThrows
    public URI getCacheResourcesPath() {

        return Optional.ofNullable(cacheResourcesPath)
            .map(VFSConfiguration::toURI)
            .orElse(URI.create(String.format(
                "%s/%s",
                SystemUtils.getUserHome(),
                ".cache/dev_cache"
            )));
    }

    @Data
    public static class VFSAuthenticationConfiguration {

        @Nullable
        private String domain;
        @Nullable
        private String username;
        @Nullable
        private String password;
    }
}
