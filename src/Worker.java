/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.io.*;
import java.util.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
/**
 *
 * @author Reed
 */
public class Worker extends SwingWorker <String, Integer>{
    private JLabel eti;
    private JProgressBar prog;
    private JButton bot, bot1;
    private boolean direct, exito = true;
    private Calendar C;
    private Vista fr;
    //Constructor del SwingWorker
    public Worker (JLabel lab, JProgressBar pro, JButton boton, JButton boton1, boolean temp){
        eti = lab;
        prog = pro;
        bot = boton;
        bot1 = boton1;
        direct = temp;
        C = new GregorianCalendar();
    }
    //Método cuando se produce el this.execute()
    @Override
    protected String doInBackground() throws Exception {
        prog.setValue(5);
        String user = System.getProperty("user.home") + "\\AppData\\Roaming", res = null;
        File fichsrc = new File("inst\\inst.dat");
        File fichdst = new File(user + "\\.minecraft");
        File fti = new File (System.getProperty("user.home") + "\\AppData\\Roaming\\opt.cfg");
        eti.setText("Comprobando instalaciones anteriores...");
        Thread.sleep(2500);
        if (!fti.exists()){
            fti.createNewFile();
            PrintWriter pw = new PrintWriter (fti);
            pw.print(true);
            pw.close();
            File del = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Data");
            borrarData(del);
        }
        if (fichdst.isDirectory() && fichdst.exists()){
            int i = JOptionPane.showConfirmDialog(null, "Minecraft ya está instalado en su sistema. ¿Desea realizar una copia de seguridad?");
            File copiaDel = new File(user + "\\.minecraft");
            if (i == 0){
                StringBuilder str = new StringBuilder().append(C.get(Calendar.DAY_OF_MONTH)).append("_").append(C.get(Calendar.MONTH) + 1).append("_").append(C.get(Calendar.YEAR));
                StringBuilder sts = new StringBuilder().append(C.get(Calendar.HOUR_OF_DAY)).append(";").append(C.get(Calendar.MINUTE)).append(";").append(C.get(Calendar.SECOND)).append("-").append(C.get(Calendar.MILLISECOND));
                File copia = new File (System.getProperty("user.home") + "\\Desktop\\Copia Minecraft\\" + str.toString() + "\\" + sts.toString());
                File zip = new File(copia.getAbsolutePath() + "\\data.dat");
                if (copia.exists()){
                    int j = JOptionPane.showConfirmDialog(null, "Ya existe una copia hecha, ¿desea borrarla?");
                    if (j == 0){
                        borrarFichero(copia);
                        copia.mkdirs();
                        try {
                            //copyDirectory(copiaDel, copia);
                            ZipFile data = new ZipFile(zip);
                            ZipParameters par = new ZipParameters();
                            par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                            par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
                            par.setEncryptFiles(true);
                            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                            par.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                            par.setPassword("Minelogin 3.0.0");
                            data.createZipFileFromFolder(copiaDel, par, false, 0);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                        }
                    } 
                } else{
                    copia.mkdirs();
                    try {
                        eti.setText("Realizando copia de seguridad...");
                        //copyDirectory(copiaDel, copia);
                        ZipFile data = new ZipFile(zip);
                        ZipParameters par = new ZipParameters();
                        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
                        par.setEncryptFiles(true);
                        par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                        par.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                        par.setPassword("Minelogin 3.0.0");
                        data.createZipFileFromFolder(copiaDel, par, false, 0);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                    }
                }
                copia = null;
                borrarFichero (copiaDel);
                if (copiaDel.delete()){
                    eti.setText("Minecraft desinstalado con éxito.");
                    Thread.sleep(1000);
                } else{
                    eti.setText("No se ha podido desinstalar Minecraft. Cancelando...");
                    this.cancel(true);
                    JOptionPane.showMessageDialog(null, "Asegúrate de no tener ningún proceso que esté usando la carpeta de Minecraft.");
                    return null;
                }
                copiaDel = null;
            } else if (i == 1){
                borrarFichero(copiaDel);
                if (copiaDel.delete()){
                    eti.setText("Minecraft desinstalado con éxito.");
                    Thread.sleep(1000);
                } else{
                    eti.setText("No se ha podido desinstalar Minecraft. Cancelando...");
                    this.cancel(true);
                    JOptionPane.showMessageDialog(null, "Asegúrate de no tener ningún proceso que esté usando la carpeta de Minecraft.");
                    return null;
                }
            } else if (i == 2){
                this.cancel(true);
                return null;
            }
        }
        eti.setText("Instalando Minecraft 1.2.5 ...");
        prog.setValue(20);
        Thread.sleep(3000);
        for (int i = 20; i < 70; i++){
            prog.setValue(i+1);
            Thread.sleep(100);
        }
        try{
            ZipFile dat = new ZipFile(fichsrc);
            if (dat.isEncrypted()){
                dat.setPassword("Minelogin 3.0.0");
            }
            dat.extractAll(user);
            //copyDirectory(fichsrc, fichdst);
            prog.setValue(90);
            if (direct){
                eti.setText("Creando accesos directos del sistema...");
                Thread.sleep(1500);
                for (int i = prog.getValue(); i < 99; i++){
                    prog.setValue(i+1);
                    Thread.sleep(100);
                }
                Thread.sleep(1000);
                File exec = new File(System.getProperty("user.home") + "\\Desktop\\RunMinecraft.bat");
                exec.createNewFile();
                PrintWriter pw = new PrintWriter (exec);
                pw.print("echo Loading Minecraft...");
                pw.println();
                pw.print("@echo off");
                pw.println();
                pw.print("java -jar " + user + "\\.minecraft\\RUN.jar");
                pw.close();
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error en la instalación. Comprueba que no has borrado ningún archivo de la carpeta.\n" + e.getMessage());
            exito = false;
        }
        if (exito){
            res = "Done!";
        } else{
            res = "Error!";
        }
        return res;
    }
    //Añadir el JFrame
    public void add (Vista fa){
        fr = fa;
    }
    //Cuando termina, salta este método
    protected void done() {
        fr.setVisible(true);
        if (exito && !this.isCancelled()){
            eti.setText("Minecraft instalado con éxito en " + System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft");
            bot.setVisible(true);
            bot.setEnabled(true);
            prog.setValue(100);
            bot1.setVisible(false);
        } else if (!exito){
            eti.setText("Minecraft no ha podido ser instalado.");
        } else if (this.isCancelled()){
            fr.retry();
        }
    }
    //Copiar directorio de un sitio a otro
    private void copyDirectory(File srcDir, File dstDir) throws IOException {
        if (srcDir.isDirectory()) { 
            if (!dstDir.exists()) { 
                dstDir.mkdir(); 
            }
             
            String[] children = srcDir.list(); 
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(srcDir, children[i]), 
                    new File(dstDir, children[i])); 
            } 
        } else { 
            copy(srcDir, dstDir); 
        } 
    }
    //Copiar fichero de un sitio a otro
    private void copy(File src, File dst) throws IOException { 
        InputStream in = new FileInputStream(src); 
        OutputStream out = new FileOutputStream(dst); 
        eti.setText("Extrayendo en " + dst.getAbsolutePath());
         
        byte[] buf = new byte[4096]; 
        int len; 
        while ((len = in.read(buf)) > 0) { 
            out.write(buf, 0, len); 
        } 
        in.close(); 
        out.close(); 
    } 
    //Borrar fichero o directorio
    private void borrarFichero (File fich){
        File[] ficheros = fich.listFiles();
        for (int x = 0; x < ficheros.length; x++){
            if (ficheros[x].isDirectory()){
                borrarFichero(ficheros[x]);
            }
            eti.setText("Borrando... " + ficheros[x].getAbsolutePath());
            ficheros[x].delete();
        }
    }
    //Borrar la carpeta de datos al ejecutarse por primera vez
    private void borrarData (File fich){
        File[] ficheros = fich.listFiles();
        for (int x = 0; x < ficheros.length; x++){
            if (ficheros[x].isDirectory()){
                borrarData(ficheros[x]);
            }
            ficheros[x].delete();
        }
    }
}
