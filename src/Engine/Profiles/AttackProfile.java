package Engine.Profiles;

public class AttackProfile {

    BaseProfile profile;
    public double attackForce;
    public double hitBoxWidth;
    public double hitBoxHeight;
    public double hitBoxRadius;

    public AttackProfile(BaseProfile profile, double attackForce, double hitBoxWidth, double hitBoxHeight, double hitBoxRadius) {

        this.profile = profile;
        this.attackForce = attackForce;
        this.hitBoxWidth = hitBoxWidth;
        this.hitBoxHeight = hitBoxHeight;
        this.hitBoxRadius = hitBoxRadius;

    }
}
