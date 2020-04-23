package back_end;

import front_end.ConversorDeImagem_CBR;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Miguel
 */
public class Back {

    private Path dirPastas;

    public void setDirectory(String dir) {
        this.dirPastas = Paths.get(dir);
    }

    public void convertImagesInCBR() {
        if (this.dirPastas == null) {
            this.dirPastas = Paths.get("C:\\Users\\Miguel\\Desktop\\folder"); //default directory
        }

        int cont = 0;
        
        boolean primaryFolder = false;
        try (DirectoryStream<Path> getName = Files.newDirectoryStream(this.dirPastas)) {
            for (Path getNamePrimaryFolder : getName) {
                if (primaryFolder == false) {
                    cont = Integer.parseInt(getNamePrimaryFolder.getFileName().toString().substring(9));
                }
                primaryFolder = true;
            }
        } catch (IOException e) {
            
        }

        primaryFolder = false;
        Path cbrFile = this.dirPastas.resolve("Capítulo" + cont + ".cbr");
        Path dir2;
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.dirPastas)) {
            for (Path camadaPath1 : stream) {
                System.out.println(camadaPath1.getFileName()); //show folders names
                dir2 = this.dirPastas.resolve(camadaPath1);

                try (ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(cbrFile.toFile()));
                        DirectoryStream<Path> stream2 = Files.newDirectoryStream(dir2)) {  
                    cont = Integer.parseInt(camadaPath1.getFileName().toString().substring(9));
                    cont++;
                    System.out.println("->>>" + cont); //show cont indice
                    cbrFile = this.dirPastas.resolve("Capítulo" + cont + ".cbr");
                    for (Path camadaPath2 : stream2) {
                        ZipEntry zipEntry = new ZipEntry(camadaPath2.getFileName().toString());
                        zipStream.putNextEntry(zipEntry); //prepare the next entry
                        FileInputStream fileInputStream = new FileInputStream(camadaPath2.toFile());
                        byte[] buff = new byte[2048];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buff)) > 0) {
                            zipStream.write(buff, 0, bytesRead);
                        }
                        zipStream.closeEntry();
                        fileInputStream.close();
                    }
                    System.out.println("------------------------------------------"); //show separator

                } catch (IOException e) {
//                    e.printStackTrace();
                    if (!"java.nio.file.NotDirectoryException".equals(e.getClass().getName())) {
                        ConversorDeImagem_CBR.callJOptionPaneErrorMensage();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (!"java.nio.file.NotDirectoryException".equals(e.getClass().getName())) {
                ConversorDeImagem_CBR.callJOptionPaneErrorMensage();
            }
        }

        if (ConversorDeImagem_CBR.returnErro == false) {
            ConversorDeImagem_CBR.callJOptionPaneConfirmMensage();
            System.out.println("Convertido com sucesso");
        }
        ConversorDeImagem_CBR.returnErro = false;
        cont = 0;
    }
}
