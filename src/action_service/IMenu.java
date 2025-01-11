/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

/**
 *
 * @author ACER
 */
public interface IMenu {
    /*Display the menu when running the program*/
    void displayMenu();
    
    /*Ask the user for confirmation, whether to go back to the main menu or not*/
    boolean confirmYesNo(String mess);
}
