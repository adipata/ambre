package lu.pata.ambre.processor;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Decrypter {

    private int row;
    private int col;
    private int tempBitVal;
    private BufferedImage originalImage;

    public Decrypter(String fileName) throws IOException {
        this.originalImage = ImageIO.read(new File(fileName));
    }

    public String decryptLowLevelBits() {
        StringBuilder stringBuilder = new StringBuilder();
        Integer messageLength=null;

        while (col < originalImage.getWidth() - 1 && row < originalImage.getHeight()) {
            char c = 0;

            for (int i = 0; i < 8; ++i) {
                decryptBitCharacter();

                c <<= 1;
                c |= tempBitVal;
            }

            c = (char) (Integer.reverse(c << 24) & 0b11111111);

            stringBuilder.append(c);

            //The first 8 characters in the decoded data represent the size of the following data.
            //The size is stored as a 0 padded number (as string)
            if(stringBuilder.length()==8){
                messageLength = Integer.parseInt(stringBuilder.toString());
            }

            if(messageLength!=null && stringBuilder.length()==messageLength+8) break;
        }

        return stringBuilder.toString().substring(8);
    }

    private void decryptBitCharacter() {
        int mask = 0b11111111;
        int rgb = originalImage.getRGB(col, row);

        int b = rgb & mask;

        int newB = originalImage.getRGB(col + 1, row) & mask;

        //tempBitVal = Math.abs(newB - b);
        tempBitVal = newB - b;
        tempBitVal=tempBitVal&mask;

        col += 2;

        if (!(col < originalImage.getWidth() - 1)){
            row++;
            col = 0;
        }
    }
}

