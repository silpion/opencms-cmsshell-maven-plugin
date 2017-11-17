package org.opencms.main;

/**
 * @author brandt
 */
public class CmsServletContainerSettingsWrapper {
    private CmsServletContainerSettings instance;

    public CmsServletContainerSettingsWrapper(CmsServletContainerSettings instance) {
        this.instance = instance;
    }

    public static CmsServletContainerSettingsWrapper createInstance(String webInfRfsPath,
                                                                    String defaultWebApplication,
                                                                    String servletMapping,
                                                                    String servletContainerName,
                                                                    String webApplicationContext) {
        return new CmsServletContainerSettingsWrapper(
                new CmsServletContainerSettings(
                        webInfRfsPath,
                        defaultWebApplication,
                        servletMapping,
                        servletContainerName,
                        webApplicationContext));
    }

    public CmsServletContainerSettings getInstance() {
        return instance;
    }
}
