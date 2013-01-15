package com.quartz.monitor.conf;

/**
 * Quartz连接的配置类
 * @author guolei
 *
 */
public class QuartzConfig
{
   private String uuid;
   private String name;
   private String host;
   private int port;
   private String userName;
   private String password;

   public QuartzConfig()
   {
   }

   public QuartzConfig(String uuid,  String host, int port, String userName, String password)
   {
      this.uuid = uuid;
      this.host = host;
      this.port = port;
      this.name = this.getName();
      this.userName = userName;
      this.password = password;
   }

   public String getName()
   {
      this.name = getHost() +"@" + getPort();
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getUuid()
   {
      return uuid;
   }

   public void setUuid(String uuid)
   {
      this.uuid = uuid;
   }

   public String getHost()
   {
      return host;
   }

   public void setHost(String host)
   {
      this.host = host;
   }

   public int getPort()
   {
      return port;
   }

   public void setPort(int port)
   {
      this.port = port;
   }

   public String getUserName()
   {
      return userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }
}

