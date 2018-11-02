/**
 * La classe <code>Fenetre</code> permet d'afficher le contenu des
 * fenetres dans ces dernieres.
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.io.*;
import java.util.Random;

public class Fenetre extends JFrame{

  /**
   * Declaration des JButton du menu principal.
   */
  public static JButton jouer = new JButton("Jouer");
  public static JButton reprendre = new JButton("Reprendre la partie");
  public static JButton quitter = new JButton("Quitter");

  /**
   * Variable permettant de stocker le nombre de bombe qu'il reste
   * selon l'utilisateur.
   */
  public static int bomberestante;

  /**
   * Declaration du JLabel affichant le nombre de bombe qu'il reste
   * selon l'utilisateur.
   */
  public static JLabel pbomberestante;

  /**
   * Declaration des JPanel contenant les diffenrents elements des
   * differents menus.
   */
  private JPanel conteneur;
  private JPanel menu;
  private JPanel choix;

  /**
   * Gestionaire de menu
   */
  public static CardLayout card;

  /**
   * Constructeur permettant de declencher les differentes fonctions
   * qui gerent les differents menus.
   *
   * @param s Nom de la fenetre
   */
  public Fenetre (String s){
    super(s);

    this.setSize(600, 600);
    this.setLocation(50,50);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.conteneur = new JPanel();
    this.menu = new JPanel();
    this.choix = new JPanel();
    Fenetre.card = new CardLayout();
    this.conteneur.setLayout(Fenetre.card);
    this.afficherMenu();
    this.afficherChoix();
    this.getContentPane().add(this.conteneur);

  }

  /**
   * Fonction permettant d'afficher le menu principal.
   */
  public void afficherMenu(){
    GridLayout gestionnaire = new GridLayout(3,1);
    SwitchFenetre event = new SwitchFenetre(this.conteneur, this);
    jouer.addActionListener(event);
    quitter.addActionListener(event);
    reprendre.addActionListener(event);
    this.menu.setLayout(gestionnaire);
    this.menu.add(jouer);
    this.menu.add(reprendre);
    this.menu.add(quitter);
    this.conteneur.add(this.menu);

  }

  /**
   * Fonction permettant d'afficher le menu de choix.
   */
  public void afficherChoix(){
    GridLayout gestionnaire2 = new GridLayout(8,2);
    JLabel qligne = new JLabel("Nombre de ligne : ");
    JLabel qcolonne = new JLabel("Nombre de colonne : ");
    JLabel qbombe = new JLabel("Nombre de bombe : ");
    JTextField tligne = new JTextField();
    JTextField tcolonne = new JTextField();
    JTextField tbombe = new JTextField();
    JButton check = new JButton("Valider");
    JButton annuler = new JButton("Annuler");
    GetData observateur = new GetData(tligne, tcolonne, tbombe, this, this.conteneur);
    check.addActionListener(observateur);
    annuler.addActionListener(observateur);
    this.choix.setLayout(gestionnaire2);
    this.choix.add(new JLabel());
    this.choix.add(new JLabel());
    this.choix.add(qligne);
    this.choix.add(tligne);
    this.choix.add(new JLabel());
    this.choix.add(new JLabel());
    this.choix.add(qcolonne);
    this.choix.add(tcolonne);
    this.choix.add(new JLabel());
    this.choix.add(new JLabel());
    this.choix.add(qbombe);
    this.choix.add(tbombe);
    this.choix.add(new JLabel());
    this.choix.add(new JLabel());
    this.choix.add(annuler);
    this.choix.add(check);

    this.conteneur.add(this.choix);
  }

  /**
   * Fonction permettant d'afficher le jeu.
   *
   * @param fenetre
   */
  public void afficherJeu(JFrame fenetre){
    int i=0, j=0;
    Random rand = new Random();
    JPanel panneau = new JPanel();
    GridLayout gestionnaire3 = new GridLayout(GetData.ligne,GetData.colonne);
    panneau.setLayout(gestionnaire3);
    Panneau tab[][] = new Panneau[GetData.ligne][GetData.colonne];
    WindowSave savewindow = new WindowSave(tab);
    fenetre.addWindowListener(savewindow);
    int randligne = rand.nextInt(GetData.ligne);
    int randcolonne = rand.nextInt(GetData.colonne);
    Fenetre.bomberestante = GetData.bombe;
    Fenetre.pbomberestante = new JLabel(Integer.toString(GetData.bombe)+"/"+Integer.toString(GetData.bombe));
    Fenetre.pbomberestante.setHorizontalAlignment(JLabel.CENTER);


    for(i=0; i<GetData.ligne; i++){
      for (j=0; j<GetData.colonne; j++) {
        tab[i][j]=new Panneau();
        Eventjeu event = new Eventjeu(tab, i, j);
        tab[i][j].addMouseListener(event);
      }
    }

    for(i=0; i<GetData.bombe;){
      if(tab[randligne][randcolonne].mine!=true){
        tab[randligne][randcolonne].mine=true;
        i++;
      }
      randligne=rand.nextInt(GetData.ligne);
      randcolonne=rand.nextInt(GetData.colonne);
    }

    for(i = 0; i<GetData.ligne; i++){
      for(j = 0; j<GetData.colonne; j++){
        panneau.add(tab[i][j]);
      }
    }


    fenetre.add(panneau, BorderLayout.CENTER);
    JPanel panneauhaut = new JPanel();
    panneauhaut.setLayout(new GridLayout(1,2));
    JButton save = new JButton("Sauvegarder et quitter");
    SaveGame eventsave = new SaveGame(tab, fenetre);
    save.addActionListener(eventsave);
    panneauhaut.add(Fenetre.pbomberestante);
    panneauhaut.add(save);
    fenetre.add(panneauhaut, BorderLayout.NORTH);
    fenetre.setVisible(true);


  }

  /**
   * Fonction permettant de reprendre le jeu a partir d'une sauvegarde et
   * de reafficher le jeu.
   *
   * @param fenetre
   */
  public void reprendreJeu(JFrame fenetre){
    int i=0, j=0;
    JPanel panneau = new JPanel();

    try{
      FileInputStream fis = new FileInputStream("donnee.txt");
      DataInputStream dis = new DataInputStream(fis);
      GetData.ligne = dis.read();
      GetData.colonne = dis.read();
      GetData.bombe = dis.read();
      Fenetre.bomberestante = dis.read();

      GridLayout gestionnaire3 = new GridLayout(GetData.ligne,GetData.colonne);
      panneau.setLayout(gestionnaire3);
      Panneau tab[][] = new Panneau[GetData.ligne][GetData.colonne];
      WindowSave savewindow = new WindowSave(tab);
      fenetre.addWindowListener(savewindow);

      for(i=0; i<GetData.ligne; i++){
        for (j=0; j<GetData.colonne; j++) {
          tab[i][j]=new Panneau();
          Eventjeu event = new Eventjeu(tab, i, j);
          tab[i][j].addMouseListener(event);
        }
      }

      for(i = 0; i<GetData.ligne; i++){
        for(j = 0; j<GetData.colonne; j++)
        try {
          tab[i][j].rightcounter = dis.read();
          tab[i][j].bombcounter= dis.read();
          tab[i][j].check= dis.readBoolean();
          tab[i][j].mine= dis.readBoolean();
          tab[i][j].dessin= dis.read();
        } catch (IOException e){
          System.err.println("Le fichier permettant la sauvegarde a probablement été supprimé.");
        }
      }
      for(i = 0; i<GetData.ligne; i++){
        for(j = 0; j<GetData.colonne; j++){
          panneau.add(tab[i][j]);
        }
      }

      Fenetre.pbomberestante = new JLabel(Integer.toString(Fenetre.bomberestante)+"/"+Integer.toString(GetData.bombe));
      Fenetre.pbomberestante.setHorizontalAlignment(JLabel.CENTER);

      fenetre.add(panneau, BorderLayout.CENTER);
      JPanel panneauhaut = new JPanel();
      panneauhaut.setLayout(new GridLayout(1,2));
      JButton save = new JButton("Sauvegarder et quitter");
      SaveGame eventsave = new SaveGame(tab, fenetre);
      save.addActionListener(eventsave);
      panneauhaut.add(Fenetre.pbomberestante);
      panneauhaut.add(save);
      fenetre.add(panneauhaut, BorderLayout.NORTH);
      fenetre.setVisible(true);

    } catch (IOException e){
      System.err.println("Le fichier permettant la sauvegarde a probablement été supprimé.");
    }
  }
}
