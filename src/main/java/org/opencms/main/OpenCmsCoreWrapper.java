package org.opencms.main;

/*-
 * #%L
 * OpenCms CmsShell Maven Plugin
 * %%
 * Copyright (C) 2017 - 2018 Silpion IT-Solutions GmbH (https://www.silpion.de/)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.opencms.configuration.CmsParameterConfiguration;
import org.opencms.db.CmsDefaultUsers;
import org.opencms.file.CmsObject;

/**
 * @author brandt
 */
public class OpenCmsCoreWrapper {
    private OpenCmsCore instance;

    public OpenCmsCoreWrapper(OpenCmsCore instance) {
        this.instance = instance;
    }

    public static OpenCmsCoreWrapper getInstance() {
        return new OpenCmsCoreWrapper(OpenCmsCore.getInstance());
    }

    public CmsSystemInfoWrapper getSystemInfo() {
        return new CmsSystemInfoWrapper(instance.getSystemInfo());
    }

    public OpenCmsCoreWrapper upgradeRunlevel(CmsParameterConfiguration configuration) {
        return new OpenCmsCoreWrapper(instance.upgradeRunlevel(configuration));
    }

    public CmsDefaultUsers getDefaultUsers() {
        return instance.getDefaultUsers();
    }

    public CmsObject initCmsObject(String user) throws CmsException {
        return instance.initCmsObject(user);
    }

    public void shutDown() {
        instance.shutDown();
    }
}
