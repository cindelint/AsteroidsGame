Spaceship ship;
ArrayList<Spaceship> health;
Star[] s;
ArrayList<Asteroid> a;
int m = 0; //hyperspace millis() count

public void setup() {
  size(500,500);
  textAlign(CENTER,CENTER);
  ship = new Spaceship();
  s = new Star[230];
  for (int i=0; i<s.length; i++) {
    s[i] = new Star();
  }
  a = new ArrayList<Asteroid>();
  for (int i=0; i<15; i++) {
    a.add(new Asteroid());
  }
  health = new ArrayList<Spaceship>();
  for (int i=0; i<5; i++) {
    health.add(new Spaceship());
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
  ship.setColor(color(200, (millis()-m-5)/5));
  for (int i=0; i<a.size(); i++) {
    a.get(i).show();
    a.get(i).move();
    if ((abs(ship.getX() - a.get(i).getX()) <= a.get(i).getSize()+6) && (abs(ship.getY() - a.get(i).getY()) <= a.get(i).getSize()+6)) { //asteroid and ship in the same place
      if (millis() - ship.hitTime >= 2000) { //if ship hasn't been recently hit
        ship.hit();
        a.remove(i);
        a.add(new Asteroid());
      }
    }
  }
  if (millis() - ship.hitTime < 1800) {
    if ((int) ((millis() - ship.hitTime)/300) % 2 == 0) {
      //timestamps 0-300, 600-900, 1200-1500. fade out
      ship.setColor(color(200,300-((millis()-ship.hitTime)%300)));
    } else {
      //timestamps 300-600, 900-1200, 1500-1800. fade in
      ship.setColor(color(200,(millis()-ship.hitTime)%300));
    }
  }

  //HEALTH (5)
  for (int i=0; i<health.size(); i++) {
    pushMatrix();
    scale(.5);
    health.get(i).drawShip(10+i*30,20,270);
    popMatrix();
  }
}

public void keyPressed() {
  if (key == 'h') { //hyperspace
    m = millis();
    ship.setX((int) (Math.random() * width));
    ship.setY((int) (Math.random() * height));
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    ship.setPointDirection(0);
  }
}
