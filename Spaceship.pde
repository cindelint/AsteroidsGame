class Spaceship extends Floater  {
  private int health;
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
    health = 5;
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
  public int getHealth() {return health;}
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
  }

  //overriding Floater to add the rockets
  public void show () { //Draws the floater at the current position
    pushMatrix();
    //draw the polygon
    drawShip((float) myCenterX, (float) myCenterY, (float) myPointDirection);

    //the rockets
    if (keyPressed && (keyCode == LEFT || keyCode == RIGHT || keyCode == UP)) {
      fill(175,0,0);
      beginShape();
      vertex(-18, 0);
      vertex(-9, -3.5);
      vertex(-9, 3.5);
      endShape();
      fill(221, 110, 0);
      beginShape();
      vertex(-15, 0);
      vertex(-9, -2);
      vertex(-9, 2);
      endShape();
    }

    //"unrotate" and "untranslate" in reverse order
    //rotate(-1*dRadians);
    //translate(-1*(float)myCenterX, -1*(float)myCenterY);
    popMatrix();
  }

  public void hit() {
    health--;
  }
}
