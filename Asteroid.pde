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
