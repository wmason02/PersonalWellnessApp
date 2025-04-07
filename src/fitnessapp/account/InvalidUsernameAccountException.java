package fitnessapp.account;

public class InvalidUsernameAccountException extends AccountException {

    private final String username;

    public InvalidUsernameAccountException(String username) {
        super("Username cannot contain special characters or spaces.");
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
}
