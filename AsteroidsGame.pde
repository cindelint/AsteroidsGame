Spaceship ship;
ArrayList<Spaceship> health;
Star[] s;
ArrayList<Asteroid> a;
int m = 0; //hyperspace millis() count
ArrayList<Bullet> b;

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
  b = new ArrayList<Bullet>();
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
    if (dist(ship.getX(), ship.getY(), a.get(i).getX(), a.get(i).getY()) <= a.get(i).getSize()+6) {
      if (millis() - ship.hitTime >= 3300) { //if ship hasn't been recently hit
        ship.hit();
        a.remove(i);
        a.add(new Asteroid());
      }
    }
  }
  if (b != null) {
    for (int i=0; i<b.size(); i++) {
      b.get(i).show();
      b.get(i).move();
      for (int j=0; j<a.size(); j++) {
        if (a != null && dist(a.get(j).getX(), a.get(j).getY(), b.get(i).getX(), b.get(i).getY()) <= a.get(j).getSize()+3) {
          a.remove(j);
          b.remove(i);
          if (j == a.size() && j != 0) {
            j--;
          }
          if (i == b.size() && i != 0) {
            i--;
          }
        }
      }
      if (b.get(i).getX() > width+10) {
        b.remove(i);
      }
    }
  }

  if (millis() - ship.hitTime < 3000) {
    if ((int) ((millis() - ship.hitTime)/300) % 2 == 0) {
      //timestamps 0-300, 600-900, 1200-1500, 1800-2100, 2400-2700. fade out
      ship.setColor(color(200,300-((millis()-ship.hitTime)%300)));
    } else {
      //timestamps 300-600, 900-1200, 1500-1800, 2100-2400,2700-3000. fade in
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
  if (key == ' ') {
    b.add(new Bullet(ship));
  }
}
