// Programa que pide los bigramas al servidor por medio de Sockets
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class Cliente {


    public Cliente()
    {
    }



    public void correr()
    {
      Socket echo = null;
        try {

            //create socket
            echo = new Socket("localhost", 8888);

            //PrintWriter out = new PrintWriter(echo.getOutputStream(), true);

            BufferedReader br = new BufferedReader(new InputStreamReader(echo.getInputStream()));

            BufferedReader stdbr = new BufferedReader(new InputStreamReader(System.in));

            ObjectOutputStream outputStream = new ObjectOutputStream(echo.getOutputStream());


            //program
            String in;
            System.out.println("Escriba el bigrama :");
            while ((in=stdbr.readLine()) != null) {

                //write to socket out stream
                MBigram bigram = new MBigram(in, 0);
                outputStream.writeObject(bigram);
                
                //read from in stream and write out
                System.out.println("res->: "+br.readLine());
                outputStream.flush();
                System.out.println("Escriba el siguiente bigrama :");
            }

        } catch(IOException e) {
            System.err.println("Something bad went wrong ");

            System.exit(1);
        }
        catch (Exception e ) {
            System.err.println("Something bad went wrong ");
            System.exit(1);
        }
    }
    public static void main( String args[] )
    {
        Cliente app = new Cliente();
        app.correr();
    }
}
