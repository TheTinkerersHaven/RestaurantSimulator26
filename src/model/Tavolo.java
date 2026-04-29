package model;

import java.util.Random;

public class Tavolo {
	 private int numeroTavolo;
	 private int numeroClienti;
	 private Piatto piattoOrdinato;
	 private int pazienza;
	 private boolean occupato;
	 private Random random = new Random();
	 
	 public Tavolo(int numeroTavolo) {
	     this.numeroTavolo=numeroTavolo;
	     this.numeroClienti=0;
	     this.piattoOrdinato=Piatto.NESSUNO;
	     this.pazienza=100;
	     this.occupato=false;
	 }
	 
	 public void faiArrivareClienti() throws Exception {
	     if (occupato) 
	    	 throw new Exception("Tavolo già occupato.");
	     this.occupato=true;
	     this.numeroClienti=random.nextInt(4)+1;
	     this.pazienza=100;
	     
	     int scelta=random.nextInt(3);
	     switch(scelta) {
	         case 0: 
	        	 this.piattoOrdinato=Piatto.SASHIMI; 
	        	 break;
	         case 1: 
	        	 this.piattoOrdinato=Piatto.URAMAKI_RAINBOW; 
	        	 break;
	         case 2: 
	        	 this.piattoOrdinato=Piatto.HOSOMAKI_MAGURO; 
	        	 break;
	     }
	 }
	 
	 public void decrementaPazienza() throws Exception {
	     if (!occupato) 
	    	 throw new Exception("Tavolo vuoto.");
	     pazienza-=2;
	     if (pazienza<0) {
	    	 pazienza=0;
	     }
	 }
	 public void clienteArrabbiato() {
	     this.occupato = false;
	     this.numeroClienti = 0;
	     this.piattoOrdinato = Piatto.NESSUNO;
	     this.pazienza = 100;
	 }
	 public void servito() {
	     this.occupato = false;
	     this.numeroClienti = 0;
	     this.piattoOrdinato = Piatto.NESSUNO;
	     this.pazienza = 100;
	 }
	 
	 public boolean isOccupato() { 
		 return occupato; 
	 }
	 
	 public int getNumeroClienti() { 
		 return numeroClienti; 
	 }
	 
	 public Piatto getPiattoOrdinato() { 
		 return piattoOrdinato; 
	 }
	 
	 public int getPazienza() { 
		 return pazienza; 
	 }
	 
	 public int getNumeroTavolo() { 
		 return numeroTavolo; 
	 }
}
