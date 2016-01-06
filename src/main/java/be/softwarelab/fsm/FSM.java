package be.softwarelab.fsm;

import java.util.Properties;
import javax.jms.Message;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FSM {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FSM.class);
    private static final org.slf4j.Logger CONSOLE = org.slf4j.LoggerFactory.getLogger("Console");
    
    private static State state;
    private static Properties config;
    private static Message message;

    private static int total = 0;

    private static String description = "";
    private static String action = "reject";
    private static String label = "";
    
    //Configuration parameters
    private static String task;
    private static String providerUrl;
    private static String name;
    private static String guid;
    private static String frequency;
    
    public static void main(String[] args) throws Exception {

        LOGGER.info("FSM: start Finite State Machine (FSM)");
        CONSOLE.info("Finite State Machine Started");
        
        // Finite State Machine
        state = State.START;
        while (state != State.STOP) {
            switch (state) {
                case START:
                    start();
                    break;
                case INITIALIZE:
                    initialize();
                    break;
                case CONFIG:
                    config();
                    break;
                case POLL:
                    poll();
                    break;
                case PUSH:
                    push();
                    break;
                case MESSAGE:
                    message();
                    break;
                case PRINT:
                    print();
                    break;
                case STOP:
                    stop();
                    break;
                default:
                    stop();
                    break;
            }
        }

        CONSOLE.info("FSM: stop");
        System.exit(0);
    }

    private static void start() {
        LOGGER.info("STATE = {}", state);
        // The Client is started: move to initialization phase.
        state = State.INITIALIZE;
    }

    private static void initialize() {
        LOGGER.info("STATE = {}", state);
        // Currently nothing - Go directory to configuration
        state = State.CONFIG;
    }

    private static void config() {
        LOGGER.info("STATE = {}", state);

        // Get the configuration
        config = ConfigurationManager.readConfiguration();

        // Load the parameters
        task = config.getProperty("task");
        providerUrl = config.getProperty("providerUrl");
        name = config.getProperty("name");
        guid = config.getProperty("guid");
        frequency = config.getProperty("frequency");

        //TODO: do something when a config-message arrives
        // Decide what to do
        String task = config.getProperty("task");
        switch (task) {
            case "poll":
                state = State.POLL;
                break;
            case "push":
                state = State.PUSH;
                break;
            default:
                state = State.STOP;
                break;
        }
    }

    // Start polling until a message arrives
    private static void poll() {
        LOGGER.info("STATE = {}", state);
        message = null;

//        try {
//            Properties props = new Properties();
//            props.put(Context.PROVIDER_URL, providerUrl);
//
//            // Create a new context: loaded from jndi.properties
//            // The properties from the config file (set above), overruled the jndi.properties
//            InitialContext ctx = new InitialContext(props);
//
//            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("connectionFactory");
//            Connection connection = factory.createConnection();
//            Queue queue = (javax.jms.Queue) ctx.lookup("clientQueue");
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            String messageSelector = "guid = '" + guid + "'";
//            MessageConsumer consumer = session.createConsumer(queue, messageSelector);
//
//            // Start connection or nothing will happen
//            connection.start();
//            LOGGER.info("FSM: clientQueue connected");
//
//            while (true) {
//                Message m = consumer.receiveNoWait();
//
//                if (m == null) {
//                    LOGGER.debug("FSM: total messages = {} - no message recieved.", total);
//                } else {
//                    message = m;
//                    break;
//                }
//
//                // Default Poll: every second.
//                TimeUnit.MILLISECONDS.sleep(Long.valueOf(frequency));
//            }
//
//            session.close();
//            connection.close();
//        } catch (Exception ex) {
//            LOGGER.error("FSM CONSUMER: EXCEPTION - {}", ex.toString());
//        } finally {
//
//        }

        // A important message has arrived! Go to the next state.
        state = State.MESSAGE;
    }

    private static void push() {
//        try {
//            Properties props = new Properties();
//            props.put(Context.PROVIDER_URL, providerUrl);
//
//            // Create a new context: loaded from jndi.properties
//            // The properties set above, overruled the jndi.properties
//            InitialContext ctx = new InitialContext(props);
//
//            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("connectionFactory");
//            Connection connection = factory.createConnection();
//            Queue queue = (javax.jms.Queue) ctx.lookup("clientQueue");
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            MessageProducer producer = session.createProducer(queue);
//
//            //TODO: currently send task - should send XML message to server.
//            // Also work to do on server to receive xml message
//            String action = "print";
//            String messageText = "This is a test message";
//            Message message = session.createTextMessage(messageText);
//            message.setStringProperty("guid", guid);
//            //Set a task: Print, Config, Registration, ...
//            message.setStringProperty("Action", action);
//            LOGGER.info("FSM PRODUCER: sending - {}", messageText);
//            CONSOLE.info("SENDING MESSAGE : {}", messageText);
//            producer.send(message);
//
//            // Clean up
//            session.close();
//            connection.close();
//        } catch (Exception ex) {
//            LOGGER.error("FSM PRODUCER: EXCEPTION: {}", ex.toString());
//        }
//        LOGGER.info("FSM PRODUCER: finished");
//
//        //Set task to polling
//        config.setProperty("task", "poll");
//        ConfigurationManager.writeConfiguration(config);

        state = State.CONFIG;
    }

    private static void message() {
        LOGGER.info("STATE = {}", state);
        total += 1;

        String type = "unknown";

        // Decides what to do with the message
        
//        //Get message
//        try {
//            type = message.getStringProperty("type");
//        } catch (JMSException ex) {
//            LOGGER.error("FSM MESSAGE: EXCEPTION: {}", ex.toString());
//            state = State.STOP;
//        }

//        if (type != null && type.equals("XML")) {
//            // Extract the message
//            String xml = "";
//            if (message != null && message instanceof TextMessage) {
//                TextMessage t = (TextMessage) message;
//                try {
//                    CONSOLE.info("TEXTMESSAGE RECEIVED: {}", t.getText());
//                    xml = t.getText();
//                } catch (JMSException ex) {
//                    LOGGER.error("JMS REMOTE CONSUMER: EXCEPTION - {}", ex.toString());
//                }
//            } else {
//                LOGGER.debug("JMS REMOTE CONSUMER: total messages = {} - non text message", total);
//            }
//
//            // Parse the XML
//            if (xml != null && !xml.isEmpty()) {
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder;
//
//                Document document = null;
//                try {
//                    builder = factory.newDocumentBuilder();
//                    document = builder.parse(new InputSource(new StringReader(xml)));
//                } catch (Exception e) {
//                    LOGGER.error("XM EXCEPTION - {}", e.toString());
//                }
//
//                Element element = document.getDocumentElement();
//
//                description = getTextValue("", element, "description");
//                action = getTextValue("", element, "action");
//                label = getTextValue("", element, "label");
//
//                LOGGER.info("description = {}", description);
//                LOGGER.info("action      = {}", action);
//                LOGGER.info("label       = {}", label);
//            }
//        } else if (type != null && type.equals("test")) {
//            // Extract the message
//            String xml = "";
//            if (message != null && message instanceof TextMessage) {
//                TextMessage t = (TextMessage) message;
//                try {
//                    CONSOLE.info("TEXTMESSAGE RECEIVED: {}", t.getText());
//                    xml = t.getText();
//                } catch (JMSException ex) {
//                    LOGGER.error("JMS REMOTE CONSUMER: EXCEPTION - {}", ex.toString());
//                }
//            } else {
//                LOGGER.debug("JMS REMOTE CONSUMER: total messages = {} - non text message", total);
//            }
//            ClientMessage cm = HandleMessage.unmarshalMessage(name);
//
//            description = cm.getGuid();
//            action = cm.getAction();
//            label = cm.getMessage();
//
//            LOGGER.info("description = {}", description);
//            LOGGER.info("action      = {}", action);
//            LOGGER.info("label       = {}", label);
//
//            LOGGER.debug("JMS REMOTE CONSUMER: total messages = {} - message rejected of type: {}", total, type);
//        } else {
//            //Do nothing
//            action = "reject";
//            LOGGER.debug("JMS REMOTE CONSUMER: total messages = {} - message rejected of type: {}", total, type);
//        }

        switch (action) {
            case "print":
                state = State.PRINT;
                break;
            case "config":
                state = State.CONFIG;
                break;
            case "registration":
                //TODO
                state = State.PRINT;
                break;
            case "approval":
                //TODO
                state = State.PRINT;
                break;
            case "close":
                state = State.STOP;
                break;
            default:
                //TODO
                state = State.POLL;
                break;
        }
    }

    private static void print() {
        LOGGER.info("STATE = {}", state);

//        if (label != null && !label.isEmpty()) {
//            TextMessage t = (TextMessage) message;
//            PrintLabel.printLabel(label);
//        } else {
//            LOGGER.debug("Empty message");
//        }

        // Move back to polling after a print.
        state = State.POLL;
    }

    private static void stop() {
        LOGGER.info("STATE = {}", state);
        // Go to the STOP state.
        state = State.STOP;
    }

    private static String getTextValue(String defaultValue, Element doc, String tag) {
        String value = defaultValue;
        NodeList nl;
        nl = doc.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
            value = nl.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }
}
