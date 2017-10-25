/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Fuzzy.Logic;
import java.util.ArrayList;

/**
 *
 * @author Someone
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        ArrayList<Data> validSet = new ArrayList<>();
        ArrayList<Data> testSet = new ArrayList<>();
        
        //data validasi
        validSet.add(new Data("B01", 97, 74, 1));
        validSet.add(new Data("B02", 36, 85, 1));
        validSet.add(new Data("B03", 63, 43, 0));
        validSet.add(new Data("B04", 82, 90, 1));
        validSet.add(new Data("B05", 71, 25, 0));
        validSet.add(new Data("B06", 79, 81, 1));
        validSet.add(new Data("B07", 55, 62, 0));
        validSet.add(new Data("B08", 57, 45, 0));
        validSet.add(new Data("B09", 40, 65, 0));
        validSet.add(new Data("B10", 57, 45, 0));
        validSet.add(new Data("B11", 77, 70, 1));
        validSet.add(new Data("B12", 68, 75, 1));
        validSet.add(new Data("B13", 60, 70, 0));
        validSet.add(new Data("B14", 82, 90, 1));
        validSet.add(new Data("B15", 40, 85, 0));
        validSet.add(new Data("B16", 80, 68, 1));
        validSet.add(new Data("B17", 60, 72, 0));
        validSet.add(new Data("B18", 50, 95, 1));
        validSet.add(new Data("B19", 100, 18, 0));
        validSet.add(new Data("B20", 11, 99, 1));
        
        new Graph(validSet, 0, "Data Hoax"); 
        
        Logic fuzzyLogic = new Logic();
        for(int i=0; i<validSet.size(); i++){
            Data dat = validSet.get(i);
            validSet.set(i,fuzzyLogic.ExecuteFuzzy(dat));
        }
                
        new Graph(validSet, 1, "Data Validasi");        
        
        //akurasi validasi
        int benar=0;
        for(int i=0; i<validSet.size(); i++){
            if(validSet.get(i).getHoax()==validSet.get(i).getResult()){
                benar++;
            }
        }
        
        System.out.println("benar :"+benar);
        System.out.println("total :"+validSet.size());
        
        //data test
        testSet.clear();
        testSet.add(new Data("B21", 58, 63));
        testSet.add(new Data("B22", 68, 70));
        testSet.add(new Data("B23", 64, 66));
        testSet.add(new Data("B24", 57, 77));
        testSet.add(new Data("B25", 77, 55));
        testSet.add(new Data("B26", 98, 64));
        testSet.add(new Data("B27", 91, 59));
        testSet.add(new Data("B28", 50, 95));
        testSet.add(new Data("B29", 95, 55));
        testSet.add(new Data("B30", 27, 79));
        //new Graph(testSet, 1, "Data Test");
    }
    
}
