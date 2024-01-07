import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String args[]) throws UnknownHostException {

        DatagramSocket dSocket = null;
        InetAddress IP = InetAddress.getLocalHost();

        try {
            int numDia, numMes, numYear;

            Scanner lee = new Scanner(System.in);
            // pide el mensaje al usuario
            boolean valido = false;
            do {
                System.out.print("Introduce el numero de dia:");
                numDia = Integer.parseInt(lee.nextLine());
                if ((numDia > 0) && (numDia < 32)) {
                    valido = true;
                }
            } while (!valido);

            valido = false;
            do {
                System.out.print("Introduce el numero de mes:");
                numMes = Integer.parseInt(lee.nextLine());
                if ((numMes > 0) && (numMes < 13)) {
                    valido = true;
                }
            } while (!valido);

            valido = false;
            do {
                System.out.print("Introduce el numero de year (4 cifras y superior a 1800):");
                numYear = Integer.parseInt(lee.nextLine());
                if (numYear > 1800)  {
                    valido = true;
                }
            } while (!valido);
            lee.close();

            // prepara el mensaje en formato YYYYMMDD
            numDia = numDia * 1;
            numMes = numMes * 100;
            numYear = numYear * 10000;
            String mensajeEnviado = Integer.toString(numYear + numMes + numDia);

            // envio del datagrama
            dSocket = new DatagramSocket();
            InetAddress aHost = IP;
            int puerto = 9000;

            DatagramPacket dpEnvio = new DatagramPacket(mensajeEnviado.getBytes(), mensajeEnviado.length(), aHost,
                    puerto);
            dSocket.send(dpEnvio);

            // Recepcion del datagrama
            byte[] mensajeRecibido = new byte[1000];
            DatagramPacket dpRespuesta = new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
            dSocket.receive(dpRespuesta);

            System.out.println("[Servidor] " + new String(dpRespuesta.getData()));
        } catch (SocketException e) {
            System.out.println("SocketException en main(): " + e);
        } catch (IOException e) {
            System.out.println("IOException en main(): " + e);
        } finally {
            if (dSocket != null) {
                dSocket.close();
            }
        }
    }
}
