package fr.bendertales.mc.talesessentials.commands.nodes;

import java.util.List;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommand;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdHeal implements TalesCommandNode, TalesCommand {

	private final List<String> permissions = List.of("essentials.commands.*", "essentials.commands.heal");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_MEDIOR, permissions);

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("heal")
                .requires(this.getRequirements().asPredicate())
	            .executes(this::healSelf)
			        .then(argument("target", EntityArgumentType.players())
	                .executes(this::healOthers))
				;
	}

	private int healOthers(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var source = context.getSource();
		var entitySelector = context.getArgument("target", EntitySelector.class);
		var players = entitySelector.getPlayers(source);
		for (ServerPlayerEntity player : players) {
			heal(player);
		}
		source.sendMessage(Text.literal("Healed targets"));
		return 0;
	}

	private int healSelf(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var source = context.getSource();
		var player = source.getPlayerOrThrow();
		heal(player);
		player.sendMessage(Text.literal("Healed"), true);
		return 1;
	}

	private static void heal(ServerPlayerEntity player) {
		player.setHealth(100f);
		var effectTypes = player.getStatusEffects().stream()
		                        .map(StatusEffectInstance::getEffectType)
		                        .toList();
		effectTypes.forEach(player::removeStatusEffect);
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}



}
