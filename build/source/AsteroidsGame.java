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
ArrayList<Spaceship> health;
Star[] s;
ArrayList<Asteroid> a;
int m = 0; //hyperspace millis() count
ArrayList<Bullet> b;
ArrayList<PowerUp> p;

//keys being pressed
boolean up, left, right;

public void setup() {
  
  textAlign(CENTER,CENTER);
  ship = new Spaceship();
  s = new Star[250];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
  a = new ArrayList<Asteroid>();
  for (int i=0; i<15; i++) {
    a.add(new Asteroid());
  }
  health = new ArrayList<Spaceship>();
  for (int i=0; i<5; i++) {
    health.add(new Spaceship());
  }
  b = new ArrayList<Bullet>();

  p = new ArrayList<PowerUp>();

  up = false;
  left = false;
  right = false;
}

public void draw() {
  background(0);
  for (int i=0; i<s.length; i++) {
    s[i].show();
  }
  ship.show();
  ship.move();

  if (up) ship.accelerate(0.1f);
  if (left) ship.turn(-5);
  if (right) ship.turn(5);

  ship.setColor(color(200, (millis()-m-5)/5));

  //show and move asteroids
  for (int i=0; i<a.size(); i++) {
    a.get(i).show();
    a.get(i).move();
    //if asteroids hit ship, ship loses health and asteroid disappears
    if (dist(ship.getX(), ship.getY(), a.get(i).getX(), a.get(i).getY()) <= a.get(i).getSize()+6) {
      if (millis() - ship.hitTime >= 3300) { //if ship hasn't been recently hit
        a.remove(i);
        a.add(new Asteroid());
        ship.hit();
      }
    }
  }

  //show and move bullets
  if (b != null) {
    for (int i=0; i<b.size(); i++) {
      b.get(i).show();
      b.get(i).move();
    }
  }

  //if bullet hit asteroid, both die
  if (a != null && b != null) {
    for (int i=0; i<b.size(); i++) { //for all bullets
      //if out of bounds, remove bullet
      if (b.get(i).getX() >= width || b.get(i).getX() <= 0 || b.get(i).getY() >= height || b.get(i).getY() <= 0) {
        b.remove(i);
        break;
      }
      for (int j=0; j<a.size(); j++) { //for all asteroids
        if (dist(a.get(j).getX(), a.get(j).getY(), b.get(i).getX(), b.get(i).getY()) <= a.get(j).getSize()+3) {
          a.remove(j);
          b.remove(i);
          break;
        }
      }
    }
  }

  //recovery time
  if (millis() - ship.hitTime < 3000) {
    if ((int) ((millis() - ship.hitTime)/300) % 2 == 0) {
      //timestamps 0-300, 600-900, 1200-1500, 1800-2100, 2400-2700. fade out
      ship.setColor(color(ship.getColor(),300-((millis()-ship.hitTime)%300)));
    } else {
      //timestamps 300-600, 900-1200, 1500-1800, 2100-2400,2700-3000. fade in
      ship.setColor(color(ship.getColor(),(millis()-ship.hitTime)%300));
    }
  }

  //HEALTH (5)
  for (int i=0; i<health.size(); i++) {
    pushMatrix();
    scale(.5f);
    health.get(i).drawShip(10+i*30,20,270);
    popMatrix();
  }

  //powerups
  if (Math.random() < .005f && p.size() < 4) {
    p.add(new PowerUp());
  }
  for (int i=0; i<p.size(); i++) {
    if (dist(ship.getX(),ship.getY(),p.get(i).getX(),p.get(i).getY()) < 18) {
      p.get(i).setColor(color(0));
      if (keyPressed && (keyCode == DOWN || key == 's')) {
        p.get(i).pickUp();
        p.remove(i);
        break;
      }
    } else {
      p.get(i).setColor(color(175, 22, 22));
    }
    p.get(i).show();
  }
}

public void keyPressed() {
  if (keyCode == UP || key == 'w') up = true;
  else if (keyCode == LEFT || key == 'a') left = true;
  else if (keyCode == RIGHT || key == 'd') right = true;
  if (key == 'q') { //hyperspace
    m = millis();
    ship.setX((int) (Math.random() * width));
    ship.setY((int) (Math.random() * height));
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    ship.setPointDirection(0);
  }
  if (key == ' ') {
    b.add(new Bullet(ship));
  }
}

