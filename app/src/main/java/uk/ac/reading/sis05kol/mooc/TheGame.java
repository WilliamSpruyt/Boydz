package uk.ac.reading.sis05kol.mooc;

//Other parts of the android libraries that we use

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class TheGame extends GameThread {

    private int imind=0;
    //Will store the image of a ball
    private Bitmap mBall;

    //The X and Y position of the ball on the screen
    //The point is the top left corner, not middle, of the ball
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float mode = 0;
    private float mBallX = -100;
    private float mBallY = -100;
    private double orbit = 0;



    //The speed (pixel/second) of the ball in direction X and Y
    private float mBallSpeedX = 0;
    private float mBallSpeedY = 0;


    //Will store the image of the Paddle used to hit the ball
    private Bitmap mPaddle;




    //Paddle's x position. Y will always be the bottom of the screen
    private float mPaddleX = 0;

    //The speed (pixel/second) of the paddle in direction X and Y
    private float mPaddleSpeedX = 0;
    private float touchY=-1;
    private float touchX=-1;
    //Will store the image of the smiley ball (score ball)
    private Bitmap mSmileyBall;
    private Bitmap[] harryWalkers;
    private Bitmap[] georgeWalkers;
    private Bitmap[] harryWalkersin;
    private Bitmap[] georgeWalkersin;
    private Body[] body;
    //The X and Y position of the ball on the screen
    //The point is the top left corner, not middle, of the ball
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float mSmileyBallX = -100;
    private float mSmileyBallY = -100;

    //Will store the image of the smiley ball (score ball)
    private Bitmap mSadBall;

    //The X and Y position of the SadBalls on the screen
    //The point is the top left corner, not middle, of the balls
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float[] mSadBallX = {-100, -100, -100};
    private float[] mSadBallY = new float[3];

    //This will store the min distance allowed between a big ball and the small ball
    //This is used to check collisions
    private float mMinDistanceBetweenBallAndPaddle = 0;


    //This is run before anything else, so we can prepare things here
    public TheGame(GameView gameView) {

        //House keeping
        super(gameView);
        body = ballMaker(mCanvasWidth, mCanvasHeight,this.getGeorgeNum(),this.getHarryNum());
        //Prepare the image so we can draw it on the screen (using a canvas)
        georgeWalkersin = new Bitmap[]{BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.georgeinv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george1inv),BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george1inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george2inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george3inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george3inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george6inv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george7inv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george7inv),
        };
        georgeWalkers = new Bitmap[]{BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george1),BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george1),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george2),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george3),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george3),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.george6), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george7), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.george7),
                 };
        harryWalkers = new Bitmap[]{BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry1),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry2),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry3),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry4), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry6), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry7),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry8), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry9)};
        harryWalkersin = new Bitmap[]{BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harryinv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry1inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry2inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry3inv3),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry4inv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry6inv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry7inv),
                BitmapFactory.decodeResource
                        (gameView.getContext().getResources(),
                                R.drawable.harry8inv), BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.harry9inv)};


        mBall = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.small_red_ball);

        //Prepare the image of the paddle so we can draw it on the screen (using a canvas)
        mPaddle = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.yellow_ball);

        //Prepare the image of the SmileyBall so we can draw it on the screen (using a canvas)
        mSmileyBall = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.smiley_ball);

        //Prepare the image of the SadBall(s) so we can draw it on the screen (using a canvas)
        mSadBall = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.sad_ball);
    }

    //This is run before a new game (also after an old game)
    @Override

    public void setupBeginning() {
        //Initialise speeds
        //mCanvasWidth and mCanvasHeight are declared and managed elsewhere
        body = ballMaker(mCanvasWidth, mCanvasHeight,this.getGeorgeNum(),this.getHarryNum());
        //doStart();


    }

    @Override
    protected void doDraw(Canvas canvas) {
        //If there isn't a canvas to do nothing
        //It is ok not understanding what is happening here
        if (canvas == null) return;

        //House keeping
        super.doDraw(canvas);

        //canvas.drawBitmap(bitmap, x, y, paint) uses top/left corner of bitmap as 0,0
        //we use 0,0 in the middle of the bitmap, so negate half of the width and height of the ball to draw the ball as expected
        //A paint of null means that we will use the image without any extra features (called Paint)

        //draw the image of the ball using the X and Y of the ball
        //canvas.drawBitmap(mBall, mBallX - mBall.getWidth() / 2, mBallY - mBall.getHeight() / 2, null);
            /*if (Mode==1 ) {int j=0;


                {body[0].counter_getter(mCanvasWidth,mCanvasHeight);}
                Mode=2;}*/

        for (int i = 0; i < body.length; i++) {


            if (body[i].getName()=="Harry" ) {
                if (body[i].getVelx()<0){
                body[i].display(canvas, harryWalkers[imind/4]);}
                if (body[i].getVelx()>0){
                    body[i].display(canvas, harryWalkersin[imind/4]);}

            }
            if (body[i].getName()=="George" ) {
                if (body[i].getVelx()<0){
                    body[i].display(canvas, georgeWalkersin[imind/4]);}
                if (body[i].getVelx()>0){
                    body[i].display(canvas, georgeWalkers[imind/4]);};
            }

            //if (Mode!=1){body[i].counter_getter(mCanvasWidth,mCanvasHeight);}
            body[i].calming();

            if (mode == 0) {
                if (touchX!=-1){
                    body[i].finger(touchX,touchY);}
                body[i].move(mCanvasWidth, mCanvasHeight);
                for (int j = 0; j < body.length; j++){
                    body[i].collide(body[j]);}
                body[i].singularity(mCanvasWidth, mCanvasHeight);
                body[i].counter_getter(mCanvasWidth, mCanvasHeight);
            }


            if (mode == 1) {
                body[i].disco_ball(mCanvasWidth, mCanvasHeight);
                body[i].finger(touchX,touchY);
            }
            if (mode == 3) {
                body[i].revdisco_ball(mCanvasWidth, mCanvasHeight);
                body[i].finger(touchX,touchY);
            }

            if (mode == 2) {
                {if (touchX!=-1){
                    body[i].finger(touchX,-1);}
                    body[i].move(mCanvasWidth, mCanvasHeight);
                    body[i].bounce(mCanvasWidth, mCanvasHeight, harryWalkers[0]);
                    for (int j = 0; j < body.length; j++){
                    body[i].collide(body[j]);}
                    body[i].counter_getter(mCanvasWidth, mCanvasHeight);
                }


            }
            imind++;
            if (imind>32){imind=0;}
        }
        touchX=-1;touchY=-1;
        //Draw Paddle using X of paddle and the bottom of the screen (top/left is 0,0)
        // canvas.drawBitmap(mPaddle, mPaddleX - mPaddle.getWidth() / 2, mCanvasHeight - mPaddle.getHeight() / 2, null);

        //Draw SmileyBall
        // canvas.drawBitmap(mSmileyBall, mSmileyBallX - mSmileyBall.getWidth() / 2, mSmileyBallY - mSmileyBall.getHeight() / 2, null);

        //Loop through all SadBall
        //  for(int i = 0; i < mSadBallX.length; i++) {
        //Draw SadBall in position i
        //   canvas.drawBitmap(mSadBall, mSadBallX[i] - mSadBall.getWidth() / 2, mSadBallY[i] - mSadBall.getHeight() / 2, null);
        // }
    }

    //This is run whenever the phone is touched by the user
    @Override
    protected void actionOnTouch(float x, float y) {

    {touchX=x;
        touchY=y;}

    }




    //This is run whenever the phone moves around its axises
    @Override
    protected void actionWhenPhoneMoved(float xDirection, float yDirection, float zDirection) {
        //Change the paddle speed
        mPaddleSpeedX = mPaddleSpeedX + 70f * xDirection;

        //If paddle is outside the screen and moving further away
        //Move it into the screen and set its speed to 0
        if (mPaddleX <= 0 && mPaddleSpeedX < 0) {
            mPaddleSpeedX = 0;
            mPaddleX = 0;
        }
        if (mPaddleX >= mCanvasWidth && mPaddleSpeedX > 0) {
            mPaddleSpeedX = 0;
            mPaddleX = mCanvasWidth;
        }

    }


    //This is run just before the game "scenario" is printed on the screen
    @Override
    protected void updateGame(float secondsElapsed) {
        //If the ball moves down on the screen

        if (mBallSpeedY > 0) {
            //Check for a paddle collision
            updateBallCollision(mPaddleX, mCanvasHeight);
        }


        //Check if the ball hits either the left side or the right side of the screen
        //But only do something if the ball is moving towards that side of the screen
        //If it does that => change the direction of the ball in the X direction
        if ((mBallX <= mBall.getWidth() / 2 && mBallSpeedX < 0) || (mBallX >= mCanvasWidth - mBall.getWidth() / 2 && mBallSpeedX > 0)) {
            mBallSpeedX = -mBallSpeedX;
        }

        //Check for SmileyBall collision
        if (updateBallCollision(mSmileyBallX, mSmileyBallY)) {
            //Increase score
            updateScore(1);
        }

        //Loop through all SadBalls
        for (int i = 0; i < mSadBallX.length; i++) {
            //Perform collisions (if necessary) between SadBall in position i and the red ball
            updateBallCollision(mSadBallX[i], mSadBallY[i]);
        }

        //If the ball goes out of the top of the screen and moves towards the top of the screen =>
        //change the direction of the ball in the Y direction
        if (mBallY <= mBall.getWidth() / 2 && mBallSpeedY < 0) {
            mBallSpeedY = -mBallSpeedY;
        }

        //If the ball goes out of the bottom of the screen => lose the game
        if (mBallY >= mCanvasHeight) {
            setState(GameThread.STATE_LOSE);
        }

    }
    public int getNumballs(){return getGeorgeNum()+getHarryNum();}
    //Collision control between mBall and another big ball
    private boolean updateBallCollision(float x, float y) {
        //Get actual distance (without square root - remember?) between the mBall and the ball being checked
        float distanceBetweenBallAndPaddle = (x - mBallX) * (x - mBallX) + (y - mBallY) * (y - mBallY);

        //Check if the actual distance is lower than the allowed => collision
        if (mMinDistanceBetweenBallAndPaddle >= distanceBetweenBallAndPaddle) {
            //Get the present speed (this should also be the speed going away after the collision)
            float speedOfBall = (float) Math.sqrt(mBallSpeedX * mBallSpeedX + mBallSpeedY * mBallSpeedY);

            //Change the direction of the ball
            mBallSpeedX = mBallX - x;
            mBallSpeedY = mBallY - y;

            //Get the speed after the collision
            float newSpeedOfBall = (float) Math.sqrt(mBallSpeedX * mBallSpeedX + mBallSpeedY * mBallSpeedY);

            //using the fraction between the original speed and present speed to calculate the needed
            //velocities in X and Y to get the original speed but with the new angle.
            mBallSpeedX = mBallSpeedX * speedOfBall / newSpeedOfBall;
            mBallSpeedY = mBallSpeedY * speedOfBall / newSpeedOfBall;

            return true;
        }

        return false;
    }



    public Body[] ballMaker(int width, int height,int GeorgeNum,int HarryNum) {
        //float  Xpos, float  Ypos, float Velx,float Vely,float Increment,float Bounc
        Random r = new Random();

        int numBalls = GeorgeNum+HarryNum;
        String name="George";
        Body body[] = new Body[numBalls];
        for (int i = 0; i < numBalls; i++) {
            if (i<HarryNum){ name="Harry";}
            else{ name="George";}
            body[i] = new Body(name,r.nextInt(width), r.nextInt(height), ((r.nextInt(10)) / 2), ((r.nextInt(10)) / 2), (r.nextDouble() * 2) - 1, (-30 - r.nextInt(40)) / 5, width, height)
            ;
        }
        return body;
    }


    public void setMode(int mude) {
        mode = mude;
    }


}