import java.io.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String savedEmail = null, savedPass = null;

        while (true) {
            System.out.println(String.join(System.lineSeparator(),
            "\n=== WELCOME! ===",
            "1 - Register",
            "2 - Login",
            "Enter choice (Or press Enter when empty to Exit): "
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
                    savedEmail = register(br); //<Call Function
                    if (savedEmail == null) continue;
                    savedPass = registerPassword(br); //<Call Function
                    if (savedPass == null) continue;
                    System.out.println("Account Registered Successfully!");
                    break;

                case "2": //Login
                    //If no registered account detected then return to Welcome Interface
                    if (savedEmail == null || savedPass == null) {
                        System.out.println("No registered account found — Please register first");
                        continue;
                    }
                    boolean loggedIn = login(br, savedEmail, savedPass); //<Call Function
                    if (loggedIn) {
                        System.out.println("Login successful!");
                        shoppingMall(br); //<Call Function
                    }
                    break;

                default:
                    System.out.println("Invalid Input!");
            }
        }
    }

    //Register Email
    public static String register(BufferedReader br) {
        while (true) {
            System.out.print("\nRegister - Enter your Email\n(Press Enter to go back): ");
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
            System.out.print("Register - Enter your Password (Press Enter when empty to go back): ");
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

    //Login
    public static boolean login(BufferedReader br, String savedEmail, String savedPass) {
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

            System.out.print("Login - Password (Press Enter when empty to go back): ");
            String pass = null;
            try {
                pass = br.readLine();
                if (pass == null || pass.trim().isEmpty()) return false;
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }

            if (email.equals(savedEmail) && pass.equals(savedPass)) {
                return true;
            } else {
                System.out.println("Login failed! Try again (Press Enter when empty to go back)");
            }
        }
    }

    //Shopping Mall
    public static void shoppingMall(BufferedReader br) {
        String[] products = {
            "myPhone 17 Super Pro Ultra Max",
            "Samsinger Milkyway F25 Ultra",
            "69 Shades of Grey",
            "Skibidi Toilet Figurine Limited Edition",
            "Genshin Impact Keychains"
        };
        int[] cost = {69420, 42069, 6000, 24500, 150};
        
        String productDescription[] = {
            "Introducing the new myPhone 17 Super Pro Ultra Max with the new 120hz Super Amoled Display that enhances colors vividly and providing smooth scrolling",
            "Introducing the new Samsinger Milkyway F25 Ultra packed with a new 1000mp telephoto camera lens that captures every detail from the photo even from afar"
        };

        while (true) {
            System.out.println("\n=== Shopping Mall ===");
            for (int i = 0; i < products.length; i++) {
                System.out.println(String.join(System.lineSeparator(),
                i + " - " + products[i],
                "₱" + cost[i],
                ""
                ));
            }
            System.out.print("Select product (Press Enter when empty to go back)");

            String input = null;
            try {
                input = br.readLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Returning to main menu...");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }

            int selected; //Convert Input to Integer
            try {
                selected = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number! Try again");
                continue;
            }

            if (selected < 0 || selected >= products.length) {
                System.out.println("Invalid product number!");
                continue;
            }

            System.out.println(String.join(System.lineSeparator(),
            "You selected: " + products[selected],
            "Price: ₱" + cost[selected],
            "Product Description:",
            productDescription[selected],
            "",
            "Press Enter when empty to go back to product list"
            ));
            
            try {
                br.readLine();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
