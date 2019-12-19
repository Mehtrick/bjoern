package de.mehtrick.bjoern.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernGeneratorConfigProvided;
import de.mehtrick.bjoern.generator.builder.BjoernFeatureTestClassBuilder;
import de.mehtrick.bjoern.parser.modell.Bjoern;

import java.io.File;
import java.io.IOException;

class BjoernCodeGenerator extends BjoernGeneratorConfigProvided {

    public BjoernCodeGenerator(BjoernGeneratorConfig bjoernGeneratorConfig) {
        super(bjoernGeneratorConfig);
    }


    /**
     * Generates the junit bjoern class but does not write it to the system
     *
     * @param bjoern the bjoern input modell
     * @return the generated junit class in form of {@link com.squareup.javapoet.JavaFile}
     */
    public JavaFile generateTransient(Bjoern bjoern) {
        System.out.println("Generate Feature: " + bjoern.getFeatureNameFormatted());
        TypeSpec typeSpec = new BjoernFeatureTestClassBuilder(bjoernGeneratorConfig).build(bjoern);
        return JavaFile.builder(bjoernGeneratorConfig.getPckg(), typeSpec).build();
    }


    public void generateAndWriteToSystem(Bjoern bjoern) throws IOException {
        JavaFile generatedBjoernFile = generateTransient(bjoern);
        writeToSystem(generatedBjoernFile);
    }

    private void writeToSystem(JavaFile generatedBjoernFile) throws IOException {
        File dir = new File(bjoernGeneratorConfig.getGendir());
        dir.mkdirs();
        generatedBjoernFile.writeTo(dir);
    }

}
