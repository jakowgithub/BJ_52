package oblik.in.ua.logika;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;


public class MainActivity extends AppCompatActivity {

    private static byte figura[][][][] = new byte [8][8][5][5];
    private static int pole[][] = new int [8][8];
    private static int povorot[] = new int [8];
    private static byte praporFiguri[] = new byte [8];
    private static int  porydokVstanovlennyaFigur[] = new int [8]; //nomeri figur
    private static int nomerVstavki;// vid 0 do 7

    TextView[][] gameFields = new TextView[8][8];
    ImageView [] figuraImageView = new ImageView [8];
    private MediaPlayer sound ;

    Button buttonDell, buttonLeft, buttonRight, buttonUp, buttonDown, buttonFix;
    private int colirFiguri, curNomerFiguri, curPovorot,colirPolya, curX, curY, praporKinezGri;
    final int colirFiguri1 = 0xFFFFEA00;
    final int colirFiguri2 = 0xFF64DD17;
    final int colirFiguri3 = 0xFFFF8F00;
    final int colirFiguri4 = 0xFF90A4AE;
    final int colirFiguri5 = 0xFFF44336;
    final int colirFiguri6 = 0xFF2196F3;
    final int colirFiguri7 = 0xFFC2185B;
    final int colirFiguri8 = 0xFFE040FB;
    final int colirPolya0  = 0xFFFFF59D;
    final int colirTextPeremoga = 0xFFD50000;
    final int colirTextError = 0xFFD50000;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713 //fact: ca-app-pub-9345510148121248~6804179052
        MobileAds.initialize(this, "ca-app-pub-9345510148121248~6804179052");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                String gameFieldId;

                gameFieldId = "textView" + i + "" + j;

                int curID = getResources().getIdentifier(gameFieldId, "id", getPackageName());

                gameFields[i][j] = findViewById(curID);
            }
        }
        iniVse();

        for (int i = 0; i < 8; i++) {

            final int k = i;

            String figuraId = "imageView"+i;

            int curID = getResources().getIdentifier(figuraId, "id", getPackageName());

            figuraImageView [i] = findViewById(curID);

            figuraImageView [i].setOnClickListener((v)-> onClickFigura(k, povorot[k]));

            figuraImageView[i].setOnLongClickListener((v) -> fixFigura(k, curPovorot, curX, curY));

            mAdView = findViewById(R.id.adView);
        }
    }

    public void iniVse (){
        pole=poleIni();
        figura=figuriIni ();
        curX=3;
        curY=3;
        curPovorot=0;
        nomerVstavki=0;

        for (int d = 0; d < 8; d++) {
            gameFields[4][d].setText("");
            gameFields[5][d].setText("");
            povorot[d]=0;
            praporFiguri[d] = 0;
            porydokVstanovlennyaFigur[d] = 0;
        }
    }

    private int [][] poleIni() {
        int pole[][] = new int [8][8];
        for (int c = 0; c < 8; c++) {
            for (int d = 0; d < 8; d++) {
                pole[c][d]=0;
            }}
                return pole;
            }

    //figuri:0=T1,1=L1,2=T2,3=kvadrat,4=L2,5=herevernuta P, 6=S, 7=pryamokutnnik.
