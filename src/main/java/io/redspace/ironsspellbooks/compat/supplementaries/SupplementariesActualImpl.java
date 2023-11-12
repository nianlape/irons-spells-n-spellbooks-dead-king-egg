package io.redspace.ironsspellbooks.compat.supplementaries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.util.Utils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.mehvahdjukaar.supplementaries.common.block.blocks.LightUpBlock;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class SupplementariesActualImpl implements ISupplementariesProxy {

    public SupplementariesActualImpl() {
        IronsSpellbooks.LOGGER.debug("Loading SupplementariesActualImpl");
    }

    static final LazyOptional<Object2ObjectOpenHashMap<Block, BlockReplacement>> REPLACEMENT_MAP = LazyOptional.of(() -> new Object2ObjectOpenHashMap<>(
            Map.of(
                    Blocks.LANTERN, new BlockReplacement((blockState) -> blockState.getValue(LanternBlock.HANGING), (beforeState) -> ModRegistry.CANDLE_HOLDERS.get(null).get().defaultBlockState().setValue(CandleHolderBlock.FACE, AttachFace.CEILING).setValue(LightUpBlock.LIT, true), 1f),
                    Blocks.SOUL_LANTERN, new BlockReplacement((blockState) -> blockState.getValue(LanternBlock.HANGING), (beforeState) -> ModRegistry.CANDLE_HOLDERS.get(DyeColor.LIGHT_BLUE).get().defaultBlockState().setValue(CandleHolderBlock.FACE, AttachFace.CEILING).setValue(LightUpBlock.LIT, true), 1f),
                    Blocks.CANDLE, new BlockReplacement((blockState) -> true, (beforeState) -> ModRegistry.CANDLE_HOLDERS.get(null).get().defaultBlockState().setValue(CandleHolderBlock.CANDLES, beforeState.getValue(CandleBlock.CANDLES)).setValue(LightUpBlock.LIT, beforeState.getValue(CandleBlock.LIT)).setValue(CandleHolderBlock.FACING, Utils.random.nextBoolean() ? Direction.EAST : Direction.NORTH), .15f)
            )
    ));

    @Override
    public StructureTemplate.StructureBlockInfo handleStructureProcessor(@NotNull LevelReader level, @NotNull BlockPos jigsawPiecePos, @NotNull BlockPos jigsawPieceBottomCenterPos, @NotNull StructureTemplate.StructureBlockInfo blockInfoLocal, @NotNull StructureTemplate.StructureBlockInfo blockInfoGlobal, @NotNull StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        //IronsSpellbooks.LOGGER.debug("SupplementariesActualImpl.handleStructureProcessor: {}", blockInfoGlobal.state.getBlock().getName().getString());
        BlockReplacement replacement = REPLACEMENT_MAP.resolve().get().get(blockInfoGlobal.state.getBlock());
        if (replacement != null) {
            if (replacement.probability < 1f && Utils.random.nextFloat() > replacement.probability && replacement.match.test(blockInfoGlobal.state)) {
                handleBlockReplacement(level, blockInfoGlobal.pos, replacement.replacement.apply(blockInfoGlobal.state));
            }
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
            section.setBlockState(SectionPos.sectionRelative(pos.getX()), SectionPos.sectionRelative(pos.getY()), SectionPos.sectionRelative(pos.getZ()), blocksState);

        }
    }

    record BlockReplacement(Predicate<BlockState> match, Function<BlockState, BlockState> replacement,
                            float probability) {
    }
}
