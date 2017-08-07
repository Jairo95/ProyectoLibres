/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libres.aplicacioneslibres.interfaces;

import com.libres.aplicacioneslibres.aplicacioneslibres.Reporte;
import com.libres.aplicacioneslibres.conexionbdd.Conexion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author mathcrap
 */
public class FacturasCliente extends javax.swing.JFrame {

    Conexion conn;
    String cliente, anio;
    JTable auxP;
    Reporte reporte;
    public FacturasCliente(Conexion conn, String cliente, String anio) {
        initComponents();
        this.setSize(1400, 400);
        
        this.conn = conn;
        this.anio = anio;
        this.cliente = cliente;
        
        this.jLabel1.setText(this.jLabel1.getText() + " " + this.cliente);
        this.jLabel2.setText(this.jLabel2.getText() + " " + this.anio);
        this.reporte = new Reporte(conn);
        
        String consulta = "SELECT  f.id_factura as id\n" +
",e.id_establecimiento as ruc\n" +
",e.nombre_establecimiento AS establecimiento\n" +
",cast((select total from tipo_gasto t where id_factura=f.id_factura\n" +
"and tipo='Alimentacion'\n" +
")as numeric) as alimentacion,cast(\n" +
"(select total from tipo_gasto t where id_factura=f.id_factura\n" +
"and tipo='Educacion'\n" +
")as numeric) as educacion,cast(\n" +
"(select total from tipo_gasto t where id_factura=f.id_factura\n" +
"and tipo='Salud'\n" +
")as numeric) as salud,cast(\n" +
"(select total from tipo_gasto t where id_factura=f.id_factura\n" +
"and tipo='Vestimenta'\n" +
")as numeric) as vestimenta,cast(\n" +
"(select total from tipo_gasto t where id_factura=f.id_factura\n" +
"and tipo='Vivienda'\n" +
")as numeric) as vivienda,cast(\n" +
"(select total from tipo_gasto t where id_factura=f.id_factura\n" +
"and tipo='Otro'\n" +
")as numeric) as otro\n" +
",cast(total_sin_iva as numeric) AS total_sin_iva\n" +
",cast(iva as numeric) AS iva\n" +
",cast(total_con_iva as numeric) AS total_con_iva\n" +
"from factura f, establecimiento e, cliente c\n" +
"where f.tipo_factura='Personal'\n" +
"and f.id_establecimiento=e.id_establecimiento\n" +
"and f.id_cliente=c.id_cliente\n" +
"and f.fecha_emision>= strftime('%d/%m/%Y','" + this.anio + "-01-01')\n" +
"and f.fecha_emision<strftime('%d/%m/%Y','" + this.anio + "-12-31')\n" +
"and c.id_cliente='" + this.cliente + "'";
        System.out.println(consulta);
        ArrayList elementos = conn.ddl(consulta);
        
        // Llenado de tabla
        int contador = 0;
        Object[] datos = new Object[12];

        for (Object elemento : elementos) { // todos los elementos de la consulta
            datos[contador] = elemento.toString();

            contador++;
            if(contador >= 12){
                contador = 0;
                agregarDatosTabla(datos);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbFacturas = new javax.swing.JTable();
        jbtPDF = new javax.swing.JButton();
        jbtExcel = new javax.swing.JButton();
        jbtSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("REPORTE DE FACTURAS PARA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jtbFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "RUC", "NOMBRE", "ALIMENTACION", "EDUCACION", "SALUD", "VESTIMENTA", "VIVIENDA", "OTRO", "TOTAL SIN IVA", "IVA", "TOTAL CON IVA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtbFacturas);

        jbtPDF.setText("TABLA COMO PDF");
        jbtPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPDFActionPerformed(evt);
            }
        });

        jbtExcel.setText("TABLA COMO EXCEL");
        jbtExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtExcelActionPerformed(evt);
            }
        });

        jbtSalir.setText("SALIR");
        jbtSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSalirActionPerformed(evt);
            }
        });

        jButton1.setText("FACTURA EN PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("FACTURA EN EXCEL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtExcel)
                        .addGap(75, 75, 75)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtSalir))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtPDF)
                    .addComponent(jbtExcel)
                    .addComponent(jbtSalir)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jLabel2.setText("AÑO ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSalirActionPerformed

    private void jbtPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPDFActionPerformed
        exportarPDF();
    }//GEN-LAST:event_jbtPDFActionPerformed

    private void jbtExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtExcelActionPerformed
        exportarExcel();
    }//GEN-LAST:event_jbtExcelActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        HashMap parametros = new HashMap();
        int fila = this.jtbFacturas.getSelectedRow();
        if(fila >= 0){
            String factura = this.jtbFacturas.getValueAt(fila, 0).toString();
            parametros.put("codeInvoice", factura);
            this.reporte.generar_reporte("reporteDetallePorFactura", parametros);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese una factura");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        HashMap parametros = new HashMap();
        int fila = this.jtbFacturas.getSelectedRow();
        if(fila > 0){
            String factura = this.jtbFacturas.getValueAt(fila, 0).toString();
            parametros.put("codeInvoice", factura);
            this.reporte.generar_excel("reporteDetallePorFactura", parametros);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese una factura");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void exportarExcel(){
        auxP = jtbFacturas;
        
        String file = JOptionPane.showInputDialog(null, "Ingrese el Nombre del archivo");
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        path = f.getAbsolutePath();
        if (auxP != null) {
            toExcel(auxP, new File(path + "/" + file + "-Personal.xls"));
        }
        JOptionPane.showMessageDialog(null, "Reportes exportados exitosamente");
        
    }
    
    public void toExcel(JTable table, File file) {
        try {
            TableModel model = table.getModel();
            FileWriter excel = new FileWriter(file);

            for (int i = 0; i < model.getColumnCount(); i++) {
                excel.write(model.getColumnName(i) + "\t");
            }

            excel.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i, j).toString() + "\t");
                }
                excel.write("\n");
            }

            excel.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    private void exportarPDF(){
        
    }
    
    private void agregarDatosTabla(Object[] datos){
        DefaultTableModel tabla = (DefaultTableModel)jtbFacturas.getModel();
        tabla.addRow(datos);

    }
    
    private void limpiarTabla(){
        DefaultTableModel tabla = (DefaultTableModel)jtbFacturas.getModel();
        while( tabla.getRowCount() > 0){
            tabla.removeRow(0);
        }
    }
    

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
            java.util.logging.Logger.getLogger(FacturasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Conexion nueva = new Conexion();
           
                new FacturasCliente(nueva, "1234567897", "2015").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtExcel;
    private javax.swing.JButton jbtPDF;
    private javax.swing.JButton jbtSalir;
    private javax.swing.JTable jtbFacturas;
    // End of variables declaration//GEN-END:variables
}
