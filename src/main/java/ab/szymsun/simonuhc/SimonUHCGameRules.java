package ab.szymsun.simonuhc;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;

public class SimonUHCGameRules {
    private static final Identifier GAME_BORDER_GAMERULE_IDENTIFIER = Identifier.of("simonuhc","game_border");
    public  static final GameRule<Integer> GAME_BORDER_GAMERULE = GameRuleBuilder
            .forInteger(5000)
            .category(GameRuleCategory.MISC)
            .buildAndRegister(GAME_BORDER_GAMERULE_IDENTIFIER);

    private static final Identifier GAME_COUNTDOWN_GAMERULE_IDENTIFIER = Identifier.of("simonuhc","game_countdown");
    public  static final GameRule<Integer> GAME_COUNTDOWN_GAMERULE = GameRuleBuilder
            .forInteger(900)
            .category(GameRuleCategory.MISC)
            .buildAndRegister(GAME_COUNTDOWN_GAMERULE_IDENTIFIER);
}