public void keyReleased() {
  if (keyCode == UP || key == 'w') up = false;
  else if (keyCode == LEFT || key == 'a') left = false;
  else if (keyCode == RIGHT || key == 'd') right = false;
}
class Asteroid extends Floater {
  private int rotSpeed;
  private int mySize;
  Asteroid() {
    newAsteroid();
    myColor = color(100);
    int randX = (int) (Math.random() * 2);
    int randY = (int) (Math.random() * 2);
    myCenterX = (Math.random() * width/5) + randX*(4*width/5);
    myCenterY = (Math.random() * width/5) + randY*(4*width/5);
  }
  private void newAsteroid() {
    corners = (int) (Math.random() * 12) + 10;
    xCorners = new int[corners];
    yCorners = new int[corners];
    mySize = (int) (Math.random() * 10 + 10);
    for (int i=0; i<corners; i++) {
      xCorners[i] = (int) (Math.cos(i * 2 * Math.PI / corners) * (Math.random() * 10 + mySize));
      yCorners[i] = (int) (Math.sin(i * 2 * Math.PI / corners) * (Math.random() * 10 + mySize));
    }
    myDirectionX = Math.random() * 7 - 3;
    myDirectionY = Math.random() * 7 - 3;
    myPointDirection = Math.random() * 360;
    rotSpeed = (int) (Math.random() * 15 - 7);
  }

  public void setX(int x) {myCenterX = x;}
  public int getX() {return (int) myCenterX;}
  public void setY(int y) {myCenterY = y;}
  public int getY() {return (int) myCenterY;}
  public void setDirectionX(double x) {myDirectionX = x;}
  public double getDirectionX() {return myDirectionX;}
  public void setDirectionY(double y) {myDirectionY = y;}
  public double getDirectionY() {return myDirectionY;}
  public void setPointDirection(int degrees) {myPointDirection = degrees;}
  public double getPointDirection() {return myPointDirection;}
  public int getSize() {return mySize;}

  //overriding to add spin
  public void move () { //move the floater in the current direction of travel
    super.move();
    turn(rotSpeed);
  }
}
class Bullet extends Floater {
  public Bullet (Spaceship theShip) {
    myCenterX = theShip.getX();
    myCenterY = theShip.getY();
    myPointDirection = theShip.getPointDirection();
    double dRadians = myPointDirection*(Math.PI/180);
    myDirectionX = 5*Math.cos(dRadians) + theShip.getDirectionX();
    myDirectionY = 5*Math.sin(dRadians) + theShip.getDirectionY();
    myColor = color(230, (int) (Math.random() * 90 + 170), (int) (Math.random() * 10 + 50));
  }
  public void show() {
    noStroke();
    fill(myColor);
    ellipse((float) myCenterX, (float) myCenterY, 6, 6);
  }
  public void setX(int x) {myCenterX = x;}
  public int getX() {return (int) myCenterX;}
  public void setY(int y) {myCenterY = y;}
  public int getY() {return (int) myCenterY;}
  public void setDirectionX(double x) {myDirectionX = x;}
  public double getDirectionX() {return myDirectionX;}
  public void setDirectionY(double y) {myDirectionY = y;}
  public double getDirectionY() {return myDirectionY;}
  public void setPointDirection(int degrees) {myPointDirection = degrees;}
  public double getPointDirection() {return myPointDirection;}
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
class PowerUp {
  private int myX, myY;
  private String[] typeList = new String[] {"laser","explosion","design","spaceshipColor","gainHealth"};
  private int type;
  private int myColor;
  public PowerUp() {
    myX = (int) (Math.random() * width);
    myY = (int) (Math.random() * height);
    type = (int) (Math.random() * typeList.length);
    myColor = color(175, 22, 22);
  }
  public int getX() {return myX;}
  public int getY() {return myY;}
  public void setColor(int x) {myColor = x;}
  public void show() {
    fill(myColor);
    stroke(myColor, 20);
    rectMode(CENTER);
    rect(myX, myY, 20, 20);
  }
  public void pickUp() {
    switch(typeList[type]) {
      case "laser":
        System.out.println("laser");
        break;
      case "explosion":
        System.out.println("explosion");
        break;
      case "design":
        ship.setDesign("lightning");
        break;
      case "spaceshipColor":
        ship.setColor(color(175, 22, 22));
        println("color");
        break;
      case "gainHealth":
        health.add(new Spaceship());
        break;
    }
  }
}
class Spaceship extends Floater  {
  private int hitTime;
  private String design;
  public Spaceship() {
    corners = 5;
    xCorners = new int[corners];
    yCorners = new int[corners];
    xCorners[0] = 18;
    yCorners[0] = 0;
    xCorners[1] = -14;
    yCorners[1] = -12;
    xCorners[2] = -9;
    yCorners[2] = -7;
    xCorners[3] = -9;
    yCorners[3] = 7;
    xCorners[4] = -14;
    yCorners[4] = 12;
    myColor = color(200);
    myCenterX = width/2;
    myCenterY = height/2;
    myDirectionX = 0;
    myDirectionY = 0;
    myPointDirection = 0;
    hitTime = -5000;
    design = " ";
  }
  public void setX(int x) {myCenterX = x;}
  public int getX() {return (int) myCenterX;}
  public void setY(int y) {myCenterY = y;}
  public int getY() {return (int) myCenterY;}
  public void setDirectionX(double x) {myDirectionX = x;}
  public double getDirectionX() {return myDirectionX;}
  public void setDirectionY(double y) {myDirectionY = y;}
  public double getDirectionY() {return myDirectionY;}
  public void setPointDirection(int degrees) {myPointDirection = degrees;}
  public double getPointDirection() {return myPointDirection;}
  public void setColor(int c) {myColor = c;}
  public int getColor() {return myColor;}
  public void setDesign(String s) {design = s;}

