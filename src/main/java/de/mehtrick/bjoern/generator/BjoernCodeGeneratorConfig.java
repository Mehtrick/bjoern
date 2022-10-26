package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import org.apache.commons.lang3.StringUtils;

/**
 * Custom config file for the bjoern generation
 * A custom file is used so that custom validations can be applied
 */
public class BjoernCodeGeneratorConfig extends BjoernGeneratorConfig {

    public BjoernCodeGeneratorConfig(String[] args) throws NotSupportedJunitVersionException, BjoernMissingPropertyException {
        super(args);
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
}
