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
