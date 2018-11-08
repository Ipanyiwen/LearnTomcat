package chapter1;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private InputStream inputStream;
    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse() {

        StringBuffer buffer = new StringBuffer(2048);
        byte[] bytes = new byte[2048];
        int i = 0;
        try {
            i = inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j = 0; j < i; j++) {
            buffer.append((char) bytes[j]);
        }

        uri = parseUri(buffer.toString());
    }

    public String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);

            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    public String getUri() {
        return uri;
    }
}
