package oblik.in.ua.bj52_1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Deck {
    private HashSet<Card> initialDeck = new HashSet<>(36);

    private LinkedList<Card> shuffleDeck ;

    public Deck() {

        for (int i=0; i<13; i++){

            String name;
            int value;

            switch (i){
                case 0: name = "6";  value=6; break;
                case 1: name = "7";  value=7; break;
                case 2: name = "8";  value=8; break;
                case 3: name = "9";  value=9; break;
                case 4: name = "10"; value=10;break;
                case 5: name = "J";  value=2; break;
                case 6: name = "D";  value=3; break;
                case 7: name = "K";  value=4; break;
                case 8: name = "A";  value=11;break;
                case 9: name = "2";  value=2; break;
                case 10: name ="3";  value=3; break;
                case 11: name ="4";  value=4; break;
                case 12: name ="5";  value=5;break;

                default: name = "errorName"; value=0; break;
            }
            for (int j=0; j<4; j++){

                String suit;

                switch (j){
                    case 0: suit = "\u2660";    break; //spade "\u2660"
                    case 1: suit = "\u2724";    break; //club "\u2725" "\u2724" "\u2720" "\u2663"
                    case 2: suit = "\u2666";    break; //diamond  "\u2bc1", U+20DF, 2666
                    case 3: suit = "\u2764";    break; //heart   "\u2764"

                    default: suit = "errorSuit"; break;
                }
                Card card = new Card(name, suit, value);
                initialDeck.add(card);
            } }

        shuffleDeck = new LinkedList<>(initialDeck);

        int w = (int) (Math.random() * 1000);
        for (int i=0; i < w; i++){
            shuffleDeck.add((int) (Math.random() * 34), shuffleDeck.pollLast());
        }
    }

    public LinkedList<Card> getShuffleDeck() { return shuffleDeck; }

    //issuance with user/computer deck created
    public int issuanceCard (int whosePoints, ArrayList<Card> whoseCards){
        int result;
        result = whosePoints + this.getShuffleDeck().getFirst().getValueCard();
        whoseCards.add(this.getShuffleDeck().pollFirst());
        return result;
    }
}
//"\u2646" - trident