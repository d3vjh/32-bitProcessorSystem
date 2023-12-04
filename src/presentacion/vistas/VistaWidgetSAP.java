package presentacion.vistas;

import presentacion.controladores.ControladorWindgetSAP;
import presentacion.vistas.VistaDisplaySieteSeg;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import presentacion.Modelo;
import presentacion.controladores.ControladorWidgetRAM;

@SuppressWarnings("serial")
public class VistaWidgetSAP extends JPanel{
    
    private logica.SistemaSAP sistema;
    private GridBagConstraints c;

    // Contenido del widget
    private JLabel lblRegA;
    private JLabel lblRegB;
    private JLabel lblALU;
    private JLabel lblIR;
    private JLabel lblOut;
    private JLabel lblPC;
    private JLabel lblMAR;
    private JLabel lblControles;
    private JLabel lblStepCount;
    private JLabel lblBus;
    private JLabel lblStepCt;
    private JLabel lblFlags;
    
    private JButton Opciones1;
    private JComboBox<String> comboOperaciones;
    private JComboBox<String> comboRegistro1;
    private JComboBox<String> comboRegistro2;
    private JTextField textField;
    private JButton btnEnviar;



    private JLabel[] btns_bitsA;
    private JLabel[] btns_bitsB;
    private JLabel[] btns_bitsALU;
    private JLabel[] btns_bitsIR;
    private JLabel[] btns_bitsOUT;
    private JLabel[] btns_bitsPC;
    private JLabel[] btns_bitsMAR;
    private JLabel[] btns_bitsControl;
    private JLabel[] btns_bistBUS;
    
    private JLabel btnCarry;
    private JLabel btnZero;
    private VistaDisplaySieteSeg vistaDisplay7Seg;
    private VistaWidgetRAM ramWidget;

    private ControladorWindgetSAP control;
    private ControladorWidgetRAM control2;

    
    // Constantes
    public static final Dimension BUTTON_SIZE = new Dimension(22, 22);
    public static final Dimension WIDGET_SIZE = new Dimension(20525, 20700);
    public static final Color BUTTON_UNSELECTED_BG = new Color(238, 238, 238);
    public static final Color BUTTON_SELECTED_BG = new Color(255, 85, 85);
    public static final Color COLOR_BACKGROUND = new Color(253, 247, 124 );
    public static final Color WIDGET_BORDER_COLOR = Color.BLACK;
    
    private final Modelo modelo;    

