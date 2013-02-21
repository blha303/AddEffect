package me.blha303;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AddEffect extends JavaPlugin {
	public Logger log;

	@Override
	public void onEnable() {
		log = this.getLogger();
		log.info("Enabled.");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		boolean hasarg = false;
		Player p = null;
		String name = "";
		String[] arg;
		int amplifier = 2;
		if (args.length >= 1) {
			arg = args[0].split(":");
			if (arg.length > 1) {
				amplifier = Integer.parseInt(arg[1]);
				name = arg[0];
			} else {
				name = args[0];
			}
		} else {
			listEffects(sender);
			return false;
		}
		if (args.length == 2) {
			p = this.getServer().getPlayer(args[1]);
			if (p != null) hasarg = true;
		}
		if (sender instanceof Player) {
			if (!((Player) sender).hasPermission("command.effect")) {
				sender.sendMessage("You can't use this command.");
				return true;
			} else {
				PotionEffectType effect = getEffect(name);
				if (effect != null) {
					if (hasarg) {
						p.addPotionEffect(new PotionEffect(effect, 1000000,
								amplifier - 1));
						sender.sendMessage(String.format(
								"Effect added to %s: %s %s", p.getName(),
								effect.getName(), amplifier));
						return true;
					} else {
						((Player) sender).addPotionEffect(new PotionEffect(
								effect, 1000000, amplifier - 1));
						sender.sendMessage(String.format("Effect added: %s %s",
								effect.getName(), amplifier));
						return true;
					}
				} else {
					sender.sendMessage("Could not get effect type.");
					return true;
				}
			}
		} else {
			if (!hasarg) {
				log.info("Player argument missing");
				return false;
			} else {
				PotionEffectType effect = getEffect(name);
				p.addPotionEffect(new PotionEffect(effect, 1000000,
						amplifier - 1));
				sender.sendMessage(String.format("Effect added to %s: %s %s",
						p.getName(), effect.getName(), amplifier));
				return true;
			}
		}
	}

	private void listEffects(CommandSender sender) {
		ChatColor a = ChatColor.BLUE;
		sender.sendMessage(a + "AddEffects list:");
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bspeed&f, &bslow&f, &bfast_digging&f, &bslow_digging&f, &bincrease_damage&f, &b"
				+ "heal&f, &bharm&f, &bjump&f, &bconfusion&f, &bregeneration&f, &bdamage_resistance&f, &b"
				+ "fire_resistance&f, &bwater_breathing&f, &binvisibility&f, &bblindness&f, &b"
				+ "night_vision&f, &bhunger&f, &bweakness&f, &bpoison&f, &bwither"));
	}

	private PotionEffectType getEffect(String name) {
		PotionEffectType effect = PotionEffectType.getByName(name);
		return effect;
	}
}
