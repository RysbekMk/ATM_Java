import atm.dao.AccountDao;
import atm.model.UserAccount;
import atm.service.AccountService;
import atm.service.impl.AccountServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.singUp("Rysbek","Sharapov");
        accountService.singUp("Bek","Muktar");
        accountService.account();






    }
}