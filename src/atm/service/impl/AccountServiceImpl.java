package atm.service.impl;

import atm.dao.AccountDao;
import atm.model.UserAccount;
import atm.service.AccountService;

import java.util.*;
import java.util.logging.XMLFormatter;


public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void singUp(String name, String lastName) {
        UserAccount userAccount = new UserAccount();
        try {
            userAccount.setName(name);
            userAccount.setLastName(lastName);
            userAccount.setCardNumber(String.valueOf(random.nextLong(100_000_00, 999_999_99)));
            userAccount.setPinCode(String.valueOf(random.nextInt(10_00, 99_99)));
            userAccount.setBalance(1000);
            accountDao.getUserAccounts().add(userAccount);
            System.out.println(userAccount);
        } catch (RuntimeException a) {
            System.out.println(Color.ANSI_RED + "WRITE CORRECTLY!" + Color.ANSI_RESET);
        }
    }

    @Override
    public void singIn(String name, String lastName) {
        while (true) {
            try {
                Scanner scanner1 = new Scanner(System.in);
                UserAccount userAccount1 = checkUserName(name, lastName);
                if (userAccount1.getName().equalsIgnoreCase(name) &&
                        userAccount1.getLastName().equalsIgnoreCase(lastName)) {
                    while (true) try {
                        System.out.println(Color.ANSI_YELLOW + "======MENU======");
                        System.out.println("|1| BALANCE \n|2| DEPOSIT \n|3| SEND \n|4| WITHDRAW MONEY\n CHOOSE :" + Color.ANSI_RESET);
                        String choice = scanner1.nextLine();
                        switch (choice) {
                            case "1" -> balance(userAccount1.getCardNumber(), userAccount1.getPinCode());
                            case "2" -> deposit(userAccount1.getCardNumber(), userAccount1.getPinCode());
                            case "3" -> {
                                System.out.println("Friend card-number :");
                                String friendCardNumber = scanner1.nextLine();
                                sendToFriend(friendCardNumber);
                            }
                            case "4" -> {
                                System.out.println("How much money do you want to withdraw :");
                                int withdraw = scanner.nextInt();
                                withdrawMoney(withdraw);
                            }
                            default ->
                                    throw new RuntimeException(Color.ANSI_RED + "CHOOSE CORRECTLY ! ! !" + Color.ANSI_RESET);
                        }
                    } catch (RuntimeException r) {
                        System.out.println(r.getMessage());
                    }
                } else {
                    throw new RuntimeException(Color.ANSI_RED + "INCORRECT" + Color.ANSI_RESET);
                }
            } catch (RuntimeException r) {
                System.out.println(Color.ANSI_RED + r.getMessage() + Color.ANSI_RESET);
            }
        }
    }


    @Override
    public void balance(String cardNumber, String pinCode) {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Card number: ");
        cardNumber = scanner1.nextLine();
        System.out.println("Pin code: ");
        pinCode = scanner1.nextLine();
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            if (cardNumber.equals(userAccount.getCardNumber()) && pinCode.equals(userAccount.getPinCode())) {
                System.out.println(Color.ANSI_CYAN + "Your balance : " + userAccount.getBalance() + Color.ANSI_RESET);
                break;
            } else {
                System.out.println(Color.ANSI_RED + "Incorrectly card number or pin code !" + Color.ANSI_RESET);
            }
            break;
        }
    }

    @Override
    public void deposit(String cardNumber, String pinCode) {
        UserAccount account1 = checkUserCard(cardNumber, pinCode);
        try {
            if (!account1.getCardNumber().equalsIgnoreCase(cardNumber) && !account1.getPinCode().equalsIgnoreCase(pinCode)) {
                throw new RuntimeException(Color.ANSI_RED + "Incorrect card-number or pin-code!!!" + Color.ANSI_RESET);
            }
            System.out.println("How much do you want to deposit :");
            int deposit = scanner.nextInt();
            if (account1.getCardNumber().equalsIgnoreCase(cardNumber) && account1.getPinCode().equalsIgnoreCase(pinCode)) {
                System.out.println("Balance successfully replenished ");
                account1.setBalance(account1.getBalance() + deposit);
                System.out.println(Color.ANSI_GREEN + "Your balance : " + account1.getBalance() + Color.ANSI_RESET);
            }
        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }
    }


    @Override
    public void sendToFriend(String friendCardNumber) {
        try {
            Scanner scanner1 = new Scanner(System.in);
            for (UserAccount userAccount : accountDao.getUserAccounts()) {
                UserAccount account1 = checkUserCard(userAccount.getCardNumber(), userAccount.getPinCode());
                if (account1.getBalance() == 0) {
                    throw new RuntimeException(Color.ANSI_RED + "INSUFFICIENT FUNDS!!!\nYour balance : " + Color.ANSI_RESET + account1.getBalance());
                }
                if (userAccount.getCardNumber().equals(account1.getCardNumber()) && userAccount.getPinCode().equals(account1.getPinCode())) {
                    for (UserAccount account : accountDao.getUserAccounts()) {
                        if (account.getCardNumber().equals(friendCardNumber)) {
                            System.out.println("How much do you want to send :");
                            int send = scanner1.nextInt();
                            if (userAccount.getBalance() >= send) {
                                account.setBalance(send);
                                userAccount.setBalance(userAccount.getBalance() - send);
                                System.out.println(Color.ANSI_GREEN + "SUCCESSFULLY!!\nYour balance : " + userAccount.getBalance());
                                System.out.println("Friend balance : " + account.getBalance() + Color.ANSI_RESET);
                                break;
                            }
                            break;
                        }
                    }
                    break;
                }
                break;
            }

        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }
    }

    @Override
    public void withdrawMoney(int withdrawMoney) {
        try {
            Scanner scanner1 = new Scanner(System.in);
            for (UserAccount userAccount : accountDao.getUserAccounts()) {
                UserAccount userAccount1 = checkUserCard(userAccount.getCardNumber(), userAccount.getPinCode());
                if (userAccount.getCardNumber().equalsIgnoreCase(userAccount1.getCardNumber())
                        && userAccount.getPinCode().equalsIgnoreCase(userAccount1.getPinCode())) {
                    if (withdrawMoney % 1000 == 0) {
                        int pieces = withdrawMoney / 1000;
                        System.out.println("1] You can get : " + 1000 + "$ " + pieces + " pieces!");
                    }
                    if (withdrawMoney % 500 == 0) {
                        int pieces = withdrawMoney / 500;
                        System.out.println("2] You can get : " + 500 + "$ " + pieces + " pieces!");
                    }
                    if (withdrawMoney % 200 == 0) {
                        int pieces = withdrawMoney / 200;
                        System.out.println("3] You can get : " + 200 + "$ " + pieces + " pieces!");
                    }
                    if (withdrawMoney % 100 == 0) {
                        int pieces = withdrawMoney / 100;
                        System.out.println("4] You can get : " + 100 + "$ " + pieces + " pieces!");
                    }
                    if (withdrawMoney % 50 == 0) {
                        int pieces = withdrawMoney / 50;
                        System.out.println("5] You can get : " + 50 + "$" + pieces + " pieces!");
                    }
                    if (withdrawMoney % 10 == 0) {
                        int pieces = withdrawMoney / 10;
                        System.out.println("6] You can get : " + 10 + "$ " + pieces + " pieces!");
                    }
                    System.out.println("Choose : ");
                    String choice = scanner1.nextLine();
                    if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5") || choice.equals("6")) {
                        System.out.println("You have withdraw " + withdrawMoney + '$');
                        userAccount1.setBalance(userAccount1.getBalance() - withdrawMoney);
                        System.out.println("You balance : " + userAccount1.getBalance());
                        break;
                    }
                    break;
                }
            }
        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }
    }


    public UserAccount checkUserCard(String cardNumber, String pinCode) {
        UserAccount userAccount = new UserAccount();
        try {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Card number: ");
            cardNumber = scanner1.nextLine();
            System.out.println("Pin code: ");
            pinCode = scanner1.nextLine();

            if (cardNumber.length() < 8 || pinCode.length() < 4) {
                throw new RuntimeException(Color.ANSI_RED + "Incorrect card-number or pin-code" + Color.ANSI_RESET);
            }
            for (UserAccount user : accountDao.getUserAccounts()) {
                if (user.getCardNumber().equals(cardNumber) && user.getPinCode().equals(pinCode)) {
                    userAccount = user;
                    break;
                }
                if (!user.getCardNumber().equalsIgnoreCase(cardNumber) || !user.getPinCode().equalsIgnoreCase(pinCode)) {
                    throw new RuntimeException(Color.ANSI_RED + "INCORRECT CARD-NUMBER OR PIN-CODE" + Color.ANSI_RESET);
                }
            }
        } catch (RuntimeException a) {
            System.out.println(a.getMessage());
        }
        return userAccount;
    }

    public UserAccount checkUserName(String name, String lastName) {
        UserAccount userAccount = new UserAccount();
        try {
            System.out.println("Your name: ");
            name = scanner.nextLine();
            System.out.println("Your lastname: ");
            lastName = scanner.nextLine();
            if (name.equals("") || lastName.equals("")) {
                throw new RuntimeException(Color.ANSI_RED + "Write correctly!!!" + Color.ANSI_RESET);
            }
            for (UserAccount account : accountDao.getUserAccounts()) {
                if (account.getName().equalsIgnoreCase(name) && account.getLastName().equalsIgnoreCase(lastName)) {
                    userAccount = account;
                    break;
                }
                if (!account.getName().equalsIgnoreCase(name) || !account.getLastName().equalsIgnoreCase(lastName)) {
                    throw new RuntimeException(Color.ANSI_RED + "Incorrect name or lastname!!!" + Color.ANSI_RESET);
                }
            }
        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }
        return userAccount;

    }

    public void account() {
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            singIn(userAccount.getName(), userAccount.getLastName());
        }
    }
}
