/**
 * JWatch - Quartz Monitor: http://code.google.com/p/jwatch/
 * Copyright (C) 2011 Roy Russo and the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 **/
package com.quartz.monitor.core;

import com.quartz.monitor.conf.QuartzConfig;
import com.quartz.monitor.object.QuartzInstance;

/**
 * 处理应用与Quartz的连接（使用JMX）
 * @author guolei
 *
 */
public interface QuartzConnectService
{
   /**
    * Initializes the connection to a quartz instance.
    *
    * @param config
    * @return
    * @throws java.io.IOException
    * @throws javax.management.MalformedObjectNameException
    */
   public QuartzInstance initInstance(QuartzConfig config) throws Exception;
}
