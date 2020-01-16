package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.generator.junitsupport.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.generator.junitsupport.SupportedJunitVersion;
import org.apache.commons.lang3.StringUtils;

/**
 * Custom config file for the bjoern generation
 * A custom file is used so that custom validations can be applied
 */
public class BjoernCodeGeneratorConfig extends BjoernGeneratorConfig {

    private final String PROPERTY_JUNIT_VERSION = "junitVersion";
    private SupportedJunitVersion junitVersion = SupportedJunitVersion.junit4;

    public BjoernCodeGeneratorConfig(String[] args) throws NotSupportedJunitVersionException, BjoernMissingPropertyException {
        super(args);
        setJunitVersion(findPropertyInArgs(PROPERTY_JUNIT_VERSION, args));
    }

    public BjoernCodeGeneratorConfig() {
        super();
    }

    @Override
    public void validate() throws BjoernMissingPropertyException {
        super.validate();
        if (StringUtils.isAllBlank(getPckg())) {
            throw new BjoernMissingPropertyException(
                    "Please configure the package name by setting the \"pckg\" property");
        }
        if (StringUtils.isAllBlank(getGendir())) {
            throw new BjoernMissingPropertyException("Please configure the gendir where the classes will be generated");
        }
    }

    public SupportedJunitVersion getJunitVersion() {
        return junitVersion;
    }

    public void setJunitVersion(String junitVersion) throws NotSupportedJunitVersionException {
        if (StringUtils.isNotBlank(junitVersion)) {
            this.junitVersion = SupportedJunitVersion.getByVersionnumber(junitVersion);
        }
    }
}
