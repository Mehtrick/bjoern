package de.mehtrick.bjoern.base;

import org.apache.commons.lang3.StringUtils;

public class NotSupportedJunitVersionException extends Exception {

    public NotSupportedJunitVersionException() {
        super("The configured JUnitVersionnumber is not supported. Please choose one of the following: " + StringUtils.join(BjoernGeneratorConfig.SupportedJunitVersion.getSupportedVersionNumbers(), ","));
    }
}
