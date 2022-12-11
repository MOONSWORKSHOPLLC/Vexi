package vexi;

public class Config {

    public int wallpaper;

    public boolean useOldAnimations;

    public boolean useToggleSprint;

    public boolean useToggleSneak;

    public boolean Timechanger;

    public int Itemphisics;

    public int LeftHand;

    public int Togglesprint;

    public int Togglesneak;

    public int SkinChanger;

    public int ZoomAnimation;

    public int HitDelayFix;

    public int ArrowTrail;

    public int TntTimer;

    public int CustomCrosshair;

    public int Particle;

    public int enablecosmetics;

    public int Timer;

    public int Bossbar;

    public int OriginalScoreboard;

    public int BlockOverlay;

    public static float red;

    public static float green;

    public static float blue;

    public static float alpha;

    public static float thickness;

    public int RGBHits;

    public boolean AnimatedCape;

    public int MaxFrames;

    public float boostedFlySpeed;

    public int Time;

    public int Wings;

    public int WingsTexture;

    public int Bandana;

    public int BandanaTexture;

    public int Halo;

    public int HaloTexture;

    public int WitchHat;

    public int WitchHatTexture;

    public int BunnyEars;

    public int BunnyEarsTexture;

    public int Blaze;

    public int BlazeTexture;

    public String skinpath;

    public String capepath;

    public String capemode;

    public String ModColor;

    public int SkinType;

    public int Shoulder;

    public int BlazePet;

    public int SlimePet;

    public int GhastPet;

    public int BatPet;

    public int SquidPet;

    public int WitherPet;

    public int NoPet;

    public boolean showTutorial;

    public int armormode;

    public Config() {
        this.wallpaper = 1;
        this.armormode = 1;
        this.showTutorial = true;
        this.useOldAnimations = true;
        this.useToggleSprint = true;
        this.useToggleSneak = false;
        this.Timechanger = false;
        this.capemode = "ares";
        this.enablecosmetics = 1;
        this.Itemphisics = 1;
        this.LeftHand = 1;
        this.Togglesprint = 2;
        this.Togglesneak = 1;
        this.SkinChanger = 1;
        this.ZoomAnimation = 1;
        this.HitDelayFix = 2;
        this.ArrowTrail = 1;
        this.TntTimer = 1;
        this.CustomCrosshair = 1;
        this.Particle = 1;
        this.Timer = 1;
        this.Bossbar = 2;
        this.OriginalScoreboard = 1;
        this.BlockOverlay = 1;
        red = 0.0F;
        green = 0.0F;
        blue = 0.0F;
        alpha = 0.4F;
        thickness = 2.0F;
        this.RGBHits = 1;
        this.AnimatedCape = false;
        this.MaxFrames = 17;
        this.boostedFlySpeed = 1.0F;
        this.Time = 18000;
        this.Wings = 1;
        this.WingsTexture = 1;
        this.Bandana = 1;
        this.BandanaTexture = 1;
        this.Halo = 1;
        this.HaloTexture = 1;
        this.WitchHat = 1;
        this.WitchHatTexture = 1;
        this.BunnyEars = 1;
        this.BunnyEarsTexture = 2;
        this.Blaze = 1;
        this.BlazeTexture = 1;
        this.skinpath = "Ares/Skins/Default.png";
        this.capepath = "Ares/Capes/v3/cape1.png";
        this.ModColor = "§c";
        this.SkinType = 1;
        this.Shoulder = 1;
        this.BatPet = 1;
        this.SquidPet = 1;
        this.GhastPet = 1;
        this.SlimePet = 1;
        this.WitherPet = 1;
        this.BlazePet = 1;
        this.NoPet = 2;
    }
}
