package de.mehtrick.bjoern.parser.reader;

import de.mehtrick.bjoern.parser.validator.BjoernValidatorException;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class BjoernZGRReaderTest {

    @Test(expected = BjoernFileExtensionInvalidException.class)
    public void testFileExtensionInvalid() {
        //when
        new BjoernZGRReader().readSpec("notfoundpath.yml", Charset.defaultCharset());
    }

    @Test(expected = BjoernFileExtensionInvalidException.class)
    public void testFileExtensionInvalid2() {
        //when
        new BjoernZGRReader().readSpec("notfoundpath.zegr", Charset.defaultCharset());
    }

    @Test
    public void testFileNotfound() {
        try{
            new BjoernZGRReader().readSpec("notfoundpath.zgr", Charset.defaultCharset());
            fail("A BjoernZGRREaderException should have been thrown");
        } catch (BjoernZGRReaderException e){
            assertThat(e.getCause().getMessage()).isEqualTo("No file found under the path notfoundpath.zgr");
        }
    }

    @Test(expected = BjoernValidatorException.class)
    public void notValidBjoernFormat(){
        new BjoernZGRReader().readSpec("src/test/resources/broken.zgr", Charset.defaultCharset());
    }

}
