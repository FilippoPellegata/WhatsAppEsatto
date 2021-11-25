
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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

    public Invio() throws SocketException {
        richiesta = "";
        socket=new DatagramSocket();
    }
    
    
    public void invioRichiesta1() throws IOException{
        richiesta="a"+";"+frame.getNome() +" "+ frame.getCognome();
        
        byte[] responseBuffer = richiesta.getBytes();

DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

responsePacket.setAddress(InetAddress.getByName(frame.getIp().getText()));

responsePacket.setPort(12345);

socket.send(responsePacket);
    }
    
    
}
