/*
    This file is part of Peers, a java SIP softphone.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Copyright 2007, 2008, 2009, 2010 Yohann Martineau 
*/

package net.sourceforge.peers.sip.transport;

import java.io.IOException;
import java.net.InetAddress;

import net.sourceforge.peers.Config;
import net.sourceforge.peers.sip.RFC3261;


public abstract class MessageSender {

    protected InetAddress inetAddress;
    protected int port;
    protected int localPort;
    private Config config;
    private String transportName;
    
    public MessageSender(InetAddress inetAddress, int port, Config config,
            String transportName) {
        super();
        this.inetAddress = inetAddress;
        this.port = port;
        this.config = config;
        this.transportName = transportName;
    }
    
    public abstract void sendMessage(SipMessage sipMessage) throws IOException;

    public String getContact() {
        StringBuffer buf = new StringBuffer();
        //buf.append(datagramSocket.getLocalAddress().getHostAddress());
        InetAddress myAddress = config.getPublicInetAddress();
        if (myAddress == null) {
            myAddress = config.getLocalInetAddress();
        }
        buf.append(myAddress.getHostAddress());
        buf.append(RFC3261.TRANSPORT_PORT_SEP);
        buf.append(config.getSipPort());
        //buf.append(datagramSocket.getLocalPort());
        buf.append(RFC3261.PARAM_SEPARATOR);
        buf.append(RFC3261.PARAM_TRANSPORT);
        buf.append(RFC3261.PARAM_ASSIGNMENT);
        buf.append(transportName);
        return buf.toString();
    }

    public int getLocalPort() {
        return localPort;
    }
    
}
