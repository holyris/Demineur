/**
 * La classe <code>WindowSave</code> permet de gerer la sauvegarde d'une partie
 * lorsque l'utilisateur clique sur la croix en haut a droite.
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.event.*;


public class WindowSave implements WindowListener {

  /**
   * Tableau de panneau qui va contenir les cases du jeu.
   */
  private Panneau tab[][];

  /**
   * Constructeur destine a la creation des variables privees.
   *
   * @param t tableau  de panneau contenant les cases du jeu
   */
  public WindowSave (Panneau[][] t){
    this.tab = t;
  }


  public void windowOpened(WindowEvent e){}

  /**
   * Fonction permettant d'ajouter un listener sur la fenetre.
   *
   * @param e
   */
  public void windowClosing(WindowEvent e){
    JFrame fenetre = new JFrame();
    SaveGame save = new SaveGame(this.tab, fenetre);
    fenetre.setSize(100, 100);
    fenetre.setLocation(200,100);
    fenetre.setResizable(false);
    fenetre.setLayout(new GridLayout(2,1));
    JButton bouton = new JButton("Sauvegarder et quitter");
    bouton.addActionListener(save);
    fenetre.add(new JLabel("Voulez vous vraiment quitter ?"));
    fenetre.add(bouton);
    fenetre.setVisible(true);
  }

  public void windowClosed(WindowEvent e){}

  public void windowIconified(WindowEvent e){}

  public void windowDeiconified(WindowEvent e){
  }

  public void windowActivated(WindowEvent e){}

  public void windowDeactivated(WindowEvent e){}



}
