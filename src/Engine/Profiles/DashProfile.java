package Engine.Profiles;

public class DashProfile {
    BaseProfile profile;
    public double dashSpeed;
    public double dashForce;

    public DashProfile(BaseProfile profile, double dashSpeed, double dashForce) {

        this.profile = profile;
        this.dashSpeed = dashSpeed;
        this.dashForce = dashForce;
    }
}

