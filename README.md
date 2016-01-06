# Finite State Machine

WIP of FSM

## Build

Build with:

    mvn clean package

Result jar in: /target/

Copy all dependencies:

	mvn dependency:copy-dependencies

Result in: /target/dependency

Start the FSM:

	java -cp "target/FSM-1.0-SNAPSHOT.jar;target/dependency/*" be.softwarelab.fsm.FSM

