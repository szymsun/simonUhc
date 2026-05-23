package ab.szymsun.simonuhc;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.*;

public class SimonUHCGameRules {
    public static final GameRuleCategory UHC = GameRuleCategory.register(Identifier.of("uhc"));
    public static final GameRuleCategory UHC_MECHANICS = GameRuleCategory.register(Identifier.of("uhc_mechanics"));
    public static GameRule<Integer> GAME_BORDER_GAMERULE;
    public static GameRule<Integer> GAME_COUNTDOWN_GAMERULE;
    public static GameRule<Boolean> QUICK_SMELT_GAMERULE;

    public static void register() {
        QUICK_SMELT_GAMERULE = GameRuleBuilder
                .forBoolean(false)
                .category(UHC_MECHANICS)
                .buildAndRegister(Identifier.of("simonuhc", "game_quick_smelt"));

        GAME_BORDER_GAMERULE = GameRuleBuilder
                .forInteger(5000)
                .category(UHC)
                .buildAndRegister(Identifier.of("simonuhc", "game_border"));

        GAME_COUNTDOWN_GAMERULE = GameRuleBuilder
                .forInteger(900)
                .category(UHC)
                .buildAndRegister(Identifier.of("simonuhc", "game_countdown"));
    }
}
