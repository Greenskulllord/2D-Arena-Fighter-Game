package Engine.Components;

import Engine.Core.Component;
import Engine.Profiles.BaseProfile;

public class StateComponent implements Component {



    //list of potential states
    public enum state {
        IDLE,
        MOVING,
        ATTACK_STARTUP,
        ATTACK_ACTIVE,
        ATTACK_RECOVERY,
        DASH_STARTUP,
        DASH_ACTIVE,
        DASH_RECOVERY,
        GUARDING,
        BLOCK_STUN,
        PARRY_ACTIVE,
        PARRY_RECOVERY,
        PARRY_SUCCESS,
        HIT_STUN,
        DEAD,
        CIRCLING
    }


    public double stateTimer; //how long Entity has been in the state
    public double Threshold;
    private state currentState;
    public state desiredState;
    public state localCurrentState;
    public BaseProfile currentProfile;
    public int comboStep = 0;


    public StateComponent (double Threshold) {
        this.currentState = state.IDLE;
        this.Threshold = Threshold;

    }

    public void changeState(state state) {
        currentState = state;
        stateTimer = 0;
    }

    @Override
    public void update(double DeltaTime) {
        //passes time based on how long its been in the state
        stateTimer += DeltaTime;
    }

    public state getCurrentState() {
        return currentState;
    }

    public void setCurrentState(state currentState) {
        this.currentState = currentState;
    }

}
