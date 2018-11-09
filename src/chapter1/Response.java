package chapter1;

import java.io.*;

public class Response {
    private static final int BUFFSIZE = 1024;

    Request request;

    OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        FileInputStream fis = null;
        try {
            byte[] bytes = new byte[BUFFSIZE];
            System.out.println(request.getUri());
            File file = new File(HttpServer.WEB_ROOT, request.getUri());

            if (file.exists()) {
                fis = new FileInputStream(file);
                String header = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html \r\n\r\n";
                outputStream.write(header.getBytes());

                int i = fis.read(bytes);
                while (i != -1) {
                    outputStream.write(bytes, 0, i);
                    i = fis.read(bytes);
                }
            } else {
                String ErrorMsg = "HTTP/1.1 404 File Not Found\r\n"
                        + "Content-Type: text/html \r\n"
                        + "Content-Type: 23 \r\n\r\n"
                        + "<h1>File Not Found</h1>";

                outputStream.write(ErrorMsg.getBytes());
            }
        }finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
