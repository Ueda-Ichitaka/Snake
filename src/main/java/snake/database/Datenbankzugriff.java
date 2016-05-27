package snake.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Datenbankzugriff
{           
    private String treiber = "sun.jdbc.odbc.JdbcOdbcDriver";    // Treiber
    String pfad = "jdbc:sqlite:src\\main\\resources\\snake\\database\\scores.sqlite"; // Pfad zur Datenbank
    private String user = "";  
    private String pWort = ""; 
    // Das Verbindungsobjekt
    private Connection conn;
    //Hält die Referenz auf das einzige existierende Objekt der Klasse DATENBANKVERBINDUNG
    private static Datenbankzugriff verbindung = new Datenbankzugriff ();

    /**
     * Der Konstruktor öffnet die Datenbankverbindung
     */
    private Datenbankzugriff()
    {
        // Datenbankverbindung öffnen       
        // Darf nur ein mal ausgeführt werden, daher wird das Muster Singleton verwendet.
        
        // ODBC bzw. JDBC Treiber laden
        try         
        {   
            
           
        	Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
        }
        catch(Exception e)
        {               
            System.out.println("Start_Fehler beim Laden des JDBC-Treibers "+ e);
            System. exit (-1);
        }
        
        //Öffnen der Datenbank       
        try
        {  
        	String filename = "scores.sqlite";
        	conn = DriverManager.getConnection(pfad, "", "");	//"jdbc:odbc:MS Access Database;DBQ=" + filename
        }
        catch(Exception e)
        {   
            System.out.println("Start_Fehler bei Datenbankzugriff\n"+ e); 
            e.printStackTrace();
            System. exit (-1);
        }
    }

    //TODO method to add entry to highscore 
    
    
    /**
     * Beendet die Verbindung zur Datenbank, alle Resourcen werden freigegeben.
     */
    public void Schliessen ()
    {
        try
        {              
            conn. close ();
            verbindung = null;
        }
        catch(Exception e)
        {   
            System.out.println ("Fehler beim Beenden der Datenbankverbindung\n"+ e); 
        }
    }
    /**
     * @param start Gibt an ab welchem Platz das Ergebnis angezeigt werden soll (Wichtig: Nullbasiert, d.h. der erste hat den Index 0
     * @param anzahl Gibt an, wieviele Einträge maximal abgerufen werden sollen (bei 0 werden alle abgerufen)
     * @return Eine Liste, in der die entsprechenden Einträge in absteigender Reihenfolge nach Punktezahlen sortiert sind
     */
    public List<Highscoreeintrag> Highscoreeintraegeholen (int start,int anzahl){
    	Statement s=null;
    	ResultSet result=null;
    	try{
    		s =conn.createStatement();
    		String limitstring = (anzahl>0)?(" LIMIT " + String.valueOf(start)+ "," + String.valueOf(anzahl)):"";
    		result=s.executeQuery("SELECT * FROM highscore ORDER BY punktezahl DESC"+limitstring+";");
    		List<Highscoreeintrag> list = new LinkedList<Highscoreeintrag>();
    		while(result.next()){
    			list.add(Highscoreeintrag.leiheintrag_erstellen(result));
    		}
    		
    		return list;
    	} catch (SQLException e) {
    		e.printStackTrace();
			return new LinkedList<Highscoreeintrag>();
			
		}finally{
    		
    	}
    }
    
    /**
     * @return Liste aller Highscoreeinträge in der Datenbank
     */
    public List<Highscoreeintrag> Highscoreeintraegeholen (){
    	return Highscoreeintraegeholen(0,0);
    }
    public String SpielerNameHolen (int id)
    {      
        ResultSet sqlResult;        // Ergebnistabelle
        Statement stmt;             // Datenbankabfrage
        String name;               
        name = "";            
        try
        {
            stmt = conn. createStatement ();
            sqlResult = stmt. executeQuery ("SELECT name FROM Highscore "+ 
                                             "WHERE score_id =" + id);                      
            // Wenn Ergebnistabelle einen Datensatz enthält, wird Cursor darauf gesetzt.
            if (sqlResult. next ())
            {
                name = sqlResult.getString ("name");
            }
            sqlResult. close ();
            stmt. close ();  
        }
        catch(Exception e)
        {   
            System.out.println ("SpielerNameHolen: Fehler bei SQL Abfrage\n" + e);
        }
        return name;
    }
    
    public int SpielerScoreHolen (int id)
    {      
        ResultSet sqlResult;        // Ergebnistabelle
        Statement stmt;             // Datenbankabfrage
        int score = 0;                           
        try
        {
            stmt = conn. createStatement ();
            sqlResult = stmt. executeQuery ("SELECT punktezahl FROM Highscore "+ 
                                             "WHERE score_id =" + id);                      
            // Wenn Ergebnistabelle einen Datensatz enthält, wird Cursor darauf gesetzt.
            if (sqlResult. next ())
            {
                score = sqlResult.getInt ("punktezahl"); 
            }
            sqlResult. close ();
            stmt. close ();  
        }
        catch(Exception e)
        {   
            System.out.println ("SpielerNameHolen: Fehler bei SQL Abfrage\n" + e);
        }
        return score;
    }
    
   
    /**
     * Liste aller Spieler ausgeben
     * @return Feld mit dem Platz, Namen und gespielte Spiele.
     */
   
     
    /**
     * Neuen Spieler anlegen
     **/
    public void spielerEinfuegen (String name, int punktezahl)
    {    
   	 	 
         Statement stmt;
         try
         {                                
             stmt = conn. createStatement ();
             stmt. executeUpdate ("INSERT INTO highscore (name, punktezahl) "+
                                  " VALUES ('"+ name +"', "+ punktezahl +" );");
             stmt. close ();
         }
         catch (Exception e)
         {   
             System.out.println ("SpielerEinfuegen: Fehler bei SQL Abfrage\n"+ e);
         }
    }

    /**
     * Ermittelt die Anzahl der Spieler in der Datenbank
     **/
       
    
    public int AnzEintraegeGeben()
    {
        ResultSet sqlResult;
        Statement stmt;
        int anzahl = 0;
        
        try
        {
            stmt = conn. createStatement ();
            sqlResult = stmt. executeQuery ("SELECT COUNT(*) FROM Highscore");
            if (sqlResult. next ())
            {
                anzahl = sqlResult. getInt (1);
                
            }
            sqlResult. close ();
            stmt. close ();
            return anzahl;
        }
        catch (Exception e)
        {   
            System.out.println ("AnzEintraegeGeben: Fehler bei SQL Abfrage \n"+ e);
            return 0;
        }
    }
                
    /**
     * Spieler löschen
     **/
    public void spielerLoeschen (int id)
    {
        Statement stmt;
        try
        {                                
            stmt = conn. createStatement ();
            stmt. executeUpdate ("DELETE FROM highscore WHERE score_id = " + id); 
            stmt. close ();
        }
        catch (Exception e)
        {   
            System.out.println ("SpielerLoeschen: Fehler bei SQL Abfrage\n"+ e);
        }
    }
    
    /**
     * Gibt eine Referenz auf das Verbindungsobjekt zurück.
     * @return Referenz auf das Verbindungsobjekt
     **/
    public static Datenbankzugriff VerbindungGeben ()
    {
        return verbindung;
    }
    
}
