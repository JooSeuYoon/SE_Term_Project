import manage.manageBooks;
import manage.manageUsers;

import java.util.Scanner;

public class start {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        System.out.println("-----Used Book MarketPlace-----");
        System.out.println("Select Menu");

        manageUsers nLog = new manageUsers();
        manageBooks func = new manageBooks();
        int mnum;

        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        mnum = scan.nextInt();
        scan.nextLine();

        String user = null;

        switch (mnum){
            case 1: user = nLog.LoginInfor();
            break;
            case 2: nLog.addUser();
            user = nLog.LoginInfor();
            break;
        }

        int ans=0;

        if(user.equals("admin")) {
            while(ans!=5) {
                System.out.println("You are Administrator");
                System.out.println("Select Menu");
                System.out.println("1. Manage Books");
                System.out.println("2. See User List");
                System.out.println("3. See Book List");
                System.out.println("4. Manage Users");
                System.out.println("5. Exit");

                ans = scan.nextInt();
                scan.nextLine();

                switch (ans){
                    case 1: func.manageBooks(user);
                    break;
                    case 2: nLog.showUserList();
                    break;
                    case 3: func.showbookList(user);
                    break;
                    case 4: nLog.changeActivated(user);
                    break;
                }
            }
        }
        else {
            while(ans!=5) {
                System.out.println("Select Menu");
                System.out.println("1. Buy Books");
                System.out.println("2. Register Books");
                System.out.println("3. Manage Books");
                System.out.println("4. See My Book List");
                System.out.println("5. Exit");

                ans = scan.nextInt();
                scan.nextLine();

                switch (ans){
                    case 1: func.buyBook(user);
                    break;
                    case 2: func.addBook(user);
                    break;
                    case 3: func.manageBooks(user);
                    break;
                    case 4: func.showbookList(user);
                    break;
                }
            }
        }

        return;
    }
}
