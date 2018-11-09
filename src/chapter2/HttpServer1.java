package chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {
    public static final String SHUT_DOWN = "/SHUTDOWN";

    private static boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 server1 = new HttpServer1();
        server1.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        int port = 8080;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutdown) {
            try {
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                Request request = new Request(inputStream);
                request.parse();

                Response response = new Response(outputStream);
                response.setRequest(request);

                RequestFacade requestFacade = new RequestFacade(request);
                ResponseFacade responseFacade = new ResponseFacade(response);

                if (request.getUri().startsWith("/servlet")) {
                    ServletProcessor1 processor = new ServletProcessor1();
                    processor.process(request, response);
                }else {
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request, response);
                }

                socket.close();

                if (request.getUri().equals(SHUT_DOWN)) {
                    shutdown = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }
    }

}
