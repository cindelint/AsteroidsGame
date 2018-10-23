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
