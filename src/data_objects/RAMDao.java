/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_objects.RAMItem;
import dal.RAMFile;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import tool_input.Input;

/**
 *
 * @author ACER
 */
public class RAMDao implements IRAMDao{
    
    List<RAMItem> ramList;
    RAMFile listFile = new RAMFile();
    private final String ID_FORMAT = "RAM%s_%03d";
    private final DateTimeFormatter MONTH_YEAR_FORMAT = DateTimeFormatter.ofPattern("MM-yyyy");
    Scanner sc = new Scanner(System.in);

    
    public RAMDao(){
        ramList = new ArrayList<>();
    }
    
    @Override
    public String generateCode(String type, int y) {
        List<RAMItem> list = this.searchByType(type);
        if(list == null || list.isEmpty()){
            y = 1;
        }else{
            list.sort((r1, r2) -> {
                //extract the numerical part from the code for comparison
                int num1 = Integer.parseInt(r1.getCode().substring(r1.getCode().lastIndexOf("_") + 1));
                int num2 = Integer.parseInt(r2.getCode().substring(r2.getCode().lastIndexOf("_") + 1));
                return Integer.compare(num1, num2);
            });
            // Get the last item in the sorted list (largest code number)
            RAMItem lastItem = list.get(list.size() - 1);
            String lastCode = lastItem.getCode();

            // Extract the numerical part from the last code and increment it
            int lastNumber = Integer.parseInt(lastCode.substring(lastCode.lastIndexOf("_") + 1));
            y = lastNumber + 1;  // Increment the number
        }
        return String.format(ID_FORMAT, type, y);
    }
    
    
    //Check production date input format
    public YearMonth enterProductionDate(){
        String productionDateInput;
        YearMonth currentDate = YearMonth.now(); //Obtains the current year-month from the system clock in the default time-zone.
        
        do{
            productionDateInput = Input.getString("Enter new Production Date (MM-yyyy): ");
            try{
                YearMonth productionDate = YearMonth.parse(productionDateInput, MONTH_YEAR_FORMAT);
                
                //Check if the date is in the future or not
                if(productionDate.isAfter(currentDate)){ //Checks if this year-month is after the specified year-month.
                    System.out.println("This is the future date. Please re-enter a valid date.");
                }else{
                    return productionDate;
                }
                
            }catch(DateTimeParseException e){
                System.out.println("Invalid date format. Please use MM-yyyy.");
            }
        }while(true);
    }

    @Override
    public RAMItem getRAM(String id) {
        //This method get a specific RAM Item according to user's choice
        for(RAMItem ram : ramList){
            if(ram.getCode().equals(id)){
                return ram;
            }
        }
        return null;
    }

