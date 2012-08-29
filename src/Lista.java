/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import javax.swing.*;
/**
 *
 * @author Reed
 */
public class Lista extends javax.swing.JDialog {
    private SortedMap<String, Set<String>> file;
    private List<Fechas> ord;
    /**
     * Creates new form Lista
     */
    public Lista(java.awt.Frame parent, boolean modal, SortedMap<String,Set<String>> map) {
        super(parent, modal);
        initComponents();
        //Pasamos el mapa de nombres de los ficheros
        if (map != null){
            file = map;
        }
        //Creamos una lista vacía para todos los nombres tratados
        ord = new ArrayList<Fechas>();
        //Tratamos los nombres
        files();
        //Ordenamos la lista por versión más reciente
        ordena();
        //Creamos el modelo del jList igual al de la lista ordenada
        DefaultListModel li = new DefaultListModel();
        for (int i = 0; i < ord.size(); i++){
            li.addElement(ord.get(i).toString());
        }
        //Imponemos nuestro modelo
        jList1.setModel(li);
    }
    public String copia(){
        //Llamada del SwingWorker para recoger el nombre elegido
        int i = jList1.getSelectedIndex();
        Fechas f = ord.get(i);
        StringBuilder str = new StringBuilder().append(f.dia).append("_").append(f.mes).append("_").append(f.año).append("/").append(f.hora).append(";").append(f.minuto).append(";").append(f.segundo).append("-").append(f.mili);
        return str.toString();
    }
    private void ordena(){
        //Ordenamos según sea mayor
        for (int cont = 0; cont < ord.size(); cont++){
            //Cogemos un elemento y lo imponemos como el mínimo
            Fechas min = ord.get(cont);
            for (int i = cont + 1; i < ord.size(); i++){
                //Vamos comparando cada elemento con el elegido para ver cual es mas reciente
                Fechas rec = ord.get(i);
                int A = Integer.parseInt(min.año);
                int B = Integer.parseInt(rec.año);
                if (A < B){
                    ord.set(i, min);
                    ord.set(cont, rec);
                    i = ord.size();
                } else if (A == B){
                    A = Integer.parseInt(min.mes);
                    B = Integer.parseInt(rec.mes);
                    if (B > A){
                        ord.set(i, min);
                        ord.set(cont, rec);
                        i = ord.size();
                    } else if (A == B){
                        A = Integer.parseInt(min.dia);
                        B = Integer.parseInt(rec.dia);
                        if (B > A){
                            ord.set(i, min);
                            ord.set(cont, rec);
                            i = ord.size();
                        } else if (A == B){
                            A = Integer.parseInt(min.hora);
                            B = Integer.parseInt(rec.hora);
                            if (B > A){
                                ord.set(i, min);
                                ord.set(cont, rec);
                                i = ord.size();
                            } else if (A == B){
                                A = Integer.parseInt(min.minuto);
                                B = Integer.parseInt(rec.minuto);
                                if (B > A){
                                    ord.set(i, min);
                                    ord.set(cont, rec);
                                    i = ord.size();
                                } else if (A == B){
                                    A = Integer.parseInt(min.segundo);
                                    B = Integer.parseInt(rec.segundo);
                                    if (B > A){
                                        ord.set(i, min);
                                        ord.set(cont, rec);
                                        i = ord.size();
                                    } else if (A == B){
                                        A = Integer.parseInt(min.mili);
                                        B = Integer.parseInt(rec.mili);
                                        ord.set(i, min);
                                        ord.set(cont, rec);
                                        i = ord.size();
                                    }
                                }
                            }
                        }
                    }
                }
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //Botón Más reciente
        jList1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Botón Restaurar
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Se tomará la versión más reciente para restaurar.");
        jList1.setSelectedIndex(0);
    }//GEN-LAST:event_formWindowClosing

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
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}