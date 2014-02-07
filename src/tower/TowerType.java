package tower;

/**
 * Created by Jurian on 25-1-14.
 */
public enum TowerType {
    BASE("res/towers/base.png","Base"),
    //Damage towers
    CANNON("res/towers/cannon.png","Cannon"),
    GATLING("res/towers/gatling.png","Gatling"),
    LIGHTNING("res/towers/lightning.png","Lightning"),
    ACP("res/towers/acp.png","ACP"),
    VIOLATOR("res/towers/violator.png","Violator"),
    ROCKET("res/towers/rocket.png","Rocket"),
    SCATTER("res/towers/scatter.png","Scatter"),
    FOCUS("res/towers/focus.png","Focus"),
    MAKESHIFT_CANNON("res/towers/ms_cannon.png","Makeshift Cannon"),
    FRIENDSHIP_LASER("res/towers/fs_laser.png","Friendship Laser"),
    ANTI_AIR("res/towers/anti_air.png","Anti-Air"),
    DRONE("res/towers/drone.png","Drone"),
    ORBITAL("res/towers/orbital.png","Orbital Strike Relay"),
    //Dispenser towers
    SLOW_FIELD("res/towers/slow_field.png","Slow Field Dispenser"),
    AR_MINE("res/towers/ar_mine.png","AR-Mine Dispenser"),
    RUPTURE_MINE("res/towers/rupture_mine.png","Rupture Mine Dispenser"),
    //Other towers
    KAIROS("res/towers/kairos.png","Kairos"),
    AMP_SPIRE("res/towers/amp_spire.png","AMP Spire"),
    RANGE_SPIRE("res/towers/range_spire.png","Range Spire"),
    MIND_CONTROL("res/towers/mind_control.png","Mind Control Spire");

    public final String location;
    public final String description;

    TowerType(String location, String description) {
        this.location = location;
        this.description = description;
    }
}
