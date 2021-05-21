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
	String xy = reader.nextString();
	String[] parts = xy.split(";");
	String username = parts[0];
	byte[] password = gson.fromJson(parts[1], byte[].class);
	byte[] initVector = gson.fromJson(parts[2], byte[].class);
	String accountType = parts[3];

	Account account = null;
	switch (accountType) {
	    case STANDARD -> account = new StandardAccount(username, password, initVector, AccountType.STANDARD);
	    case EMAIL -> account = new EmailAccount(username, password, initVector, AccountType.EMAIL);
	    case BANK -> account = new BankAccount(username, password, initVector, AccountType.BANK);
	}
	return account;
    }

    public void write(JsonWriter writer, Account account) throws IOException {
	if (account == null) {
	    writer.nullValue();
	    return;
	}
	Gson gson = new Gson();

	String xy = account.getUsername()+ ";" + gson.toJson(account.getPassword()) + ";" + gson.toJson(account.getInitVector()) + ";" + account.getAccountType();
	writer.value(xy);
    }
}
