import java.io.*;

public class OnlineShoppingSystem {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //A single string of each 'accounts' contains "savedEmail,savedPass,savedUsername"
        String[] accounts = new String[3];
        int existingAccounts = 0;
        String savedEmail = null, savedPass = null, savedUsername = null;
        
        //Store 5 products in cart for each account [account][products]
        String[][] cartData = new String[3][5];
        int cartProductsAmount = 0;
        
        //Store 5 vouchers for each account [account][vouchers]
        String[][] voucherData = new String[3][5];
        int vouchersAmount = 0;
        
        
        //-----------------------------------------------
        int[] cost = { 69420, 42069, 6000, 24500, 35 };
        String[] products = {
            "myPhone 17 Super Pro Ultra to the Max",
            "Samsing Milkyway F25 Ultra",
            "Fifty Shades of Grey",
            "Skibidi Toilet Figurine Limited Edition",
            "Genshin Impact Acrylic Keychains"
        };
        
        String[] productDescription = {
            "Introducing the new myPhone 17 Super Pro Ultra Max with the new 120hz Super Amoled Display",
            "Introducing the new Samsinger Milkyway F25 Ultra packed with a new 1000mp telephoto camera lens capturing each detail for a pixel perfect photo",
            "Is a chunchun romance novel by British Author E.L.James featuring explicitly chunchun scenes featuring elements of chunchun practices",
            "[Limited Stock Only!] Skibidi Skibi Toilet Skibidi Skibidi Toilet",
            "Genshin Impact custom made Acrylic Keychains [Available Characters: Furina, Flins, Varka, Capitano, Venti]"
        };
        
        String[] productVariants = { "Ugly Orange,Blue,Silver", "Black,White,Grey", "None", "None", "Furina,Flins,Varka,Capitano,Venti" };
        String[] productStock = { "1000,50,45", "128,256,512", "69", "1", "0,15,20,25,30" };
        
        String[] availableVouchers = { "5,5000", "10,5000", "15,15000", "20,40000", "20,60000" };

