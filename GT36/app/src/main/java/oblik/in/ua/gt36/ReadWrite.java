package oblik.in.ua.gt36;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ReadWrite {

    ReadWrite (){}

    public Map<String, String> readFile(Context context){
        Map <String, String> kIP=new HashMap<>();
        try {
            FileInputStream fileInputStream = context.openFileInput("gt36.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine())!=null) {

                String r2 = line.trim();

                if (!r2.isEmpty()) {

                    String imya = r2.substring(0, r2.indexOf(" ")).trim();

                    String point = r2.substring(r2.indexOf(" ") + 1).trim();

                    kIP.put(imya, point);
                }}
            fileInputStream.close();
            bufferedReader.close();

        } catch (Exception e) {
            kIP.put("totalComp", "0");
            kIP.put("totalUser", "0");
            return kIP;
        }
        return kIP;
    }

    public void saveFile (Map <String, String> kIP, Context context){

        if (kIP!=null && !kIP.isEmpty() &&
                kIP.get("totalComp")!=null && kIP.get("totalUser")!=null) {

            try {
                FileOutputStream fileOutputStream = context.openFileOutput("gt36.txt", MODE_PRIVATE);

                for (String Imya : kIP.keySet()) {
                    String txt = Imya + " " + kIP.get(Imya);
                    fileOutputStream.write(txt.getBytes());
                    fileOutputStream.write(System.lineSeparator().getBytes());
                }
                fileOutputStream.close();

            } catch (IOException ioe)   { ioe.printStackTrace(); }
        } }

}