//povorot:0=0,1=90,2=180, 3=270.
    private static byte [][][][] figuriIni (){
        byte figura [][][][]=new byte [8][8][5][5];
        for (byte a=0; a<8; a++){
            for (byte b=0; b<8; b++){
                for (byte c=0; c<5; c++){
                    for (byte d=0; d<5; d++){
//T1-figura - simetrichna
                        if (0==a ) {//povorot 0
                            if ((0==b) || (4==b)) {
                                if ((0==c && 0==d)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==1)||
                                    (c==3 && d==1))
                                    figura [a][b][c][d]=1;
                            }  //povorot+90
                            if ((b==1)|| (b==5)) {
                                if ((c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==2 && d==2)||
                                    (c==2 && d==3))
                                    figura [a][b][c][d]=1;
                            }//povorot+180
                            if ((b==2) || (b==6)) {
                                if ((c==0 && d==1)||
                                    (c==1 && d==1)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1) ||
                                    (c==2 && d==2)||
                                    (c==3 && d==0)||
                                    (c==3 && d==1)||
                                    (c==3 && d==2))
                                    figura [a][b][c][d]=1;
                            }//povorot+270
                            if ((b==3)|| (b==7)) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                        }
//L1-figura ne symetrichna
                        if (a==1 ) {//povorot 0
                            if (b==0) {
                                if ((c==0 && d==0)||
                                    (c==1 && d==0)||
                                    (c==2 && d==0)||
                                    (c==3 && d==0)||
                                    (c==4 && d==0)||
                                    (c==2 && d==1)||
                                    (c==3 && d==1)||
                                    (c==4 && d==1))
                                    figura [a][b][c][d]=1;
                            }  //povorot+90
                            if (b==1) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==0 && d==4)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2))
                                    figura [a][b][c][d]=1;
                            }   //povorot+180
                            if (b==2) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==3 && d==1)||
                                    (c==4 && d==1))
                                    figura [a][b][c][d]=1;
                            }//povorot+270
                            if (b==3) {
                                if ((c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==1 && d==4)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==0 && d==4))
                                    figura [a][b][c][d]=1;
                            }// dzerkalna figura L1
                            if (b==4) {
                                if ((c==0 && d==1)||
                                    (c==1 && d==1)||
                                    (c==2 && d==1)||
                                    (c==3 && d==1)||
                                    (c==4 && d==1)||
                                    (c==2 && d==0)||
                                    (c==3 && d==0)||
                                    (c==4 && d==0))
                                    figura [a][b][c][d]=1;
                            }// dzerkalna figura L1 +90
                            if (b==5) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==1 && d==4))
                                    figura [a][b][c][d]=1;
                            }// dzerkalna figura L1 +180
                            if (b==6) {
                                if ((c==0 && d==0)||
                                    (c==1 && d==0)||
                                    (c==2 && d==0)||
                                    (c==3 && d==0)||
                                    (c==4 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==1)||
                                    (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }//dzerkalna figura L1 +270
                            if (b==7) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==0 && d==4)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==1 && d==4))
                                    figura [a][b][c][d]=1;
                            }

                        }
//T-figura - simetriya
                        if (a==2 ) {//povorot 0
                            if ((b==0)||(b==6)){
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==7)) {//povorot+90
                                if ((c==0 && d==2)||
                                    (c==1 && d==2)||
                                    (c==2 && d==2)||
                                    (c==3 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==2)||(b==4)){//povorot-180
                                if ((c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2)||
                                    (c==2 && d==3)
                                        )
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==3)||(b==5)) {//povorot-270
                                if ((c==0 && d==0)||
                                    (c==1 && d==0)||
                                    (c==2 && d==0)||
                                    (c==3 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                        }
//kvadrat-figura
                        if (a==3) {//povorot 0
                            if ((b==0)||(b==4)) {
                                if ((c==0 && d==0)||
                                    (c==1 && d==0)||
                                    (c==2 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==1)||
                                    (c==2 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==5)) {//povorot+90
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==2)||(b==6)) {//povorot+180
                                if ((c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==3)||(b==7)) {//povorot+270
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                        }
//L2-figura ne simetriya
                        if (a==4 ) {//povorot 0
                            if (b==0) {
                                if ((c==0 && d==0)||
                                    (c==1 && d==0)||
                                    (c==2 && d==0)||
                                    (c==3 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2)||
                                    (c==3 && d==1)||
                                    (c==3 && d==2))
                                    figura [a][b][c][d]=1;
                            }  //povorot+90
                            if (b==1) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }   //povorot+180
                            if (b==2) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==2)||
                                    (c==3 && d==2))
                                    figura [a][b][c][d]=1;
                            }//povorot+270
                            if (b==3) {
                                if ((c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2)||
                                    (c==2 && d==3))
                                    figura [a][b][c][d]=1;
                            }// dzerkalna figura L2
                            if (b==4) {
                                if ((c==0 && d==2)||
                                    (c==1 && d==2)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2)||
                                    (c==3 && d==0)||
                                    (c==3 && d==1)||
                                    (c==3 && d==2))
                                    figura [a][b][c][d]=1;
                            }// dzerkalna figura L2 +90
                            if (b==5) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2)||
                                    (c==2 && d==3))
                                    figura [a][b][c][d]=1;
                            }// dzerkalna figura L2 +180
                            if (b==6) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==2 && d==0)||
                                    (c==3 && d==0))
                                    figura [a][b][c][d]=1;
                            }//dzerkalna figura L2 +270
                            if (b==7) {
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==2 && d==2)||
                                    (c==2 && d==3))
                                    figura [a][b][c][d]=1;
                            }
                        }
