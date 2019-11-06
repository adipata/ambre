package lu.pata.ambre.processor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Encrypter {

    private int imageWidth;
    private int imageHeight;
    protected String fileIn;
    protected String fileOut;
    protected BufferedImage image;

    public Encrypter(String fileIn,String fileOut) throws IOException {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
        this.image = ImageIO.read(new File(fileIn));
        this.imageWidth = image.getWidth();
    }

    public abstract void encrypt(String text);

    public int getMaxSize(){
        return (image.getWidth())*image.getHeight()/2/8-10;
    }

    protected void saveEncryptedData() {
        try {
            File file = new File(fileOut);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
