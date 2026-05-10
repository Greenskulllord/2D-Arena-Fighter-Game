package Engine.System;
import Engine.Components.InputComponent;
import Engine.Components.StateComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import Engine.Events.EventBus;
import Engine.Events.InputEvent;

public class InputBufferingSystem {
    EventBus bus;

    public InputBufferingSystem (EventBus bus) {
        this.bus = bus;

    }

    public void OnInput(InputEvent event) {
        InputComponent input = event.A.getComponent(InputComponent.class);

        if(input != null) {
            input.add(event);
        }
    }


    public void update(double DeltaTime) {

        for (Entity A : ActiveEntities.getActiveEntities()) {
            boolean canExecute;
            double timePassed;


            StateComponent stateA = A.getComponent(StateComponent.class);
            InputComponent inputA = A.getComponent(InputComponent.class);


            if (stateA == null || inputA == null) continue;  //look for another entity


            for (InputEvent event : inputA.queueList) {
                event.inputTime += DeltaTime;
            }




            //delete events/empty the list
            while(!inputA.queueList.isEmpty()) {
                
                //math to handle the buffer expiration number
                double startD = inputA.peek().requestedProfile.startUpDuration;
                double ActiveD = inputA.peek().requestedProfile.activeDuration;
                double RecoveryD = inputA.peek().requestedProfile.recoveryDuration;
                double bufferExpiration = startD + ActiveD + RecoveryD;

                if (inputA.peek().inputTime >= bufferExpiration) {
                    inputA.consume();
                }

                else break;
            }


           if (inputA.queueList.isEmpty()) continue;

           //gathering data and saving to local variables
           timePassed = stateA.stateTimer;
           canExecute = false;
           stateA.desiredState = inputA.peek().state;
           stateA.localCurrentState = stateA.getCurrentState();


            //go through all the checks to see if execute can be true
           if (stateA.localCurrentState == StateComponent.state.IDLE || stateA.localCurrentState == StateComponent.state.MOVING) {
               canExecute = true;

           }


           else if (stateA.localCurrentState == StateComponent.state.ATTACK_RECOVERY
           || stateA.localCurrentState == StateComponent.state.DASH_RECOVERY
           || stateA.localCurrentState == StateComponent.state.PARRY_RECOVERY) {

               if (timePassed > stateA.Threshold) {
                   canExecute = true;

               }
           }

           else if (stateA.localCurrentState == StateComponent.state.ATTACK_STARTUP
                   || stateA.localCurrentState == StateComponent.state.ATTACK_ACTIVE) {

               if (timePassed > stateA.Threshold) {
                   canExecute = true;
               }
           }

            if (canExecute) {

//                System.out.print("desired state: " + stateA.desiredState + "\n");
//                System.out.print("current state: " + stateA.localCurrentState + "\n");
//                System.out.print("can execute: " + canExecute + "\n");
//                System.out.print("---------------------------------------\n");


                stateA.changeState(stateA.desiredState); //change enum state
                stateA.currentProfile = inputA.peek().requestedProfile; //equip attack
                inputA.consume(); //delete the used up stat

            }
        }
    }
}
