package com.example.btasinktask;

public class dto_pacientes {

    //Objeto de Datos de Transferencia para Tabla: tb_pacientes
    String dui;
    String nombres;
    String apellidos;
    String direccion;
    String telefono;
    String estado1;
    String fecha1;
    String comentario;
    String nombre_cont_p;
    String telefono_cont_p;
    String direccion_cont_p;
    String documento_especialista;  //FK.

    public dto_pacientes() {
    }

    public dto_pacientes(String dui, String nombres, String apellidos, String direccion, String telefono, String estado1, String fecha1, String comentario, String nombre_cont_p, String telefono_cont_p, String direccion_cont_p, String documento_especialista) {
        this.dui = dui;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado1 = estado1;
        this.fecha1 = fecha1;
        this.comentario = comentario;
        this.nombre_cont_p = nombre_cont_p;
        this.telefono_cont_p = telefono_cont_p;
        this.direccion_cont_p = direccion_cont_p;
        this.documento_especialista = documento_especialista;
    }


    public dto_pacientes(String dui, String nombres, String apellidos, String direccion, String telefono, String fecha1, String documento_especialista) {
        this.dui = dui;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fecha1 = fecha1;
        this.documento_especialista = documento_especialista;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado1() {
        return estado1;
    }

    public void setEstado1(String estado1) {
        this.estado1 = estado1;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombre_cont_p() {
        return nombre_cont_p;
    }

    public void setNombre_cont_p(String nombre_cont_p) {
        this.nombre_cont_p = nombre_cont_p;
    }

    public String getTelefono_cont_p() {
        return telefono_cont_p;
    }

    public void setTelefono_cont_p(String telefono_cont_p) {
        this.telefono_cont_p = telefono_cont_p;
    }

    public String getDireccion_cont_p() {
        return direccion_cont_p;
    }

    public void setDireccion_cont_p(String direccion_cont_p) {
        this.direccion_cont_p = direccion_cont_p;
    }

    public String getDocumento_especialista() {
        return documento_especialista;
    }

    public void setDocumento_especialista(String documento_especialista) {
        this.documento_especialista = documento_especialista;
    }

    @Override
    public String toString() {
        return dui + " " + nombres + " " + apellidos + " " + direccion + " " + telefono + " " + fecha1 + " " + documento_especialista;
        //return dui +  " ~ " + nombres + " ~ " + apellidos;


        /*return dui;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fecha1 = fecha1;
        this.documento_especialista = documento_especialista;*/

    }

}
