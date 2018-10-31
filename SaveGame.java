/**
 * La classe <code>SaveGame</code> permet de gerer la sauvegarde d'une partie
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;


public class SaveGame implements ActionListener {

  /**
   * Tableau de panneau qui va contenir les cases du jeu.
   */
  private Panneau tabpanneau[][];

  /**
   * JFrame qui va permettre de recuperer la fenetre actuelle afin de la detruire
   * et d'afficher une nouvelle fenetre contenant la partie sauvegardee.
   */
  private JFrame frame;

  /**
   * Constructeur destine a la creation des variables privees.
   *
   * @param t tableau  de panneau contenant les cases du jeu
   * @param frame fenetre actuelle
   */
	public SaveGame (Panneau[][] t, JFrame frame){
    this.tabpanneau = t;
    this.frame = frame;
	}

  /**
   * Fonction qui lorsqu'elle est declenchee (en appuyant sur le bouton a
   * laquelle elle est assignee), extrait les donnees du fichier de sauvegarde,
   * recreer l'interface de jeu avec les donnees extraites, ferme l'ancienne fenetre
   * et enfin ouvre une nouvelle fenetre avec l'interface de jeu recree.
   */
	public void actionPerformed(ActionEvent evenement) {
		String composant = evenement.getActionCommand();

		if (composant.equals("Sauvegarder et quitter")){
      if (Panneau.fin == false){
        try {
          FileOutputStream fos = new FileOutputStream("donnee.txt");
          DataOutputStream dos = new DataOutputStream(fos);
          dos.write(GetData.ligne);
          dos.write(GetData.colonne);
          dos.write(GetData.bombe);
          dos.write(Fenetre.bomberestante);
          for(int i = 0; i<GetData.ligne; i++){
            for(int j = 0; j<GetData.colonne; j++)
            try {
              dos.write(this.tabpanneau[i][j].rightcounter);
              dos.write(this.tabpanneau[i][j].bombcounter);
              dos.writeBoolean(this.tabpanneau[i][j].check);
              dos.writeBoolean(this.tabpanneau[i][j].mine);
              dos.write(this.tabpanneau[i][j].dessin);
            } catch (IOException e){
              System.err.println("Le fichier permettant la sauvegarde a probablement été supprimé.");
            }
          }
          fos.close();
          dos.close();
        } catch (IOException e) {
          System.err.println("Le fichier permettant la sauvegarde a probablement été supprimé.");
        }
        this.frame.setVisible(false);
        this.frame.dispose();
        Fenetre fenetre = new Fenetre("Demineur");
        GetData.jeu.setVisible(false);
        GetData.jeu.dispose();
        fenetre.setVisible(true);
      }
    }
  }
}