    @Override
    public boolean isExistCode(String id) {
        //Check if the entered RAM Item code matched the existed one in the list
        for(RAMItem ram : ramList){
            if(ram.getCode().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createItem(RAMItem r) {
        String ramType = Input.getString("Enter new RAM Type: ");
        String code = generateCode(ramType, 0);
        
        /*Check if the generated code exists or not*/
        while(isExistCode(code)){
            System.out.println("The generated code already exists. Generating new code...");
            code = generateCode(code, 0);
        }
        
        //The default boolean flag for "active" is false, so in this case, active will turn true 
        RAMItem obj = new RAMItem(code, ramType, Input.getString("Enter new Bus speed: "), 
               Input.getString("Enter new RAM Brand: "), Input.getInt("Enter new Quantity: ", 0, 1000), enterProductionDate(), true);
        if(ramList.add(obj)){
           System.out.println("Create RAM Item successfully!");
           return true;
        }else{
           System.out.println("Failed to create new RAM Item!");
           return false;
        }
    }
    
    public boolean searchItem(int choice, String value){
        
        List<RAMItem> foundItems;
        
        switch(choice){
            case 1:
                foundItems = searchByType(value);
                if(foundItems.isEmpty()){
                    System.out.println("No items found with the specified type!");
                }else{
                    System.out.println("\n------------------------ RAM Items by Type --------------------------");
                    System.out.printf("| %-15s %-10s %-17s %-10s           |\n", "ID", "Type", "Production Date", "Quantity");
                    System.out.println("---------------------------------------------------------------------");
                    for(RAMItem item : foundItems){
                        if(!item.isActive()){
                            System.out.printf("| %-15s %-10s %-17s %-10d (inactive)|\n",
                                item.getCode(), item.getType(),
                                item.getProduction_month_year().format(MONTH_YEAR_FORMAT),
                                item.getQuantity());
                        }else{
                            System.out.printf("| %-15s %-10s %-17s %-10d           |\n",
                                item.getCode(), item.getType(),
                                item.getProduction_month_year().format(MONTH_YEAR_FORMAT),
                                item.getQuantity());
                        }
                    }
                    System.out.print("---------------------------------------------------------------------");
                }
                break;
                
            case 2:
                foundItems = searchByBus(value);
                if(foundItems.isEmpty()){
                    System.out.println("No items found with the specified type!");
                }else{
                    System.out.println("\n------------------------ RAM Items by Bus ---------------------------");
                    System.out.printf("| %-15s %-10s %-17s %-10s           |\n", "ID", "Bus", "Production Date", "Quantity");
                    System.out.println("---------------------------------------------------------------------");
                    for(RAMItem item : foundItems){
                        if(!item.isActive()){
                            System.out.printf("| %-15s %-10s %-17s %-10d (inactive)|\n",
                                item.getCode(), item.getBus() + "MHz",
                                item.getProduction_month_year().format(MONTH_YEAR_FORMAT),
                                item.getQuantity());
                        }else{
                            System.out.printf("| %-15s %-10s %-17s %-10d           |\n",
                                item.getCode(), item.getBus() + "MHz",
                                item.getProduction_month_year().format(MONTH_YEAR_FORMAT),
                                item.getQuantity());
                        }
                    }
                    System.out.print("---------------------------------------------------------------------");
                }
                break;
                
            case 3:
                foundItems = searchByBrand(value);
                if(foundItems.isEmpty()){
                    System.out.println("No items found with the specified type!");
                }else{
                    System.out.println("\n------------------------ RAM Items by Brand -------------------------");
                    System.out.printf("| %-15s %-10s %-17s %-10s           |\n", "ID", "Brand", "Production Date", "Quantity");
                    System.out.println("---------------------------------------------------------------------");
                    for(RAMItem item : foundItems){
                        if(!item.isActive()){
                            System.out.printf("| %-15s %-10s %-17s %-10d (inactive)|\n",
                                item.getCode(), item.getBrand(),
                                item.getProduction_month_year().format(MONTH_YEAR_FORMAT),
                                item.getQuantity());
                        }else{
                            System.out.printf("| %-15s %-10s %-17s %-10d           |\n",
                                item.getCode(), item.getBrand(),
                                item.getProduction_month_year().format(MONTH_YEAR_FORMAT),
                                item.getQuantity());
                        }
                    }
                    System.out.print("---------------------------------------------------------------------");
                }
                break;
                
            default:
                return false;
        }
        
        //Check if the specified found items exist 
        if(foundItems.isEmpty()){
            System.out.println("No RAM items found for the given criteria!");
            return false;
        }
            
        //Print out the found items based on specified attribute
        System.out.println("\nFound " + foundItems.size() + " RAM item(s)!");
        return true;
    }

    @Override
    public List<RAMItem> searchByType(String type) {
        //search the item based on type
        List<RAMItem> foundItems = new ArrayList<>();
        for(RAMItem item : ramList){
            if(item.getType().equalsIgnoreCase(type)){
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    @Override
    public List<RAMItem> searchByBus(String bus) {
        //search the item based on bus
        List<RAMItem> foundItems = new ArrayList<>();
        for(RAMItem item : ramList){
            if(item.getBus().equalsIgnoreCase(bus)){
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    @Override
    public List<RAMItem> searchByBrand(String brand) {
        //search the item based on brand
        List<RAMItem> foundItems = new ArrayList<>();
        for(RAMItem item : ramList){
            if(item.getBrand().equalsIgnoreCase(brand)){
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    @Override
    public boolean updateRAM(String oldId, RAMItem ram) {
        RAMItem existingRAM = getRAM(oldId);
        
        //Check if the RAM item or the code of it does exist
        if(existingRAM == null || !isExistCode(oldId)){
            System.out.println("The item with code: " + oldId + " does not exist!");
            return false;
        }
        
        //Check if the RAM item is inactive 
        if(!existingRAM.isActive()){
            System.out.println("The RAM item is currently inactive...");
            return false;
        }
        
        //Begin to update RAM item
        
        //Update type (with the numerical part of the code remain unchanged)
        String newType = Input.getNewString("Enter new type (leave blank to keep current): ", existingRAM.getType());
        if(!newType.isEmpty()){
            //Extract the numerical part from the old code 
            String oldNumPart = oldId.substring(oldId.lastIndexOf("_"));
            //Update the code while keeping the num part intact
            existingRAM.setType(newType);
            existingRAM.setCode(String.format(ID_FORMAT, newType, Integer.parseInt(oldNumPart.substring(1)))); //"Type" will be still updated, the extracted num part will be addded
                                                                                                               //This way the code's numerical part is till preserved 
        }
        
        //Update bus 
        String newBus = Input.getNewString("Enter new bus (leave blank to keep current): ", existingRAM.getBus());
        if(!newBus.isEmpty()){
            existingRAM.setBus(newBus);
        }
        
        //Update brand
        String newBrand = Input.getNewString("Enter new brand (leave blank to keep current): ", existingRAM.getBrand());
        if(!newBrand.isEmpty()){
            existingRAM.setBrand(newBrand);
        }
        
        //Update quantity
        int newQuantity = Input.getNewInt("Enter new quantity (leave blank to keep current): ", 0, 1000, existingRAM.getQuantity());
        if(newQuantity != 0){
            existingRAM.setQuantity(newQuantity);
        }

        //Update production date (check month-year format also)
        YearMonth newProductionDate = null;
        do{
            System.out.print("Enter new production date (MM-yyy) (leave blank to leave current): ");
            String productionDateInput = sc.nextLine().trim();
            
            //Check if user leaves this info blank
            if(productionDateInput.isEmpty()){
                break;
            }
            
            //Check if the entered date is in the future
            try{
                newProductionDate = YearMonth.parse(productionDateInput, MONTH_YEAR_FORMAT);
                YearMonth currentDate = YearMonth.now(); 

                if(newProductionDate.isAfter(currentDate)){
                    System.out.println("This is a future date. Please re-enter a valid date.");
                    newProductionDate = null;
                }else{
                    existingRAM.setProduction_month_year(newProductionDate);
                }

            }catch(DateTimeParseException e){
                System.out.println("Invalid date format. Keeping the current value...");
            }
        }while(newProductionDate == null);
        

        System.out.println("Update the item with code: " + oldId + " successfully!");
        return true;
    }

    @Override
    public RAMItem deleteItem(String id) {
        RAMItem itemToDelete = getRAM(id);
        
        //Check if the item to be deleted or the code of it does exist 
        if(itemToDelete == null || !isExistCode(id)){
            System.out.println("The RAM item with " + id + " not found!");
            return null;
        }
        
        //Add options for confirmation
        ArrayList<String> confirmationOptions = new ArrayList<>();
        confirmationOptions.add("Yes");
        confirmationOptions.add("No");
        String confirmation = Input.getOption("Are you sure you want to delete this item? (Choose Yes or No):", confirmationOptions, false, null);
        if(confirmation.equalsIgnoreCase("Yes")){
            //Set the active status to "inactive" after deletion
            itemToDelete.setActive(false);
            System.out.println("The RAM item has been deleted successfully!");
            return itemToDelete;
        }else{
            System.out.println("Deletion cancelled.");
            return null;
        }
    }
    
    @Override
    public void printAllItems(int choice) { 
        //Check if the list is empty
        if(ramList.isEmpty()){
            System.out.println("Nothing to show here...");
            return;
        }
        
        List<RAMItem> activeItems = new ArrayList<>();
        
        //Filter active RAM items
        for(RAMItem ram : ramList){
            if(ram.isActive()){
               activeItems.add(ram);
            }
        }
        
        //Check if there are no active items in the list
        if(activeItems.isEmpty()){
            System.out.println("Nothing to show here...");
            return;
        }
        
        //3 cases of sorting for printing the list
        switch(choice){
            case 1:
                Collections.sort(activeItems, Comparator.comparing(RAMItem::getType));
                break;
                
            case 2:
                Collections.sort(activeItems, Comparator.comparing(RAMItem::getBus));
                break;
                
            case 3:
                Collections.sort(activeItems, Comparator.comparing(RAMItem::getBrand));
                break;
        }
        
        openTable();
        for(int i = 0; i < activeItems.size(); i++){
            System.out.print(activeItems.get(i).printfInfo(i + 1));
        }
        
        closeTable();
    }
    
    @Override
    public boolean loadFromFile() {
        return listFile.loadfromFile(ramList);
    }

    @Override
    public boolean saveToFile() {
        return listFile.saveToFile(ramList);
    }

    @Override
    public void openTable() {
        System.out.println("\n---------------------------------------------------------------------------------------------------");
        System.out.printf("| %2s.| %-16s| %-12s| %-10s| %-15s| %-10s| %-17s |\n", "No", "ID", "Type", "Bus", "Brand", "Quantity", "Production Date");
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    @Override
    public void closeTable() {
        System.out.println("---------------------------------------------------------------------------------------------------");
    }
}
