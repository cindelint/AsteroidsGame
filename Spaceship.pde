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
    design = "lightning";
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

    popMatrix();
  }

  public void hit() {
    hitTime = (int) millis();
    if (health.size() > 0) {
      health.remove(0);
    } else {
      noLoop();
      background(100);
      textSize(20);
      fill(0);
      text("you have died.\n game over", width/2, height/2);
    }
  }
}
