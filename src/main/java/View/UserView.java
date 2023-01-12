package View;

import java.util.Scanner;

public class UserView implements IView{

    @Override
    public void displayMenu() {
        System.out.println("Please choose one of the following options:\n1.View Available Properties\n2.View Appointments\n");
    }

    @Override
    public void displayData(String data) {
        System.out.println(data);
    }

    @Override
    public String fetchData() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


}
