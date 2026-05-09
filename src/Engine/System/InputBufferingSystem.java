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
                if (inputA.peek().inputTime >= 1) {
                    inputA.consume();
                    System.out.print(inputA.queueList.peek() + " has been deleted\n");
                }

                else break;
            }


           if (inputA.queueList.isEmpty()) continue;

           //gathering data and saving to local variables
           timePassed = stateA.stateTimer;
           canExecute = false;
           stateA.desiredState = inputA.peek().state;
           stateA.localCurrentState = stateA.getCurrentState();


           System.out.print("---------------------------------------\n");
           System.out.print("can execute: " + canExecute + "\n");
           System.out.print("desired state: " + stateA.desiredState + "\n");
           System.out.print("current state: " + stateA.localCurrentState + "\n");
           System.out.print("time passed: " + timePassed + "\n");



           //go through all the checks to see if execute can be true
           if (stateA.localCurrentState == StateComponent.state.IDLE || stateA.localCurrentState == StateComponent.state.MOVING) {
               canExecute = true;
               System.out.print("can execute: " + canExecute + "\n");
               System.out.print("---------------------------------------\n");
           }


           else if (stateA.localCurrentState == StateComponent.state.ATTACK_RECOVERY
           || stateA.localCurrentState == StateComponent.state.DASH_RECOVERY
           || stateA.localCurrentState == StateComponent.state.PARRY_RECOVERY) {

               if (timePassed > stateA.Threshold) {
                   canExecute = true;
                   System.out.print("can execute: " + canExecute + "\n");
                   System.out.print("---------------------------------------\n");
               }
           }

            System.out.print("---------------------------------------\n");

            if (canExecute) {
                stateA.currentProfile = inputA.peek().requestedProfile; //equip attack
                stateA.changeState(stateA.desiredState); //change enum state
                inputA.consume(); //delete the used up stat

            }
        }
    }
}
