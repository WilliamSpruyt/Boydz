package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Family on 25/05/2016.
 */

public class Body {
    int[] c;
    String name;
    float centrex;
    float centrey;
    double xpos;
    double ypos;
    double velx;
    double vely;
    float initvelx;
    float initvely;
    float initx;
    float inity;
    float rad;
    int mCanvasWidth;
    int mCanvasHeight;
    double orbit = 0;
    double counter = 0;
    double increment;
    float transparency = 128;
    float bounce;
    float e = 0.8f;
    int CALM = 6;

    //The Constructor is defined with arguments.
    Body(String Name, float Xpos, float Ypos, float Velx, float Vely, double Increment, float Bounce, int canvaswidth, int canvasheight) {
        name = Name;
        xpos = Xpos;
        ypos = Ypos;
        velx = Velx;
        initvelx = Velx;
        initvely = Vely;
        initx = Xpos;
        inity = Ypos;
        vely = Vely;
        mCanvasWidth = canvaswidth;
        mCanvasHeight = canvasheight;
        centrex = mCanvasWidth / 2;
        centrey = mCanvasHeight / 2;
        increment = Increment;
        bounce = Bounce;

    }

    public void display(Canvas canvas, Bitmap mBall) {
        float expos = Float.valueOf(String.valueOf(xpos));
        float whypos = Float.valueOf(String.valueOf(ypos));
        canvas.drawBitmap(mBall, expos - mBall.getWidth() / 2, whypos - mBall.getHeight() / 2, null);

    }

    void move(int width, int height) {
        xpos = xpos + velx;
        ypos = ypos + vely;
        if (xpos > width + (2 * rad)) {
            xpos = 0 - (2 * rad);
        }
        if (ypos > height + (2 * rad)) {
            /*ypos = 0 - (2 * rad);*/
        }
        if (xpos < 0 - (2 * rad)) {
            xpos = width + (2 * rad);
        }
        if (ypos < 0 - (2 * rad)) {
           /* ypos = height + (2 * rad);*/
        }
    }

    void singularity(int width, int height) {
        double dis = Math.sqrt(Math.pow(xpos - width / 2, 2) + Math.pow(ypos - height / 2, 2));
        double pull = 0.1 / (dis);
        velx = velx + ((width / 2) - (xpos)) * pull;
        vely = vely + ((height / 2) - (ypos)) * pull;
    }

    /*void crowding(){
        for(int i = 0; i < numBalls; ++i)

            if (this!= body[i] &&dist(this.xpos,this.ypos,body[i].xpos,body[i].ypos )<((this.rad+body[i].rad)/2)-1){xpos=(mCanvasWidth*1.5)*random(-1,1);ypos=mCanvasHeight*1.5*random(1,-1);}
 */
    void collide(Body thebody){


            double dis = Math.sqrt(Math.pow(xpos - thebody.getXpos(),2) + Math.pow(ypos - thebody.getYpos(),2));

            if (this != thebody){
                if (dis<=(50)){
                    double tempx=this.getVelx();
                    double tempy=this.getVely();
                    this.setVelx(thebody.getVelx());
                    this.setVely(thebody.getVely());

                    thebody.setVelx(tempx);
                    thebody.setVely(tempy);
                }}}



    void disco_ball(int width, int height) {

        orbit = Math.sqrt(Math.pow(xpos - width / 2, 2) + Math.pow(ypos - height / 2, 2));


        counter += increment / 10;
        double ffs = width / 2 + (orbit * Math.sin(counter));
        double fFs = height / 2 + (orbit * Math.cos(counter));
        xpos = Float.valueOf(String.valueOf(ffs));
        ypos = Float.valueOf(String.valueOf(fFs));


    }
    void revdisco_ball(int width, int height) {

        orbit = Math.sqrt(Math.pow(xpos - width / 2, 2) + Math.pow(ypos - height / 2, 2));


        counter -= increment / 10;
        double ffs = width / 2 + (orbit * Math.cos(counter));
        double fFs = height / 2 + (orbit * Math.cos(counter));
        xpos = Float.valueOf(String.valueOf(ffs));
        ypos = Float.valueOf(String.valueOf(fFs));


    }

    void bounce(int width, int height, Bitmap ball) {
        float grav = 0.05f;
        Random r = new Random();
        xpos = xpos + velx;
        ypos = ypos + vely;
        vely = vely + grav;
        if (xpos + velx > width) {
            velx = velx * -1;
        }
        if (ypos + vely > (height - (ball.getHeight() / 2.5))) {
            vely = bounce;
            bounce = bounce * e;
        }
        if (xpos + velx < 0) {
            velx = velx * -1;
        }
        if (bounce > -0.1) {
            bounce = r.nextFloat() * -70;
        }
    }

    void calming() {

        if (Math.abs(velx) > CALM) {
            velx *= 0.7;
        }
        if (Math.abs(vely) > CALM) {
            vely *= 0.7;
        }
    }

    void reset_vels() {

        velx = initvelx;
        vely = initvely;
    }

    void reset_pos() {
        xpos = initx;
        ypos = inity;
    }

    void counter_getter(int width, int height) {
        orbit = Math.sqrt(Math.pow(xpos - width / 2, 2) + Math.pow(ypos - height / 2, 2));

        double expo = Double.valueOf(String.valueOf(xpos));
        double whypo = Double.valueOf(String.valueOf(ypos));

        if (xpos < width / 2) {
            counter = (Math.acos((whypo - height / 2) / orbit)) * -1;
        }
        if (xpos > width / 2) {
            counter = Math.acos((whypo - height / 2) / orbit);
        }

    }

    public double getVelx() {
        return velx;
    }
    public double getVely() {
        return vely;
    }
    public void setVelx(double vel){velx=vel;}
    public void setVely(double vel){vely=vel;}

    public String getName() {
        return name;
    }
    public double getXpos(){return xpos;}

    public double getYpos(){return ypos;}

    public void finger(float x, float y) {
        velx = -((x - xpos) / 50);
        if (y != -1) {
            vely = -((y - ypos) / 50);
        }
    }

}
