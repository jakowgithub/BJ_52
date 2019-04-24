package oblik.in.ua.bkj36;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int userBall = 0, computerBall=0,
                       counter=0 ;
    final String str1 = "Computer winn !";
    final String str2 = "User winn !";
    final String str3 = "Balance !";

    TextView[] userCard = new TextView[8];
    TextView[] computerCard = new TextView[8];
    TextView compResult, userResult, textViewInfo;
    Button buttonNext, buttonStop;

    private static ArrayList<Card> cardsUser = new ArrayList<>(8);
    private static ArrayList <Card> cardsCopmputer = new ArrayList<>(8);
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
        userResult = findViewById(R.id.userresult);
        textViewInfo = findViewById(R.id.textViewInfo);
        buttonNext = findViewById(R.id.buttonNext);
        buttonStop = findViewById(R.id.buttonStop);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(0xff43A047);

        deck = new Deck();

        for (int i=0; i<2; i++){
            this.showCardUser(i, deck.getShuffleDeck().getFirst().toString());
            userBall = deck.issuanceCard(userBall, cardsUser);
            userResult.setText(String.valueOf(userBall));
            counter++;
        }
        buttonNext.setOnClickListener((v) -> clickNext(deck));
        buttonStop.setOnClickListener((v) -> clickStop(deck));
        String str = "Deck contains 36 cards.";
        textViewInfo.setText(str);

        if (userBall>21){
            textViewInfo.setText(str1);
            textViewInfo.setTextColor(Color.RED);
            this.hideButton();
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
        if (computerBall<15) {

            for (int i=2; i<8; i++){

                if (computerBall>14) break;

                String cardStr = deck.getShuffleDeck().getFirst().toString();
                this.showCardComputer (counter,  cardStr);
                computerBall = deck.issuanceCard(computerBall, cardsCopmputer);
                compResult.setText(String.valueOf(computerBall));
                counter++;
            }
        }
        this.hideButton();
        textViewInfo.setTextColor(Color.RED);

        if (userBall > 21)                textViewInfo.setText(str1);
        else if (computerBall > 21)       textViewInfo.setText(str2);
        else if (computerBall > userBall) textViewInfo.setText(str1);
        else if (userBall > computerBall) textViewInfo.setText(str2);
        else                              textViewInfo.setText(str3);
    }
}
//"\u2646" - trident