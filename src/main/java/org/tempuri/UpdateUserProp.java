/**
 * UpdateUserProp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class UpdateUserProp  implements java.io.Serializable {
    private java.lang.String passCode;

    private java.lang.String newUserProp;

    public UpdateUserProp() {
    }

    public UpdateUserProp(
           java.lang.String passCode,
           java.lang.String newUserProp) {
           this.passCode = passCode;
           this.newUserProp = newUserProp;
    }


    /**
     * Gets the passCode value for this UpdateUserProp.
     * 
     * @return passCode
     */
    public java.lang.String getPassCode() {
        return passCode;
    }


    /**
     * Sets the passCode value for this UpdateUserProp.
     * 
     * @param passCode
     */
    public void setPassCode(java.lang.String passCode) {
        this.passCode = passCode;
    }


    /**
     * Gets the newUserProp value for this UpdateUserProp.
     * 
     * @return newUserProp
     */
    public java.lang.String getNewUserProp() {
        return newUserProp;
    }


    /**
     * Sets the newUserProp value for this UpdateUserProp.
     * 
     * @param newUserProp
     */
    public void setNewUserProp(java.lang.String newUserProp) {
        this.newUserProp = newUserProp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateUserProp)) return false;
        UpdateUserProp other = (UpdateUserProp) obj;
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
            ((this.newUserProp==null && other.getNewUserProp()==null) || 
             (this.newUserProp!=null &&
              this.newUserProp.equals(other.getNewUserProp())));
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
        if (getNewUserProp() != null) {
            _hashCode += getNewUserProp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateUserProp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdateUserProp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "passCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newUserProp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "newUserProp"));
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
