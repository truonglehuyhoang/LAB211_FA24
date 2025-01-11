/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import business_objects.RAMItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class RAMFile implements RAMFileDAL{
    private final String fileName = "RAMModules.dat";
    
    
    //Store the list of RAM items into a binary file (RAMModules.dat) to preserve data between sessions.
    @Override
    public boolean saveToFile(List<RAMItem> ram) {
        FileOutputStream f = null;
        ObjectOutputStream of = null;
        try{
            f = new FileOutputStream(fileName);
            of = new ObjectOutputStream(f);
            of.writeObject(ram); //run successfully, we can implement Serializable (write object to the ObjectOutputStream)
            return true;
        }catch (Exception e){
            System.out.println("File error:" + e.getMessage());
            return false;
        } finally{
            try{
                if(f!=null) f.close();
                if(of!=null) of.close();
            }catch(IOException e){
                System.out.println("File error:" + e.getMessage());
            }
        }
    }

    //Load data from the RAMModules.dat file at the start of the program to ensure continuity.
    @Override
    public boolean loadfromFile(List<RAMItem> ram) {
        FileInputStream f = null;
        ObjectInputStream of = null;
        try{
            File check = new File(fileName);
            if(check.exists()){
                f = new FileInputStream(fileName);
                of = new ObjectInputStream(f);
                @SuppressWarnings("unchecked") //Aware that this method uses uncheck operation, and accept the responsibility ensuring that it is safe
                List<RAMItem> loadedItems = (List<RAMItem>) of.readObject(); //read an object from the ObjectInputStream
                ram.addAll(loadedItems);
                return true;
            }
        }catch (IOException | ClassNotFoundException e){
            System.out.println("File error:" + e.getMessage());
        } finally{
            try{
                if(f!=null) f.close();
                if(of!=null) of.close();
            }catch(Exception e){
                System.out.println("File error:" + e.getMessage());
            }
        }
        return false;
    }
}
