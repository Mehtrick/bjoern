package de.mehtrick.bjoern.generator;

import com.squareup.javapoet.JavaFile;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernGeneratorConfigProvided;
import de.mehtrick.bjoern.generator.builder.BjoernClassesToBuild;
import de.mehtrick.bjoern.generator.builder.BjoernFeatureTestClassBuilder;
import de.mehtrick.bjoern.parser.modell.Bjoern;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class BjoernCodeGenerator extends BjoernGeneratorConfigProvided {

    public BjoernCodeGenerator(BjoernGeneratorConfig bjoernGeneratorConfig) {
        super(bjoernGeneratorConfig);
    }


    /**
     * Generates the junit bjoern classes but does not write it to the system
     *
     * @param bjoern the bjoern input modell
     * @return the generated junit class in form of {@link com.squareup.javapoet.JavaFile}
     */
    public List<JavaFile> generateTransient(Bjoern bjoern) {
        System.out.println("Generate Feature: " + bjoern.getFeatureNameFormatted());
        BjoernClassesToBuild bjoernClasses = new BjoernFeatureTestClassBuilder(bjoernGeneratorConfig).build(bjoern);
        JavaFile featureClass = JavaFile.builder(bjoernGeneratorConfig.getPckg(), bjoernClasses.getFeatureClass()).build();
        JavaFile featureInterface = JavaFile.builder(bjoernGeneratorConfig.getPckg(), bjoernClasses.getFeatureInterface()).build();
        return Arrays.asList(featureClass, featureInterface);
    }

    public void generateAndWriteToSystem(Bjoern bjoern) throws IOException {
        List<JavaFile> generatedBjoernFiles = generateTransient(bjoern);
        generatedBjoernFiles.stream().forEach(this::writeToSystem);
    }

    private void writeToSystem(JavaFile generatedBjoernFile) {
        try {
            File dir = new File(bjoernGeneratorConfig.getGendir());
            dir.mkdirs();
            generatedBjoernFile.writeTo(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
