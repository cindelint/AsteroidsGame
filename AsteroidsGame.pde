Spaceship ship;
Star[] s;
Asteroid[] a;

public void setup() {
  size(500,500);
  ship = new Spaceship();
  s = new Star[200];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
  a = new Asteroid[12];
  for (int i=0; i<a.length; i++) {
    a[i] = new Asteroid();
  }
}

public void draw() {
  background(0);
  for (int i=0; i<s.length; i++) {
    s[i].show();
  }
  ship.show();
  ship.move();
  if (keyPressed) {
    if (keyCode == UP) {
      ship.accelerate(0.1);
    } else if (keyCode == LEFT) {
      ship.turn(-5);
    } else if (keyCode == RIGHT) {
      ship.turn(5);
    }
  }
  for (int i=0; i<a.length; i++) {
    a[i].show();
    a[i].move();
    if ((abs(ship.getX() - a[i].getX()) <= a[i].getSize()+6) && (abs(ship.getY() - a[i].getY()) <= a[i].getSize()+6)) {
      ship.getHit();
    }
  }
}

public void keyPressed() {
  if (key == 'h') { //hyperspace
    ship.setX((int) (Math.random() * width));
    ship.setY((int) (Math.random() * height));
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    ship.setPointDirection(0);
  }
}
