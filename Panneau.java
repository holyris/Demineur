/**
 * La classe <code>Panneau</code> permet de gerer toutes les
 * fonctionnalitees durant une partie.
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.lang.*;

public class Panneau extends JPanel {

  /**
   * Variable permettant de stocker le nombre de clique droit effectue.
   * Initialisee a 0 car au debut du jeu aucun clique droit ne peut avoir eu lieu.
   */
  protected int rightcounter = 0;

  /**
   * Variable permettant de stocker le nombre de bombe que le joueur pense avoir trouve.
   * Initialisee a 0 car au debut du jeu le joueur ne peut avoir trouve aucune bombe.
   */
  protected int bombcounter = 0;

  /**
   * Booleen qui permet de savoir si une case est decouverte ou non.
   */
  protected boolean check = false;

  /**
   * Booleen qui permet de savoir si une case est minee ou non.
   */
  public boolean mine = false;

  /**
   * Booleen qui permet de savoir si le jeu est termine ou non (victoire ou defaite).
   */
  public static boolean fin = false;

  /**
   * Serie de declaration d'image qui vont etre utilisee lors d'une partie.
   */
  private Image cache;
  private Image etoile;
  private Image interrogation;
  private Image case0;
  private Image case1;
  private Image case2;
  private Image case3;
  private Image case4;
  private Image case5;
  private Image case6;
  private Image case7;
  private Image case8;
  private Image case_bomb;
  private Image case_bomb_clicked;
  private Image case_bomb_marked;

  /**
   * Variable permettant de choisir l'image qui va etre afficher sur un panneau definit.
   * Initialisee a 9 car le dessin 9 correpond au "cache" pour masquee ce que contiennent les cases.
   */
  public int dessin=9;

  /**
   * Constructeur permettant d'associer les variables aux fichiers images.
   */
  public Panneau (){
    super();
    cache = Toolkit.getDefaultToolkit().getImage("./Images/case_cache.png");
    case_bomb = Toolkit.getDefaultToolkit().getImage("./Images/case_bombe.png");
    case_bomb_clicked = Toolkit.getDefaultToolkit().getImage("./Images/case_bombe_clicked.png");
    case_bomb_marked = Toolkit.getDefaultToolkit().getImage("./Images/case_bombe_marked.png");
    etoile = Toolkit.getDefaultToolkit().getImage("./Images/case_etoile.png");
    interrogation = Toolkit.getDefaultToolkit().getImage("./Images/case_interrogation.png");
    case0 = Toolkit.getDefaultToolkit().getImage("./Images/case_check0.png");
    case1 = Toolkit.getDefaultToolkit().getImage("./Images/case_check1.png");
    case2 = Toolkit.getDefaultToolkit().getImage("./Images/case_check2.png");
    case3 = Toolkit.getDefaultToolkit().getImage("./Images/case_check3.png");
    case4 = Toolkit.getDefaultToolkit().getImage("./Images/case_check4.png");
    case5 = Toolkit.getDefaultToolkit().getImage("./Images/case_check5.png");
    case6 = Toolkit.getDefaultToolkit().getImage("./Images/case_check6.png");
    case7 = Toolkit.getDefaultToolkit().getImage("./Images/case_check7.png");
    case8 = Toolkit.getDefaultToolkit().getImage("./Images/case_check8.png");
  }

  /**
   * Fonction permettant de gerer le clique droit.
   */
  public void rightClic(){
    if(this.check==false){
      if(this.rightcounter==2) this.rightcounter=0;
      else rightcounter++;

      if(this.rightcounter==0){
        this.dessin=9;
      } else if (this.rightcounter==1){
        this.dessin=10;
        Fenetre.bomberestante--;
      } else if (this.rightcounter==2){
        this.dessin=11;
        Fenetre.bomberestante++;
      }
      this.repaint();
      Fenetre.pbomberestante.setText(Integer.toString(Fenetre.bomberestante)+"/"+Integer.toString(GetData.bombe));
    }
  }

  /**
   * Fonction permettant de gerer le clique gauche.
   *
   * @param tabpanneau tableau de panneau
   * @param i ligne
   * @param j colonne
   */
  public void leftClic(Panneau[][] tabpanneau, int i, int j){
    if(this.rightcounter==0){
      this.checkExplosion(tabpanneau, i , j);

      this.chainReaction(tabpanneau, i, j);

      this.checkWin(tabpanneau);
    }
  }

  /**
   * Fonction permettant de verifier si les 8 cases autour d'une case donnee sont minees.
   * Si une case de ces cases est minees alors les cases adjacentes a ces mines
   * auront des numeros correspondant au nombre de bombe dans les 8 cases autour d'elles.
   *
   * @param tabpanneau tableau de panneau
   * @param i ligne
   * @param j colonne
   */
  private void checkAround(Panneau[][] tabpanneau, int i, int j){
    if(tabpanneau[i][j].check==false){
      for(int i2 = i-1; i2<i+2;i2++){
        for(int j2 = j-1; j2<j+2; j2++){
          if(i2>=0 && i2<=GetData.ligne -1 && j2>=0 && j2<=GetData.colonne -1){
            if(tabpanneau[i2][j2].mine==true){
              tabpanneau[i][j].bombcounter++;
            }

          }
        }
      }
    }
    if (tabpanneau[i][j].bombcounter==0) this.dessin=0;
    else if (tabpanneau[i][j].bombcounter==1) this.dessin=1;
    else if (tabpanneau[i][j].bombcounter==2) this.dessin=2;
    else if (tabpanneau[i][j].bombcounter==3) this.dessin=3;
    else if (tabpanneau[i][j].bombcounter==4) this.dessin=4;
    else if (tabpanneau[i][j].bombcounter==5) this.dessin=5;
    else if (tabpanneau[i][j].bombcounter==6) this.dessin=6;
    else if (tabpanneau[i][j].bombcounter==7) this.dessin=7;
    else if (tabpanneau[i][j].bombcounter==8) this.dessin=8;

    if(tabpanneau[i][j].rightcounter == 1){
      Fenetre.bomberestante++;
    }
    check=true;
    Fenetre.pbomberestante.setText(Integer.toString(Fenetre.bomberestante)+"/"+Integer.toString(GetData.bombe));
    this.repaint();
  }

  /**
   * Fonction qui rehitere la fonction checkAround tant qu'aucune bombe n'est trouvee.
   *
   * @param tabpanneau tableau de panneau
   * @param i ligne
   * @param j colonne
   */
  public void chainReaction(Panneau[][] tabpanneau, int i, int j){
    if(tabpanneau[i][j].mine==false){
      tabpanneau[i][j].checkAround(tabpanneau, i, j);
      if(tabpanneau[i][j].bombcounter==0){
        for(int i2 = i - 1; i2< i + 2; i2++){
          for(int j2 = j - 1; j2< j + 2; j2++){
            if(i2>=0 && i2<=GetData.ligne -1 && j2>=0 && j2<=GetData.colonne -1){
              if(tabpanneau[i2][j2].check==false){
                tabpanneau[i2][j2].chainReaction(tabpanneau, i2, j2);

              }
            }
          }
        }
      }
    }
  }

  /**
   * Fonction permettant de verifier si la case cliquee contient une bombe.
   * Si c'est le cas le jeu s'arrete et une fenetre contenant un menu pour rejouer
   * ou pour retourner au menu principal apparait.
   *
   * @param tabpanneau tableau de panneau
   * @param i ligne
   * @param j colonne
   */
  public void checkExplosion(Panneau[][] tabpanneau, int x, int y){

     if (this.mine==true) {
      this.fin = true;
      for(int i=0; i<GetData.ligne;i++){
        for(int j=0; j<GetData.colonne; j++){
          if(tabpanneau[i][j].mine==true){
            tabpanneau[i][j].dessin=12;
            tabpanneau[i][j].repaint();
          }
          if (tabpanneau[i][j].mine==true && tabpanneau[i][j].rightcounter == 1) {
            tabpanneau[i][j].dessin=14;
            tabpanneau[i][j].repaint();
          }
        }
      }
      tabpanneau[x][y].dessin=13;
      tabpanneau[x][y].repaint();
      JFrame defaite = new JFrame("Demineur");
      defaite.setSize(400, 200);
      defaite.setLocation(100,100);
      defaite.setResizable(false);
      defaite.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      SwitchFenetre event = new SwitchFenetre(defaite);
      JLabel message = new JLabel("Defaite !");
      JButton rejouer = new JButton("Rejouer");
      JButton menu = new JButton("Menu");
      menu.addActionListener(event);
      rejouer.addActionListener(event);
      GridLayout gestionnaire = new GridLayout(3,1);
      defaite.setLayout(gestionnaire);
      message.setHorizontalAlignment(JLabel.CENTER);
      defaite.add(message);
      defaite.add(menu);
      defaite.add(rejouer);
      defaite.setVisible(true);
    }
  }

  /**
   * Fonction permettant de verifier si l'utilisateur a gagner ou non.
   * Si c'est le cas le jeu s'arrete et une fenetre contenant un menu pour rejouer
   * ou pour retourner au menu principal apparait.
   *
   * @param tabpanneau tableau de panneau
   */
  public void checkWin(Panneau[][] tabpanneau){
    int checkcounter=0;
    for(int i = 0; i<GetData.ligne; i++){
      for(int j =0; j<GetData.colonne; j++){
        if(tabpanneau[i][j].check==true) checkcounter++;
      }
    }

    if(checkcounter==GetData.ligne*GetData.colonne-GetData.bombe){

      this.fin = true;
      JFrame victoire = new JFrame("Demineur");
      victoire.setSize(400, 200);
      victoire.setLocation(100,100);
      victoire.setResizable(false);
      victoire.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      SwitchFenetre event2 = new SwitchFenetre(victoire);

      JLabel message = new JLabel("Victoire !");
      JButton rejouer = new JButton("Rejouer");
      JButton menu = new JButton("Menu");
      menu.addActionListener(event2);
      rejouer.addActionListener(event2);
      GridLayout gestionnaire = new GridLayout(3,1);
      victoire.setLayout(gestionnaire);
      message.setHorizontalAlignment(JLabel.CENTER);
      victoire.add(message);
      victoire.add(menu);
      victoire.add(rejouer);

      victoire.setVisible(true);

    }
  }

  /**
   * Fonction qui permet de donner une image au pinceau en fonction de la variable
   * dessin.
   *
   * @param g
   */
  public void paintComponent(Graphics g) {
        Graphics secondPinceau = g.create();

        int height = this.getSize().height;
        int width = this.getSize().width;

        if (this.isOpaque()) {
          secondPinceau.setColor(this.getBackground());
          secondPinceau.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        if(this.dessin == 0) secondPinceau.drawImage(case0, 0, 0, width,height, this);
        else if (this.dessin == 1) secondPinceau.drawImage(case1, 0, 0, width,height, this);
        else if (this.dessin == 2) secondPinceau.drawImage(case2, 0, 0, width,height, this);
        else if (this.dessin == 3) secondPinceau.drawImage(case3, 0, 0, width,height, this);
        else if (this.dessin == 4) secondPinceau.drawImage(case4, 0, 0, width,height, this);
        else if (this.dessin == 5) secondPinceau.drawImage(case5, 0, 0, width,height, this);
        else if (this.dessin == 6) secondPinceau.drawImage(case6, 0, 0, width,height, this);
        else if (this.dessin == 7) secondPinceau.drawImage(case7, 0, 0, width,height, this);
        else if (this.dessin == 8) secondPinceau.drawImage(case8, 0, 0, width,height, this);
        else if (this.dessin == 9) secondPinceau.drawImage(cache, 0, 0, width,height, this);
        else if (this.dessin == 10) secondPinceau.drawImage(etoile, 0, 0, width,height, this);
        else if (this.dessin == 11) secondPinceau.drawImage(interrogation, 0, 0, width,height, this);
        else if (this.dessin == 12) secondPinceau.drawImage(case_bomb, 0, 0, width,height, this);
        else if (this.dessin == 13) secondPinceau.drawImage(case_bomb_clicked, 0, 0, width,height, this);
        else if (this.dessin == 14) secondPinceau.drawImage(case_bomb_marked, 0, 0, width,height, this);
    }
}
