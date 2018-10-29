package oblik.in.ua.brickpuzzle;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer,timerVniz;
    private static byte figuraCur, povorotCur, perevirka, praporVniz, praporStart,
                        praporStop,figuraNext;
    private static int  xCur, yCur,point,timeStart =1000,timePeriod = 1000, maxPoint;
    private static byte figura [][][][] = new byte [7][4][3][3];
    private static byte poleRuch [][] = new byte [22][12];
    public static Map <String, String> kartkaIP = new HashMap<>();

    TextView [] [] gameFields = new TextView [20] [10];
    Button buttonStart, buttonStop, buttonLeft, buttonRight, buttonTwist, buttonSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<20; i++){
            for (int j=0; j<10; j++){

                String  gameFieldId;

                if (i<10) {gameFieldId = "textView0"+i+""+j;}
                else      {gameFieldId = "textView" +i+""+j;}

                int curID = getResources().getIdentifier(gameFieldId, "id", getPackageName());

                gameFields[i][j] =  findViewById(curID);
            }}

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
        buttonTwist = findViewById(R.id.buttonTwist);
        buttonSpeed = findViewById(R.id.buttonSpeed);

        kartkaIP = readFile ();
        iniVse();
    }

    //metod  zadae blakitnij kolir v gameFields
    public void drukFigura(int x, int y){
        gameFields [x][y].setBackgroundColor(0xFF64B5F6);
    }
    //metod  zadae svitlo-sirij kolir v gameFields
    public void drukNastupnaFigura(int x, int y){
        gameFields [x][y].setBackgroundColor(0xFF90A4AE);
    }
    //metod  zadae govtij kolir z ramkoj v gameFields
    public void stertFigura(int x, int y){
        gameFields [x][y].setBackground(getDrawable(R.drawable.border));
    }

    private static byte randomNomer (){

        return  (byte) (Math.random()*7);
    }

    public static void iniVse(){
        xCur = 0; yCur=4; povorotCur=0;
        figuraNext = randomNomer();
        figuraCur = randomNomer();
        poleRuch = poleIni();
        figura = figuraIni();
        perevirka=praporVniz=praporStart=praporStop=0;
        timeStart=timePeriod=1000;
        point=0;

        if(null!=kartkaIP.get("MaxPoint"))
            maxPoint = Integer.parseInt(kartkaIP.get("MaxPoint").trim());
    }

    private static byte[][] poleIni (){
        byte pole [][]=new byte [22][12];
        for (byte c=0; c<22; c++){
            for (byte d=0; d<12; d++){
// megi polya=-1
                if (c==0||c==21||d==0||d==11)
                    pole[c][d]=-1;
            }}
        return pole;
    }
    //figuri:0=L,1=J,2=T,3=S,4=Z,5=kvadrat, 6=|.