    public VistaWidgetSAP(Modelo m, VistaDisplaySieteSeg display, VistaWidgetRAM ramWidget) {
        // Encapsula el modelo
        this.modelo = m;        
        this.sistema = m.getSistema();
        this.vistaDisplay7Seg = display;
        this.ramWidget = ramWidget;
        this.setBorder(BorderFactory.createLineBorder(WIDGET_BORDER_COLOR));

        this.sistema.addObserver(getControl());  // Agrega el manejador del observador

        // Tamaño preferido
        this.setPreferredSize(WIDGET_SIZE);
        this.setBackground(COLOR_BACKGROUND);

        // Layout
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        c.insets = new Insets(10, 10, 10, 0);

        // Encabezado
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        this.lblBus = new JLabel("BUS");
        this.add(this.lblBus, c);    
        
        // Etiquetas de registros
        c.gridwidth = 1;
        c.gridy = 2;
        c.gridx = 0;
        this.lblRegA = new JLabel("Registro A");
        this.add(lblRegA, c);

        //  B
        c.gridy = 4;
        c.gridx = 0;
        this.lblRegB = new JLabel("Registro B");
        this.add(lblRegB, c);

        // ALU 
        c.gridy = 6;
        c.gridx = 0;
        this.lblALU = new JLabel("ALU");
        this.add(lblALU, c);

        // Line Break
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 12;
        this.add(new JLabel("=================================================="), c);
        c.gridwidth = 1;

        // IR 
        c.gridy = 8;
        c.gridx = 0;
        this.lblPC = new JLabel("Registro de Instrucción");
        this.add(lblPC, c);

        // PC 
        c.gridy = 10;
        c.gridx = 0;
        this.lblIR = new JLabel("Contador de Programa");
        this.add(lblIR, c);

        // Line Break
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 12;
        this.add(new JLabel("=================================================="), c);
        c.gridwidth = 1;

        // OUT 
        c.gridy = 12;
        c.gridx = 0;
        this.lblOut = new JLabel("Salida");
        this.add(lblOut, c);

        // Memory Address Register 
        c.gridy = 14;
        c.gridx = 0;
        this.lblMAR = new JLabel("MAR");
        this.add(lblMAR, c);

        // Step Count 
        c.gridy = 16;
        c.gridx = 0;
        this.lblStepCount = new JLabel("Conteo de Ciclos");
        this.add(this.lblStepCount, c);

        // Valor step count
        c.gridy = 16;
        c.gridx = 1;
        this.lblStepCt = new JLabel("" + this.sistema.getStepCount());
        this.add(this.lblStepCt, c);

        // Line Break
        c.gridx = 0;
        c.gridwidth = 12;
        c.gridy = 17;
        this.add(new JLabel("=================================================="), c);

        // Lineas de Control 
        c.gridwidth = 1;
        c.gridy = 18;
        c.gridx = 0;
        this.lblControles = new JLabel("Lineas de Control ");
        this.add(this.lblControles, c);

        // Flags 
        c.gridy = 20;
        c.gridx = 0;
        this.lblFlags = new JLabel("Flags");
        this.add(this.lblFlags, c);
        
         // Opciones
        c.gridx = 0;
        c.gridy = 22;
        c.gridwidth = 12;
        this.add(new JLabel("Operacion"), c);
        c.gridwidth = 1;
        
        // Registro 1 
        c.gridx = 0;
        c.gridy = 24;
        c.gridwidth = 12;
        this.add(new JLabel("Registro 1 "), c);
        c.gridwidth = 1;
        
         // Espacio de memoria
        c.gridx = 0;
        c.gridy = 26;
        c.gridwidth = 12;
        this.add(new JLabel("Espacio de memoria"), c);
        c.gridwidth = 1;

        // Registro 2
        c.gridx = 0;
        c.gridy = 28;
        c.gridwidth = 12;
        this.add(new JLabel("Registro 2"), c);
        c.gridwidth = 1;
        
        // Prepara espacio display 
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);

        // Estados de las líneas de control
        this.btns_bitsControl = new JLabel[16];
        this.btns_bitsControl[0] = crearLabel("HLT"); 
        this.btns_bitsControl[1] = crearLabel("MI");
        this.btns_bitsControl[2] = crearLabel("RI");
        this.btns_bitsControl[3] = crearLabel("RO");
        this.btns_bitsControl[4] = crearLabel("IO");
        this.btns_bitsControl[5] = crearLabel("II");
        this.btns_bitsControl[6] = crearLabel("AI");
        this.btns_bitsControl[7] = crearLabel("AO");
        this.btns_bitsControl[8] = crearLabel("ΣO");
        this.btns_bitsControl[9] = crearLabel("SU");
        this.btns_bitsControl[10] = crearLabel("BI");
        this.btns_bitsControl[11] = crearLabel("OI");
        this.btns_bitsControl[12] = crearLabel("CE");
        this.btns_bitsControl[13] = crearLabel("CO");
        this.btns_bitsControl[14] = crearLabel("J");
        this.btns_bitsControl[15] = crearLabel("FI");

        c.gridy = 8;
        for (int i = 0; i < 16; i++) {
            btns_bitsControl[i].setBackground(BUTTON_UNSELECTED_BG);
            if (i == 16) {
                c.gridy++;
                c.gridx = 1;
            } else if (i >= 16) {
                c.gridx = i - 7;
            } else {
                c.gridx = i + 1;
            }
            c.gridy = 18;
            this.add(this.btns_bitsControl[i], c);
        }

