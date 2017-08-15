/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libres.aplicacioneslibres.interfaces;

import com.libres.aplicacioneslibres.conexionbdd.Conexion;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author andreu
 */
public class SeleccionarTipoGastoNegocios extends javax.swing.JFrame {

    final JComboBox comboBox;
    JTable tablaProductos;
    String tipoEstado[];

    String evtTipo = "";
    int filaTipo = -1;
    
    Object[][] tipos;
    
    Conexion conTipo;
    
    
    //Tabla Establecimiento----
    String ID_ESTABLECIMIENTO;
    String NOMBRE_ESTABLECIMIENTO;
    String TIPO_GASTO_ESTABLECIMIENTO;
    String DIRECCION_ESTABLECIMIENTO;
    String TELEFONO_ESTABLECIMIENTO;
    
    //tabla Factura------------- 
    String ID_FACTURA;
    String ID_CLIENTE;
    //String ID_ESTABLECIMIENTO;
    String TIPO_FACTURA;
    String FECHA_EMISION;
    String ESTADO_FACTURA;
    String AMBIENTE_FACTURA;
    Double TOTAL_SIN_IVA;
    Double IVA;
    Double TOTAL_CON_IVA;
    
    //Tabla DETALLE---------------
    //String ID_FACTURA
    //String ID_PRODUCTO
    int CANTIDAD;
    Double TOTAL;
    
    //Tabla  PRODUCTO
    String ID_PRODUCTO;
    String NAME_PRODUCTO;
    String FAMILIA;
    Double PRECIO_UNITARIO;
    
    int anio;
    /**
     * Creates new form SeleccionarTipoGasto
     *
     * @param conn
     * @param infoEncabezado
     * @param infoTributaria
     * @param infoFactura
     * @param infoDetalles
     */
    public SeleccionarTipoGastoNegocios(Conexion conn,HashMap<String,String> infoEncabezado,HashMap<String,String> infoTributaria,HashMap<String,String> infoFactura,ArrayList<HashMap> infoDetalles) {
        initComponents();
        
        this.conTipo = conn;
        
        ID_ESTABLECIMIENTO=infoTributaria.get("ruc");
        NOMBRE_ESTABLECIMIENTO=infoTributaria.get("razonSocial");
        jlabelESTABLECIMIENTO.setText(NOMBRE_ESTABLECIMIENTO);
        TIPO_GASTO_ESTABLECIMIENTO=conTipo.getTipoGastoEstablecimiento(ID_ESTABLECIMIENTO);
        String item;
        for (int i = 0; i < cmbTIPO_GASTO_ESTABLECIMIENTO.getItemCount(); i++)
        {
            item = cmbTIPO_GASTO_ESTABLECIMIENTO.getItemAt(i);
            if (item.equalsIgnoreCase(TIPO_GASTO_ESTABLECIMIENTO))
            {
                cmbTIPO_GASTO_ESTABLECIMIENTO.setSelectedIndex(i);
                break;
            }
        }
        
        DIRECCION_ESTABLECIMIENTO=infoTributaria.get("dirMatriz");
        TELEFONO_ESTABLECIMIENTO="";
    
        //Tabla Factura
        ID_FACTURA = infoTributaria.get("estab")+"-"+infoTributaria.get("ptoEmi")+"-"+infoTributaria.get("secuencial");
        ID_CLIENTE = infoFactura.get("identificacionComprador");
        //String ID_ESTABLECIMIENTO;
        TIPO_FACTURA = "Negocio";
        FECHA_EMISION = infoFactura.get("fechaEmision");
        ESTADO_FACTURA = infoEncabezado.get("estado");
        AMBIENTE_FACTURA = infoEncabezado.get("ambiente");
        TOTAL_SIN_IVA =Double.parseDouble( infoFactura.get("totalSinImpuestos") );
        IVA = Double.parseDouble(infoFactura.get("valor") );
        TOTAL_CON_IVA = Double.parseDouble(infoFactura.get("importeTotal") );
        
        this.anio = Integer.parseInt(infoFactura.get("fechaEmision").substring(0, 4));

        
        int numeroProductos =infoDetalles.size();
        tipos=new Object[numeroProductos][6];
        
        //Extraer detalle de los productos y los guarda en la matriz Object [][] detalleProducto
        for (int i =0; i<numeroProductos;i++){
            tipos[i][0]=infoDetalles.get(i).get("descripcion").toString();
            tipos[i][1]=Double.parseDouble( infoDetalles.get(i).get("precioTotalSinImpuesto").toString() );
            
            //Retorna el tipo de gasto o Familia que pertece un producto de acuerdo a su ID_PRODUCTO
            String tipoFamiliaProducto = conTipo.consultarProductoPor( infoDetalles.get(i).get("codigoPrincipal").toString());
            if(!tipoFamiliaProducto.equals("")){
                tipos[i][2]=tipoFamiliaProducto;//Familia o TIPO_FACTURA de Gasto
            }else {
                tipos[i][2]=this.conTipo.getTipoGastoEstablecimiento(ID_ESTABLECIMIENTO);//Familia o TIPO_FACTURA de Gasto
            }
            
            tipos[i][3]=infoDetalles.get(i).get("codigoPrincipal").toString();
            tipos[i][4]=(int)Double.parseDouble( infoDetalles.get(i).get("cantidad").toString() );
            tipos[i][5]=Double.parseDouble( infoDetalles.get(i).get("precioUnitario").toString() );
        }

        String nombreCabeceras[] = {"Descripcion", "Precio Total", "Tipo de Gasto"};

        tipoEstado = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            tipoEstado[i] = "";
        }

