package Engine.Profiles;

public class AttackProfile {

    BaseProfile profile;
    public double attackForce;
    public double attackSpeed;
    public double hitBoxWidth;
    public double hitBoxHeight;
    public double hitBoxRadius;

    public AttackProfile(BaseProfile profile, double attackForce, double hitBoxWidth,
                         double hitBoxHeight, double hitBoxRadius, double attackSpeed) {

        this.profile = profile;
        this.attackForce = attackForce;
        this.attackSpeed = attackSpeed;


        this.hitBoxWidth = hitBoxWidth;
        this.hitBoxHeight = hitBoxHeight;
        this.hitBoxRadius = hitBoxRadius;
    }
}
