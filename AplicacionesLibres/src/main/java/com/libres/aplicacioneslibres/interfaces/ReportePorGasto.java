/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libres.aplicacioneslibres.interfaces;

import com.libres.aplicacioneslibres.aplicacioneslibres.Reporte;
import com.libres.aplicacioneslibres.conexionbdd.Conexion;
import java.awt.Component;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author mathcrap
 */
public class ReportePorGasto extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReportePorGasto
     */
    private Conexion conn;
    Reporte reporte;
    public ReportePorGasto(Conexion conn) {
        initComponents();
        
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        Component north = ui.getNorthPane();
        MouseMotionListener[] actions
                = (MouseMotionListener[]) north.getListeners(MouseMotionListener.class);
        for (MouseMotionListener action : actions) {
            north.removeMouseMotionListener(action);
        }
        this.conn = conn;
        this.cargar_anios();
        this.cargar_clientes();
        this.cargar_tipogasto();
        reporte = new Reporte(conn);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jcbCliente = new javax.swing.JComboBox<>();
        jcbAnio = new javax.swing.JComboBox<>();
        jcbTipoGasto = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        jLabel1.setText("REPORTE POR GASTO DE PRODUCTO");

        jLabel2.setText("Cliente");

        jLabel3.setText("Año");

        jLabel4.setText("Tipo de gasto");

        jcbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cliente" }));

        jcbAnio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año" }));

        jcbTipoGasto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo de gasto" }));

        jButton1.setText("PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("EXCEL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbAnio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbTipoGasto, 0, 185, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(518, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcbAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jcbTipoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(365, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String cliente = this.jcbCliente.getSelectedItem().toString();
        String anio = this.jcbAnio.getSelectedItem().toString();
        String tipogasto = this.jcbTipoGasto.getSelectedItem().toString();
        HashMap parametros = new HashMap();
        if(!cliente.equals("Cliente")){
            if(!anio.equals("Año")){
                if(!tipogasto.equals("Tipo de gasto")){
                    String inicioAnio = anio + "-01-01";
                    String finAnio = anio + "-12-31";
                    parametros.put("client", cliente);
                    parametros.put("startDate", inicioAnio);
                    parametros.put("endDate", finAnio);
                    parametros.put("family", tipogasto);
                    this.reporte.generar_reporte("reportePorGastoProducto", parametros);
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione tipo de gasto");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un año");
            }
            
        } else{
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String cliente = this.jcbCliente.getSelectedItem().toString();
        String anio = this.jcbAnio.getSelectedItem().toString();
        String tipogasto = this.jcbTipoGasto.getSelectedItem().toString();
        HashMap parametros = new HashMap();
        if(!cliente.equals("Cliente")){
            if(!anio.equals("Año")){
                if(!tipogasto.equals("Tipo de gasto")){
                    String inicioAnio = anio + "-01-01";
                    String finAnio = anio + "-12-31";
                    parametros.put("client", cliente);
                    parametros.put("startDate", inicioAnio);
                    parametros.put("endDate", finAnio);
                    parametros.put("family", tipogasto);
                    this.reporte.generar_excel("reportePorGastoProducto", parametros);
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione tipo de gasto");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un año");
            }
            
        } else{
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public void cargar_anios(){
        //combo_anio.removeAllItems();
        ArrayList anios=conn.cargarAnios();
        //anios.sort(null);
        for (Object obj:anios){
            jcbAnio.addItem(obj.toString());
        }
    }
    
    public void cargar_clientes(){
        String consulta = "select id_cliente from cliente";
        
        System.out.println(consulta);
        ArrayList elementos = conn.ddl(consulta);

        for (Object elemento : elementos) {
            this.jcbCliente.addItem(elemento.toString());  
            
        }
    }
    
    public void cargar_tipogasto(){
        String consulta = "select familia from producto\n" +
                "group by familia";
        
        System.out.println(consulta);
        ArrayList elementos = conn.ddl(consulta);

        for (Object elemento : elementos) {
            this.jcbTipoGasto.addItem(elemento.toString());  
            
        }
    }
        
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
            java.util.logging.Logger.getLogger(ReportePorGasto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportePorGasto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportePorGasto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportePorGasto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ReportePorGasto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> jcbAnio;
    private javax.swing.JComboBox<String> jcbCliente;
    private javax.swing.JComboBox<String> jcbTipoGasto;
    // End of variables declaration//GEN-END:variables
}
