
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.*;

public class Tira2017{
   // Tiedostojen lukeminen
   private static LinkedList<Integer> readInput(String name){
      // Luodaan lista, jonne luetaan tiedostojen integer-arvot
      LinkedList<Integer> myList = new LinkedList<Integer>();
      
      try{
         BufferedReader br = new BufferedReader( new FileReader(name));
         String line = br.readLine();
         
         while(line != null){
            myList.add(Integer.parseInt(line.trim()));
            line = br.readLine();
         }         
      }
      catch(IOException e){
         System.out.println("File not found.");
      }
      return myList;
   }
   
   // OR-operaatio
   private void OR(LinkedList<Integer> listA, LinkedList<Integer> listB){
       // Luodaan HashTable-olio
       HashTable OR = new HashTable();
       int key;
       int value;
       
       // Käydään listat läpi ja asetetaan avain-arvo -parit hajautustaulukkoon.
       // Jos avaimelle on jo asetettu arvo, poistetaan ja lisätään tiedot uudestaan,
       // mutta lisätään arvoon aina yksi, jotta saadaan laskettua montako kertaa se esiintyy syötteissä
       for(int i = 0; i < listA.size(); i++){
           key = listA.get(i);
           value = OR.get(key);
           if(value > 0){
              OR.put(key, OR.remove(key)+1);
           }
           else{
              OR.put(key, 1);
           }
       }
       for(int j = 0; j < listB.size(); j++){
           key = listB.get(j);
           value = OR.get(key);
           if(value > 0){
              OR.put(key, OR.remove(key)+1);
           }
           else{
              OR.put(key, 1);
           }
       }
       // Poisto- ja kirjoitusoperaatioiden kutsut
       userInputRemove(OR, "or.txt");
       writeOutput(OR, "or.txt");
   }
   
   // AND-operaatio
   private void AND(LinkedList<Integer> listA, LinkedList<Integer> listB){
      // Luodaan molemmille listoille omat taulukot, joita sitten vertaillaan
      // ja luodaan vertailun perusteella hajautustaulukko
      HashTable A = new HashTable();
      HashTable B = new HashTable();
      HashTable AND = new HashTable();
      int key, value;
      
      for(int i = 0; i < listA.size(); i++){
         key = listA.get(i);
         // Rivin numero, jolla alkio esiintyy ensimmäisen kerran setA-tiedostossa
         value = i + 1;
         // Duplikaatin varalta tarkistetaan onko avain-arvo pari jo olemassa.
         // Mikäli on, asetetaan vanha arvo takaisin, kun halutaan rivi,
         // jolla alkio on ensimmäisen kerran ollut tiedostossa
         if(A.get(key) > 0){
            A.put(key, A.get(key));
         }
         else{
            A.put(key, value);
         }
      }
      
      // B-taulukon valuella ei ole merkitystä tässä vaiheessa,
      // asetetaan vain jokin arvo jonka avulla tarkistetaan ettei
      // lisätä avain-arvo -paria useaan kertaan
      for(int i = 0; i < listB.size(); i++){
         key = listB.get(i);
         value = B.get(key);
         // Tarkastetaan onko luku jo talletettu
         if(value > 0){
            B.put(key, B.remove(key)+1);
         }
         else{
            B.put(key, 1);
         }
      }
      
      // Otetaan A-taulukosta avaimet talteen
      int[] keysA = A.keys();
      // Suoritetaan taulukoiden vertailu ja etsitään AND-operaation vaatimat tulosteet
      for(int i = 0; i < keysA.length; i++){
         // Haetaan A:n avaimilla B-taulukosta arvoja
         if(B.exists(keysA[i])){
            key = keysA[i];
            value = A.get(key);
            AND.put(key, value);
         }
      }
      
      userInputRemove(AND, "and.txt");
      writeOutput(AND, "and.txt");
   }
   
   //XOR-operaatio
   private void XOR(LinkedList<Integer> listA, LinkedList<Integer> listB){
      HashTable A = new HashTable();
      HashTable B = new HashTable();
      HashTable XOR = new HashTable();
      int key, value;
      
      // Asetetaan molemmat listat omiin taulukkoihinsa, ja annetaan arvoksi
      // tieto siitä, kummassa taulukossa alkio on esiintynyt
      for(int i = 0; i < listA.size(); i++){
         key = listA.get(i);
         value = XOR.get(key);
         // setA-tiedostosta tuleville annetaan arvoksi 1
         // Tässä vaiheessa ei vielä tarvitse tarkistaa muuta, kuin
         // että onko setA-tiedostossa duplikaatteja
         if(value > 0){
            XOR.put(key, XOR.remove(key)+1);
         }
         else{
            XOR.put(key, 1);
         }
      }
      
      for(int i = 0; i < listB.size(); i++){
         key = listB.get(i);
         value = XOR.get(key);
         // setB-tiedostosta tuleville annetaan arvoksi 2
         // Nyt tarkastetaan, että onko jo olemassa alkiota vastaavaa avain-arvo-paria
         // ja tarkastetaan onko se setA vai setB tiedostosta ja toimitaan sen mukaan
         if(value == 2){
            XOR.put(key, XOR.remove(key)+2);
         }
         // Mikäli löytyy solmu joka on lisätty tiedostosta setA,
         // poistetaan tiedot kokonaan
         else if(value == 1){
            XOR.remove(key);
         }
         else{
            XOR.put(key, 2);
         }
      }
      
      userInputRemove(XOR, "xor.txt");
      writeOutput(XOR, "xor.txt");
   }
   
