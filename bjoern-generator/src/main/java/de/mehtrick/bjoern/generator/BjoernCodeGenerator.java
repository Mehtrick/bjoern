package de.mehtrick.bjoern.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernGeneratorConfigProvided;
import de.mehtrick.bjoern.generator.builder.BjoernAbstractTestMethodBuilder;
import de.mehtrick.bjoern.generator.builder.BjoernFeatureTestClassBuilder;
import de.mehtrick.bjoern.generator.builder.BjoernScenarioTestMethodBuilder;
import de.mehtrick.bjoern.parser.modell.Bjoern;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

class BjoernCodeGenerator extends BjoernGeneratorConfigProvided{

	public BjoernCodeGenerator(BjoernGeneratorConfig bjoernGeneratorConfig) {
		super(bjoernGeneratorConfig);
	}

	public void generate(Bjoern bjoern) throws IOException {
		System.out.println("Generate Feature: " + bjoern.getFeatureNameFormatted());
		Set<MethodSpec> abstractMethods = BjoernAbstractTestMethodBuilder.build(bjoern.getBackground(),
				bjoern.getScenarios());
		List<MethodSpec> scenarios = BjoernScenarioTestMethodBuilder.build(bjoern, bjoernGeneratorConfig.getJunitVersion());
		TypeSpec bjoernClass = new BjoernFeatureTestClassBuilder(bjoernGeneratorConfig).build(bjoern, scenarios, abstractMethods);
		writeToSystem(bjoernClass);
	}

	private void writeToSystem(TypeSpec bjoernClass) throws IOException {
		JavaFile javaFile = JavaFile.builder(bjoernGeneratorConfig.getPckg(), bjoernClass).build();
		File dir = new File(bjoernGeneratorConfig.getGendir());
		dir.mkdirs();
		javaFile.writeTo(dir);
	}

}