        //  BUS 
        c.gridy = 0;
        c.gridx = 1;
        //AQUI SE EDITAN LOS TAMAÑOS CAMBIAR EL TAMAÑO DEL ARRAY Y EL CICLO FOR 
        btns_bistBUS = new JLabel[32];
        for (int i = 0; i <= 31; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bistBUS[i] = b;
        }

        //  A 
        c.gridy = 2;
        c.gridx = 1;
        btns_bitsA = new JLabel[32];
        for (int i = 0; i <= 31; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsA[i] = b;
        }

        // B 
        c.gridy = 4;
        c.gridx = 1;
        btns_bitsB = new JLabel[32];
        for (int i = 0; i <= 31; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsB[i] = b;
        }

        //  ALU 
        c.gridy = 6;
        c.gridx = 1;
        btns_bitsALU = new JLabel[32];
        for (int i = 0; i <= 31; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsALU[i] = b;
        }

        //  IR 
        c.gridy = 8;
        c.gridx = 1;
        btns_bitsIR = new JLabel[32];
        for (int i = 0; i <= 31; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsIR[i] = b;
        }

        //  out 
        c.gridy = 12;
        c.gridx = 1;
        btns_bitsOUT = new JLabel[32];
        for (int i = 0; i <= 31; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsOUT[i] = b;
        }

        //  PC
        c.gridy = 10;
        c.gridx = 1;
        btns_bitsPC = new JLabel[4];
        for (int i = 0; i <= 3; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsPC[i] = b;
        }

        //  MAR
        c.gridy = 14;
        c.gridx = 1;
        btns_bitsMAR = new JLabel[16];
        for (int i = 0; i <= 15; i++) {
            c.gridx = i + 1;
            JLabel b = crearLabel("0"); 
            this.add(b, c);
            btns_bitsMAR[i] = b;
        }

        //  Flags
        c.gridy = 20;
        c.gridx = 1;
        this.btnCarry = crearLabel("C");
        this.add(this.btnCarry, c);
        c.gridx = 2;
        this.btnZero = crearLabel("Z");
        this.add(this.btnZero, c);
        
         //  Operacion        
        c.gridx = 1;
        c.gridy = 22;
        c.gridwidth = 1;
        comboOperaciones = new JComboBox<>();
        comboOperaciones.addItem("No operacion");
        comboOperaciones.addItem("Suma");
        comboOperaciones.addItem("Resta");
        comboOperaciones.addItem("Multiplicación");
        comboOperaciones.addItem("División");
        this.add(comboOperaciones, c);
  
