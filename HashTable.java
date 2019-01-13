// Tira 2017 ht
// Roni Perälä
// 424962

class HashTable{
   
   // Muuttujia
   private ListNode[] table;
   // Muuttuja kapasiteetille, jonka avulla voidaan laskea
   // taulukon täyttöaste ja muokata taulukon kokoa sen mukaan
   private int keyAmount;
   // Hajautustaulukon koko, alustetaan 8
   private int capacity = 8;
   
   // Rakentaja
   public HashTable(){
      // Alustetaan tyhjä taulukko, joka on kooltaan 8
      table = new ListNode[capacity];
      keyAmount = 0;
   }
   
   // Tarkistus, onko hajautustaulukko tyhjä
   public boolean isEmpty(){
      return keyAmount == 0;
   }
   
   // Funktio joka tarkistaa onko annettua avainta olemassa
   public boolean exists(int key){
      int[] intTable = keys();
      if(intTable != null){
         for(int i = 0; i < intTable.length; i++){
            if(intTable[i] == key){
               return true;
            }
         }
         return false;
      }
      else{
         return false;
      }
   }
   
   // Otetaan avaimet ylös taulukkoon
   public int[] keys(){
       // Luodaan avaintaulukko
      int[] keyTable = new int[keyAmount];
      int counter = 0;
      // Käydään taulukko läpi
      for(int i = 0; i < table.length; i++){
         if(table[i] != null){
            ListNode head = table[i];
            // Jos hajautustaulukon paikassa on vain yksisolmuinen lista
            // niin tallennetaan headin avain
            if(head.next == null){
               keyTable[counter] = head.key;
               counter++;
            }
            // Jos löytyy lista, jossa enemmän solmuja, niin käydään lista läpi
            // ja otetaan kaikki avaimet ylös
            else{
               while(head != null){
                   keyTable[counter] = head.key;
                   counter++;
                   head = head.next;
               }
            }
         }
      }
      return keyTable;
   }
   
   // Palauttaa avainten määrän
   public int getKeyAmount(){
      return keyAmount;
   }
   
   
   // Lisäysfunktio
   public void put(int key, int val){
      if(((keyAmount + 1) / capacity) > 0.75){
         rehash();
      }
      // Etsitään sopiva paikka lisäykselle
      int pos = hashFunction(key);
      // Luodaan solmu
      ListNode node = new ListNode(key, val);
      // Mikäli taulukosta haettu kohta on tyhjä, lisätään avain-arvo -pari
      if(table[pos] == null){
         table[pos] = node;
      }
      // Muuten lisätään arvo seuraavaan solmuun
      else{
         node.next = table[pos];
         table[pos] = node;
      }
      keyAmount++;
   }
   
   // Hakufunktio
   public int get(int key){
      // Etsitään mahdollista paikkaa
      int pos = hashFunction(key);
      ListNode head = table[pos];
      ListNode temp = head;
      int val;
      
      // Tyhjätarkistus
      if(!isEmpty()){
         // Käydään lista läpi solmu kerrallaan, kunnes vastaan tulee
         // haettu avain tai tyhjä solmu
         while(temp != null){
            if(temp.key == key){
               val = temp.value;
               return val;
            }
            temp = temp.next;
         }
         return 0;
      }
      else{
         return 0;
      }
   }
   
   // Poistofunktio
   public int remove(int key){
      int pos = hashFunction(key);
      // Jos etsitystä paikasta ei löydy mitään palautetaan nolla
      if(table[pos] == null){
         return 0;
      }
      else{
         // Luodaan oliot nykyiselle ja edelliselle solmulle
         ListNode curr = table[pos].next;
         ListNode prev = table[pos];
         // Jos poistettava avain "headissa", tässä tapauksessa previous
         if(key == prev.key){
            table[pos] = curr;
            keyAmount--;
            return prev.value;
         }
         // Jos löytyy lista, niin iteroidaan läpi kunnes löytyy etsitty avain
         // ja suoritetaan poisto
         else{
            while(curr != null && curr.key != key){
               curr = curr.next;
               prev = prev.next;
            }
            if(curr == null){
               return 0;
            }
            else{
               prev.next = curr.next;
               keyAmount--;
               return curr.value;
            }
         }
      }
   }
   
   // Hajautusfunktio
   private int hashFunction(int key){
      // Suoritetaan ns. hashCode manuaalisesti eli varmistetaan
      // että avain on taulukon koon mukainen
      int hashVal = (key % capacity);
      
      // Huomioidaan, jos hashCode antaa negatiivisen arvon
      if(hashVal < 0){
         hashVal += capacity;
      }
      return hashVal;
   }
   
   // Uudelleenhajautusfunktio
   public void rehash(){
      // Vanha, taulukon apumuuttuja
      ListNode[] old = table;
      // Kaksinkertaistetaan taulukon koko
      capacity *= 2;
      // Alustetaan uuden kokoinen taulukko
      table = new ListNode[capacity];
      // Nollataan keyAmount
      keyAmount = 0;
      ListNode t;
      int key;
      int value;
      // Käydään vanhan taulukon alkiot läpi
      for(int i = 0; i < old.length; i++){
          if(old[i] != null){
             t = old[i];
             while(t != null){
                 key = t.key;
                 value = t.value;
                 put(key, value);
                 t = t.next;
             }
          }
      }
   }
}

