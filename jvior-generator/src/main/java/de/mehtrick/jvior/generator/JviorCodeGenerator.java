package de.mehtrick.jvior.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.generator.builder.JviorAbstractTestMethodBuilder;
import de.mehtrick.jvior.generator.builder.JviorFeatureTestClassBuilder;
import de.mehtrick.jvior.generator.builder.JviorScenarioTestMethodBuilder;
import de.mehtrick.jvior.parser.modell.Jvior;

class JviorCodeGenerator {

	public void generate(Jvior jvior) throws IOException {
		System.out.println("Generate Feature: " + jvior.getFeatureNameFormatted());
		Set<MethodSpec> abstractMethods = JviorAbstractTestMethodBuilder.build(jvior.getBackground(),
				jvior.getScenarios());
		List<MethodSpec> scenarios = JviorScenarioTestMethodBuilder.build(jvior);
		TypeSpec jviorClass = JviorFeatureTestClassBuilder.build(jvior, scenarios, abstractMethods);
		writeToSystem(jviorClass);
	}

	private void writeToSystem(TypeSpec jviorClass) throws IOException {
		JavaFile javaFile = JavaFile.builder(JviorGeneratorConfig.getPckg(), jviorClass).build();
		File dir = new File(JviorGeneratorConfig.getGendir());
		dir.mkdirs();
		javaFile.writeTo(dir);
	}

}
