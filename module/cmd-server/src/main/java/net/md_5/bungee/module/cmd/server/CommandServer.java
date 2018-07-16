package net.md_5.bungee.module.cmd.server;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.Map;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Command to list and switch a player between available servers.
 */
public class CommandServer extends Command implements TabExecutor
{

    public CommandServer()
    {
        super( "server", "bungeecord.command.server" );
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
        if ( args.length == 0 )
        {
/*
            if ( sender instanceof ProxiedPlayer )
            {
                sender.sendMessage( ProxyServer.getInstance().getTranslation( "current_server", ( (ProxiedPlayer) sender ).getServer().getInfo().getName() ) );
            }
            TextComponent serverList = new TextComponent( ProxyServer.getInstance().getTranslation( "server_list" ) );
            serverList.setColor( ChatColor.GOLD );
            boolean first = true;
            for ( ServerInfo server : servers.values() )
            {
                if ( server.canAccess( sender ) )
                {
                    TextComponent serverTextComponent = new TextComponent( first ? server.getName() : ", " + server.getName() );
                    int count = server.getPlayers().size();
                    serverTextComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder( count + ( count == 1 ? " player" : " players" ) + "\n" )
                            .append( "Click to connect to the server" ).italic( true )
                            .create() ) );
                    serverTextComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/server " + server.getName() ) );
                    serverList.addExtra( serverTextComponent );
                    first = false;
                }
            }
*/
            StringBuilder builder = new StringBuilder();
            builder.append( ChatColor.translateAlternateColorCodes( '&', "&8« &b&l系统 &8» &c请输入要传送的服务器!" ) );
            sender.sendMessage( builder.toString() );
        } else
        {
            if ( !( sender instanceof ProxiedPlayer ) )
            {
                return;
            }
            ProxiedPlayer player = (ProxiedPlayer) sender;

            ServerInfo server = servers.get( args[0] );
            if ( server == null )
            {
                player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&8« &b&l系统 &8» &c服务器名字不存在!" ) );
            } else if ( !server.canAccess( player ) )
            {
                player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&8« &b&l系统 &8» &c #你尝试进入这个服务器" ) );
                player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&8« &b&l系统 &8» &c #然而,什么也没有发生。(啊别打别打我说我说 是权限问题啊啊啊啊)" ) );
            } else
            {
                player.connect( server );
            }
        }
    }
    @Override
    public Iterable<String> onTabComplete(final CommandSender sender, final String[] args)
    {
        return ( Collections.EMPTY_LIST );
    }
}
