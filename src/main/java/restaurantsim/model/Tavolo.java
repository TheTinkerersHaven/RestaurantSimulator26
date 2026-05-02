package restaurantsim.model;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Rappresenta un tavolo del ristorante.
 */
public class Tavolo {
	/**
	 * Semaforo per sincronizzare l'accesso al tavolo. Deve essere acquisito prima di leggere o modificare lo stato del tavolo.
	 */
	private Semaphore mutex;

	/**
	 * Il numero del tavolo, che identifica univocamente il tavolo all'interno della sala.
	 */
	private int numeroTavolo;
	/**
	 * Il piatto ordinato al tavolo, che rappresenta il piatto che i clienti al tavolo hanno ordinato. Se il tavolo è vuoto, questo valore è {@link Piatto#NESSUNO}.
	 */
	private Piatto piattoOrdinato;
	/**
	 * La pazienza dei clienti al tavolo, che rappresenta quanto tempo i clienti al tavolo sono disposti ad aspettare prima di arrabbiarsi.
	 */
	private int pazienza;
	/**
	 * Indica se il tavolo è occupato o meno.
	 */
	private boolean occupato;
	/**
	 * Generatore di numeri casuali per determinare il piatto ordinato.
	 */
	private Random random = new Random();

	/**
	 * Inizializza un oggetto tavolo vuoto. Necessario per Jackson.
	 */
	public Tavolo() {
		this.mutex = new Semaphore(1);
	}

	/**
	 * Inizializza un tavolo con il numero specificato, impostando lo stato iniziale del tavolo come vuoto, con pazienza al massimo e non occupato.
	 * 
	 * @param numeroTavolo il numero del tavolo da inizializzare
	 */
	public Tavolo(int numeroTavolo) {
		this.mutex = new Semaphore(1);
		this.numeroTavolo = numeroTavolo;
		this.piattoOrdinato = Piatto.NESSUNO;
		this.pazienza = 100;
		this.occupato = false;
	}

	/**
	 * Fai arrivare i clienti al tavolo, impostando lo stato del tavolo come occupato, assegnando un piatto ordinato casuale e resettando la pazienza al massimo.
	 * 
	 * @return lo stato del tavolo dopo aver fatto arrivare i clienti
	 * @throws TavoloOccupatoException se il tavolo è già occupato.
	 * @throws InterruptedException se il thread viene interrotto mentre aspetta di acquisire il semaforo, poiché l'operazione di far arrivare i clienti è stata interrotta.
	 */
	public StatoTavolo prenotaTavoloSeLibero() throws TavoloOccupatoException, InterruptedException {
		mutex.acquire();

		try {
			if (occupato) {
				throw new TavoloOccupatoException();
			}

			this.occupato = true;
			this.pazienza = 100;

			int scelta = random.nextInt(3);
			switch (scelta) {
				case 0:
					this.piattoOrdinato = Piatto.SASHIMI;
					break;
				case 1:
					this.piattoOrdinato = Piatto.URAMAKI_RAINBOW;
					break;
				case 2:
					this.piattoOrdinato = Piatto.HOSOMAKI_MAGURO;
					break;
			}

			return getStatoTavoloInterno();
		} finally {
			mutex.release();
		}
	}

	/**
	 * Decrementa la pazienza dei clienti al tavolo di 1 punto se ci sono clienti.
	 * 
	 * @return Lo stato attuale del tavolo dopo il decremento della pazienza.
	 * @throws TavoloNonOccupatoException se il tavolo non è occupato.
	 * @throws InterruptedException se il thread viene interrotto durante l'acquisizione del mutex.
	 */
	public StatoTavolo decrementaPazienzaSeOccupato() throws TavoloNonOccupatoException, InterruptedException {
		mutex.acquire();

		try {
			if (!occupato) {
				throw new TavoloNonOccupatoException();
			}

			pazienza -= 1;

			// Se la pazienza è finita, svuota il tavolo e resetta la pazienza al massimo
			if (pazienza == 0) {
				occupato = false;
				piattoOrdinato = Piatto.NESSUNO;
			}

			return getStatoTavoloInterno();
		} finally {
			mutex.release();
		}
	}

	/**
	 * Restituisce se il tavolo è occupato.
	 * 
	 * @return se il tavolo è occupato.
	 * @throws InterruptedException se il thread viene interrotto durante l'acquisizione del mutex.
	 */
	public boolean isOccupato() throws InterruptedException {
		mutex.acquire();

		try {
			return occupato;
		} finally {
			mutex.release();
		}
	}

