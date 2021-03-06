package com.company;

import de.tum.in.www1.jReto.Connection;
import de.tum.in.www1.jReto.RemotePeer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class P2PConnection {

    private String localDisplayName;
    private String remoteDisplayName;
    /** The full text in the chat room; contains all messages. */
    private String chatText = "";

    /** The remotePeer object representing the other peer(besides the local peer) */
    private RemotePeer remotePeer;
    /** The Connection used to receive messages. */
    private Connection incomingConnection;
    /** The Connection used to send messages. */
    private Connection outgoingConnection;

    public P2PConnection(RemotePeer remotePeer, String localDisplayName) {
        this.localDisplayName = localDisplayName;
        this.remotePeer = remotePeer;

        // When an incoming connection is available, call acceptConnection.
        this.remotePeer.setIncomingConnectionHandler((peer, connection) -> this.acceptIncomingConnection(connection));

        // Create a connection to the remote peer
        this.outgoingConnection = remotePeer.connect();
        // The first message sent through the outgoing connection contains the display name that should be used, so it is sent here.
       // this.outgoingConnection.send(new TextMessage(localDisplayName).serialize());
    }

    private void acceptIncomingConnection(Connection connection) {
        if (this.incomingConnection == null) {
            // If this is the first connection, we use it to receive message data. Therefore we call handleMessageData when data was received.
            this.incomingConnection = connection;
            //this.incomingConnection.setOnData((t, data) -> handleMessageData(data));
        } else {
            // Any additional connections are used to receive a file transfer.
           // connection.setOnTransfer((c, t) -> this.receiveFileTransfer(c, t));
        }
    }
//    public void sendMessage(String message) {
//        this.outgoingConnection.send(new TextMessage(message).serialize());
//        appendChatMessage(this.localDisplayName, message);
//    }
//    private void handleMessageData(ByteBuffer data) {
//        String message = new TextMessage(data).text;
//
//        if (this.remoteDisplayName == null) {
//            this.setDisplayName(message);
//        } else {
//            appendChatMessage(this.remoteDisplayName, message);
//        }
//    }
//    private void appendChatMessage(String displayName, String message) {
//        this.chatText += displayName+": "+ message+"\n";
//        //chatUI.updateChatData();
//    }
//
//
//    public void sendFile(FileChannel fileChannel, String fileName, int fileSize) throws IOException {
//        // Establish a new connection to transmit the file.
//        Connection connection = this.remotePeer.connect();
//        // Send the file name
//        connection.send(new TextMessage(fileName).serialize());
//        // Send the file itself. Data will be read as the file is being sent (the closure will be called multiple times).
//        OutTransfer transfer = connection.send(fileSize, (position, length) -> readData(fileChannel, position, length));
//        // When progress is made, we update the file progress property.
//        transfer.setOnProgress( t -> setFileProgress(t.getProgress(), t.getLength()));
//        // When the transfer is done, we reset the progress and close the file handle.
//        transfer.setOnEnd(t -> endTransfer(fileChannel));
//
//    }
//
//
//    private void receiveFileTransfer(Connection connection, InTransfer transfer) {
//        /*
//         * 2.4e Receive a file transfer.
//         *
//         * 1. As data is received, it should be written to the file channel (use writeData).
//         * 2. Update the progress bar using setFileProgress.
//         * 3. Call endTransfer and close the connection when the transfer completes or is cancelled.
//         * */
//        // receiveFile will be called twice; once for the transfer that contains the fileName only, and once for the actual file.
//        if (fileName == null) {
//            transfer.setOnCompleteData((t, data) -> { fileName = new TextMessage(data).text; });
//        } else {
//            // If we already have a filePath, we can receive the file.
//            FileChannel fileChannel = this.chatUI.getSaveFileChannel(fileName);
//
//            // Whenever data is received, we write data to the file channel.
//            transfer.setOnPartialData((t, data) -> writeData(fileChannel, data));
//            // When progress is made, update the progress property
//            transfer.setOnProgress( t -> setFileProgress(t.getProgress(), t.getLength()));
//            // When everything is received, close the connection, reset the filePath, close the file handle, and inform the delegate that a file was received.
//            transfer.setOnEnd(t -> { endTransfer(fileChannel); connection.close(); });
//            // Reset the file path.
//            this.fileName = null;
//        }
//    }
//
//    /** Reads data from a file. */
//    private ByteBuffer readData(FileChannel fileChannel, int position, int length) {
//        try {
//            ByteBuffer buffer = ByteBuffer.allocate(length);
//            fileChannel.read(buffer, position);
//
//            buffer.rewind();
//
//            return buffer;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//    /** Writes data to a file. */
//    private void writeData(FileChannel fileChannel, ByteBuffer data) {
//        try {
//            fileChannel.write(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    /** Closes the file channel and resets progress. */
//    private void endTransfer(FileChannel fileChannel) {
//        setFileProgress(0, 1);
//        try {
//            fileChannel.force(true);
//            fileChannel.close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
    private void setDisplayName(String displayName) {
        this.remoteDisplayName = displayName;
    }
    public String getDisplayName() {
        return this.remoteDisplayName;
    }
//    public String getChatText() {
//        return this.chatText;
//    }
//    public int getFileProgress() {
//        return this.fileProgress;
//    }
//    public void setFileProgress(long progress, long totalLength) {
//        this.fileProgress = (int)((progress*100) / totalLength);
//        this.chatUI.updateChatData();
//    }
}
