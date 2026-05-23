package ab.szymsun.simonuhc.mixin;

import ab.szymsun.simonuhc.SimonUHCGameRules;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;




@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private static void changeFurnaceSpeed(ServerWorld world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        if (!world.getGameRules().getValue(SimonUHCGameRules.QUICK_SMELT_GAMERULE)) {
            return;
        }
        IAbstractFurnaceBlockEntityAccessor accessor = (IAbstractFurnaceBlockEntityAccessor) blockEntity;

        ItemStack input = blockEntity.getStack(0);  // INPUT_SLOT_INDEX
        ItemStack fuel = blockEntity.getStack(1);   // FUEL_SLOT_INDEX

        if (input.isEmpty()) {
            if (accessor.getLitTimeRemaining() > 0) {
                accessor.setLitTimeRemaining(accessor.getLitTimeRemaining() - 1);
                blockEntity.markDirty();
            }
            return;
        }

        int cookingTotalTime = accessor.getCookingTotalTime();
        if (cookingTotalTime <= 0) {
            return;
        }

        boolean inventoryChanged = false;

        while (!input.isEmpty() && accessor.getCookingTotalTime() > 0) {
            cookingTotalTime = accessor.getCookingTotalTime();

            // 1. Check if we need to burn fuel
            if (accessor.getLitTimeRemaining() < cookingTotalTime) {
                if (!fuel.isEmpty()) {
                    int singleFuelValue = accessor.invokeGetFuelTime(world.getFuelRegistry(), fuel);

                    if (singleFuelValue > 0) {
                        accessor.setLitTimeRemaining(accessor.getLitTimeRemaining() + singleFuelValue);
                        accessor.setLitTotalTime(accessor.getLitTimeRemaining());

                        ItemStack remainder = fuel.getItem().getRecipeRemainder();
                        if (!remainder.isEmpty()) {
                            blockEntity.setStack(1, remainder.copy());
                        } else {
                            fuel.decrement(1); // Safely consume 1 fuel
                        }
                        inventoryChanged = true;
                    } else {
                        break; // Fuel item isn't valid fuel
                    }
                } else {
                    break; // No fuel left
                }
            }

            // 2. Try to cook the item
            if (accessor.getLitTimeRemaining() >= cookingTotalTime) {
                accessor.setLitTimeRemaining(accessor.getLitTimeRemaining() - cookingTotalTime);
                accessor.setCookingTimeSpent(cookingTotalTime - 1);

                SingleStackRecipeInput recipeInput = new SingleStackRecipeInput(input);
                var rawMatchGetter = accessor.getMatchGetter();

                @SuppressWarnings("unchecked")
                var typedMatchGetter = (ServerRecipeManager.MatchGetter<SingleStackRecipeInput, ? extends net.minecraft.recipe.AbstractCookingRecipe>) rawMatchGetter;
                RecipeEntry<?> match = typedMatchGetter.getFirstMatch(recipeInput, world).orElse(null);

                if (match != null) {
                    DefaultedList<ItemStack> inv = accessor.getInventory();

                    // Attempt the craft
                    if (IAbstractFurnaceBlockEntityAccessor.invokeCraftRecipe(world.getRegistryManager(), match, recipeInput, inv, blockEntity.getMaxCountPerStack())) {
                        blockEntity.setLastRecipe(match);
                        inventoryChanged = true;
                    } else {
                        // OOPS: Output is full! Refund the burn time we just took for this item and stop.
                        accessor.setLitTimeRemaining(accessor.getLitTimeRemaining() + cookingTotalTime);
                        break;
                    }
                } else {
                    break; // No valid recipe
                }
            } else {
                break; // Not enough burn time left and out of fuel
            }
        }

        if (inventoryChanged) {
            blockEntity.markDirty();
        }

        if (!blockEntity.getCachedState().get(AbstractFurnaceBlock.LIT)) {
            accessor.setLitTimeRemaining(0);
        }

        ci.cancel();
    }
}
