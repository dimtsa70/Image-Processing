


import java.io.*;
import java.util.*;

public class PPMImageStacker {
    ArrayList<PPMImage> imgList;
    PPMImage stackedImage;
    
    public PPMImageStacker(File dir) throws FileNotFoundException, UnsupportedFileFormatException {
        imgList = new ArrayList<>();
        PPMImage imgFile;
        if(!dir.exists()) {
            throw new FileNotFoundException("[ERROR] Directory " + dir + "does not exist!\n");
        }
        else if(dir.exists() && !dir.isDirectory()) {
            throw new FileNotFoundException("[ERROR] Directory " + dir + "is not a directory!\n");
        }
     
        File[] files = dir.listFiles();
        
        for(File file : files) {
            if(file.isFile() && !file.getName().endsWith(".ppm")) {
                throw new UnsupportedFileFormatException();
            }
        }
        for(File file : files) {
            imgFile = new PPMImage(file);
            imgList.add(imgFile);
        }
    }


    public void stack() {
        int i, j;
        int height = imgList.get(0).getHeight();
        int width = imgList.get(0).getWidth();
        int colordepth = imgList.get(0).getColorDepth();
        int totalImages = imgList.size();
        
        this.stackedImage = new PPMImage(imgList.get(0));
        
        this.stackedImage.height = height;
        this.stackedImage.width = width;
        this.stackedImage.colordepth = colordepth;
        this.stackedImage.array = new RGBPixel[height][width];
        
        short red = 0, green = 0, blue = 0;
       
        
        for(i = 0; i < height; i++) {
            for(j = 0; j < width; j++) {
                red = green = blue = 0;
                for(PPMImage image : imgList) {
                    red += image.array[i][j].getRed();
                    green += image.array[i][j].getGreen();
                    blue += image.array[i][j].getBlue();
                }
                red = (short) (red/totalImages);
                green = (short) (green/totalImages);
                blue = (short) (blue/totalImages);
                this.stackedImage.array[i][j] = new RGBPixel(red, green, blue);
            }
        }
    }

    public PPMImage getStackedImage() {
        return stackedImage;
    }
}
