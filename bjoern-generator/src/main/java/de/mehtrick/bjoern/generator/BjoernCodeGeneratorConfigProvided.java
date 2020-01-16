package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;

/**
 * Abstract class to annotate that this class contains a  {@link BjoernGeneratorConfig}
 */
public abstract class BjoernCodeGeneratorConfigProvided {

    public BjoernCodeGeneratorConfig bjoernGeneratorConfig;

    public BjoernCodeGeneratorConfigProvided(BjoernCodeGeneratorConfig bjoernGeneratorConfig) {
        this.bjoernGeneratorConfig = bjoernGeneratorConfig;
    }


}
