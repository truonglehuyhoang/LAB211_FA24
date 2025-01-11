/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import business_objects.RAMItem;

/**
 *
 * @author ACER
 */
public interface IRAMManagementSystem {
    void createNewRAMItem(RAMItem r);
    void searchRAMItem(int choice, String value);
    void updateRAMItem(String oldId, RAMItem r);
    void deleteRAMItem(String id);
    void loadFile();
    void saveFile();
    void printRAMItem(int choice);
}
