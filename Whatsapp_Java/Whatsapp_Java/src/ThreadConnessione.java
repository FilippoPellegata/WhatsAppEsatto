
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pellegata_Filippo
 */
public class ThreadConnessione extends Thread {

    DatagramSocket server;
    WhatsApp frame;
    String nome="";

    InetAddress indirizzo = null;
    int port = 0;

    public ThreadConnessione(WhatsApp frame1) throws SocketException {
        server = new DatagramSocket(12346);
        frame=frame1;
    }

    //richiedi connessione    
    public String[] richiediConnessione() throws SocketException, IOException {

        byte[] buffer = new byte[1500];

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        server.receive(packet);

        byte[] dataReceived = packet.getData(); // copia del buffer dichiarato sopra

        String messaggioRicevuto = new String(dataReceived, 0, packet.getLength());

        String[] messaggio = messaggioRicevuto.split(";");
        indirizzo=packet.getAddress();
        port=packet.getPort();

        return messaggio;
    }

    public void elaboraMessaggio() throws IOException {
        String[] messaggio = richiediConnessione();

        //primo caso+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (messaggio[0].equals("a")&&(indirizzo==null)) {

            Object[] options = {"si", "no"};
            int n = JOptionPane.showOptionDialog(frame,
                    "vuoi instaurare la connessione?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

//se si----------------------------------------------------------------------------------------
            if (n == 0) {
                
                String risposta = "si" + ";" + frame.getNome() +" "+ frame.getCognome();
                 nome= frame.getNome()+"_"+frame.getCognome();
                byte[] responseBuffer = risposta.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

                responsePacket.setAddress(indirizzo);

                responsePacket.setPort(port);

                server.send(responsePacket);
            }//se no-------------------------------------------------------------------------------------------
            else if (n == 1) {
                String risposta = "n;";

                byte[] responseBuffer = risposta.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

                responsePacket.setAddress(indirizzo);

                responsePacket.setPort(port);

                server.send(responsePacket);
            }

        }
        
        else if(messaggio[0].equals("a")&&(indirizzo==null)){
            Object[] options = {"ok"};
            int n = JOptionPane.showOptionDialog(frame,
                    "connessione già occupata", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        }
        //se riceve y e il nome faccio..., terzo punto connessione
        else if(messaggio[0].equals("y")&&messaggio.length==2&&(indirizzo==null)){
            Object[] options = {"si", "no"};
            int n = JOptionPane.showOptionDialog(frame,
                    "vuoi instaurare la connessione?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

//se si----------------------------------------------------------------------------------------
            if (n == 0) {

                String risposta = "y;";

                byte[] responseBuffer = risposta.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

                responsePacket.setAddress(indirizzo);

                responsePacket.setPort(port);

                server.send(responsePacket);
        }else if (n == 1) {
                String risposta = "n;";

                byte[] responseBuffer = risposta.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

                responsePacket.setAddress(indirizzo);

                responsePacket.setPort(port);

                server.send(responsePacket);
            }
        
      
           
       }else if(messaggio[0].equals("y")&&(indirizzo==null)){
           frame.setNomeChat(nome );
       }
    
    
}
}