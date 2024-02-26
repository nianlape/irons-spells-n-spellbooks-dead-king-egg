package io.redspace.ironsspellbooks.api.registry;

import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraftforge.registries.RegistryObject;

public class SchoolRegistryHolder {

    DeferredHolder<EntityType<?>, SchoolType> registrySchool;

    public SchoolRegistryHolder(DeferredHolder<EntityType<?>, SchoolType> registrySchool) {
        this.registrySchool = registrySchool;
    }

    public SchoolType get() {
        return registrySchool.get();
    }
}
