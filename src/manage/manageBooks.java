package manage;

import java.util.Scanner;
import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class manageBooks {
    Scanner scan = new Scanner(System.in);

    String usercsv = System.getProperty("user.dir").concat("\\userList.csv");
    String bookcsv = System.getProperty("user.dir").concat("\\bookList.csv");

    class books{
        String bName;
        String bISBN;
        String bWriter;
        String bPublisher;
        String bPubYear;
        String bSellerID;
        String bPrice;
        String bStatue;
    }

    public void addBook(String userID) {

        System.out.println("Do you want to add Books? (y or n): ");
        String ans;
        ans = scan.nextLine();

        if (ans.equals("y")) {
            books newbook = enterInfo(userID);
            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                br = new BufferedReader(new FileReader(bookcsv));
                String line = "";
                List<List<String>> allData = new ArrayList<List<String>>();

                while ((line = br.readLine()) != null) {
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpList = Arrays.asList(array);
                    allData.add(tmpList);
                }

                bw = new BufferedWriter(new FileWriter(bookcsv));

                for (List<String> newLine : allData) {
                    List<String> list = newLine;
                    for (String data : list) {
                        bw.write(data);
                        bw.write(",");
                    }
                    bw.newLine();
                }
                bw.append(newbook.bName);
                bw.append(",");
                bw.append(newbook.bISBN);
                bw.append(",");
                bw.append(newbook.bWriter);
                bw.append(",");
                bw.append(newbook.bPublisher);
                bw.append(",");
                bw.append(newbook.bPubYear);
                bw.append(",");
                bw.append(newbook.bPrice);
                bw.append(",");
                bw.append(newbook.bStatue);
                bw.append(",");
                bw.append(newbook.bSellerID);
                bw.newLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public books enterInfo(String userID){

        books newbook = new books();
        System.out.println("Enter Book Name: ");
        newbook.bName = scan.nextLine();
        System.out.println("Enter Book ISBN: ");
        newbook.bISBN = scan.nextLine();
        System.out.println("Enter Book Writer: ");
        newbook.bWriter = scan.nextLine();
        System.out.println("Enter Book Publisher: ");
        newbook.bPublisher = scan.nextLine();
        System.out.println("Enter Book Publish Year: ");
        newbook.bPubYear = scan.nextLine();
        System.out.println("Enter Book Price: ");
        newbook.bPubYear = scan.nextLine();
        System.out.println("Enter Book Statue (Excellent, Good, Fair): ");
        newbook.bStatue = scan.nextLine();

        newbook.bSellerID = userID;

        return newbook;
    }

    public void showbookList(String userID){
        String ans;
        System.out.println("Do you want to see Books List? (y or n): ");
        ans = scan.nextLine();

        if(ans.equals("y")) {
            BufferedReader br = null;
            List<List<String>> allData = new ArrayList<List<String>>();

            try {
                br = new BufferedReader(new FileReader(bookcsv));
                String line = "";

                while ((line = br.readLine()) != null) {
                    List<String> tmpLst = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpLst = Arrays.asList(array);
                    allData.add(tmpLst);
                }

                if(userID.equals("admin")){
                    for(List<String> newline : allData){
                        System.out.println(newline);
                    }
                }
                else{
                    for(List<String> newline : allData){
                        if(newline.get(7).equals(userID)){
                            System.out.println(newline);
                        }
                    }
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if(br != null){
                        br.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void buyBook(String userID){
        String ans;
        System.out.println("Do you want to buy books? (y or n): ");
        ans = scan.nextLine();

        if(ans.equals("y")){
            List<List<String>> searchedBook = searchBook();
            BufferedReader br = null;
            String sellerID = null;
            String answer;

            if(searchedBook.size()==1){

                System.out.println("Do you want to buy this book? (y or n): ");
                answer = scan.nextLine();

                if(answer.equals("y")) {
                    System.out.println("You select User " + searchedBook.get(0).get(7) + "'s book : " + searchedBook.get(0).get(0));
                    sellerID = searchedBook.get(0).get(7);
                }
            }else if(searchedBook.size()>1){
                int bnum;
                System.out.println("Which book you want to buy: (Enter Number 1~n): ");
                bnum = scan.nextInt();
                scan.nextLine();

                System.out.println("Do you want to buy this book? (y or n): ");
                answer = scan.nextLine();

                if(answer.equals("y")) {
                    System.out.println("You select User " + searchedBook.get(bnum - 1).get(7) + "'s book : " + searchedBook.get(bnum - 1).get(0));
                    sellerID = searchedBook.get(bnum - 1).get(7);
                }
            }else if(searchedBook.isEmpty()){
                System.out.println("There's no searched book");
                return;
            }

            try {
                br = new BufferedReader(new FileReader(usercsv));
                String line = "";

                while((line = br.readLine())!=null){
                    List<String> tmpLst = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpLst = Arrays.asList(array);

                    if(tmpLst.get(0).equals(sellerID)){
                        System.out.println("Seller "+sellerID+"'s Email : "+tmpLst.get(4));
                        System.out.println("Sending Email Success");
                    }else if(tmpLst.get(0).equals(userID)){
                        System.out.println("User "+userID+"'s Email : "+tmpLst.get(4));
                        System.out.println("Sending Email Success");
                    }
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if(br!=null){
                        br.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void manageBooks(String userID){
        int bnum;

        BufferedReader br = null;
        BufferedWriter bw = null;
        String line="";

        try {
            br = new BufferedReader(new FileReader(bookcsv));

            List<List<String>> allData = new ArrayList<List<String>>();
            while((line = br.readLine())!=null){
                List<String> tmpLst = new ArrayList<String>();
                String array[] = line.split(",");
                tmpLst = Arrays.asList(array);
                allData.add(tmpLst);
            }

            if (userID.equals("admin")) {
                showbookList(userID);
                System.out.println("Which book you want to delete? (Enter number 1~n): ");
                bnum = scan.nextInt();
                scan.nextLine();
                allData.remove(bnum-1);
                bw = new BufferedWriter(new FileWriter(bookcsv));

                for(List<String> chgLine : allData){
                    List<String> lst = chgLine;
                    for(String data : lst){
                        bw.write(data);
                        bw.write(",");
                    }
                    bw.newLine();
                }
                System.out.println("Delete Book Success");
            }
            else {
                String ans;
                System.out.println("Modify or Delete Book? (1 or 2): ");
                ans = scan.nextLine();

                if (ans.equals("1")) {
                    showbookList(userID);
                    System.out.println("Which book you want to modify? (Enter number 1~n): ");
                    bnum = scan.nextInt();
                    scan.nextLine();

                    int j = 0,k=0;
                    int count=0;
                    for(List<String> l : allData){
                        if(l.get(7).equals(userID)){
                            if(j==(bnum-1)){
                                count = k;
                            }
                            j++;
                        }
                        k++;
                    }

                    int mnum;
                    System.out.println("Would you like to change any of the information in the book?");
                    System.out.println("1. Book Name");
                    System.out.println("2. ISBN");
                    System.out.println("3. Book Writer");
                    System.out.println("4. Book Publisher");
                    System.out.println("5. Book Published Year");
                    System.out.println("6. Book Selling Price");
                    System.out.println("7. Book Statue");
                    System.out.println("Enter the number: ");
                    mnum = scan.nextInt();
                    scan.nextLine();

                    String info;
                    System.out.println("Book's information "+allData.get(count).get(mnum-1)+" -> (Enter information)");
                    info = scan.nextLine();

                    List<String> tmpList = new ArrayList<String>();
                    for(int i = 0;i<allData.get(count).size();i++){
                        if(i==(mnum-1)){
                            tmpList.add(info);
                        }
                        else{
                            tmpList.add(allData.get(count).get(i));
                        }
                    }
                    allData.remove(count);

                    bw = new BufferedWriter(new FileWriter(bookcsv));

                    for(List<String> chglin : allData){
                        List<String> lst = chglin;
                        for(String data: lst){
                            bw.write(data);
                            bw.write(",");
                        }
                        bw.newLine();
                    }

                    for(int n = 0;n<tmpList.size()-1;n++){
                        bw.append(tmpList.get(n));
                        bw.append(",");
                    }
                    bw.append(tmpList.get(tmpList.size()-1));
                    bw.newLine();

                    System.out.println("Modify Book Success");
                }
                else if (ans.equals("2")) {
                    showbookList(userID);
                    System.out.println("Which book you want to delete? (Enter number 1~n): ");
                    bnum = scan.nextInt();
                    scan.nextLine();

                    int j = 0,k=0;
                    int count=0;
                    for(List<String> l : allData){
                        if(l.get(7).equals(userID)){
                            if(j==(bnum-1)){
                                count = k;
                            }
                            j++;
                        }
                        k++;
                    }

                    allData.remove(count);

                    bw = new BufferedWriter(new FileWriter(bookcsv));

                    for(List<String> chgLine : allData){
                        List<String> lst = chgLine;
                        for(String data : lst){
                            bw.write(data);
                            bw.write(",");
                        }
                        bw.newLine();
                    }
                    System.out.println("Delete Book Success");
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(bw!=null){
                    bw.close();
                }
                if(br!=null){
                    br.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public List<List<String>> searchBook() {
        String ans;
        System.out.println("Do you want search books? (y or n): ");
        ans = scan.nextLine();
        List<List<String>> allData = new ArrayList<List<String>>();

        if (ans.equals("y")) {
            String bookinfo;
            System.out.println("Enter Search word: ");
            bookinfo = scan.nextLine();

            BufferedReader br = null;


            try {
                br = new BufferedReader(new FileReader(bookcsv));
                String line = "";
                while ((line = br.readLine()) != null) {
                    List<String> tmpLst = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpLst = Arrays.asList(array);
                    for (int i = 0; i < 8; i++) {
                        if (tmpLst.get(i).equals(bookinfo)) {
                            System.out.println(tmpLst);
                            allData.add(tmpLst);
                            break;
                        }
                    }
                }
                return allData;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return allData;
    }

}
