class Spaceship extends Floater  {
  private boolean hit;
  private int health;
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
    health = 100;
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
  public int getHealth() {return health;}

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

  public void hit(boolean b) {
    if (b) {
      myColor = color(255,0,0,200);
      health--;
    } else {
      myColor = color(200);
    }
  }
}
