/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import action_service.IMenu;
import action_service.Menu;

/**
 *
 * @author ACER
 */
public class Main {
    public static void main(String[] args) {
        IMenu menu = new Menu();
        menu.displayMenu();
    }
}
