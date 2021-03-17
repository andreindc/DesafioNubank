package com.nubanck.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @desc Main class of the application.
 * @author Andreina Díaz andreinadc@gmail.com
 */
public class View {

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ControllerData controllerData = new ControllerData();
        String option;
        String next;

        do {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("\n\n ---------------------------- \n");
            System.out.println("    ** OPERATIONS MENU ** ");
            System.out.println("1. Account creation ");
            System.out.println("2. Transaction authorization ");
            System.out.println("3. Exit ");
            System.out.print("   Option: ");

            option = reader.readLine();
            System.out.println("\n ---------------------------- \n");
            try {
                switch (option) {
                    case "1":
                        controllerData.Create();
                        break;
                    case "2":
                        controllerData.Authorize();
                        break;
                    case "3":
                        break;
                    default:
                        System.out.println(" Enter a valid option (1,2 or 3)");
                        break;
                }
            }catch (Exception e){
                System.out.println(" The data entered is not in the expected input format");
            }
            System.out.print("\n Press a key to continue ");
            next= reader.readLine();

        }while(!option.equals("3"));
    }
}
