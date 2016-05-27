package snake.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Highscoreeintrag {
	

	public static final String NAME_SPALTE = "name",PUNKTE_SPALTE="punktezahl",DATUM_SPALTE="datum",ID_SPALTE="score_id";
	public String name;
	public int punktezahl;
	public int score_id;
	public Date datum;
	public Highscoreeintrag(String name, int punktezahl, int score_id) {
		super();
		this.name = name;
		this.punktezahl = punktezahl;
		this.score_id = score_id;
	}
	public Highscoreeintrag(String name, int punktezahl, int score_id,
			Date datum) {
		super();
		this.name = name;
		this.punktezahl = punktezahl;
		this.score_id = score_id;
		this.datum = datum;
	}
	

	
	public static Highscoreeintrag leiheintrag_erstellen(ResultSet data) throws SQLException{
		try {
			return new Highscoreeintrag(data.getString(NAME_SPALTE),data.getInt(PUNKTE_SPALTE),data.getInt(ID_SPALTE),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.getString(DATUM_SPALTE)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

    /**
     * Methoden
     */
    public static String spielerNameAusDB(int id)
    {
        String name;
        name = Datenbankzugriff.VerbindungGeben().SpielerNameHolen(id);

        return name;
    }
    
    public static int getScore(int id)
    {
    	int platz;
    	platz = Datenbankzugriff.VerbindungGeben().SpielerScoreHolen(id);
    	return platz;
    }
    
    
     
    
    public static void neuerSpieler(String name, int punktezahl)
    {
        Datenbankzugriff.VerbindungGeben().spielerEinfuegen(name,punktezahl);
    }
    
   
        
    public static void SpielerLoeschen(int id)
    {
        Datenbankzugriff.VerbindungGeben().spielerLoeschen (id);
    }
    
    public static  void beenden(int nr)
    {
        Datenbankzugriff.VerbindungGeben().Schliessen();
    }
    
    public static int AnzEintraegeGeben()
    {
    	int anzahl;
    	anzahl = Datenbankzugriff.VerbindungGeben().AnzEintraegeGeben ();

    	System.out.println("Es sind "+anzahl+" Eintraege gespeichert.");
    	return anzahl;
    }
    
   
}
