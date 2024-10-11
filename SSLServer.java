import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class SSLServer {
    public static void main(String[] args) {
        try {
            // Carregar o KeyStore
            char[] keystorePassword = "senha".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("keystore.jks"), keystorePassword);

            // Configurar o KeyManagerFactory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keystorePassword);

            // Configurar o SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            // Criar o SSLServerSocket
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(8443);

            System.out.println("Servidor SSL iniciado na porta 8443");

            while (true) {
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                System.out.println("Cliente conectado: " + sslSocket.getInetAddress());

                // Enviar mensagem criptografada
                PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
                out.println("Conexão segura estabelecida com sucesso!");

                sslSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}