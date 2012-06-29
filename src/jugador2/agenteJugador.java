package jugador2;

import jade.core.Agent;

/**
 * Agente jugador de partidas
 * @author Marco Antonio Martin Callejo
 */
public class agenteJugador extends Agent {

    @Override
   protected void setup() {
      this.addBehaviour(new compSolicPartida());
    }
}
 