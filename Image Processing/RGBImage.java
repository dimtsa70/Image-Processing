

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;


public class RGBImage implements Image {
    int width, height, colordepth;
    RGBPixel[][] array;
    public static final int MAX_COLORDEPTH = 255;
    
    public RGBImage() {}
    
    //dhmiourgei mia eikona me width, height, colordepth
    //kai pinaka pixel me diastaseis height x width
    public RGBImage(int width, int height, int colordepth) {
        this.width = width;
        this.height = height;
        this.colordepth = colordepth;
        this.array = new RGBPixel[height][width];
    }
    
    //copy constructor
    public RGBImage(RGBImage copyImg) {
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.colordepth = copyImg.colordepth;
        this.array = new RGBPixel[height][width];
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.array[i][j] = new RGBPixel(copyImg.array[i][j]);
            }
        }
    }
    
    //dhmiourgia eikonas apo YUVImage
    public RGBImage(YUVImage YUVImg) {
        this.height = YUVImg.height;
        this.width = YUVImg.width;
        this.array = new RGBPixel[height][width];
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.array[i][j] = new RGBPixel(YUVImg.array[i][j]);
            }
        }
    
    
    }
    
    //epistrefei to platos
    public int getWidth() {
        return this.width;
    }
    
    //epistrefei to ipsos
    public int getHeight() {
        return this.height;
    }
    
    //epistrefei megisth foteinotita
    public int getColorDepth() {
        return this.colordepth;
    }
    
    //epistrefei ena pixel tou pinaka apo thn thesh row, col
    RGBPixel getPixel(int row, int col) {
        return(this.array[row][col]);
    }
    void setPixel(int row, int col, RGBPixel pixel) {
        this.array[row][col] = pixel;
    }
    
    
    //synarthsh pou metatrepei thn eikona se aspromavri
    @Override
    public void grayscale() {
        RGBPixel setPixel;
        short red = 0, green = 0, blue = 0;
        short gray = (short) (0.3 * red + 0.59 * green + 0.11 * blue);//ypologismos gray apoxrwshs
        int i,j;
        for(i = 0; i < this.height; i++) {//gia kathe pixel tou pinaka ypologizoume to gray
            for(j = 0; j < this.width; j++) {//kai to vazoume sta red, green, blue
                
                red = this.array[i][j].getRed();
                green = this.array[i][j].getGreen();
                blue = this.array[i][j].getBlue();
                gray = (short) (0.3 * red + 0.59 * green + 0.11 * blue);
                this.array[i][j].setRed(gray);
                this.array[i][j].setGreen(gray);
                this.array[i][j].setBlue(gray);
            }
            
        }
        
    }
    
    //synarthsh poy diplasiazei to megethos
    //ths eikonas
    @Override
    public void doublesize() {
        int i, j;
        int newHeight = 2 * this.height;
        int newWidth = 2 * this.width;
        
        //dhmiourgia neou pinaka me diplasiasmeno megethos
        RGBPixel[][] resizeArray = new RGBPixel[newHeight][newWidth];
        
        //h thesh array[i][j] antigrafetai
        //stis nees theseis tou resizeArray
        //[2*i][2*j], [2*i+1][2*j], [2*i][2*j+1],
        //[2*i+1][2*j+1] 
        for(i = 0; i < this.height; i++) {
            for(j = 0; j < this.width; j++) {
                if(2*i < newHeight && 2*j < newWidth)
                   resizeArray[2*i][2*j] = this.array[i][j];
                
                if(2*i + 1 < newHeight && 2*j < newWidth)
                    resizeArray[2*i+1][2*j] = this.array[i][j];
                
                if(2*i < newHeight && 2*j + 1 < newWidth)
                    resizeArray[2*i][2*j+1]  = this.array[i][j];
                if(2*i + 1 < newHeight && 2*j + 1 < newWidth)
                    resizeArray[2*i+1][2*j+1] = this.array[i][j];
            }
        }
        //thesimo twn height,width,array me
        //tis nees times kai pinaka antistoixa
        this.height = newHeight;
        this.width = newWidth;
        this.array = resizeArray;
    }
    
    //synarthsh pou ypodiplasiazei to
    //megethos ths eikonas
    @Override
    public void halfsize() {
        int i,j, sumPixel;
        int newHeight = this.height / 2;
        int newWidth = this.width / 2;
        
        //neos ypodiplasiasmenos pinakas
        RGBPixel[][] halfArray = new RGBPixel[newHeight][newWidth];
        
        //h thesh halfArray[i][j] prokyptei
        //apo tis theseis [2*i+1][2*j+1],
        //[2*i][2*j], [2*i+1][2*j], [2*i][2*j+1]
        //to this.array, ek twn opoiwn
        //ypologizetai o mesos oros gia kathe red, green, blue
        for(i = 0; i < newHeight; i++) {
            for(j = 0; j < newWidth; j++) {
                RGBPixel p1 = this.array[2 * i][2 * j];
                RGBPixel p2 = this.array[2 * i + 1][2 * j];
                RGBPixel p3 = this.array[2 * i][2 * j + 1];
                RGBPixel p4 = this.array[2 * i + 1][2 * j + 1];

            
                short red = (short) ((p1.getRed() + p2.getRed() + p3.getRed() + p4.getRed()) / 4);
                short green = (short) ((p1.getGreen() + p2.getGreen() + p3.getGreen() + p4.getGreen()) / 4);
                short blue = (short) ((p1.getBlue() + p2.getBlue() + p3.getBlue() + p4.getBlue()) / 4);

            halfArray[i][j] = new RGBPixel(red, green, blue);
                
            }
        }
        
        this.height = newHeight;
        this.width = newWidth;
        this.array = halfArray;
        
    }
    
    //synarthsh pou peristrefei
    //thn eikona wrologiaka 90 moires
    @Override
    public void rotateClockwise() {
        int i, j, temp;
        
        RGBPixel[][] rotateArray = new RGBPixel[this.width][this.height];
        for(i = 0; i < this.height; i++) {
            for(j = 0; j < this.width; j++) {
               rotateArray[j][this.height-1-i] = this.array[i][j]; 
            }
        }
        temp = this.height;
        this.height = this.width;
        this.width = temp;
        this.array = rotateArray;
    }
    
}

