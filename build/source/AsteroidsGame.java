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

Spaceship c;
Star[] s;

public void setup() {
  
  c = new Spaceship();
  s = new Star[200];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
}

public void draw() {
  background(0);
  for (int i=0; i<s.length; i++) {
    s[i].show();
  }
  c.show();
  c.move();
  if (keyPressed) {
    if (keyCode == UP) {
      c.accelerate(0.1f);
    } else if (keyCode == LEFT) {
      c.turn(-5);
    } else if (keyCode == RIGHT) {
      c.turn(5);
    }
  }
}

public void keyPressed() {
  if (key == 'h') {
    c.setX((int) (Math.random() * width));
    c.setY((int) (Math.random() * height));
    c.setDirectionX(0);
    c.setDirectionY(0);
    c.setPointDirection(0);
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
    double dRadians = myPointDirection*(Math.PI/180);
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
}
class Star //note that this class does NOT extend Floater
{
  private float myX, myY, mySize;
  private int myColor;
  Star() {
    myX = (float) (Math.random() * width);
    myY = (float) (Math.random() * height);
    mySize = (float) (Math.random() * 4 + 1);
    myColor = color((int) (Math.random()*150+50), (int) (Math.random()*150+50), (int) (Math.random()*150+50));
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
