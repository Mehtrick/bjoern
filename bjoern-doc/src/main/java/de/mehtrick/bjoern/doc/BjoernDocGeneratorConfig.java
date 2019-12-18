package de.mehtrick.bjoern.doc;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import org.apache.commons.lang3.StringUtils;

/**
 * Custom config file for the doc generation
 * A custom file is used so that custom validations can be applied
 */
public class BjoernDocGeneratorConfig extends BjoernGeneratorConfig {

    public BjoernDocGeneratorConfig(String[] args) throws NotSupportedJunitVersionException, BjoernMissingPropertyException {
        super(args);
    }

    public BjoernDocGeneratorConfig() {
        super();
    }

    @Override
    public void validate() throws BjoernMissingPropertyException {
        super.validate();
        if (StringUtils.isAllBlank(getDocdir())) {
            throw new BjoernMissingPropertyException("Please configure the docDir where the documentation will be generated");
        }
    }
}
