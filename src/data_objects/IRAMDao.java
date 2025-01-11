/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_objects.RAMItem;
import java.time.YearMonth;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface IRAMDao{
    /*get unique code of the item when create new Item*/
    String generateCode(String type, int y);
    
    /*Check user's entered production date*/
    YearMonth enterProductionDate();
    
    /*Get the RAM Item based on the code user enters*/
    RAMItem getRAM(String id);
    
    /*Check id the RAM Item code does exist or not*/
    boolean isExistCode(String id);
    
    /*2. Add an Item into List*/
    boolean createItem(RAMItem r);
    
    /*3. Search SubMenu*/
    boolean searchItem(int choice, String value);
    
    /*Search items based on type*/
    List<RAMItem> searchByType(String type);
    
    /*Search items based on bus speed*/
    List<RAMItem> searchByBus(String bus);
    
    /*Search items based on brand*/
    List<RAMItem> searchByBrand(String brand);
    
    /*4. Update Item Information*/
    boolean updateRAM(String oldId, RAMItem r);
    
    /*5. Delete Item*/
    RAMItem deleteItem(String id);
    
    /*6. Show All Items*/
    void printAllItems(int choice);
    
    /*Read and write file*/
    boolean loadFromFile();
    
    boolean saveToFile();
    
    /*Table decoration*/
    void openTable();
    
    void closeTable();
}
