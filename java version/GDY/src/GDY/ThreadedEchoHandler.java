package GDY;

import java.io.*;
import java.net.*;
import java.util.*;

class ThreadedEchoHandler implements Runnable
{
    private Socket incoming;
    
    public ThreadedEchoHandler(Socket incomingSocket)
    {
        incoming = incomingSocket;
    }

    public void run()
    {
        try (InputStream inStream = incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream();
             var in = new Scanner(inStream, "UTF-8");
             var out = new PrintWriter(
                     new OutputStreamWriter(outStream, "UTF-8"),
                     true /* autoFlush */))
        {
            out.println( "Hello! Enter BYE to exit." );

            // echo client input
            var done = false;
            while (!done && in.hasNextLine())
            {
                String line = in.nextLine();
                out.println("Echo: " + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}