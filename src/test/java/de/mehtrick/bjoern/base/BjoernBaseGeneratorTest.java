package de.mehtrick.bjoern.base;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class BjoernBaseGeneratorTest {


    @Test
    void folderNotFound() {
        //when
        File[] bjoernFiles = AbstractBjoernGenerator.getFilesFromFolder("someRandomFolder");
        //then
        Assertions.assertThat(bjoernFiles).isEmpty();
    }
}