       comboOperaciones.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // Acciones a realizar cuando se selecciona una operación en el JComboBox
        String operacionSeleccionada = comboOperaciones.getSelectedItem().toString();
        if(operacionSeleccionada == "Suma"){
                    System.out.println("Si funciona xddddddddddddddddddd");
        }
    }
});
          
       //  Registro 1     
        comboRegistro1 = new JComboBox<>();
        c.gridx = 1;
        c.gridy = 24;
        c.gridwidth = 1;
        comboRegistro1.addItem("No registro");
        comboRegistro1.addItem("Registro A");
        comboRegistro1.addItem("Registro B");
        comboRegistro1.addItem("Registro C");
        comboRegistro1.addItem("Registro D");
        this.add(comboRegistro1, c);
  
       comboRegistro1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // Acciones a realizar cuando se selecciona una operación en el JComboBox
        String operacionSeleccionada = comboRegistro1.getSelectedItem().toString();
        if(operacionSeleccionada == "Registro A"){
                    
        }
    }
});
       
       
       //Espacio de memoria 
       
       c.gridx = 1;
       c.gridy = 26;
       textField = new JTextField(10); 
       this.add(textField, c);
              
       //Registro 2 
      
        c.gridx = 1;
        c.gridy = 28;
        comboRegistro2 = new JComboBox<>();
        comboRegistro2.addItem("No registro");
        comboRegistro2.addItem("Registro A");
        comboRegistro2.addItem("Registro B");
        comboRegistro2.addItem("Registro C");
        comboRegistro2.addItem("Registro D");
        this.add(comboRegistro2, c);
  
       comboRegistro2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // Acciones a realizar cuando se selecciona una operación en el JComboBox
        String operacionSeleccionada = comboRegistro2.getSelectedItem().toString();
        if(operacionSeleccionada == "Registro E"){
                    
        }
    }
}); 
       
       //boton envio
        c.gridx = 3;
        c.gridy = 22;
        c.gridheight = 1;
        c.gridwidth = 5;
    btnEnviar = new JButton("Enviar");
    btnEnviar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Acciones a realizar cuando se presiona el botón Enviar
        procesarEnvio();
    }
});
    this.add(btnEnviar, c);
       
       
       
        
        getControl().cambioLineasControl();
        repaint();
    }

    protected JLabel crearLabel(String str){
        JLabel b = new JLabel(str);
        b.setPreferredSize(BUTTON_SIZE);
        b.setBackground(BUTTON_UNSELECTED_BG);
        b.setFont(new java.awt.Font("Arial", 0, 15)); 
        b.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        b.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        b.setOpaque(true);
        return b;
    }

    
    private void procesarEnvio() {
        
        //AQUI SE ENVIA EL MENSAJE AL OPRIMIR EL BOTON ENVIAR , VA A CONTROLADORWIDGETRAM
        
   // this.btnEnviar.setActionCommand("openAssembler");
    //this.btnEnviar.addActionListener((ActionListener) getControl2());
    // Obtener la operación seleccionada del JComboBox
    String operacionSeleccionada = comboOperaciones.getSelectedItem().toString();
    // Obtener el registro 1
    String Registro1 = comboRegistro1.getSelectedItem().toString();
    // Obtener el registro 2
    String Registro2 = comboRegistro2.getSelectedItem().toString();
    // Obtener el texto ingresado en el JTextField
    String textoIngresado = textField.getText();

    // Mostrar un mensaje con la información recopilada
    String mensaje = "Operación: " + operacionSeleccionada + "\nRegistro1: " + Registro1 + "\nRegistro2: " + Registro2 + "\nEspacio de memoria: " + textoIngresado;
    JOptionPane.showMessageDialog(null, mensaje, "Mensaje de Envío", JOptionPane.INFORMATION_MESSAGE);
}
    
    
    
    public Modelo getModelo() {
        return modelo;
    }
        
    public ControladorWindgetSAP getControl() {
        if(control == null){
            control = new ControladorWindgetSAP(this);
        }
        return control;
    }

    
     public ControladorWidgetRAM getControl2() {
        if (control2 == null) {
            control2 = new ControladorWidgetRAM();
        }
        return control2;
     }
    
    
    public JLabel[] getBtns_bitsA() {
        return btns_bitsA;
    }

    public JLabel[] getBtns_bitsB() {
        return btns_bitsB;
    }

    public JLabel[] getBtns_bitsALU() {
        return btns_bitsALU;
    }

    public JLabel[] getBtns_bitsIR() {
        return btns_bitsIR;
    }

    public JLabel[] getBtns_bitsOUT() {
        return btns_bitsOUT;
    }

    public JLabel[] getBtns_bitsPC() {
        return btns_bitsPC;
    }

    public JLabel[] getBtns_bitsMAR() {
        return btns_bitsMAR;
    }

    public JLabel[] getBtns_bitsControl() {
        return btns_bitsControl;
    }

    public JLabel[] getBtns_bistBUS() {
        return btns_bistBUS;
    }

    public JLabel getBtnCarry() {
        return btnCarry;
    }

    public JLabel getBtnZero() {
        return btnZero;
    }

    public JLabel getLblStepCt() {
        return lblStepCt;
    }

    public VistaDisplaySieteSeg getVistaDisplay() {
        return vistaDisplay7Seg;
    }

    public VistaWidgetRAM getRamWidget() {
        return ramWidget;
    }
    
    
    
}
