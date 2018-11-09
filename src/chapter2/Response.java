package chapter2;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {

    private static final int BUFF_SIZE = 4096;

    private OutputStream outputStream;

    private Request request;

    private PrintWriter writer;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFF_SIZE];
        FileInputStream fis = null;

        String uri = request.getUri();
        File file = new File(Constants.WEBROOT, uri);
        try {
            if (file.exists()) {
                String message = "HTTP/1.1 200 ok \r\n"
                        + "Content-Type: text/html;"
                        + "Content-Length: " + file.length() + "\r\n"
                        + "Server: JAVA-8;\r\n\r\n";
                outputStream.write(message.getBytes());

                fis = new FileInputStream(file);
                int i = fis.read(bytes);
                while (i != -1) {
                    outputStream.write(bytes, 0, i);
                    i = fis.read(bytes);
                }
            } else {
                String message = "HTTP/1.1 404 File Not Found\r\n"
                        + "Content-Type: text/html; \r\n"
                        + "Content-Lenght: 23 \r\n\r\n"
                        + "<h1> 404 </h1> <h2> File Not Found </h2>";
                outputStream.write(message.getBytes());
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(outputStream, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
