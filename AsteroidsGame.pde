Spaceship ship;
ArrayList<Spaceship> health;
Star[] s;
ArrayList<Asteroid> a;
int m = 0; //hyperspace millis() count
ArrayList<Bullet> b;

//keys being pressed
boolean up, left, right;

public void setup() {
  size(700,700);
  textAlign(CENTER,CENTER);
  ship = new Spaceship();
  s = new Star[250];
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

  up = false;
  left = false;
  right = false;
}

public void draw() {
  background(0);
  for (int i=0; i<s.length; i++) {
    s[i].show();
  }
  ship.show();
  ship.move();

  if (up) ship.accelerate(0.1);
  if (left) ship.turn(-5);
  if (right) ship.turn(5);

  ship.setColor(color(200, (millis()-m-5)/5));

  //show and move asteroids
  for (int i=0; i<a.size(); i++) {
    a.get(i).show();
    a.get(i).move();
    //if asteroids hit ship, ship loses health and asteroid disappears
    if (dist(ship.getX(), ship.getY(), a.get(i).getX(), a.get(i).getY()) <= a.get(i).getSize()+6) {
      if (millis() - ship.hitTime >= 3300) { //if ship hasn't been recently hit
        ship.hit();
        a.remove(i);
        a.add(new Asteroid());
      }
    }
  }

  //show and move bullets
  if (b != null) {
    for (int i=0; i<b.size(); i++) {
      b.get(i).show();
      b.get(i).move();
    }
  }

  //if bullet hit asteroid, both die
  if (a != null && b != null) {
    for (Bullet bees : b) { //for all bullets
      //if out of bounds, remove bullet
      if (bees.getX() >= width || bees.getX() <= 0 || bees.getY() >= height || bees.getY() <= 0) {
        bees.remove(i);
        break;
      }
      for (int j=0; j<a.size(); j++) { //for all asteroids
        if (dist(a.get(j).getX(), a.get(j).getY(), b.get(i).getX(), b.get(i).getY()) <= a.get(j).getSize()+3) {
          a.remove(j);
          b.remove(i);
          break;
        }
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
  if (keyCode == UP || key == 'w') up = true;
  else if (keyCode == LEFT || key == 'a') left = true;
  else if (keyCode == RIGHT || key == 'd') right = true;
  if (keyCode == DOWN || key == 's') { //hyperspace
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

public void keyReleased() {
  if (keyCode == UP || key == 'w') up = false;
  else if (keyCode == LEFT || key == 'a') left = false;
  else if (keyCode == RIGHT || key == 'd') right = false;
}
