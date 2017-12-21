package com.jp;

import java.net.*;  
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.io.*;  

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
public class PortScanPlus {  
	
	public static String portIsOpen(final String ip, final int port, final int timeout) {
		
		        try {
		          String WAS = "";
		          Socket socket = new Socket();
		          socket.connect(new InetSocketAddress(ip, port), timeout);
		          socket.setSoTimeout(1000);
		          boolean HTTP = false;
		          try
		          {
		          OutputStream out = socket.getOutputStream();
		          PrintWriter outw = new PrintWriter(out, false);
		          outw.print("GET / HTTP/1.0\r\n");
		          outw.print("Accept: text/plain, text/html, text/*\r\n");
		          outw.print("\r\n");
		          outw.flush();

		          InputStream in = socket.getInputStream();
		          InputStreamReader inr = new InputStreamReader(in);
		          BufferedReader br = new BufferedReader(inr);
		          String line;
		          while ((line = br.readLine()) != null) 
		          {
		                  //System.out.println(line);
		        	      if (line.indexOf("HTTP") != -1) HTTP=true;
		          }
		          br.close();
		          }
		          catch(Exception ex)
		          {}
		          
		           socket.close();
		         	          
		          
		          if (TestWAS(ip, new Integer(port).toString()))
		            WAS = " SECURED WAS ADMIN";
		          //else
		          {
		        	  try
		        	  {
		        		  
		        			// Create a trust manager that does not validate certificate chains
		        			TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
		        					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		        						return null;
		        					}
		        					public void checkClientTrusted(X509Certificate[] certs, String authType) {
		        					}
		        					public void checkServerTrusted(X509Certificate[] certs, String authType) {
		        					}
		        				}
		        			};

		        			// Install the all-trusting trust manager
		        			SSLContext sc = SSLContext.getInstance("SSL");
		        			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		        			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		        			        		  
		        		
		        	  SSLSocketFactory factory = 
			        	      // (SSLSocketFactory)SSLSocketFactory.getDefault();
		        			  HttpsURLConnection.getDefaultSSLSocketFactory();
			          SSLSocket sslSocket = 
			        	      (SSLSocket)factory.createSocket();
			          sslSocket.connect(new InetSocketAddress(ip, port), timeout);
			          sslSocket.setSoTimeout(1000);
			          //printSocketInfo(sslSocket);
			          sslSocket.startHandshake();
			          javax.security.cert.X509Certificate[] certificates = sslSocket.getSession().getPeerCertificateChain();
			          Date expiration = certificates[0].getNotAfter();
			          //System.out.println(certificates.length);
			          sslSocket.close();
			          if (true)
			           return ""+port+ " SSL "+ expiration + " " + WAS;
			          else 
			        	  return ""+port;	  
		        	  
		          } catch (Exception ex) { 
		        	  //System.out.print(ex);
		        		  return ""+port+ " "+ (HTTP?"HTTP":"") + " " + WAS;  
		        	  }
		          }
		        } catch (Exception ex) {
		          return "";
		        }
		      }
		  
		
	
	 private static void printSocketInfo(SSLSocket s) {
	      System.out.println("Socket class: "+s.getClass());
	      System.out.println("   Remote address = "
	         +s.getInetAddress().toString());
	      System.out.println("   Remote port = "+s.getPort());
	      System.out.println("   Local socket address = "
	         +s.getLocalSocketAddress().toString());
	      System.out.println("   Local address = "
	         +s.getLocalAddress().toString());
	      System.out.println("   Local port = "+s.getLocalPort());
	      System.out.println("   Need client authentication = "
	         +s.getNeedClientAuth());
	      SSLSession ss = s.getSession();
	      System.out.println("   Cipher suite = "+ss.getCipherSuite());
	      System.out.println("   Protocol = "+ss.getProtocol());
	   }
	public static boolean TestWAS(String hostname, String port)
	{
		//System.out.println("TestWAS on port: "+port);
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}
		};
      try
      {
		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		URL url = new URL("https://"+hostname+":"+port+"/admin");
		URLConnection con = url.openConnection();
		con.setConnectTimeout(1000);
		con.setReadTimeout(1000);
		Reader reader = new InputStreamReader(con.getInputStream());
		StringBuffer output = new StringBuffer();
		while (true) {
			int ch = reader.read();
			output.append((char)ch);
			if (ch==-1) {
				break;
			}
	//		System.out.print((char)ch);
		
		}
		//System.out.println(output);
		if (output.indexOf("username") != -1)
		{
			//System.out.println(output);
			return true;
		}
		else
			return false;
      }
      catch (Exception e)
      {
      	
      }
		return false;
	}
	
    public static void main(String[] args) throws IOException {  
        InetAddress host = InetAddress.getLocalHost();  
        int startPort = 1;  
        int endPort = 65535;
        long startTime = System.currentTimeMillis();
        long endtTime;
        int timeout = 1000;
        switch (args.length) {  
        case 3 :  
            endPort = Integer.parseInt(args[2]);  
        // Fall thru  
        case 2 :  
            startPort = Integer.parseInt(args[1]);  
        // Fall thru  
        case 1 :  
            host = InetAddress.getByName(args[0]);  
        // Fall thru  
        case 0 :  
            break;  
        default :  
            System.err.println(  
                "Usage: java PortScan [host] [startPort] [endPort]");  
            System.exit(1);  
        }  
        if (startPort < 0 || startPort > 65535 ||  
              endPort < 0 ||   endPort > 65535) {  
            throw new IllegalArgumentException(  
                          "PortScan: invalid port number");  
        }  
        System.out.println("Scanning host " + host.getHostName());  
        for (int i = startPort; i <= endPort; i++) {  
             String out = "";
            out = portIsOpen(host.getHostName(),i,timeout);
            if (!out.equals("")) 
             System.out.println(out); 
      //      System.out.println("open");  
        }  
        endtTime =  System.currentTimeMillis();
        System.out.println("Duration : " + (endtTime - startTime));
    }  
}  
