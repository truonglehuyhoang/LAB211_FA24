/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

/**
 *
 * @author ACER
 */
public class DaoFactory implements IDaoFactory{

    @Override
    public IRAMDao ramDao() {
        return new RAMDao();
    }
    
}