   // Operaatio käyttäjän syötteen poistamiseksi taulukosta
   private void userInputRemove(HashTable table, String filename){
      Scanner scan = new Scanner(System.in);
      // Muuttuja jonka avulla seurataan, jatketaanko kyselyä
      boolean isOK = true;
      System.out.println("If you want to delete item from outcome of file '" + filename + "',");
      System.out.println("write the item and press enter.");
      System.out.println("When you are finished deleting items, just press enter.");
      // Scannerin toimivuuden takia tarkistetaan missä vaiheessa
      // ohjelmaa mennään ja vasta xor.txt-tiedoston kirjoittamisen jälkeen
      // suljetaan scanner
      if(filename.equals("or.txt")){
         while(isOK){
            String line = scan.nextLine();
            // Tarkastus onko syöte jätetty tyhjäksi vai onko poistettava alkio annettu
            if(line.equals("")){
               isOK = false;
            }
            else{
               int n = Integer.parseInt(line);
               // Mikäli alkiolle löytyy tiedot hajautustaulukosta
               // voidaan poisto suorittaa
               if(table.exists(n)){
                  table.remove(n);
               }
               // Mikäli avainta ei löytynyt, tulostetaan näytölle virheilmoitus
               // ja jatketaan kyselyä
               else{
                  System.out.println("The item didn't exist, try again.");
               }
            }
         }
      }
      else if(filename.equals("and.txt")){
         while(isOK){
            String line = scan.nextLine();
            if(line.equals("")){
               isOK = false;
            }
            else{
               int n = Integer.parseInt(line);
               if(table.exists(n)){
                  table.remove(n);
               }
               else{
                  System.out.println("The item didn't exist, try again.");
               }
            }
         }
      }
      else{
         while(isOK){
            String line = scan.nextLine();
            if(line.equals("")){
               isOK = false;
            }
            else{
               int n = Integer.parseInt(line);
               if(table.exists(n)){
                  table.remove(n);
               }
               else{
                  System.out.println("The item didn't exist, try again.");
               }
            }
         }
         // Vasta täällä suljetaan scanner
         scan.close();
      }
   }
   
   // Tiedostoon kirjoittaminen
   private void writeOutput(HashTable table, String filename){
      // Otetaan hajautustaulukon avaimet taulukkoon
      int[] keys = table.keys();
      // Järjestetään taulukko
      Arrays.sort(keys);
      // Sarakkeiden muuttujat
      String valueCol;
      int operationCol;
       
      try{
         BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
         // Käydään avainlista läpi ja kirjoitetaan tiedostoon tarvittavat tiedot
         for(int i = 0; i < keys.length; i++){
            valueCol = Integer.toString(keys[i]);
            operationCol = table.get(keys[i]);
            // Jätetään sarakkeiden tulosteille neljän merkin verran tilaa
            // kuten harjoitustyön esimerkkitiedostoissa oli tehty
            bw.write(String.format("%1$4s %2$4s", valueCol, operationCol));
            bw.newLine();
         }
         bw.close();
      }
      catch (IOException e){
         System.err.format("IOException: %s%n", e);
      }
      System.out.println("Writing file...");
      System.out.println("There are " + keys.length + " items in file '" + filename + "'.");
   }
   
   public static void main(String[] args){
       // Asetetaan tiedostojen nimet muuttujiin
       String firstSet = "setA.txt";
       String secondSet = "setB.txt";
       // Luodaan olio
       Tira2017 ht=new Tira2017();
       // Luetaan tiedostot
       LinkedList<Integer> listA = ht.readInput(firstSet);
       LinkedList<Integer> listB = ht.readInput(secondSet);
       // Ohjelman käynnistys ja viesti käyttäjälle
       System.out.println("Performing 'OR'-, 'AND'-, and 'XOR' -logical operations.");
       System.out.println("Program can deal with large integers.");
       // Operaatiokutsut
       ht.OR(listA, listB);
       ht.AND(listA, listB);
       ht.XOR(listA, listB);
   }
}