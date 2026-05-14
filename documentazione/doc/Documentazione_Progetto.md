<style>
  body {
    font-family: "Georgia", "Times New Roman", Times, serif;
    text-align: justify;
    line-height: 1.7;
    color: #34495e;
  }
  h1, h2, h3, h4, h5, h6 {
    font-family: "Segoe UI", "Helvetica Neue", Helvetica, Arial, sans-serif;
  }
  h1 { 
    color: #2c3e50;
    font-size: 2.8em;
    text-align: center;
    border: none;
  }
  h2 { 
    color: #2980b9; 
    border-bottom: 2px solid #ecf0f1; 
    padding-bottom: 8px;
    margin-top: 1.5em;
  }
  h3 { 
    color: #e74c3c;
    border-left: 4px solid #e74c3c;
    padding-left: 12px;
    margin-top: 1.5em;
  }
  code {
    font-family: "JetBrains Mono", Consolas, monospace;
    background-color: #f8f9fa;
    color: #e83e8c;
    padding: 3px 6px;
    border-radius: 4px;
    font-size: 0.9em;
  }
  li {
    margin-bottom: 8px;
  }
  .cover-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 95vh;
    text-align: center;
    padding: 0 20px;
  }
  .cover-img {
    max-width: 500px;
    width: 80%;
    border-radius: 12px;
    box-shadow: 0px 10px 25px rgba(0,0,0,0.15);
    margin-bottom: 40px;
  }
  .cover-title {
    font-size: 46px !important;
    margin-bottom: 10px;
    color: #2c3e50;
    text-transform: uppercase;
    letter-spacing: 2px;
    border: none;
    font-weight: bold;
  }
  .cover-subtitle {
    font-size: 24px;
    color: #e74c3c;
    margin-top: 0;
    font-weight: 400;
  }
  .cover-info {
    margin-top: 100px;
    font-size: 18px;
    color: #34495e;
    border-top: 1px solid #bdc3c7;
    padding-top: 20px;
    width: 60%;
  }
</style>

<div class="cover-container">
  <img class="cover-img" src="../../src/main/resources/images/Restaurant%20Simulator%20V2.png" alt="Restaurant Simulator Banner">
  <h1 class="cover-title">Restaurant Simulator 26</h1>
  <p class="cover-subtitle">Documento di Specifica e Analisi</p>
  
  <div class="cover-info">
    <p><strong>Corso:</strong> Informatica e TPSIT</p>
    <p><strong>Data:</strong> 13 Maggio 2026</p>
  </div>
</div>

<div style="page-break-after: always;"></div>

