
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    int statoConnessione = 0; // 0 non connesso, 1 accettata, 2 connesso
    
    Invio invio;
    
    public ThreadConnessione(WhatsApp frame1, Invio invio) throws SocketException {
        server = new DatagramSocket(12346);
        frame=frame1;
        this.invio = invio;
    }

    //richiedi connessione    
    public String[] riceviMessaggio() throws SocketException, IOException {

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

    public void elaboraMessaggio(String[] messaggio) throws IOException {

        //primo caso+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (messaggio[0].equals("a") && statoConnessione == 0) {

            Object[] options = {"si", "no"};
            int n = JOptionPane.showOptionDialog(frame,
                    "vuoi instaurare la connessione?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            //n = 0;
//se si----------------------------------------------------------------------------------------
            String risposta = "";

            if (n == 0) {
                
                risposta = "y" + ";" + frame.getNome() +" "+ frame.getCognome();
                 nome= frame.getNome()+"_"+frame.getCognome();
                 statoConnessione = 1;
                
            }//se no-------------------------------------------------------------------------------------------
            else if (n == 1) {
                risposta = "n;";  
            }
            
            invio.inviaMessaggio(risposta, indirizzo, port);

        }
        
        else if(messaggio[0].equals("a") && (statoConnessione == 1 || statoConnessione == 2)){
            Object[] options = {"ok"};
            int n = JOptionPane.showOptionDialog(frame,
                    "connessione già occupata", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }
        //se riceve y e il nome faccio..., terzo punto connessione
        else if(messaggio[0].equals("y")&&messaggio.length==2){
            Object[] options = {"si", "no"};
            int n = JOptionPane.showOptionDialog(frame,
                    "vuoi instaurare la connessione?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

//se si----------------------------------------------------------------------------------------
        String risposta = "";

        if (n == 0) {

                risposta = "y;";
                statoConnessione = 2;
                 frame.setNomeChat(nome );

                
        }else if (n == 1) {
                risposta = "n;";
                statoConnessione = 0;
                
            }
        
                invio.inviaMessaggio(risposta, indirizzo, port);
      
           
       }else if(messaggio[0].equals("y") && messaggio.length == 1){
           frame.setNomeChat(nome );
           statoConnessione = 2;
       }
    
    
}
    
    @Override
    public void run() {
        String[] m = null;
        
        while (true) {
        try {
            m = riceviMessaggio();
            for (int i = 0; i < m.length; i++) {
            System.out.println(m[i]);
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadConnessione.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            try {
                elaboraMessaggio(m);
            } catch (IOException ex) {
                Logger.getLogger(ThreadConnessione.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}