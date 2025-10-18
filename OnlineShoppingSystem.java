import java.io.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] accounts = new String[3];
        int existingAccounts = 0;
        
        String savedEmail = null, savedPass = null, savedUsername = null;

        while (true) {
            System.out.println(String.join(System.lineSeparator(),
            "\n=== WELCOME! ===",
            "1 - Register",
            "2 - Login",
            "(Press Enter when empty to Exit Program)"
            ));

            String choice = null;
            try {
                choice = br.readLine();
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }

            if (choice == null || choice.trim().isEmpty()) {
                System.out.println("App Closed");
                return;
            }

            switch (choice.trim()) {
                case "1": //Register
                    if (existingAccounts >= 3) {
                        System.out.println("Registered Accounts limit reached!");
                        continue;
                    }
                    
                    
                    savedEmail = registerEmail(br); //<Call Function
                    if (savedEmail == null) continue;
                    
                    //Prevent registering the same emails
                    for (int i = 0; i < existingAccounts; i++) {
                        if (accounts[i].startsWith(savedEmail + ",")) {
                            System.out.println("This email already has an existing account!");
                            continue;
                        }
                    }
                    
                    savedPass = registerPassword(br); //<Call Function
                    if (savedPass == null) continue;
                    savedUsername = registerUsername(br); //<Call Function
                    if (savedUsername == null) continue;
                    
                    accounts[existingAccounts] = savedEmail + "," + savedPass + "," + savedUsername;
                    existingAccounts++;
                    
                    System.out.println("Account Registered Successfully!");
                    break;

                case "2": //Login
                    //If no registered account detected then return to Welcome Interface
                    if (savedEmail == null || savedPass == null) {
                        System.out.println("No registered account found — Please register first");
                        continue;
                    }
                    boolean loggedIn = login(br, accounts, existingAccounts); //<Call Function
                    if (loggedIn) {
                        shoppingMall(br, accounts, existingAccounts, savedEmail); //<Call Function
                    }
                    break;

                default:
                    System.out.println("Invalid Input!");
            }
        }
    }

    //Register Email
    public static String registerEmail(BufferedReader br) {
        while (true) {
            System.out.print("\n(Press Enter when empty to go back)\nRegister - Enter your Email:");
            try {
                String email = br.readLine();
                if (email == null || email.trim().isEmpty()) return null;
                if (!email.endsWith("@gmail.com") && !email.endsWith("@email.com")) {
                    System.out.println("Invalid Email Address!");
                    continue;
                }
                return email.trim(); //Register
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    //Register Password
    public static String registerPassword(BufferedReader br) {
        while (true) {
            System.out.print("\n(Press Enter when empty to go back)\nRegister - Enter your Password:");
            try {
                String pass = br.readLine();
                if (pass == null || pass.trim().isEmpty()) return null;
                if (pass.length() < 8) {
                    System.out.println("Password must at least have a minimum of 8 characters");
                    continue;
                }
                return pass; //Register Password
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    //Register Username
    public static String registerUsername(BufferedReader br) {
        while (true) {
            System.out.print("\nPlease set a username for your account:");
            try {
                String username = br.readLine();
                if (username == null || username.trim().isEmpty()) {
                    System.out.println("Username cannot be empty!");
                    continue;
                }
                return username.trim(); //Register Username
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    //Login
    public static boolean login(BufferedReader br, String[] accounts, int existingAccounts) {
        while (true) {
            System.out.print("\nLogin - Email (Press Enter when empty to go back): ");
            String email = null;
            try {
                email = br.readLine();
                if (email == null || email.trim().isEmpty()) return false;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }

            System.out.print("\nLogin - Password (Press Enter when empty to go back): ");
            String pass = null;
            try {
                pass = br.readLine();
                if (pass == null || pass.trim().isEmpty()) return false;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            for (int i = 0; i < existingAccounts; i++) {
                String[] parts = accounts[i].split(","); //Separate each string
                String partsEmail = parts[0];
                String partsPass = parts[1];
                String partsUsername = parts[2];
                
                if (email.equals(partsEmail) && pass.equals(partsPass)) {
                    System.out.println("Login successful! Welcome " + partsUsername);
                    return true;
                }
            }

            System.out.println("\nEmail or Password Error! Try Again\n(Press Enter when empty to go back");
        }
    }



    //Shopping Mall Interface
    public static void shoppingMall(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail) {
        String[] products = {
            "myPhone 17 Super Pro Ultra Max",
            "Samsinger Milkyway F25 Ultra",
            "50 Shades of Grey",
            "Skibidi Toilet Figurine Limited Edition",
            "Genshin Impact Acrylic Keychains"
        };
        int[] cost = {69420, 42069, 6000, 24500, 150};
        
        String[] productDescription = {
            "Introducing the new myPhone 17 Super Pro Ultra Max with the new 120hz Super Amoled Display that enhances colors vividly providing smooth scrolling on the go",
            "Introducing the new Samsinger Milkyway F25 Ultra packed with a new 1000mp telephoto camera lens capturing each detail for a pixel perfect photo",
            "Is a erotic romance novel by British Author E.L.James featuring explicitly erotic scenes featuring elements of sexual practices",
            "[Limited Stock Only!] Skibidi Skibi Toilet Skibidi Skibidi Toilet",
            "Genshin Impact custom made Acrylic Keychains — Available Characters: Venti, Zhongli, Raiden Shogun, Nahida, Furina, Mauvuika, Columbina, Flins, Varka, Capitano"
        };

        while (true) {
            System.out.println(String.join(System.lineSeparator(),
            "\n=== Shopping Mall ===",
            "(Type 'SEARCH' to open search box and search a product)",
            "(Type 'CART' to open shopping cart)",
            "(Type 'USERNAME' to edit username)",
            "(Type 'PASSWORD' to change password)",
            "(Type 'DELETE' to delete account",
            ""
            ));
            for (int i = 0; i < products.length; i++) {
                System.out.println(String.join(System.lineSeparator(),
                i + " - " + products[i],
                "₱" + cost[i]
                ));
            }
            System.out.print("(Press Enter when empty to go back): ");

            String input = null;
            try {
                input = br.readLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("\nReturning to login page...");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            input = input.trim();
            
            switch (input.toUpperCase()) { //Make string input commands not case sensitive
                case "SEARCH":
                    while (true) {
                        System.out.println("\nSearch a Product: ");
                        String searchbox = null;
                        try {
                            searchbox = br.readLine();
                            if (searchbox == null || searchbox.trim().isEmpty()) break;
                        } catch (IOException e) {
                            System.out.println("Error");
                            break;
                        }
                        
                        searchBoxSelection(br, input, searchbox, products, cost, productDescription);
                    }
                    continue;
                    
                case "USERNAME":
                    while (true) {
                        System.out.println("\n=== EDIT USERNAME ====");
                        try {
                            System.out.print("Enter New Username: ");
                            String newUsername = br.readLine();
                        
                            if (newUsername == null || newUsername.trim().isEmpty()) {
                                System.out.println("\nEdit Cancelled");
                                break;
                            }
                        
                            System.out.print("Enter Password: ");
                            String inputPassword = br.readLine();
                        
                            if (inputPassword == null || inputPassword.trim().isEmpty()) {
                                System.out.println("\nEdit Cancelled");
                                break;
                            }
                            
                            for (int i = 0; i < existingAccounts; i++) {
                                String[] parts = accounts[i].split(",");
                            
            //Check if it matches current email and password to ensure it overwrites username
            //of correct account
                                if (parts[0].equals(savedEmail) && parts[1].equals(inputPassword)) {
                                    accounts[i] = parts[0] + "," + parts[1] + "," + newUsername.trim();
                                    System.out.println("Saved! Username successfully updated");
                                    break;
                                } else {
                                    System.out.println("Password Error! Try Again");
                                    continue;
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                    continue;
                    
                case "PASSWORD":
                    while (true) {
                        System.out.println("\n=== CHANGE PASSWORD ====");
                        try {
                            System.out.print("Enter Current Password: ");
                            String inputPassword = br.readLine();
                        
                            if (inputPassword == null || inputPassword.isEmpty()) {
                                System.out.println("\nEdit Cancelled");
                                break;
                            }
                            
                            for (int i = 0; i < existingAccounts; i++) {
                                String[] parts = accounts[i].split(",");
                            
            //Check if it matches current email and password to ensure it overwrites password
            //of correct account
                                if (parts[0].equals(savedEmail) && parts[1].equals(inputPassword)) {
                                    System.out.print("Enter New Password: ");
                                    String newPassword = br.readLine();
                                    
                                    System.out.print("Enter Again: ");
                                    String confirmation = br.readLine();
                                    
                                    if (!newPassword.equals(confirmation)) {
                                        System.out.println("Password Doesn't Match! Try Again");
                                        continue;
                                    } else {
                                        accounts[i] = parts[0] + "," + newPassword + "," + parts[2];
                                        System.out.println("Password Successfully Changed!");
                                    }
                                    break;
                                } else {
                                    System.out.println("Password Error! Try Again");
                                    continue;
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                    continue;
            }
            
            //If not a selection command, convert to int to select product
            int selected;
            try {
                selected = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid number! Try again");
                return;
            }

            if (selected < 0 || selected >= products.length) {
                System.out.println("\nProduct not found!");
                continue;
            }

            System.out.println(String.join(System.lineSeparator(),
            "\nYou selected: " + products[selected],
            "Price: ₱" + cost[selected],
            "Product Description:",
            productDescription[selected],
            "-----------------------------------",
            "(Press Enter when empty to go back)"
            ));
            
            try {
                String input2 = br.readLine();
                if (input2 == null || input2.trim().isEmpty()) break;
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    public static void searchBoxSelection(BufferedReader br, String input, String searchbox, String[] products, int[] cost, String[] productDescription) {
        boolean found = false;
        while (true) {
            boolean[] validIndex = new boolean[products.length];
            System.out.println("\n=== SEARCH RESULTS ===");
            for (int i = 0; i < products.length; i++) {
                //Make searchbox not case sensitive
                if (products[i].toLowerCase().contains(searchbox.toLowerCase())) {
                    System.out.println(String.join(System.lineSeparator(),
                    i + " - " + products[i],
                    "₱" + cost[i],
                    ""
                    ));
                    validIndex[i] = true;
                    found = true;
                }
            }
                
            if (!found) {
                System.out.println("Product not found");
                continue;
            }
                        
            System.out.print("\n(Press Enter when empty to go back)\nSelect Product: ");
            int selected;
            try {
                input = br.readLine();
                if (input == null || input.trim().isEmpty()) {
                    break;
                }
                
                //Convert input to integer          
                try {
                    selected = Integer.parseInt(input.trim());
                    if (!validIndex[selected]) {
                        System.out.println("Invalid Selection!");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid number! Try again");
                    return;
                }
                
                if (selected < 0 || selected >= products.length) {
                    System.out.println("\nProduct not found!");
                    continue;
                }
                            
                System.out.println(String.join(System.lineSeparator(),
                "\nYou selected: " + products[selected],
                "Price: ₱" + cost[selected],
                "Product Description:",
                productDescription[selected],
                "-----------------------------------",
                "(Press Enter when empty to go back)"
                ));
                          
                            
                try {
                    String input2 = br.readLine();
                    if (input2 == null || input2.trim().isEmpty()) return;
                } catch (IOException e) {
                    System.out.println("Error");
                }
                            
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
        }
    }
}