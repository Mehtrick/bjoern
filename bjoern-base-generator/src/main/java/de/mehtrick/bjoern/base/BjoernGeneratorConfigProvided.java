package de.mehtrick.bjoern.base;

/**
 * Abstract class to annotate that this class contains a  {@link de.mehtrick.bjoern.base.BjoernGeneratorConfig}
 */
public abstract class BjoernGeneratorConfigProvided {

	public BjoernGeneratorConfig bjoernGeneratorConfig;

	public BjoernGeneratorConfigProvided(BjoernGeneratorConfig bjoernGeneratorConfig) {
		this.bjoernGeneratorConfig = bjoernGeneratorConfig;
	}


}
