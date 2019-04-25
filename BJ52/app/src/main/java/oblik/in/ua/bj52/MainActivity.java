package oblik.in.ua.bj52;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static int userBall = 0, computerBall=0,
                       counter=0, totalUser=0, totalComp=0;
    final String str1 = "Computer winn !";
    final String str2 = "User winn !";
    final String str3 = "Balance !";
    final String str4 = "totalComp";
    final String str5 = "totalUser";

    TextView[] userCard = new TextView[8];
    TextView[] computerCard = new TextView[8];
    TextView compResult, compTotal,
             userResult, userTotal,
             textViewInfo;
    Button buttonNext, buttonStop;

    private static ArrayList<Card> cardsUser = new ArrayList<>(8);
    private static ArrayList <Card> cardsCopmputer = new ArrayList<>(8);
    private static Map <String, String> resultTotal;
    private static Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (byte i = 0; i < 8; i++) {

            String userCarddId = "userView" + i ;
            String computerCarddId = "compView" + i ;

            int curUserCardID = getResources().getIdentifier(userCarddId, "id", getPackageName());
            int curComputerCardID = getResources().getIdentifier(computerCarddId, "id", getPackageName());

            userCard [i] = findViewById(curUserCardID);
            computerCard [i] = findViewById(curComputerCardID);

            userCard [i].setVisibility(View.INVISIBLE);
            computerCard [i].setVisibility(View.INVISIBLE);
        }
        compResult = findViewById(R.id.compResult);
        compTotal = findViewById(R.id.compResult2);
        userResult = findViewById(R.id.userResult);
        userTotal = findViewById(R.id.userResult2);
        textViewInfo = findViewById(R.id.textViewInfo);
        buttonNext = findViewById(R.id.buttonNext);
        buttonStop = findViewById(R.id.buttonStop);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(0xff43A047);

        resultTotal = readFile();

        if (null!=resultTotal.get(str4) &&
            null!=resultTotal.get(str5)) {

            totalComp = Integer.parseInt(resultTotal.get(str4).trim());
            totalUser = Integer.parseInt(resultTotal.get(str5).trim());
        }
        compTotal.setText(totalComp);
        userTotal.setText(totalUser);

        deck = new Deck();

        for (int i=0; i<2; i++){
            this.showCardUser(i, deck.getShuffleDeck().getFirst().toString());
            userBall = deck.issuanceCard(userBall, cardsUser);
            userResult.setText(String.valueOf(userBall));
            counter++;
        }
        buttonNext.setOnClickListener((v) -> clickNext(deck));
        buttonStop.setOnClickListener((v) -> clickStop(deck));
        String str = "Deck contains 52 cards.";
        textViewInfo.setText(str);

        if (userBall>21){
            textViewInfo.setText(str1);
            textViewInfo.setTextColor(Color.RED);
            this.hideButton();
            totalComp++;
            compTotal.setText(totalComp);
            this.saveFile(totalComp, totalUser);

        }
    }
    public void showCardUser(int i, String text){
        if (i>=0 && i<8 && text!=null){
            this.userCard[i].setVisibility(View.VISIBLE);
            this.userCard[i].setText(text);
        }}
    public void showCardComputer(int i, String text){
        if (i>=0 && i<8 && text!=null){
            this.computerCard [i].setVisibility(View.VISIBLE);
            this.computerCard [i].setText(text);
        }}
    public void hideButton(){
        buttonNext.setClickable(false);
        buttonStop.setClickable(false);
    }
    public  void clickNext(Deck deck){

        String cardStr = deck.getShuffleDeck().getFirst().toString();
        this.showCardUser (counter,  cardStr);
        userBall = deck.issuanceCard(userBall, cardsUser);
        userResult.setText(String.valueOf(userBall));
        counter++;

        if (userBall>21){
            textViewInfo.setText(str1);
            textViewInfo.setTextColor(Color.RED);
            this.hideButton();
            totalComp++;
            compTotal.setText(totalComp);
            this.saveFile(totalComp, totalUser);
        }

    }
    public  void clickStop(Deck deck) {

        counter=0;

        for (int i=0; i<2; i++){
            String cardStr = deck.getShuffleDeck().getFirst().toString();
            this.showCardComputer (counter,  cardStr);
            computerBall = deck.issuanceCard(computerBall, cardsCopmputer);
            compResult.setText(String.valueOf(computerBall));
            counter++;
        }
        if (computerBall<17) {

            for (int i=2; i<8; i++){

                if (computerBall>16) break;

                String cardStr = deck.getShuffleDeck().getFirst().toString();
                this.showCardComputer (counter,  cardStr);
                computerBall = deck.issuanceCard(computerBall, cardsCopmputer);
                compResult.setText(String.valueOf(computerBall));
                counter++;
            }
        }
        this.hideButton();
        textViewInfo.setTextColor(Color.RED);

        if (userBall > 21)              {
            textViewInfo.setText(str1);
            totalComp++;
            compTotal.setText(totalComp);
        }
        else if (computerBall > 21){
            textViewInfo.setText(str2);
            totalUser++;
            userTotal.setText(totalUser);
        }
        else if (computerBall > userBall){
            textViewInfo.setText(str1);
            totalComp++;
            compTotal.setText(totalComp);
        }
        else if (userBall > computerBall) {
            textViewInfo.setText(str2);
            totalUser++;
            userTotal.setText(totalUser);
        }
        else {
            textViewInfo.setText(str3);
            totalComp++;
            compTotal.setText(totalComp);
            totalUser++;
            userTotal.setText(totalUser);
        }

        this.saveFile(totalComp, totalUser);
    }

    public Map<String, String> readFile(){

        Map <String, String> compUserBoll = new HashMap<>();

        try {
            FileInputStream fileInputStream = openFileInput("boll.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine())!=null) {

                String r2 = line.trim();

                if (!r2.isEmpty()) {

                    String imya = r2.substring(0, r2.indexOf(" ")).trim();

                    String point = r2.substring(r2.indexOf(" ") + 1).trim();

                    compUserBoll.put(imya, point);
                }}
            fileInputStream.close();
            bufferedReader.close();

        } catch (IOException ioe) {
            resultTotal = new HashMap<>();
            resultTotal.put("totalComp","0");
            resultTotal.put("totalUser","0");
            ioe.printStackTrace();
        }

        return compUserBoll;
    }

    public void saveFile(int totalComp, int totalUser){
        String tComp = String.valueOf(totalComp);
        String tUser = String.valueOf(totalUser);
        Map<String, String> kIP = new HashMap<>();
        kIP.put(str4, tComp);
        kIP.put(str5, tUser);

        if (kIP!=null && !kIP.isEmpty()) {

            try {
                FileOutputStream fileOutputStream = openFileOutput("boll.txt", MODE_PRIVATE);

                for (String Imya : kIP.keySet()) {
                    String txt = Imya + " " + kIP.get(Imya);
                    fileOutputStream.write(txt.getBytes());
                    fileOutputStream.write(System.lineSeparator().getBytes());
                }
                fileOutputStream.close();

            } catch (IOException e)   { e.printStackTrace(); }

        } }
}
//"\u2646" - trident