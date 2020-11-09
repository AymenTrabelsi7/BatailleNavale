package BatailleNavale;

public class BatailleNavale {
	public static void main(String[] args) {
		Tableau test = new Tableau();
		System.out.println(test.afficher());
		System.out.println("Ajout de la case B2");
		test.ajouter("B2");
		System.out.println(test.afficher());
	}
}
