package de.mehtrick.bjoern.parser.reader;

import de.mehtrick.bjoern.parser.validator.BjoernValidatorException;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.*;

public class BjoernZGRReaderTest {

    @Test
    public void testFileExtensionInvalid() {
        //when
        assertThatExceptionOfType(BjoernFileExtensionInvalidException.class).isThrownBy(() -> {
            new BjoernZGRReader().readSpec("notfoundpath.yml", Charset.defaultCharset());
        });
    }

    @Test
    public void testFileExtensionInvalid2() {
        //when
        assertThatExceptionOfType(BjoernFileExtensionInvalidException.class).isThrownBy(() -> {
            new BjoernZGRReader().readSpec("notfoundpath.zegr", Charset.defaultCharset());
        });
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

    @Test
    public void notValidBjoernFormat() {
        //when
        assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
            new BjoernZGRReader().readSpec("src/test/resources/broken.zgr", Charset.defaultCharset());
        });
    }

}
