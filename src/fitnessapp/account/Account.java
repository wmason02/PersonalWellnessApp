package fitnessapp.account;

import fitnessapp.Person;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Represents a an object which contains an individualized username, password, and Person
 *  Manages files and serialization of the Person object
 */
public class Account implements Serializable {
    private String username;
    private String password;
    private Person person;

    private final static Logger logger = Logger.getLogger(Account.class.getName());

    private final static String FILE_EXTENSION = "str";
    private final static String ALPHA_NUMERIC_REGEX = "^(\\w|\\d)+$";

    /**
     * Create a new account with an empty Person
     * 
     * @param username an alpha numeric username
     * @param password a password
     * @throws AccountException
     */
    public Account(String username, String password) throws AccountException {
        if (!validateUsername(username)) {
            throw new InvalidUsernameAccountException(username);
        }
        if (password.isBlank()) {
            throw new AccountException("Invalid password! Password can not be blank.");
        }

        Path filePath = createFilePath(username);
        if (Files.exists(filePath)) {
            throw new AccountException("Account already exists!");
        }
        this.username = username;
        this.password = password;
        this.person = new Person();
    }
    
    /**
     *  Checks a provided username for special characters or spaces.
     *  If such are present, throws an exception; if not, returns said username
     * 
     * @param username provided by the user and that needs to be checked to ensure no special characters or spaces
     * 
     * @return username after being checked to ensure no special characters or spaces
     */
    private static boolean validateUsername(String username) {
        if (username.matches(ALPHA_NUMERIC_REGEX)) {
            return true;
        } else {
            logger.log(Level.WARNING, "Username cannot contain special characters or spaces: {0}", username);
            return false;
        }
    }

    /**
     * Get the file path of a user's account file
     * 
     * @param username the username of the account
     * @return file path of the user's account
     */
    private static Path createFilePath(String username) {
        return Path.of("accounts", username + "." + FILE_EXTENSION);
    }

    /**
     * Serializes the account with a filename based on the provided username
     * @throws IOException
     */
    public void save() throws IOException {
        Path filePath = createFilePath(username);
        Files.createDirectories(filePath.getParent()); // Create accounts directory

        try (OutputStream fileOut = Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
            logger.log(Level.INFO, "The account for \"{0}\" has been serialized to {1}. Account database has been updated", new Object[]{username, filePath});
        }
    }

    /** 
     * @return password of an Account
     */
    public String getPassword(){
        return password;
    }

    /**
     *  Retrieves an Account after it has been serialized
     * 
     * @param username of the Account in search of 
     * @param password of the Account in search of
     * 
     * @return Account after it has been verified to match the provided username and password
     * @throws InvalidUsernameAccountException
     * @throws AccountException
     * @throws IOException
     */
    public static Account retrieveAccount(String username, String password) throws IOException, AccountException {
        if (!validateUsername(username)) {
            throw new InvalidUsernameAccountException(username);
        }
        Path filePath = createFilePath(username);
        try (InputStream fileIn = Files.newInputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.READ);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Account account = (Account) in.readObject();
            if (account.getPassword().equals(password)) {
                return account;
            } else {
                throw new AccountException("Incorrect password!");
            }
        } catch (ClassNotFoundException e) {
            throw new AccountException("Account file contained unknown classes! Possibly outdated?");
        }
    }

    /**
     * @return username of the Account
     */
    public String getUserName() {
        return username;
    }

    /**
     * Set the person of this account
     * @param person the person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return person of the Account
     */
    public Person getPerson() {
        return person;
    }
}