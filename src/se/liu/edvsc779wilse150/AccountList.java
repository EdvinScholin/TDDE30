package se.liu.edvsc779wilse150;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AccountList
{
    private List<EncryptedAccount> listOfEncryptedAccounts = new ArrayList<>();
    private static final String FILE_NAME = "./EncryptedAccounts";

    public void addAccount(EncryptedAccount encryptedAccount) throws FileNotFoundException {
        listOfEncryptedAccounts.add(encryptedAccount);
        saveOnFile();
    }

    public void saveOnFile() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            gson.toJson(this, printWriter);
        }
    }
}
