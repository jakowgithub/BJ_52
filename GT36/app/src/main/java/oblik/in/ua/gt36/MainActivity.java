package oblik.in.ua.gt36;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
    final String gameOver = "GAME OVER !";

    TextView[] userCard = new TextView[8];
    TextView[] computerCard = new TextView[8];
    TextView compResult, compTotal,
            userResult, userTotal,
            textViewInfo;
    Button buttonNext, buttonStop;

    private static ArrayList<Card> cardsUser = new ArrayList<>(9);
    private static ArrayList <Card> cardsCopmputer = new ArrayList<>(9);
    private static Map<String, String> resultTotal = new HashMap<>();
    private static Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
