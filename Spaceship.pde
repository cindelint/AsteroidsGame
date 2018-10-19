class Spaceship extends Floater  {
  public Spaceship() {
    corners = 3;
    xCorners = new int[corners];
    yCorners = new int[corners];
    xCorners
    myColor = color(255,0,0);
    myCenterX = width/2;
    myCenterY = height/2;
    myDirectionX = 2;
    myDirectionY = 0;
    myPointDirection = 0;
  }
  public void setX(int x) {
    myCenterX = x;
  }
  public int getX() {
    return myCenterX;
  }
  public void setY(int y) {
    myCenterY = y;
  }
  public int getY() {
    return myCenterY;
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
  public int getPointDirection() {
    return myPointDirection;
  }
}
