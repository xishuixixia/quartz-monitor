package com.quartz.monitor.object;

import javax.management.ObjectName;

public class JMXInput
{
   private QuartzInstance quartzInstance;
   private String[] signature;
   private String operation;
   private Object[] parameters;
   private ObjectName objectName;

   public JMXInput()
   {
   }

   /**
    * 
    * @param quartzInstance
    * @param signature
    * @param operation
    * @param parameters
    * @param objectName
    */
   public JMXInput(QuartzInstance quartzInstance, String[] signature, String operation, Object[] parameters, ObjectName objectName)
   {
      this.quartzInstance = quartzInstance;
      this.signature = signature;
      this.operation = operation;
      this.parameters = parameters;
      this.objectName = objectName;
   }

   public QuartzInstance getQuartzInstanceConnection()
   {
      return quartzInstance;
   }

   public void setQuartzInstanceConnection(QuartzInstance quartzInstance)
   {
      this.quartzInstance = quartzInstance;
   }

   public String[] getSignature()
   {
      return signature;
   }

   public void setSignature(String[] signature)
   {
      this.signature = signature;
   }

   public String getOperation()
   {
      return operation;
   }

   public void setOperation(String operation)
   {
      this.operation = operation;
   }

   public Object[] getParameters()
   {
      return parameters;
   }

   public void setParameters(Object[] parameters)
   {
      this.parameters = parameters;
   }

   public ObjectName getObjectName()
   {
      return objectName;
   }

   public void setObjectName(ObjectName objectName)
   {
      this.objectName = objectName;
   }
}
