package oblik.in.ua.gt36;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private static int userBall = 0, computerBall=0,
                       counter=0, totalUser=0, totalComp=0,
                       counterTrumpUser=0,
                       counterTrumpComputer=0;
    private static boolean [] choiceFlag = new boolean[4];
    private static boolean userTurnFlag = true, takeComputerFlag= false;
    final String str1 = "Computer winn !";
    final String str2 = "User winn !";
    final String str3 = "Balance !";
    final String str4 = "totalComp";
    final String str5 = "totalUser";
    final String gameOver = "GAME OVER.";
    final String tryAgain = "TRY AGAIN !";

    Button[] userCard = new Button[4];
    Button[] computerCard = new Button[4];
    TextView compResult, compTotal, userResult, userTotal, textViewInfo;
    Button buttonNext, buttonStop;

    private static ArrayList<Card> cardsUserCurrent = new ArrayList<>(4);
    private static ArrayList <Card> cardsCopmputerCurrent = new ArrayList<>(4);
    private static ArrayList<Card> cardsChoiceUser = new ArrayList<>(4);

    private static List<Card> sortedCardsChoiceUser = new ArrayList<>(4);
    private static List<Card> sortedCardsUserCurrent = new ArrayList<>(4);
    private static List <Card> sortedCardsCopmputerCurrent = new ArrayList<>(4);

    private static ArrayList <Card> cardsTakeUser = new ArrayList<>(4);
    private static ArrayList <Card> cardsTakeComputer = new ArrayList<>(4);
    private static ArrayList <Card> cardsGiveBackUser = new ArrayList<>(4);
    private static ArrayList <Card> cardsGiveBackComputer = new ArrayList<>(4);

    private static ArrayList<Card> cardsUserTakeAll = new ArrayList<>(36);
    private static ArrayList<Card> cardsComputerTakeAll = new ArrayList<>(36);
    private static Map<String, String> resultTotal = new HashMap<>();
    private static Deck deck;
    private static Card cardTrump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller controller = new Controller();
        ReadWrite readWrite = new ReadWrite();

        for (byte i = 0; i < 4; i++) {

            final int k=i;

            choiceFlag[i] = true;

            String userCarddId = "btnUser" + i ;
            String computerCarddId = "btnComp" + i ;

            int curUserCardID = getResources().getIdentifier(userCarddId, "id", getPackageName());
            int curComputerCardID = getResources().getIdentifier(computerCarddId, "id", getPackageName());

            userCard [i] = findViewById(curUserCardID);
            computerCard [i] = findViewById(curComputerCardID);
            userCard [i].setOnClickListener((v) -> clickUserCard (k, deck));
            computerCard [i].setOnClickListener((v) -> clickComputerCard (k, deck));
        }

        compResult = findViewById(R.id.compResult);
        compTotal = findViewById(R.id.compResult2);
        userResult = findViewById(R.id.userResult);
        userTotal = findViewById(R.id.userResult2);
        textViewInfo = findViewById(R.id.textViewInfo);

        buttonNext = findViewById(R.id.buttonNext);
        buttonStop = findViewById(R.id.buttonStop);
        buttonNext.setOnClickListener((v) -> clickNext(deck));