        tablaProductos = new JTable(tipos, nombreCabeceras) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        jScrollPane1.setViewportView(tablaProductos);

        comboBox = new JComboBox();
        comboBox.addItem("Mercaderia");
        comboBox.addItem("Arriendo");
        comboBox.addItem("Servicios Basicos");
        comboBox.addItem("Sueldos");
        comboBox.addItem("Movilizacion");
        comboBox.addItem("Viaticos");
        comboBox.addItem("Capacitacion");
        comboBox.addItem("Suministros");
        comboBox.addItem("Herramientas");

        //Inicializar suma para productos autocompletados
        //Cargar autocalificacion con los combobox
        //extraigo la tabla del objeto donde  ocurrio el evento
        for (int row=0;row<tablaProductos.getRowCount();row++){
            for (int column=0;column<tablaProductos.getColumnCount();column++){
                
                TableModel model = tablaProductos.getModel();
                Object data = model.getValueAt(row, column);
                if (!data.equals("") && column == 2) {
                    //int opc = comboBox.getSelectedIndex();
                    //System.out.println(row);

                     if (data.equals("Mercaderia")) {
                        sumarAgregado(txtMercaderia, row, "Mercaderia");
                    }
                    if (data.equals("Arriendo")) {
                        sumarAgregado(txtArriendo, row, "Arriendo");
                    }
                    if (data.equals("Servicios Basicos")) {
                        sumarAgregado(txtServicios, row, "Servicios Basicos");
                    }
                    if (data.equals("Sueldos")) {
                        sumarAgregado(txtSueldos, row, "Sueldos");
                    }
                    if (data.equals("Movilizacion")) {
                        sumarAgregado(txtMovilizacion, row, "Movilizacion");
                    }
                    if (data.equals("Viaticos")) {
                        sumarAgregado(txtViaticos, row, "Viaticos");
                    }
                    if (data.equals("Capacitacion")) {
                        sumarAgregado(txtCapacitacion, row, "Capacitacion");
                    }
                    if (data.equals("Suministros")) {
                        sumarAgregado(txtSuministros, row, "Suministros");
                    }
                    if (data.equals("Herramientas")) {
                        sumarAgregado(txtHerramientas, row, "Herramientas");
                    }
                    
                }
            }
        }
        tablaProductos.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                int row = tme.getFirstRow();
                int column = tme.getColumn();

                TableModel model = (TableModel) tme.getSource();
                Object data = model.getValueAt(row, column);

