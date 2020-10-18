package me.marvel.armorplusplus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.marvel.armorplusplus.commands.TabComplete;
public class ArmorPlusPlus extends JavaPlugin implements Listener {
	
	public boolean outdated = false;
	public boolean uptodate = false;
	public String msg = "";
	public UpdateChecker uc;
	private boolean hasMineTinker = false;
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		if(e.getPlayer().isOp() || e.getPlayer().hasPermission("armorplusplus.cheat")) {
			Player p = e.getPlayer();
			if(outdated) {
				if(msg != "") p.sendMessage(msg);
				else p.sendMessage(ChatColor.GREEN + "ArmorPlusPlus > Available update version. Current plugin is outdated. Download at https://www.spigotmc.org/resources/armorplusplus.74748/");
			}
			if(hasMineTinker) {
				p.sendMessage(ChatColor.RED + "ArmorPlusPlus > Incompatibility plugin found: MineTinker. Note: This plugin will not work properly!");
			}
		}
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "==========================");
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Armor++ plugin enabled v" + this.getDescription().getVersion());
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "==========================");
		try {
			if(getConfig().getBoolean("check-update")) {
				uc = new UpdateChecker(this, 74748);
				uc.getVersion((version) -> {
					if(version == null) {
						getServer().getConsoleSender().sendMessage(ChatColor.RED + "ArmorPlusPlus > Unable to check updates. To manually check updates, please type /armorplusplus check.");
					}else {
						if(version.equalsIgnoreCase(getDescription().getVersion())) {
							outdated = false;
							uptodate = true;
							getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ArmorPlusPlus > No available updates.");
						}else {
							outdated = true;
							uptodate = false;
							msg = ChatColor.GREEN + "ArmorPlusPlus > Available update version " + version + ". Download at https://www.spigotmc.org/resources/armorplusplus.74748/";
							getServer().getConsoleSender().sendMessage(msg);
						}
					}
				});
			}
		}catch(Exception e) {
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "ArmorPlusPlus > Unable to check updates. To manually check updates, please type /armorplusplus check.");
		}
		
		getCommand("armorplusplus").setExecutor(new me.marvel.armorplusplus.commands.ArmorPlusPlus(this, this));
		getCommand("armorplusplus").setTabCompleter(new TabComplete());
		getServer().getPluginManager().registerEvents(new me.marvel.armorplusplus.commands.ArmorPlusPlus(this, this), this);
		getServer().getPluginManager().registerEvents(new ArmorAbilities(this), this);
		Method.plugin = this;
		loadRecipe();
		loadAbilities();
		for(Plugin p : getServer().getPluginManager().getPlugins()) {
			String name = p.getName();
			if(name.equalsIgnoreCase("MineTinker")) {
				hasMineTinker = true;
			}
		}
		if(hasMineTinker) {
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "ArmorPlusPlus >  Incompatibility plugins found: MineTinker.");
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "ArmorPlusPlus > This plugin will not work properly!");
		}else {
			getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[ArmorPlusPlus/INFO] No incompatibility plugins found!");
		}
	}

	private void loadRecipe() {
		ArmorRecipe ar = new ArmorRecipe(this);
		ar.furnaceArmor();
		ar.glassArmor();
		ar.craftArmor();
		ar.dirtArmor();
		ar.tntArmor();
		ar.noteArmor();
		ar.pumpkinArmor();
		ar.melonArmor();
		ar.spongeArmor();
		ar.dispenserArmor();
		ar.prismarineArmor();
		ar.enderArmor();
		ar.lapisArmor();
		ar.cactusArmor();
		ar.leavesArmor();
		ar.sugarcaneArmor();
		ar.stickypistonArmor();
		ar.sandArmor();
		ar.quartzArmor();
		ar.obsidianArmor();
		ar.emeraldArmor();
		ar.pistonArmor();
		ar.wetspongeArmor();
		ar.magmaArmor();
		ar.netherrackArmor();
		ar.brickArmor();
		ar.netherBrickArmor();
		ar.redNetherBrickArmor();
	}
	private void loadAbilities() {
			// For every 2.5 second
			ArmorAbilities ability = new ArmorAbilities(this);
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					ability.dirtArmor();
				}
			}, 0L, 50L);
			// For every 6 second
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					ability.pumpkinArmor();
					ability.melonArmor();
				}
			}, 0L, 120L);
			
			// For every tick
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					ability.craftArmor();
					ability.invisibility();
					ability.smelt();
					ability.explode();
					ability.spongeArmor();
					ability.dispenserArmor();
					ability.prismarineArmor();
					ability.enderArmor();
					ability.lapisArmor();
					ability.leavesArmor();
					ability.sugarcaneArmor();
					ability.stickypistonArmor();
					ability.sandArmor();
					ability.quartzArmor();
					ability.obsidianArmor();
					ability.emeraldArmor();
					ability.pistonArmor();
					ability.wetspongeArmor();
					ability.magmaArmor();
					/*Netherrack armor not needing a method since it triggers an event.*/
					ability.brickArmor();
					ability.netherBrickArmor();
					ability.redNetherBrickArmor();
				}
			}, 0L, 1L);
			
			// For every 12 ticks
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					ability.cactusArmor();
				}
			}, 0L, 12L);
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "==========================");
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Armor++ plugin disabled v" + this.getDescription().getVersion());
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "==========================");
	}
	

}
