package Engine.Core;

import java.io.FileNotFoundException;

public interface Component {
    //update interface. make every component go off of DeltaTime
    public void update(double DeltaTime) throws FileNotFoundException;
}
