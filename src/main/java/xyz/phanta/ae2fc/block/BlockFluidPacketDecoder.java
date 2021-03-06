package xyz.phanta.ae2fc.block;

import appeng.block.AEBaseTileBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.phanta.ae2fc.inventory.GuiType;
import xyz.phanta.ae2fc.inventory.InventoryHandler;
import xyz.phanta.ae2fc.tile.TileFluidPacketDecoder;

public class BlockFluidPacketDecoder extends AEBaseTileBlock {

    public BlockFluidPacketDecoder() {
        super(Material.IRON);
        setTileEntity(TileFluidPacketDecoder.class);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }
        TileFluidPacketDecoder tile = getTileEntity(world, pos);
        if (tile != null) {
            if (!world.isRemote) {
                InventoryHandler.openGui(player, world, pos, facing, GuiType.FLUID_PACKET_DECODER);
            }
            return true;
        }
        return false;
    }

}
