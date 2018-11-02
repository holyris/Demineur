/**
 * La classe <code>GetData</code> permet de recueillir la configuration
 * avec laquelle le joueur souhaite jouer.
 *
 * @version 0.1
 * @author Thebault Alexandre / Roy Adrien
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GetData implements ActionListener {

		/**
		 * JTextField detsine a recueillir le nombre de ligne souhaitee.
		 */
		private JTextField tligne;

		/**
	   * JTextField detsine a recueillir le nombre de colonne souhaitee.
	   */
    private JTextField tcolonne;

		/**
	   * JTextField detsine a recueillir le nombre de bombe souhaitee.
	   */
    private JTextField tbombe;

		/**
	   * Variable qui permet de stocker le nombre de de ligne souhaitee.
	   */
    public static int ligne;

		/**
	   * Variable qui permet de stocker le nombre de de colonne souhaitee.
	   */
    public static int colonne;

		/**
	   * Variable qui permet de stocker le nombre de de bombe souhaitee.
	   */
    public static int bombe;

		/**
	   * Correspond a la fenetre ou se trouve le jeu.
	   */
		public static JFrame jeu;

		/**
	   * Correspond au panneau qui contient tous les elements de la fenetre de choix.
	   */
		private JPanel conteneur;

		/**
		 * Fenetre de choix.
		 */
    private Fenetre fenetre;

		/**
	   * Variable qui signal si il y a des erreurs.
		 * Initialisee a 0 car en arrivant sur la fenetre de choix
		 * il ne peut pas y avoir d'erreur.
	   */
		private int err = 0;

		/**
	   * Seconde variable qui signal si il y a des erreurs.
		 * Initialisee a 0 car en arrivant sur la fenetre de choix
		 * il ne peut pas y avoir d'erreur.
	   */
		private int err2 = 0;

		/**
	   * Constructeur destine a la creation des variables de la classe.
	   *
	   * @param l zone contenant le nombre de ligne
	   * @param c zone contenant le nombre de colonne
		 * @param b zone contenant le nombre de bombe
		 * @param window fenetre de choix
		 * @param panneau panneau contenant chaque element de la fenetre de choix
	   */
	public GetData (JTextField l, JTextField c, JTextField b, Fenetre window, JPanel panneau){
		this.tligne = l;
		this.tcolonne = c;
		this.tbombe = b;
		this.fenetre = window;
		this.conteneur = panneau;
	}

	/**
	 * Fonction lie a 2 boutons qui se declenchera quand l'un des deux sera actionne.
	 * Si c'est le bouton valider alors la fonction verifiera si les donnees sont valides
	 * et lancera le jeu.
	 * Si c'est le bouton annuler alors l'utilisateur sera renvoye au menu principal.
	 */
	public void actionPerformed(ActionEvent evenement) {
		String composant = evenement.getActionCommand();

		if (composant.equals("Valider")){
			if(tligne.getText().equals("") || tcolonne.getText().equals("") || tbombe.getText().equals("")) {
				JFrame erreur = new JFrame("Erreur");
				erreur.setSize(250, 100);
				erreur.setLocation(100,100);
				erreur.setResizable(false);
				erreur.add(new JLabel("Veuillez remplir tous les champs."), BorderLayout.CENTER);
				erreur.setVisible(true);
		    } else {
						try {
			        GetData.ligne = Integer.parseInt(tligne.getText());
			       	GetData.colonne = Integer.parseInt(tcolonne.getText());
			        GetData.bombe = Integer.parseInt(tbombe.getText());
							if (GetData.ligne < 4 || GetData.colonne < 4 || GetData.ligne > 30 || GetData.colonne > 30){
								JFrame erreur = new JFrame("Erreur");
								erreur.setSize(500, 100);
								erreur.setLocation(100,100);
								erreur.setResizable(false);
								erreur.add(new JLabel("Le nombre de ligne et de colonne doit etre superieur a 3 et inferieur a 31."), BorderLayout.CENTER);
								erreur.setVisible(true);
								err = 1;
							} else {
								err = 0;
							}
							if (GetData.bombe < 0 || GetData.bombe > (GetData.ligne*GetData.colonne) - 1){
								JFrame erreur1 = new JFrame("Erreur");
								erreur1.setSize(550, 100);
								erreur1.setLocation(60,120);
								erreur1.setResizable(false);
								erreur1.add(new JLabel("Il doit y avoir au moins une case de libre ! Donc vous pouvez mettre au maximum "+(ligne*colonne - 1)+" bombes."), BorderLayout.CENTER);
								erreur1.setVisible(true);
								err2 = 1;
							} else {
								err2 = 0;
							}
						} catch (NumberFormatException e) {
							JFrame erreur = new JFrame("Erreur");
							erreur.setSize(250, 100);
							erreur.setLocation(100,100);
							erreur.setResizable(false);
							erreur.add(new JLabel("Rentrez uniquement des entiers."), BorderLayout.CENTER);
							erreur.setVisible(true);
							err = 1;
						}
					if(err == 0  && err2 == 0){
		        this.fenetre.setVisible(false);
						this.fenetre.dispose();
						GetData.jeu = new JFrame("Demineur");
						GetData.jeu.setSize(28*GetData.colonne, 28*GetData.ligne);
						GetData.jeu.setMinimumSize(new Dimension(28*GetData.colonne, 28*GetData.ligne));

				    GetData.jeu.setLocation(0,0);
				    GetData.jeu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        		this.fenetre.afficherJeu(GetData.jeu);
					}
				}
   		} else if (composant == "Annuler"){
				Fenetre.card.previous(conteneur);
			}
	}
}
