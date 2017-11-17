package org.opencms.main;

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
