package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import de.mehtrick.bjoern.generator.junitsupport.SupportedJunitVersion;
import de.mehtrick.bjoern.parser.modell.BjoernBackground;

import javax.lang.model.element.Modifier;

public class BjoernBackgroundTestBuilder {

	/**
	 * creates the background part of a test. In junit this is comparable to {@link org.junit.Before} or {@link org.junit.jupiter.api.BeforeEach}
	 *
	 * @param background
	 * @param junitVersion
	 * @return e.g.
	 * /
	 * <code>
	 * @Before public void background() {
	 * given_AFoo();
	 * given_ABar();
	 * }
	 * </code>
	 */
	public static MethodSpec build(BjoernBackground background, SupportedJunitVersion junitVersion) {

		Builder backgroundMethodBuilder = MethodSpec.methodBuilder("background").addAnnotation(junitVersion.getBeforeAnnotationClass())
				.addModifiers(Modifier.PUBLIC).addException(Exception.class);

		background.getGiven()
				.forEach(given -> backgroundMethodBuilder.addStatement(BjoernStatementParser.createMethodCallOutOfStatemet(given)));
		return backgroundMethodBuilder.build();
	}
}
