package chapter1;

import java.io.*;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("www.google.com", 80);
        boolean autoFlush = true;
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), autoFlush);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter.println("GET / HTTP/1.1");
        printWriter.println("Host: 172.20.7.101:8080");
        printWriter.println("Connection: Close");
        printWriter.println();

        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while (loop) {
            if (reader.ready()) {
                int i = 0;
                while (i != -1) {
                    i = reader.read();
                    sb.append((char) i);
                }
                loop = false;
                Thread.currentThread().sleep(50);
            }

        }

        System.out.println(sb.toString());
        socket.close();

    }
}
