package atm.model;

import atm.service.impl.Color;

public class UserAccount {
    private String name;
    private String lastName;
    private String cardNumber;
    private String pinCode;
    private int balance;

    public UserAccount() {
    }

    public UserAccount(String name, String lastName, String cardNumber, String pinCode, int balance) {
        this.name = name;
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length()>=3) {
            this.name = name;
        }else {
            System.out.println(Color.ANSI_RED+"The length of the name must be longer than 3!"+Color.ANSI_RESET);
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.length()>=3){
            this.lastName = lastName;
        }else {
            System.out.println(Color.ANSI_RED+"The length of the lastname must be longer than 3!"+Color.ANSI_RESET);
        }

    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void  setCardNumber(String cardNumber){
        this.cardNumber=cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return    "\n========================================="+
                "\nName:   "+"\u001B[32m" + name +"\u001B[0m"+
                "      LastName:  " + "\u001B[32m" +lastName +"\u001B[0m"+
                "\n=========================================="+
                "\nCardNumber:  " + "\u001B[32m" +cardNumber +"\u001B[0m"+
                "       PinCode:  " + "\u001B[32m" +pinCode +"\u001B[0m"+
                "\n=========================================="+
                "\nBalance   |" + "\u001B[32m" +balance +"|\u001B[0m";
    }
}
