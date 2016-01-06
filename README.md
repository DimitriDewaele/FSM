# Finite State Machine

WIP of FSM

## FSM

Add diagram

## Related Technologies

 * ActiveMQ
 * JAXB

## Build

### Basic build

Build with:

    mvn clean package

Result jar in: /target/

### Get dependencies

Copy all dependencies:

	mvn dependency:copy-dependencies

Result in: /target/dependency

Start the FSM:

	java -cp "target/FSM-1.0-SNAPSHOT.jar;target/dependency/*" be.softwarelab.fsm.FSM

Build jar with all dependencies:

### Assembly

Add assembly to pom.

Build with compile (Compile goal should be added before assembly:single or otherwise the code on your own project is not included)

	mvn clean compile assembly:single

Add executions to build automatically during install.

	mvn install

Run with:

	java -cp "target/FSM-1.0-SNAPSHOT-jar-with-dependencies.jar"

	java -jar target/FSM-1.0-SNAPSHOT-jar-with-dependencies.jar 