package com.github.unchama.seichiassist.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import com.github.unchama.seichiassist.SeichiAssist;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Util {
	static private FireworkEffect.Type[] types = { FireworkEffect.Type.BALL,
		FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST,
		FireworkEffect.Type.CREEPER, FireworkEffect.Type.STAR, };
	public static ItemStack getskull(String name){
		ItemStack skull;
		SkullMeta skullmeta;
		skull = new ItemStack(Material.SKULL_ITEM, 1);
		skullmeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skull.setDurability((short) 3);
		skullmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "ガチャ券");
		List<String> lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GREEN + "右クリックで使えます"
				, ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "所有者:" + name);
		skullmeta.setLore(lore);
		skullmeta.setOwner("unchama");
		skull.setItemMeta(skullmeta);
		return skull;
	}
	public static ItemStack getInventoryOpenItem(String name){
		ItemStack endframe = new ItemStack(Material.ENDER_PORTAL_FRAME,1);
		ItemMeta itemmeta = Bukkit.getItemFactory().getItemMeta(Material.ENDER_PORTAL_FRAME);
		itemmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "4次元ポケット");
		List<String> lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GREEN + "右クリックで開けます"
										, ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "所有者:" + name);
		itemmeta.setLore(lore);
		endframe.setItemMeta(itemmeta);
		return endframe;
	}
	public static int getOnlinePlayer(){
		return Bukkit.getOnlinePlayers().size();
	}
	public static double toDouble(String s){
		return Double.parseDouble(s);
	}
	public static int toInt(String s) {
		return Integer.parseInt(s);
	}
	public static double Decimal(double d) {
		BigDecimal bi = new BigDecimal(String.valueOf(d));
		return bi.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static boolean isPlayerInventryFill(Player player){
		return (player.getInventory().firstEmpty() == -1);
	}
	public static void dropItem(Player player,ItemStack itemstack){
		player.getWorld().dropItemNaturally(player.getLocation(), itemstack);
	}
	public static void addItem(Player player,ItemStack itemstack){
		player.getInventory().addItem(itemstack);
	}
	public static void sendEveryMessage(String str){
		SeichiAssist plugin = SeichiAssist.plugin;
		for ( Player player : plugin.getServer().getOnlinePlayers() ) {
			player.sendMessage(str);
		}
	}
	public static void sendEverySound(Sound str, float a, float b){
		SeichiAssist plugin = SeichiAssist.plugin;
		for ( Player player : plugin.getServer().getOnlinePlayers() ) {
			player.playSound(player.getLocation(), str, a, b);
		}
	}

	public static String toTimeString(int _second) {
		int second = _second;
		int minute = 0;
		int hour = 0;
		String time = "";
		while(second >= 60){
			second -=60;
			minute++;
		}
		while(minute >= 60){
			minute -= 60;
			hour++;
		}
		if(hour != 0){
			time = hour + "時間";
		}
		if(minute != 0){
			time = time + minute + "分";
		}
		if(second != 0){
			time = time + second + "秒";
		}
		return time;
	}

	//プレイヤーネームを格納（toLowerCaseで全て小文字にする。)
	public static String getName(Player p) {
		return p.getName().toLowerCase();
	}
	public static String getName(String name) {
		return name.toLowerCase();
	}
	public static void launchFireWorks(Location loc) {
		// 花火を作る
		Firework firework = loc.getWorld().spawn(loc, Firework.class);

		// 花火の設定情報オブジェクトを取り出す
		FireworkMeta meta = firework.getFireworkMeta();
		Builder effect = FireworkEffect.builder();
		Random rand = new Random();

		// 形状をランダムに決める
		effect.with(types[rand.nextInt(types.length)]);

		// 基本の色を単色～5色以内でランダムに決める
		effect.withColor(getRandomCrolors(1 + rand.nextInt(5)));

		// 余韻の色を単色～3色以内でランダムに決める
		effect.withFade(getRandomCrolors(1 + rand.nextInt(3)));

		// 爆発後に点滅するかをランダムに決める
		effect.flicker(rand.nextBoolean());

		// 爆発後に尾を引くかをランダムに決める
		effect.trail(rand.nextBoolean());

		// 打ち上げ高さを1以上4以内でランダムに決める
		meta.setPower(1 + rand.nextInt(4));

		// 花火の設定情報を花火に設定
		meta.addEffect(effect.build());
		firework.setFireworkMeta(meta);

	}
	public static Color[] getRandomCrolors(int length) {
		// 配列を作る
		Color[] colors = new Color[length];
		Random rand = new Random();
		// 配列の要素を順に処理していく
		for (int n = 0; n != length; n++) {
			// 24ビットカラーの範囲でランダムな色を決める
			colors[n] = Color.fromBGR(rand.nextInt(1 << 24));
		}

		// 配列を返す
		return colors;
	}
	public static CoreProtectAPI getCoreProtect() {
		Plugin plugin = SeichiAssist.plugin.getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (plugin == null || !(plugin instanceof CoreProtect)) {
		    return null;
		}

		// Check that the API is enabled
		CoreProtectAPI CoreProtect = ((CoreProtect)plugin).getAPI();
		if (CoreProtect.isEnabled()==false){
		    return null;
		}

		// Check that a compatible version of the API is loaded
		if (CoreProtect.APIVersion() < 4){
		    return null;
		}

		return CoreProtect;
	}
	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = SeichiAssist.plugin.getServer().getPluginManager().getPlugin("WorldGuard");

	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }

	    return (WorldGuardPlugin) plugin;
	}
	public static WorldEditPlugin getWorldEdit() {
        Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if(pl instanceof WorldEditPlugin)
            return (WorldEditPlugin)pl;
        else return null;
    }
	public static boolean containsGacha(Player player) {
		org.bukkit.inventory.ItemStack[] inventory = player.getInventory().getStorageContents();
		Material material;
		SkullMeta skullmeta;
		for (int i = 0; i < inventory.length; i++) {
			material = inventory[i].getType();
			if(material.equals(Material.SKULL_ITEM)){
				skullmeta = (SkullMeta)inventory[i].getItemMeta();
				if(skullmeta.hasOwner()){
					if(skullmeta.getOwner().equals("unchama")){
						return true;
					}
				}
			}
		}
		return false;
	}
	//現在の採掘量順位を表示する
	public static int calcPlayerRank(Player p){
		//ランク用関数
		int i = 0;
		int t = calcMineBlock(p);
		//ランクが上がらなくなるまで処理
		while(SeichiAssist.ranklist.get(i).intValue() > t){
			i++;
		}
		return i+1;
	}

	//統計の総ブロック破壊数を出力する
	public static int calcMineBlock(Player p){
		int sum = 0;
		for(Material m : SeichiAssist.materiallist){
			sum += p.getStatistic(Statistic.MINE_BLOCK, m);
		}
		return  sum;
	}
}