//L_J-figura
                        if (a==5) {//povorot 0/
                            if ((b == 0) ||(b == 6)){
                                if ((c == 0 && d == 0) ||
                                    (c == 1 && d == 0) ||
                                    (c == 2 && d == 0) ||
                                    (c == 0 && d == 3) ||
                                    (c == 1 && d == 3) ||
                                    (c == 2 && d == 3) ||
                                    (c == 2 && d == 1) ||
                                    (c == 2 && d == 2))
                                    figura[a][b][c][d] = 1;
                            }  //povorot+90
                            if ((b == 1)||(b == 7)) {
                                if ((c == 0 && d == 0) ||
                                    (c == 0 && d == 1) ||
                                    (c == 0 && d == 2) ||
                                    (c == 1 && d == 0) ||
                                    (c == 2 && d == 0) ||
                                    (c == 3 && d == 0) ||
                                    (c == 3 && d == 1) ||
                                    (c == 3 && d == 2))
                                    figura[a][b][c][d] = 1;
                            }   //povorot+180
                            if ((b == 2)||(b == 4)) {
                                if ((c == 0 && d == 0) ||
                                    (c == 0 && d == 1) ||
                                    (c == 0 && d == 2) ||
                                    (c == 0 && d == 3) ||
                                    (c == 1 && d == 0) ||
                                    (c == 1 && d == 3) ||
                                    (c == 2 && d == 0) ||
                                    (c == 2 && d == 3))
                                    figura[a][b][c][d] = 1;
                            }//povorot+270
                            if ((b == 3)||(b == 5)) {
                                if ((c == 0 && d == 0) ||
                                    (c == 0 && d == 1) ||
                                    (c == 0 && d == 2) ||
                                    (c == 1 && d == 2) ||
                                    (c == 2 && d == 2) ||
                                    (c == 3 && d == 2) ||
                                    (c == 3 && d == 0) ||
                                    (c == 3 && d == 1))
                                    figura[a][b][c][d] = 1;
                            }
                        }
//S-figura
                        if (a==6) {//povorot 0/180
                            if ((b==0)||(b==2)) {
                                if ((c==0 && d==1) ||
                                    (c==0 && d==2) ||
                                    (c==1 && d==1) ||
                                    (c==1 && d==2) ||
                                    (c==2 && d==0) ||
                                    (c==2 && d==1) ||
                                    (c==3 && d==0) ||
                                    (c==3 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==3)) {//povorot+90/+270
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==2 && d==2)||
                                    (c==2 && d==3))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==4)||(b==6)) {// povorot 0/180
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==2 && d==1)||
                                    (c==2 && d==2)||
                                    (c==3 && d==1)||
                                    (c==3 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==5)||(b==7)) {//povorot+90/+270
                                if ((c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==2 && d==0)||
                                    (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                        }
  //I-figura
                        if (a==7) {//povorot 0/180
                            if ((b==0)||(b==2)||(b==4)||(b==6)) {
                                if ((c==0 && d==0) ||
                                    (c==0 && d==1) ||
                                    (c==1 && d==0) ||
                                    (c==1 && d==1) ||
                                    (c==2 && d==0) ||
                                    (c==2 && d==1) ||
                                    (c==3 && d==0) ||
                                    (c==3 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==3)||(b==5)||(b==7)) {//povorot+90/+270
                                if ((c==0 && d==0)||
                                    (c==0 && d==1)||
                                    (c==0 && d==2)||
                                    (c==0 && d==3)||
                                    (c==1 && d==0)||
                                    (c==1 && d==1)||
                                    (c==1 && d==2)||
                                    (c==1 && d==3))
                                    figura [a][b][c][d]=1;
                            }
                        }
                    }}}}
        return figura;
    }
