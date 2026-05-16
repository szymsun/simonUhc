package ab.szymsun.simonuhc.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public interface ICommand {
    static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

        });
    }
}
