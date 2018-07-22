package de.mehtrick.jvior.generator;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import de.mehtrick.jvior.generator.builder.JviorAbstractTestMethodBuilder;
import de.mehtrick.jvior.generator.builder.JviorFeatureTestClassBuilder;
import de.mehtrick.jvior.generator.builder.JviorScenarioTestMethodBuilder;
import de.mehtrick.jvior.parser.modell.Jvior;

class JviorCodeGenerator {

	public static void generate(JviorGeneratorConfig config, Jvior jvior) throws IOException {
		Set<MethodSpec> abstractMethods = JviorAbstractTestMethodBuilder.build(jvior.getScenarios());
		List<MethodSpec> scenarios = JviorScenarioTestMethodBuilder.build(jvior);
		TypeSpec jviorClass = JviorFeatureTestClassBuilder.build(jvior, scenarios, abstractMethods);
		writeToSystem(config, jviorClass);
	}

	private static void writeToSystem(JviorGeneratorConfig config, TypeSpec jviorClass) throws IOException {
		JavaFile javaFile = JavaFile.builder(config.getPckg(), jviorClass).build();
		javaFile.writeTo(System.out);
	}

}
