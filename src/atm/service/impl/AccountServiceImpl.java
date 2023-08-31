package atm.service.impl;

import atm.dao.AccountDao;
import atm.model.UserAccount;
import atm.service.AccountService;

import java.awt.*;
import java.util.*;
import java.util.stream.Stream;


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
            userAccount.setBalance(0);
            accountDao.getUserAccounts().add(userAccount);
            System.out.println(userAccount);
        } catch (RuntimeException a) {
            System.out.println(Color.ANSI_RED + "WRITE CORRECTLY!" + Color.ANSI_RESET);
        }
    }

    @Override
    public void singIn(String name, String lastName) {
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            while (true) {
                System.out.println(Color.ANSI_YELLOW + "=>=>=>=>=>=>SIGN IN<=<=<=<=<=<=" + Color.ANSI_RESET);
                System.out.println("Your name: ");
                name = scanner.nextLine();
                System.out.println("Your lastname: ");
                lastName = scanner.nextLine();
                if (name.equals(userAccount.getName()) && lastName.equals(userAccount.getLastName())) {
                    System.out.println(Color.ANSI_YELLOW + "=======SUCCESSFUL=======" + Color.ANSI_RESET);
                    while (true) try {
                        System.out.println(Color.ANSI_YELLOW + "========MENU=======: \n  Users data ->->-> |1|\n  Balance   ->->-> |2|" +
                                "\n  Deposit   ->->-> |3|\n  Send   ->->->->  |4| " + Color.ANSI_RESET);
                        String choice = scanner.nextLine();
                        switch (choice) {
                            case "1" -> System.out.println(accountDao.getUserAccounts());
                            case "2" -> balance(userAccount.getCardNumber(), userAccount.getPinCode());
                            case "3" -> deposit(userAccount.getCardNumber(), userAccount.getPinCode());
                            case "4" -> {
                                System.out.println("Friend card-number : ");
                                sendToFriend(scanner.nextLine());
                            }
                        }
                    } catch (RuntimeException e) {
                        System.out.println(Color.ANSI_RED + "WRITE CORRECTLY!" + Color.ANSI_RESET);
                        break;
                    }
                } else {
                    System.out.println(Color.ANSI_RED + "INCORRECTLY NAME OR LASTNAME!" + Color.ANSI_RESET);
                    break;
                }
            }
        }
    }

    @Override
    public void balance(String cardNumber, String pinCode) {
//        System.out.println("Card number: ");
//        cardNumber = scanner.nextLine();
//        System.out.println("Pin code: ");
//        pinCode = scanner.nextLine();
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            if (cardNumber.equals(userAccount.getCardNumber()) && pinCode.equals(userAccount.getPinCode())) {
                System.out.println(Color.ANSI_CYAN + "Your balance : " + userAccount.getBalance() + Color.ANSI_RESET);
                break;
            } else {
                System.out.println(Color.ANSI_RED + "Incorrectly card number or pin code !" + Color.ANSI_RESET);
                break;
            }
        }
    }

    @Override
    public void deposit(String cardNumber, String pinCode) {
//        System.out.println("Card number: ");
//        cardNumber = scanner.nextLine();
//        System.out.println("Pin code: ");
//        pinCode = scanner.nextLine();
        for (UserAccount account : accountDao.getUserAccounts()) {
            try {
                if (account.getCardNumber().equals(cardNumber) && account.getPinCode().equals(pinCode)) {
                    System.out.println("How much do you want to deposit :");
                    int deposit = scanner.nextInt();
                    System.out.println("Balance successfully replenished ");
                    account.setBalance(account.getBalance() + deposit);
                    System.out.println(Color.ANSI_GREEN + "Your balance : " + account.getBalance() + Color.ANSI_RESET);
                } else {
                    System.out.println(Color.ANSI_RED + "Incorrectly card-number or pin-code!" + Color.ANSI_RESET);
                }
                break;
            } catch (RuntimeException r) {
                System.out.println(Color.ANSI_RED + "WRITE CORRECTLY" + Color.ANSI_RESET);
                break;
            }
        }
    }


    @Override
    public void sendToFriend(String friendCardNumber) {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Pin code: ");
        String pinCode = scanner.nextLine();
        try {
            for (UserAccount userAccount : accountDao.getUserAccounts()) {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    for (UserAccount account : accountDao.getUserAccounts()) {
                        if (account.getCardNumber().equals(friendCardNumber)) {
                            System.out.println("How much do you want to send :");
                            int send = scanner1.nextInt();
                            if (userAccount.getBalance() >= send) {
                                account.setBalance(send);
                                userAccount.setBalance(userAccount.getBalance() - send);
                                System.out.println(Color.ANSI_GREEN + "Your balance : " + userAccount.getBalance() + Color.ANSI_RESET);
                                break;
                            } else {
                                System.out.println(Color.ANSI_RED + "INSUFFICIENT FUNDS!" + Color.ANSI_RESET);
                            }
                        }
                    }
                }
            }

        } catch (RuntimeException r) {
            System.out.println(Color.ANSI_RED + "WRITE CORRECTLY!" + Color.ANSI_RESET);
        }
    }

    @Override
    public void withdrawMoney(int withdraw) {
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            if (userAccount.getBalance()>=withdraw){
                System.out.println("How much do you want to withdraw :");
                withdraw = scanner.nextInt();
                int count = 0;
                int count5=0;

            }
        }
    }

    public void account() {
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            singIn(userAccount.getName(), userAccount.getLastName());
        }
    }
}

