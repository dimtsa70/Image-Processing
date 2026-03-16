


//Exceprions gia mh ypostirizomena format eikonwn
public class UnsupportedFileFormatException extends java.lang.Exception {
    String msg;
    public UnsupportedFileFormatException() {}
    
    public UnsupportedFileFormatException(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "[UnsupportedFileFormatException] " + msg;
    }
}
