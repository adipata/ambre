package com.skocur.imagecipher.encrypters;

import java.awt.*;
import java.io.IOException;

public class MultiColorEncryption extends Encrypter {

    public MultiColorEncryption(String fileName) throws IOException {
        super(fileName);
    }

    @Deprecated
    @Override
    public void encrypt(String text) {
        int index = 0;
        boolean hasDone = false;

        for (int i = 0; i < image.getHeight() && !hasDone; ++i) {
            for (int j = 0; j < image.getWidth() && !hasDone; ++j) {
                if (index < text.length()) {
                    Color color = encryptBlue(text.charAt(index++), j, i);

                    image.setRGB(j, i, color.getRGB());
                } else {
                    hasDone = true;
                }
            }
        }

        saveEncryptedData();
    }

    @Deprecated
    private Color encryptBlue(char character, int posX, int posY) {
        int argb = image.getRGB(posX ,posY);

        int r = (argb >> 16) & 0b11111111;
        int g = (argb >> 8) & 0b11111111;

        int newB = 0;
        if (character < 255) {
            newB = character & 0b11111111;
        }

        return new Color(r, g, newB);
    }
}
