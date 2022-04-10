package de.mehtrick.bjoern.base;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class AbstractBjoernGeneratorTest {

    @Test
    void correctListFiles() {
        //when
        File[] bjoernFiles = AbstractBjoernGenerator.getFilesFromFolder("src/test/resources/AbstractBjoernGeneratorTest");
        //then
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
