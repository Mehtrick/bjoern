package de.mehtrick.bjoern.base;

/**
 * Abstract class to annotate that this class contains a  {@link BjoernGeneratorConfig}
 */
public abstract class BjoernGeneratorConfigProvided {

	public BjoernGeneratorConfig bjoernGeneratorConfig;

	public BjoernGeneratorConfigProvided(BjoernGeneratorConfig bjoernGeneratorConfig) {
		this.bjoernGeneratorConfig = bjoernGeneratorConfig;
	}


}
