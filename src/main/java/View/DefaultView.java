package View;

import java.util.Scanner;

public class DefaultView implements IView{
    @Override
    public void displayMenu() {
        System.out.println("Please choose one of the following options:\n 1.Login\n\n2.Create an account \n\n");
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
