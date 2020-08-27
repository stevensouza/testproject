package com.stevesouza.pentaho;

import org.junit.Test;
import org.pentaho.di.core.KettleClientEnvironment;
import org.pentaho.di.core.encryption.Encr;
import org.pentaho.di.core.exception.KettleException;

import static org.assertj.core.api.Assertions.assertThat;

public class PentahoEncryptTest {
    @Test
    public void testPentahoEncryptDecrypt() throws KettleException {
        KettleClientEnvironment.init();
        String password = "hi # mom";
        String encryptedPassword = Encr.encryptPassword(password);
        String decryptedPassword = Encr.decryptPasswordOptionallyEncrypted("Encrypted "+encryptedPassword);
        assertThat(decryptedPassword).isEqualTo(password);
        System.out.println("before encryption: "+password+", encrypted:"+encryptedPassword+", after decryption: "+decryptedPassword);
    }
}
