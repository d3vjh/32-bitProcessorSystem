package presentacion.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.EventLog;
import logica.SistemaSAP;
import presentacion.Modelo;
import presentacion.vistas.VistaPanelAssembler;
import presentacion.vistas.VistaPanelCPU;
import presentacion.vistas.VistaWidgetRAM;

public class ControladorWidgetRAM implements interfaces.IRAMObserver, ActionListener {

    private final VistaWidgetRAM widgetRAM;
    private VistaPanelCPU view;
    private final SistemaSAP sistema;    
    private final Modelo modelo;

    public ControladorWidgetRAM(VistaWidgetRAM aThis) {
        widgetRAM = aThis;
        view = widgetRAM.getView();
        sistema = widgetRAM.getSistema();
        modelo = aThis.getModelo();
    }

    @Override
    // Si un valor en la memoria ha cambiado, entonces hay que redibujar
    public void cambiaValorRAM(int address) {

        // Iterar sobre todos los bits en la posición de memoria actual
        for (int i = 0; i <= 15; i++) {
            System.out.println("Vamos en el  " + i);
            widgetRAM.getBtnArrayBotones()[address][i].setText("" + this.buscarEnRAM(address, 15 - i));

            // Compruebar si es el valor MAR, en cuyo caso se necesita un color para resaltar
            if (widgetRAM.isDebeResaltarMAR() && address == widgetRAM.getValorMAR()) {
                widgetRAM.getBtnArrayBotones()[address][i].setBackground(widgetRAM.COLOR_MAR);
            } else {
                widgetRAM.getBtnArrayBotones()[address][i].setBackground(widgetRAM.getBtnArrayBotones()[address][i].getText().equals("1") ? widgetRAM.COLOR_ON : widgetRAM.COLOR_OFF);
            }

            // Si estamos en la posición más a la derecha, mantenga el borde
            if (i == 15) {
                widgetRAM.getBtnArrayBotones()[address][i].setBorder(widgetRAM.RIGHT_BORDER);
            } else {
                widgetRAM.getBtnArrayBotones()[address][i].setBorder(null);
            }

            // Si estamos en la fila inferior, mantener el borde.
            if (address == 64) {
                if (i == 15) {
                    widgetRAM.getBtnArrayBotones()[address][i].setBorder(widgetRAM.BOTTOM_RIGHT_BORDER);
                } else {
                    widgetRAM.getBtnArrayBotones()[address][i].setBorder(widgetRAM.BOTTOM_BORDER);
                }
            }
        }
    }

    public void cambioMAR(int v) {
        // Si no estamos en modo resaltado
        if (!widgetRAM.isDebeResaltarMAR()) {
            cambiaValorRAM(v);
            return;
        }

        // Coge el antiguo valor MAR
        int oldVal = widgetRAM.getValorMAR();

        // Pintar el nuevo valor con el color correcto
        widgetRAM.setValorMAR(v);
        cambiaValorRAM(widgetRAM.getValorMAR());

        // Elimina la coloración especial del antiguo valor MAR
        cambiaValorRAM(oldVal);
    }

