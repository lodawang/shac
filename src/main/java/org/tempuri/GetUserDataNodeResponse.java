/**
 * GetUserDataNodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetUserDataNodeResponse  implements java.io.Serializable {
    private java.lang.String getUserDataNodeResult;

    public GetUserDataNodeResponse() {
    }

    public GetUserDataNodeResponse(
           java.lang.String getUserDataNodeResult) {
           this.getUserDataNodeResult = getUserDataNodeResult;
    }


    /**
     * Gets the getUserDataNodeResult value for this GetUserDataNodeResponse.
     * 
     * @return getUserDataNodeResult
     */
    public java.lang.String getGetUserDataNodeResult() {
        return getUserDataNodeResult;
    }


    /**
     * Sets the getUserDataNodeResult value for this GetUserDataNodeResponse.
     * 
     * @param getUserDataNodeResult
     */
    public void setGetUserDataNodeResult(java.lang.String getUserDataNodeResult) {
        this.getUserDataNodeResult = getUserDataNodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetUserDataNodeResponse)) return false;
        GetUserDataNodeResponse other = (GetUserDataNodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getUserDataNodeResult==null && other.getGetUserDataNodeResult()==null) || 
             (this.getUserDataNodeResult!=null &&
              this.getUserDataNodeResult.equals(other.getGetUserDataNodeResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGetUserDataNodeResult() != null) {
            _hashCode += getGetUserDataNodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUserDataNodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUserDataNodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUserDataNodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetUserDataNodeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
