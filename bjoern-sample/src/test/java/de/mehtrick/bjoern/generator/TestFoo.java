package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.sample.AbstractTestFoo;
import de.mehtrick.bjoern.sample.Bar;
import de.mehtrick.bjoern.sample.Foo;

import static org.junit.Assert.assertEquals;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */

public class TestFoo extends AbstractTestFoo {

	private Bar bar;
	private Foo foo;
	private String messageOfFoo;

	public TestFoo() {
	}

	@Override
	public void when_FooWantsToDrinkBottleOfBeer(String param1) {
		messageOfFoo = foo.drinkBeerOfBar(bar.getBottlesOfBeer());
	}

	@Override
	public void given_ABar() {
		setBar(new Bar());
	}

	@Override
	public void given_ThereAreBottlesOfBeer(String param1) {
		bar.setBottlesOfBeer(Integer.valueOf(param1));
	}

	@Override
	public void given_AFoo() {
		setFoo(new Foo());
	}

	@Override
	public void given_ThereAreBottlesOfWine(String param1) {
		bar.setBottlesOfWine(Integer.valueOf(param1));

	}

	@Override
	public void then_FooSays(String param1) {
		assertEquals(param1, messageOfFoo);

	}

	public Bar getBar() {
		return this.bar;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public String getMessageOfFoo() {
		return this.messageOfFoo;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}


	public String toString() {
		return "TestFoo(bar=" + this.getBar() + ", foo=" + this.getFoo() + ", messageOfFoo=" + this.getMessageOfFoo() + ")";
	}

}