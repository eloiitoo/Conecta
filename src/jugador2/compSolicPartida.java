package jugador2;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Es el primer comportamiento de los agentes jugadores, se encarga de solicitar
 * al agente Servidor un puesto en una nueva partida y uno de los dos colores que
 * pueden tener las fichas que utilizar?, rojas ? azules.
 * @author Marco Antonio Martin Callejo
 */
public class compSolicPartida extends OneShotBehaviour {

   @Override
   public void action() {
      ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE);

      mensaje.setContent(this.myAgent.getLocalName());

      //Se anade el destinatario.
      AID id = new AID();
      id.setLocalName("Servidor");
      mensaje.addReceiver(id);
      mensaje.setContent("JUGAR");
      this.myAgent.send(mensaje);

      MessageTemplate filtroPerformativa = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
      MessageTemplate filtroEmisor = MessageTemplate.MatchSender(new AID("Servidor",AID.ISLOCALNAME));
      MessageTemplate plantilla3 = MessageTemplate.and(filtroPerformativa,filtroEmisor);
      MessageTemplate filtroContenido = MessageTemplate.or(MessageTemplate.MatchContent("ROJAS"), MessageTemplate.MatchContent("AZULES"));
      MessageTemplate plantillaAceptacion = MessageTemplate.and(plantilla3,filtroContenido);
      MessageTemplate plantillaRechazado = MessageTemplate.and(filtroEmisor,MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL));

      ACLMessage mensajeRec = this.myAgent.blockingReceive();
      if (mensajeRec != null) {
         // Ha sido aceptada mi petici?n de conexi?n al servidor, paso a a?adir el comportamiento de jugar
         if (plantillaAceptacion.match(mensajeRec)){
            this.myAgent.addBehaviour(new compJugar());
            System.out.println(this.myAgent.getAID().getLocalName()+" juega con las fichas "+mensajeRec.getContent());
         } // El servidor respondi?, pero no acepto nuestra petici?n, mostramos el mensaje recibido con el motivo
         else if (plantillaRechazado.match(mensajeRec)){
            System.out.println("El servidor de partidas respondio pero no ha aceptado la conexion,su respuesta es: "+mensajeRec.getContent());
         }
      }
   }
}