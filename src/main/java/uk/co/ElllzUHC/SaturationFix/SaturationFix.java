package uk.co.ElllzUHC.SaturationFix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.ElllzUHC.Scenarios.Scenario;
import uk.co.ElllzUHC.Scenarios.ScenarioInterface;

import java.util.Random;

/**
 * Created by Elliot on 03/12/2014.
 */
public class SaturationFix extends JavaPlugin implements Listener,ScenarioInterface {

    private boolean state;

    public void onEnable(){
        state = false;

        Scenario manager = (Scenario) Bukkit.getPluginManager().getPlugin("ScenarioManager");
        manager.registerScenario(this);

        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void setScenarioState(Boolean aBoolean) {
        state = aBoolean;
    }

    @Override
    public boolean getScenarioState() {
        return state;
    }

    @Override
    public String getScenarioName() {
        return "SaturationFix";
    }

    @Override
    public String getScenarioDescription() {
        return "Fixes broken aspects of 1.7 saturation.";
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event)
    {
        if(!getScenarioState()){
            return;
        }
        final Player player = event.getPlayer();
        final float before = player.getSaturation();

        new BukkitRunnable()
        {
            public void run()
            {
                float change = player.getSaturation() - before;
                player.setSaturation((float)(before + change * 2.5D));
            }
        }.runTaskLater(this, 1L);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)

    {
        if (event.getFoodLevel() < ((Player)event.getEntity()).getFoodLevel() && getScenarioState()) {
            event.setCancelled(new Random().nextInt(100) < 66);
        }
    }
}
