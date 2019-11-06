package lu.pata.ambre.processor;

import java.awt.*;
import java.io.IOException;

public class LowLevelBitEncryption extends Encrypter {

    private int row;
    private int col;

    public LowLevelBitEncryption(String fileIn,String fileOut) throws IOException {
        super(fileIn,fileOut);
    }

    @Override
    public void encrypt(String text) {
        for (char c : text.toCharArray()) {
            encryptByte(c);
        }

        // Resetting values of cursor coordinates
        col = 0;
        row = 0;

        saveEncryptedData();
    }

    private void encryptByte(char character) {
        for (int i = 0; i < 8; ++i) {
            encryptBitCharacter((character >> i) & 0b1);
        }
    }

    private void encryptBitCharacter(int c) {
        int mask = 0b11111111;
        int rgb = image.getRGB(col, row);
        int r = (rgb >> 16) & mask;
        int g = (rgb >> 8) & mask;

        int b = image.getRGB(col + 1, row) & mask;
        b -= c;
        b=b&mask;

        Color color = new Color(r, g, b);
        image.setRGB(col, row, color.getRGB());

        col += 2;

        if(!(col < image.getWidth() - 1)){
            row++;
            col = 0;
        }
    }
}
