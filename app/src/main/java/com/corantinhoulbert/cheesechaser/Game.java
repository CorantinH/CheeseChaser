package com.corantinhoulbert.cheesechaser;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Game extends View implements View.OnTouchListener{

    private int[][] board;
    private List<Integer> deck;


    private boolean mBooleanIsPressed = false;

    int dx = 0;
    int dy = 0;
    int predx = 0;
    int predy = 0;

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        initiateBoard();
        initiateDeck();
    }



    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            Log.d(" HANDLER" , "log LONG TOUCH");
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Resources res = getResources();

        Random rand = new Random();
        Integer randomInt = initiateDeck().get(rand.nextInt(initiateDeck().size()));

        Bitmap bitmap = BitmapFactory.decodeResource(res, randomInt);

        canvas.drawBitmap(bitmap, dx, dy, paint);



    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(" TOUCH" , "DOWN");
            // Execute your Runnable after 1000 milliseconds = 1 second.
            handler.postDelayed(runnable, 1000);
            mBooleanIsPressed = true;

            predx = x;
            predy = y;

        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(mBooleanIsPressed) {
                mBooleanIsPressed = false;
                handler.removeCallbacks(runnable);

            }
            Log.d(" TOUCH" , "UP");


        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {

            //anything happening with event here is the X Y of the raw screen event, relative to the view.
            Log.d(" TOUCH" , "MOVE");
            this.dx =  predx + (x - predx);
            this.dy =   predy + (y - predy) ;

        }
        this.invalidate();
        return true;
    }

    private void initiateBoard()
    {
        board = new int[80][80];

        for (int i=0; i<80;i++)
        {
            for (int j=0; j<80;j++)
            {
                board[i][j]=0;
            }
        }
        initiateStart();
        randomGame();

    }
    private void initiateStart()
    {
        deck = initiateDeck();
        int firstCard = removeTopCard(deck);
        board[40][40]= firstCard;
        board[40][41]= 1;
        board[40][39]= 1;
        board[39][40]= 1;
        board[39][41]= 1;
        board[39][39]= 1;
        board[41][40]= 1;
        board[41][41]= 1;
        board[41][39]= 1;

    }
    private List<Integer> initiateDeck()
    {
        List<Integer> newDeck= new ArrayList<Integer>();
        for (int souris=0; souris<20;souris++)
        {
            newDeck.add(R.drawable.souris);
        }
        for (int fromages=0; fromages<9;fromages++)
        {
            newDeck.add(R.drawable.fromage);
        }
        for (int chats=0; chats<7;chats++)
        {
            newDeck.add(R.drawable.chat);
        }
        for (int tapettes=0; tapettes<4;tapettes++)
        {
            newDeck.add(R.drawable.tapette);
        }
        shuffleDeck(newDeck);
        return newDeck;



    }
    private void shuffleDeck(List<Integer> inDeck)
    {
        Collections.shuffle(inDeck);
    }
    private int removeTopCard(List<Integer> inDeck)
    {
        int output = inDeck.get(0);
        inDeck.remove(0);
        return output;
    }
    private int[][] scan4mice(int x,int y)
    {
        int[][] output = new int[4][2];
        int[][][] scan = scan9(x,y);
        int counter =0;
        int mouseCount = 0;
        for (int k= 0;k<=2;k++)
        {
            for (int l= 0;l<=2;l++)
            {
                counter++;
                if ((counter%2)==0)
                {
                    if (scan[k][l][2]==2){
                        mouseCount++;
                        output[mouseCount-1][0]=scan[l][k][0];
                        output[mouseCount-1][1]=scan[l][k][1];

                    }
                }
            }
        }

        return output;
    }

    private int[][][] scan9(int x, int y)
    {
        int[][][] output = new int[3][3][3];
        for (int i=-1; i<2;i++)
        {
            for (int j=-1;j<2;j++)
            {
                if ((x+i)>=0 && (x+i)<=80)
                {
                    output[i+1][j+1][0]=x+i;
                    if((y+j)>=0 && (y+j) <= 80)
                    {
                        output[i+1][j+1][1]=y+j;
                    }
                }
                if ((x+i)>=0 && (x+i)<=80 && (y+j)>=0 && (y+j) <= 80)
                {
                    output[i+1][j+1][2]=board[x+i][y+j];
                }


            }
        }
        return output;
    }

    private int scoring(boolean win)
    {
        int score=0;
        if (win)
        {
            for (int i=0; i<80;i++)
            {
                for (int j=0; j<80;j++)
                {
                    switch (board[i][j])
                    {
                        case 2:
                            score= score+1;
                            break;
                        case 3:
                            int[][][] scan = scan9(i,j);
                            int counter =0;
                            int mouseCount = 0;
                            for (int k= 0;k<2;k++)
                            {
                                for (int l= 0;l<2;l++)
                                {
                                    counter++;
                                    if ((counter%2)==0)
                                    {
                                        if (scan[l][k][2]==1){
                                            mouseCount++;
                                        }
                                    }
                                }
                            }
                            if(mouseCount ==4){
                                score= score+ (10 + (mouseCount*2));
                            }
                            else{
                                score= score+mouseCount;
                            }
                            break;
                        default:
                            break;
                    }
                }

            }
        }

        return score;
    }


    private int randomGame(){
        List<Integer> virtualDeck = initiateDeck();
        for (int i=0;i<8;i++)
        {
            for (int j=0;j<5;j++)
            {
                board[i][j]= removeTopCard(virtualDeck);
            }
        }
        ruling();
        int score = scoring(true);
        return score;
    }
    private void ruling(){
        int[][] mice;
        for (int i=0; i<80;i++)
        {
            for (int j=0;j<80;j++)
            {
                if(board[i][j]==4)
                {
                    mice = scan4mice(i,j);
                    int kl=mice.length;
                    for (int k =0; k<kl;k++)
                    {
                        int x=mice[k][0];
                        int y=mice[k][1];
                        board[x][y]=6;

                    }
                }
            }
        }
        for (int i=0; i<80;i++) {
            for (int j = 0; j < 80; j++) {
                if (board[i][j] == 3) {
                    mice = scan4mice(i, j);
                    int ll = mice.length;
                    if (ll != 4) {
                        board[i][j] = 7;
                    }
                }
            }
        }
    }
}