public void drukFiguri (int nomerFiguri, int povorotFiguri, int x, int y){

        int visotaFiguri, shirinaFiguri;

        if ((x>=0)&&(x<8)      && (y>=0)&&(y<8)     &&
            (nomerFiguri>=0)   && (nomerFiguri<8)   &&
            (povorotFiguri>=0) && (povorotFiguri<8)){

            switch (nomerFiguri){

                case 0:
                    colirFiguri = colirFiguri1;
                    colirPolya = 1;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    else {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    break;
                case 1:
                    colirFiguri = colirFiguri2;
                    colirPolya = 2;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 5;
                        shirinaFiguri = 2;
                    }
                    else {
                        visotaFiguri = 2;
                        shirinaFiguri = 5;
                    }
                    break;
                case 2:
                    colirFiguri = colirFiguri3;
                    colirPolya = 3;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    else {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    break;
                case 3:
                    colirFiguri = colirFiguri4;
                    colirPolya = 4;
                    visotaFiguri=3;
                    shirinaFiguri=3;
                    break;
                case 4:
                    colirFiguri = colirFiguri5;
                    colirPolya = 5;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    else {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    break;
                case 5:
                    colirFiguri = colirFiguri6;
                    colirPolya = 6;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    else {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    break;
                case 6:
                    colirFiguri = colirFiguri7;
                    colirPolya = 7;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    else {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    break;
                case 7:
                    colirFiguri = colirFiguri8;
                    colirPolya = 8;
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 2;
                    }
                    else {
                        visotaFiguri = 2;
                        shirinaFiguri = 4;
                    }
                    break;
                default:
                    colirFiguri = colirPolya0;
                    colirPolya = 0;
                    visotaFiguri=5;
                    shirinaFiguri=5;
                    break;
            }
            if (((x + visotaFiguri-1)<8) &&
                ((y + shirinaFiguri-1)<8)){

                for (int c = 0; c < visotaFiguri; c++) {
                    for (int d = 0; d < shirinaFiguri; d++) {

                        if (1 == figura[nomerFiguri][povorotFiguri][c][d]) {
                            gameFields[x + c][y + d].setBackgroundColor(colirFiguri);
                        } } } } }
    }

    public int perevirkaKinzyaPolya (int nomerFiguri, int povorotFiguri, int x, int y){

        int visotaFiguri, shirinaFiguri;

        if ((x>=0)&&(x<8)      && (y>=0)&&(y<8)     &&
            (nomerFiguri>=0)   && (nomerFiguri<8)   &&
            (povorotFiguri>=0) && (povorotFiguri<8)){

            switch (nomerFiguri){

                case 0:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    else {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    break;
                case 1:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 5;
                        shirinaFiguri = 2;
                    }
                    else {
                        visotaFiguri = 2;
                        shirinaFiguri = 5;
                    }
                    break;
                case 2:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    else {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    break;
                case 3:
                    visotaFiguri=3;
                    shirinaFiguri=3;
                    break;
                case 4:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    else {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    break;
                case 5:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    else {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    break;
                case 6:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 3;
                    }
                    else {
                        visotaFiguri = 3;
                        shirinaFiguri = 4;
                    }
                    break;
                case 7:
                    if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6)) {
                        visotaFiguri = 4;
                        shirinaFiguri = 2;
                    }
                    else {
                        visotaFiguri = 2;
                        shirinaFiguri = 4;
                    }
                    break;
                default:
                    visotaFiguri=5;
                    shirinaFiguri=5;
                    break;
            }
            if (((x + visotaFiguri-1 ) < 8) &&
                ((y + shirinaFiguri-1) < 8))

                return 5;

            else {
                      //povertau povorot v poperednij stan
                povorot[nomerFiguri] = (povorot[nomerFiguri] - 1) < 0 ? 7 : povorot[nomerFiguri] - 1;

                curPovorot  = povorot[nomerFiguri];

                return 0;
            }
        }
                return 0;
    }
    public void onClickFigura (int nomerFiguri, int povorotFiguri) {
          //to prevent re-shaping
        if (0 == praporFiguri[nomerFiguri]){

            curNomerFiguri = nomerFiguri;

            povorot[nomerFiguri] = povorotFiguri;

            curPovorot  = povorot[nomerFiguri];

            if (5 == perevirkaKinzyaPolya(curNomerFiguri, povorot[nomerFiguri], curX, curY)){

                    drukPolya(pole);

                    drukFiguri(curNomerFiguri, povorot[nomerFiguri], curX, curY);

                    povorot[nomerFiguri] = (povorot[nomerFiguri] + 1) > 7 ? 0 : povorot[nomerFiguri] + 1;
            }
        }
        for(int i=0; i<3; i++) {
            gameFields[4][2+i].setText("");
            gameFields[5][2+i].setText("");
        }

        if(1==praporKinezGri){

            praporKinezGri=0;
           // mAdView.removeAllViews();
        }
    }

    public boolean fixFigura (int nomerFiguri, int povorotFiguri, int x, int y)  {

        if (5 == perevirkaVstavki (nomerFiguri, povorotFiguri, x, y)) {

            int znachPolya = nomerFiguri+1;//instead of switch

            for (int c = 0; c < 5; c++) {
                for (int d = 0; d < 5; d++) {

                    if (1 == figura[curNomerFiguri][curPovorot][c][d])
                        pole[x + c][y + d] = znachPolya;//instead of switch
                }}
                    if(5 == pereverkaKinezGri()) {

                       praporKinezGri = 1;

                       sound = MediaPlayer.create(this,R.raw.was_wollen_wir_trinken_obrizka);
                       sound.start();

                      gameFields[4][2].setText("W");
                      gameFields[4][3].setText("I");
                      gameFields[4][4].setText("N");

                      for(int i=0; i<3; i++){
                         gameFields[4][2+i].setTextSize(32);
                         gameFields[4][2+i].setTextColor(colirTextPeremoga);
                         gameFields[4][2+i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                      }

                      AdRequest adRequest = new AdRequest.Builder().build();
                      mAdView.loadAd(adRequest);

                      pole = poleIni();
                      for (int d = 0; d < 8; d++) {praporFiguri[d] = 0;}
                    }
            curX = 3;
            curY = 3;
            curPovorot = 0;
            for (int d = 0; d < 8; d++) { povorot[d] = 0; }

            porydokVstanovlennyaFigur[nomerVstavki]= znachPolya;
            nomerVstavki++;

            drukPolya(pole);

            praporFiguri[nomerFiguri]=1;

            return true;
        }
        else {
                    gameFields[4][2].setText("N");
                    gameFields[4][3].setText("O");
                    gameFields[5][2].setText("F");
                    gameFields[5][3].setText("I");
                    gameFields[5][4].setText("X");

                    for(int i=0; i<3; i++){
                        gameFields[4][2+i].setTextSize(32);
                        gameFields[4][2+i].setTextColor(colirTextError);
                        gameFields[4][2+i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        gameFields[5][2+i].setTextSize(32);
                        gameFields[5][2+i].setTextColor(colirTextError);
                        gameFields[5][2+i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    }

                    drukPolya(pole);

                    drukFiguri(curNomerFiguri, curPovorot, curX, curY);

            return false;
        }
    }

    public void drukPolya(int pole[][]){

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                switch (pole [x][y]){
                    case 0: colirFiguri = colirPolya0;   break;
                    case 1: colirFiguri = colirFiguri1;  break;
                    case 2: colirFiguri = colirFiguri2;  break;
                    case 3: colirFiguri = colirFiguri3;  break;
                    case 4: colirFiguri = colirFiguri4;  break;
                    case 5: colirFiguri = colirFiguri5;  break;
                    case 6: colirFiguri = colirFiguri6;  break;
                    case 7: colirFiguri = colirFiguri7;  break;
                    case 8: colirFiguri = colirFiguri8;  break;
                    default: colirFiguri = colirPolya0;  break;
                }
                gameFields[x][y].setBackgroundColor(colirFiguri);
            }}
    }
public int perevirkaVstavki (int nomerFiguri, int povorotFiguri, int x, int y){
//vdala perevirka = 5, ne vdala=0.
    for (int c = 0; c < 5; c++) {
        for (int d = 0; d < 5; d++) {

            if ((1==figura[nomerFiguri][povorotFiguri][c][d]) && (pole [x+c][y+d] > 0))

                return  0;
             } }
                return 5;
}
    public  int pereverkaKinezGri (){//vdala perevirka = 5, ne vdala=0.

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                if (0 == pole [x][y])

                    return 0;
            }}
                    return 5;
    }

    public  void ruchVerch (View v){

     if (this.curX > 0) {

         if (5 == perevirkaKinzyaPolya (curNomerFiguri,curPovorot, curX-1, curY)){

             curX --;

             drukPolya(pole);

             drukFiguri(curNomerFiguri, curPovorot, curX, curY);
         }
    }
        for(int i=0; i<3; i++) {
            gameFields[4][2+i].setText("");
            gameFields[5][2+i].setText("");
        }

    }
    public  void ruchLivo (View v){

        if (curY > 0) {

            curY-- ;

            if (5 == perevirkaKinzyaPolya (curNomerFiguri,curPovorot, curX, curY)){

                drukPolya(pole);

                drukFiguri(curNomerFiguri,curPovorot, curX, curY);
            }
        }
        for(int i=0; i<3; i++) {
            gameFields[4][2+i].setText("");
            gameFields[5][2+i].setText("");
        }
    }

    public  void ruchVniz (View v){

        int visotaFiguri;

        switch (curNomerFiguri){

            case 0: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 4;
                    else visotaFiguri = 3;
                    break;
            case 1: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 5 ;
                    else visotaFiguri = 2;
                    break;
            case 2: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 3 ;
                    else visotaFiguri = 4;
                    break;
            case 3: visotaFiguri = 3 ;
                    break;
            case 4: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 4 ;
                    else visotaFiguri = 3;
                    break;
            case 5: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 3 ;
                    else visotaFiguri = 4;
                    break;
            case 6: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 4 ;
                    else visotaFiguri = 3;
                    break;
            case 7: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         visotaFiguri = 4 ;
                    else visotaFiguri = 2;
                    break;
            default: visotaFiguri = 0;
                    break;
        }

        if (curX < 8-visotaFiguri) {

            if (5 == perevirkaKinzyaPolya(curNomerFiguri,curPovorot, curX, curY)){

                curX ++;

                drukPolya(pole);

                drukFiguri(curNomerFiguri,curPovorot, curX, curY);
            }
        }
        for(int i=0; i<3; i++) {
            gameFields[4][2+i].setText("");
            gameFields[5][2+i].setText("");
        }
    }

    public  void ruchPravo (View v){

        int shirinaFiguri;

        switch (curNomerFiguri){

            case 0: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 3;
                    else shirinaFiguri = 4;
                    break;
            case 1: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 2 ;
                    else shirinaFiguri = 5;
                    break;
            case 2: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 4 ;
                    else shirinaFiguri = 3;
                    break;
            case 3: shirinaFiguri = 3 ;
                    break;
            case 4: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 3 ;
                    else shirinaFiguri = 4;
                    break;
            case 5: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 4 ;
                    else shirinaFiguri = 3;
                    break;
            case 6: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 3 ;
                    else shirinaFiguri = 4;
                    break;
            case 7: if((curPovorot==0)||(curPovorot==2)||(curPovorot==4)||(curPovorot==6))
                         shirinaFiguri = 2 ;
                    else shirinaFiguri = 4;
                    break;
            default:     shirinaFiguri = 0;
                break;
        }

        if (curY < 8 - shirinaFiguri) {

            curY ++;

            if (5 == perevirkaKinzyaPolya(curNomerFiguri,curPovorot, curX, curY)){

                drukPolya(pole);

                drukFiguri(curNomerFiguri,curPovorot, curX, curY);
            }
        }
        for(int i=0; i<3; i++) {
            gameFields[4][2+i].setText("");
            gameFields[5][2+i].setText("");
        }
    }
    public  void delFigura (View v){

            for (int c = 0; c < 8; c++) {
                for (int d = 0; d < 8; d++) {

                    if (porydokVstanovlennyaFigur[nomerVstavki]==pole[c][d])

                        pole[c][d]=0;
                }}

            curNomerFiguri = (porydokVstanovlennyaFigur[nomerVstavki]-1) < 0 ? 0 : (porydokVstanovlennyaFigur[nomerVstavki]-1);

            nomerVstavki = (nomerVstavki-1) < 0 ? 0 : (nomerVstavki-1);

        drukPolya(pole);

        curX=3;

        curY=3;

        curPovorot=0;

        praporFiguri[curNomerFiguri]=0;

        for (int d = 0; d < 8; d++) {povorot[d]=0;}

        for(int i=0; i<3; i++) {
            gameFields[4][2+i].setText("");
            gameFields[5][2+i].setText("");
        }
    }
}

