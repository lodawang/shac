/**
 * UpdateUserInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class UpdateUserInfo  implements java.io.Serializable {
    private java.lang.String passCode;

    private java.lang.String newUserInfo;

    public UpdateUserInfo() {
    }

    public UpdateUserInfo(
           java.lang.String passCode,
           java.lang.String newUserInfo) {
           this.passCode = passCode;
           this.newUserInfo = newUserInfo;
    }


    /**
     * Gets the passCode value for this UpdateUserInfo.
     * 
     * @return passCode
     */
    public java.lang.String getPassCode() {
        return passCode;
    }


    /**
     * Sets the passCode value for this UpdateUserInfo.
     * 
     * @param passCode
     */
    public void setPassCode(java.lang.String passCode) {
        this.passCode = passCode;
    }


    /**
     * Gets the newUserInfo value for this UpdateUserInfo.
     * 
     * @return newUserInfo
     */
    public java.lang.String getNewUserInfo() {
        return newUserInfo;
    }


    /**
     * Sets the newUserInfo value for this UpdateUserInfo.
     * 
     * @param newUserInfo
     */
    public void setNewUserInfo(java.lang.String newUserInfo) {
        this.newUserInfo = newUserInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateUserInfo)) return false;
        UpdateUserInfo other = (UpdateUserInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.passCode==null && other.getPassCode()==null) || 
             (this.passCode!=null &&
              this.passCode.equals(other.getPassCode()))) &&
            ((this.newUserInfo==null && other.getNewUserInfo()==null) || 
             (this.newUserInfo!=null &&
              this.newUserInfo.equals(other.getNewUserInfo())));
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
        if (getPassCode() != null) {
            _hashCode += getPassCode().hashCode();
        }
        if (getNewUserInfo() != null) {
            _hashCode += getNewUserInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateUserInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdateUserInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "passCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newUserInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "newUserInfo"));
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
