/**
 * 
 */
package gsb.modele.dao;

import gsb.modele.Medicament;
import gsb.modele.Offrir;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Guillaume
 *
 */
public class OffrirDao {
	public static Offrir rechercher(String depotLegal){
		Offrir uneOffre=null;
		ResultSet reqSelection = ConnexionMySql.execReqSelection("select * from OFFRIR where DEPOTLEGAL ='"+depotLegal+"'");
		try {
			if (reqSelection.next()) {
				Medicament unMedicament = MedicamentDao.rechercher(reqSelection.getString(1));
				uneOffre = new Offrir(unMedicament, reqSelection.getInt(2));	
			};
			}
		catch(Exception e) {
			System.out.println("erreur reqSelection.next() pour la requ�te - select * from OFFRIR where DEPOTLEGAl ='"+depotLegal+"'");
			e.printStackTrace();
			}
		ConnexionMySql.fermerConnexionBd();
		return uneOffre;
	}
	
	public static ArrayList<Offrir> retournerCollectionDesOffres(){
		ArrayList<Offrir> collectionDesOffres = new ArrayList<Offrir>();
		ResultSet reqSelection = ConnexionMySql.execReqSelection("select DEPOTLEGAL from OFFRIR");
		try{
		while (reqSelection.next()) {
			String depotLegal = reqSelection.getString(1);
		    collectionDesOffres.add(OffrirDao.rechercher(depotLegal));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur retournerCollectionDesMedicaments()");
		}
		return collectionDesOffres;
	}
	
	public static HashMap<String,Offrir> retournerDictionnaireDesOffres(){
		HashMap<String, Offrir> diccoDesOffres = new HashMap<String, Offrir>();
		ResultSet reqSelection = ConnexionMySql.execReqSelection("select DEPOTLEGAL from OFFRIR");
		try{
		while (reqSelection.next()) {
			String depotLegal = reqSelection.getString(1);
		    diccoDesOffres.put(depotLegal, OffrirDao.rechercher(depotLegal));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur retournerDiccoDesOffres()");
		}
		return diccoDesOffres;
	}
	
	public static int creer(Offrir uneOffre){
		int result = 0;
		String requeteInsertion;
		Medicament unMedicament= uneOffre.getUnMedicament();
		int qteOfferte = uneOffre.getQteOfferte();
		requeteInsertion = "insert into OFFRIR values('"+unMedicament+"','"+qteOfferte+"'";
		try{
			result = ConnexionMySql.execReqMaj(requeteInsertion);
		}
		catch(Exception e){
			System.out.println("echec insertion Offrir");
		}
		ConnexionMySql.fermerConnexionBd();
		return result;
	}
	
	public static int supprimer(String depotLegal){
		String requeteSuppression = "delete from OFFRIR where DEPOTLEGAL='"+depotLegal+"'";
		int result = ConnexionMySql.execReqMaj(requeteSuppression);
		ConnexionMySql.fermerConnexionBd();
		return result;	
	}
	
	public static int modifier(Offrir uneOffre){
		String requeteModification;
		Medicament unMedicament= uneOffre.getUnMedicament();
		int qteOfferte = uneOffre.getQteOfferte();
		requeteModification = "update OFFRIR set QTEOFFERTE='"+qteOfferte+"' where DEPOTLEGAL='"+unMedicament+"'";
		int result = ConnexionMySql.execReqMaj(requeteModification);
		ConnexionMySql.fermerConnexionBd();
		return result;
	}

}
