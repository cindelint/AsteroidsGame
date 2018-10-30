Spaceship ship;
Star[] s;
Asteroid[] a;

public void setup() {
  size(500,500);
  textAlign(CENTER,CENTER);
  ship = new Spaceship();
  s = new Star[230];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
  a = new Asteroid[15];
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
  ship.hit(false);
  for (int i=0; i<a.length; i++) {
    a[i].show();
    a[i].move();
    if ((abs(ship.getX() - a[i].getX()) <= a[i].getSize()+6) && (abs(ship.getY() - a[i].getY()) <= a[i].getSize()+6)) {
      ship.hit(true);
    }
  }
  if (ship.getHealth() > 0) {
    fill(230,0,12);
    noStroke();
    rect(10,10,ship.getHealth()*2,20);
    noFill();
    stroke(200);
    rect(10,10,200,20);
    fill(255);
    textSize(13);
    text(ship.getHealth() + " / 100", 105, 20);
  } else {
    noLoop();
    background(100);
    textSize(20);
    fill(0);
    text("you have died.\n game over", width/2, height/2);
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
