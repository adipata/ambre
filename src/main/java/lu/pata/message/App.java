package lu.pata.message;

import com.skocur.imagecipher.Decrypter;
import com.skocur.imagecipher.encrypters.Encrypter;
import com.skocur.imagecipher.encrypters.LowLevelBitEncryption;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Encrypter encrypter=new LowLevelBitEncryption("toppbild_skidakning_alvan_1920x800.jpg");

        String s="";
        for(int i=0;i<100;i++){

        }

        encrypter.encrypt("Coco Jambo:exit");

        Decrypter decrypter = new Decrypter("toppbild_skidakning_alvan_1920x800.jpg");
        String message2 = decrypter.decryptLowLevelBits();
        System.out.println( message2 );
    }
}
