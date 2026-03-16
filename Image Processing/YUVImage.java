

package ce326.hw3;

import java.io.*;
import java.util.*;

public class YUVImage {
    int width, height;
    YUVPixel[][] array;
    
    //dhmiourgia YUVImage me parametrous
    //width, height kai dhmiourgia toy pinaka
    //YUVPixel me arxiko thesimo twn
    //Y=16, U=128, V=128
    public YUVImage(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.array = new YUVPixel[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.array[i][j] = new YUVPixel((short) 16, (short) 128, (short) 128);
            }
        }
    }
   
    //copy constructor
    public YUVImage(YUVImage copyImg) {
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.array = new YUVPixel[height][width];
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.array[i][j] = new YUVPixel(copyImg.array[i][j]);
            }
        }
    }
    
    //dhmiourgia YUVImage apo RGBImage (PPMImage)
    public YUVImage(RGBImage RGBImg) {
        this.height = RGBImg.height;
        this.width = RGBImg.width;
        this.array = new YUVPixel[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.array[i][j] = new YUVPixel(RGBImg.array[i][j]);
            }
        }
    }
    
    //dhmiourgia YUVImage me parametro arxeio ths morfhs ".yuv"
    public YUVImage(File file) throws FileNotFoundException, UnsupportedFileFormatException {
        int Y, U, V;
        Scanner fsc;
        String magicNumber, fileNm = file.getPath();
    
        //an den yparxei 'h den mporei na diavastei
        //tote den mporei na diavastei gia epeksergasia
        if(!file.exists() || !file.canRead()) {
            throw new FileNotFoundException();
        }

        try {
            fsc = new Scanner(new File(fileNm));
            magicNumber = fsc.next();
            if(!magicNumber.equals("YUV3")) {//an den einai ths morfhs ".yuv" tote vgazei exception
                throw new UnsupportedFileFormatException();
            }
            
            //thesimo twn width, height kai pinaka pixels
            this.width = fsc.nextInt();
            this.height = fsc.nextInt();
            this.array = new YUVPixel[height][width];
            
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    Y = fsc.nextInt();
                    U = fsc.nextInt();
                    V = fsc.nextInt();
                    this.array[i][j] = new YUVPixel((short) Y, (short) U, (short) V);
                }
            }
           
           
        } catch(NoSuchElementException ex) {
            throw new UnsupportedFileFormatException();
        }
        
    } 
     
    //voithitikh synarthsh pou me thn 
    //toFile ektypwnoun se arxeio file
    //thn epeksergasmenh eikona
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("YUV3\n");
        sb.append(width);
        sb.append(" ");
        sb.append(height);
        sb.append("\n");
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                sb.append(this.array[i][j].getY());
                sb.append(" ");
                sb.append(this.array[i][j].getU());
                sb.append(" ");
                sb.append(this.array[i][j].getV());
                sb.append("\n");
                
            }
            
        }
        
        return sb.toString();
    }
    
    public void toFile(java.io.File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(this.toString());
        } catch (FileNotFoundException e) {
        }
    }
    
    //methodos eksisoropishs foteinothtas eikonas
    public void equalize() {
        short YVal, newY;
        Histogram hist = new Histogram(this);
        
        //leptomereies sthn klash Histogram.java
        hist.equalize();
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                YVal = array[i][j].getY();
                newY = hist.getEqualizedLuminocity(YVal); //nea timh Y
                array[i][j].setY(newY);
            }
        }
    }
    
}