// Programa que da resultado sobre diagramas a travez de sockets
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Servidor {

    BufferedReader in;
    PrintWriter out;
    ServerSocket server;
    Socket client;
    BigramController bigramController;
    public Servidor()
    {
      String inputFileName = "Input.txt";
      System.out.println("Procesando archivo " + inputFileName);
      this.bigramController = new BigramController("Input.txt");
      //Read the file and create the bigram hashtable with all the bigrams and the count example:
      // "Word1 Word2" => 2
      this.bigramController.createBigramTable();
      //Collect results and fill a array to sort the result.
      this.bigramController.collectResults();

      this.bigramController.printInfile("Results.txt");

      System.out.println("Bigramas procesados");
      System.out.println("Esperando nuevos clientes");
    }


    public void correr()
    {
       ServerSocket server = null;
       try {

          server = new ServerSocket(8888,5);

           while(!server.isClosed()) {

               Socket accept = server.accept();

               Thread ins = new Thread(){
                   @Override
                   public void run() {
                       try {
                           System.out.println("Nueva Conexión");
                           BufferedReader br = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                           PrintWriter out = new PrintWriter(accept.getOutputStream(), true);

                           String in;

                           while((in=br.readLine()) != null) {
                               in=in.toLowerCase();
                               out.println("El bigrama " + in+  " aparece "+  bigramController.searchBigram(in) + " veces.");
                           }
                       } catch (Exception ex) {

                       }
                   }
               };
               ins.start();
           }

       } catch(Exception e) {
       }
    }





    public static void main( String args[] )
    {
        Servidor app = new Servidor();
        app.correr();
    }
}
