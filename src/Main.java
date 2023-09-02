import atm.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.singUp("Rysbek","Sharapov");
        accountService.singUp("Bek","Muktar");
        accountService.account();
    }
}