import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Temp {
    public static void main(String[] args) throws IOException {
        File f = new File("\\\\DE0102CPOS20001\\c$\\gkretail\\pos-full\\log\\pos-debug-102-001.log-20240405000000.zip");
        try {
            URL url = f.toURI().toURL();
            URLConnection con = url.openConnection();
            String userpass = "pos-install" + ":" + "M6kUVm3T";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
            con.setRequestProperty("Authorization", basicAuth);
            InputStream in = con.getInputStream();
            byte[] buffer = in.readAllBytes();
            System.out.println(new String(buffer, StandardCharsets.UTF_8));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}