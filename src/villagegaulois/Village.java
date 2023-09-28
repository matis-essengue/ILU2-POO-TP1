package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal < 0 || indiceEtal >= etals.length) {
				System.out.println("L'étal n°" + indiceEtal + " n'existe pas.");
				return;
			}
			if (etals[indiceEtal].isEtalOccupe()) {
				System.out.println("L'étal n°" + indiceEtal + " est déjà occupé.");
				return;
			}
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalsOccupe = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsOccupe++;
				}
			}
			Etal[] etalsProduit = new Etal[nbEtalsOccupe];
			int nbEtalsProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsProduit[nbEtalsProduit] = etals[i];
					nbEtalsProduit++;
				}
			}
			return etalsProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder marche = new StringBuilder();
			int nbEtalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					marche.append(etals[i].afficherEtal());
				} else {
					nbEtalsVides++;
				}
			}
			if (nbEtalsVides != 0) {
				marche.append("Il reste " + nbEtalsVides + " non utilisés dans le marché.");
			}

			return marche.toString();
		}
	}

	public String installerVendeur(Gaulois villageois, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(villageois.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal == -1) {
			chaine.append("Il n'y a plus de place sur le marché pour " + villageois + ".\n");
		} else {
			marche.utiliserEtal(indiceEtal, villageois, produit, nbProduit);
			chaine.append(
					"Le vendeur " + villageois.getNom() + " vend des " + produit + " à l'étal n°" + indiceEtal + ".\n");
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length == 0) {
			chaine.append("Il n'y a plus de " + produit + " sur le marché.\n");
		} else {
			chaine.append("les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etals.length; i++) {
				chaine.append("- " + etals[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal == null) {
			System.out.println("Le vendeur " + vendeur.getNom() + " n'est pas sur le marché.");
		}
		return etal;
	}

	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal == null) {
			chaine.append("Le vendeur " + vendeur.getNom() + " n'est pas sur le marché.\n");
		} else {
			chaine.append(etal.libererEtal());
		}
		return chaine.toString();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}