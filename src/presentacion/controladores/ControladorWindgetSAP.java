
package presentacion.controladores;

import interfaces.SAPObserver;
import presentacion.Modelo;
import logica.SistemaSAP;
import presentacion.vistas.VistaWidgetSAP;


public class ControladorWindgetSAP implements SAPObserver{

    private final VistaWidgetSAP widgetSAP;
    private final Modelo modelo;
    private final SistemaSAP sistema;

    public ControladorWindgetSAP(VistaWidgetSAP aThis) {
        widgetSAP = aThis;
        modelo = widgetSAP.getModelo();
        sistema = modelo.getSistema();
    }   

    // Si cambia el registro A/B o cambia la bandera SUB, vuelva a pintar
    private void actualizarALU() {
        for (int i = 0; i <= 15; i++) {
            widgetSAP.getBtns_bitsALU()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.ALU, 15 - i));
        }
    }

    @Override
    public void cambioRegistroA(int v) {
        for (int i = 0; i <= 15; i++) {
            widgetSAP.getBtns_bitsA()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.A, 15 - i));
        }
        actualizarALU();
    }

    @Override
    public void cambioRegistroB(int v) {
        for (int i = 0; i <= 15; i++) {
            widgetSAP.getBtns_bitsB()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.B, 15 - i));
        }
        actualizarALU();
    }

    @Override
    public void cambioPC(int v) {
        for (int i = 0; i <= 5; i++) {
            widgetSAP.getBtns_bitsPC()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.PC, 5 - i));
        }
    }

    @Override
    public void cambioMAR(int v) {
        for (int i = 0; i <= 6; i++) {
            widgetSAP.getBtns_bitsMAR()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.MAR, 6 - i));
        }
        widgetSAP.getRamWidget().getControl().cambioMAR(v);
    }

    @Override
    public void cambioOUT(int v) {
        for (int i = 0; i <= 15; i++) {
            widgetSAP.getBtns_bitsOUT()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.OUT, 15 - i));
        }
        widgetSAP.getVistaDisplay().setValor(v);
    }

    @Override
    public void cambioIR(int v) {
        for (int i = 0; i <= 15; i++) {
            widgetSAP.getBtns_bitsIR()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.IR, 15 - i));
        }
    }

    @Override
    public void cambioConteoPaso(int newVal) {
        widgetSAP.getLblStepCt().setText("" + newVal);
    }

    @Override
    public void cambioFLAGS() {
        if(this.sistema.getFlags().getZF()){
            widgetSAP.getBtnZero().setBackground(VistaWidgetSAP.BUTTON_SELECTED_BG);
        }else{
            widgetSAP.getBtnZero().setBackground(VistaWidgetSAP.BUTTON_UNSELECTED_BG);
        }
        if(this.sistema.getFlags().getCF()){
            widgetSAP.getBtnCarry().setBackground(VistaWidgetSAP.BUTTON_SELECTED_BG);
        }else{
            widgetSAP.getBtnCarry().setBackground(VistaWidgetSAP.BUTTON_UNSELECTED_BG);
        }        
    }
    
    @Override
    public void cambioBUS(int v) {
        for (int i = 0; i <= 16; i++) {
            widgetSAP.getBtns_bistBUS()[i].setText(sistema.decodificarRegistro(SistemaSAP.TipoRegistro.BUS, 16 - i));
        }
    }

    @Override
    public void cambioLineasControl() {
        boolean[] newLines = this.sistema.getControlLines();
        for (int i = 0; i < 16; i++) {
            if (newLines[i]) {
                widgetSAP.getBtns_bitsControl()[i].setBackground(widgetSAP.BUTTON_SELECTED_BG);
            } else {
                widgetSAP.getBtns_bitsControl()[i].setBackground(widgetSAP.BUTTON_UNSELECTED_BG);
            }
        }
    }
}
