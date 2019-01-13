// Tira 2017 ht
// Roni Perälä
// 424962

// Solmu-luokka linkitettyä listaa varten
class ListNode{
   public ListNode next;
   public int value;
   public int key;
   
   // Rakentaja
   public ListNode(int k, int v){
      this.value = v;
      this.next = null;
      this.key = k;
   }
}