/**
 * UserStateServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class UserStateServiceLocator extends org.apache.axis.client.Service implements org.tempuri.UserStateService {

    public UserStateServiceLocator() {
    }


    public UserStateServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserStateServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserStateServiceSoap
    private java.lang.String UserStateServiceSoap_address = "http://10.124.88.126:8011/webservice/UserStateService.asmx";

    public java.lang.String getUserStateServiceSoapAddress() {
        return UserStateServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserStateServiceSoapWSDDServiceName = "UserStateServiceSoap";

    public java.lang.String getUserStateServiceSoapWSDDServiceName() {
        return UserStateServiceSoapWSDDServiceName;
    }

    public void setUserStateServiceSoapWSDDServiceName(java.lang.String name) {
        UserStateServiceSoapWSDDServiceName = name;
    }

    public org.tempuri.UserStateServiceSoap getUserStateServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserStateServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserStateServiceSoap(endpoint);
    }

    public org.tempuri.UserStateServiceSoap getUserStateServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.UserStateServiceSoapStub _stub = new org.tempuri.UserStateServiceSoapStub(portAddress, this);
            _stub.setPortName(getUserStateServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserStateServiceSoapEndpointAddress(java.lang.String address) {
        UserStateServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.UserStateServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.UserStateServiceSoapStub _stub = new org.tempuri.UserStateServiceSoapStub(new java.net.URL(UserStateServiceSoap_address), this);
                _stub.setPortName(getUserStateServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserStateServiceSoap".equals(inputPortName)) {
            return getUserStateServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "UserStateService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "UserStateServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserStateServiceSoap".equals(portName)) {
            setUserStateServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
