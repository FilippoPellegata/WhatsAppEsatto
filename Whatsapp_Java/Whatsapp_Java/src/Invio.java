
import java.awt.Frame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pellegata_Filippo
 */
public class Invio {
    String richiesta;
    WhatsApp frame;
    DatagramSocket socket;
   

    public Invio(WhatsApp frame) throws SocketException {
        richiesta = "";
        socket=new DatagramSocket(); // da cambiare con random
        this.frame=frame;
    }

    public WhatsApp getFrame() {
        return frame;
    }
    
    
    public void invioRichiesta1() throws IOException{
        richiesta="a"+";"+frame.getNome() +" "+ frame.getCognome();
        
        byte[] responseBuffer = richiesta.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        responsePacket.setAddress(InetAddress.getByName(frame.getIp()));

        responsePacket.setPort(12346);

        socket.send(responsePacket);
    }
    
    

    
    public void inviaMessaggio(String messaggio, InetAddress indirizzo, int porta) throws IOException {
        byte[] responseBuffer = messaggio.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        responsePacket.setAddress(InetAddress.getByName(frame.getIp()));

        responsePacket.setPort(12346);

        socket.send(responsePacket);
    }
    
    
    public void inviaMessaggio2(String messaggio) throws IOException {
        messaggio="m;"+messaggio;
        
        byte[] responseBuffer = messaggio.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        responsePacket.setAddress(InetAddress.getByName(frame.getIp()));

        responsePacket.setPort(12346);

        socket.send(responsePacket);
    }
    
    public void termina(String messaggio) throws UnknownHostException, IOException{
        
        
        byte[] responseBuffer = messaggio.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

        responsePacket.setAddress(InetAddress.getByName(frame.getIp()));

        responsePacket.setPort(12346);

        socket.send(responsePacket);

    }
    
}
