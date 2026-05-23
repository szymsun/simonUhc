package ab.szymsun.simonuhc.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface IAbstractFurnaceBlockEntityAccessor {

    @Accessor("cookingTimeSpent") int getCookingTimeSpent();
    @Accessor("cookingTimeSpent") void setCookingTimeSpent(int cookingTimeSpent);

    @Accessor("cookingTotalTime") int getCookingTotalTime();
    @Accessor("cookingTotalTime") void setCookingTotalTime(int cookingTotalTime);

    @Accessor("litTimeRemaining") int getLitTimeRemaining();
    @Accessor("litTimeRemaining") void setLitTimeRemaining(int litTimeRemaining);

    @Accessor("litTotalTime") int getLitTotalTime();
    @Accessor("litTotalTime") void setLitTotalTime(int litTotalTime);

    @Accessor("inventory")
    DefaultedList<ItemStack> getInventory();

    @Accessor("matchGetter") ServerRecipeManager.MatchGetter<?, ?> getMatchGetter();

    // STRICTLY REMOVE ALL PREFIXES: No static, no default, no body brackets.
    // If the compiler flags an abstract method warning, ensure it remains a pure interface signature.
    @Invoker("getFuelTime")
    int invokeGetFuelTime(FuelRegistry fuelRegistry, ItemStack stack);

    @Invoker("craftRecipe")
    static boolean invokeCraftRecipe(DynamicRegistryManager dynamicRegistryManager, RecipeEntry<?> recipe, SingleStackRecipeInput input, DefaultedList<ItemStack> inventory, int maxCount) {
        throw new AssertionError();
    }
}
