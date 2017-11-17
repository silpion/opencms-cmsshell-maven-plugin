package org.opencms.main;

/**
 * @author brandt
 */
public class CmsSystemInfoWrapper {
    private CmsSystemInfo instance;

    public CmsSystemInfoWrapper(CmsSystemInfo instance) {
        this.instance = instance;
    }

    public void init(CmsServletContainerSettings settings) {
        instance.init(settings);
    }

    public String getConfigurationFileRfsPath() {
        return instance.getConfigurationFileRfsPath();
    }
}