	/**
	 * Restituisce il piatto ordinato al tavolo.
	 * 
	 * @return il piatto ordinato al tavolo
	 * @throws InterruptedException se il thread viene interrotto mentre aspetta di acquisire il semaforo, poiché l'operazione di ottenere il piatto ordinato è stata interrotta.
	 */
	public Piatto getPiattoOrdinato() throws InterruptedException {
		mutex.acquire();

		try {
			return piattoOrdinato;
		} finally {
			mutex.release();
		}
	}

	/**
	 * Restituisce la pazienza dei clienti al tavolo.
	 * 
	 * @return la pazienza dei clienti al tavolo
	 * @throws InterruptedException se il thread viene interrotto mentre aspetta di acquisire il semaforo, poiché l'operazione di ottenere la pazienza è stata interrotta.
	 */
	public int getPazienza() throws InterruptedException {
		mutex.acquire();

		try {
			return pazienza;
		} finally {
			mutex.release();
		}
	}

	/**
	 * Restituisce il numero del tavolo.
	 * 
	 * @return il numero del tavolo
	 */
	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	/**
	 * Serve il piatto al tavolo.
	 * 
	 * @param piatto il piatto da servire al tavolo
	 * @return Lo stato attuale del tavolo dopo il servizio (vuoto se servito correttamente).
	 * @throws TavoloNonOccupatoException se il tavolo è vuoto, poiché non è possibile servire un piatto a un tavolo vuoto.
	 * @throws PiattoErratoException      se il piatto da servire è diverso da quello ordinato al tavolo
	 * @throws InterruptedException       se il thread viene interrotto durante l'acquisizione del mutex.
	 */
	public StatoTavolo serviTavolo(Piatto piatto) throws TavoloNonOccupatoException, PiattoErratoException, InterruptedException {
		mutex.acquire();

		try {
			if (!occupato || piattoOrdinato.equals(Piatto.NESSUNO)) {
				throw new TavoloNonOccupatoException();
			}

			if (!piattoOrdinato.equals(piatto)) {
				throw new PiattoErratoException("Piatto errato. Il tavolo " + numeroTavolo + " ha ordinato " + piattoOrdinato + ".");
			}

			this.occupato = false;
			this.piattoOrdinato = Piatto.NESSUNO;

			return getStatoTavoloInterno();
		} finally {
			mutex.release();
		}
	}

	/**
	 * Resetta lo stato del tavolo, svuotando il tavolo e resettando la pazienza al massimo.
	 * 
	 * @throws InterruptedException se il thread viene interrotto durante l'acquisizione del mutex.
	 */
	public void reset() throws InterruptedException {
		mutex.acquire();

		try {
			this.occupato = false;
			this.piattoOrdinato = Piatto.NESSUNO;
			this.pazienza = 100;
		} finally {
			mutex.release();
		}
	}

	/**
	 * Carica lo stato del tavolo da un altro oggetto tavolo salvato.
	 * 
	 * @param tavolo il tavolo da cui caricare lo stato
	 * @throws InterruptedException se il thread viene interrotto mentre aspetta di acquisire il semaforo, poiché l'operazione di caricare lo stato del tavolo è stata interrotta.
	 */
	public void caricaTavolo(StatoTavolo tavolo) throws InterruptedException {
		mutex.acquire();

		try {
			this.occupato = tavolo.isOccupato();
			this.piattoOrdinato = tavolo.getPiattoOrdinato();
			this.pazienza = tavolo.getPazienza();
		} finally {
			mutex.release();
		}
	}

	/**
	 * Restituisce lo stato attuale del tavolo.
	 * 
	 * @return Un oggetto {@link StatoTavolo} che rappresenta lo stato attuale del tavolo.
	 * @throws InterruptedException se il thread viene interrotto durante l'acquisizione del mutex.
	 */
	public StatoTavolo getStatoTavolo() throws InterruptedException {
		mutex.acquire();

		try {
			return new StatoTavolo(numeroTavolo, occupato, piattoOrdinato, pazienza);
		} finally {
			mutex.release();
		}
	}

	/**
	 * Restituisce lo stato attuale del tavolo senza acquisire il mutex.
	 * Da utilizzare solo all'interno di metodi che hanno già acquisito il mutex.
	 * 
	 * @return Un oggetto {@link StatoTavolo} che rappresenta lo stato attuale del tavolo.
	 */
	private StatoTavolo getStatoTavoloInterno() {
		return new StatoTavolo(numeroTavolo, occupato, piattoOrdinato, pazienza);
	}
}
