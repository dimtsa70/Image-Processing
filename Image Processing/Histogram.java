/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Histogram {
    int[] histogram;
    int[] equalized;
    int height, width;
    
    //ypologismos plhthous pixel pou exoun
    //tade foteinothta kai apothikeusi autou
    //ston pinaka histogram
    public Histogram(YUVImage img) {
        height = img.height;
        width = img.width;
        int i;
        histogram = new int[236];
        for(i = 0; i < 236; i++) {
            histogram[i] = 0;
        }
        
        int value = 0;
        for(i = 0; i < img.height; i++) {
            for(int j = 0; j < img.width; j++) {
                value = img.array[i][j].getY();
                histogram[value]++;
            }
        }
    }
    
    //synarthsh ektypwshs istogrammatos
    //eikonas
    @Override
    public String toString() {
        int value;
        StringBuilder sb =  new StringBuilder();
        
        for(int i = 0; i < 236; i++) {
            sb.append("\n");
            sb.append(String.format("%3d",i)).append(".");
            
            sb.append("(").append(String.format("%4d", histogram[i]))
                .append(")\t");
            
            value = histogram[i];
 
            sb.append("#".repeat(value / 1000));
            
            value %= 1000;
            
            sb.append("$".repeat(value / 100));
            value %= 100;
            
            sb.append("@".repeat(value / 10));
            value %= 10;
            
            sb.append("*".repeat(value)); 
        }
        
        sb.append("\n");
        return sb.toString();
    }
    
    //ektypwsh toString se arxeio
    public void toFile(java.io.File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(this.toString());
        } catch (FileNotFoundException e) {
        }
    }
    
    //methodos eskisorropishs eikonas
    public void equalize() {
        int i;
        double[] probability = new double[236];
        int totalpixels = height * width;
        
        //ypologismos pithanothtas
        //pixel pou exoun X foteinotita
        for(i = 0; i < 236; i++) {
            probability[i] = (double) histogram[i] / totalpixels;
        }
        
        //pinakas athroistikhs katanomhs
        double[] cdf = new double[236];
        
        //ypologismos cdf gia [0-235]
        cdf[0] = probability[0];
        for(i = 1; i < 236; i++) {
            cdf[i] = cdf[i-1] + probability[i];
        }
        
        //pinakas eksidorropimenhs foteinothtas
        //kai ypologismos gia [0-235]
         equalized = new int[236];
        for(i = 0; i < 236; i++) {
            equalized[i] = (int) Math.floor(cdf[i] * 235);
        }
            
    }

    //synarthsh pou epistrefei thn eksisorrophmenh foteinotita
    public short getEqualizedLuminocity(int luminocity) {
        return (short) equalized[luminocity];
    }

}
