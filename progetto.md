## 1. Il Ruolo dell'Utente: Il "Manager del Caos"
L'utente non controlla tutto, ma gestisce le **risorse critiche** tramite la GUI per ottimizzare il lavoro dei thread.

* **Assunzione Dinamica:** Inizi con 1 Cameriere (1 thread) e 1 Cuoco (1 thread). L'utente ha un budget e può cliccare su un pulsante "Assumi" per istanziare e avviare nuovi thread in tempo reale.
* **Gestione Priorità:** L'utente può cliccare su un cliente specifico nella `JList` per assegnargli lo stato "VIP", spostandolo in cima alla `PriorityBlockingQueue` dei tavoli.
* **Potenziamenti (Upgrade):** Spendendo il "guadagno" (punteggio), l'utente può sbloccare un thread "Lavapiatti" che riduce il tempo di attesa dei cuochi o aumentare la dimensione della `ArrayBlockingQueue` (il bancone dei piatti).

## 2. Meccaniche di Gioco (Game Loop vs Thread)
Per rendere il tutto interattivo, devi introdurre dei **vincoli di tempo e fallimento**:

### A. La "Barra della Pazienza"
Ogni thread `Cliente` ha una variabile `patience` (es. da 100 a 0).
* Mentre il thread è in `WAIT` (attesa tavolo o attesa cibo), la pazienza diminuisce.
* **Interattività:** Se la pazienza arriva a 0, il thread invia un segnale di "Interrupt" a se stesso, si rimuove dalle code e la GUI mostra un feedback negativo (es. il tavolo diventa rosso lampeggiante e il punteggio cala).

### B. Eventi Casuali (I "Boss Level")
Puoi creare un thread `EventManager` che, a intervalli casuali, scatena imprevisti:
* **Critico:** "Il forno è rotto!" -> Il thread `Cuoco` entra in uno stato di `sleep` prolungato finché l'utente non clicca ripetutamente su un pulsante "Ripara".
* **Ispezione Sanitaria:** Tutti i processi devono rallentare, testando la tua capacità di gestire il `throughput` dei thread sotto stress.

## 3. Elementi GUI Interattivi in Swing
Per la parte visuale, usa componenti Swing in modo creativo:

* **Pannello "Drag & Drop":** Invece di un'assegnazione automatica, l'utente trascina l'icona del "Piatto Pronto" dal bancone al tavolo (interagendo direttamente con la coda dei messaggi tra thread).
* **Termometro dello Stress:** Una `JProgressBar` globale che aumenta in base a quanti thread `Cliente` sono in attesa. Se arriva al 100%, il gioco finisce ("Ristorante Chiuso").
* **Dashboard Statistiche:** Un pannello che usa un `javax.swing.Timer` per rinfrescare ogni 100ms i dati sui thread:
    * *Thread Attivi:* 12
    * *Clienti Soddisfatti:* 45
    * *Efficienza Media:* 82%

## 4. Sfida Tecnica: Lo Stato del Gioco
Gamificare significa dover gestire lo **Stato Globale** in modo thread-safe.

1.  **Pausa/Ripresa:** Implementare un sistema per sospendere tutti i thread contemporaneamente (usando un flag `volatile boolean running` o i nuovi `StructuredTaskScope` se usi Java moderno).
2.  **Salvataggio:** Salvare lo stato delle code e dei thread in un file JSON/XML per riprendere la "partita".