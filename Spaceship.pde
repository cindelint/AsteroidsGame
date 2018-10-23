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
