package oblik.in.ua.gt36;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ReadWrite {

    private int totalUser=0, totalComp=0;
    private Map<String, String> resultTotal = new HashMap<>();
    final String str4 = "totalComp";
    final String str5 = "totalUser";

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
            kIP.put(str4, "0");
            kIP.put(str5, "0");
            return kIP;
        }
        return kIP;
    }

    public void saveFile (Map <String, String> kIP, Context context){

        if (kIP!=null && !kIP.isEmpty() &&
                kIP.get(str4)!=null && kIP.get(str5)!=null) {

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
public  Map<String, String> readResultTotal (Context context){
    try {
        resultTotal.putAll(readFile(context));
    } catch (Exception exp) {
        resultTotal.put(str4, "0");
        resultTotal.put(str5, "0");
    }

    if (resultTotal.get(str4) != null && resultTotal.get(str5) != null) {

        totalComp = Integer.parseInt(Objects.requireNonNull(resultTotal.get(str4)));
        totalUser = Integer.parseInt(Objects.requireNonNull(resultTotal.get(str5)));
    }
    return resultTotal;
    }

    public int getTotalUser() { return totalUser; }
    public void setTotalUser(int totalUser) { this.totalUser = totalUser; }

    public int getTotalComp() { return totalComp; }
    public void setTotalComp(int totalComp) { this.totalComp = totalComp; }

    public Map<String, String> getResultTotal() { return resultTotal; }
    public void setResultTotal(Map<String, String> resultTotal) { this.resultTotal = resultTotal; }
}
