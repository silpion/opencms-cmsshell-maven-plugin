Maven Wrapper Plugin für OpenCms-Shell
======================================

Common Configuration
------------------------

    <plugin>
        <groupId>de.silpion.opencms.maven.plugins</groupId>
        <artifactId>opencms-cmsshell-maven-plugin</artifactId>
        <version>1.0</version>
        <configuration>
            <username>Admin</username>
            <password>admin</password>
            <webInfPath>/opt/apache-tomcat-8.5.3/webapps/opencms/WEB-INF/</webInfPath>
            
            <!-- default parameter -->
            <servletMapping>opencms</servletMapping>
            <defaultWebappName>opencms</defaultWebappName>
            <verbose>false</verbose>
        </configuration>
    </plugin>

Goals
-----

### importResources

    <plugin>
        [...]
        <executions>
            <execution>
                <id>import</id>
                <goals>
                    <goal>importResources</goal>
                </goals>
                <configuration>
                    <artifacts>
                        <artifact>
                            <groupId>de.mnbn.opencms.content</groupId>
                            <artifactId>initial</artifactId>
                            <version>1.0.0</version>
                            <type>zip</type>
                        </artifact>
                    </artifacts>
                    
                    <!-- or local files -->
                    <files>
                        <file>
                            <importFile>${project.xxx}/de.mnbn.opencms.content_0.1.zip</importFile>
                        </file>
                    </files>
                    
                    <!-- default parameter -->
                    <publishAfter>true</publishAfter>
                </configuration>
            </execution>
        </executions>
    </plugin>

### deleteResource

    <plugin>
        [...]
        <executions>
            <execution>
                <id>delete</id>
                <goals>
                    <goal>deleteResource</goal>
                </goals>
                <configuration>
                    <resourceNames>
                        <resourceName>/sites/test</resourceName>
                    </resourceNames>
                    
                    <!-- default parameter -->
                    <publishAfter>true</publishAfter>
                </configuration>
            </execution>
        </executions>
    </plugin>

### replaceModule

like _importResources_, but without _publishAfter_