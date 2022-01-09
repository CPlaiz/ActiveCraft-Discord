package de.cplaiz.activecraftdiscord.minecraft.listener;

import de.cplaiz.activecraftdiscord.ActiveCraftDiscord;
import de.cplaiz.activecraftdiscord.discord.SendToDiscord;
import de.silencio.activecraftcore.utils.ColorUtils;
import de.silencio.activecraftcore.playermanagement.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

public class AchievementListener implements Listener {

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {

        Player player = event.getPlayer();
        Profile profile = new Profile(player);
        if (profile.isVanished()) return;
        String advancementKey = event.getAdvancement().getKey().getKey();

        if (advancementKey.startsWith("recipes/") || advancementKey.contains("/root")) return;

        String advancementName = switch (advancementKey) {
            //Story
            case "story/mine_stone" -> "Stone Age";
            case "story/upgrade_tools" -> "Getting an upgrade";
            case "story/smelt_iron" -> "Acquire Hardware";
            case "story/obtain_armor" -> "Suit Up";
            case "story/lava_bucket" -> "Hot Stuff";
            case "story/iron_tools" -> "Isn't It Iron Pick";
            case "story/deflect_arrow" -> "Not Today, Thank You";
            case "story/form_obsidian" -> "Ice Bucket Challenge";
            case "story/mine_diamond" -> "Diamonds";
            case "story/enter_the_nether" -> "We Need To Go Deeper";
            case "story/shiny_gear" -> "Cover Me With Diamonds";
            case "story/enchant_item" -> "Enchanter";
            case "story/cure_zombie_villager" -> "Zombie Doctor";
            case "story/follow_ender_eye" -> "Eye Spy";
            case "story/enter_the_end" -> "The End?";
            //Nether
            case "nether/return_to_sender" -> "Return to Sender";
            case "nether/find_bastion" -> "Those Were the Days";
            case "nether/obtain_ancient_debris" -> "Hidden in the Depths";
            case "nether/fast_travel" -> "Subspace Bubble";
            case "nether/find_fortress" -> "A Terrible Fortress";
            case "nether/obtain_crying_obsidian" -> "Who is Cutting Onions?";
            case "nether/distract_piglin" -> "Oh Shiny";
            case "nether/ride_strider" -> "This Boat Has Legs";
            case "nether/ride_strider_in_overworld_lava" -> "Feels like home";
            case "nether/uneasy_alliance" -> "Uneasy Alliance";
            case "nether/loot_bastion" -> "War Pigs";
            case "nether/use_lodestone" -> "Country Lode, Take Me Home";
            case "nether/netherite_armor" -> "Cover Me in Debris";
            case "nether/get_wither_skull" -> "Spooky Scary Skeleton";
            case "nether/obtain_blaze_rod" -> "Into Fire";
            case "nether/charge_respawn_anchor" -> "Not Quite Nine Lives";
            case "nether/explore_nether" -> "Hot Tourist Destinations";
            case "nether/summon_wither" -> "Withering Heights";
            case "nether/brew_potion" -> "\tLocal Brewery";
            case "nether/create_beacon" -> "Bring Home the Beacon";
            case "nether/all_potions" -> "HA Furious Cocktail";
            case "nether/create_full_beacon" -> "Beaconator";
            case "nether/all_effects" -> "How Did We Get Here?";

            //The End
            case "end/kill_dragon" -> "Free the End";
            case "end/dragon_egg" -> "The Next Generation";
            case "end/enter_end_gateway" -> "Remote Getaway";
            case "end/respawn_dragon" -> "The End... Again...";
            case "end/dragon_breath" -> "You Need A Mint";
            case "end/find_end_city" -> "The City at the End of the Game";
            case "end/elytra" -> "Sky's The Limit";
            case "end/levitate" -> "Great View From Up Here";
            //Adventure
            case "adventure/voluntary_exile" -> "Voluntary Exile";
            case "adventure/spyglass_at_parrot" -> "Is It a Bird?";
            case "adventure/kill_a_mob" -> "Monster Hunter";
            case "adventure/trade" -> "What a Deal!";
            case "adventure/trade_at_world_height" -> "Star Trader";
            case "adventure/honey_block_slide" -> "Sticky Situation";
            case "adventure/ol_betsy" -> "Ol' Betsy";
            case "adventure/lightning_rod_with_villager_no_fire" -> "Surge Protector";
            case "adventure/walk_on_powder_snow_with_leather_boots" -> "Light as a Rabbit";
            case "adventure/sleep_in_bed" -> "Sweet Dreams";
            case "adventure/hero_of_the_village" -> "Hero of the Village";
            case "adventure/spyglass_at_ghast" -> "Is It a Balloon?";
            case "adventure/throw_trident" -> "A Throwaway Joke";
            case "adventure/shoot_arrow" -> "Take Aim";
            case "adventure/kill_all_mobs" -> "Monsters Hunted";
            case "adventure/totem_of_undying" -> "Postmortal";
            case "adventure/summon_iron_golem" -> "Hired Help";
            case "adventure/two_birds_one_arrow" -> "Two Bird, One Arrow";
            case "adventure/whos_the_pillager_now" -> "Who's the Pillager Now?";
            case "adventure/arbalistic" -> "Arbalistic";
            case "adventure/adventuring_time" -> "Adventuring Time";
            case "adventure/spyglass_at_dragon" -> "Is It a Plane?";
            case "adventure/very_very_frightening" -> "Very Very Frightening";
            case "adventure/sniper_duel" -> "Sniper Duel";
            case "adventure/bullseye" -> "Bullseye";
            case "adventure/play_jukebox_in_meadows" -> "Sound of Music";
            case "adventure/fall_from_world_height" -> "Caves & Cliffs";
            //husbandry
            case "husbandry/safely_harvest_honey" -> "Bee Our Guest";
            case "husbandry/breed_an_animal" -> "The Parrots and the Bats";
            case "husbandry/ride_a_boat_with_a_goat" -> "Whatever Floats Your Goat!";
            case "husbandry/tame_an_animal" -> "Best Friends Forever";
            case "husbandry/make_a_sign_glow" -> "Glow and Behold!";
            case "husbandry/fishy_business" -> "Fishy Business";
            case "husbandry/silk_touch_nest" -> "Total Beelocation";
            case "husbandry/plant_seed" -> "A Seedy Place";
            case "husbandry/wax_on" -> "Wax On";
            case "husbandry/bred_all_animals" -> "Two by Two";
            case "husbandry/complete_catalogue" -> "A Complete Catalogue";
            case "husbandry/tactical_fishing" -> "Tactical Fishing";
            case "husbandry/balanced_diet" -> "A Balanced Diet";
            case "husbandry/obtain_netherite_hoe" -> "Serious Dedication";
            case "husbandry/wax_off" -> "Wax Off";
            case "husbandry/axolotl_in_a_bucket" -> "The Cutest Predator";
            case "husbandry/kill_axolotl_target" -> "The Healing Power of Friendship!";
            default -> "which is somehow not displayed correctly ¯\\_(ツ)_/¯ \n Please contact the developers about this issue.";
        };

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(
                ColorUtils.removeColorAndFormat(player.getDisplayName()) + " got the achievement " + advancementName,
                null,
                "https://crafatar.com/avatars/" + player.getUniqueId() + "?overlay=true");
        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.setTimestamp(OffsetDateTime.now());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy z");
        SendToDiscord.sendLog("*[Advancement] [" + player.getName() + "] [" + sdf.format(new Date()) + "]* " + ColorUtils.removeColorAndFormat(new Profile(player).getNickname()) + " got the achievement " + advancementName);
        SendToDiscord.sendChatEmbed(embedBuilder);
    }
}