        while (true) {
            //Update existingAccounts in real time
            int remainingAccounts = 0;
            for (String account : accounts) {
                if (account != null) remainingAccounts++;
            }
            existingAccounts = remainingAccounts;
            
            
            //Main Interface
            System.out.println("Existing Accounts: " + existingAccounts);
            System.out.print(String.join(System.lineSeparator(),
            "",
            "===== WELCOME! =====",
            "    Register - 1",
            "     Login   - 2",
            "------------------------------",
            "(Press Enter to Exit Program)",
            "Input: "
            ));

            String choice = null;
            try {
                choice = br.readLine();
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }

            if (choice == null || choice.trim().isEmpty()) {
                System.out.println("\nApp Closed");
                return;
            }

            switch (choice.trim()) {
                case "1": //Register
                    if (existingAccounts >= 3) {
                        System.out.println("\nRegistered Accounts limit reached!");
                        continue;
                    }
                    
                    boolean accountRegistered = false;
                    while (!accountRegistered) {
                        savedEmail = registerEmail(br); //<Call Function
                        if (savedEmail == null) break;
                    
                        //Prevent registering the same emails
                        boolean alreadyExists = false;
                        for (int i = 0; i < existingAccounts; i++) {
                            if (accounts[i].startsWith(savedEmail + ",")) {
                                System.out.println("\nThis email already has an existing account!");
                                alreadyExists = true;
                                break;
                            }
                        }
                        if (alreadyExists) continue;
                        
                        while (!accountRegistered) {
                            savedPass = registerPassword(br); //<Call Function
                            if (savedPass == null) break;
                            //Prevent password being similar to email
                            if (savedPass.equalsIgnoreCase(savedEmail)) {
                                System.out.println("\nPassword cannot be the same as your Email!");
                                continue;
                            }
                            
                            while (true) {
                                savedUsername = registerUsername(br); //<Call Function
                                if (savedUsername == null) break;
                    
                                accounts[existingAccounts] = savedEmail + "," + savedPass + "," + savedUsername;
                                existingAccounts++;
                                
                                accountRegistered = true;
                                System.out.println("\nAccount Registered Successfully!");
                                break;
                            }
                        }
                    }
                    break;

                case "2": //Login
                    //If no registered account detected then return to Welcome Interface
                    if (existingAccounts == 0) {
                        System.out.println("\nNo registered account found in system!\nPlease register first");
                        continue;
                    }
                    boolean loggedIn = login(br, accounts, existingAccounts); //<Call Function
                    if (loggedIn) {
                        shoppingMall(br, accounts, existingAccounts, savedEmail, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount, availableVouchers); //<Call Function
                    }
                    break;

                default:
                    System.out.println("\nInvalid Input! Try Again");
            }
        }
    }

    //Register Email
    public static String registerEmail(BufferedReader br) {
        while (true) {
            System.out.print("\n(Press Enter to Return)\nRegister - Enter your Email: ");
            try {
                String email = br.readLine();
                if (email.isEmpty()) return null;
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
            System.out.print("\n(Press Enter to Return)\nRegister - Enter your Password: ");
            try {
                String pass = br.readLine();
                if (pass.isEmpty()) return null;
                
                //Prevent spaces
                if (pass.contains(" ")) {
                    System.out.println("\nPassword cannot contain spaces!");
                    continue;
                }
                
                //Prevent password if less sthan 8 characters
                if (pass.length() < 8) {
                    System.out.println("\nPassword must at least have a minimum of 8 characters");
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
            System.out.print("\n(Press Enter to Return)\nRegister - Please set a username for your account: ");
            try {
                String username = br.readLine();
                if (username.isEmpty()) return null;
                
                //Prevent username being only spaces
                if (username.replace(" ","").isEmpty()) {
                    System.out.println("\nUsername must contain characters!");
                    continue;
                }
                
                //Prevent if less than 3 characters
                if (username.length() < 3) {
                    System.out.println("\nUsername must contain at least 3 characters!");
                    continue;
                }
                
                //Prevent if more than 21 characters
                if (username.length() > 21) {
                    System.out.println("\nUsername must be or less than 21 characters!");
                    continue;
                }
                
                //Prevent if it contains special characters
                boolean containsSC = false;
                String[] specialCharacters = { 
                    "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "]", "{", "}", "\\", "|", ";", ":", "'", "\"", ",", ".", "<", ">", "/", "?"
                };
                for (String notAllowed : specialCharacters) {
                    if (username.contains(notAllowed)) {
                        System.out.println("\nUsername must not contain any special characters!");
                        containsSC = true;
                        break;
                    }
                }
                if (containsSC) continue;
                
                return username; //Register Username
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
                
                if (email.trim().equals(partsEmail) && pass.equals(partsPass)) {
                    System.out.println("\nLOGIN SUCCESSFUL! Welcome " + partsUsername);
                    return true;
                }
            }

            System.out.println("\nEmail or Password Error! Try Again\n(Press Enter when empty to go back");
        }
    }



    //Shopping Mall Interface
    public static void shoppingMall(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String[] products, int[] cost, String[] productDescription, String[] productVariants, String[] productStock, String[][] cartData, int cartProductsAmount, String[][] voucherData, int vouchersAmount, String[] availableVouchers) {
        while (true) {
            System.out.println(String.join(System.lineSeparator(),
            "\n===== HOME =====",
            "(Type 'SEARCH' to open search box and search a product)",
            "(Type 'CART' to open shopping cart)",
            "(Type 'VOUCHERS' to open voucher page)",
            "(Type 'USERNAME' to edit username)",
            "(Type 'PASSWORD' to change password)",
            "(Type 'DELETE' to delete account",
            "-------------------------------------------------------",
            "(Press Enter to Logout)"
            ));
            
            //Loop to print each product
            System.out.println("\n===== PRODUCTS =====");
            for (int i = 0; i < products.length; i++) {
                System.out.println(String.join(System.lineSeparator(),
                "",
                i + 1 + " - " + products[i],
                "₱" + cost[i]
                ));
            }
            System.out.println("===========================");
            
            System.out.print("Input: ");
            String input = null;
            try {
                input = br.readLine().trim();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("\nLogged Out\nReturning to login page...");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error");
                continue;
            }
            
            //Convert input to uppercase before reading value
            switch (input.toUpperCase().trim()) {
                case "SEARCH":
                    while (true) {
                        System.out.print("\n===== SEARCH BOX =====\nSearch a Product: ");
                        String searchbox = null;
                        try {
                            searchbox = br.readLine();
                            if (searchbox == null || searchbox.trim().isEmpty()) break;
                        } catch (IOException e) {
                            System.out.println("Error");
                            break;
                        }
                        
                        searchBoxSelection(br, accounts, existingAccounts, savedEmail, input, searchbox, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount);
                    }
                    continue;
                    
                case "CART":
                    userCart(br, accounts, existingAccounts, savedEmail, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount);
                    continue;
                    
                case "VOUCHERS":
                    voucherPage(br, accounts, existingAccounts, savedEmail, voucherData, vouchersAmount, availableVouchers);
                    continue;
                    
                case "USERNAME":
                    boolean usernameUpdated = false;
                    while (!usernameUpdated) {
                        System.out.println("\n===== EDIT USERNAME =====");
                        try {
                            System.out.print("Enter New Username: ");
                            String newUsername = br.readLine();
                        
                            if (newUsername == null || newUsername.isEmpty()) {
                                System.out.println("\nEdit Cancelled");
                                break;
                            }
                            
                            while (true) {
                                System.out.print("Enter Password: ");
                                String inputPassword = br.readLine();
                        
                                if (inputPassword == null || inputPassword.isEmpty()) {
                                    break;
                                }
                            
                                for (int i = 0; i < existingAccounts; i++) {
                                    String[] parts = accounts[i].split(",");
                            
                                    //Check if it matches current email and password to ensure it overwrites username
                                    //of correct account
                                    if (parts[0].equals(savedEmail) && parts[1].equals(inputPassword)) {
                                        accounts[i] = parts[0] + "," + parts[1] + "," + newUsername.trim();
                                        System.out.println("\nSaved! Username successfully updated");
                                        usernameUpdated = true;
                                    } else {
                                        System.out.println("Password Error! Try Again");
                                        //continue;
                                    }
                                }
                                
                                if (usernameUpdated) break; //<Exit inner loop
                            }
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                    continue;
                    
                case "PASSWORD":
                    while (true) {
                        System.out.println("\n===== CHANGE PASSWORD ======");
                        try {
                            System.out.println("Enter Current Password: ");
                            String inputPassword = br.readLine();
                        
                            if (inputPassword.trim() == null) {
                                System.out.println("\nEdit Cancelled");
                                break;
                            }
                            
                            //Check if it matches current email and password to ensure it overwrites password
                            //of correct account
                            for (int i = 0; i < existingAccounts; i++) {
                                String[] parts = accounts[i].split(",");
                                
                                //Stop if password doesn't match
                                if (!parts[0].equals(savedEmail) && !parts[1].equals(inputPassword)) {
                                    System.out.println("\nPassword Error! Try Again");
                                    continue;
                                }
                                
                                //Set New Password
                                while (true) {
                                    System.out.print("Enter New Password: ");
                                    String newPassword = br.readLine();
                                    
                                    if (newPassword == null || newPassword.isEmpty()){
                                        break;
                                    }
                                    
                                    if (newPassword.contains(" ")) {
                                        System.out.println("\nPassword cannot contain spaces!");
                                        continue;
                                    }
                                    
                                    if (newPassword.length() < 8) {
                                        System.out.println("\nPassword must at least have a minimum of 8 characters");
                                        continue;
                                    }
                                    
                                    System.out.print("Enter Again: ");
                                    String confirmation = br.readLine();
                                    
                                    if (!newPassword.equals(confirmation)) {
                                        System.out.println("\nPassword Doesn't Match! Try Again");
                                        //continue;
                                    } else {
                                        accounts[i] = parts[0] + "," + newPassword + "," + parts[2];
                                        System.out.println("\nPassword Successfully Changed! Login again ");
                                        return;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                    continue;
                    
                case "DELETE":
                    while (true) {
                        try {
                            System.out.print(String.join(System.lineSeparator(),
                            "\n=== ARE YOU SURE YOU WANT TO DELETE YOUR ACCOUNT? ===",
                            "===      DATA CANNOT BE RECOVERED ONCE DELETED!     ===",
                            "Enter 'YES' to confirm",
                            "(Press Enter to Return): "
                            ));
                            String confirmDelete = br.readLine();
                        
                            if (confirmDelete == null || confirmDelete.trim().isEmpty()) {
                                System.out.println("\nAccount Deletion Cancelled");
                                break;
                            }
                            
                            if (!confirmDelete.equalsIgnoreCase("YES")) {
                                System.out.println("\nInvalid Input! Try Again");
                                continue;
                            }
                            
                            while (true) {
                                System.out.println("\nEnter your Password");
                                String inputPassword = br.readLine();
                            
                                if (inputPassword == null || inputPassword.isEmpty()) {
                                    break;
                                }
                            
                                //Check if it matches current email and password to ensure it deletes the
                                //correct account
                                for (int i = 0; i < existingAccounts; i++) {
                                    String[] parts = accounts[i].split(",");
                            
                                    if (parts[0].equals(savedEmail) && parts[1].equals(inputPassword)) {
                                        accounts[i] = null;
                                        
                                        System.out.println("\nACCOUNT SUCCESSFULLY DELETED");
                                        return;
                                    } else {
                                        System.out.println("\nPassword Error! Try Again");
                                        //continue;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                    continue;
            }
            
            //If it's not any of the matching string commands above then call function
            productSelection(br, accounts, existingAccounts, savedEmail, input, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount);
        }
    }
    
    
    
    //Product Selection
    public static void productSelection(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String input, String[] products, int[] cost, String[] productDescription, String[] productVariants, String[] productStock, String[][] cartData, int cartProductsAmount, String[][] voucherData, int vouchersAmount) {
        while (true) {
            try {
                int selected;
                try {
                    selected = Integer.parseInt(input.trim()) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid Input! Try Again");
                    return;
                }
            
                //If input is less than 1 or more than the printed products then stop
                if (selected < 0 || selected >= products.length) {
                    System.out.println("\nProduct not found!");
                    break;
                }

                System.out.println(String.join(System.lineSeparator(),
                "\n===== PRODUCT INFO =====",
                "Name: " + products[selected],
                "Product Description:",
                productDescription[selected],
                "-----------------------------------",
                "Type 'ADD' to add product to cart",
                "Type 'BUY' to buy product",
                "(Press Enter to Return)"
                ));
                
                System.out.print("Input: ");
                String input2 = br.readLine();
                if (input2 == null || input2.trim().isEmpty()) return;
                
                switch (input2.toUpperCase()) {
                    case "ADD":
                        addToCart(br, accounts, existingAccounts, savedEmail, selected, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount);
                        break;
                    case "BUY":
                        //buyProduct(br, accounts, existingAccounts, savedEmail, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount);
                        break;
                }
                
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    //Search box Selection
    public static void searchBoxSelection(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String input, String searchbox, String[] products, int[] cost, String[] productDescription, String[] productVariants, String[] productStock, String[][] cartData, int cartProductsAmount, String[][] voucherData, int vouchersAmount) {
        boolean found = false;
        while (true) {
            boolean[] validIndex = new boolean[products.length];
            System.out.println("\n===== SEARCH RESULTS =====\n");
            for (int i = 0; i < products.length; i++) {
                //Make searchbox not case sensitive
                if (products[i].toUpperCase().contains(searchbox.toUpperCase())) {
                    System.out.println(String.join(System.lineSeparator(),
                    i + 1 + " - " + products[i],
                    "₱" + cost[i],
                    ""
                    ));
                    validIndex[i] = true;
                    found = true;
                }
            }
                
            if (!found) {
                System.out.println("\nProduct not found");
                break;
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
                    selected = Integer.parseInt(input.trim()) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid Input! Try Again");
                    return;
                }
                

                if (selected < 0 || selected >= products.length) {
                    System.out.println("\nInvalid Selection! Try Again");
                    continue;
                }
                
                //Prevent selecting other items not from search result
                if (!validIndex[selected]) {
                    System.out.println("\nInvalid! Product not found from search results");
                    continue;
                }
                
                System.out.println(String.join(System.lineSeparator(),
                        "\n===== PRODUCT INFO =====",
                        "Name: " + products[selected],
                        "Price: ₱" + cost[selected],
                        "Product Description:",
                        productDescription[selected],
                        "-----------------------------------",
                        "Type 'ADD' to add product to cart",
                        "Type 'BUY' to buy product",
                        "(Press Enter to Return)"
                ));
                          
                System.out.print("Input: ");
                String input2 = br.readLine();
                if (input2 == null || input2.trim().isEmpty()) return;
                
                switch (input2.toUpperCase()) {
                    case "ADD":
                        addToCart(br, accounts, existingAccounts, savedEmail, selected, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount);
                        break;
                    case "BUY":
                        //buyProduct(br, accounts, existingAccounts, savedEmail, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount);
                        break;
                }
                            
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    
    
    //Add Products to Cart
    public static void addToCart(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, int selected, String[] products, int[] cost, String[] productDescription, String[] productVariants, String[] productStock, String[][] cartData, int cartProductsAmount) {
        try {
            //Update cartProductsAmount
            int remainingCartProducts = 0;
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i] == null) break;
                String[] parts = accounts[i].split(",");
                
                //If logged in account matches accounts data then proceed
                if (savedEmail.equals(parts[0])) {
                    for (int j = 0; j < cartData[i].length; j++) {
                        if (cartData[i][j] != null) remainingCartProducts++;
                    }
                    break;
                }
            }
            cartProductsAmount = remainingCartProducts;
            
            //Prevent adding more to cart if user cart reaches 5
            if (cartProductsAmount == 5) {
                System.out.println("\n Your Cart is full!");
                return;
            }
            
            String[] variantType = productVariants[selected].split(",");
            String[] stockAmount = productStock[selected].split(",");
            int selectedVariantType = -1;
            
            //For Products with variants
            boolean productIsAdded = false;
            if (productVariants[selected].contains(",")) {
                while (!productIsAdded) {
                    System.out.println("\n===== Select Variant =====");
                
                    //Display Variants add 1 so it starts with one
                    for (int i = 0; i < variantType.length; i++) {
                        System.out.println((i + 1) + " - " + variantType[i]);
                    }
                    
                    System.out.print("Input: ");
                    String selectVariant = br.readLine();
                    if (selectVariant.trim().isEmpty()) {
                        break;
                    }
                        
                    int selectVariantInt = -1;
                    try {
                        selectVariantInt = Integer.parseInt(selectVariant) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Try Again");
                        continue;
                    }
                
                    //Prevent invalid variant selection
                    if (selectVariantInt < 0 || selectVariantInt > variantType.length) {
                        System.out.println("\nInvalid Variant! Select Another");
                        continue;
                    }
                    
                    selectedVariantType = selectVariantInt;
                
                
                    
                    //Select Stock
                    while (!productIsAdded) { 
                        //If variant stock is 0 then return
                        if (stockAmount[selectedVariantType].equals("0")) {
                            System.out.println("\nVariant Out of Stock! Select Another");
                            break;
                        }
                    
                        System.out.println("\n=== Select Amount ===");
                        //Display Remaining Stock (Reduce by 1 since our print loop added an increment value)
                        System.out.println("Available Stock: " + stockAmount[selectedVariantType]);
                    
                        System.out.print("Input: ");
                        String selectAmount = br.readLine();
                        if (selectAmount.trim().isEmpty()) {
                           break;
                        }
                        
                        int selectAmountInt = -1;
                        try {
                            selectAmountInt = Integer.parseInt(selectAmount);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input! Try Again");
                            continue;
                        }
                    
                    
                        int stockAmountInt = Integer.parseInt(stockAmount[selectedVariantType]);
                        //Prevent invalid amount selection
                        if (selectAmountInt < 1 || selectAmountInt > stockAmountInt) {
                            System.out.println("Invalid Amount!");
                            continue;
                        }
                        
                        while (true) {
                            System.out.println("\nADDED TO CART SUCCESSFULLY!");
                            System.out.println("Product: " + products[selected]);
                            System.out.println("Variant: " + variantType[selectedVariantType]);
                            System.out.println("Quantity: " + selectAmountInt);
                            System.out.println("Price: " + cost[selected]);
                            
                            for (int i = 0; i < existingAccounts; i++) {
                                String[] parts = accounts[i].split(",");
                                //If logged in account matches account thr proceed
                                if (savedEmail.equals(parts[0])) {
                                    for (int j = 0; j < cartData[i].length; j++) {
                                        //If cartData is null or has no product data, add product data to that index
                                        if (cartData[i][j] == null) {
                                            cartData[i][j] =  products[selected] + "," + variantType[selectedVariantType] + "," + selectAmountInt + "," + cost[selected];
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        
                            //Return Confirmation
                            System.out.println("(Press Enter to Return)");
                            String enterToExit = br.readLine();
                            if (enterToExit.trim().isEmpty()) {
                                productIsAdded = true;
                                break;
                            } else {
                                System.out.println("Invalid Input!");
                            }
                        }
                    }
                }
                
            //For products without variants
            } else {
                while (!productIsAdded) { 
                    //If variant stock is 0 then return
                    if (productStock.equals("0")) {
                        System.out.println("\nVariant Out of Stock! Select Another");
                        break;
                    }
                    
                    System.out.println("\n=== Select Amount ===");
                    //Display Remaining Stock (Reduce by 1 since our print loop added an increment value)
                    System.out.println("Available Stock: " + productStock[selected]);
                    
                    System.out.print("Input: ");
                    String selectAmount = br.readLine();
                    if (selectAmount.trim().isEmpty()) {
                       break;
                    }
                        
                    int selectAmountInt;
                    try {
                        selectAmountInt = Integer.parseInt(selectAmount);
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid Input! Try Again");
                        continue;
                    }
                    
                    
                    int stockAmountInt = Integer.parseInt(productStock[selected]);
                    //Prevent invalid amount selection
                    if (selectAmountInt < 1 || selectAmountInt > stockAmountInt) {
                        System.out.println("\nInvalid Amount!");
                        continue;
                    }
                    
                    
                    while (true) {
                        System.out.println("\nADDED TO CART SUCCESSFULLY!");
                        System.out.println("Product: " + products[selected]);
                        System.out.println("Variant: No Variant Available");
                        System.out.println("Quantity: " + selectAmountInt);
                        System.out.println("Price: " + cost[selected]);
                        
                        for (int i = 0; i < existingAccounts; i++) {
                            String[] parts = accounts[i].split(",");
                            //If logged in account matches the proceed
                            if (savedEmail.equals(parts[0])) {
                                for (int j = 0; j < cartData[i].length; j++) {
                                    //If cartData is null or has no product data, add product data to that index
                                    if (cartData[i][j] == null) {
                                        cartData[i][j] =  products[selected] + "," + "No Variant Available" + "," + selectAmountInt + "," + cost[selected];
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        
                    
                        //Return Confirmation
                        System.out.println("(Press Enter to Return)");
                        String enterToExit = br.readLine();
                        if (enterToExit == null || enterToExit.trim().isEmpty()) {
                            productIsAdded = true;
                            break;
                        } else {
                            System.out.println("Invalid Input!");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    
    
    //User Cart
    public static void userCart(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String[] products, int[] cost, String[] productDescription, String[] productVariants, String[] productStock, String[][] cartData, int cartProductsAmount, String[][] voucherData, int vouchersAmount) {
        while (true) {
            try {
                System.out.println("\n===== MY CART =====");
                
                //Update cartProductsAmount
                int remainingCartProducts = 0;
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) break;
                    String[] parts = accounts[i].split(",");
                
                    //If logged in account matches accounts data then proceed
                    if (savedEmail.equals(parts[0])) {
                       for (int j = 0; j < cartData[i].length; j++) {
                            if (cartData[i][j] != null) remainingCartProducts++;
                        }
                        break;
                    }
                }
                cartProductsAmount = remainingCartProducts;
                System.out.println("Amount: " + cartProductsAmount + "/5");
            
                boolean hasProducts = false;
                for (int i = 0; i < existingAccounts; i++) {
                    String[] parts = accounts[i].split(",");
                    //If logged in account matches accounts data then proceed
                    if (parts[0].equals(savedEmail.trim())) {
                        //Check if i(account data) for account exists
                        for (int j = 0; j < cartData[i].length; j++) {
                            //Check if cartData isn't null
                            if (cartData[i][j] != null) {
                                String[] cartDataParts = cartData[i][j].split(",");
                                
                                System.out.println(String.join(System.lineSeparator(),
                                        "\n#" + (j + 1),
                                        "Product: " + cartDataParts[0],
                                        "Variant: " + cartDataParts[1],
                                        "Quantity: " + cartDataParts[2],
                                        "Price: " + cartDataParts[3],
                                        ""
                                ));
                                hasProducts = true;
                            }
                        }
                        break;
                    }
                }
                
                //If user didn't added any products to cart yet then run this part
                if (!hasProducts) {
                    System.out.println("Looks like your Cart is Empty! T^T");
                    System.out.println("(Press Enter to Return)");
                    String input = br.readLine();
                    if (input == null || input.trim().isEmpty()) {
                        break;
                    } else {
                        System.out.println("Invalid Input! Try Again");
                        continue;
                    }
                }
                
                System.out.print("\nInput a number to select a product\n(Press Enter to Return)\nInput: ");
                String cartInput = br.readLine();
                if (cartInput == null || cartInput.trim().isEmpty()) break;
           
                
                int cartInputInt = -1;
                try {
                    cartInputInt = Integer.parseInt(cartInput) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Error");
                    continue;
                }
                
                //Product selection inside cart
                while (true) {
                    if (cartInputInt < 0 || cartInputInt >= cartProductsAmount) {
                        System.out.println("\nInvalid Input! Try Again");
                        break;
                    }
                    
                    for (int i = 0; i < existingAccounts; i++) {
                        String[] parts = accounts[i].split(",");
                        //If logged in account matches then proceed
                        if (savedEmail.equals(parts[0])) {
                            for (int j = 0; j < cartData[i].length; j++) {
                                String[] cartDataParts = cartData[i][cartInputInt].split(",");
                                System.out.println(String.join(System.lineSeparator(),
                                        "\n===== You Selected =====",
                                        "Product: " + cartDataParts[0],
                                        "Variant: " + cartDataParts[1],
                                        "Quantity: " + cartDataParts[2],
                                        "Price: " + cartDataParts[3],
                                        ""
                                ));
                            }
                            break;
                        }
                    }
                    
                    System.out.print("Type 'BUY' to buy product\n(Press Enter to Return\nInput: ");
                    String input = br.readLine();
                    if (input == null || input.trim().isEmpty()) break;
                    
                    if (input.toUpperCase().trim().equals("BUY")) {
                        buyProductFromCart(br, accounts, existingAccounts, savedEmail, cartInputInt, products, cost, productDescription, productVariants, productStock, cartData, cartProductsAmount, voucherData, vouchersAmount);
                    } else {
                        System.out.println("Invalid Input! Try Again");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    
    
    //Voucher main page
    public static void voucherPage(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String[][] voucherData, int vouchersAmount, String[] availableVouchers) {
        while (true) {
            try {
                System.out.print("\n===== VOUCHERS =====\nType 'MYVOUCHERS' to check your vouchers\nType 'CLAIM' to claim vouchers\n");
                
                //Update vouchersAmount
                int remainingVouchersAmount = 0;
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) break;
                    String[] parts = accounts[i].split(",");
                
                    //If logged in account matches accounts data then proceed
                    if (savedEmail.equals(parts[0])) {
                       for (int j = 0; j < voucherData[i].length; j++) {
                            if (voucherData[i][j] != null) remainingVouchersAmount++;
                        }
                        break;
                    }
                }
                vouchersAmount = remainingVouchersAmount;
                
                System.out.print("Input: ");
                String input = br.readLine();
                if (input == null || input.trim().isEmpty()) break;
                
                switch (input.toUpperCase().trim()) {
                    case "MYVOUCHERS":
                        myVouchers(br, accounts, existingAccounts, savedEmail, voucherData, vouchersAmount);
                      break;
                    case "CLAIM":
                        claimVouchers(br, accounts, existingAccounts, savedEmail, voucherData, vouchersAmount, availableVouchers);
                      break;
                    default:
                        System.out.println("Invalid Input! Try Again");
                      break;
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    
    //Check User Vouchers
    public static void myVouchers(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String[][] voucherData, int vouchersAmount) {
        while (true) {
            try {
                System.out.println("\n===== MY VOUCHERS =====");
                
                //Update vouchersAmount
                int remainingVouchersAmount = 0;
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) break;
                    String[] parts = accounts[i].split(",");
                
                    //If logged in account matches accounts data then proceed
                    if (savedEmail.equals(parts[0])) {
                       for (int j = 0; j < voucherData[i].length; j++) {
                            if (voucherData[i][j] != null) remainingVouchersAmount++;
                        }
                        break;
                    }
                }
                vouchersAmount = remainingVouchersAmount;
                
                
                //Check User Vouchers
                boolean hasVouchers = false;
                for (int i = 0; i < existingAccounts; i++) {
                    String[] parts = accounts[i].split(",");
                    //If logged in account matches accounts data then proceed
                    if (parts[0].equals(savedEmail.trim())) {
                        //Check if i(account data) for account exists
                        for (int j = 0; j < voucherData[i].length; j++) {
                            //Check if voucherData isn't null
                            if (voucherData[i][j] != null) {
                                String[] voucherDataParts = voucherData[i][j].split(",");
                                
                                System.out.println(String.join(System.lineSeparator(),
                                        "\nVoucher #" + (j + 1),
                                        voucherDataParts[0] + "% OFF",
                                        "Minimum Spend: " + voucherDataParts[1],
                                        ""
                                ));
                                hasVouchers = true;
                            }
                        }
                        break;
                    }
                }
                
               
                //if no vouchers found in account then run this part
                if (!hasVouchers) {
                    System.out.println("\nNo Vouchers Found");
                    System.out.print("(Press Enter to Return)\nInput: ");
                    String input = br.readLine();
                    if (input == null || input.trim().isEmpty()) {
                        break;
                    } else {
                        System.out.println("Invalid Input! Try Again");
                    }
                }
                
                
                if (hasVouchers) {
                    System.out.print("\n(Press Enter to Return)\nInput: ");
                    String input = br.readLine();
                    if (input == null || input.trim().isEmpty()) {
                        break;
                    } else {
                        System.out.println("Invalid Input! Try Again");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
    
    
    //Claim Vouchers
    public static void claimVouchers(BufferedReader br, String[] accounts, int existingAccounts, String savedEmail, String[][] voucherData, int vouchersAmount, String[] availableVouchers) {
        while (true) {
            try {
                System.out.println("\n===== CLAIM VOUCHERS =====");
                
                //Update vouchersAmount
                int remainingVouchersAmount = 0;
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i] == null) break;
                    String[] parts = accounts[i].split(",");
                
                    //If logged in account matches accounts data then proceed
                    if (savedEmail.equals(parts[0])) {
                       for (int j = 0; j < voucherData[i].length; j++) {
                            if (voucherData[i][j] != null) remainingVouchersAmount++;
                        }
                        break;
                    }
                }
                vouchersAmount = remainingVouchersAmount;
                System.out.println("My Vouchers: " + remainingVouchersAmount + "/5\n\nAvailable Vouchers:");
                
                //Print Available Vouchers
                boolean allVouchersClaimed = true; //<Check for if all vouchers are claimed
                for (int i = 0; i < existingAccounts; i++) {
                    String[] parts = accounts[i].split(",");
                    //If logged in account matches then proceed
                    if (savedEmail.equals(parts[0])) {
                        //Loop through current availableVouchers to be claimed
                        for (int j = 0; j < availableVouchers.length; j++) {
                            String[] voucherDataParts = availableVouchers[j].split(",");
                            boolean alreadyClaimed = false; //<Check for if voucher is already claimed
                            //Loop through voucher data [5 times]
                            for (int k = 0; k < voucherData[i].length; k++) {
                                //1st condition checks if user voucher data slot has value and not empty
                                //2nd condition checks whether the slot value is similar to availableVouchers
                                if (voucherData[i][k] != null && voucherData[i][k].equals(availableVouchers[j])) {
                                    alreadyClaimed = true;
                                    break;
                                }
                            }
                            
                            //If user already has the voucher then skip printing
                            if (alreadyClaimed) {
                                System.out.println(String.join(System.lineSeparator(),
                                    "Voucher #" + (j + 1) + "[ALREADY CLAIMED!]",
                                    voucherDataParts[0] + "% OFF",
                                    "Minimum Spend: " + voucherDataParts[1],
                                    ""
                                ));
                                continue;
                            }
                            
                            System.out.println(String.join(System.lineSeparator(),
                                    "Voucher #" + (j + 1),
                                    voucherDataParts[0] + "% OFF",
                                    "Minimum Spend: " + voucherDataParts[1],
                                    ""
                            ));
                            allVouchersClaimed = false;
                        }
                        break;
                    }
                }
                
                //If all vouchers are claimed then print No available vouchers
                if (allVouchersClaimed){
                    System.out.println("\nNo Available Vouchers to Claim");
                    System.out.print("(Press Enter to Return)\n Input: ");
                    String input = br.readLine();
                    if (input == null || input.trim().isEmpty()) {
                        break;
                    } else {
                        System.out.println("Invalid Input! Try Again");
                        continue;
                    }
                }
                
                
                System.out.print("\nSelect a Voucher\n(Press Enter to Return\nInput: ");
                String input = br.readLine();
                if (input == null || input.trim().isEmpty()) break;

                
                int inputInt = -1;
                try {
                    inputInt = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input! Try Again");
                    continue;
                }
                
                //Claim Selected Voucher
                boolean successfullyClaimed = false;
                while (!successfullyClaimed) {
                    if (inputInt < 0 || inputInt >= availableVouchers.length) {
                        System.out.println("Invalid Input! Try Again");
                        break;
                    }
                    
                    for (int i = 0; i < existingAccounts; i++) {
                        String[] parts = accounts[i].split(",");
                        //If logged in account matches then proceed
                        if (savedEmail.equals(parts[0])) {
                            boolean alreadyClaimed = false;
                            
                            for (int j = 0; j < voucherData[i].length; j++) {
                                //1st condition checks if user voucher data slot has value and not empty
                                //2nd condition checks whether the slot value is similar to availableVouchers or contains USED
                                if (voucherData[i][j] != null && (voucherData[i][j].equals(availableVouchers[inputInt]) || voucherData[i][j].contains("USED"))) {
                                    alreadyClaimed = true;
                                    break;
                                }
                            }
                            
                            if (alreadyClaimed) {
                                System.out.println("You Already Claimed this Voucher! Select Another");
                                successfullyClaimed = true;
                                break;
                            }
                            
                            for (int j = 0; j < voucherData[i].length; j++) {
                                //Add voucher to user voucherData
                                if (voucherData[i][j] == null) {
                                    voucherData[i][j] = availableVouchers[inputInt];
                                    
                                    String[] voucherDataParts = availableVouchers[inputInt].split(",");
                                
                                    System.out.println(String.join(System.lineSeparator(),
                                           "\n===== SUCCESSFULLY CLAIMED VOUCHER =====",
                                            voucherDataParts[0] + "% OFF",
                                            "Minimum Spend: " + voucherDataParts[1],
                                            ""
                                    ));
                                    successfullyClaimed = true;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    
                    while (true) {
                        System.out.print("\n(Press Enter to Return\nInput: ");
                        String input2 = br.readLine();
                        if (input2 == null || input2.trim().isEmpty()) {
                            successfullyClaimed = true;
                            break;
                        } else {
                            System.out.print("\nInvalid Input!");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
