package io.redspace.ironsspellbooks.worldgen;

import com.mojang.serialization.Codec;
import io.redspace.ironsspellbooks.compat.supplementaries.SupplementariesProxy;
import io.redspace.ironsspellbooks.registries.StructureProcessorRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class SupplementariesCompatProcessor extends StructureProcessor {

    public static final Codec<SupplementariesCompatProcessor> CODEC = Codec.unit(SupplementariesCompatProcessor::new);

    public SupplementariesCompatProcessor() {

    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(@Nonnull LevelReader level, @Nonnull BlockPos jigsawPiecePos, @Nonnull BlockPos jigsawPieceBottomCenterPos, @Nonnull StructureTemplate.StructureBlockInfo blockInfoLocal, @Nonnull StructureTemplate.StructureBlockInfo blockInfoGlobal, @Nonnull StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        return SupplementariesProxy.PROXY.handleStructureProcessor(level, jigsawPiecePos, jigsawPieceBottomCenterPos, blockInfoLocal, blockInfoGlobal, settings, template);
    }

    @Nonnull
    @Override
    protected StructureProcessorType<?> getType() {
        return StructureProcessorRegistry.SUPPLEMENTARIES_COMPAT.get();
    }
}