package chapter2;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor1 {

    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/")+1);

        URLClassLoader loader = null;

        try {
            URL[] urls = new URL[1];
            URLStreamHandler urlStreamHandler = null;
            File classPath = new File(Constants.WEBROOT);

            String repostiry = (new URL("file", null, classPath.getCanonicalPath()+File.separator)).toString();
            System.out.println(classPath.getCanonicalPath());
            System.out.println(repostiry);
            urls[0] = new URL(null, repostiry, urlStreamHandler);
            loader = new URLClassLoader(urls);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class<?> myClass = null;

        try {
            System.out.println(servletName);
            myClass = loader.loadClass(servletName);
            System.out.println(myClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;

        try {
            servlet = (Servlet) myClass.newInstance();
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet.service((ServletRequest) requestFacade, (ServletResponse) responseFacade);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
