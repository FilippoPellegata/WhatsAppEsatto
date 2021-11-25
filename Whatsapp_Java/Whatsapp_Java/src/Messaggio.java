/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pelle
 */
public class Messaggio {
    
    int numeroId;
    String Messaggio;
    boolean statoInvio;

    public Messaggio() {
        numeroId=0;
        Messaggio="";
        statoInvio=false;
    }

    public void setId(int _id) {
        this.numeroId = _id;
    }

    public void setContenutoMessaggio(String MessaggioRicevuto) {
        this.Messaggio = MessaggioRicevuto;
    }

    public void setStato(boolean stato) {
        this.statoInvio = stato;
    }

    public int getId() {
        return numeroId;
    }

    public String getContenutoMessaggio() {
        return Messaggio;
    }

    
}
