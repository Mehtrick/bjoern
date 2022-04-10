package de.mehtrick.bjoern.base;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

class BjoernBaseGeneratorTest {

    private static final Logger log = LoggerFactory.getLogger(BjoernBaseGeneratorTest.class);

    @Test
    void correctListFiles() {
        //when
        File[] bjoernFiles = AbstractBjoernGenerator.getFilesFromFolder("src/test/resources/AbstractBjoernGeneratorTest");
        //then
        Arrays.stream(bjoernFiles).forEach(file -> log.info(file.getName()));
        Assertions.assertThat(bjoernFiles).hasSize(1);
    }

    @Test
    void folderNotFound() {
        //when
        File[] bjoernFiles = AbstractBjoernGenerator.getFilesFromFolder("someRandomFolder");
        //then
        Assertions.assertThat(bjoernFiles).isEmpty();
    }
}
