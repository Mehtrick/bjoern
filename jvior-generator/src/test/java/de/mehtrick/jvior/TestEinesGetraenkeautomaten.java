package de.mehtrick.jvior;

import org.junit.Test;

/**
 * Test eines Getraenkeautomaten
 */
public abstract class TestEinesGetraenkeautomaten {
	/**
	 * Getraenk nicht vorhanden
	 */
	@Test
	public void GetraenkNichtVorhanden() {
		given_EinAutomat();
		given_MitFlaschenCola("2");
		given_MitFlaschenSprite("0");
		when_KaufeSprite("1");
		then_AutomatSagt("alle");
		then_EsExistierenColaImAutomaten("2");
	}

	/**
	 * Getraenk vorhanden
	 */
	@Test
	public void GetraenkVorhanden() {
		given_EinAutomat();
		given_MitFlaschenCola("2");
		given_MitFlaschenSprite("0");
		when_KaufeCola("1");
		then_AutomatSagt("ok");
		then_EsExistierenColaImAutomaten("1");
	}

	public abstract void then_AutomatSagt(String param1);

	public abstract void when_KaufeSprite(String param1);

	public abstract void when_KaufeCola(String param1);

	public abstract void given_MitFlaschenCola(String param1);

	public abstract void then_EsExistierenColaImAutomaten(String param1);

	public abstract void given_EinAutomat();

	public abstract void given_MitFlaschenSprite(String param1);
}
