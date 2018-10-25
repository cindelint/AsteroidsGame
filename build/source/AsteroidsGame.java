import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

Spaceship ship;
Star[] s;
Asteroid[] a;

public void setup() {
  
  ship = new Spaceship();
  s = new Star[200];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
  a = new Asteroid[12];
  for (int i=0; i<a.length; i++) {
    a[i] = new Asteroid();
  }
}

public void draw() {
  background(0);
  for (int i=0; i<s.length; i++) {
    s[i].show();
  }
  ship.show();
  ship.move();
  if (keyPressed) {
    if (keyCode == UP) {
      ship.accelerate(0.1f);
    } else if (keyCode == LEFT) {
      ship.turn(-5);
    } else if (keyCode == RIGHT) {
      ship.turn(5);
    }
  }
  for (int i=0; i<a.length; i++) {
    a[i].show();
    a[i].move();
    if ((abs(ship.getX() - a[i].getX()) <= a[i].getSize()+6) && (abs(ship.getY() - a[i].getY()) <= a[i].getSize()+6)) {
      ship.getHit();
    }
  }
}

public void keyPressed() {
  if (key == 'h') { //hyperspace
    ship.setX((int) (Math.random() * width));
    ship.setY((int) (Math.random() * height));
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    ship.setPointDirection(0);
  }
}
class Asteroid extends Floater {
  private int rotSpeed;
  private int mySize;
  //private float[] cratersX, cratersY, cratersW;
  //private int numOfCraters;
  Asteroid() {
    corners = (int) (Math.random() * 12) + 10;
    xCorners = new int[corners];
    yCorners = new int[corners];
    mySize = (int) (Math.random() * 10 + 10);
    for (int i=0; i<corners; i++) {
      xCorners[i] = (int) (Math.cos(i * 2 * Math.PI / corners) * (Math.random() * 10 + mySize));
      yCorners[i] = (int) (Math.sin(i * 2 * Math.PI / corners) * (Math.random() * 10 + mySize));
    }
    myColor = color(100);
    myCenterX = Math.random() * width;
    myCenterY = Math.random() * height;
    myDirectionX = Math.random() * 6 - 3;
    myDirectionY = Math.random() * 6 - 3;
    myPointDirection = Math.random() * 360;
    rotSpeed = (int) (Math.random() * 5 + 3);
  }
  public void setX(int x) {
    myCenterX = x;
  }
  public int getX() {
    return (int) myCenterX;
  }
  public void setY(int y) {
    myCenterY = y;
  }
  public int getY() {
    return (int) myCenterY;
  }
  public void setDirectionX(double x) {
    myDirectionX = x;
  }
  public double getDirectionX() {
    return myDirectionX;
  }
  public void setDirectionY(double y) {
    myDirectionY = y;
  }
  public double getDirectionY() {
    return myDirectionY;
  }
  public void setPointDirection(int degrees) {
    myPointDirection = degrees;
  }
  public double getPointDirection() {
    return myPointDirection;
  }
  public int getSize() {
    return mySize;
  }

  //overriding to add spin
  public void move () { //move the floater in the current direction of travel
    //change the x and y coordinates by myDirectionX and myDirectionY
    myCenterX += myDirectionX;
    myCenterY += myDirectionY;

    //wrap around screen
    if (myCenterX > width + mySize*2) {
      myCenterX = -mySize*2;
    } else if (myCenterX < -mySize*2) {
      myCenterX = width + mySize*2;
    }
    if (myCenterY > height + mySize*2) {
      myCenterY = -mySize*2;
    } else if (myCenterY < -mySize*2) {
      myCenterY = height + mySize*2;
    }

    turn(rotSpeed);
  }
}
abstract class Floater //Do NOT modify the Floater class! Make changes in the Spaceship class
{
  protected int corners;  //the number of corners, a triangular floater has 3
  protected int[] xCorners;
  protected int[] yCorners;
  protected int myColor;
  protected double myCenterX, myCenterY; //holds center coordinates
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel
  protected double myPointDirection; //holds current direction the ship is pointing in degrees
  abstract public void setX(int x);
  abstract public int getX();
  abstract public void setY(int y);
  abstract public int getY();
  abstract public void setDirectionX(double x);
  abstract public double getDirectionX();
  abstract public void setDirectionY(double y);
  abstract public double getDirectionY();
  abstract public void setPointDirection(int degrees);
  abstract public double getPointDirection();

