package se.liu.edvsc779wilse150;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class KeyGen
{
    private KeyGenerator kg = KeyGenerator.getInstance("AES");

    public KeyGen() throws NoSuchAlgorithmException {
    }

    public Key getKey() {
        return kg.generateKey();
    }
}
