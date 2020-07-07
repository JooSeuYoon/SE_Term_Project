package manage;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

public class manageUsers {

    Scanner scan = new Scanner(System.in);
    String usercsv = System.getProperty("user.dir").concat("\\userList.csv");

    class user{
        private String userID;
        private String password;
        private String userName;
        private String userPhone;
        private String userEmail;
        private boolean activated;

        public String getUserID(){
            return userID;
        }
        public boolean getActivated(){
            return activated;
        }
        public String getUserEmail(){
            return userEmail;
        }
    }

    class loginInfo{
        private String userID;
        private String password;

        public String getUserID(){
            return userID;
        }
        public String getUserPW(){
            return password;
        }
    }

    public String LoginInfor(){
        String answer;
        System.out.println("Do you want to Log In? (y or n): ");
        answer = scan.nextLine();

        if(answer.equals("y")) {
            loginInfo newlog = new loginInfo();

            System.out.println("Enter user ID: ");
            newlog.userID = scan.nextLine();
            System.out.println("Enter password: ");
            newlog.password = scan.nextLine();

            String active = "true";
            String deactive = "false";
            BufferedReader br = null;
            try{
                br = new BufferedReader(new FileReader(usercsv));
                String line="";

                while((line = br.readLine())!=null) {
                    List<String> tmpLst = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpLst = Arrays.asList(array);

                    if(active.equals(tmpLst.get(5))){
                        if(newlog.userID.equals(tmpLst.get(0))&&newlog.password.equals(tmpLst.get(1))) {
                            System.out.println("Login Success!\n");
                            return newlog.userID;
                        }
                    }
                    else if(deactive.equals(tmpLst.get(5))){
                        System.out.println("Deactivated User\n");
                    }
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{
                    if(br!=null){
                        br.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }


        }

        System.out.println("Login Fail");
        return null;
    }

    public void addUser(){
        String answer;
        System.out.println("Do you want to sign up? (y or n): ");
        answer = scan.nextLine();

        if(answer.equals("y")){
            user newuser = enterInfo();
            BufferedReader br = null;
            BufferedWriter bw = null;

            try {
                br = new BufferedReader(new FileReader(usercsv));
                String line="";
                List<List<String>> allData = new ArrayList<List<String>>();

                while((line = br.readLine())!=null){
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpList = Arrays.asList(array);
                    allData.add(tmpList);
                }

                bw = new BufferedWriter(new FileWriter(usercsv));

                for(List<String> newLine : allData){
                    List<String> list = newLine;
                    for(String data : list){
                        bw.write(data);
                        bw.write(",");
                    }
                    bw.newLine();
                }
                bw.append(newuser.userID);
                bw.append(",");
                bw.append(newuser.password);
                bw.append(",");
                bw.append(newuser.userName);
                bw.append(",");
                bw.append(newuser.userPhone);
                bw.append(",");
                bw.append(newuser.userEmail);
                bw.append(",");
                bw.append("true");
                bw.newLine();


            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally{
                try{
                    if(bw != null){
                        bw.close();
                    }
                    if(br != null){
                        br.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        else
            return;

    }

    public user enterInfo(){

        user newuser = new user();

        System.out.println("Enter ID: ");
        newuser.userID = scan.nextLine();
        System.out.println("Enter password: ");
        newuser.password = scan.nextLine();
        System.out.println("Enter Name: ");
        newuser.userName = scan.nextLine();
        System.out.println("Enter Phone number: ");
        newuser.userPhone = scan.nextLine();
        System.out.println("Enter Email address: ");
        newuser.userEmail = scan.nextLine();

        newuser.activated = true;

        return newuser;
    }

    public void changeActivated(String userID){
        String admin = "admin";

        if(admin.equals(userID)) {
            showUserList();
            List<List<String>> allData = new ArrayList<List<String>>();
            int count=0;

            String line = "";
            String ans;
            System.out.println("Deactive user or Delete? (1,2)");
            ans = scan.nextLine();

            BufferedWriter bw = null;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(usercsv));
                while((line=br.readLine())!=null){
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpList = Arrays.asList(array);
                    allData.add(tmpList);
                }

                bw = new BufferedWriter(new FileWriter(usercsv));

                if ("1".equals(ans)) {
                    String duser;
                    System.out.println("Enter the user to be deactivated: ");
                    duser = scan.nextLine();
                    int i = 0;

                    for (List<String> newLine : allData) {
                        List<String> list = newLine;
                        if(duser.equals(list.get(0))){
                            count = i;
                        }
                        i++;
                    }

                    if(count==0){
                        System.out.println("Deactivate Fail\n");
                        return;
                    }

                    List<String> tmplst = new ArrayList<String>();
                    for(int j = 0;j<allData.get(count).size()-1;j++) {
                        tmplst.add(allData.get(count).get(j));
                    }
                    tmplst.add("false");
                    allData.remove(count);

                    for(List<String> chgline : allData){
                        List<String> lst = chgline;
                        for(String data : lst){
                            bw.write(data);
                            bw.write(",");
                        }
                        bw.newLine();
                    }
                    bw.append(tmplst.get(0));
                    bw.append(",");
                    bw.append(tmplst.get(1));
                    bw.append(",");
                    bw.append(tmplst.get(2));
                    bw.append(",");
                    bw.append(tmplst.get(3));
                    bw.append(",");
                    bw.append(tmplst.get(4));
                    bw.append(",");
                    bw.append(tmplst.get(5));
                    bw.newLine();

                    System.out.println("Deavtivated User Success\n");
                }
                else if ("2".equals(ans)) {
                    String duser;
                    System.out.println("Enter the user to be deleted: ");
                    duser = scan.nextLine();
                    int i = 0;

                    for (List<String> newLine : allData) {
                        List<String> list = newLine;
                        if (duser.equals(list.get(0))&&"false".equals(list.get(5))) {
                        count = i;
                        }
                        i++;
                    }

                    if(count==0){
                        System.out.println("Delete Fail\n");
                        return;
                    }

                    allData.remove(count);

                    for(List<String> chgline : allData){
                        List<String> lst = chgline;
                        for(String data : lst){
                            bw.write(data);
                            bw.write(",");
                        }
                        bw.newLine();
                    }
                    System.out.println("Delete User Success");
                }
                else{
                    System.out.println("Enter wrong number.");
                    return;
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(bw != null){
                        bw.close();
                    }
                    if(br != null){
                        br.close();
                    }
            }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else {
            return;
        }
    }

    public void showUserList(){
        String answer;
        System.out.println("Do you want to see User List?(y or n): ");
        answer = scan.nextLine();

        if(answer.equals("y")){
            BufferedReader br = null;

            try{
                br = new BufferedReader(new FileReader(usercsv));
                String line = "";

                while((line= br.readLine())!=null){
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");
                    tmpList = Arrays.asList(array);
                    System.out.println(tmpList);
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

    public static void main(String[] args){
        manageUsers start = new manageUsers();
        String userID = start.LoginInfor();
        start.changeActivated(userID);
    }

}
