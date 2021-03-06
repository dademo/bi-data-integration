/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.exception;

/**
 * @author dademo
 */
public class NotADirectoryException extends RuntimeException {

    private static final long serialVersionUID = -1347099597070844462L;

    public NotADirectoryException(String path) {
        super(String.format("Path %s is not a directory", path));
    }
}
