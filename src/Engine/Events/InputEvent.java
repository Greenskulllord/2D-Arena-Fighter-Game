package Engine.Events;
import Engine.Components.StateComponent;
import Engine.Core.Entity;
import Engine.Profiles.BaseProfile;

public class InputEvent implements IEvent{

    public StateComponent.state state;
    public double DeltaTime;
    public double inputTime = 0;
    public BaseProfile requestedProfile;
    public Entity A;

    public InputEvent(Entity EntityA, double DeltaTime, StateComponent.state state, BaseProfile profile) {
        this.DeltaTime = DeltaTime;
        this.state = state;
        this.requestedProfile = profile;
        this.A = EntityA;

    }
}
