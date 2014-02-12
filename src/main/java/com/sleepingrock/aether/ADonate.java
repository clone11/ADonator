package com.sleepingrock.aether;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Chris on 2/9/14.
 */
public class ADonate extends JavaPlugin {

    public static Permission permission = null;
    public static Economy economy = null;
    public static Logger log;

    private String sqlUserName;
    private String sqlPassword;
    private String sqlPort;
    private String sqlDatabase;
    private String sqlHost;
    public DonatorDatabase dbConnection = null;

    @Override
    public void onEnable() {
        log = this.getLogger();
        log.info("Hooking into economy and permissions");
        if (!setupEconomy() || !setupPermission()) return; //Plugin will need both to run return otherwise.
        log.info("Setting up events");
        setupEvents();
        log.info("Setting up config");
        setupConfig();
        log.info("Setting up SQL");
        setupSQL();
        log.info("Setting up commands");
        setupCommands();
        log.info("Done loading!");
    }
    @Override
    public void onDisable() {

    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider;
        economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) economy = economyProvider.getProvider();
        return economy != null;
    }

    private boolean setupPermission () {
        RegisteredServiceProvider<Permission> permissionProvider;
        permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) permission = permissionProvider.getProvider();
        return permission != null;
    }

    private void setupEvents() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
    }

    private void setupConfig() {
        this.saveDefaultConfig();
        sqlUserName = this.getConfig().getString("mysql.username");
        sqlPassword = this.getConfig().getString("mysql.password");
        sqlHost = this.getConfig().getString("mysql.hostname");
        sqlPort = this.getConfig().getString("mysql.port");
        sqlDatabase = this.getConfig().getString("mysql.database");
    }

    private void setupSQL() {
        dbConnection = new DonatorDatabase(sqlHost, sqlPort, sqlUserName, sqlPassword, sqlDatabase);
    }

    private void setupCommands() {
        getCommand("donate").setExecutor(new DonateCommandExecutor(this));
    }
}
