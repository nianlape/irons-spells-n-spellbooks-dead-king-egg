package io.redspace.ironsspellbooks.compat.supplementaries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.mehvahdjukaar.supplementaries.common.block.blocks.LightUpBlock;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SupplementariesActualImpl implements ISupplementariesProxy {

    public SupplementariesActualImpl(){
        IronsSpellbooks.LOGGER.debug("Loading SupplementariesActualImpl");
    }
    @Override
    public StructureTemplate.StructureBlockInfo handleStructureProcessor(@NotNull LevelReader level, @NotNull BlockPos jigsawPiecePos, @NotNull BlockPos jigsawPieceBottomCenterPos, @NotNull StructureTemplate.StructureBlockInfo blockInfoLocal, @NotNull StructureTemplate.StructureBlockInfo blockInfoGlobal, @NotNull StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        IronsSpellbooks.LOGGER.debug("SupplementariesActualImpl.handleStructureProcessor: {}", blockInfoGlobal.state.getBlock().getName().getString());
        if (blockInfoGlobal.state.is(Blocks.LANTERN) && blockInfoGlobal.state.getValue(LanternBlock.HANGING)) {
            IronsSpellbooks.LOGGER.debug("SupplementariesActualImpl.handleStructureProcessor: replacing hanging lantern");
            BlockState blockState = ModRegistry.CANDLE_HOLDERS.get(null).get().defaultBlockState().setValue(CandleHolderBlock.FACE, AttachFace.CEILING).setValue(LightUpBlock.LIT, true);
            handleBlockReplacement(level, blockInfoGlobal.pos, blockState);
        }
        return blockInfoGlobal;
    }

    private void handleBlockReplacement(LevelReader level, BlockPos pos, BlockState blocksState) {
        ChunkPos chunkPos = new ChunkPos(pos);
        ChunkAccess chunk = level.getChunk(chunkPos.x, chunkPos.z);
        int sectionIndex = chunk.getSectionIndex(pos.getY());

        // if section index is < 0 we are out of bounds
        if (sectionIndex >= 0) {
            LevelChunkSection section = chunk.getSection(sectionIndex);
            this.setBlock(section, pos, blocksState);
        }
    }

    private void setBlock(LevelChunkSection section, BlockPos pos, BlockState state) {
        section.setBlockState(SectionPos.sectionRelative(pos.getX()), SectionPos.sectionRelative(pos.getY()), SectionPos.sectionRelative(pos.getZ()), state);
    }
}
