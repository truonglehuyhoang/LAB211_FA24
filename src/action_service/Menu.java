/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import action_service.RAMManagementSystem;
import action_service.IRAMManagementSystem;
import business_objects.RAMItem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import tool_input.Input;

/**
 *
 * @author ACER
 */
public class Menu implements IMenu{
   
    IRAMManagementSystem system = new RAMManagementSystem();
    Scanner sc = new Scanner(System.in);
    int choice = 0;
    @Override
    public void displayMenu() {
        do{
            System.out.println("------------------- MENU -------------------");
            System.out.println("| 1. Create new RAM Item                   |");
            System.out.println("| 2. Search RAM Item                       |");
            System.out.println("| 3. Update RAM Item                       |");
            System.out.println("| 4. Delete RAM Item                       |");
            System.out.println("| 5. Show all RAM Items                    |");
            System.out.println("| 6. Save RAM Item to file                 |");
            System.out.println("| 7. Quit                                  |");
            System.out.println("--------------------------------------------");
                try{
                    choice = Input.getInt(" => Enter your choice: ", 1, 7);

                    switch(choice){
                        case 1:
                            do{
                                RAMItem newItem = new RAMItem();
                                system.createNewRAMItem(newItem);
                            }while(!confirmYesNo("\nDo you want to go back to the Main Menu?\n->'Yes' to go back\n->'No' to continue adding\n=> Your choice: "));
                            break;
                            
                        case 2:
                            do{
                                System.out.println("Choose the attribute to search:");
                                System.out.println("1.Search by Type");
                                System.out.println("2.Search by Bus");
                                System.out.println("3.Search by Brand");
                                
                                int choice1 = Input.getInt("Your choice (1-3): ", 1, 3);

                                String value = Input.getString("Enter the value to search: ");
                                system.searchRAMItem(choice1, value);
                            }while(!confirmYesNo("\nDo you want to go back to the Main Menu?\n->'Yes' to go back\n->'No' to continue searching\n=> Your choice: "));
                            break;
                            
                        case 3:
                            do{
                                String getCode = Input.getString("Enter RAM Code to be updated: ");
                                RAMItem updatedItem = new RAMItem();
                                system.updateRAMItem(getCode, updatedItem);
                            }while(!confirmYesNo("\nDo you want to go back to the Main Menu?\n->'Yes' to go back\n->'No' to continue updating\n=> Your choice: "));
                            break;
                            
                        case 4:
                            do{
                                String getCode = Input.getString("Enter the RAM code to be deleted: ");
                                system.deleteRAMItem(getCode);
                            }while(!confirmYesNo("\nDo you want to go back to the Main Menu?\n->'Yes' to go back\n->'No' to continue deleting\n=> Your choice: "));
                            break;
                            
                        case 5:
                            do{
                                System.out.println("Choose the attribute to sort the list:");
                                System.out.println("1.Sort by Type");
                                System.out.println("2.Sort by Bus");
                                System.out.println("3.Sort by Brand");
                                
                                int choice2 = Input.getInt("Your choice (1-3): ", 1, 3);
                                system.printRAMItem(choice2);
                            }while(!confirmYesNo("\nDo you want to go back to the Main Menu? (Yes/No):"));
                            break;
                            
                        case 6:
                            do{
                                system.saveFile();
                                System.out.println("Saved Successfully!");
                            }while(!confirmYesNo("\nDo you want to go back to the Main Menu? (Yes/No):"));
                            break;
                            
                        case 7:
                            
                            System.out.println("------------------------------------");
                            System.out.println("| ~Thank you! Exitting the program~ |");
                            System.out.println("------------------------------------");
                            break;

                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                }catch(InputMismatchException e){
                    System.out.println("The input must be a number. Try again.");
                    sc.nextLine();
                }
            }while(choice != 7);
            sc.close();
    }

    @Override
    public boolean confirmYesNo(String mess) {
        System.out.println(mess);
        String flag = sc.nextLine();
        do{
            if(flag.equalsIgnoreCase("Y") || flag.equalsIgnoreCase("YES")){
                return true;
            }else if(flag.equalsIgnoreCase("N") || flag.equalsIgnoreCase("NO")){
                return false;
            }else{
                System.out.println("Please enter Y or N. Try again.");
                flag = sc.nextLine();
            }
        }while(true);
    }
}
