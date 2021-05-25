package se.liu.password_manager.account_management;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * This class is used by the gson library and defines how gson is supposed to handle Account objects.
 */


public class AccountAdapter extends TypeAdapter<Account>
{
    public Account read(JsonReader reader) throws IOException {
	if (reader.peek() == JsonToken.NULL) {
	    reader.nextNull();
	    return null;
	}
	Gson gson = new Gson();
	String printerValue = reader.nextString();
	String[] parts = printerValue.split(";");
	String username = parts[0];
	byte[] password = gson.fromJson(parts[1], byte[].class);
	byte[] initVector = gson.fromJson(parts[2], byte[].class);
	AccountType accountType = AccountType.valueOf(parts[3]);

	String accountNumber = null;
	String email = null;
	String domain = null;
	if (parts.length == 5 && accountType.equals(AccountType.BANK)) {
	    accountNumber = parts[4];
	}
	else if (parts.length == 6 && accountType.equals(AccountType.EMAIL)) {
	    email = parts[4];
	    domain = parts[5];
	}

	Account account = null;
	switch (accountType) {
	    case STANDARD -> account = new StandardAccount(username, password, initVector, AccountType.STANDARD);
	    case EMAIL -> account = new EmailAccount(username, email, domain, password, initVector, AccountType.EMAIL);
	    case BANK -> account = new BankAccount(username, accountNumber, password, initVector, AccountType.BANK);
	}
	return account;
    }

    public void write(JsonWriter writer, Account account) throws IOException {
	if (account == null) {
	    writer.nullValue();
	    return;
	}
	String printerValue = null;
	Gson gson = new Gson();
	switch (account.getAccountType()) {
	    case STANDARD -> printerValue =
		    account.getUsername() + ";" + gson.toJson(account.getPassword()) + ";" + gson.toJson(account.getInitVector()) + ";" +
		    account.getAccountType();
	    case EMAIL -> printerValue =
		    account.getUsername()+ ";" + gson.toJson(account.getPassword()) + ";" + gson.toJson(account.getInitVector()) + ";" +
		    account.getAccountType() + ";" + ((EmailAccount)account).getEmail() + ";" +
		    ((EmailAccount)account).getDomain();
	    case BANK -> printerValue =
		    account.getUsername()+ ";" + gson.toJson(account.getPassword()) + ";" + gson.toJson(account.getInitVector()) + ";" +
		    account.getAccountType() + ";" + ((BankAccount)account).getBankAccountNumber();
	}

	writer.value(printerValue);
    }
}
