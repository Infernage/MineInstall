/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
/**
 *
 * @author Reed
 */
public class Lista extends javax.swing.JDialog {
    private SortedMap<String, Set<String>> file;
    private List<Fechas> ord;
    private List<Fechas> ordenada;
    private DefaultMutableTreeNode abuelo;
    private DefaultTreeModel modelo;
    private List<Fechas> encriptada;
    private List<Fechas> decriptada;
    private Fechas res;
    /**
     * Creates new form Lista
     */
    public Lista(java.awt.Frame parent, boolean modal, SortedMap<String,Set<String>> map) {
        super(parent, modal);
        res = null;
        abuelo = new DefaultMutableTreeNode("Copias de seguridad");
        modelo = new DefaultTreeModel(abuelo);
        initComponents();
        //Pasamos el mapa de nombres de los ficheros
        if (map != null){
            file = map;
        }
        //Creamos una lista vacía para todos los nombres tratados
        ord = new ArrayList<Fechas>();
        ordenada = new ArrayList<Fechas>();
        encriptada = new ArrayList<Fechas>();
        decriptada = new ArrayList<Fechas>();
        //Tratamos los nombres
        files();
        //Ordenamos la lista por versión más reciente
        ordenar();
        //Cambiamos el modelo del árbol y lo adecuamos
        arbol();
    }
    private void arbol(){
        String arb = System.getProperty("user.home") + "\\Desktop\\Copia Minecraft";
        for (int i = 0; i < ordenada.size(); i++){
            String temp = copy(ordenada.get(i));
            StringTokenizer token = new StringTokenizer (temp, "/"); //Separamos la carpeta fecha de la carpeta hora
            String dia = token.nextToken();
            String hora = token.nextToken();
            File mine = new File(arb + "\\" + dia + "\\" + hora + "\\.minecraft");
            if (mine.exists()){
                decriptada.add(ordenada.get(i));
            } else{
                encriptada.add(ordenada.get(i));
            }
        }
        DefaultMutableTreeNode enc = new DefaultMutableTreeNode("Encriptados");
        DefaultMutableTreeNode dec = new DefaultMutableTreeNode("Desenencriptados");
        modelo.insertNodeInto(enc, abuelo, 0);
        modelo.insertNodeInto(dec, abuelo, 1);
        for (int i = 0; i < encriptada.size(); i++){
            modelo.insertNodeInto(new DefaultMutableTreeNode(encriptada.get(i).toString()), enc, i);
        }
        for (int i = 0; i < decriptada.size(); i++){
            modelo.insertNodeInto(new DefaultMutableTreeNode(decriptada.get(i).toString()), dec, i);
        }
    }
    private String copy(Fechas f){
        StringBuilder str = new StringBuilder().append(f.dia).append("_").append(f.mes).append("_").append(f.año).append("/").append(f.hora).append(";").append(f.minuto).append(";").append(f.segundo).append("-").append(f.mili);
        return str.toString();
    }
    public String copia(){
        //Llamada del SwingWorker para recoger el nombre elegido
        StringBuilder str = new StringBuilder().append(res.dia).append("_").append(res.mes).append("_").append(res.año).append("/").append(res.hora).append(";").append(res.minuto).append(";").append(res.segundo).append("-").append(res.mili);
        return str.toString();
    }
    private void ordenar(){
        //Cogemos el primer elemento
        Fechas fecha = ord.get(0);
        for (int i = 1; i < ord.size(); i++){
            if (!fecha.compare(ord.get(i))){ //Si el elemento actual es más reciente, lo cogemos
                fecha = ord.get(i);
            }
        }
        //Cogemos el elemento más reciente y lo removemos de la otra lista
        ordenada.add(fecha);
        ord.remove(fecha);
        if (!ord.isEmpty()){
            ordenar();
        }
    }
    private void files(){
        //Cogemos las llaves del Map
        Set<String> day = file.keySet();
        Iterator <String> it = day.iterator();
        while (it.hasNext()){
            //Las recorremos y cogemos sus nombres para tratarlos
            String temp = it.next();
            StringTokenizer token = new StringTokenizer(temp, "_");
            String dia = token.nextToken();
            String mes = token.nextToken();
            String año = token.nextToken();
            //Cogemos los valores de cada llave
            Set<String> hour = file.get(temp);
            Iterator<String> i = hour.iterator();
            while (i.hasNext()){
                //Hacemos lo mismo que con las llaves
                String tem = i.next();
                StringTokenizer toke = new StringTokenizer (tem, ";-");
                String hora = toke.nextToken();
                String minuto = toke.nextToken();
                String segundo = toke.nextToken();
                String mili = toke.nextToken();
                //Creamos un String tratado con los parámetros necesarios
                String fecha = dia + "/" + mes + "/" + año + "-" + hora + "/" + minuto + "/" + segundo + "/" + mili;
                //Añadimos una nueva clase a la lista
                ord.add(new Fechas(fecha));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButton1.setText("Restaurar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Más reciente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Copias de seguridad encontradas...");

        jTree1.setModel(this.modelo);
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 87, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //Botón Más reciente
        res = ordenada.get(0);
        JOptionPane.showMessageDialog(null, "Elegida la opción: " + res.toString());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Botón Restaurar
        if (res != null){
            this.setVisible(false);
        } else{
            JOptionPane.showMessageDialog(null, "No has marcado ninguna opción");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Se tomará la versión más reciente para restaurar.");
        res = ordenada.get(0);
    }//GEN-LAST:event_formWindowClosing

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // TODO add your handling code here:
        Object [] nodes = evt.getPath().getPath();
        for (Object nodo : nodes){
            for (int i = 0; i < encriptada.size(); i++){
                if (nodo.toString().equals(encriptada.get(i).toString())){
                    res = encriptada.get(i);
                }
            }
            for (int i = 0; i < decriptada.size(); i++){
                if (nodo.toString().equals(decriptada.get(i).toString())){
                    res = decriptada.get(i);
                }
            }
        }
    }//GEN-LAST:event_jTree1ValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Lista dialog = new Lista(new javax.swing.JFrame(), true, null);
                dialog.setLocationRelativeTo(null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
