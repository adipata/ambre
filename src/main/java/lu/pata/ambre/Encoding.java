package lu.pata.ambre;

import lu.pata.ambre.cli.ShellHelper;
import lu.pata.ambre.processor.Decrypter;
import lu.pata.ambre.processor.Encrypter;
import lu.pata.ambre.processor.LowLevelBitEncryption;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@ShellComponent
public class Encoding {

    @Autowired
    private ShellHelper shellHelper;

    @ShellMethod("Encode data into image.")
    public void enc(
            @ShellOption(help = "Original image file",defaultValue = "ski.jpg") String fileIn,
            @ShellOption(help = "Output image file (PNG)",defaultValue = "ski.png") String fileOut,
            @ShellOption(help = "Data file, usually a TXT",defaultValue = "letter.txt") String fileData
    ) throws IOException {
        Encrypter encrypter=new LowLevelBitEncryption(fileIn,fileOut);
        String content = Files.readString(Paths.get(fileData), StandardCharsets.UTF_8);
        if(content.length()>encrypter.getMaxSize()){
            shellHelper.printError("The maximum data size that can be encoded depends on the original image. For "+fileIn+" the maximum size is "+encrypter.getMaxSize()+". The file "+fileData+" has "+content.length()+".");
            return;
        }
        encrypter.encrypt(String.format("%08d", content.length())+content);
        shellHelper.print("Done");
    }

    @ShellMethod("Decode data in an image")
    public void dec(
            @ShellOption(help = "Image file",defaultValue = "ski.png") String fileIn,
            @ShellOption(help = "Output data from image to file (TXT)",defaultValue = "out.txt") String fileData
    ) throws IOException {
        try {
            Decrypter decrypter = new Decrypter(fileIn);
            String message2 = decrypter.decryptLowLevelBits();
            Files.write(Paths.get(fileData), message2.getBytes());
            shellHelper.print("Done");
        } catch (NumberFormatException ex){
            shellHelper.printError("Looks like the input image file does not contain data.");
        }
    }

    @ShellMethod("Get info about an input image")
    public void info(
            @ShellOption(help = "Image file",defaultValue = "ski.jpg") String fileIn
    ) throws IOException, TikaException {
        TikaConfig tika = new TikaConfig();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, fileIn);
        MediaType mimetype = tika.getDetector().detect(TikaInputStream.get(new FileInputStream(new File(fileIn))), metadata);
        shellHelper.print("Type: " + mimetype);

        BufferedImage image= ImageIO.read(new File(fileIn));
        int maxDataSize=(image.getWidth())*image.getHeight()/2/8-10;

        shellHelper.print("Size: "+image.getWidth()+"x"+image.getHeight());
        shellHelper.print("Max data: "+maxDataSize);


    }
}
