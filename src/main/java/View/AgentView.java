package View;

import java.util.Scanner;

public class AgentView implements IView{
    @Override
    public void displayMenu() {
        System.out.println("Please choose one of the following options:\n1.View Your Properties\n2.Add Properties\n3.See Appointments");
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
