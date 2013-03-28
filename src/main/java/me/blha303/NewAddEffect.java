package me.blha303;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NewAddEffect extends JavaPlugin {
	public Logger log;

	@Override
	public void onEnable() {
		log = this.getLogger();
		log.info("Enabled.");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		boolean everyone = false;
		Player p = null;
		Player target = null;
		int seconds = 30;
		int amplifier = 0;
		PotionEffectType effect;
		if (args.length >= 2) {
			target = getServer().getPlayer(args[0]);
			if (target == null) {
				if (args[0].equals("@a")) {
					everyone = true;
				}
				sender.sendMessage(error(args[0] + "doesn't match any online player."));
				return true;
			}
			try {
				effect = getEffect(Integer.parseInt(args[1]));
			} catch (NumberFormatException e) {
				sender.sendMessage(error(args[1] + " is not a valid number."));
				return true;
			}
			if (args.length >= 3) {
			try {
				seconds = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage(error(args[2] + "is not a valid number."));
				return true;
			}
			if (args.length == 4) {
				try {
					amplifier = Integer.parseInt(args[3]);
				} catch (NumberFormatException e) {
					sender.sendMessage(error(args[3] + "is not a valid number."));
					return true;
				}
			}
			}
		} else {
			listEffects(sender);
			sender.sendMessage(error("Usage: /effect <player> <effect> [seconds] [amplifier]"));
			return true;
		}
		if (sender instanceof Player) {
			p = (Player)sender;
		}
		
		if (p != null) {            // If sender is a player
			
			if (!p.hasPermission("addeffect.effect")) {
				sender.sendMessage(error("You can't use this command."));
				return true;
			} else {
				if (effect != null) {
					if (everyone) {
						for (Player player : getServer().getOnlinePlayers()) {
							player.removePotionEffect(effect);
							player.addPotionEffect(new PotionEffect(effect, seconds*20, amplifier));
							sender.sendMessage(String.format("Given %s (ID %s) x %s to %s for %s seconds", effect.getName(), effect.getId(), amplifier, "all players", seconds));
						}
					}
					target.removePotionEffect(effect);
					target.addPotionEffect(new PotionEffect(effect, seconds*20, amplifier));
					sender.sendMessage(String.format("Given %s (ID %s) x %s to %s for %s seconds", effect.getName(), effect.getId(), amplifier, target.getName(), seconds));
					return true;
				} else {
					sender.sendMessage(error("There is no such mob effect with ID " + args[1]));
					return true;
				}
			}
			
		} else {
		
			if (effect != null) {
				target.removePotionEffect(effect);
				target.addPotionEffect(new PotionEffect(effect, seconds*20, amplifier));
				sender.sendMessage(String.format("Given %s (ID %s) x %s to %s for %s seconds", getEffectName(effect.getId()), effect.getId(), amplifier, target.getName(), seconds));
				return true;
			} else {
				sender.sendMessage(error("There is no such mob effect with ID " + args[1]));
				return true;
			}
			
		}
		
	}

	private void listEffects(CommandSender sender) {
		sender.sendMessage(ChatColor.BLUE + "AddEffects list:");
/*		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
				"&bspeed&f, &bslow&f, &bfast_digging&f, &bslow_digging&f, &bincrease_damage&f, &b"
				+ "heal&f, &bharm&f, &bjump&f, &bconfusion&f, &bregeneration&f, &bdamage_resistance&f, &b"
				+ "fire_resistance&f, &bwater_breathing&f, &binvisibility&f, &bblindness&f, &b"
				+ "night_vision&f, &bhunger&f, &bweakness&f, &bpoison&f, &bwither")); */
		sender.sendMessage(color(String.format("&b1&f=&a%s&f, &b2&f=&a%s&f, &b3&f=&a%s&f,", getEffectName(1), getEffectName(2), getEffectName(3))));
		sender.sendMessage(color(String.format("&b4&f=&a%s&f, &b5&f=&a%s&f, &b6&f=&a%s&f,", getEffectName(4), getEffectName(5), getEffectName(6))));
		sender.sendMessage(color(String.format("&b7&f=&a%s&f, &b8&f=&a%s&f, &b9&f=&a%s&f,", getEffectName(7), getEffectName(8), getEffectName(9))));
		sender.sendMessage(color(String.format("&b10&f=&a%s&f, &b11&f=&a%s&f, &b12&f=&a%s&f,", getEffectName(10), getEffectName(11), getEffectName(12))));
		sender.sendMessage(color(String.format("&b13&f=&a%s&f, &b14&f=&a%s&f, &b15&f=&a%s&f,", getEffectName(13), getEffectName(14), getEffectName(15))));
		sender.sendMessage(color(String.format("&b16&f=&a%s&f, &b17&f=&a%s&f, &b18&f=&a%s&f,", getEffectName(16), getEffectName(17), getEffectName(18))));
		sender.sendMessage(color(String.format("&b19&f=&a%s&f, &b20&f=&a%s&f", getEffectName(19), getEffectName(20))));
	}

	private PotionEffectType getEffect(int id) {
		PotionEffectType effect = PotionEffectType.getById(id);
		return effect;
	}
	
	private String error(String msg) {
		return ChatColor.RED + msg;
	}
	
	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	private String getEffectName(int id) {
		switch (id) {
		case 1: return "Speed";
		case 2: return "Slowness";
		case 3: return "Haste";
		case 4: return "Mining Fatigue";
		case 5: return "Strength";
		case 6: return "Instant Health";
		case 7: return "Instant Damage";
		case 8: return "Jump Boost";
		case 9: return "Nausea";
		case 10: return "Regeneration";
		case 11: return "Resistance";
		case 12: return "Fire Resistance";
		case 13: return "Water Breathing";
		case 14: return "Invisibility";
		case 15: return "Blindness";
		case 16: return "Night Vision";
		case 17: return "Hunger";
		case 18: return "Weakness";
		case 19: return "Poison";
		case 20: return "Wither";
		default: return "Unknown";
		}
	}
}
