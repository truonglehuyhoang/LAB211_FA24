/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_objects;

import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ACER
 */
public class RAMItem extends ItemList implements Serializable, Comparable<RAMItem>{
    
    private String code;
    private String type;
    private String bus;
    private String brand;
    private int quantity;
    private YearMonth production_month_year;
    private boolean active;

    public RAMItem() {
    }
    
    //Constructor for loading item list
    public RAMItem(String type, String bus, String brand, int quantity, YearMonth production_month_year, boolean active) {
        this.code = "";
        this.type = type;
        this.bus = bus;
        this.brand = brand;
        this.quantity = quantity;
        this.production_month_year = production_month_year;
        this.active = active;
    }
    
    //Constructor for when user choose to create new item 
    public RAMItem(String code, String type, String bus, String brand, int quantity, YearMonth production_month_year, boolean active) {
        this.code = code;
        this.type = type;
        this.bus = bus;
        this.brand = brand;
        this.quantity = quantity;
        this.production_month_year = production_month_year;
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public YearMonth getProduction_month_year() {
        return production_month_year;
    }

    public void setProduction_month_year(YearMonth production_month_year) {
        this.production_month_year = production_month_year;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void setActive(boolean active){
        this.active = active;
    }
    
    //This method prints out the list of RAM items
    //The searchItem method in RAMDao class will search for both active and inactive items
    //The printAllItems method will print only active items
    @Override
    public String printfInfo(int i){
        //Use formatter to format production_month_year field
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        String formattedDate = production_month_year.format(formatter);
        //Print out acttive items (for printAllItems methods)
        return String.format("| %2d.| %-16s| %-12s| %-10s| %-15s| %-10d| %-17s |\n", i, code, type, bus + "MHz", brand, quantity, formattedDate);
    }
    
    //compareTo method (implemented by Comparable interface)
    //This method compares 3 fields of the object: type, bus and brand 
    //The Comparator in RAMDao class will call this method to perform comparison 
    @Override
    public int compareTo(RAMItem o) {
        int typeComparison = this.type.compareTo(o.type);
        int busComparison = this.bus.compareTo(o.bus);
        
        if(typeComparison != 0){
            return typeComparison;
        }
        
        if(busComparison != 0){
            return busComparison;
        }
        
        return this.brand.compareTo(o.brand);
    }
}
