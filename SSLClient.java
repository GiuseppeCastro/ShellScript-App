import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class SSLClient {
    public static void main(String[] args) {
        try {
            // Carregar o TrustStore
            char[] truststorePassword = "senha".toCharArray();
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream("truststore.jks"), truststorePassword);

            // Configurar o TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            // Configurar o SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            // Criar o SSLSocket
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("localhost", 8443);

            System.out.println("Conectado ao servidor SSL");

            // Ler mensagem criptografada
            BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            String message = in.readLine();
            System.out.println("Mensagem recebida: " + message);

            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}