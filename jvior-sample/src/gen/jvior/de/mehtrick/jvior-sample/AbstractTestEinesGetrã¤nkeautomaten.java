package de.mehtrick.jvior-sample;

import java.lang.String;
import org.junit.Before;
import org.junit.Test;

/**
 * Test eines Getr√§nkeautomaten */
public abstract class AbstractTestEinesGetr„§nkeautomaten {
  @Before
  public void background() {
    given_EinAutomat();
    given_EinKassensystem();
  }

  /**
   * Getr√§nk nicht vorhanden */
  @Test
  public void Getr„§nkNichtVorhanden() {
    given_MitFlaschenColaUndFlaschenFanta("2", "3");
    given_MitFlaschenSprite("0");
    when_KaufeSprite("1");
    then_AutomatSagt("alle");
    then_EsExistierenColaImAutomaten("2");
  }

  /**
   * Getraenk vorhanden */
  @Test
  public void GetraenkVorhanden() {
    given_MitFlaschenCola("2");
    given_MitFlaschenSprite("0");
    when_KaufeCola("1");
    then_AutomatSagt("ok");
    then_EsExistierenColaImAutomaten("1");
  }

  public abstract void when_KaufeCola(String param1);

  public abstract void given_EinKassensystem();

  public abstract void given_MitFlaschenSprite(String param1);

  public abstract void given_MitFlaschenColaUndFlaschenFanta(String param1, String param2);

  public abstract void given_MitFlaschenCola(String param1);

  public abstract void when_KaufeSprite(String param1);

  public abstract void then_AutomatSagt(String param1);

  public abstract void then_EsExistierenColaImAutomaten(String param1);

  public abstract void given_EinAutomat();
}
