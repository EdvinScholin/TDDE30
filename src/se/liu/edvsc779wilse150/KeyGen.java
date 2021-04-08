package se.liu.edvsc779wilse150;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class KeyGen
{
    private KeyGenerator kg;

    {
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Key generateKey() {
        return kg.generateKey();
    }
}