//        buttonStop.setOnClickListener((v) -> clickStop(deck));
        View viewMain = this.getWindow().getDecorView();
        viewMain.setBackgroundColor(0xff43A047);

        try {
            resultTotal.putAll(readWrite.readFile(getApplicationContext()));
        } catch (Exception exp) {
            resultTotal.put(str4, "0");
            resultTotal.put(str5, "0");
        }

        if (resultTotal.get(str4) != null &&
            resultTotal.get(str5) != null) {

            totalComp = Integer.parseInt(Objects.requireNonNull(resultTotal.get(str4)));
            totalUser = Integer.parseInt(Objects.requireNonNull(resultTotal.get(str5)));
        }

        compTotal.setText(Objects.requireNonNull(resultTotal.get(str4)));
        userTotal.setText(Objects.requireNonNull(resultTotal.get(str5)));

        deck = new Deck();

        // issuing cards
        for (byte i = 0; i < 4; i++) {
            cardsUserCurrent.add(deck.getShuffleDeck().pollFirst());
            this.showCardUser(i, cardsUserCurrent.get(i).toString());
            cardsCopmputerCurrent.add(deck.getShuffleDeck().pollFirst());
            this.showCardComputer(i, cardsCopmputerCurrent.get(i).toString());
        }
        //showTramp
        cardTrump = deck.getShuffleDeck().pollFirst();
        buttonStop.setText( cardTrump.toString());
        //increase valueCard = +30, if it is trump
        for (byte i = 0; i < 4; i++) {
            if (cardsUserCurrent.get(i).getSuitCard().equals(cardTrump.getSuitCard()))
                cardsUserCurrent.get(i).setValueCard(cardsUserCurrent.get(i).getValueCard()+30);

            if (cardsCopmputerCurrent.get(i).getSuitCard().equals(cardTrump.getSuitCard()))
                cardsCopmputerCurrent.get(i).setValueCard(cardsCopmputerCurrent.get(i).getValueCard()+30);
        }
        deck.getShuffleDeck().forEach(card -> {
            if (card.getSuitCard().equals(cardTrump.getSuitCard()))
                card.setValueCard(card.getValueCard()+30);
        });
    }
    public void clickUserCard (int k, Deck deck){

        if (choiceFlag[k]){

            this.userCard[k].setBackgroundResource(R.drawable.border2);
            cardsChoiceUser.add(cardsUserCurrent.get(k));
            textViewInfo.setText(cardsUserCurrent.get(k).toString());
            choiceFlag [k]= false;
        }
        else {
            this.userCard[k].setBackgroundResource(R.drawable.border);
            cardsChoiceUser.remove(cardsUserCurrent.get(k));
            textViewInfo.setText("remove" + cardsUserCurrent.get(k).toString());
            choiceFlag [k]= true;
        }
    }
    public void clickComputerCard (int k, Deck deck){
        //this.computerCard [k].setBackgroundColor(Color.parseColor("#90a4ae"));
        this.computerCard [k].setBackgroundResource(R.drawable.border2);
        textViewInfo.setText(cardsCopmputerCurrent.get(k).toString());
    }

    public void showCardUser(int i, String text){
        if (i>=0 && i<4 && text!=null){
            this.userCard[i].setText(text);
        }}
    public void showCardComputer(int i, String text){
        if (i>=0 && i<4 && text!=null){
            this.computerCard [i].setText(text);
        }}

    public  void clickNext(Deck deck){
// check (cardsUserCurrent &&  cardsCopmputerCurrent) is empty, is choice card
        if(!cardsUserCurrent.isEmpty() &&
           !cardsCopmputerCurrent.isEmpty()){

            if(!cardsChoiceUser.isEmpty()){

                if (userTurnFlag){
                    //turn user
                    sortedCardsChoiceUser = cardsChoiceUser
                            .stream()
                            .sorted(Comparator
                                    .comparing(Card::getValueCard)
                                    .reversed())
                            .collect(Collectors.toList());
                    sortedCardsCopmputerCurrent = cardsCopmputerCurrent
                             .stream()
                             .sorted(Comparator.comparing(Card::getValueCard))
                             .collect(Collectors.toList());

                    for (Card cardChoisUser: sortedCardsChoiceUser){

                       for (Card cardCopmputerCurrent: sortedCardsCopmputerCurrent){

                        if ((cardChoisUser.getSuitCard().equals(cardCopmputerCurrent.getSuitCard())
                             && cardChoisUser.getValueCard() < cardCopmputerCurrent.getValueCard())
                                ||
                           (!(cardChoisUser.getSuitCard().equals(cardTrump.getSuitCard()))
                             &&(cardCopmputerCurrent.getSuitCard().equals(cardTrump.getSuitCard())))
                        ){
                            takeComputerFlag = true;
                            userTurnFlag = false;

                            cardsTakeComputer.add(cardCopmputerCurrent);
                            sortedCardsCopmputerCurrent.remove(cardCopmputerCurrent);

                            cardsGiveBackUser.add(cardChoisUser);
                            //cardChoisUser cann`t deleted an sortedCardsChoiceUser
                            // in a loop  for (Card cardChoisUser: sortedCardsChoiceUser) without break

                            break;
                        }
                        else {
                            takeComputerFlag = false;
                            userTurnFlag = true;
                        } }
                       //if computer don`t take cards, calculation give back cards
                        if (!takeComputerFlag){ }
                    }
                }

                else {
              //turn computer
            }
            }
            else { textViewInfo.setText("choose a card");}
        }

       else {
           //end of the game
        }
        for (byte i = 0; i < 4; i++) {
            this.userCard[i].setBackgroundResource(R.drawable.border);
            this.computerCard [i].setBackgroundResource(R.drawable.border);
        }
    }
    public static int getUserBall() { return userBall;}
    public static void setUserBall(int userBall) { MainActivity.userBall = userBall; }

    public static int getComputerBall() { return computerBall; }
    public static void setComputerBall(int computerBall) { MainActivity.computerBall = computerBall; }

    public static int getCounter() { return counter; }
    public static void setCounter(int counter) { MainActivity.counter = counter; }

    public static int getTotalUser() { return totalUser; }
    public static void setTotalUser(int totalUser) { MainActivity.totalUser = totalUser; }

    public static int getTotalComp() { return totalComp; }
    public static void setTotalComp(int totalComp) { MainActivity.totalComp = totalComp; }

    public static int getCounterTrumpUser() { return counterTrumpUser; }
    public static void setCounterTrumpUser(int counterTrumpUser) { MainActivity.counterTrumpUser = counterTrumpUser; }

    public static int getCounterTrumpComputer() { return counterTrumpComputer; }
    public static void setCounterTrumpComputer(int counterTrumpComputer) { MainActivity.counterTrumpComputer = counterTrumpComputer; }

    public static boolean[] getChoiceFlag() { return choiceFlag; }
    public static void setChoiceFlag(boolean[] choiceFlag) { MainActivity.choiceFlag = choiceFlag; }

    public static boolean isUserTurnFlag() { return userTurnFlag; }
    public static void setUserTurnFlag(boolean userTurnFlag) { MainActivity.userTurnFlag = userTurnFlag; }

    public static boolean isTakeComputerFlag() { return takeComputerFlag; }
    public static void setTakeComputerFlag(boolean takeComputerFlag) { MainActivity.takeComputerFlag = takeComputerFlag; }

    public static ArrayList<Card> getCardsUserCurrent() { return cardsUserCurrent; }
    public static void setCardsUserCurrent(ArrayList<Card> cardsUserCurrent) { MainActivity.cardsUserCurrent = cardsUserCurrent; }

    public static ArrayList<Card> getCardsCopmputerCurrent() { return cardsCopmputerCurrent; }
    public static void setCardsCopmputerCurrent(ArrayList<Card> cardsCopmputerCurrent) { MainActivity.cardsCopmputerCurrent = cardsCopmputerCurrent; }

    public static ArrayList<Card> getCardsChoiceUser() { return cardsChoiceUser; }
    public static void setCardsChoiceUser(ArrayList<Card> cardsChoiceUser) { MainActivity.cardsChoiceUser = cardsChoiceUser; }

    public static List<Card> getSortedCardsChoiceUser() { return sortedCardsChoiceUser; }
    public static void setSortedCardsChoiceUser(List<Card> sortedCardsChoiceUser) { MainActivity.sortedCardsChoiceUser = sortedCardsChoiceUser; }

    public static List<Card> getSortedCardsUserCurrent() { return sortedCardsUserCurrent; }
    public static void setSortedCardsUserCurrent(List<Card> sortedCardsUserCurrent) { MainActivity.sortedCardsUserCurrent = sortedCardsUserCurrent; }

    public static List<Card> getSortedCardsCopmputerCurrent() { return sortedCardsCopmputerCurrent; }
    public static void setSortedCardsCopmputerCurrent(List<Card> sortedCardsCopmputerCurrent) { MainActivity.sortedCardsCopmputerCurrent = sortedCardsCopmputerCurrent; }

    public static ArrayList<Card> getCardsTakeUser() { return cardsTakeUser; }
    public static void setCardsTakeUser(ArrayList<Card> cardsTakeUser) { MainActivity.cardsTakeUser = cardsTakeUser; }

    public static ArrayList<Card> getCardsTakeComputer() { return cardsTakeComputer; }
    public static void setCardsTakeComputer(ArrayList<Card> cardsTakeComputer) { MainActivity.cardsTakeComputer = cardsTakeComputer; }

    public static ArrayList<Card> getCardsGiveBackUser() { return cardsGiveBackUser; }
    public static void setCardsGiveBackUser(ArrayList<Card> cardsGiveBackUser) { MainActivity.cardsGiveBackUser = cardsGiveBackUser; }

    public static ArrayList<Card> getCardsGiveBackComputer() { return cardsGiveBackComputer; }
    public static void setCardsGiveBackComputer(ArrayList<Card> cardsGiveBackComputer) { MainActivity.cardsGiveBackComputer = cardsGiveBackComputer; }

    public static ArrayList<Card> getCardsUserTakeAll() { return cardsUserTakeAll; }
    public static void setCardsUserTakeAll(ArrayList<Card> cardsUserTakeAll) { MainActivity.cardsUserTakeAll = cardsUserTakeAll; }

    public static ArrayList<Card> getCardsComputerTakeAll() { return cardsComputerTakeAll; }
    public static void setCardsComputerTakeAll(ArrayList<Card> cardsComputerTakeAll) { MainActivity.cardsComputerTakeAll = cardsComputerTakeAll; }

    public static Map<String, String> getResultTotal() { return resultTotal; }
    public static void setResultTotal(Map<String, String> resultTotal) { MainActivity.resultTotal = resultTotal; }

    public static Deck getDeck() { return deck; }
    public static void setDeck(Deck deck) { MainActivity.deck = deck; }

    public static Card getCardTrump() { return cardTrump; }
    public static void setCardTrump(Card cardTrump) { MainActivity.cardTrump = cardTrump; }

}
