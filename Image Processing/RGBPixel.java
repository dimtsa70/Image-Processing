package ce326.hw3;

public class RGBPixel {
    private int pixel;
    
    //dhmiourgia pixel me parametrous red, green, blue
    public RGBPixel(short red, short green, short blue) {
        int setRed = (int) red;
        setRed = setRed << 16;
        int setGreen = (int) green;
        setGreen = setGreen << 8;
        int setBlue = (int) blue;
        pixel = (setRed) | (setGreen) | (setBlue);
    }
    
    //antigrafh pixel
    public RGBPixel(RGBPixel pixel) {
       this.pixel = pixel.pixel;
    }
    
    //dhmiourgia RGBPixel apo YUVPixel
    public RGBPixel(YUVPixel pixel) {
      int C = pixel.getY() - 16;
      int D = pixel.getU() - 128;
      int E = pixel.getV() - 128;
      
      short YtoR = clip((short) ((298*C + 409*E + 128) >> 8));
      short UtoG = clip((short) ((298*C - 100*D - 208*E + 128) >> 8));
      short VtoB = clip((short) ((298*C + 516*D + 128) >> 8));
      
      setRed(YtoR);
      setGreen(UtoG);
      setBlue(VtoB);
    }
    
    short clip(short value) {
        if(value > 255) {
            value = 255;
        }
        else if(value < 0) {
            value = 0;
        }
    return value;
    }
    
    //epistrefei thn timh tou red
    public short getRed() {
        return((short) (pixel >> 16));
    }
    
    //epistrefei thn timh tou green
     public short getGreen() {
        
         return ((short) (pixel >> 8 & 0xFF));
    }
     
     //epistrefei thn timh tou blue
    public short getBlue() {
        return (short) (pixel & 0xFF);
    }
    
    //thetei to red meros me thn timh ths parametrou
    public void setRed(short red) {
        int setRed = (red & 0xFF) << 16;
        pixel = (pixel & 0xFF00FFFF) | setRed;
    }
    
    //thetei to green meros me thn timh ths parametrou
    public void setGreen(short green) {
        int setGreen = (green & 0xFF) << 8;
        pixel = (pixel & 0xFFFF00FF) | setGreen;
    }
    
    //thetei to blue meros me thn timh ths parametrou
    public void setBlue(short blue) {
        int setBlue = (int) blue;
        pixel = (pixel & 0xFFFFFF00) | setBlue;
    }
   
    //epistrefei ena pixel
    public int getRGB() {
        return pixel;
    }
    
    //thetei ena pixel
    final public void setRGB(int value) {
       pixel = value;
    }
    
    //ektypwsh enos RGB
    @Override
    public String toString() {
        String redStr = Integer.toString(getRed());
        String greenStr = Integer.toString(getGreen());
        String blueStr = Integer.toString(getBlue());
        return(redStr + " " + greenStr + " " + blueStr);
    }
 
}
