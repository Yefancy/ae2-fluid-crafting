package xyz.phanta.ae2fc.util;

import appeng.api.definitions.IItemDefinition;
import appeng.container.implementations.ContainerPatternTerm;
import appeng.container.slot.OptionalSlotFake;
import appeng.container.slot.SlotFakeCraftingMatrix;
import appeng.container.slot.SlotRestrictedInput;
import appeng.recipes.game.DisassembleRecipe;
import appeng.util.inv.ItemSlot;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class Ae2Reflect {

    private static final Method mItemSlot_setExtractable;
    private static final Field fDisassembleRecipe_nonCellMappings;
    private static final Field fContainerPatternTerm_craftingSlots;
    private static final Field fContainerPatternTerm_outputSlots;
    private static final Field fContainerPatternTerm_patternSlotIN;
    private static final Field fContainerPatternTerm_patternSlotOUT;

    static {
        try {
            mItemSlot_setExtractable = reflectMethod(ItemSlot.class, "setExtractable", boolean.class);
            fDisassembleRecipe_nonCellMappings = reflectField(DisassembleRecipe.class, "nonCellMappings");
            fContainerPatternTerm_craftingSlots = reflectField(ContainerPatternTerm.class, "craftingSlots");
            fContainerPatternTerm_outputSlots = reflectField(ContainerPatternTerm.class, "outputSlots");
            fContainerPatternTerm_patternSlotIN = reflectField(ContainerPatternTerm.class, "patternSlotIN");
            fContainerPatternTerm_patternSlotOUT = reflectField(ContainerPatternTerm.class, "patternSlotOUT");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize AE2 reflection hacks!", e);
        }
    }

    public static Method reflectMethod(Class<?> owner, String name, Class<?>... paramTypes) throws NoSuchMethodException {
        Method m = owner.getDeclaredMethod(name, paramTypes);
        m.setAccessible(true);
        return m;
    }

    public static Field reflectField(Class<?> owner, String name) throws NoSuchFieldException {
        Field f = owner.getDeclaredField(name);
        f.setAccessible(true);
        return f;
    }

    @SuppressWarnings("unchecked")
    public static <T> T readField(Object owner, Field field) {
        try {
            return (T)field.get(owner);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read field: " + field);
        }
    }

    public static void writeField(Object owner, Field field, Object value) {
        try {
            field.set(owner, value);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to write field: " + field);
        }
    }

    public static void setItemSlotExtractable(ItemSlot slot, boolean extractable) {
        try {
            mItemSlot_setExtractable.invoke(slot, extractable);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to invoke method: " + mItemSlot_setExtractable, e);
        }
    }

    public static Map<IItemDefinition, IItemDefinition> getDisassemblyNonCellMap(DisassembleRecipe recipe) {
        return readField(recipe, fDisassembleRecipe_nonCellMappings);
    }

    public static SlotFakeCraftingMatrix[] getCraftingSlots(ContainerPatternTerm cont) {
        return readField(cont, fContainerPatternTerm_craftingSlots);
    }
    
    public static OptionalSlotFake[] getOutputSlots(ContainerPatternTerm cont) {
        return readField(cont, fContainerPatternTerm_outputSlots);
    }
    
    public static SlotRestrictedInput getPatternSlotIn(ContainerPatternTerm cont) {
        return readField(cont, fContainerPatternTerm_patternSlotIN);
    }
    
    public static SlotRestrictedInput getPatternSlotOut(ContainerPatternTerm cont) {
        return readField(cont, fContainerPatternTerm_patternSlotOUT);
    }

}
