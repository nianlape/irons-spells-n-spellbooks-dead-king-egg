package io.redspace.ironsspellbooks.loot;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.util.LazyOptional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpellFilter {
    SchoolType schoolType = null;
    List<AbstractSpell> spells = new ArrayList<>();
    static final LazyOptional<List<AbstractSpell>> DEFAULT_SPELLS = LazyOptional.of(() -> SpellRegistry.REGISTRY.get().getValues().stream().filter(AbstractSpell::allowLooting).toList());
    static final LazyOptional<Map<SchoolType, List<AbstractSpell>>> SPELLS_FOR_SCHOOL = LazyOptional.of(() -> SchoolRegistry.REGISTRY.get().getValues().stream().collect(Collectors.toMap((school -> school), (school -> SpellRegistry.getSpellsForSchool(school).stream().filter(AbstractSpell::allowLooting).toList()))));

    public static final Codec<SpellFilter> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                // ExtraCodecs.either(
                ExtraCodecs.NON_EMPTY_STRING.optionalFieldOf("school").forGetter(filter -> filter.schoolType.getId().toString().describeConstable()),
                ExtraCodecs.nonEmptyList(ExtraCodecs.NON_EMPTY_STRING.listOf()).optionalFieldOf("spells").forGetter(filter -> Optional.of(((SpellFilter) filter).spells.stream().map(AbstractSpell::getSpellId).toList()))
                //)
        ).apply(instance, SpellFilter::new);
    });

    public SpellFilter(String school) {

    }

    public SpellFilter(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public SpellFilter(List<AbstractSpell> spells) {
        this.spells = spells;
    }

    public SpellFilter() {
    }

    public SpellFilter(Optional<String> s, Optional<List<String>> strings) {

    }

    public boolean isFiltered() {
        return schoolType != null || !spells.isEmpty();
    }

    public List<AbstractSpell> getApplicableSpells() {
        try {
            if (!spells.isEmpty()) {
                return spells;
            } else if (schoolType != null) {
                return SPELLS_FOR_SCHOOL.resolve().get().get(schoolType);
            } else {
                return DEFAULT_SPELLS.resolve().get();
            }
        } catch (Exception e) {
            IronsSpellbooks.LOGGER.error("SpellFilter failure: {}", e.getMessage());
            return List.of(SpellRegistry.none());
        }

    }

    public AbstractSpell getRandomSpell(RandomSource random, Predicate<AbstractSpell> filter) {
        //Will throw a non fatal error if the filter empties the list
        var spells = getApplicableSpells().stream().filter(filter).toList();
        return spells.get(random.nextInt(spells.size()));
    }

    public AbstractSpell getRandomSpell(RandomSource randomSource) {
        return getRandomSpell(randomSource, (spell -> spell.isEnabled() && spell != SpellRegistry.none() && spell.allowLooting()));
    }

    public static SpellFilter deserializeSpellFilter(JsonObject json) {
        if (GsonHelper.isValidNode(json, "school")) {
            var schoolType = GsonHelper.getAsString(json, "school");
            return new SpellFilter(SchoolRegistry.getSchool(new ResourceLocation(schoolType)));
        } else if (GsonHelper.isArrayNode(json, "spells")) {
            var spellsFromJson = GsonHelper.getAsJsonArray(json, "spells");
            List<AbstractSpell> applicableSpellList = new ArrayList<>();
            for (JsonElement element : spellsFromJson) {
                String spellId = element.getAsString();

                var spell = SpellRegistry.getSpell(spellId);

                if (spell != SpellRegistry.none()) {
                    applicableSpellList.add(spell);
                }
            }
            return new SpellFilter(applicableSpellList);
        } else {
            return new SpellFilter();
        }
    }

    private void deserializeSchool() {

    }
}