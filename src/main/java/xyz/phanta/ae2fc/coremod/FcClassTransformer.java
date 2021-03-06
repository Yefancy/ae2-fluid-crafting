package xyz.phanta.ae2fc.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import xyz.phanta.ae2fc.coremod.transform.*;

public class FcClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] code) {
        Transform tform;
        switch (transformedName) {
            case "appeng.crafting.CraftingTreeNode":
                tform = CraftingTreeNodeTransformer.INSTANCE;
                break;
            case "appeng.helpers.DualityInterface":
                tform = DualityInterfaceTransformer.INSTANCE;
                break;
            case "appeng.me.cluster.implementations.CraftingCPUCluster":
                tform = CraftingCpuTransformer.INSTANCE;
                break;
            case "thelm.packagedauto.integration.appeng.recipe.PackageCraftingPatternHelper":
                tform = PautoCraftingPatternHelperTransformer.TFORM_INPUTS;
                break;
            case "thelm.packagedauto.integration.appeng.recipe.RecipeCraftingPatternHelper":
                tform = PautoCraftingPatternHelperTransformer.TFORM_OUTPUTS;
                break;
            case "thelm.packagedauto.tile.TileUnpackager":
                tform = TileUnpackagerTransformer.INSTANCE;
                break;
            case "appeng.container.implementations.ContainerInterfaceTerminal":
                tform = ContainerInterfaceTerminalTransformer.INSTANCE;
                break;
            default:
                return code;
        }
        System.out.println("[AE2FC] Transforming class: " + transformedName);
        return tform.transformClass(code);
    }

    public interface Transform {

        byte[] transformClass(byte[] code);

    }

    public static abstract class ClassMapper implements Transform {

        @Override
        public byte[] transformClass(byte[] code) {
            ClassReader reader = new ClassReader(code);
            ClassWriter writer = new ClassWriter(reader, getWriteFlags());
            reader.accept(getClassMapper(writer), 0);
            return writer.toByteArray();
        }

        protected int getWriteFlags() {
            return 0;
        }

        protected abstract ClassVisitor getClassMapper(ClassVisitor downstream);

    }

}
