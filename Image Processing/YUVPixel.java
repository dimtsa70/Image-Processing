package ce326.hw3;

public class YUVPixel {
    private int pixel;
    
    //dhmiourgia pixel me parametrous Y, U, V
    public YUVPixel(short Y, short U, short V) {
        int setY = (int) Y;
        setY = setY << 16;
        int setU = (int) U;
        setU = setU << 8;
        int setV = (int) V;
        pixel = (setY) | (setU) | (setV);
    }
    
    //antigrafh pixel
    public YUVPixel(YUVPixel pixel) {
        this.pixel = pixel.pixel;
    }
    
    //dhmiourgia pixel vasei RGBPixel
    public YUVPixel(RGBPixel pixel) {
        int setY = ((66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128) >> 8) +16;
        int setU = ((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128) >> 8) +128;
        int setV = ((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128) >> 8) +128;
        
        this.pixel = (setY << 16) | (setU << 8) | (setV);
    }
    
    //epistrefei to Y
    public short getY() {
        return((short) (pixel >> 16));
    }
    
    //epistrefei to U
     public short getU() {
        
         return ((short) (pixel >> 8 & 0xFF));
    }
     
    //epistrefei to V
    public short getV() {
        return (short) (pixel & 0xFF);
    }
    
    //thetei to Y
    public void setY(short Y) {
        int setY = (Y & 0xFF) << 16;
        pixel = (pixel & 0xFF00FFFF) | setY;
    }
    
    //thetei to U
    public void setU(short U) {
        int setU = (U & 0xFF) << 8;
        pixel = (pixel & 0xFFFF00FF) | setU;
    }
    
    //thetei to V
    public void setV(short V) {
        int setV = (int) V;
        pixel = (pixel & 0xFFFFFF00) | setV;
    }
    
}
