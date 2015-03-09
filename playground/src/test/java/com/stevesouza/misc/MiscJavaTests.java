package com.stevesouza.misc;

import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;


public class MiscJavaTests {

    @Test
    public void testClassMethods() {
        assertThat(MiscJavaTests.class.getSimpleName()).isEqualTo("MiscJavaTests");
        assertThat(MiscJavaTests.class.getName()).isEqualTo("com.stevesouza.misc.MiscJavaTests");

    }


    @Test
    public void testVarArgs() {
        assertThat(varArgsMethod("Steve ", "Souza")).isEqualTo("Steve Souza");
    }

    @Test
    public void testUri() throws Exception {
        URI uri = new URI("file://myfile.dat");
        assertThat(uri.getScheme()).isEqualTo("file");
        assertThat(uri.getSchemeSpecificPart()).isEqualTo("//myfile.dat");
        uri = new URI("file:///mydir/myfile.dat");
        assertThat(uri.getSchemeSpecificPart()).isEqualTo("///mydir/myfile.dat");
    }

    private static String varArgsMethod(String... args) {
        String retValue = "";
        for (String str : args) {
            retValue+=str;
        }

        return retValue;
    }


}