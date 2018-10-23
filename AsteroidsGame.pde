Spaceship c;
Star[] s;

public void setup() {
  size(500,500);
  c = new Spaceship();
  s = new Star[200];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
}

public void draw() {
  background(0);
  for (int i=0; i<s.length; i++) {
    s[i].show();
  }
  c.show();
  c.move();
  if (keyPressed) {
    if (keyCode == UP) {
      c.accelerate(0.1);
    } else if (keyCode == LEFT) {
      c.turn(-5);
    } else if (keyCode == RIGHT) {
      c.turn(5);
    }
  }
}

void keyPressed() {
  if (key == 'h') {
    c.setX((int) (Math.random() * width));
    c.setY((int) (Math.random() * height));
    c.setDirectionX(0);
    c.setDirectionY(0);
    c.setPointDirection(0);
  }
}