  //Accelerates the floater in the direction it is pointing (myPointDirection)
  public void accelerate (double dAmount)
  {
    //convert the current direction the floater is pointing to radians
    double dRadians =myPointDirection*(Math.PI/180);
    //change coordinates of direction of travel
    myDirectionX += ((dAmount) * Math.cos(dRadians));
    myDirectionY += ((dAmount) * Math.sin(dRadians));
  }
  public void turn (int nDegreesOfRotation)
  {
    //rotates the floater by a given number of degrees
    myPointDirection+=nDegreesOfRotation;
  }
  public void move ()   //move the floater in the current direction of travel
  {
    //change the x and y coordinates by myDirectionX and myDirectionY
    myCenterX += myDirectionX;
    myCenterY += myDirectionY;

    //wrap around screen
    if(myCenterX >width)
    {
      myCenterX = 0;
    }
    else if (myCenterX<0)
    {
      myCenterX = width;
    }
    if(myCenterY >height)
    {
      myCenterY = 0;
    }

    else if (myCenterY < 0)
    {
      myCenterY = height;
    }
  }
  public void show ()  //Draws the floater at the current position
  {
    fill(myColor);
    stroke(myColor);

    //translate the (x,y) center of the ship to the correct position
    translate((float)myCenterX, (float)myCenterY);

    //convert degrees to radians for rotate()
    float dRadians = (float)(myPointDirection*(Math.PI/180));

    //rotate so that the polygon will be drawn in the correct direction
    rotate(dRadians);

    //draw the polygon
    beginShape();
    for (int nI = 0; nI < corners; nI++)
    {
      vertex(xCorners[nI], yCorners[nI]);
    }
    endShape(CLOSE);

    //"unrotate" and "untranslate" in reverse order
    rotate(-1*dRadians);
    translate(-1*(float)myCenterX, -1*(float)myCenterY);
  }
} 
class Spaceship extends Floater  {
  public Spaceship() {
    corners = 5;
    xCorners = new int[corners];
    yCorners = new int[corners];
    xCorners[0] = 16;
    yCorners[0] = 0;
    xCorners[1] = -12;
    yCorners[1] = -10;
    xCorners[2] = -8;
    yCorners[2] = -5;
    xCorners[3] = -8;
    yCorners[3] = 5;
    xCorners[4] = -12;
    yCorners[4] = 10;
    myColor = color(200);
    myCenterX = width/2;
    myCenterY = height/2;
    myDirectionX = 0;
    myDirectionY = 0;
    myPointDirection = 0;
  }
  public void setX(int x) {
    myCenterX = x;
  }
  public int getX() {
    return (int) myCenterX;
  }
  public void setY(int y) {
    myCenterY = y;
  }
  public int getY() {
    return (int) myCenterY;
  }
  public void setDirectionX(double x) {
    myDirectionX = x;
  }
  public double getDirectionX() {
    return myDirectionX;
  }
  public void setDirectionY(double y) {
    myDirectionY = y;
  }
  public double getDirectionY() {
    return myDirectionY;
  }
  public void setPointDirection(int degrees) {
    myPointDirection = degrees;
  }
  public double getPointDirection() {
    return myPointDirection;
  }

  //overriding Floater to add the rockets
  public void show () { //Draws the floater at the current position
    fill(myColor);
    stroke(myColor);

    //translate the (x,y) center of the ship to the correct position
    translate((float)myCenterX, (float)myCenterY);

    //convert degrees to radians for rotate()
    float dRadians = (float)(myPointDirection*(Math.PI/180));

    //rotate so that the polygon will be drawn in the correct direction
    rotate(dRadians);

    //draw the polygon
    beginShape();
    for (int nI = 0; nI < corners; nI++) {
      vertex(xCorners[nI], yCorners[nI]);
    }
    endShape(CLOSE);

    //the rockets
    if (keyPressed) {
      fill(255,0,0);
      beginShape();
      vertex(-15, 0);
      vertex(-8, -3);
      vertex(-8, 3);
      endShape();
    }

    //"unrotate" and "untranslate" in reverse order
    rotate(-1*dRadians);
    translate(-1*(float)myCenterX, -1*(float)myCenterY);
  }

  public void getHit() {
    myColor = color(255,0,0);
    show();
    myColor = color(200);
    show();
  }
}
class Star //note that this class does NOT extend Floater
{
  private float myX, myY, mySize, myDirection;
  private int myColor;
  Star() {
    myX = (float) (Math.random() * width);
    myY = (float) (Math.random() * height);
    mySize = (float) (Math.random() * 3 + 2);
    myColor = color((int) (Math.random()*100+100), (int) (Math.random()*100+100), (int) (Math.random()*100+100), (int) (Math.random() * 50 + 150));
    myDirection = (float) (Math.random() * 2*PI);
  }
  private void show() {
    fill(myColor);
    noStroke();
    ellipse(myX, myY, mySize, mySize);
  }
}
  public void settings() {  size(500,500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