                if (!data.equals("") && column == 2) {
                    //int opc = comboBox.getSelectedIndex();
                    //System.out.println(row);

                    if (!tipoEstado[row].equals("")) {
                        if (tipoEstado[row].equals("Mercaderia")) {
                            restarAgregado(txtMercaderia, row);
                        }
                        if (tipoEstado[row].equals("Arriendo")) {
                            restarAgregado(txtArriendo, row);
                        }
                        if (tipoEstado[row].equals("Servicios Basicos")) {
                            restarAgregado(txtServicios, row);
                        }
                        if (tipoEstado[row].equals("Sueldos")) {
                            restarAgregado(txtSueldos, row);
                        }
                        if (tipoEstado[row].equals("Movilizacion")) {
                            restarAgregado(txtMovilizacion, row);
                        }
                        if (tipoEstado[row].equals("Viaticos")) {
                            restarAgregado(txtViaticos, row);
                        }
                        if (tipoEstado[row].equals("Capacitacion")) {
                            restarAgregado(txtCapacitacion, row);
                        }
                        if (tipoEstado[row].equals("Suministros")) {
                            restarAgregado(txtSuministros, row);
                        }
                        if (tipoEstado[row].equals("Herramientas")) {
                            restarAgregado(txtHerramientas, row);
                        }
                    }

                    if (data.equals("Mercaderia")) {
                        sumarAgregado(txtMercaderia, row, "Mercaderia");
                    }
                    if (data.equals("Arriendo")) {
                        sumarAgregado(txtArriendo, row, "Arriendo");
                    }
                    if (data.equals("Servicios Basicos")) {
                        sumarAgregado(txtServicios, row, "Servicios Basicos");
                    }
                    if (data.equals("Sueldos")) {
                        sumarAgregado(txtSueldos, row, "Sueldos");
                    }
                    if (data.equals("Movilizacion")) {
                        sumarAgregado(txtMovilizacion, row, "Movilizacion");
                    }
                    if (data.equals("Viaticos")) {
                        sumarAgregado(txtViaticos, row, "Viaticos");
                    }
                    if (data.equals("Capacitacion")) {
                        sumarAgregado(txtCapacitacion, row, "Capacitacion");
                    }
                    if (data.equals("Suministros")) {
                        sumarAgregado(txtSuministros, row, "Suministros");
                    }
                    if (data.equals("Herramientas")) {
                        sumarAgregado(txtHerramientas, row, "Herramientas");
                    }
                }

            }
        });

        DefaultTableCellRenderer alinearDerecha = new DefaultTableCellRenderer();
        alinearDerecha.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        tablaProductos.getColumnModel().getColumn(1).setCellRenderer(alinearDerecha);

        tablaProductos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));

        tablaProductos.getColumnModel().getColumn(1).setMinWidth(100);
        tablaProductos.getColumnModel().getColumn(1).setMaxWidth(100);
        tablaProductos.getColumnModel().getColumn(2).setMinWidth(150);
        tablaProductos.getColumnModel().getColumn(2).setMaxWidth(150);

        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        jButton1 = new javax.swing.JButton();
        lblMercaderia = new javax.swing.JLabel();
        txtMercaderia = new javax.swing.JTextField();
        lblArriendo = new javax.swing.JLabel();
        txtArriendo = new javax.swing.JTextField();
        lblServicios = new javax.swing.JLabel();
        txtServicios = new javax.swing.JTextField();
        lblSueldos = new javax.swing.JLabel();
        txtSueldos = new javax.swing.JTextField();
        lblMovilizacion = new javax.swing.JLabel();
        txtMovilizacion = new javax.swing.JTextField();
        lblViaticos = new javax.swing.JLabel();
        txtViaticos = new javax.swing.JTextField();
        lblCapacitacion = new javax.swing.JLabel();
        txtCapacitacion = new javax.swing.JTextField();
        lblSuministros = new javax.swing.JLabel();
        txtSuministros = new javax.swing.JTextField();
        lblHerramientas = new javax.swing.JLabel();
        txtHerramientas = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jlabelESTABLECIMIENTO = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        cmbTIPO_GASTO_ESTABLECIMIENTO = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblMercaderia.setText("Mercaderia");

        txtMercaderia.setEditable(false);
        txtMercaderia.setText("0.0");

        lblArriendo.setText("Arriendo");

        txtArriendo.setEditable(false);
        txtArriendo.setText("0.0");

        lblServicios.setText("Servicios Basicos");

        txtServicios.setEditable(false);
        txtServicios.setText("0.0");

        lblSueldos.setText("Sueldos");

        txtSueldos.setEditable(false);
        txtSueldos.setText("0.0");

        lblMovilizacion.setText("Movilizacion");

        txtMovilizacion.setEditable(false);
        txtMovilizacion.setText("0.0");

        lblViaticos.setText("Viaticos");

        txtViaticos.setEditable(false);
        txtViaticos.setText("0.0");

        lblCapacitacion.setText("Capacitacion");

        txtCapacitacion.setEditable(false);
        txtCapacitacion.setText("0.0");

        lblSuministros.setText("Sumistros");

        txtSuministros.setEditable(false);
        txtSuministros.setText("0.0");

        lblHerramientas.setText("Herramientas");

        txtHerramientas.setEditable(false);
        txtHerramientas.setText("0.0");

        jSeparator1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TOTAL POR TIPO GASTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jlabelESTABLECIMIENTO.setFont(new java.awt.Font("Cantarell", 1, 14)); // NOI18N
        jlabelESTABLECIMIENTO.setText("ESTABLECIMIENTO");

        jSeparator2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "SELECCIONAR TIPO DE GASTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        cmbTIPO_GASTO_ESTABLECIMIENTO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".", "Mercaderia", "Arriendo", "Servicios basicos", "Sueldos", "Movilizacion", "Viaticos", "Capacitacion", "Suministros", "Herramientas" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jlabelESTABLECIMIENTO, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbTIPO_GASTO_ESTABLECIMIENTO, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(509, 509, 509)
                            .addComponent(jButton1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblMercaderia)
                                .addComponent(lblArriendo)
                                .addComponent(lblServicios)
                                .addComponent(lblSueldos)
                                .addComponent(lblMovilizacion))
                            .addGap(46, 46, 46)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtMovilizacion)
                                    .addGap(304, 304, 304))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtMercaderia, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(65, 65, 65)
                                            .addComponent(lblViaticos))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtArriendo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(65, 65, 65)
                                            .addComponent(lblCapacitacion))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(65, 65, 65)
                                            .addComponent(lblSuministros))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(65, 65, 65)
                                            .addComponent(lblHerramientas)))
                                    .addGap(51, 51, 51)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSuministros, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtViaticos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabelESTABLECIMIENTO)
                    .addComponent(cmbTIPO_GASTO_ESTABLECIMIENTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMercaderia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtViaticos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMercaderia)
                            .addComponent(lblViaticos))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtArriendo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCapacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblArriendo)
                            .addComponent(lblCapacitacion))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSuministros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblServicios)
                            .addComponent(lblSuministros))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSueldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSueldos)
                            .addComponent(lblHerramientas))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblMovilizacion))
                    .addComponent(txtMovilizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int filasTotales = tablaProductos.getRowCount();
        boolean validado = true;

        for (int i = 0; i < filasTotales; i++) {
            if (tablaProductos.getValueAt(i, 2).equals("")) {
                validado = false;
                break;
            }
        }

        if (validado == true) {
            String query;

            double totales[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

            if (!txtMercaderia.getText().equals("0.0")) {
                totales[0] = ingresarTipo(txtMercaderia, lblMercaderia);
            }
            if (!txtArriendo.getText().equals("0.0")) {
                totales[1] = ingresarTipo(txtArriendo, lblArriendo);
            }
            if (!txtServicios.getText().equals("0.0")) {
                totales[2] = ingresarTipo(txtServicios, lblServicios);
            }
            if (!txtSueldos.getText().equals("0.0")) {
                totales[3] = ingresarTipo(txtSueldos, lblSueldos);
            }
            if (!txtMovilizacion.getText().equals("0.0")) {
                totales[4] = ingresarTipo(txtMovilizacion, lblMovilizacion);
            }
            if (!txtViaticos.getText().equals("0.0")) {
                totales[5] = ingresarTipo(txtViaticos, lblViaticos);
            }
            if (!txtCapacitacion.getText().equals("0.0")) {
                totales[6] = ingresarTipo(txtCapacitacion, lblCapacitacion);
            }
            if (!txtSuministros.getText().equals("0.0")) {
                totales[7] = ingresarTipo(txtSuministros, lblSuministros);
            }
            if (!txtHerramientas.getText().equals("0.0")) {
                totales[8] = ingresarTipo(txtHerramientas, lblHerramientas);
            }

            if (conTipo.verificar_usuario("SELECT * FROM HISTORIAL_PAGOS_NEGOCIOS WHERE anio_historial_n=" + anio + " AND id_cliente='" + ID_CLIENTE + "'")) {
                query = "UPDATE HISTORIAL_PAGOS_NEGOCIOS SET total_mercaderia=total_mercaderia+" + totales[0] + ","
                        + "total_arriendo=total_arriendo+" + totales[1] + ","
                        + "total_servicios=total_servicios+" + totales[2] + ","
                        + "total_sueldos=total_sueldos+" + totales[3] + ","
                        + "total_movilizacion=total_movilizacion+" + totales[4] + ","
                        + "total_viaticos=total_viaticos+" + totales[5] + ","
                        + "total_capacitacion=total_capacitacion+" + totales[6] + ","
                        + "total_suministros=total_suministros+" + totales[7] + ","
                        + "total_herramientas=total_herramientas+" + totales[8] + " WHERE anio_historial_n=" + anio + " AND id_cliente='" + ID_CLIENTE + "'";
            } else {
                query = "INSERT INTO HISTORIAL_PAGOS_NEGOCIOS VALUES (" + anio + ",'" + ID_CLIENTE + "'," + totales[0] + "," + totales[1] + "," + totales[2] + "," + totales[3] + "," + totales[4] + "," + totales[5] + "," + totales[6] + "," + totales[7] + ", " + totales[8] + ")";
            }
            
              TIPO_GASTO_ESTABLECIMIENTO=cmbTIPO_GASTO_ESTABLECIMIENTO.getSelectedItem().toString();
            if (!TIPO_GASTO_ESTABLECIMIENTO.equals(".")){
                conTipo.insertar(query);
                registrarFactura();//ingresar Establecimiento,Factura,Producto y Detalle
                JOptionPane.showMessageDialog(this, "Factura ingresada exitosamente");
                recargar(conTipo);
                this.dispose();
             }else{
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un tipo de gasto que identifique al Establecimiento");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado el tipo para cada producto");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

     public void registrarFactura(){
        
        //Insertar Establecimiento
        if (this.conTipo.consultarEstablecimientoPor(ID_ESTABLECIMIENTO).equals("")){
            String sql = "INSERT INTO ESTABLECIMIENTO VALUES ('"+ID_ESTABLECIMIENTO+
                    "', '"+NOMBRE_ESTABLECIMIENTO+"', '"+TIPO_GASTO_ESTABLECIMIENTO+
                    "', '"+DIRECCION_ESTABLECIMIENTO+"', '"+TELEFONO_ESTABLECIMIENTO+"')";
            this.conTipo.insertar(sql);
        }else{
            String sql = "UPDATE ESTABLECIMIENTO SET TIPO_GASTO_ESTABLECIMIENTO='"+TIPO_GASTO_ESTABLECIMIENTO+"' WHERE ID_ESTABLECIMIENTO='"+ID_ESTABLECIMIENTO+"'";
            this.conTipo.insertar(sql);
            
        }
        //Insertar Factura
        if (this.conTipo.verificar_factura(ID_FACTURA)==false){
            String sql = "INSERT INTO FACTURA VALUES('"+ID_FACTURA+"', '"+ID_CLIENTE+
                    "', '"+ID_ESTABLECIMIENTO+"', '"+TIPO_FACTURA+"', '"+FECHA_EMISION+
                    "', '"+ESTADO_FACTURA+"', '"+AMBIENTE_FACTURA+"', "+TOTAL_SIN_IVA+
                    ", "+IVA+", "+TOTAL_CON_IVA+")";
            this.conTipo.insertar(sql);
        }
        
        //Insertar Productos y Detalles
        int numeroProductos =tipos.length;
        
        //Extraer detalle de los productos y los guarda en la matriz Object [][] detalleProducto
        for (int i =0; i<numeroProductos;i++){
//            tipos[i][0]=infoDetalles.get(i).get("descripcion").toString();
//            tipos[i][1]=Double.parseDouble( infoDetalles.get(i).get("precioTotalSinImpuesto").toString() );
//            tipos[i][2]=conTipo.consultarProductoPor( infoDetalles.get(i).get("codigoPrincipal").toString() );//Familia o TIPO_FACTURA de Gasto
//            tipos[i][3]=infoDetalles.get(i).get("codigoPrincipal").toString();
//            tipos[i][4]=(int)Double.parseDouble( infoDetalles.get(i).get("cantidad").toString() );
//            tipos[i][5]=Double.parseDouble( infoDetalles.get(i).get("precioUnitario").toString() );
            //Tabla DETALLE---------------
            //ID_FACTURA
            ID_PRODUCTO =  tipos[i][3].toString();
            CANTIDAD = (int)tipos[i][4];
            TOTAL =(double)tipos[i][1];
            String sqlDetalle = "INSERT INTO DETALLE VALUES('"+ID_FACTURA+"', '"+ID_PRODUCTO+"', "+CANTIDAD+", "+TOTAL+")";
            
            //Tabla  PRODUCTO
            //ID_PRODUCTO
            NAME_PRODUCTO = tipos[i][0].toString();
            FAMILIA = tipos[i][2].toString();
            PRECIO_UNITARIO = (double) tipos[i][5];
            
            String sqlProducto = "INSERT INTO PRODUCTO VALUES('"+ID_PRODUCTO+"', '"+NAME_PRODUCTO+"', '"+FAMILIA+"', "+PRECIO_UNITARIO+")";
            String sqlProductoUpdate = "UPDATE PRODUCTO SET NAME_PRODUCTO='"+
                    NAME_PRODUCTO+"', FAMILIA='"+FAMILIA+"', PRECIO_UNITARIO="+PRECIO_UNITARIO+" WHERE ID_PRODUCTO='"+ID_PRODUCTO+"'";
            
            if(this.conTipo.consultarProductoPor(ID_PRODUCTO).equals("")){
                this.conTipo.insertar(sqlProducto);
            }else{
                this.conTipo.insertar(sqlProductoUpdate);//el insert ejecuta cualquier operacion INSERT UPDATE DELETE!!
            }
            this.conTipo.insertar(sqlDetalle);
        }
        
    }
            
    private void recargar(Conexion conn) {
         ArrayList auxRec = new ArrayList();
        FacturaManualPersonal.combo_Establecimientos.removeAllItems();
        FacturaManualNegocio.combo_Establecimientos.removeAllItems();
        FacturaManualPersonal.combo_Establecimientos.addItem("");
        FacturaManualNegocio.combo_Establecimientos.addItem("");
        auxRec = conn.cargarEstablecimiento();
        for (Object est : auxRec) {            
            FacturaManualPersonal.combo_Establecimientos.addItem(est.toString());
            FacturaManualNegocio.combo_Establecimientos.addItem(est.toString());
        }
    }
    
    public void restarAgregado(JTextField txtField, int row) {
        double total;
        total = Double.parseDouble(txtField.getText());
        total -= (Double) tablaProductos.getValueAt(row, 1);
        total = BigDecimal.valueOf(total).setScale(3, RoundingMode.HALF_UP).doubleValue();
        txtField.setText(String.valueOf(total));
    }

    private void sumarAgregado(JTextField txtField, int row, String tipo) {
        double total;
        total = Double.parseDouble(txtField.getText());
        total += (Double) tablaProductos.getValueAt(row, 1);
        total = BigDecimal.valueOf(total).setScale(3, RoundingMode.HALF_UP).doubleValue();
        txtField.setText(String.valueOf(total));
        tipoEstado[row] = tipo;
    }

    public double ingresarTipo(JTextField txtField, JLabel lblLabel) {
        double total;
        String query;

        total = Double.parseDouble(txtField.getText());
        total = BigDecimal.valueOf(total).setScale(3, RoundingMode.HALF_UP).doubleValue();

        query = "INSERT INTO TIPO_GASTO (id_factura,tipo,total)"
                + "VALUES('" + ID_FACTURA + "','" + lblLabel.getText() + "'," + total + ")";

        conTipo.insertar(query);

        return total;
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
            java.util.logging.Logger.getLogger(SeleccionarTipoGastoNegocios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeleccionarTipoGastoNegocios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeleccionarTipoGastoNegocios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeleccionarTipoGastoNegocios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new SeleccionarTipoGasto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbTIPO_GASTO_ESTABLECIMIENTO;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jlabelESTABLECIMIENTO;
    private javax.swing.JLabel lblArriendo;
    private javax.swing.JLabel lblCapacitacion;
    private javax.swing.JLabel lblHerramientas;
    private javax.swing.JLabel lblMercaderia;
    private javax.swing.JLabel lblMovilizacion;
    private javax.swing.JLabel lblServicios;
    private javax.swing.JLabel lblSueldos;
    private javax.swing.JLabel lblSuministros;
    private javax.swing.JLabel lblViaticos;
    private javax.swing.JTextField txtArriendo;
    private javax.swing.JTextField txtCapacitacion;
    private javax.swing.JTextField txtHerramientas;
    private javax.swing.JTextField txtMercaderia;
    private javax.swing.JTextField txtMovilizacion;
    private javax.swing.JTextField txtServicios;
    private javax.swing.JTextField txtSueldos;
    private javax.swing.JTextField txtSuministros;
    private javax.swing.JTextField txtViaticos;
    // End of variables declaration//GEN-END:variables
}
