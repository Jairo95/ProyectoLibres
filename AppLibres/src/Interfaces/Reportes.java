/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import conexionBDD.Conexion;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.MouseMotionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author vengatus
 */
public class Reportes extends javax.swing.JInternalFrame {

    Conexion conn;
    String cedula_usuario;
    int anio;
    JTable auxP, auxN;

    public Reportes(Conexion conn, String cedula_usuario, int anio) {
        initComponents();
        lbl_Reporte.setText("REPORTE DEL AÑO " + anio);
        this.conn = conn;
        this.cedula_usuario = cedula_usuario;
        this.anio = anio;

        combo_Establecimientos.addItem("Todos");
        ArrayList establecimientos = conn.cargarEstablecimiento();
        for (Object est : establecimientos) {
            combo_Establecimientos.addItem(est.toString());
        }
        combo_mes.setEnabled(false);
        combo_tiempo.setEnabled(false);
        combo_Establecimientos.setEnabled(false);
        panel_mes.setVisible(false);
        panel_rango.setVisible(false);

        //fecha minima
        date_inicio.setMinSelectableDate(new Date(anio - 1900, 0, 1));
        //fecha maxima
        Date n = new Date();
        if ((n.getYear() + 1900) == anio) {
            date_inicio.setMaxSelectableDate(n);
        } else {
            date_inicio.setMaxSelectableDate(new Date(anio - 1900, 11, 31));
        }

        //fecha minima
        date_fin.setMinSelectableDate(new Date(anio - 1900, 0, 1));
        //fecha maxima        
        if ((n.getYear() + 1900) == anio) {
            date_fin.setMaxSelectableDate(n);
        } else {
            date_fin.setMaxSelectableDate(new Date(anio - 1900, 11, 31));
        }

        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        Component north = ui.getNorthPane();
        MouseMotionListener[] actions
                = (MouseMotionListener[]) north.getListeners(MouseMotionListener.class);
        for (MouseMotionListener action : actions) {
            north.removeMouseMotionListener(action);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        lbl_Reporte = new javax.swing.JLabel();
        btnExport = new javax.swing.JButton();
        combo_tipo_detalle = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        combo_tiempo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        panel_rango = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        date_inicio = new com.toedter.calendar.JDateChooser();
        date_fin = new com.toedter.calendar.JDateChooser();
        combo_Establecimientos = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        panel_mes = new javax.swing.JPanel();
        combo_mes = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        combo_tipo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setEnabled(false);
        setPreferredSize(new java.awt.Dimension(800, 700));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_Reporte.setFont(new java.awt.Font("Open Sans", 1, 48)); // NOI18N
        lbl_Reporte.setText("REPORTES ");
        getContentPane().add(lbl_Reporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        btnExport.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        btnExport.setText("Exportar");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        getContentPane().add(btnExport, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 560, 310, 70));

        combo_tipo_detalle.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        combo_tipo_detalle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Mes", "Por Establecimiento", "Por Factura", "General" }));
        combo_tipo_detalle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipo_detalleItemStateChanged(evt);
            }
        });
        getContentPane().add(combo_tipo_detalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 300, 50));

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel1.setText("Tipo de detalle:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        combo_tiempo.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        combo_tiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año", "Mes", "Rango de Fechas" }));
        combo_tiempo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tiempoItemStateChanged(evt);
            }
        });
        combo_tiempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tiempoActionPerformed(evt);
            }
        });
        getContentPane().add(combo_tiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 300, 50));

        jLabel2.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel2.setText("Reporte por:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        panel_rango.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel5.setText("Fecha de fin:");
        panel_rango.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel6.setText("Fecha de inicio:");
        panel_rango.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));
        panel_rango.add(date_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 300, 40));
        panel_rango.add(date_fin, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 300, 40));

        getContentPane().add(panel_rango, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 560, 160));

        combo_Establecimientos.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        combo_Establecimientos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_EstablecimientosItemStateChanged(evt);
            }
        });
        getContentPane().add(combo_Establecimientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 300, 50));

        jLabel4.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel4.setText("Establecimiento:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        panel_mes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combo_mes.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        combo_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octube", "Noviembre", "Diciembre" }));
        combo_mes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_mesItemStateChanged(evt);
            }
        });
        panel_mes.add(combo_mes, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 300, 50));

        jLabel3.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel3.setText("Mes:");
        panel_mes.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

        getContentPane().add(panel_mes, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 560, 160));

        jLabel7.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel7.setText("Reporte de gastos:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        combo_tipo.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        combo_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Personal", "Negocio" }));
        getContentPane().add(combo_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 300, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private boolean validar_fechas() {

        if (date_fin.getDate() != null && date_inicio.getDate() != null) {
            if (date_fin.getDate().compareTo(date_inicio.getDate()) > 0) {
                return true;
            }
            JOptionPane.showMessageDialog(null, "Rango de fechas incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
            return false;

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, los campos de las fechas", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //return false;
    }

    private void generar_reporte(String archivo, Map parametros) {
        try {
            //todos los establecimientos
            String path = "src/Reportes/" + archivo;
            JasperReport jr = null;

            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            /*Agregar parametros
            Map parametros = new HashMap();
            parametros.put("textoEntrada", "Reporte Anual");
            parametros.put("idCliente", this.cedula_usuario);
            parametros.put("Anio", this.anio);
            Fin parametros*/
            System.out.println("-------");
            for (Object object : parametros.entrySet()) {
                System.out.println(object);

            }
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conn.getConn());
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle(path);
            jv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        Map parametros;
        //Gastos Personales
        int m = combo_mes.getSelectedIndex() + 1;
        String fecha_inicio,
                fecha_fin;
        switch(combo_tiempo.getSelectedIndex()) {
                    case 0:
                        fecha_inicio = this.anio + "-" + "01" + "-01";
                        fecha_fin = this.anio + "-" + "12" + "-31";
                        break;
                    case 1:
                        fecha_inicio = this.anio + "-" + (m) + "-01";
                        fecha_fin = this.anio + "-" + (m) + "-31";
                        break;
                    default:
                        fecha_inicio = new SimpleDateFormat("yyyy-MM-dd").format(date_inicio.getDate());
                        fecha_fin = new SimpleDateFormat("yyyy-MM-dd").format(date_fin.getDate());
                        break;
                        
        }       
        
        switch (combo_tipo_detalle.getSelectedIndex()) {
            case 0:
                parametros = new HashMap();
                parametros.put("tipo_factura", combo_tipo.getSelectedItem().toString());
                parametros.put("Anio", this.anio);
                parametros.put("cliente", this.cedula_usuario);
                generar_reporte("detallePorMes.jasper", parametros);
                break;
            case 1://detalle por establecimiento
                parametros = new HashMap();
                parametros.put("tipo_factura", combo_tipo.getSelectedItem().toString());
                parametros.put("fechaInicio", fecha_inicio);
                parametros.put("fechaFin", fecha_fin);
                parametros.put("cliente", this.cedula_usuario);
                generar_reporte("detallePorEstablecimiento.jasper", parametros);
                break;
            case 2://detalle por tipo factura
                if (combo_tipo.getSelectedIndex() == 0) {
                    parametros = new HashMap();
                    parametros.put("tipo_factura", combo_tipo.getSelectedItem().toString());
                    parametros.put("fechaInicio", fecha_inicio);
                parametros.put("fechaFin", fecha_fin);
                    parametros.put("cliente", this.cedula_usuario);
                    generar_reporte("detallePorFacturaPersonal.jasper", parametros);
                } else {
                    parametros = new HashMap();
                    parametros.put("tipo_factura", combo_tipo.getSelectedItem().toString());
                    parametros.put("fechaInicio", fecha_inicio);
                    parametros.put("fechaFin", fecha_fin);
                    parametros.put("cliente", this.cedula_usuario);
                    generar_reporte("detallePorFacturaNegocio.jasper", parametros);
                    //Personal
                }
                break;
            default:
                switch (combo_tiempo.getSelectedIndex()) {
                    case 0://Reporte anual                
                        if (combo_Establecimientos.getSelectedIndex() == 0) {
                            if(combo_tipo.getSelectedIndex()==0){
                            parametros = new HashMap();
                            parametros.put("textoEntrada", "Reporte Anual");
                            parametros.put("Anio", this.anio);
                            parametros.put("cliente", this.cedula_usuario);
                            generar_reporte("reporteAnioDiferencia.jasper", parametros);
                            }
                            else
                            {
                            parametros = new HashMap();
                            parametros.put("textoEntrada","Resporte anual" );
                            parametros.put("tipoFactura", combo_tipo.getSelectedItem().toString());
                            parametros.put("idCliente", this.cedula_usuario);
                            parametros.put("fechaInicio", this.anio + "-01-01");
                            parametros.put("fechaFin", this.anio + "-12-31");
                            generar_reporte("reporteDinamico_Establecimiento.jasper", parametros);
                            }

                        } else {//establecimiento especifico
                            parametros = new HashMap();
                            parametros.put("textoEntrada", "Reporte Anual");
                            parametros.put("tipoFactura", combo_tipo.getSelectedItem().toString());
                            parametros.put("idCliente", this.cedula_usuario);
                            parametros.put("establecimiento", combo_Establecimientos.getSelectedItem().toString());
                            parametros.put("fechaInicio", this.anio + "-01-01");
                            parametros.put("fechaFin", this.anio + "-12-31");
                            generar_reporte("reporteDinamico.jasper", parametros);
                        }

                        break;
                    case 1://mes
                        m = combo_mes.getSelectedIndex() + 1;
                        fecha_inicio = this.anio + "-" + (m) + "-01";
                         fecha_fin = this.anio + "-" + (m) + "-31";

                        if (combo_Establecimientos.getSelectedIndex() == 0) { //todos los establecimientos
                            parametros = new HashMap();
                            parametros.put("textoEntrada", "Reporte del mes " + combo_mes.getSelectedItem().toString());
                            parametros.put("tipoFactura", combo_tipo.getSelectedItem().toString());
                            parametros.put("idCliente", this.cedula_usuario);
                            parametros.put("fechaInicio", fecha_inicio);
                            parametros.put("fechaFin", fecha_fin);
                            generar_reporte("reporteDinamico_Establecimiento.jasper", parametros);

                        } else {//establecimiento especifico
                            parametros = new HashMap();
                            parametros.put("textoEntrada", "Reporte del mes " + combo_mes.getSelectedItem().toString());
                            parametros.put("tipoFactura", combo_tipo.getSelectedItem().toString());
                            parametros.put("idCliente", this.cedula_usuario);
                            parametros.put("establecimiento", combo_Establecimientos.getSelectedItem().toString());
                            parametros.put("fechaInicio", fecha_inicio);
                            parametros.put("fechaFin", fecha_fin);
                            generar_reporte("reporteDinamico.jasper", parametros);
                        }

                        break;
                    default://por fecha                   
                        if (validar_fechas()) {
                            fecha_inicio = new SimpleDateFormat("yyyy-MM-dd").format(date_inicio.getDate());
                            fecha_fin = new SimpleDateFormat("yyyy-MM-dd").format(date_fin.getDate());
                            if (combo_Establecimientos.getSelectedIndex() == 0) { //todos los establecimientos
                                parametros = new HashMap();
                                parametros.put("textoEntrada", "Reporte del mes " + combo_mes.getSelectedItem().toString());
                                parametros.put("tipoFactura", combo_tipo.getSelectedItem().toString());
                                parametros.put("idCliente", this.cedula_usuario);
                                parametros.put("fechaInicio", fecha_inicio);
                                parametros.put("fechaFin", fecha_fin);
                                generar_reporte("reporteDinamico_Establecimiento.jasper", parametros);
                            } else {//establecimiento especifico
                                parametros = new HashMap();
                                parametros.put("textoEntrada", "Reporte entre " + fecha_inicio + " - " + fecha_fin);
                                parametros.put("tipoFactura", combo_tipo.getSelectedItem().toString());
                                parametros.put("idCliente", this.cedula_usuario);
                                parametros.put("establecimiento", combo_Establecimientos.getSelectedItem().toString());
                                parametros.put("fechaInicio", fecha_inicio);
                                parametros.put("fechaFin", fecha_fin);
                                generar_reporte("reporteDinamico.jasper", parametros);
                            }

                        }
                }

        }

        /*else {
            //Gastos de negocio
            switch (combo_tiempo.getSelectedIndex()) {
                case 0://Reporte anual
                    if (combo_Establecimientos.getSelectedIndex() == 0) { //todos los establecimientos

                    } else {//establecimiento especifico

                    }
                case 1://mes
                    if (combo_Establecimientos.getSelectedIndex() == 0) { //todos los establecimientos

                    } else {//establecimiento especifico

                    }
                    break;

                default://por fecha
                    if (validar_fechas()) {
                        if (combo_Establecimientos.getSelectedIndex() == 0) { //todos los establecimientos

                        } else {//establecimiento especifico

                        }
                    }
            }
        }
         */
        //JOptionPane.showMessageDialog(null, "Reportes Generados Exitosamente");
    }//GEN-LAST:event_btnExportActionPerformed


    private void combo_tiempoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tiempoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch (combo_tiempo.getSelectedIndex()) {
                case 1:
                    panel_rango.setVisible(false);
                    panel_mes.setVisible(true);
                    break;
                case 2:
                    panel_mes.setVisible(false);
                    panel_rango.setVisible(true);
                    break;
                default:
                    panel_mes.setVisible(false);
                    panel_rango.setVisible(false);
            }
        }
    }//GEN-LAST:event_combo_tiempoItemStateChanged

    private void combo_EstablecimientosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_EstablecimientosItemStateChanged
        ArrayList auxDatosEst;
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (combo_Establecimientos.getSelectedIndex() != 0) {
                auxDatosEst = conn.cambiarDatosEstablecimiento(combo_Establecimientos.getSelectedItem().toString());
            } else {

            }
        }
    }//GEN-LAST:event_combo_EstablecimientosItemStateChanged

    private void combo_mesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_mesItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_mesItemStateChanged

    private void combo_tiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tiempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_tiempoActionPerformed

    private void combo_tipo_detalleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipo_detalleItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            switch(combo_tipo_detalle.getSelectedIndex()){
                case 3:
                combo_mes.setEnabled(true);
                combo_tiempo.setEnabled(true);
                combo_Establecimientos.setEnabled(true);
                combo_mes.setSelectedIndex(0);
                combo_tiempo.setSelectedIndex(0);
                combo_Establecimientos.setSelectedIndex(0);
                break;
                
                case 2:
                combo_mes.setEnabled(true);
                combo_mes.setSelectedIndex(0);
                combo_tiempo.setSelectedIndex(0);
                combo_Establecimientos.setSelectedIndex(0);
                combo_Establecimientos.setEnabled(false);
                combo_tiempo.setEnabled(true);
                break;
                
                case 1:
                combo_mes.setEnabled(true);
                combo_mes.setSelectedIndex(0);
                combo_tiempo.setSelectedIndex(0);
                combo_Establecimientos.setSelectedIndex(0);
                combo_Establecimientos.setEnabled(false);
                combo_tiempo.setEnabled(true);
                break;
                
                default:
                combo_mes.setEnabled(false);
                combo_tiempo.setEnabled(false);
                combo_mes.setSelectedIndex(0);
                combo_tiempo.setSelectedIndex(0);
                combo_Establecimientos.setSelectedIndex(0);
                combo_Establecimientos.setEnabled(false);
                break;
                
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_combo_tipo_detalleItemStateChanged

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
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> combo_Establecimientos;
    private javax.swing.JComboBox<String> combo_mes;
    private javax.swing.JComboBox<String> combo_tiempo;
    private javax.swing.JComboBox<String> combo_tipo;
    private javax.swing.JComboBox<String> combo_tipo_detalle;
    private com.toedter.calendar.JDateChooser date_fin;
    private com.toedter.calendar.JDateChooser date_inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lbl_Reporte;
    private javax.swing.JPanel panel_mes;
    private javax.swing.JPanel panel_rango;
    // End of variables declaration//GEN-END:variables
}