package de.mehtrick.bjoern.parser.reader;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class BjoernZGRReaderTest {

    @Test(expected = BjoernFileExtensionInvalidException.class)
    public void testFileExtensionInvalid() {
        //when
        BjoernZGRReader.readSpec("notfoundpath.yml");
    }

    @Test(expected = BjoernFileExtensionInvalidException.class)
    public void testFileExtensionInvalid2() {
        //when
        BjoernZGRReader.readSpec("notfoundpath.zegr");
    }

    @Test
    public void testFileNotfound() {
        try{
            BjoernZGRReader.readSpec("notfoundpath.zgr");
            fail("A BjoernZGRREaderException should have been thrown");
        } catch (BjoernZGRReaderException e){
            assertThat(e.getCause().getMessage()).isEqualTo("No file found under the path notfoundpath.zgr");
        }
    }

    @Test
    public void notValidBjoernFormat(){
        BjoernZGRReader.readSpec("src/test/resources/broken.zgr");
    }

}
