package BatailleNavale;

public class BatailleNavale {
	public static void main /*run*/(String[] args){
		Tableau test = new Tableau();
		System.out.println("Initialisation");
		System.out.println(test.afficher());
		System.out.println("Ajout de la case B2");
		test.ajouter("B2");
		System.out.println(test.afficher());
		System.out.println("Suppression de la case B2");
		test.supprimer("B2");
		System.out.println(test.afficher());
		test.ajouter("B2");
		int a = test.attaque("B2");
		int b = test.attaque("A1");
		System.out.println("Attaque B2 : " + a + "\nAttaque A1 : " + b);
		System.out.println("Ajout d'un véhicule de C3 à C5");
		test.ajouterVehicule("C3", "C5");
		System.out.println(test.afficher());
	}
}