  private void drawDesign(int designColor) {
    fill(designColor);
    noStroke();
    beginShape();
    if (design == "lightning") {
      vertex(12,1);
      vertex(-1,-4);
      vertex(0,1);
      vertex(-8,-1);
      vertex(3,4);
      vertex(2,-1);
    }
    endShape();
  }
  public void drawShip(float x, float y, float direction) {
    fill(myColor);
    noStroke();

    //translate the (x,y) center of the ship to the correct position
    translate((float)x, (float)y);

    //convert degrees to radians for rotate()
    float dRadians = (float)(direction*(Math.PI/180));

    //rotate so that the polygon will be drawn in the correct direction
    rotate(dRadians);
    beginShape();
    for (int nI = 0; nI < corners; nI++) {
      vertex(xCorners[nI], yCorners[nI]);
    }
    endShape(CLOSE);

    drawDesign(color(100));
  }

  //overriding Floater to add the rockets
  public void show() { //Draws the floater at the current position
    pushMatrix();
    //draw the polygon
    drawShip((float) myCenterX, (float) myCenterY, (float) myPointDirection);

    //the rockets
    if (keyPressed && (keyCode == LEFT || keyCode == RIGHT || keyCode == UP)) {
      fill(175,0,0);
      beginShape();
      vertex(-18, 0);
      vertex(-9, -3.5f);
      vertex(-9, 3.5f);
      endShape();
      fill(221, 110, 0);
      beginShape();
      vertex(-15, 0);
      vertex(-9, -2);
      vertex(-9, 2);
      endShape();
    }

    popMatrix();
  }

  public void hit() {
    hitTime = (int) millis();
    if (health.size() > 0) {
      health.remove(0);
    } else {
      noLoop();
      for (int i=p.size()-1; i>=0; i--)  p.remove(i);
      for (int i=a.size()-1; i>=0; i--)  a.remove(i);
      for (int i=b.size()-1; i>=0; i--)  b.remove(i);
      background(70);
      textSize(20);
      fill(0);
      text("you have died.\n game over", width/2, height/2);
    }
  }
}
class Star //note that this class does NOT extend Floater
{
  private float myX, myY, mySize, myDirection;
  private int myColor, myType;
  Star() {
    myX = (float) (Math.random() * width);
    myY = (float) (Math.random() * height);
    mySize = (float) (Math.random() * 3 + 2);
    myColor = color((int) (Math.random()*80+120), (int) (Math.random()*80+120), (int) (Math.random()*80+120), (int) (Math.random() * 50 + 150));
    myDirection = (float) (Math.random() * 2*PI);
  }
  private void show() {
    fill(myColor);
    noStroke();
    ellipse(myX, myY, mySize, mySize);
  }
}
  public void settings() {  size(700,700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