    @Override
    // Responde al clic del botón que indica un cambio de bit en la memoria
    public void actionPerformed(ActionEvent e) {
        // Si el usuario desea alternar el resaltado MAR en la parte de RAM del widget
        if (e.getActionCommand().contentEquals("toggleMAR")) {
            // Alternar valor de estado
            widgetRAM.setDebeResaltarMAR (!widgetRAM.isDebeResaltarMAR());

            // Actualizar etiqueta de botón
            widgetRAM.getHighlightMarButton().setText(widgetRAM.isDebeResaltarMAR() ? widgetRAM.MAR_ON_LABEL : widgetRAM.MAR_OFF_LABEL);

            // Actualizar visualización
            this.cambioMAR(widgetRAM.getValorMAR());

            return;
        }

        // Si el programa se está ejecutando, primero detenerlo
        if (this.modelo.isEjecutandoPrograma()) {
            ActionEvent x = new ActionEvent("", 5, "autoplay");
            this.view.getControl().actionPerformed(x);

            // Dormir para que el registro de eventos no se agrupe
            try {
                Thread.sleep(25);
            } catch (InterruptedException e1) {
                EventLog.getEventLog().addEntrada("Error en sleep para 100 ms");
            }
        }

        // Si el usuario quiere abrir el ensamblador
        if (e.getActionCommand().contentEquals("openAssembler")) {
            // Crear instancia del ensamblador
            VistaPanelAssembler vistaPanelAssembler = new VistaPanelAssembler(modelo, widgetRAM.getParentPanel());

            // Establecer la vista en el ensamblador (de la ventana actual)
            modelo.getVentanaPrincipal().setContentPane(vistaPanelAssembler);
            modelo.getVentanaPrincipal().pack();
            modelo.getVentanaPrincipal().setVisible(true);

            return;
        }

        // Si el usuario hace clic en el botón analizar programa
        if (e.getActionCommand().contentEquals("analyzeProgram")) {
            EventLog.getEventLog().addEntrada("=============");
            EventLog.getEventLog().addEntrada("[DIR]\t[INSTR]\t[DEC]");
            for (int i = 0; i < 16; i++) {
                this.sistema.analizarInstruccion(i);
            }
            EventLog.getEventLog().addEntrada("=============");
            return;
        }

        // Si el usuario hace clic en el botón borrar memoria
        if (e.getActionCommand().contentEquals("clearmem")) {
            // Obtener el contenido de la memoria
            int[] arr = this.sistema.getRAM().getData();

            for (int i = 0; i < 64; i++) {
                // Colocamos cada posición en 0
                arr[i] = 0;
            }

            // Obligar a la pantalla a volver a pintar dos veces, para manejar el retraso visual
            for (int i = 0; i < 64; i++) {
                this.cambiaValorRAM(i);
            }
            for (int i = 0; i < 64; i++) {
                this.cambiaValorRAM(i);
            }
            return;
        }

        // Si el usuario hace clic en el botón Mostrar los códigos de operación
        if (e.getActionCommand().contentEquals("showopcodes")) {
            EventLog.getEventLog().addEntrada("=============");
            EventLog.getEventLog().addEntrada("NOP\t000000");
            EventLog.getEventLog().addEntrada("LDA\t000001");
            EventLog.getEventLog().addEntrada("ADD\t000010");
            EventLog.getEventLog().addEntrada("SUB\t000011");
            EventLog.getEventLog().addEntrada("STA\t000100");
            EventLog.getEventLog().addEntrada("LDI\t000101");
            EventLog.getEventLog().addEntrada("JMP\t000110");
            EventLog.getEventLog().addEntrada("JC\t000111");
            EventLog.getEventLog().addEntrada("JZ\t001000");
            EventLog.getEventLog().addEntrada("OUT\t001110");
            EventLog.getEventLog().addEntrada("HLT\t001111");
            EventLog.getEventLog().addEntrada("INP\t010000");
            EventLog.getEventLog().addEntrada("OTP\t010001");
            EventLog.getEventLog().addEntrada("=============");
            return;
        }

        // Si el usuario hace clic en el botón Cargar programa de demostración
        if (e.getActionCommand().contentEquals("loadcountprogram")) {
            // Toma la representación interna de la RAM
            int[] arr = this.sistema.getRAM().getData();

            // Primero borramos la memoria
            for (int i = 0; i < 64; i++) {
                // Coloca en cada posición 0
                arr[i] = 0;
                // La pantalla vuelve a pintar
                this.cambiaValorRAM(i);

            }

            // Actualizamos el contenido de la memoria con el programa demo
            // Nota: Este programa es un simple contador
            arr[0] = (int) 0b0000010100000000;
            this.cambiaValorRAM(0);
            arr[1] = (int) 0b0000001000001110;
            this.cambiaValorRAM(1);
            arr[2] = (int) 0b0000111000000000;
            this.cambiaValorRAM(2);
            arr[3] = (int) 0b0000010000001010;
            this.cambiaValorRAM(3);
            arr[4] = (int) 0b0000011000000001;
            this.cambiaValorRAM(4);
            arr[14] = (int) 0b0000000000000001;
            this.cambiaValorRAM(14);

            return;
        }

        // De lo contrario, el usuario debe haber solicitado un cambio de bit en algún lugar de la memoria.
        
        // Analizar la dirección de memoria
        int address = Byte.parseByte(e.getActionCommand().substring(0, e.getActionCommand().indexOf(",")));
        // Analizar el cambio de  bit en la posición 
        int bitPos = Byte.parseByte(e.getActionCommand().substring(e.getActionCommand().indexOf(",") + 1));
        bitPos = (int) (15 - bitPos);

        // Obtener el valor actual del bit agregar la posición modificada
        int currVal = buscarEnRAM(address, bitPos);
        // Obtenga el valor actual de la memoria en la dirección especificada
        int memVal = this.sistema.getRAM().getData()[address];

        // Determinar si necesitamos restar o sumar
        int newVal;
        if (currVal == 1) {
            // Restar   
            newVal = (int) (memVal - Math.pow(2, bitPos));
        } else {
            // Sumar
            newVal = (int) (memVal + Math.pow(2, bitPos));
        }
        this.sistema.getRAM().cambiarValor(address, newVal);

        // Informe al log
        logica.EventLog.getEventLog().addEntrada("Dirección de memoria " + address + " cambió a " + newVal);
    }
    
    // Función auxiliar para acceder a bits individuales en la memoria; Address: [0, 15]
    // bitPos: [0, 7]
    public int buscarEnRAM(int address, int bitPos) {
        int val = 0b1111111111111111 & this.sistema.getRAM().getData()[address];
        return (val >> bitPos) & 0b1;
    }
}
