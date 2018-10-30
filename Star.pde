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
