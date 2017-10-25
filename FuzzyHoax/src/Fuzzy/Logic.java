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
    private ArrayList<Integer> inputxno;
    private ArrayList<Integer> inputyno;
    private ArrayList<Integer> inputino;
    private ArrayList<Double> valx;
    private ArrayList<Double> valy;
    private ArrayList<Double> vali;
    private ArrayList<Double> inferenceVal = new ArrayList<>();
    private double layak0, layak1;
    
    public Data ExecuteFuzzy(Data data){
        this.inputxno = new ArrayList<>();
        this.inputyno = new ArrayList<>();
        this.inputino = new ArrayList<>();
        this.valx = new ArrayList<>();
        this.valy = new ArrayList<>();
        this.vali = new ArrayList<>();
        this.layak0=0.0;
        this.layak1=0.0;
        
        /*---Fuzzification---*/
        fuzzification(data);
        
        /*---Inference---*/
        inference();        
        
        /*---Defuzzification---*/        
//        System.out.print(data.getHoax()+" ");
        data.setResult(mamdani());
//        data.setHoax(sugeno());
        
        return data;        
    }
    
    /*---Fuzzification---*/
    /*
    u:uphill, d:downhill
    
    emosi          
    0:0-40 | 0-30(1); 30-40(d)
    1:30-50 | 30-40(u); 40-50(d)
    2:40-60 | 40-50(u); 50-60(d)
    3:50-70 | 50-60(u); 60-70(d)
    4:60-80 | 60-70(u); 70-80(d)
    5:70-90 | 70-80(u); 80-90(d)
    6:90-100 | 90-100(u)
    
    provokasi
    0:0-60 | 0-50(1); 50-60(d)
    1:50-80 | 50-60(u); 60-70(1); 70-80(d)
    2:70-90 | 70-80(u); 80-90(d)
    3:80-100 | 80-90(up); 90-100(1)
    */
    private void fuzzification(Data data){
        //input emosi
        int n = data.getEmosi();
        
        if(n<30){
            this.inputxno.add(0);
            this.valx.add(1.0);
        }else if(n<40){
            this.inputxno.add(0);
            this.valx.add(this.calcDownhill(30, 40, n));
            this.inputxno.add(1);
            this.valx.add(this.calcUphill(30, 40, n));
        }else if (n<50){
            this.inputxno.add(1);
            this.valx.add(this.calcDownhill(40, 50, n));
            this.inputxno.add(2);
            this.valx.add(this.calcUphill(40, 50, n));
        }else if (n<60){
            this.inputxno.add(2);
            this.valx.add(this.calcDownhill(50, 60, n));
            this.inputxno.add(3);
            this.valx.add(this.calcUphill(50, 60, n));
        }else if (n<70){
            this.inputxno.add(3);
            this.valx.add(this.calcDownhill(60, 70, n));
            this.inputxno.add(4);
            this.valx.add(this.calcUphill(60, 70, n));
        }else if (n<80){
            this.inputxno.add(4);
            this.valx.add(this.calcDownhill(70, 80, n));
            this.inputxno.add(5);
            this.valx.add(this.calcUphill(70, 80, n));
        }else if (n<90){
            this.inputxno.add(5);
            this.valx.add(this.calcDownhill(80, 90, n));
            this.inputxno.add(6);
            this.valx.add(this.calcUphill(80, 90, n));
        }else{
            this.inputxno.add(6);
            this.valx.add(1.0);
        }
        
        //input provokasi
        n = data.getProvokasi();
        if(n<50){
            this.inputyno.add(0);
            this.valy.add(1.0);
        }else if(n<60){
            this.inputyno.add(0);
            this.valy.add(this.calcDownhill(50, 60, n));
            this.inputyno.add(1);
            this.valy.add(this.calcUphill(50, 60, n));
        }else if(n<70){            
            this.inputyno.add(1);
            this.valy.add(1.0);
        }else if(n<80){
            this.inputyno.add(1);
            this.valy.add(this.calcDownhill(70, 80, n));
            this.inputyno.add(2);
            this.valy.add(this.calcUphill(70, 80, n));
        }else if(n<90){
            this.inputyno.add(2);
            this.valy.add(this.calcDownhill(80, 90, n));
            this.inputyno.add(3);
            this.valy.add(this.calcUphill(80, 90, n));
        }else{
            this.inputyno.add(3);
            this.valy.add(1.0);
        }
    }    
    
    /*---Inference---*/    
    private void inference(){
        for(int i=0; i<this.inputxno.size(); i++){
            for(int j=0; j<this.inputyno.size(); j++){
                inferenceTab(i, j);
            }
        }
    }
    
    /*
    p=y, e=x
    p\e|0|1|2|3|4|5|6
      0|0|0|0|0|0|0|0
      1|0|0|0|0|1|1|1
      2|0|1|1|0|1|1|1
      3|1|1|1|1|1|1|1
    
    */
    private void inferenceTab(int x, int y){
        if(this.inputyno.get(y)==0){
            this.inputino.add(0);
        }else if(this.inputyno.get(y)==3){
            this.inputino.add(1);      
        }else if (this.inputxno.get(x)==1 && this.inputyno.get(y)==2){
            this.inputino.add(1);
        }else if(this.inputxno.get(x)==2 && this.inputyno.get(y)==2){
            this.inputino.add(1);   
        }else if (this.inputxno.get(x)==4 && this.inputyno.get(y)==1){
            this.inputino.add(1);
        }else if (this.inputxno.get(x)==4 && this.inputyno.get(y)==2){
            this.inputino.add(1);
        }else if (this.inputxno.get(x)==5 && this.inputyno.get(y)==1){
            this.inputino.add(1);
        }else if (this.inputxno.get(x)==5 && this.inputyno.get(y)==2){
            this.inputino.add(1);
        }else if (this.inputxno.get(x)==6 && this.inputyno.get(y)==1){
            this.inputino.add(1);
        }else if (this.inputxno.get(x)==6 && this.inputyno.get(y)==2){
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
    0:rendah:0-80 | 0-40(1); 0-60(downhill)
    1:tinggi:50-100 | 40-60(uphill); 60-100(1)
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
        
        for(int i=0; i<=30; i+=2){
            a+=(i*layak0);
            c+=layak0;
        }
        for(int i=32; i<=40; i+=2){
            double t= (70-i)*1.0/70-50;
            if(layak0<t && layak0>layak1){
                a+=(i*layak0);
                c+=layak0;
            }else if(layak1<t && layak0<layak1){
                b+=(i*layak1);
                d+=layak1;
            }else if(layak0!=0.0){
                a+=(i*t);
                c+=t;
            }
        }
        for(int i=42; i<=50; i+=2){
            double t= (i-50)*1.0/70-50;
            if(layak0<t && layak0>layak1){
                a+=(i*layak0);
                c+=layak0;
            }else if(layak1<t && layak0<layak1){
                b+=(i*layak1);
                d+=layak1;
            }else if(layak1!=0.0){
                a+=(i*t);
                c+=t;
            }
        }
        for(int i=52; i<=100; i+=2){
            b+=(i*layak1);
            d+=layak1;
        }               
                
        double y = (a+b)/(c+d);    
        
//        System.out.println(y);
                        
        if(y>40){
            return 1;
        }else{
            return 0;
        }
    }
    
    private int sugeno(){
        return 0;
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
}
