package researchersmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import researchersmod.cardmods.DoubleDamageOnce;
import researchersmod.cards.BaseCard;
import researchersmod.cards.common.EncryptData;
import researchersmod.cards.common.Exploit;
import researchersmod.cards.common.RagingBlade;
import researchersmod.cards.rare.AccretionDisc;
import researchersmod.cards.rare.Centrifuge;
import researchersmod.cards.rare.O5Command;
import researchersmod.cards.rare.OrbitalStrike;
import researchersmod.cards.targeting.ExperimentTargeting;
import researchersmod.cards.uncommon.Entropy;
import researchersmod.cards.uncommon.FerrousBlade;
import researchersmod.cards.uncommon.TeslaCoil;
import researchersmod.character.ResearchersCharacter;
import researchersmod.potions.BasePotion;
import researchersmod.relics.*;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.ui.ModConfig;
import researchersmod.util.GeneralUtils;
import researchersmod.util.KeywordInfo;
import researchersmod.util.PowerIDWrapper;
import researchersmod.util.TextureLoader;
import researchersmod.variables.EthericVariable;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.unlockProgress;

@SpireInitializer
public class Researchers implements
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        PostExhaustSubscriber,
        OnPlayerTurnStartSubscriber,
        PostBattleSubscriber,
        SetUnlocksSubscriber,
        StartGameSubscriber,
        PostUpdateSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new Researchers();
        ResearchersCharacter.Meta.registerColor();

    }

    public static float time = 0f;
    @Override
    public void receivePostUpdate() {
        time += Gdx.graphics.getDeltaTime();
    }

    public static int expsTerminatedThisTurn;
    public static int expsTerminatedThisCombat;
    public static int expsCompletedThisTurn;
    public static int expsCompletedThisCombat;
    public static int cardsPhasedThisTurn;
    public static int cardsPhasedThisCombat;
    public static AbstractCard LastPhasedCard;

    public Researchers() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        registerPotions();
        registerSnapshotPowers();
        Colors.put("researchersmod:DB", new Color(0f/255f, 149f/255f, 1.0f, 1.0f));
        //If you want to set up a config panel, that will be done here.
        //You can find information about this on the BaseMod wiki page "Mod Config and Panel".
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, new ModConfig());
        CustomTargeting.registerCustomTargeting(ExperimentTargeting.EXPERIMENT, new ExperimentTargeting());
        if(!ModConfig.enableUnlocks) unlockBundles();
        if(ModConfig.enableCentrifuge) {
            CardLibrary.add(new Centrifuge());
        }
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    public static void registerPotions() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BasePotion.class) //In the same package as this class
                .any(BasePotion.class, (info, potion) -> { //Run this code for any classes that extend this class
                    //These three null parameters are colors.
                    //If they're not null, they'll overwrite whatever color is set in the potions themselves.
                    //This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    //playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });
    }


    public static HashMap<String, PowerIDWrapper> statSnapshotting = new HashMap<>();

    public static void registerSnapshotPowers() {
        statSnapshotting.put(VigorPower.POWER_ID,new PowerIDWrapper(true));
        statSnapshotting.put(PenNibPower.POWER_ID,new PowerIDWrapper(2,true));
        statSnapshotting.put(DoubleDamageOnce.ID,new PowerIDWrapper(2,true));
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION,info.COLOR);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = Researchers.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + Researchers.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(Researchers.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        ResearchersCharacter.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new EthericVariable());
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(!ModConfig.enableUnlocks) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen || !ModConfig.enableUnlocks)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    public static int CardsExhaustedThisTurn = 0;
    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        CardsExhaustedThisTurn++;
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        CardsExhaustedThisTurn = 0;
        expsCompletedThisTurn = 0;
        expsTerminatedThisTurn = 0;
        cardsPhasedThisTurn = 0;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        expsTerminatedThisCombat = 0;
        expsCompletedThisCombat = 0;
        CardsExhaustedThisTurn = 0;
        cardsPhasedThisTurn = 0;
        cardsPhasedThisCombat = 0;
        expsCompletedThisTurn = 0;
        expsTerminatedThisTurn = 0;
        LastPhasedCard = null;
    }

    private void unlockBundles() {
        for (int y = 4; y >= 0; y--) {
            ArrayList<AbstractUnlock> unlockBundle = UnlockTracker.getUnlockBundle(ResearchersCharacter.Meta.RESEARCHERS, y);
            switch (((AbstractUnlock) unlockBundle.get(0)).type) {
                case CARD:
                    for (AbstractUnlock unlock : unlockBundle) {
                        UnlockTracker.unlockCard(((AbstractUnlock) unlock).card.cardID);
                    }
                    break;
                case RELIC:
                    for (AbstractUnlock abstractUnlock : unlockBundle) {
                        UnlockTracker.hardUnlockOverride(((AbstractUnlock) abstractUnlock).relic.relicId);
                        UnlockTracker.markRelicAsSeen(((AbstractUnlock) abstractUnlock).relic.relicId);
                    }
                    break;
            }
            unlockProgress.putInteger(ResearchersCharacter.Meta.RESEARCHERS.toString() + "UnlockLevel", 5);
        }
    }

    @Override
    public void receiveSetUnlocks() {
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, Exploit.ID, TeslaCoil.ID, OrbitalStrike.ID), ResearchersCharacter.Meta.RESEARCHERS, 0);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, OccamsRazor.ID, MarketGardener.ID, TripleTechChambers.ID), ResearchersCharacter.Meta.RESEARCHERS, 1);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, EncryptData.ID, Entropy.ID, O5Command.ID), ResearchersCharacter.Meta.RESEARCHERS, 2);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, ElectromagneticEqualizer.ID, HypnoticWatch.ID, BehaviorAdjustment.ID), ResearchersCharacter.Meta.RESEARCHERS, 3);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, RagingBlade.ID, FerrousBlade.ID, AccretionDisc.ID), ResearchersCharacter.Meta.RESEARCHERS, 4);
    }

    @Override
    public void receiveStartGame() {
        expsTerminatedThisCombat = 0;
        expsCompletedThisCombat = 0;
        CardsExhaustedThisTurn = 0;
        cardsPhasedThisTurn = 0;
        cardsPhasedThisCombat = 0;
        expsCompletedThisTurn = 0;
        expsTerminatedThisTurn = 0;
        LastPhasedCard = null;
        ExperimentCardManager.experiments.group.clear();
    }
}
