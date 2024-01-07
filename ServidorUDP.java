import java.net.*;
import java.io.*;

public class ServidorUDP {
    public static void main(String args[]) {

        DatagramSocket dSocket = null;

        try {
            // numero de puerto
            int puerto = 9000;
            dSocket = new DatagramSocket(puerto);
            byte[] mensajeRecibido = new byte[1000];

            while (true) {
                // Recibo y leo datagrama
                DatagramPacket dpRecibido = new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
                dSocket.receive(dpRecibido);
                System.out.println("[Cliente] " + new String(dpRecibido.getData()));
                mensajeRecibido = new byte[1000];

                // Obtengo el contenido del mensaje como String
                String mensajeString = new String(dpRecibido.getData()).trim();

                // Calculamos el dia de la semana
                int fechaBase = Integer.parseInt(mensajeString);
                LibFechas fecha = new LibFechas(fechaBase);

                // Convierte el String a un arreglo de bytes
                byte[] mensajeAEnviar = fecha.getDiaSemanaYFecha().getBytes();

                // Envio el mismo paquete recibido al remitente
                DatagramPacket dpRespuesta = new DatagramPacket(
                        mensajeAEnviar, mensajeAEnviar.length, dpRecibido.getAddress(), dpRecibido.getPort());
                dSocket.send(dpRespuesta);
            }
        } catch (SocketException e) {
            System.out.println("SocketException en main(): " + e);
        } catch (IOException e) {
            System.out.println("IOException en main():" + e);
        } finally {
            if (dSocket != null) {
                dSocket.close();
            }
        }
    }
}