import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static String masterKey = null;
    private static File file = new File("./resources/shadow");
    private static String username = null;
    private static String password = null;
    private static boolean loggedIn = false;
    private static boolean exit = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        masterKey = args.length > 0 ? args[0] : getInput("Please enter your master key: ", "\n\n");
        mainMenu();
    }

    private static void mainMenu(){
        while (!exit) {
            String response = getInput("Please choose an option:\n1. Login\n2. Create Account\n3. Exit\n\n>>> ", "\n\n");
            switch (response) {
                case "1":
                    loginMenu();
                    break;
                case "2":
                    createUserMenu();
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    unknownOption();
            }
        }
    }

    private static void loginMenu(){
        String user = getInput("Please enter your username: ", "");
        String pass = getInput("Please enter your password: ", "\n\n");
        CredentialManager m = new CredentialManager(masterKey, file);
        if (!m.login(user, pass)){
            getInput("Invalid login username and/or password!\nPress enter to continue...", "\n\n");
            return;
        }
        loggedIn = true;
        username = user;
        password = pass;
        loggedInMenu();
    }

    private static void createUserMenu(){
        String username = getInput("Please enter a new username: ", "");
        String password = getInput("Please enter a password: ", "\n\n");
        CredentialManager m = new CredentialManager(masterKey, file);
        if (m.createUser(username,password)) getInput("User has been created!\nPress enter to continue...", "\n\n");
        else getInput("Error: Username already exists!\nPress enter to continue...", "\n\n");
    }

    private static void loggedInMenu(){
        while (loggedIn){
            String response = getInput("Welcome "+username+"! Please choose an option\n1. Logout\n\n>>> ", "\n\n");
            switch (response) {
                case "1":
                    loggedIn = false;
                    username = null;
                    password = null;
                    break;
                default:
                    unknownOption();
            }
        }

    }

    private static void unknownOption(){
        System.out.print("Uh-oh! That was an invalid option. Lets try again...\n");
        getInput("Press enter to continue...", "\n\n");
    }

    private static String getInput(String prompt, String padding) {
        System.out.print(prompt);
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.print(padding);
        return s;
    }
}
