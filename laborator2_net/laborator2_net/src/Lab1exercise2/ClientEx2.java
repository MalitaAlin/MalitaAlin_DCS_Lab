/*
 * ClientSimplu.java
 */

/**
 * Class created by @author Mihai HULEA at Feb 23, 2005.
 * 
 * This class is part of the laborator2_serverclientmonofir project.
 * 
 * 1. Modificati aplicatia client astfel incat aceasta sa trimita catre server mesaje
 * citite de la tastatura. 
 */
package Lab1exercise2;
import java.net.*;
import java.io.*;

public class ClientEx2 {

  public static void main(String[] args)throws Exception{
    Socket socket=null;
    try {
      //creare obiect address care identifica adresa serverului
      InetAddress server_address =InetAddress.getByName("localhost");
      //se putea utiliza varianta alternativa: InetAddress.getByName("127.0.0.1")
      
      socket = new Socket(server_address,1900);

      //construieste fluxul de intrare prin care sunt receptionate datele de la server
      BufferedReader in =
        new BufferedReader(
          new InputStreamReader(
            socket.getInputStream()));
      
      //construieste fluxul de iesire prin care datele sunt trimise catre server
      // Output is automatically flushed
      // by PrintWriter:
      PrintWriter out =
        new PrintWriter(
          new BufferedWriter(
            new OutputStreamWriter(
              socket.getOutputStream())),true);

  	BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
	System.out.println("  This Client allows for calculating the percentage of the first number from the second number.\n ");

      for(int i = 0; i < 10; i ++) {
    		System.out.println("Input two numbers separated by '%': ");
			String numbers = reader.readLine();
			out.println(numbers);
			String result = in.readLine();
			System.out.println("The percentage is "+ result +"%");
			
      }
      out.println("END"); //trimite mesaj care determina serverul sa inchida conexiunea

    }
    catch (Exception ex) {ex.printStackTrace();}
    finally{
      socket.close();
    }
  }
}

