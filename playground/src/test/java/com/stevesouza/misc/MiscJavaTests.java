package com.stevesouza.misc;

import org.junit.Test;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class MiscJavaTests {

    @Test
    public void testClassMethods() {
        assertThat(MiscJavaTests.class.getSimpleName()).isEqualTo("MiscJavaTests");
        assertThat(MiscJavaTests.class.getName()).isEqualTo("com.stevesouza.misc.MiscJavaTests");

    }
    
    @Test
    public void testMessageFormatBasic() {
        String message = MessageFormat.format("hello world this is {0} {1}", "steve", "souza");
        assertThat(message).isEqualTo("hello world this is steve souza");
    }
    
    @Test
    public void testMessageFormatDate() {
        String message = MessageFormat.format("{0} | {0, date} | {0, date, short} | {0, date, medium} |  {0, date, long} |  {0, date, full}", new Date());
        System.out.println("   messageformat for dates: "+message);
        assertThat(message).isNotEmpty();
    }

    @Test
    public void testMessageFormatTime() {
        String message = MessageFormat.format("{0} | {0, time} | {0, time, short} | {0, time, medium} |  {0, time, long} |  {0, time, full}", new Date());
        System.out.println("   messageformat for times: "+message);
        assertThat(message).isNotEmpty();
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