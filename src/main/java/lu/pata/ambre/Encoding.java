package lu.pata.ambre;

import lu.pata.ambre.cli.ShellHelper;
import lu.pata.ambre.processor.Encrypter;
import lu.pata.ambre.processor.LowLevelBitEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
            @ShellOption(help = "Output image file",defaultValue = "ski.png") String fileOut,
            @ShellOption(help = "Data file",defaultValue = "letter.txt") String fileData
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

}