//povrot:0=0,1=-90,2=-180, 3=-270.
    private static byte [][][][] figuraIni (){
        byte figura [][][][]=new byte [7][4][3][3];
        for (byte a=0; a<7; a++){
            for (byte b=0; b<4; b++){
                for (byte c=0; c<3; c++){
                    for (byte d=0; d<3; d++){
//L-figura
                        if (a==0 ) {//povorot 0
                            if (b==0) {
                                if ((c==0 && d==0)||
                                        (c==1 && d==0)||
                                        (c==2 && d==0)||
                                        (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }  //povorot-90
                            if (b==1) {
                                if ((c==2 && d==0)||
                                        (c==2 && d==1)||
                                        (c==2 && d==2)||
                                        (c==1 && d==2))
                                    figura [a][b][c][d]=1;
                            }//povorot-180
                            if (b==2) {
                                if ((c==0 && d==1)||
                                        (c==0 && d==2)||
                                        (c==1 && d==2)||
                                        (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }//povorot-270
                            if (b==3) {
                                if ((c==0 && d==0)||
                                        (c==0 && d==1)||
                                        (c==0 && d==2)||
                                        (c==1 && d==0))
                                    figura [a][b][c][d]=1;
                            }
                        }
//J-figura
                        if (a==1 ) {//povorot 0
                            if (b==0) {
                                if ((c==0 && d==2)||
                                        (c==1 && d==2)||
                                        (c==2 && d==2)||
                                        (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }  //povorot-90
                            if (b==1) {
                                if ((c==0 && d==0)||
                                        (c==0 && d==1)||
                                        (c==0 && d==2)||
                                        (c==1 && d==2))
                                    figura [a][b][c][d]=1;
                            }   //povorot-180
                            if (b==2) {
                                if ((c==0 && d==0)||
                                        (c==1 && d==0)||
                                        (c==2 && d==0)||
                                        (c==0 && d==1))
                                    figura [a][b][c][d]=1;
                            }//povorot-270
                            if (b==3) {
                                if ((c==1 && d==0)||
                                        (c==2 && d==0)||
                                        (c==2 && d==1)||
                                        (c==2 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                        }
//T-figura
                        if (a==2 ) {//povorot 0
                            if (b==0) {
                                if ((c==0 && d==0)||
                                        (c==0 && d==1)||
                                        (c==0 && d==2)||
                                        (c==1 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if (b==1) {//povorot-90
                                if ((c==0 && d==0)||
                                        (c==1 && d==0)||
                                        (c==2 && d==0)||
                                        (c==1 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if (b==2) {//povorot-180
                                if ((c==2 && d==0)||
                                        (c==2 && d==1)||
                                        (c==2 && d==2)||
                                        (c==1 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if (b==3) {//povorot-270
                                if ((c==0 && d==2)||
                                        (c==1 && d==2)||
                                        (c==2 && d==2)||
                                        (c==1 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                        }
//S-figura
                        if (a==3) {//povorot 0/180
                            if ((b==0)||(b==2)) {
                                if ((c==0 && d==1)||
                                        (c==0 && d==2)||
                                        (c==1 && d==0)||
                                        (c==1 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==3)) {//povorot-90/-270
                                if ((c==0 && d==0)||
                                        (c==1 && d==0)||
                                        (c==1 && d==1)||
                                        (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                        }
//Z-figura
                        if (a==4) {//povorot 0/180
                            if ((b==0)||(b==2)) {
                                if ((c==0 && d==0)||
                                        (c==0 && d==1)||
                                        (c==1 && d==1)||
                                        (c==1 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==3)) {//povorot-90/-270
                                if ((c==0 && d==2)||
                                        (c==1 && d==1)||
                                        (c==1 && d==2)||
                                        (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                        }
//kvadrat-figura
                        if (a==5) {//povorot 0/-90/-180/-270
                            if ((b==0)||(b==1)||
                                    (b==2)||(b==3)) {
                                if ((c==0 && d==0)||
                                        (c==0 && d==1)||
                                        (c==1 && d==0)||
                                        (c==1 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                        }
//|-figura
                        if (a==6) {//povorot 0/180
                            if ((b==0)||(b==2)) {
                                if ((c==0 && d==1) ||
                                        (c==1 && d==1) ||
                                        (c==2 && d==1))
                                    figura [a][b][c][d]=1;
                            }
                            if ((b==1)||(b==3)) {//povorot-90/-270
                                if ((c==1 && d==0)||
                                        (c==1 && d==1)||
                                        (c==1 && d==2))
                                    figura [a][b][c][d]=1;
                            }
                        }
                    }}}}
        return figura;
    }
    public void vidbitokFiguri (int xC, int yC,byte nomerF, byte nomerP){
        for (byte c=0; c<3; c++){
            for (byte d=0; d<3; d++){
                if (1==figura [nomerF][nomerP][c][d]){
                    this.drukFigura((xC+c),(yC+d));
                    drukVPole((xC+c+1),(yC+d+1));
                }
            }}}

    public void vidbitokNastupnojFiguri( byte nomerF){
        for (byte c=0; c<3; c++){
            for (byte d=0; d<3; d++){
                if (1==figura [nomerF][0][c][d]){
                    this.drukNastupnaFigura(c,d);
                }
            }}}

    public void stiranieFiguri (int xC, int yC,byte nF, byte nP){
        for (byte c=0; c<3; c++){
            for (byte d=0; d<3; d++){
                if (1==figura [nF][nP][c][d]){
                    this.stertFigura((xC+c),(yC+d));
                    stiranieZPolya((xC+c+1),(yC+d+1));
                }
            }}}

    public void  drukVPole(int xP, int yP){ poleRuch[xP][yP]=1; }

    public void  stiranieZPolya (int xP, int yP){ poleRuch[xP][yP]=0; }

    //perevirka=0-pochatok,=-1-mega,=1-figura,=5-vdala.
    public byte perevirkaVPole (int xC, int yC, byte nF, byte nP){
        for (byte c=0; c<3; c++){
            for (byte d=0; d<3; d++){
                if (figura [nF][nP][c][d]==1){
                    if (poleRuch[(xC+c+1)][(yC+d+1)]==-1)
                        return perevirka=-1;
                    if (poleRuch[(xC+c+1)][(yC+d+1)]==1)
                        return perevirka=1;
                }}}
        return  perevirka=5;
    }
    public void kinezGri() {

        timer.cancel();

        for (int i=0; i<20; i++){
            for (int j=0; j<10; j++){
                this.stertFigura(i,j);
            }}
        iniVse();
    }
    private byte indexPovnogoRyadka() {
//yakcho indexPovnogoRyadka vidsutnij povertae 100.
        for (byte x=20; x>=1; x--){
            byte sum=0;
            for (byte y=1; y<=10; y++){
                sum+=poleRuch[x][y];
            }
            if (sum==10)return x;
        }
        return 100;
    }
    private byte[] [] zsuvPolya(byte indexPR) {
        byte[] [] poleNove = new byte [22][12];
        for (int y=1; y<=10; y++){
            for (int x=20; x>=1; x--){
                if (x>indexPR) poleNove [x][y]=poleRuch[x][y];
                if (x<indexPR) poleNove [x+1][y]=poleRuch[x][y];
            }}
        for (byte c=0; c<22; c++){
            for (byte d=0; d<12; d++){
//megi polya=-1
                if (c==0||c==21||d==0||d==11)
                    poleNove[c][d]=-1;
            }}
        return poleNove;
    }
    private int videoZsuvPolya() {
        byte iPR=indexPovnogoRyadka();
        byte kilkistPovnichRydkiv=0;
        int point;
        if (100!=iPR){
//odnochasno ne moge byt` bilche 3-x povnich Ryadkiv
            for (int i=1; i<=3;i++){
                if (100!=iPR){
                    kilkistPovnichRydkiv++;
                    poleRuch= zsuvPolya(iPR);
                    iPR=indexPovnogoRyadka();
                }}
            for (byte x=0; x<22; x++){
                for (byte y=0; y<12; y++){
                    if (1==poleRuch[x][y]) this.drukFigura((x-1), (y-1));
                    if (0==poleRuch[x][y]) this.stertFigura((x-1),(y-1));
                }}
        }
        switch (kilkistPovnichRydkiv){
            case 1: point=1; break;
            case 2: point=3; break;
            case 3: point=6; break;
            default: point=0;
        }
        return point;
    }
    class RuchVniz extends TimerTask {

        public void run() {

           runOnUiThread(()-> {

            stiranieFiguri (xCur,yCur,figuraCur,povorotCur);
            vidbitokNastupnojFiguri(figuraNext);
            perevirka=perevirkaVPole((xCur+1), yCur, figuraCur, povorotCur);
            if (5==perevirka) {
                xCur++;
                vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
            }
            else {vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
                  stiranieFiguri (0,0,figuraNext,(byte)0);
                  xCur=0; yCur=4; povorotCur=0;
                  figuraCur = figuraNext;
                  figuraNext = randomNomer();
                  vidbitokNastupnojFiguri(figuraNext);
                  point+=videoZsuvPolya();
                if (point>maxPoint){
                    maxPoint=point;
                    kartkaIP.put("MaxPoint",""+maxPoint);
                    saveFile(kartkaIP);
                }
                gameFields[1][9].setText(String.valueOf(point));
                gameFields[0][9].setText(String.valueOf(maxPoint));
                if (1==praporVniz){
                    timerVniz.cancel();
                    praporVniz=0;
                    praporStart=1;
                    praporStop=0;
                }
                perevirka=perevirkaVPole(xCur,yCur,figuraCur,povorotCur);
                if (5==perevirka){
                    vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
                    vidbitokNastupnojFiguri(figuraNext);
                }
                else kinezGri();
            }
                });

        }}

    public Map <String, String> readFile(){

        //kIP - kartka im`ya-point
        Map <String, String> kIP=new HashMap<>();

        try {
            FileInputStream fileInputStream = openFileInput("point.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine())!=null) {
                //obrizau probil z pochatku ta kinzya
                String r2 = line.trim();
                //perevirka na pustoj
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

    public void saveFile(Map<String, String> kIP){

        if (kIP!=null && !kIP.isEmpty()) {

            try {
                FileOutputStream fileOutputStream = openFileOutput("point.txt", MODE_PRIVATE);

                for (String Imya : kIP.keySet()) {
                    String txt = Imya + " " + kIP.get(Imya);
                    fileOutputStream.write(txt.getBytes());
                    fileOutputStream.write(System.lineSeparator().getBytes());
                }
                fileOutputStream.close();

            } catch (IOException e)   { e.printStackTrace(); }

        } }

    public  void clickStart(View v){
        if (0==praporStart){
            praporStart=1;
            praporStop=0;
            vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
            vidbitokNastupnojFiguri(figuraNext);
            timer=new Timer();
            timer.schedule(new RuchVniz(),timeStart,timePeriod);
        } }
    public void clickStop(View v){
        if (1==praporStart){
            praporStop=1;
            praporStart=0;
            timer.cancel();
            vidbitokNastupnojFiguri(figuraNext);
        }}

    public void clickLeft(View v){
        if ((1==praporStart)||(1==praporStop)){
            stiranieFiguri (xCur,yCur,figuraCur,povorotCur);
            vidbitokNastupnojFiguri(figuraNext);
            perevirka=perevirkaVPole(xCur,(byte)(yCur-1),figuraCur, povorotCur);
            if (5==perevirka) yCur--;
            vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
        }}

    public void clickRight(View v){
        if ((1==praporStart)||(1==praporStop)){
            stiranieFiguri (xCur,yCur,figuraCur,povorotCur);
            vidbitokNastupnojFiguri(figuraNext);
            perevirka=perevirkaVPole(xCur,(yCur+1),figuraCur, povorotCur);
            if (5==perevirka) yCur++;
            vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
        }}
    public void clickTwist(View v){
        if ((1==praporStart)||(1==praporStop)){
            stiranieFiguri (xCur,yCur,figuraCur,povorotCur);
            vidbitokNastupnojFiguri(figuraNext);
            byte t = (povorotCur+1) >3 ? 0: (byte)(povorotCur+1);
            perevirka=perevirkaVPole(xCur,yCur,figuraCur,t);
            if (5==perevirka) povorotCur=t;
            vidbitokFiguri (xCur,yCur,figuraCur,povorotCur);
        }}
    public void clickSpeed(View v){

        if ((0==praporVniz)&&
           ((1==praporStart)||(1==praporStop))){

            vidbitokNastupnojFiguri(figuraNext);
            praporVniz=1;
            if (1==praporStart) timer.cancel();
            timerVniz=new Timer();
            timerVniz.schedule(new RuchVniz(), 0, timePeriod/50);

            timer=new Timer();
            timer.schedule(new RuchVniz(), timeStart, timePeriod);
        }}
//LLL
}
