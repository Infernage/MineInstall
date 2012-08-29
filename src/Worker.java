/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Reed
 */
public class Worker extends SwingWorker <String, Integer>{
    private JLabel eti;
    private JProgressBar prog;
    private JButton bot, bot1, bot2, bot3;
    private boolean direct, exito = true;
    private Calendar C;
    private JFrame fr;
    //Constructor del SwingWorker
    public Worker (JLabel lab, JProgressBar pro, JButton boton, JButton boton1, JButton boton2, boolean temp, JButton boton3){
        eti = lab;
        prog = pro;
        bot = boton;
        bot1 = boton1;
        bot2 = boton2;
        direct = temp;
        bot3 = boton3;
        C = new GregorianCalendar();
    }
    //Método cuando se produce el this.execute()
    @Override
    protected String doInBackground() throws Exception {
        prog.setValue(5);
        String user = System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft", res = null;
        File fichsrc = new File("inst\\.minecraft");
        File fichdst = new File(user);
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
            int i = JOptionPane.showConfirmDialog(null, "Minecraft ya está instalado en su sistema. ¿Desea desinstalarlo?\n(Se realiza copia de seguridad)");
            if (i == 0){
                StringBuilder str = new StringBuilder().append(C.get(Calendar.DAY_OF_MONTH)).append("_").append(C.get(Calendar.MONTH) + 1).append("_").append(C.get(Calendar.YEAR));
                StringBuilder sts = new StringBuilder().append(C.get(Calendar.HOUR)).append(";").append(C.get(Calendar.MINUTE)).append(";").append(C.get(Calendar.SECOND)).append("-").append(C.get(Calendar.MILLISECOND));
                File copiaDel = new File(user);
                File copia = new File (System.getProperty("user.home") + "\\Desktop\\Copia Minecraft\\" + str.toString() + "\\" + sts.toString() + "\\.minecraft");
                if (copia.exists()){
                    int j = JOptionPane.showConfirmDialog(null, "Ya existe una copia hecha, ¿desea borrarla?");
                    if (j == 0){
                        borrarFichero(copia);
                        copia.mkdirs();
                        try {
                            copyDirectory(copiaDel, copia);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                        }
                        copia.mkdirs();
                    } 
                } else{
                    copia.mkdirs();
                    try {
                        copyDirectory(copiaDel, copia);
                    } catch (IOException ex) {
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
                }
                copiaDel = null;
            } else if(i == 1){
                eti.setText("No se puede continuar la instalación.");
                this.cancel(true);
            } else if (i == 2){
                eti.setText("Operación cancelada por el usuario.");
                this.cancel(true);
            }
        }
        eti.setText("Instalando Minecraft V1.2.5 ...");
        prog.setValue(20);
        Thread.sleep(3000);
        for (int i = 20; i < 70; i++){
            prog.setValue(i+1);
            Thread.sleep(100);
        }
        try{
            copyDirectory(fichsrc, fichdst);
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
                pw.print("@echo off");
                pw.println();
                pw.print("java -jar " + user + "\\RUN.jar");
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
    public void add (JFrame fa){
        fr = fa;
    }
    //Cuando termina, salta este método
    protected void done() {
        fr.setVisible(true);
        if (exito){
            eti.setText("Minecraft instalado con éxito en " + System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft");
            bot.setVisible(true);
            bot.setEnabled(true);
            prog.setValue(100);
            bot1.setVisible(false);
            bot2.setVisible(false);
            bot3.setVisible(false);
        } else{
            eti.setText("Minecraft no ha podido ser instalado.");
        }
    }
    //Se usa cuando se llama al método publish()
    /*protected void process(List<Integer> chunks){
        for (int i = 0; i < chunks.size(); i++){
            if (i != 0){
                prog.setValue((chunks.get(0)));
            }
        }
    }*/
    //Cuando se pulsa el botón cancelar, se cancela la instalación (ARREGLAR)
    private void cancelar(){
        eti.setText("Cancelando...");
        this.cancel(true);
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
