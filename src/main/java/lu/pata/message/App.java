package lu.pata.message;

import com.skocur.imagecipher.Decrypter;
import com.skocur.imagecipher.encrypters.Encrypter;
import com.skocur.imagecipher.encrypters.LowLevelBitEncryption;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, NoSuchAlgorithmException {
        enc();
        dec();
    }

    static void enc() throws IOException, NoSuchAlgorithmException {
        Encrypter encrypter=new LowLevelBitEncryption("toppbild_skidakning_alvan_1920x800.jpg");
        System.out.println("Max size: "+encrypter.getMaxSize());

        int max=encrypter.getMaxSize()-50;
        int i=0;

        StringBuilder m=new StringBuilder();
        while(m.length()<max){
            i++;
            m.append("Coco Jambo("+i+") |");
        }

        System.out.println("Total i="+i);
        String msg=m.toString();
        System.out.println(msg);
        System.out.println(digest(msg));

        encrypter.encrypt(String.format("%08d", msg.length())+msg);
    }

    static void dec() throws IOException, NoSuchAlgorithmException {
        Decrypter decrypter = new Decrypter("toppbild_skidakning_alvan_1920x800.jpg");
        String message2 = decrypter.decryptLowLevelBits();
        System.out.println(digest(message2));
        System.out.println( message2 );
    }

    static String digest(String m) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(m.getBytes());
        byte[] digest = md.digest();

        return Hex.encodeHexString(digest).toUpperCase();
    }
}
