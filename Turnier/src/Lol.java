import java.io.*;
/**
 * Als erstes die Methode leseDaten ausführen zum einlesen der gewünschten Spielstaerken
 * Danach die Methode ergebnisse auführen um eine Empfehlung zu erhalten
 * Es ist mit der jeweiligen Methode ebenfalls möglich einzelne Tuniervarianten auszuführen
 */
class Tobis_Tunier
{
    //Arrays
    public double[] staerke;
    public int[] KO;
    public int[] Siege;

    //Variablen
    public int gewinner;
    public int anzahlSpieler;
    public int max;
    public int anzahlDurchlaeufe;
    public int debug;
    /**
     * debug auf 1 setzen um Zwischenergebnisse und weiteres zu erhalten sonst 0 setzen
     */
    public Tobis_Tunier(int debug)
    {
    }

    public static void main(String[] args){
        Tobis_Tunier t = new Tobis_Tunier(1);
        try{
            t.leseDaten("spielstaerken3.txt");
            t.ergebnisse(1000000f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Hier den Dateinamen mit dem Suffix Dateityp als String eingeben z.B. "spielstaeken1.txt"
     */
    public  void leseDaten(String dateiName) throws IOException{
        try (BufferedReader br = new BufferedReader ( new FileReader(dateiName))){
            String anzahl;
            anzahl = br.readLine();
            anzahlSpieler = Integer.parseInt(anzahl);
            staerke = new double[anzahlSpieler + 1]; //Stelle 0 wird nicht benutzt
            for(int reihe = 1; reihe <= anzahlSpieler ; reihe++){
                staerke[reihe] = (double)Integer.parseInt(br.readLine()); 
                 //System.out.println(String.valueOf(staerke[reihe]));
            }
        }
        catch (NumberFormatException | IOException e) {
             //System.out.println ("Fehler beim Einlesen der Datei" + dateiName);
            throw e;
        }
    }

    public void duell(int duellEins, int duellZwei)
    {
        double staerkeGesamt = staerke[duellEins] + staerke[duellZwei]; 
        double x = staerke[duellEins]/staerkeGesamt; 
        double r = Math.random(); 
        if(r < x){
            gewinner = duellEins;
        }
        else{
            gewinner = duellZwei;
        }
    }

    public void KO ()
    {
        KO = new int[anzahlSpieler+1];
        int anzahlPaare;
        anzahlPaare = anzahlSpieler / 2;
        int y = anzahlPaare; //laufvariable
        max = anzahlSpieler - 1;
        int spielerEins = 1;
        int spielerZwei = 2;
        if(debug == 1){
             //System.out.println("anzahlSpieler"+" "+"="+" "+anzahlSpieler);
             //System.out.println("anzahlPaare"+" "+"="+" "+anzahlPaare);
        }
        int spielerEinsAlt = 0;
        int spielerZweiAlt = 0;
        for(int j = 1; j < anzahlSpieler + 1;j++){
            KO[j] = j;
        }
        for(int j = 1; j <= anzahlPaare && y != 0; j++){
            if(debug == 1){
                 //System.out.println(java.util.Arrays.toString(KO));
            }
            for(int i = 1; i <=  y ;i++){
                if(spielerEins <= max && spielerZwei <= anzahlSpieler){
                    while((KO[spielerEins] == 0 || KO[spielerZwei] == 0 ) || (spielerEins == spielerZweiAlt) && (spielerZwei <= anzahlSpieler)){
                        if(KO[spielerEins] == 0 && spielerEins < anzahlSpieler){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("KO[spielerEins] == 0 && spielerEins < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerEins == spielerEinsAlt){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerEinsAlt"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerEins == spielerZweiAlt){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerZweiAlt"+" "+"="+" "+"true");
                            }
                        }
                        if(spielerEins == spielerZwei && spielerZwei < anzahlSpieler){
                            spielerZwei++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerZwei && spielerZwei < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(KO[spielerZwei] == 0 && spielerZwei < anzahlSpieler){
                            spielerZwei++;
                            if(debug == 1){
                                 //System.out.println("KO[spielerZwei] == 0 && spielerZwei < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerZwei == spielerZweiAlt && spielerZwei < anzahlSpieler){
                            spielerZwei++;
                            if(debug == 1){
                                 //System.out.println("spielerZwei == spielerZweiAlt && spielerZwei < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerEins == spielerZweiAlt && spielerEins < max){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerZweiAlt && spielerEins < max"+" "+"="+" "+"true");
                            }
                        }
                        if(debug == 1){
                             //System.out.println("spielerEins"+" "+"="+ " " + spielerEins +" "+"spielerZwei"+" "+"="+" " + spielerZwei);
                        }
                    }
                    duell(spielerEins,spielerZwei);
                    if(debug == 1){
                         //System.out.println(spielerEins + " " + "kämpft" + " " + "gegen" + " " + spielerZwei);
                         //System.out.println("gewinner" + " " + "ist" +" "+ "Spieler" + " "+ gewinner);
                    }
                    if(gewinner == spielerEins){
                        KO[spielerZwei] = 0;
                    }
                    else{
                        KO[spielerEins] = 0;
                    }
                    spielerEinsAlt = spielerEins;
                    spielerZweiAlt = spielerZwei;
                    if(spielerEins <= max-2 && spielerZwei <= max-1){
                        spielerEins = spielerEins + 2;
                        spielerZwei = spielerZwei + 2;
                    }
                }
                if(debug == 1){
                     //System.out.println(java.util.Arrays.toString(KO));
                }
            }
            if(debug == 1){
                 //System.out.println("DURCHGANG ENDE");
                 //System.out.println(java.util.Arrays.toString(KO));
            }
            spielerEins = 1;
            spielerZwei = 2;
            y = y/2;    
        }
        for(int i = 1; i < anzahlSpieler + 1;i++){
            if(KO[i] != 0){
                gewinner = i;
            }
            i++;
        }
         //System.out.println("KO:"+" "+"Gewinner"+" "+"ist"+" "+"Spieler"+" "+gewinner+"");
    }

    public void KOx5 ()
    {   KO = new int[anzahlSpieler+1];
        Siege = new int[3];
        Siege[1] = 0;
        Siege[2] = 0;
        int anzahlPaare;
        anzahlPaare = anzahlSpieler / 2;
        int y = anzahlPaare; //laufvariable
        max = anzahlSpieler - 1;
        int spielerEins = 1;
        int spielerZwei = 2;
        if(debug == 1){
             //System.out.println("anzahlSpieler"+" "+"="+" "+anzahlSpieler);
             //System.out.println("anzahlPaare"+" "+"="+" "+anzahlPaare);
        }
        int spielerEinsAlt = 0;
        int spielerZweiAlt = 0;
        for(int j = 1; j < anzahlSpieler + 1;j++){
            KO[j] = j;
        }
        for(int j = 1; j <= anzahlPaare && y != 0; j++){
            if(debug == 1){
                 //System.out.println(java.util.Arrays.toString(KO));
            }
            for(int i = 1; i <=  y ;i++){
                if(spielerEins <= max && spielerZwei <= anzahlSpieler){
                    while((KO[spielerEins] == 0 || KO[spielerZwei] == 0 ) || (spielerEins == spielerZweiAlt) && (spielerZwei <= anzahlSpieler)){
                        if(KO[spielerEins] == 0 && spielerEins < anzahlSpieler){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("KO[spielerEins] == 0 && spielerEins < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerEins == spielerEinsAlt){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerEinsAlt"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerEins == spielerZweiAlt){
                            spielerEins++;

                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerZweiAlt"+" "+"="+" "+"true");
                            }
                        }
                        if(spielerEins == spielerZwei && spielerZwei < anzahlSpieler){
                            spielerZwei++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerZwei && spielerZwei < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(KO[spielerZwei] == 0 && spielerZwei < anzahlSpieler){
                            spielerZwei++;
                            if(debug == 1){
                                 //System.out.println("KO[spielerZwei] == 0 && spielerZwei < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerZwei == spielerZweiAlt && spielerZwei < anzahlSpieler){
                            spielerZwei++;
                            if(debug == 1){
                                 //System.out.println("spielerZwei == spielerZweiAlt && spielerZwei < anzahlSpieler"+" "+"="+" "+"true");
                            }
                        }
                        else if(spielerEins == spielerZweiAlt && spielerEins < max){
                            spielerEins++;
                            if(debug == 1){
                                 //System.out.println("spielerEins == spielerZweiAlt && spielerEins < max"+" "+"="+" "+"true");
                            }
                        }
                        if(debug == 1){
                             //System.out.println("spielerEins"+" "+"="+ " " + spielerEins +" "+"spielerZwei"+" "+"="+" " + spielerZwei);
                        }
                    } 
                    for(int n = 1;n <= 5;n++){
                        duell(spielerEins,spielerZwei); 
                        if(debug == 1){
                             //System.out.println(spielerEins + " " + "kämpft" + " " + "gegen" + " " + spielerZwei);
                             //System.out.println("gewinner" + " " + "ist" +" "+ "Spieler" + " "+ gewinner);
                        }
                        if(gewinner == spielerZwei){
                            Siege[2] = Siege[2] + 1;
                        }
                        else{
                            Siege[1] = Siege[1] + 1;
                        }                        
                    }
                    if(Siege[1] >= 3){
                        gewinner = 1;
                    }
                    else if(Siege[2] >= 3){
                        gewinner = 2;
                    }
                    Siege[1] = 0;
                    Siege[2] = 0;
                    if(gewinner == spielerEins){
                        KO[spielerZwei] = 0;
                    }
                    else{
                        KO[spielerEins] = 0;
                    }
                    spielerEinsAlt = spielerEins;
                    spielerZweiAlt = spielerZwei;
                    if(spielerEins <= max-2 && spielerZwei <= max-1){
                        spielerEins = spielerEins + 2;
                        spielerZwei = spielerZwei + 2;
                    }
                    if(debug == 1){
                         //System.out.println(java.util.Arrays.toString(KO));
                    }
                }
            }
            if(debug == 1){
                 //System.out.println("DURCHGANG ENDE");
                 //System.out.println(java.util.Arrays.toString(KO));
            }
            spielerEins = 1;
            spielerZwei = 2;
            y = y/2;    
            for(int i = 1; i < anzahlSpieler + 1;i++){
                if(KO[i] != 0){
                    gewinner = i;
                }
                i++;
            }
        }
         //System.out.println("KOx5:"+" "+"Gewinner"+" "+"ist"+" "+"Spieler"+" "+gewinner);        
    }

    public void Liga()
    {
        int [] Siege;
        Siege = new int [anzahlSpieler + 1];
        int a = 0; 
        int x = 1;
        int y = 1;
        int check = 1;
        int sieger = 1;
        int siegerIndex = 1;
        for (a  = 0; a == Siege.length; a++){
            Siege[a] = 0;
            if(debug == 1){
                 //System.out.println(java.util.Arrays.toString(Siege));
            }
        }
        for (x = 1; x <= anzahlSpieler; x++){
            for (y = 1; y <= anzahlSpieler; y++){
                if (x != y){
                    duell(x,y);
                    if(debug == 1){
                         //System.out.println(gewinner);
                    }
                    if ( x == gewinner){
                        Siege[x] = Siege [x] + 1;
                    }
                    else {
                        Siege[y] = Siege[y] + 1;
                    }
                }
                if(debug == 1){
                     //System.out.println(java.util.Arrays.toString(Siege));
                }
            }
            y = 1;
            if(debug == 1){
                 //System.out.println(java.util.Arrays.toString(Siege));
            }
        }
        for ( check = 1; check <= anzahlSpieler; check++){
            if (Siege[check] > sieger){
                sieger = Siege[check];
                siegerIndex = check;
            }
            else if(Siege[check] == sieger){
                if(debug == 1){
                     //System.out.println("else_if_sieger"+" "+"ist"+":"+" "+sieger);
                }
                if(staerke[check] > staerke[siegerIndex]){
                    sieger = Siege[check];
                    siegerIndex = check;
                }
            }
        }
        if(debug == 1){
             //System.out.println(java.util.Arrays.toString(Siege));
        }
         //System.out.println("Liga:"+" "+"Gewinner"+" "+"ist"+" "+"Spieler"+" "+siegerIndex);
    }

    /**
     * Hier die Anzahl an Durchläufen eingeben
     */
    public void ergebnisse(float anzahlDurchlaeufe)
    { 
        int sieger=0;
        float gesamt=0;
        float ergebnisse_KO=0;
        float ergebnisse_KOx5=0;
        float ergebnisse_Liga=0;
        String empfehlung=" ";
        for(int i=1;i <= anzahlSpieler;i++){
            if(staerke[i]>sieger){
                sieger = i;
            }
        }
         //System.out.println("staerkster"+" "+"Spieler"+" "+"ist"+" "+sieger);
        for(int i=0; i < anzahlDurchlaeufe;i++){
            KO();
            if(gewinner == sieger){
                ergebnisse_KO = ergebnisse_KO+1;
            }
            KOx5();
            if(gewinner == sieger){
                ergebnisse_KOx5 = ergebnisse_KOx5+1;
            }
            Liga();
            if(gewinner == sieger){
                ergebnisse_Liga= ergebnisse_Liga+1;
            }
        }
        float prozent=0; 
        prozent = (ergebnisse_KO/anzahlDurchlaeufe)*100;
         //System.out.println("Der"+" "+"stärkste"+" "+"Spieler"+" "+"gewinnt"+" "+"bei"+" "+"KO"+" "+"in"+" "+ergebnisse_KO+" "+"von"+" "+anzahlDurchlaeufe+" "+"Fällen"+","+" "+"also"+" "+prozent+"%");

        prozent=0; 
        prozent = (ergebnisse_KOx5/anzahlDurchlaeufe)*100;
         //System.out.println("Der"+" "+"stärkste"+" "+"Spieler"+" "+"gewinnt"+" "+"bei"+" "+"KOx5"+" "+"in"+" "+ergebnisse_KOx5+" "+"von"+" "+anzahlDurchlaeufe+" "+"Fällen"+","+" "+"also"+" "+prozent+"%");

        prozent=0; 
        prozent = (ergebnisse_Liga/anzahlDurchlaeufe)*100;
         //System.out.println("Der"+" "+"stärkste"+" "+"Spieler"+" "+"gewinnt"+" "+"bei"+" "+"Liga"+" "+"in"+" "+ergebnisse_Liga+" "+"von"+" "+anzahlDurchlaeufe+" "+"Fällen"+","+" "+"also"+" "+prozent+"%");

        if(ergebnisse_KO >= ergebnisse_KOx5 && ergebnisse_KO >= ergebnisse_Liga){
            empfehlung = "KO";
        }
        else if(ergebnisse_KOx5 >= ergebnisse_KO && ergebnisse_KOx5 >= ergebnisse_Liga){
            empfehlung = "KOx5";
        }
        else if(ergebnisse_Liga >= ergebnisse_KO && ergebnisse_Liga >= ergebnisse_KOx5){
            empfehlung = "Liga";
        }
         //System.out.println("Die"+" "+"Empfehlung"+" "+"für"+" "+"Tobi"+" "+"wäre"+" "+"die"+" "+"Tuniervariante"+" "+empfehlung);
    }
}