## Indice
- [1. Studio di Fattibilità](#1-studio-di-fattibilità)
  - [1.1 Fattibilità Tecnica](#11-fattibilità-tecnica)
  - [1.2 Fattibilità Economica](#12-fattibilità-economica)
  - [1.3 Fattibilità Operativa](#13-fattibilità-operativa)
  - [1.4 Fattibilità Temporale](#14-fattibilità-temporale)
- [2. Analisi dei Requisiti](#2-analisi-dei-requisiti)
  - [2.1 Obiettivi del Progetto](#21-obiettivi-del-progetto)
  - [2.2 Mappatura degli Stakeholder](#22-mappatura-degli-stakeholder)
- [3. Definizione dei Requisiti](#3-definizione-dei-requisiti)
  - [3.1 Requisiti Funzionali (Cosa deve fare il sistema)](#31-requisiti-funzionali-cosa-deve-fare-il-sistema)
  - [3.2 Requisiti Non Funzionali e Vincoli Tecnologici](#32-requisiti-non-funzionali-e-vincoli-tecnologici)
- [4. Specifica dei Requisiti](#4-specifica-dei-requisiti)
  - [4.1 Casi d'Uso (Use Case)](#41-casi-duso-use-case)
  - [4.2 Diagramma delle Classi (UML)](#42-diagramma-delle-classi-uml)
- [5. Strumenti Utilizzati](#5-strumenti-utilizzati)
  - [5.1 Tecnologie Core e Architettura](#51-tecnologie-core-e-architettura)
  - [5.2 Ambiente e Strumenti di Sviluppo](#52-ambiente-e-strumenti-di-sviluppo)
  - [5.3 Modellazione e Documentazione](#53-modellazione-e-documentazione)

<div style="page-break-after: always;"></div>

## 1. Studio di Fattibilità
Lo studio di fattibilità valuta la sostenibilità del progetto "Restaurant Simulator" sotto i profili tecnico, economico, operativo e temporale per garantire che gli obiettivi prefissati siano raggiungibili con le risorse a disposizione.

### 1.1 Fattibilità Tecnica
Il sistema sarà interamente sviluppato in linguaggio **Java**, implementando principi chiave della programmazione Object-Oriented tra cui spicca l'**ereditarietà**, e avvalendosi di architetture standard ben divise in **packages** (Pattern MVC - Model, View, Controller). La gestione interna dei dati sfrutterà in maniera estensiva performanti **data structures** native (come le *Collections*). Verrà utilizzato **Gradle** come strumento di automazione per la build. Per l'interfaccia grafica si farà esclusivo affidamento sulle librerie standard **Swing** di Java, garantendo una UI solida e ben integrata. Le competenze del team sono perfettamente in linea con questi vincoli hardware/software.

### 1.2 Fattibilità Economica
Trattandosi di un progetto con valenza didattica e basato su tecnologie open-source, l'impatto economico è estremamente ridotto. Non ci sono costi legati a licenze software o infrastrutture (l'IDE Visual Studio Code, l'IDE Eclipse, il JDK, e Gradle sono gratuiti e l'esecuzione è interamente locale).

### 1.3 Fattibilità Operativa
L'applicativo è pensato per essere intuitivo ("plug & play"). L'utente non necessita di addestramento specifico, se non di una minima familiarità con il concetto di gioco manageriale. Questo assicura un'elevata facilità di fruizione.

### 1.4 Fattibilità Temporale
Lo sviluppo seguirà un approccio incrementale (Sprint). La consegna delle versioni Alpha e Beta è schedulata in base ai tempi accademici, integrando per tempo i sistemi di core-gameplay e salvataggio locale JSON.

<div style="page-break-after: always;"></div>

## 2. Analisi dei Requisiti

### 2.1 Obiettivi del Progetto
L'obiettivo principale del progetto è realizzare un gestionale/simulatore di un **ristorante di Sushi**: il sistema dovrà permettere al giocatore di immedesimarsi nella gestione attiva e dinamica di tale esercizio, affrontando in ottica di *Time Management* le criticità tipiche del dominio gestionale quali il bilanciamento di risorse limitate, le attese dei clienti e lo smistamento simultaneo delle comande.

Al di là della componente videoludica, dal punto di vista accademico e ingegneristico lo sviluppo di "Restaurant Simulator 26" funge da terreno di prova pratico per lo studio di architetture asincrone complesse. Il software maschera un solido engine concorrente simulando, tramite multithreading, l'intelligenza e l'indipendenza di un ecosistema persistente, in cui una sala dotata di **4 tavoli** e una cucina condotta da **3 cuochi** autonomi convivono elaborando calcoli concorrenti. L'integrazione di un sistema premiale/punitivo stimola infine la risposta strategica in tempo reale dell'utente.

### 2.2 Mappatura degli Stakeholder
* **Giocatori (Utenti Finali):** Coloro che interagiscono con l'applicazione per intrattenimento. Richiedono un'interfaccia responsiva, meccaniche di gioco bilanciate (con sanzioni per i clienti "arrabbiati" e punteggi premianti per i piatti serviti), feedback visivi/audio chiari e la possibilità di salvare i progressi o compararsi in una classifica.
* **Team di Sviluppo e Manutentori:** Coloro che progettano e scrivono il codice. Necessitano di una base di codice ben organizzata in package e design pattern, facilmente scalabile per l'aggiunta di espansioni future (come l'inserimento di nuovi piatti nel menu) o la facile risoluzione dei bug.

<div style="page-break-after: always;"></div>

## 3. Definizione dei Requisiti
In questa fase definiamo i requisiti utente e li traduciamo in requisiti di sistema chiari e verificabili, divisi per direttive:

### 3.1 Requisiti Funzionali (Cosa deve fare il sistema)
* **Organizzazione della Sala:** Gestione dinamica dei tavoli (stato libero/occupato). La sala dispone un tetto massimo di **4 tavoli**.
* **Gestione del cliente:** I clienti devono essere generati ritmicamente dal sistema (gestito da core logic concorrente `ArrivoClientiWorker`), con gestione del livello di tolleranza all'attesa. Se l'attesa è troppa e il cliente va via "arrabbiato", viene applicata una penalità di **-5 punti**; raggiunti i **10 clienti arrabbiati** scatta il Game Over.
* **Interazione del Personale:** L'utente deve poter ricevere comande basate su 3 piatti sushi disponibili: **Sashimi**, **Uramaki Rainbow** e **Hosomaki Maguro**. La comanda viene inoltrata alla brigata di **3 cuochi** (`ControllerCuoco`). Terminato il tempo di cottura specifico del piatto (es. 10 per il Sashimi), il piatto andrà servito incrementando il punteggio di **+10 punti**.
* **Gestione delle Sessioni di Gioco:** Il software struttura il gameplay in partite gestite dal modulo `ControllerPartita`. Al termine di ogni sessione, il sistema elabora il punteggio finale e permette di salvare i progressi prima di iniziare un nuovo ciclo o uscire.
* **Feedback Audio/Visivo:** Output interattivi quali suoni e pop-up a compimento di azioni chiave (supportati da `ControllerSuoni` e `ControllerNotifiche`).
* **Persistenza Dati (I/O)::** I punteggi e i progressi della simulazione devono essere serializzati/deserializzati correttamente sfruttando strutture testuali JSON (`salvataggio.json`, `classifica.json`).

### 3.2 Requisiti Non Funzionali e Vincoli Tecnologici
* **Tecnologia e Linguaggio:** Sviluppo esclusivo in Java.
* **Architettura e Design:** Adozione netta del pattern MVC (con netta separazione a livello di package) e utilizzo di paradigmi ad oggetti (Ereditarietà, Polimorfismo).
* **Programmazione Concorrente (TPSIT):** Il core logico deve integrare estensivamente l'uso dei **Thread**, assicurando la **mutua esclusione** e la corretta **sincronizzazione** per gestire eventi paralleli coordinandoli con sicurezza insieme alla GUI (mediante `Semaphore`).
* **Storage in Run-Time:** Uso di Data Structure avanzate del framework Java (es. Java Collections Framework) per lo storing a runtime di comande, tavoli e punteggi.
* **Libreria Grafica:** Obbligo di utilizzo della libreria **Swing** per il rendering della GUI, escludendo alternative esterne.
* **Qualità del Codice ed Eccezioni:** Elevata organizzazione dei file di logica e risorse, per garantire **complessità gestita** e profonda **robustezza del codice**, implementando un uso appropriato di **eccezioni personalizzate** (`TavoloOccupatoException`, `TavoloNonOccupatoException`, `PiattoErratoException`) per validare le regole di dominio.
* **Usabilità & UI:** Interfaccia grafica priva di disordine (Cluttering), fruibile sia per visualizzare subito le comande sia la mappa della sala.
* **Affidabilità:** Gestione sicura degli errori qualora i file JSON venissero cancellati a livello di sistema operativo o risultassero corrotti; il gioco non andrà in crash irrecuperabili.
* **Portabilità:** Forte indipendenza dalla piattaforma nativa grazie all'implementazione in linguaggio Java; l'applicazione girerà egregiamente su Windows, OS X e distribuzioni Linux.

<div style="page-break-after: always;"></div>

## 4. Specifica dei Requisiti
La specifica modella nel dettaglio i flussi delle operazioni tramite casi d'uso (Use Case):

### 4.1 Casi d'Uso (Use Case)
* **UC1 - Avvio Applicativo e Gestione Partita:** L'utente accede al menu principale, dal quale può inizializzare una nuova sessione di gioco o ricaricare i progressi dal file `salvataggio.json`. È prevista anche un'area per visualizzare i punteggi più alti raggiunti.
* **UC2 - Afflusso e Accoglienza:** Il sistema produce proceduralmente l'ingresso di nuovi gruppi di clienti. L'utente ha il compito di cliccare/selezionare i nuovi arrivati e indirizzarli verso una postazione tavolo con capienza adeguata.
* **UC3 - Presa in Carico, Preparazione e Servizio:** L'utente prende le ordinazioni di Sashimi, Uramaki o Hosomaki. Rilevato l'ordine, esso entra nelle competenze dei 3 cuochi concorrenti (monitorati dai timer di preparazione: 10, 30 e 20). Quando le pietanze sono ultimate all'uscita della cucina, il giocatore compie il trasferimento al tavolo, soddisfando la richiesta.
* **UC4 - Pagamento e Storico:** Al rilascio del tavolo, aumenta il punteggio della partita (+10) se il cliente è stato servito correttamente. Se il cliente si esaurisce per l'attesa (rabbia), conferisce un malus di -5 e libererà la risorsa forzatamente.
* **UC5 - Fine della partita:** Al raggiungimento di 10 errori complessivi (clienti arrabbiati), scatta il Game Over. Viene richiesto il nome del giocatore che, insieme al punteggio della partita, verrà salvato nel file `classifica.json`.

### 4.2 Diagramma delle Classi (UML)
*(Nota tecnica: a corredo per queste dipendenze, nella pagina successiva è riportato il diagramma UML completo dell'architettura del simulatore. È consigliabile visualizzare il diagramma separatamente in quanto le sue grandi dimensioni rendono difficile la lettura all'interno di questo documento.)*

<div style="page-break-before: always;"></div>

<div align="center" style="margin-top: 250px;">
    <img src="../uml/Restaurant%20Simulator.svg" alt="Diagramma UML dell'architettura" style="transform: rotate(-90deg); width: 140%; max-width: 1000px; margin-left: -20%;">
</div>

<div style="page-break-after: always;"></div>

## 5. Strumenti Utilizzati
A supporto della creazione e architettura di questo progetto software sono impiegati:

### 5.1 Tecnologie Core e Architettura
* **Linguaggio di Programmazione:** Java, preferito per la sua maturità nell'object-oriented programming e per il supporto nativo estensivo delle Collection (dati) e del multithreading (Worker e Timer).
* **Costruzione dell'Interfaccia (GUI):** Adozione degli standard classici di riferimento Java come Java Swing, coordinati tramite un'infrastruttura di Classi Controller dedicate (es. `ControllerFinestra`, `ControllerNavigazione`).
* **Memoria e Interscambio (Filesystem):** Algoritmi di I/O per salvataggi su formato dati standard aperto JSON. Trattamento testuale leggero, universale, ed efficiente.

### 5.2 Ambiente e Strumenti di Sviluppo
* **Motore di Build:** Gradle (integrato con Domain Specific Language, DSL, basata su Kotlin `build.gradle.kts`); vitale per normalizzare le direttive di compilazione, per l'import delle dipendenze extra e per la fase generazione della documentazione tramite Javadoc.
* **Ambienti di Sviluppo (IDE) e OS:** L'ecosistema di sviluppo è cross-platform (eseguito su **Linux** e **Windows**). Viene impiegato **Visual Studio Code** e parallelamente **Eclipse IDE**, scelto appositamente per sfruttare il plugin **WindowBuilder** limitando lo sviluppo time-consuming dell'interfaccia.
* **Source Management:** Git per il controllo della versione, agganciato alla piattaforma esterna Repository GitHub.
* **Assistente di Sviluppo AI:** Supporto occasionale di **GitHub Copilot** per la velocizzazione della stesura di boilerplate code, commenti e piccoli script di utilità.

### 5.3 Modellazione e Documentazione
* **Modellazione Ingegneristica (UML):** **PlantUML** per la traduzione logica testuale e la stesura dei Diagrammi di Classe architetturali. I diagrammi sono stati successivamente post-processati graficamente tramite il software **Inkscape**.
* **Scrittura e Presentazione:** **Google Docs** per concettualizzare la relazione, **Gamma** per il montaggio della presentazione di discussione, e **Visual Studio Code** (con l'estensione _Markdown PDF_) per lo sviluppo integrato di questo stesso documento SRS in formato PDF.