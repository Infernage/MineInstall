/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.io.*;
import java.util.*;
import net.lingala.zip4j.core.ZipFile;
/**
 *
 * @author Reed
 */
public class Unworker extends SwingWorker<Integer, Integer>{
    //Clase de desinstalación
    private JLabel eti;
    private JProgressBar pro;
    private JButton desins, salir, finalizar, inst;
    private JFrame fr;
    private File rest, newRest; //Minecraft a restaurar
    private SortedMap<String,Set<String>> fich; //Lista de todas las copias de Minecraft
    //Constructor con todas las variables necesarias
    public Unworker (JLabel A, JProgressBar B, JButton C, JButton D, JButton E, JButton F, JFrame G){
        eti = A;
        pro = B;
        finalizar = C;
        salir = D;
        desins = E;
        inst = F;
        fr = G;
        pro.setValue(0);
        eti.setText("Recopilando información de instalaciones anteriores...");
        fich = new TreeMap<String,Set<String>>();
    }
    @Override
    protected Integer doInBackground() throws Exception {
        Thread.sleep(3000);
        //Cogemos la base de datos de las copias de seguridad
        File copia = new File(System.getProperty("user.home") + "\\Desktop\\Copia Minecraft");
        //Preguntamos si desea restaurar o desinstalar
        int as = JOptionPane.showConfirmDialog(null, "¿Desea restaurar una copia realizada anteriormente?");
        if (as == 0){ //Restaurar
            ficheros(copia);//Listamos los ficheros que haya en copia
            Lista vist = new Lista(fr, true, fich);//Creamos un Dialog para ver por cual restauramos
            vist.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            vist.setTitle("Copias");
            vist.setLocationRelativeTo(null);
            vist.setVisible(true);
            String fi = vist.copia(); //Obtenemos la seleccionada
            StringTokenizer token = new StringTokenizer (fi, "/"); //Separamos la carpeta fecha de la carpeta hora
            String dia = token.nextToken();
            String hora = token.nextToken();
            token = null;
            rest = new File(copia.getAbsolutePath() + "\\" + dia + "\\" + hora + "\\.minecraft"); //Aplicamos el path del antiguo sistema
            newRest = new File(copia.getAbsolutePath() + "\\" + dia + "\\" + hora + "\\data.dat"); //Aplicamos el path del nuevo sistema
            pro.setValue(10);
            int temp = 0;
            if (!rest.exists() && !newRest.exists()){//Si las carpetas no existe, saltamos con error
                JOptionPane.showMessageDialog(null, "Error, no se ha podido encontrar la restauración.");
            } else if (rest.exists() && !newRest.exists()){
                temp = 1;
            } else {
                temp = 2;
            }
            if (temp == 1){
                eti.setText("Preparando desinstalación...");
                Thread.sleep(5000);
                File mine = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft");
                if (mine.exists() && mine.isDirectory()){
                    borrarFichero(mine);//Borramos el Minecraft instalado
                    eti.setText("Minecraft desinstalado con éxito.");
                    pro.setValue(50);
                    Thread.sleep(2000);
                    eti.setText("Restaurando copia de seguridad de Minecraft...");
                    Thread.sleep(1000);
                }
                mine.mkdirs();
                copyDirectory(rest, mine);//Instalamos la restauración
                eti.setText("Recopilando información adicional...");
                Thread.sleep(5000);
                for (int i = 50; i < 90; i++){
                    pro.setValue(i+1);
                    Thread.sleep(100);
                }
                Thread.sleep(2000);
                File exec = new File(System.getProperty("user.home") + "\\Desktop\\RunMinecraft.bat");
                if(!exec.exists()){//Comprobamos si existe acceso directo
                    exec.createNewFile();
                    PrintWriter pw = new PrintWriter (exec);
                    pw.print("@echo off");
                    pw.println();
                    pw.print("java -jar " + System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\RUN.jar");
                    pw.close();
                }
                eti.setText("Minecraft restaurado con éxito!");
            } else if (temp == 2){
                eti.setText("Preparando desinstalación...");
                Thread.sleep(5000);
                File mine = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft");
                if (mine.exists() && mine.isDirectory()){
                    borrarFichero(mine);//Borramos el Minecraft instalado
                    eti.setText("Minecraft desinstalado con éxito.");
                    pro.setValue(50);
                    Thread.sleep(2000);
                    eti.setText("Restaurando copia de seguridad de Minecraft...");
                    Thread.sleep(1000);
                }
                //Instalamos la restauración
                ZipFile dat = new ZipFile(newRest);
                if (dat.isEncrypted()){
                    dat.setPassword("Minelogin 3.0.0");
                }
                dat.extractAll(System.getProperty("user.home") + "\\AppData\\Roaming");
                eti.setText("Recopilando información adicional...");
                Thread.sleep(5000);
                for (int i = 50; i < 90; i++){
                    pro.setValue(i+1);
                    Thread.sleep(100);
                }
                Thread.sleep(2000);
                File exec = new File(System.getProperty("user.home") + "\\Desktop\\RunMinecraft.bat");
                if(!exec.exists()){//Comprobamos si existe acceso directo
                    exec.createNewFile();
                    PrintWriter pw = new PrintWriter (exec);
                    pw.print("@echo off");
                    pw.println();
                    pw.print("java -jar " + System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\RUN.jar");
                    pw.close();
                }
                eti.setText("Minecraft restaurado con éxito!");
            }
        } else{//Desinstalación
            pro.setValue(30);
            eti.setText("Preparando desinstalación...");
            Thread.sleep(5000);
            File mine = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft");
            if (mine.exists() && mine.isDirectory()){
                borrarFichero(mine);
                eti.setText("Minecraft desinstalado con éxito.");
                pro.setValue(50);
                Thread.sleep(2000);
                mine.deleteOnExit();
            }
            eti.setText("Recopilando información adicional...");
            Thread.sleep(5000);
            for (int i = 50; i < 90; i++){
                pro.setValue(i+1);
                Thread.sleep(100);
            }
            Thread.sleep(2000);
            File exec = new File(System.getProperty("user.home") + "\\Desktop\\RunMinecraft.bat");
            if (exec.exists()){//Si existe el acceso directo, lo borramos
                exec.delete();
            }
            eti.setText("Minecraft desinstalado con éxito!");
        }
        return 0;
    }
    @Override
    protected void done(){
        finalizar.setEnabled(true);
        finalizar.setVisible(true);
        pro.setValue(100);
        salir.setVisible(false);
        desins.setVisible(false);
        inst.setVisible(false);
        fr.setVisible(true);
    }
    //Método para listar los ficheros copia
    private void ficheros (File copia){
        File [] copias;
        File [] copias2;
        if (copia.exists() && copia.isDirectory()){
            copias = copia.listFiles();
            for (int i = 0; i < copias.length; i++){
                String day = copias[i].getName();
                if (copias[i].isDirectory()){
                    copias2 = copias[i].listFiles();
                    Set<String> hour = new HashSet<String>();
                    for (int j = 0; j < copias2.length; j++){
                        hour.add(copias2[j].getName());
                        //JOptionPane.showMessageDialog(null, copias[i] + "/" + copias2[j]);
                    }
                    fich.put(day, hour);
                }
            }
        } else{
            JOptionPane.showMessageDialog(null, "No existen copias realizadas.");
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
}

