package io.redspace.ironsspellbooks.compat.supplementaries;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SupplementariesDummyImpl implements ISupplementariesProxy {
    @Override
    public StructureTemplate.StructureBlockInfo handleStructureProcessor(@NotNull LevelReader level, @NotNull BlockPos jigsawPiecePos, @NotNull BlockPos jigsawPieceBottomCenterPos, @NotNull StructureTemplate.StructureBlockInfo blockInfoLocal, @NotNull StructureTemplate.StructureBlockInfo blockInfoGlobal, @NotNull StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        //Do nothing
        return blockInfoGlobal;
    }
}
