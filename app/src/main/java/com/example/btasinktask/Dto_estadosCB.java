package com.example.btasinktask;

public class Dto_estadosCB {

    boolean estadocb1 = false;
    boolean estadocb2 = false;
    boolean estadocb3  = false;
    boolean estadocb4 = false;


    public Dto_estadosCB() {
    }

    public Dto_estadosCB(boolean estadocb1, boolean estadocb2, boolean estadocb3, boolean estadocb4) {
        this.estadocb1 = estadocb1;
        this.estadocb2 = estadocb2;
        this.estadocb3 = estadocb3;
        this.estadocb4 = estadocb4;
    }


    public boolean isEstadocb1() {
        return estadocb1;
    }

    public void setEstadocb1(boolean estadocb1) {
        this.estadocb1 = estadocb1;
    }

    public boolean isEstadocb2() {
        return estadocb2;
    }

    public void setEstadocb2(boolean estadocb2) {
        this.estadocb2 = estadocb2;
    }

    public boolean isEstadocb3() {
        return estadocb3;
    }

    public void setEstadocb3(boolean estadocb3) {
        this.estadocb3 = estadocb3;
    }

    public boolean isEstadocb4() {
        return estadocb4;
    }

    public void setEstadocb4(boolean estadocb4) {
        this.estadocb4 = estadocb4;
    }
}
