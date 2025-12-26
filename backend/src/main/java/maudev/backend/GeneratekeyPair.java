package maudev.backend;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GeneratekeyPair {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        var keyPair = keyPairGenerator.generateKeyPair();
        byte[] pub =  keyPair.getPublic().getEncoded();
        byte[] priv =  keyPair.getPrivate().getEncoded();

        PemWriter pemwriter = new PemWriter(new OutputStreamWriter(new FileOutputStream("pub.pem")));
        PemObject pemObject = new PemObject("PUBLIC KEY", pub);
        pemwriter.writeObject(pemObject);
        pemwriter.close();

        PemWriter pemwriter2 = new PemWriter(new OutputStreamWriter(new FileOutputStream("pri.pem")));
        PemObject pemObject2 = new PemObject("PRIVATE KEY", priv);
        pemwriter2.writeObject(pemObject2);
        pemwriter2.close();
    }
}
