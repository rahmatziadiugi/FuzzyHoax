/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fuzzy;

import Driver.Data;
import java.util.ArrayList;

/**
 *
 * @author Someone
 */
public class Logic {
    //x emosi, y provokasi
    private ArrayList<Integer> inputxno = new ArrayList<>();
    private ArrayList<Integer> inputyno = new ArrayList<>();
    private ArrayList<Integer> inputino = new ArrayList<>();
    private ArrayList<Double> valx = new ArrayList<>();
    private ArrayList<Double> valy = new ArrayList<>();
    private ArrayList<Double> vali = new ArrayList<>();
    private double layak0=0.0, layak1=0.0;
    
    public Data ExecuteFuzzy(Data data){
        /*---Fuzzification---*/
        fuzzification(data);
        
        /*---Inference---*/
        inference();
        
        /*---Defuzzification---*/        
        data.setHoax(mamdani());
        //data.setHoax(sugeno());
        
        return data;        
    }
    
    /*---Fuzzification---*/
    /*
    emosi          
    0:rendah:0-50 | 0-30(1); 30-50(downhill)
    1:sedang:30-80 | 30-50(uphill); 50-70(1); 70-80(downhill)
    2:tinggi:70-100 | 70-80(uphill); 80-100(1)
    
    provokasi
    0:sedikit:0-70 | 0-50(1); 50-70(downhill)
    1:banyak:50-100 | 50-80(uphill); 80-100(1)
    */
    private void fuzzification(Data data){
        //input emosi
        int n = data.getEmosi();
        if(n<30){
            this.inputxno.add(0);
            this.valx.add(1.0);
        }else if(n<50){
            this.inputxno.add(0);
            this.valx.add(this.calcDownhill(30, 50, n));
            this.inputxno.add(1);
            this.valx.add(this.calcUphill(30, 50, n));
        }else if (n<70){
            this.inputxno.add(1);
            this.valx.add(1.0);
        }else if (n<80){
            this.inputxno.add(1);
            this.valx.add(this.calcDownhill(70, 80, n));
            this.inputxno.add(2);
            this.valx.add(this.calcUphill(70, 80, n));
        }else{
            this.inputxno.add(2);
            this.valx.add(1.0);
        }
        
        //input provokasi
        n = data.getProvokasi();
        if(n<50){
            this.inputyno.add(0);
            this.valy.add(1.0);
        }else if(n<70){
            this.inputyno.add(0);
            this.valy.add(this.calcDownhill(50, 70, n));
            this.inputyno.add(1);
            this.valy.add(this.calcUphill(50, 70, n));
        }else{
            this.inputyno.add(1);
            this.valy.add(1.0);
        }
    }
    
    private double calcUphill(int a, int b, int c){
        /*        
          /|
         /||
        / ||
        a cb
        linear
        */        
        return 1.0 * (c-a) / (b-a);
    }
    
    private double calcDownhill(int a, int b, int c){
        /*        
        |\
        ||\
        || \
        ac b
        linear
        */
        return 1.0 * (b-c) / (b-a);
    }
    
    
    /*---Inference---*/
    /*
    y\x|0|1|2
      0|0|0|1
      1|0|1|1
    
    */
    private void inference(){
        for(int i=0; i<this.inputxno.size(); i++){
            for(int j=0; j<this.inputyno.size(); j++){
                inferenceTab(i, j);
            }
        }
    }
    
    private void inferenceTab(int x, int y){
        if(this.inputxno.get(x)==0 && this.inputyno.get(y)==2){
            this.inputino.add(1);            
        }else if (this.inputxno.get(x)==1 && this.inputyno.get(y)==1){
            this.inputino.add(1);
        }else if (this.inputxno.get(x)==1 && this.inputyno.get(y)==2){
            this.inputino.add(1);
        }else{
            this.inputino.add(0);
        }
        //value
        if(this.valx.get(x)<this.valy.get(y)){
            this.vali.add(this.valx.get(x));
        }else{
            this.vali.add(this.valy.get(y));
        }        
    }
    
    /*---Defuzzification---*/
    /*
    mamdani
    0:rendah:0-80 | 0-50(1); 50-70(downhill)
    1:tinggi:50-100 | 50-70(uphill); 70-100(1)
    */
    private int mamdani(){
        for(int i=0; i<this.inputino.size(); i++){
            if(this.inputino.get(i)==0){
                if(this.vali.get(i)>this.layak0){
                    this.layak0 = this.vali.get(i);
                }
            }
            
            if(this.inputino.get(i)==1){
                if(this.vali.get(i)>this.layak0){
                    this.layak1 = this.vali.get(i);
                }
            }
        }
        //center gravity
        // a+b/c+d
        double a=0.0, b=0.0, c=0.0, d=0.0;
        
        System.out.println(layak0+" "+layak1);
        
        if(this.layak0<this.layak1){
            a=(10+20+30+40)*layak0;
            b=(50+60+70+80+90+100)*layak1;
            c=4*layak0;
            d=6*layak1;
        }else{
            a=(10+20+30+40+50+60)*layak0;
            b=(60+70+80+90+100)*layak1;
            c=6*layak0;
            d=4*layak1;
        }
        
        double y = (a+b)/(c+d);        
        
        if(y>65){
            return 1;
        }else{
            return 0;
        }
    }
    
    private int sugeno(){
        return 0;
    }    
}
