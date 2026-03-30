package Engine.Core;

public interface Component {
    //update interface. make every component go off of DeltaTime
    public void update(double DeltaTime);
}
