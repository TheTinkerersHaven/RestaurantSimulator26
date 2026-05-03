package restaurantsim.controller;

import java.io.BufferedInputStream;
import java.util.Objects;

import javazoom.jl.player.Player;

/**
 * Controller che si occupa della gestione e della riproduzione degli effetti sonori del gioco.
 * Utilizza la libreria JLayer per supportare formati audio compressi e ridurre la latenza.
 */
public class ControllerSuoni {
    /** Stato che indica se l'audio è abilitato */
    private boolean enabled;

    /**
     * Inizializza il controller dei suoni.
     * Esegue un'inizializzazione silenziosa delle classi audio in un thread separato
     * per evitare lag durante la prima riproduzione effettiva in gioco.
     */
    public ControllerSuoni() {
        this.enabled = true;
        // Carichiamo un piccolo buffer silenzioso o semplicemente inizializziamo le classi
        // senza far baccano all'avvio
        new Thread(() -> {
            try {
                // Inizializza le classi di JLayer in background senza suonare nulla
                new Player(new java.io.ByteArrayInputStream(new byte[0]));
            } catch (Exception ignored) {}
        }).start();
    }

    /**
     * Riproduce un file audio specificato dal percorso delle risorse.
     * La riproduzione avviene in un nuovo thread che termina automaticamente alla fine del suono.
     * 
     * @param soundFilePath Il percorso relativo del file audio nelle risorse (es. "sounds/ding.mp3").
     */
    public void playSound(String soundFilePath) {
        if (!enabled) return;

        new Thread(() -> {
            try (BufferedInputStream bis = new BufferedInputStream(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(soundFilePath)))) {
                Player player = new Player(bis);
                player.play();
                player.close();
            } catch (Exception e) {
                System.err.println("Errore nella riproduzione del suono: " + soundFilePath);
            }
        }).start();
    }

    /**
     * Restituisce lo stato dell'attivazione dei suoni.
     * 
     * @returns lo stato dell'attivazione dei suoni.
     */
    public boolean getEnabled() {
        return this.enabled;
    }

    /**
     * Abilita o disabilita la riproduzione dei suoni.
     * 
     * @param enabled true per abilitare l'audio, false per disabilitarlo.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Riproduce il suono associato all'arrivo di un nuovo cliente.
     */
    public void playClienteArrivato() {
        playSound("sounds/ding2x.mp3");
    }

    /**
     * Riproduce il suono associato al completamento di un piatto da parte del cuoco.
     */
    public void playCuocoPronto() {
        playSound("sounds/ovending.mp3");
    }
}
