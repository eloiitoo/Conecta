package jugador1;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import tablero.tableroSimple;

/**
 * Comportamiento que ejecutan los agentes jugadores, env?a los mensajes a los
 * agentes Servidor y Pantalla necesarios para actuar cuando se le concede el turno
 * y desea comprobar el estado del tablero
 * @author Marco Antonio Martin Callejo
 */
@SuppressWarnings("serial")
public class compJugar extends OneShotBehaviour {

   tableroSimple tablero;

   @Override
   public void action() {

      int columnaEscogida = 0;
      Random rand = new Random();
      AID aid = new AID();
      MessageTemplate filtroEmisor = null;
      MessageTemplate filtroContenido = null;
      MessageTemplate filtroPerformativa = null;
      MessageTemplate filtrotemp = null;
      MessageTemplate filtro = null;
      ACLMessage mensajeMirar = null;
      ACLMessage mensajeColumna = null;

      aid.setLocalName("Servidor");


      // Ha llegado una peticion de consulta del tablero de un jugador
      ACLMessage turnoConcedido = this.myAgent.blockingReceive();
      if (turnoConcedido != null) {

         filtroEmisor = MessageTemplate.MatchSender(aid);
         filtroContenido = MessageTemplate.MatchContent("MUEVE");
         filtroPerformativa = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
         filtrotemp = MessageTemplate.and(filtroEmisor, filtroContenido);
         filtro = MessageTemplate.and(filtrotemp, filtroPerformativa);

         // El servidor de partidas me ha pedido que haga un movimiento
         if (filtro.match(turnoConcedido)) {
//----------------- CONSULTA AL AGENTE PANTALLA DEL ESTADO DEL TABLERO ------------//
            // Creacion del objeto ACLMessage que enviaremos a la pantalla para que nos deje ver el tablero
            mensajeMirar = new ACLMessage(ACLMessage.REQUEST);
            //Rellenar los campos necesarios del mensaje

            aid.setLocalName("Pantalla");
            mensajeMirar.addReceiver(aid);
            mensajeMirar.setContent("MIRAR-PANTALLA");
            this.myAgent.send(mensajeMirar);

            filtroEmisor = MessageTemplate.MatchSender(aid);
            filtroPerformativa = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            filtro = MessageTemplate.and(filtroEmisor, filtroPerformativa);

            //Espera la respuesta del agente pantalla con el estado actual del panel
            ACLMessage respuestaPantalla = this.myAgent.blockingReceive();

            if (filtro.match(respuestaPantalla)) {

               try { // Obtiene el cuerpo del mensaje, que es un objeto tableroSimple
                  this.tablero = (tableroSimple) (java.io.Serializable) respuestaPantalla.getContentObject();

                  
//----------------- TABLERO RECIBIDO AHORA HAY QUE ESCOGER UNA COLUMNA ------------//
//----------------ESTRATEGIA DE JUEGO: ESCOGEMOS UNA COLUMNA LIBRE ALEATORIA---------//

                  
                  columnaEscogida = Alg_Aleatorio.ElegirColumna(tablero);

                  
                  
                  
                  
//----------------------------FIN DE LA ESTRATEGIA------------------------------------//
//----------------------------ENVIAMOS LA COLUMNA AL SERVIDOR-------------------------//
                  mensajeColumna = new ACLMessage(ACLMessage.AGREE);
                  mensajeMirar.setSender(this.myAgent.getAID());
                  mensajeMirar.addReceiver(new AID("Servidor",AID.ISLOCALNAME));
                  mensajeMirar.setContent(String.valueOf(columnaEscogida));
                  this.myAgent.send(mensajeMirar);
 //----------------------------COLUMNA ENVIADA AL SERVIDOR-------------------------//  
                  // Volvemos a esperar nuestro turno
                  this.myAgent.addBehaviour(new compJugar());

               } catch (UnreadableException ex) {
                  Logger.getLogger(compJugar.class.getName()).log(Level.SEVERE, null, ex);
                  System.out.println(respuestaPantalla.getContent());
               }
            }
         }// El servidor no nos ha concedido el turno, lo que significa que la partida ha acabado
         else if (filtroEmisor.match(turnoConcedido)){ 
            aid.setLocalName("Servidor");
            filtroEmisor = MessageTemplate.MatchSender(aid);
            MessageTemplate tmp1 = MessageTemplate.MatchContent("GANADOR");
            MessageTemplate tmp2 = MessageTemplate.MatchContent("PERDEDOR");
            MessageTemplate tmp3 = MessageTemplate.or(tmp1, tmp2);
            MessageTemplate tmp4 = MessageTemplate.MatchContent("EMPATE");
            filtroContenido = MessageTemplate.or(tmp3,tmp4);
            filtroPerformativa = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            filtrotemp = MessageTemplate.and(filtroEmisor, filtroContenido);
            filtro = MessageTemplate.and(filtrotemp, filtroPerformativa);

            if (filtro.match(turnoConcedido)) {
              // La siguiente linea comentada hace que se solicite una nueva partida
              //this.myAgent.addBehaviour(new CompSolicPartida());
               
              // En el mensaje el servidor nos dice si hemos ganado,perdido o hay empate
              System.out.println(this.myAgent.getAID().getLocalName() + ": El servidor dice " + turnoConcedido.getContent());
            }
         }
      }
   }
}
