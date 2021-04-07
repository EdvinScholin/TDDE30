package se.liu.edvsc779wilse150;

import java.util.ArrayList;
import java.util.List;

public class AccountList
{
    private List<Account> listOfAccounts = new ArrayList<>();
    private static final String FILE_NAME = "./Accounts";

    public void addAccount(Account account) {
        listOfAccounts.add(account);
    }

    public void saveOnFile() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            gson.toJson(this, printWriter);
        }
    }
}
