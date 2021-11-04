import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class CredentialManager {
    private HashMap<String, String> credentials;
    private String key = null;
    private File file = null;

    public CredentialManager(String key, File file) {
        this.key = key;
        this.file = file;
        try {
            loadCredentials();
        } catch (IOException ignored){
            credentials = new HashMap<>();
        }
    }

    private void loadCredentials() throws IOException {
        credentials = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
            stream.forEach((line) -> {
                String[] cred = line.split(":");
                if (cred.length==2) credentials.put(cred[0], cred[1]);
            });
        }
    }

    public boolean userExists(String username){
        // WILL REQUIRE DECRYPTION IN FUTURE VERSION
        return credentials.containsKey(username);
    }

    public boolean login(String username, String password){
        // WILL REQUIRE DECRYPTION IN FUTURE VERSION
        if (!userExists(username)) return false;
        return Objects.equals(password, credentials.get(username));
    }

    public boolean createUser(String username, String password) {
        // WILL REQUIRE ENCRYPTION IN FUTURE VERSION
        if (userExists(username)) return false;
        try {
            append(username+":"+password);
            loadCredentials();
        }
        catch (IOException e) {
            return false;
        }
        return credentials.containsKey(username);
    }

    private void append(String text) throws IOException {
        FileWriter fw = new FileWriter(String.valueOf(file), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.newLine();
        bw.close();
    }

    private String encrypt(String text){

    }

    private String decrypt(String cipher){

    }
}
