/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Reed
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Crear Ventana principal
        Vista v = new Vista();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setTitle("Instalador Minecraft 1.2.5");
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
}
