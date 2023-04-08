package com.tylerflar.listeners;

import static com.tylerflar.util.Util.sendWebhookMessage;

import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tylerflar.MCDisCrossChat;

public class PlayerAdvancementDoneListener implements Listener {
    private boolean _enabled = true;
    private String _webhoString;
    private String _serverName;
    private String _serverIcon;

    public PlayerAdvancementDoneListener(MCDisCrossChat plugin) {
        this._enabled = plugin.config.enabled;
        this._webhoString = plugin.config.eventsWebhookUrl;
        this._serverName = plugin.config.serverName;
        this._serverIcon = plugin.config.serverIcon;
    }

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent event) {
        String advancementName = event.getAdvancement().getKey().getKey();
        String translated = getAdvancementName(advancementName);
        if (_enabled && !translated.equals("")) {
            Player player = event.getPlayer();

            JSONObject json = new JSONObject();
            json.put("content", "");

            JSONArray embeds = new JSONArray();
            JSONObject embed = new JSONObject();
            JSONObject thumbnail = new JSONObject();
            thumbnail.put("url", "https://mc-heads.net/avatar/" + player.getName());
            
            embed.put("title", "Player Achievement");
            embed.put("description", player.getName() + " has completed the advancement " + translated + "!");
            embed.put("color", 0xB3B5B4);
            embed.put("thumbnail", thumbnail);
            embeds.add(embed);

            json.put("embeds", embeds);
            json.put("username", _serverName);
            json.put("avatar_url", _serverIcon);

            sendWebhookMessage(json, _webhoString);
        }
    }

    private String getAdvancementName(String advancementName) {
        String name = "";
        switch (advancementName) {
            case "story/root":
                name = "Minecraft";
                break;
            case "story/mine_stone":
                name = "Stone Age";
                break;
            case "story/upgrade_tools":
                name = "Getting an Upgrade";
                break;
            case "story/smelt_iron":
                name = "Acquire Hardware";
                break;
            case "story/obtain_armor":
                name = "Suit Up";
                break;
            case "story/lava_bucket":
                name = "Hot Stuff";
                break;
            case "story/iron_tools":
                name = "Isn't It Iron Pick";
                break;
            case "story/deflect_arrow":
                name = "Not Today, Thank You";
                break;
            case "story/form_obsidian":
                name = "Ice Bucket Challenge";
                break;
            case "story/mine_diamond":
                name = "DIAMONDS!";
                break;
            case "story/enter_the_nether":
                name = "We Need to Go Deeper";
                break;
            case "story/shiny_gear":
                name = "Cover Me with Diamonds";
                break;
            case "story/enchant_item":
                name = "Enchanter";
                break;
            case "story/cure_zombie_villager":
                name = "Zombie Doctor";
                break;
            case "story/follow_ender_eye":
                name = "Eye Spy";
                break;
            case "story/enter_the_end":
                name = "The End?";
                break;
            case "nether/root":
                name = "The Nether";
                break;
            case "nether/return_to_sender":
                name = "Return to Sender";
                break;
            case "nether/find_bastion":
                name = "Those Were the Days";
                break;
            case "nether/obtain_ancient_debris":
                name = "Hidden in the Depths";
                break;
            case "nether/fast_travel":
                name = "Subspace Bubble";
                break;
            case "nether/find_fortress":
                name = "A Terrible Fortress";
                break;
            case "nether/obtain_crying_obsidian":
                name = "Who is Cutting Onions?";
                break;
            case "nether/distract_piglin":
                name = "Oh Shiny";
                break;
            case "nether/ride_strider":
                name = "This Boat Has Legs";
                break;
            case "nether/uneasy_alliance":
                name = "Uneasy Alliance";
                break;
            case "nether/loot_bastion":
                name = "War Pigs";
                break;
            case "nether/use_lodestone":
                name = "Country Lode, Take Me Home";
                break;
            case "nether/netherite_armor":
                name = "Cover Me in Debris";
                break;
            case "nether/get_wither_skull":
                name = "Spooky Scary Skeletons";
                break;
            case "nether/obtain_blaze_rod":
                name = "Into Fire";
                break;  
            case "nether/charge_respawn_anchor":
                name = "Not Quite \"Nine\" Lives";
                break;
            case "nether/ride_strider_in_overworld_lava":
                name = "Feels Like Home";
                break;
            case "nether/explore_nether":
                name = "Hot Tourist Destinations";
                break;
            case "nether/summon_wither":
                name = "Withering Heights";
                break;
            case "nether/brew_potion":
                name = "Local Brewery";
                break;
            case "nether/create_beacon":
                name = "Bring Home the Beacon";
                break;
            case "nether/all_potions":
                name = "A Furious Cocktail";
                break;
            case "nether/create_full_beacon":
                name = "Beaconator";
                break;
            case "nether/all_effects":
                name = "How Did We Get Here?";
                break;
            case "end/root":
                name = "The End?";
                break;
            case "end/kill_dragon":
                name = "Free the End";
                break;
            case "end/dragon_egg":
                name = "The Next Generation";
                break;
            case "end/enter_end_gateway":
                name = "Remote Getaway";
                break;
            case "end/respawn_dragon":
                name = "The End... Again...";
                break;
            case "end/dragon_breath":
                name = "You Need a Mint";
                break;
            case "end/find_end_city":
                name = "The City at the End of the Game";
                break;
            case "end/elytra":
                name = "Sky's the Limit";
                break;
            case "end/levitate":
                name = "Great View From Up Here";
                break;
            case "adventure/root":
                name = "Adventure";
                break;
            case "adventure/voluntary_exile":
                name = "Voluntary Exile";
                break;
            case "adventure/spyglass_at_parrot":
                name = "Is It a Bird?";
                break;
            case "adventure/kill_a_mob":
                name = "Monster Hunter";
                break;
            case "adventure/trade":
                name = "What a Deal!";
                break;
            case "adventure/honey_block_slide":
                name = "Sticky Situation";
                break;
            case "adventure/ol_betsy":
                name = "Ol' Betsy";
                break;
            case "adventure/lightning_rod_with_villager_no_fire":
                name = "Surge Protector";
                break;
            case "adventure/fall_from_world_height":
                name = "Caves & Cliffs";
                break;
            case "adventure/avoid_vibration":
                name = "Sneak 100";
                break;
            case "adventure/sleep_in_bed":
                name = "Sweet Dreams";
                break;
            case "adventure/hero_of_the_village":
                name = "Hero of the Village";
                break;
            case "adventure/spyglass_at_ghast":
                name = "Is It a Balloon?";
                break;
            case "adventure/throw_trident":
                name = "A Throwaway Joke";
                break;
            case "adventure/shoot_arrow":
                name = "Take Aim";
                break;
            case "adventure/kill_all_mobs":
                name = "Monsters Hunted";
                break;
            case "adventure/totem_of_undying":
                name = "Postmortal";
                break;
            case "adventure/summon_iron_golem":
                name = "Hired Help";
                break;
            case "adventure/trade_at_world_height":
                name = "Star Trader";
                break;
            case "adventure/two_birds_one_arrow":
                name = "Two Birds, One Arrow";
                break;
            case "adventure/whos_the_pillager_now":
                name = "Who's the Pillager Now?";
                break;
            case "adventure/arbalistic":
                name = "Arbalistic";
                break;
            case "adventure/adventuring_time":
                name = "Adventuring Time";
                break;
            case "adventure/play_jukebox_in_meadows":
                name = "Sound of Music";
                break;
            case "adventure/walk_on_powder_snow_with_leather_boots":
                name = "Light as a Rabbit";
                break;
            case "adventure/spyglass_at_dragon":
                name = "Is It a Plane?";
                break;
            case "adventure/very_very_frightening":
                name = "Very Very Frightening";
                break;
            case "adventure/sniper_duel":
                name = "Sniper Duel";
                break;
            case "adventure/bullseye":
                name = "Bullseye";
                break;
            case "husbandry/root":
                name = "Husbandry";
                break;
            case "husbandry/safe_harvest_honey":
                name = "Bee Our Guest";
                break;
            case "husbandry/breed_an_animal":
                name = "The Parrots and the Bats";
                break;
            case "husbandry/allay_deliver_item_to_player":
                name = "You've Got a Friend in Me";
                break;
            case "husbandry/ride_a_boat_with_a_goat":
                name = "Whatever Floats Your Goat!";
                break;
            case "husbandry/tame_an_animal":
                name = "Best Friends Forever";
                break;
            case "husbandry/make_a_sign_glow":
                name = "Glow and Behold!";
                break;
            case "husbandry/fishy_business":
                name = "Fishy Business";
                break;
            case "husbandry/silk_touch_nest":
                name = "Total Beelocation";
                break;
            case "husbandry/tadpole_in_a_bucket":
                name = "Bukkit Bukkit";
                break;
            case "husbandry/plant_seed":
                name = "A Seedy Place";
                break;
            case "husbandry/wax_on":
                name = "Wax On";
                break;
            case "husbandry/bred_all_animals":
                name = "Two by Two";
                break;
            case "husbandry/allay_deliver_cake_to_note_block":
                name = "Birthday Song";
                break;
            case "husbandry/complete_catalogue":
                name = "Complete Catalogue";
                break;
            case "husbandry/tactical_fishing":
                name = "Tactical Fishing";
                break;
            case "husbandry/leash_all_frog_variants":
                name = "When the Squad Hops into Town";
                break;
            case "husbandry/balanced_diet":
                name = "A Balanced Diet";
                break;
            case "husbandry/obtain_netherite_hoe":
                name = "Serious Dedication";
                break;
            case "husbandry/wax_off":
                name = "Wax Off";
                break;
            case "husbandry/axolotl_in_a_bucket":
                name = "The Cutest Predator";
                break;
            case "husbandry/froglights":
                name = "With Our Powers Combined!";
                break;
            case "husbandry/kill_axolotl_target":
                name = "The Healing Power of Friendship!";
                break;
            default:
                name = "";
                break;
        }

        return name;
    }
}