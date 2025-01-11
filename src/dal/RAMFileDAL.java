/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import business_objects.RAMItem;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface RAMFileDAL {
    /*7. Store Data to Files*/
    
    /*Write/Save to file*/
    boolean saveToFile(List<RAMItem> ram);
    
    /*Read/Load from file*/
    boolean loadfromFile(List<RAMItem> ram);
}
