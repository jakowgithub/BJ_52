package oblik.in.ua.puzzle15;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {

    private byte xEmpty, yEmpty;
    private int pole[][] = new int [4][4];
    TextView[][] poleVidime = new TextView[4][4];
    Button buttonNew, buttonSave;
    final int colirFishki = 0xFF2196F3;
    private Map <String, String> kartkaRN = new HashMap<>();
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 4; j++) {

                final byte k = i;

                final byte m = j;

                String gameFieldId = "textView" + i + "" + j;

                int curID = getResources().getIdentifier(gameFieldId, "id", getPackageName());

                poleVidime[i][j] = findViewById(curID);

                poleVidime[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { MainActivity.this.onClickPoleVidime(k, m); }});
            } }

        buttonNew = findViewById(R.id.buttonNew);
        buttonSave = findViewById(R.id.buttonSave);

        iniVse();

        kartkaRN = readFile();

        if( null!=kartkaRN && !kartkaRN.isEmpty() ) {

               for (String Imya : kartkaRN.keySet()) {

                   int a = Integer.parseInt(Imya.trim());

                   String[] data = Objects.requireNonNull(kartkaRN.get(Imya)).split(" ");

                   for (int b = 0; b < data.length; b++) {

                       pole[a][b] = Integer.parseInt(data[b].trim());
                   }
               }
            drukPolya (pole);
        }
    }

    private byte random_0_3 () { return  (byte) (Math.random()*3); }
    public void setxEmpty(byte xEmpty) { this.xEmpty = xEmpty; }
    public void setyEmpty(byte yEmpty) { this.yEmpty = yEmpty; }

    private void drukPolya (int pole [] []){

        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 4; j++) {

                if (pole[i][j] != 0){

                    String number = String.valueOf(pole[i][j]);

                    poleVidime[i][j].setText(number);
                }
                if (0 == pole[i][j]) poleVidime[i][j].setText("");
            } }
        searchEmpty ();
    }

    private void iniVse(){

        boolean fromFirst = false;

        for (byte i = 0; i < 4; i++) {
            for (byte j = 0; j < 4; j++) {
                pole[i][j] = 0;
                poleVidime[i][j].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                poleVidime[i][j].setTextColor(colirFishki);
                poleVidime[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
                poleVidime[i][j].setText("");
            } }
        for (byte n = 1; n < 16; n++) {

            byte a = random_0_3();
            byte b = random_0_3();

            if (0 == pole[a][b])  pole[a][b] = n;

            else {
                if (fromFirst){

                    for (byte i = 0; i < 4; i++) {

                        if (!fromFirst) break;

                        for (byte j = 0; j < 4; j++) {

                            if (pole[i][j] == 0){

                                pole[i][j] = n;
                                fromFirst=false;
                                break;
                            } } } }
                else {
                    for (byte i = 3; i >= 0; i--) {

                        if (fromFirst) break;

                        for (byte j = 3; j >= 0; j--) {

                            if (pole[i][j] == 0) {

                                pole[i][j]=n;
                                fromFirst=true;
                                break;
                            } } } }
            }}

            drukPolya (pole);
    }

    private void searchEmpty (){
        //znaxodit koordinati pustogo elementa xEmpty, yEmpty
        for (byte i = 0; i < 4; i++) {

            for (byte j = 0; j < 4; j++) {

                if (0 == pole[i][j]){

                    setxEmpty(i);
                    setyEmpty(j);
                    break;
                }
            } }
    }

    private void  onClickPoleVidime (byte i, byte j){

       if (xEmpty==i)

           if ((j-1 >= 0) || (j+1 <= 3))

               if ((yEmpty == j-1) || (yEmpty == j+1))

                   move(i,j);

       if (yEmpty==j)

           if ((i-1 >= 0) || (i+1 <= 3))

               if ((xEmpty == i-1) || (xEmpty == i+1))

                     move(i,j);

           if (5 == pereverkaKinezGri()) {

               sound = MediaPlayer.create(this,R.raw.wjcmh);
               sound.start();
        }
    }

    private void move (byte x, byte y){

        pole [xEmpty][yEmpty] = pole [x] [y];
        pole [x] [y] = 0;

        drukPolya(pole);
    }

    public void  onClickNewGame (View view){
        iniVse();
    }

    private Map <String, String> readFile(){

        //kIP - kartka im`ya-number
        Map <String, String> kIP=new HashMap<>();

        try {
            FileInputStream fileInputStream = openFileInput("puzzle15.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine())!=null) {
                //obrizau probil z pochatku ta kinzya
                String r2 = line.trim();

                 if (!r2.isEmpty()) {

                    //vidrizav im`ya ta pribirau probilj
                    String imya = r2.substring(0, r2.indexOf(" ")).trim();

                    //vidrizav point ta pribirau probilj
                    String point = r2.substring(r2.indexOf(" ") + 1, r2.length()).trim();

                    kIP.put(imya, point);
                }}
            fileInputStream.close();
            bufferedReader.close();

        } catch (IOException e) { e.printStackTrace(); }

        return kIP;
    }

    private void saveFile(Map <String, String> kIP){

        if (kIP!=null && !kIP.isEmpty()) {

            try {
                FileOutputStream fileOutputStream = openFileOutput("puzzle15.txt", MODE_PRIVATE);

                for (String Imya : kIP.keySet()) {
                    String txt = Imya + " " + kIP.get(Imya);
                    fileOutputStream.write(txt.getBytes());
                    fileOutputStream.write(System.lineSeparator().getBytes());
                }
                fileOutputStream.close();

            } catch (IOException e)   { e.printStackTrace(); }
        } }

    public void  onClickSave (View view){

        for (byte i = 0; i < 4; i++) {

            String nomerRyadka = String.valueOf(i);
            StringBuilder znachennyaRydka = new StringBuilder();

            for (byte j = 0; j < 4; j++) {
                znachennyaRydka.append(pole[i][j]).append(" ");
            }
        kartkaRN.put(nomerRyadka, znachennyaRydka.toString());
        }
        saveFile(kartkaRN);
    }

private int pereverkaKinezGri(){

        for (byte i = 0; i < 4; i++) {

            for (byte j = 0; j < 4; j++) {

                if   ((3==i)&&(3==j)) {

                    if (0!= pole [3] [3])
                        return 0;
                }
                else {
                    if ((4*i+j+1) != pole [i] [j])
                        return 0;
                }
            } }
      return 5;
    }
}
