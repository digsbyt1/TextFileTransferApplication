
import  java.io.*;
import java.net.*;

public class test {
    public static void main(String[] args) throws Exception {

        URL url = new URL("http://10.72.17.144:8080/index.html");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
 //       conn.setDoOutput(true);

   //     String query = "SCSU";
     //   String formEl = "type=web&query=" + URLEncoder.encode(query,"UTF-8");

       // DataOutputStream out =
         // new DataOutputStream(conn.getOutputStream());
       // out.writeBytes(formEl);
       // out.close();
	System.out.println(url.getContent());
		
        BufferedReader in = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));

        String ln= "" ;

        while ((ln= in.readLine()) != null) {
            System.out.println(ln);
        }
        in.close();
    }
}
