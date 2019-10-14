package com.example.btasinktask;

public class Dto_variables {

    private String informacion = "";

    String temperatura_corporal;
    String frecuencia_respiratoria;
    String presion_arterial;
    //SPO2: Saturación Parcial del Oxígeno
    //      Pulso o Frecuencia Cardiaca y Saturación Parcial del Oxígeno.
    String frecuencia_cardiaca_o_pulso;
    String saturacion_parcial_oxigeno_SPO2;

    String alarma;

    String dataStream;

    //String fecha;
    //String hora;


    public Dto_variables() {
    }


    public String getTemperatura_corporal() {
        return temperatura_corporal;
    }

    public void setTemperatura_corporal(String temperatura_corporal) {
        this.temperatura_corporal = temperatura_corporal;
    }

    public String getFrecuencia_respiratoria() {
        return frecuencia_respiratoria;
    }

    public void setFrecuencia_respiratoria(String frecuencia_respiratoria) {
        this.frecuencia_respiratoria = frecuencia_respiratoria;
    }

    public String getPresion_arterial() {
        return presion_arterial;
    }

    public void setPresion_arterial(String presion_arterial) {
        this.presion_arterial = presion_arterial;
    }

    public String getFrecuencia_cardiaca_o_pulso() {
        return frecuencia_cardiaca_o_pulso;
    }

    public void setFrecuencia_cardiaca_o_pulso(String frecuencia_cardiaca_o_pulso) {
        this.frecuencia_cardiaca_o_pulso = frecuencia_cardiaca_o_pulso;
    }

    public String getSaturacion_parcial_oxigeno_SPO2() {
        return saturacion_parcial_oxigeno_SPO2;
    }

    public void setSaturacion_parcial_oxigeno_SPO2(String saturacion_parcial_oxigeno_SPO2) {
        this.saturacion_parcial_oxigeno_SPO2 = saturacion_parcial_oxigeno_SPO2;
    }

    public String getAlarma() {
        return alarma;
    }

    public void setAlarma(String alarma) {
        this.alarma = alarma;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getDataStream() {
        return dataStream;
    }

    public void setDataStream(String dataStream) {
        this.dataStream = dataStream;
    }


}
