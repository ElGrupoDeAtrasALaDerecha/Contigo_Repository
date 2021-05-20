package usa.modelo.dto.decorador;

import usa.modelo.dto.PersonalCalificado;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class Certificado extends Informacion {

    private String tipoCertificacion;
    private String soporte;

    public Certificado(IInformacion informacion) {
        super(informacion);
    }

    public Certificado() {
        super();
        this.soporte = "";
        this.tipoCertificacion = "";
    }

    public String getTipoCertificacion() {
        return tipoCertificacion;
    }

    public void setTipoCertificacion(String tipoCertificacion) {
        this.tipoCertificacion = tipoCertificacion;
    }

    public String getSoporte() {
        return soporte;
    }

    public void setSoporte(String soporte) {
        this.soporte = soporte;
    }

    @Override
    public void agregarInformacion(){
        super.agregarInformacion(this);
    }

    @Override
    public void agregarInformacion(IInformacion i){
        super.agregarInformacion(this);
        informacion.agregarInformacion(i);
    }
}
