package Engine.Profiles;

public class DashProfile {
    BaseProfile profile;
    public double dashSpeed;
    public double dashForce;
    public double dashCooldown;
    public double dashDuration;

    public DashProfile(BaseProfile profile, double dashSpeed, double dashForce, double dashCooldown, double dashDuration) {

        this.profile = profile;
        this.dashSpeed = dashSpeed;
        this.dashForce = dashForce;
        this.dashCooldown = dashCooldown;
        this.dashDuration = dashDuration;
    }
}

