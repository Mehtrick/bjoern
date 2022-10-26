package de.mehtrick.bjoern.parser.reader;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BjoernFileExtensionsTest {

    @Test
    public void testValidExtensions(){
        //When
        String[] extensionValues = BjoernFileExtensions.getValuesAsString();

        //Then
        assertThat(extensionValues.length).isEqualTo(1);
        assertThat(extensionValues[0]).isEqualToIgnoringCase("zgr");
    }
}
