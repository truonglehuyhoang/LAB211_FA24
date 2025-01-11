/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import business_objects.RAMItem;
import data_objects.DaoFactory;
import data_objects.IDaoFactory;
import data_objects.IRAMDao;

/**
 *
 * @author ACER
 */
public class RAMManagementSystem implements IRAMManagementSystem{
    //This class will implement the methods in RAMDao class 
    private final IDaoFactory factory = new DaoFactory();
    private final IRAMDao obj = factory.ramDao();
    
    public RAMManagementSystem(){
        obj.loadFromFile();
    }

    @Override
    public void createNewRAMItem(RAMItem r) {
        obj.createItem(r);
    }

    @Override
    public void searchRAMItem(int choice, String value) {
        obj.searchItem(choice, value);
    }

    @Override
    public void updateRAMItem(String oldId, RAMItem r) {
        obj.updateRAM(oldId, r);
    }

    @Override
    public void deleteRAMItem(String id) {
        obj.deleteItem(id);
    }

    @Override
    public void loadFile() {
        obj.loadFromFile();
    }

    @Override
    public void saveFile() {
        obj.saveToFile();
    }

    @Override
    public void printRAMItem(int choice) {
        obj.printAllItems(choice);
    }

}
