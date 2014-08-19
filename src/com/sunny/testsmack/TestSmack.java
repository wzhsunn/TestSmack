package com.sunny.testsmack;

import java.io.IOException;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class TestSmack {

	public static XMPPConnection connection;     
    public static Chat newChat;     
    public static ChatManager chatmanager;   
    
	static void  Test() throws IOException, XMPPException, SmackException, InterruptedException {
//		XMPPConnection.DEBUG_ENABLED = true;
//		SmackConfiguraiton.DEBUG_ENABLED = true;
		ConnectionConfiguration config = new ConnectionConfiguration("127.0.0.1", 5222);
		config.setDebuggerEnabled(true);
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
//		config.setSocketFactory(new DummySSLSocketFactory());
		connection = new XMPPTCPConnection(config);
		connection.connect();
		connection.login("sun2", "abc123");
		AccountManager accountManager = AccountManager.getInstance(connection);
		accountManager.createAccount("sun4", "abc123");
		
		//addListener();
		 chatmanager = ChatManager.getInstanceFor(connection);
		newChat = chatmanager.createChat("sun@wechat.com", new MessageListener() {
		    public void processMessage(Chat chat, Message message) {
		        System.out.println("Received message: " + message);
		    }
		});
		newChat.sendMessage("hi nicole hh!");
//		while(true){};
		Thread.sleep(100000);
		connection.disconnect();
		
	}
	  private static void addListener() {     
	        // just need Messages     
	        PacketFilter filterMessage = new PacketTypeFilter(Message.class);     
	    
	        PacketListener myListener = new PacketListener() {     
	            public void processPacket(Packet packet) throws NotConnectedException {     
	                System.out.println("From: " + packet.getFrom() + "\n");     
	                System.out.println("Body: " + ((Message) packet).getBody());     
	                // when receiving prc's Message, just say something else again     
	                // and again, robot     
	                try {     
	                    newChat.sendMessage("hi again");     
	                } catch (XMPPException e) {     
	                    e.printStackTrace();     
	                }     
	            }     
	        };     
	        // register the listener to the connection     
	        connection.addPacketListener(myListener, filterMessage);     
	    }     
	  
	public static void main(String[] args) throws IOException, XMPPException, SmackException, InterruptedException {
		// TODO Auto-generated method stub
		Test();
	}

}
