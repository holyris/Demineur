/**
 * La classe <code>SwitchFenetre</code> permet de gerer changement de fenetre.
 * Cette classe gere le passage du menu de depart au menu de configuration du jeu
 * et du menu de fin de jeu a celui de depart.
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchFenetre implements ActionListener {

  /**
   * Variable correspondant au panneau contenant les boutons
   */
  private JPanel conteneur;

  /**
   * Variable correspondant a la fenetre actuelle
   */
  private Fenetre fenetre;

  /**
   * Variable corrspondant a la fenetre futur
   */
  private JFrame frame;

  /**
   * Constructeur destine a la creation des variables privees.
   *
   * @param panneau panneau contenant les boutons du menu
   * @param window fenetre actuelle
   */
  public SwitchFenetre (JPanel panneau, Fenetre window){
    this.conteneur=panneau;
    this.fenetre=window;
  }

  /**
   * Deuxieme constructeur destine a la creation des variables privees.
   *
   * @param window2 fenetre qui va contenir le jeu sauvegarde
   */
  public SwitchFenetre (JFrame window2){

    this.frame=window2;
  }

  /**
   * Fonction permettant de gerer tous les boutons.
   */
  public void actionPerformed(ActionEvent observateur) {
    String s = observateur.getActionCommand();


    if (s=="Jouer"){
      try {
      Fenetre.card.next(this.conteneur);
      } catch (IllegalArgumentException e) {
        /* Erreur incomprehensible, catched pour éviter un message dans le terminal */
      }

    }  else if (s.equals("Reprendre la partie")){
      Panneau.fin = false;
      this.fenetre.setVisible(false);
      this.fenetre.dispose();
      if (GetData.jeu != null){
        GetData.jeu.setVisible(false);
        GetData.jeu.dispose();
      }
      GetData.jeu = new JFrame("Demineur");
      try {
        FileInputStream fis = new FileInputStream("donnee.txt");
        DataInputStream dis = new DataInputStream(fis);
        GetData.ligne = dis.read();
        GetData.colonne = dis.read();

        GetData.jeu.setSize(28*GetData.ligne, 28*GetData.colonne);
        GetData.jeu.setMinimumSize(new Dimension(28*GetData.colonne, 28*GetData.ligne));
        fis.close();
        dis.close();
      } catch (IOException e){
        System.err.println("Le fichier permettant la sauvegarde a probablement été supprimé.");

      }
      GetData.jeu.setLocation(0,0);
      GetData.jeu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      Fenetre fenetre = new Fenetre("Demineur");

      fenetre.reprendreJeu(GetData.jeu);

    } else if (s=="Quitter"){
      System.exit(0);

    } else if (s=="Menu"){
      Panneau.fin = false;
      this.frame.setVisible(false);
      this.frame.dispose();
      Fenetre fenetre = new Fenetre("Demineur");
      GetData.jeu.setVisible(false);
      GetData.jeu.dispose();
      fenetre.setVisible(true);

    } else if (s=="Rejouer"){
      Panneau.fin = false;
      this.frame.setVisible(false);
      this.frame.dispose();
      GetData.jeu.setVisible(false);
      GetData.jeu.dispose();
      GetData.jeu = new JFrame("Demineur");
      GetData.jeu.setSize(28*GetData.colonne, 28*GetData.ligne);
      GetData.jeu.setLocation(0,0);
      GetData.jeu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      Fenetre fenetre = new Fenetre("Demineur");
      fenetre.afficherJeu(GetData.jeu);
    }
  }
}
