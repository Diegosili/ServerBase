/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbase;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author siliprandi.diego
 */
public class ServerBase {
    private int port;
    private ServerSocket SS;
    
    public ServerBase(int port) {
        this.port = port;
        if(!startServer()) {
            System.err.println("errore in fase di creazione");
        }
    }
    
    private boolean startServer(){
        try {
            SS = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        System.out.println("Server creato con successo");
        return true;
    }
    
    public void runServer(){
        try {
            while(true) {
                System.out.println("Server in attesa di richiesta...");
                
                Socket client = SS.accept();
                System.out.println("Un cliente connesso!!!");
                
                OutputStream versoIlClient = client.getOutputStream();
                BufferedWriter scrittore = new BufferedWriter(new OutputStreamWriter(versoIlClient));
                InputStream dalClient = client.getInputStream();
                BufferedReader lettore = new BufferedReader(new InputStreamReader(dalClient));
                
                scrittore.write("Ciao bello sono il server");
                String risposta = lettore.readLine();
                System.out.println("risposta del client: "+risposta);
                scrittore.write("Come va la vita?");
                risposta = lettore.readLine();
                System.out.println("risposta del client: "+risposta);
                
                scrittore.close();
                lettore.close();
                client.close();
                
                System.out.println("Chiusura connessione effettuata");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerBase.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerBase server = new ServerBase(6666);
        server.runServer();
    }
}
    

