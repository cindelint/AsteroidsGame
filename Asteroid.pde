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
