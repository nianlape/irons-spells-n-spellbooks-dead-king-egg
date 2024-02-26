package io.redspace.ironsspellbooks.api.registry;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.spells.NoneSpell;
import io.redspace.ironsspellbooks.spells.blood.*;
import io.redspace.ironsspellbooks.spells.holy.*;
import io.redspace.ironsspellbooks.spells.ender.*;
import io.redspace.ironsspellbooks.spells.evocation.*;
import io.redspace.ironsspellbooks.spells.fire.*;
import io.redspace.ironsspellbooks.spells.ice.*;
import io.redspace.ironsspellbooks.spells.lightning.*;
import io.redspace.ironsspellbooks.spells.nature.*;
import io.redspace.ironsspellbooks.spells.eldritch.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SpellRegistry {
    public static final ResourceKey<Registry<AbstractSpell>> SPELL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(IronsSpellbooks.MODID, "spells"));
    private static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, IronsSpellbooks.MODID);
    public static final Supplier<IForgeRegistry<AbstractSpell>> REGISTRY = SPELLS.makeRegistry(() -> new RegistryBuilder<AbstractSpell>().disableSaving().disableOverrides());
    private static final NoneSpell noneSpell = new NoneSpell();

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    public static NoneSpell none() {
        return noneSpell;
    }

    private static DeferredHolder<EntityType<?>, AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static AbstractSpell getSpell(String spellId) {
        return getSpell(new ResourceLocation(spellId));
    }

    public static List<AbstractSpell> getEnabledSpells() {
        return SpellRegistry.REGISTRY.get()
                .getValues()
                .stream()
                .filter(AbstractSpell::isEnabled)
                .toList();
    }

    public static List<AbstractSpell> getSpellsForSchool(SchoolType schoolType) {

        var groupedBySchool = SpellRegistry.REGISTRY.get()
                .getValues()
                .stream()
                .collect(Collectors.groupingBy(AbstractSpell::getSchoolType));

        return groupedBySchool.get(schoolType);
    }

    public static AbstractSpell getSpell(ResourceLocation resourceLocation) {
        var spell = REGISTRY.get().getValue(resourceLocation);
        if (spell == null) {
            return noneSpell;
        }
        return spell;
    }

    //TODO: should the none spell be registered?

    // BLOOD
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ACUPUNCTURE_SPELL = registerSpell(new AcupunctureSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLOOD_NEEDLES_SPELL = registerSpell(new BloodNeedlesSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLOOD_SLASH_SPELL = registerSpell(new BloodSlashSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLOOD_STEP_SPELL = registerSpell(new BloodStepSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> DEVOUR_SPELL = registerSpell(new DevourSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> HEARTSTOP_SPELL = registerSpell(new HeartstopSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> RAISE_DEAD_SPELL = registerSpell(new RaiseDeadSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> RAY_OF_SIPHONING_SPELL = registerSpell(new RayOfSiphoningSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> WITHER_SKULL_SPELL = registerSpell(new WitherSkullSpell());
    //public static final DeferredHolder<EntityType<?>, AbstractSpell> SACRIFICE_SPELL = registerSpell(new SacrificeSpell());

    // ENDER
    public static final DeferredHolder<EntityType<?>, AbstractSpell> COUNTERSPELL_SPELL = registerSpell(new CounterspellSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> DRAGON_BREATH_SPELL = registerSpell(new DragonBreathSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> EVASION_SPELL = registerSpell(new EvasionSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> MAGIC_ARROW_SPELL = registerSpell(new MagicArrowSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> MAGIC_MISSILE_SPELL = registerSpell(new MagicMissileSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> STARFALL_SPELL = registerSpell(new StarfallSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> TELEPORT_SPELL = registerSpell(new TeleportSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SUMMON_ENDER_CHEST_SPELL = registerSpell(new SummonEnderChestSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> RECALL_SPELL = registerSpell(new RecallSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> PORTAL_SPELL = registerSpell(new PortalSpell());

    // EVOCATION
    public static final DeferredHolder<EntityType<?>, AbstractSpell> CHAIN_CREEPER_SPELL = registerSpell(new ChainCreeperSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FANG_STRIKE_SPELL = registerSpell(new FangStrikeSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FANG_WARD_SPELL = registerSpell(new FangWardSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FIRECRACKER_SPELL = registerSpell(new FirecrackerSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> GUST_SPELL = registerSpell(new GustSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> INVISIBILITY_SPELL = registerSpell(new InvisibilitySpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> LOB_CREEPER_SPELL = registerSpell(new LobCreeperSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SHIELD_SPELL = registerSpell(new ShieldSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SPECTRAL_HAMMER_SPELL = registerSpell(new SpectralHammerSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SUMMON_HORSE_SPELL = registerSpell(new SummonHorseSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SUMMON_VEX_SPELL = registerSpell(new SummonVexSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SLOW_SPELL = registerSpell(new SlowSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ARROW_VOLLEY_SPELL = registerSpell(new ArrowVolleySpell());

    // FIRE
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLAZE_STORM_SPELL = registerSpell(new BlazeStormSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BURNING_DASH_SPELL = registerSpell(new BurningDashSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FIREBALL_SPELL = registerSpell(new FireballSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FIREBOLT_SPELL = registerSpell(new FireboltSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FIRE_BREATH_SPELL = registerSpell(new FireBreathSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> MAGMA_BOMB_SPELL = registerSpell(new MagmaBombSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> WALL_OF_FIRE_SPELL = registerSpell(new WallOfFireSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> HEAT_SURGE_SPELL = registerSpell(new HeatSurgeSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FLAMING_STRIKE = registerSpell(new FlamingStrikeSpell());

    // HOLY
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ANGEL_WINGS_SPELL = registerSpell(new AngelWingsSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLESSING_OF_LIFE_SPELL = registerSpell(new BlessingOfLifeSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> CLOUD_OF_REGENERATION_SPELL = registerSpell(new CloudOfRegenerationSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FORTIFY_SPELL = registerSpell(new FortifySpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> GREATER_HEAL_SPELL = registerSpell(new GreaterHealSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> GUIDING_BOLT_SPELL = registerSpell(new GuidingBoltSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> HEALING_CIRCLE_SPELL = registerSpell(new HealingCircleSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> HEAL_SPELL = registerSpell(new HealSpell());
    //public static final DeferredHolder<EntityType<?>, AbstractSpell> SUNBEAM_SPELL = registerSpell(new SunbeamSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> WISP_SPELL = registerSpell(new WispSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> DIVINE_SMITE_SPELL = registerSpell(new DivineSmiteSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> HASTE_SPELL = registerSpell(new HasteSpell());

    // ICE
    public static final DeferredHolder<EntityType<?>, AbstractSpell> CONE_OF_COLD_SPELL = registerSpell(new ConeOfColdSpell());
    //    public static final DeferredHolder<EntityType<?>, AbstractSpell> FROSTBITE_SPELL = registerSpell(new FrostbiteSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FROST_STEP_SPELL = registerSpell(new FrostStepSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ICE_BLOCK_SPELL = registerSpell(new IceBlockSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ICICLE_SPELL = registerSpell(new IcicleSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SUMMON_POLAR_BEAR_SPELL = registerSpell(new SummonPolarBearSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> RAY_OF_FROST_SPELL = registerSpell(new RayOfFrostSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FROSTWAVE_SPELL = registerSpell(new FrostwaveSpell());

    // LIGHTNING
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ASCENSION_SPELL = registerSpell(new AscensionSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> CHAIN_LIGHTNING_SPELL = registerSpell(new ChainLightningSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> CHARGE_SPELL = registerSpell(new ChargeSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ELECTROCUTE_SPELL = registerSpell(new ElectrocuteSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> LIGHTNING_BOLT_SPELL = registerSpell(new LightningBoltSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> LIGHTNING_LANCE_SPELL = registerSpell(new LightningLanceSpell());
    //public static final DeferredHolder<EntityType<?>, AbstractSpell> THUNDER_STEP_SPELL = registerSpell(new ThunderStepSpell());

    // NATURE
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ACID_ORB_SPELL = registerSpell(new AcidOrbSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLIGHT_SPELL = registerSpell(new BlightSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> POISON_ARROW_SPELL = registerSpell(new PoisonArrowSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> POISON_BREATH_SPELL = registerSpell(new PoisonBreathSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> POISON_SPLASH_SPELL = registerSpell(new PoisonSplashSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ROOT_SPELL = registerSpell(new RootSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SPIDER_ASPECT_SPELL = registerSpell(new SpiderAspectSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> FIREFLY_SWARM_SPELL = registerSpell(new FireflySwarmSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> OAKSKIN_SPELL = registerSpell(new OakskinSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> EARTHQUAKE_SPELL = registerSpell(new EarthquakeSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> STOMP_SPELL = registerSpell(new StompSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> GLUTTONY_SPELL = registerSpell(new GluttonySpell());

    //VOID
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ABYSSAL_SHROUD_SPELL = registerSpell(new AbyssalShroudSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> BLACK_HOLE_SPELL = registerSpell(new BlackHoleSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SCULK_TENTACLES_SPELL = registerSpell(new SculkTentaclesSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> SONIC_BOOM_SPELL = registerSpell(new SonicBoomSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> PLANAR_SIGHT_SPELL = registerSpell(new PlanarSightSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> TELEKINESIS_SPELL = registerSpell(new TelekinesisSpell());
    public static final DeferredHolder<EntityType<?>, AbstractSpell> ELDRITCH_BLAST_SPELL = registerSpell(new EldritchBlastSpell());
}
