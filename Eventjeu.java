/**
 * La classe <code>Eventjeu</code> est utilisee pour gerer la souris.
 * Lorsque l'utilisateur appuie sur un bouton de la souris,
 * des actions se produisent dans le programme grace a cette classe.
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Eventjeu implements MouseListener{

  /**
   * Variable permettant de stocker le nombre de clique droit effectue.
   * Initialisee a 0 car au debut du jeu aucun clique droit ne peut avoir eu lieu.
   */
  private int rightcounter = 0;

  /**
   * Variable qui permet de stocker des panneau dans un tableau.
   */
  private Panneau[][] tabpanneau;

  /**
   * Variables permettant de se situer dans le tableau tabpanneau.
   */
  private int i,j;

  /**
   * Constructeur destine a la creation des variables privees.
   *
   * @param tabpanneau tableau de panneau
   * @param i ligne
   * @param j colonne
   */
  public Eventjeu(Panneau[][] tabpanneau,int i, int j){
    this.tabpanneau=tabpanneau;
    this.i=i;
    this.j=j;
  }

  public void mouseClicked(MouseEvent evenement) {

  }
  public void mouseEntered(MouseEvent evenement){

  }

  public void mouseExited(MouseEvent evenement){

  }

  /**
   * La fonction mousePressed declenche des foncions dans la classe Panneau.
   * Si le bouton 1 est actionne alors la fonction leftClic de la classe Panneau est declenchee.
   * Si c'est le bouton 3 alors c'est la fonction rightClic qui est declenchee.
   */

  public void mousePressed(MouseEvent evenement){

    if (Panneau.fin == false){
      if(evenement.getButton()==MouseEvent.BUTTON3){
        this.tabpanneau[this.i][this.j].rightClic();
      }
      else if (evenement.getButton()==MouseEvent.BUTTON1){
        this.tabpanneau[this.i][this.j].leftClic(this.tabpanneau, this.i, this.j);
      }
    }
  }

  public void mouseReleased(MouseEvent evenement){

  }

}
