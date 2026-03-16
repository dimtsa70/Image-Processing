
package ce326.hw3;
import java.io.*;
import java.util.*;

public class PPMImage extends RGBImage {
    
    //dhmiourgia PPMImage lamvanontas parametro apo arxeio ths morfhs ".ppm"
    public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        int red, green, blue;
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
            if(!magicNumber.equals("P3")) {//an den einai ths morfhs "ppm" tote vgazei exception
                throw new UnsupportedFileFormatException();
            }
            
            //thesimo width, height, colordepth kai tou pinaka pixels
            this.width = fsc.nextInt();
            this.height = fsc.nextInt();
            this.colordepth = fsc.nextInt();
            this.array = new RGBPixel[height][width];
            
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    red = fsc.nextInt();
                    green = fsc.nextInt();
                    blue = fsc.nextInt();
                    this.array[i][j] = new RGBPixel((short) red, (short) green, (short) blue);
                }
            }
           
           
        } catch(NoSuchElementException ex) {
            throw new UnsupportedFileFormatException();
        }
        
    }
    
    //constructor pou xrisimopoieitai
    //gia to photo stacking
    public PPMImage(RGBImage img) {
        this.array = img.array;
        this.height = img.height;
        this.width = img.width;
        this.colordepth = img.colordepth;
    }
    
    //dhmiourgei PPMImage apo YUVImage
    public PPMImage(YUVImage img) {
        this.height = img.height;
        this.width = img.width;
        
        this.array = new RGBPixel[height][width];
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.array[i][j] = new RGBPixel(img.array[i][j]);
            }
        }
    }
    
    //voithitikh synartsh
    //pou kaleitai apo thn
    //toFile gia thn eggrafh
    //ths epeksergasmenhs eikonas
    //se arxeio file
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("P3\n");
        sb.append(width);
        sb.append(" ");
        sb.append(height);
        sb.append(" ");
        sb.append(255);
        sb.append("\n");
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                sb.append(this.array[i][j].getRed());
                sb.append(" ");
                sb.append(this.array[i][j].getGreen());
                sb.append(" ");
                sb.append(this.array[i][j].getBlue());
                sb.append("\n");
                
            }
            
        }
        
        return sb.toString();
    }

    //synarthsh ektypwshs
    //ths eikonas se arxeio file
    public void toFile(java.io.File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(this.toString());
        } catch (FileNotFoundException e) {
        }
    }

}

