package org.tempuri;

public class UserStateServiceSoapProxy implements org.tempuri.UserStateServiceSoap {
  private String _endpoint = null;
  private org.tempuri.UserStateServiceSoap userStateServiceSoap = null;
  
  public UserStateServiceSoapProxy() {
    _initUserStateServiceSoapProxy();
  }
  
  public UserStateServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserStateServiceSoapProxy();
  }
  
  private void _initUserStateServiceSoapProxy() {
    try {
      userStateServiceSoap = (new org.tempuri.UserStateServiceLocator()).getUserStateServiceSoap();
      if (userStateServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userStateServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userStateServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userStateServiceSoap != null)
      ((javax.xml.rpc.Stub)userStateServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.UserStateServiceSoap getUserStateServiceSoap() {
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap;
  }
  
  public java.lang.String createUserState(java.lang.String userInfo, java.lang.String ip, java.lang.String mac, java.lang.String userId, java.lang.String userName) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.createUserState(userInfo, ip, mac, userId, userName);
  }
  
  public boolean checkUserState(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.checkUserState(passCode);
  }
  
  public boolean logoutUserState(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.logoutUserState(passCode);
  }
  
  public void setPreOut(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    userStateServiceSoap.setPreOut(passCode);
  }
  
  public void refreshUserState(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    userStateServiceSoap.refreshUserState(passCode);
  }
  
  public java.lang.String getUserInfo(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserInfo(passCode);
  }
  
  public org.tempuri.GetUserObjResponseGetUserObjResult getUserObj(java.lang.String passcode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserObj(passcode);
  }
  
  public java.lang.String getUserObjText(java.lang.String passcode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserObjText(passcode);
  }
  
  public java.lang.String getBOGroupObj(java.lang.String passcode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getBOGroupObj(passcode);
  }
  
  public boolean updateUserInfo(java.lang.String passCode, java.lang.String newUserInfo) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.updateUserInfo(passCode, newUserInfo);
  }
  
  public java.lang.String getUserData(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserData(passCode);
  }
  
  public boolean updateUserData(java.lang.String passCode, java.lang.String newUserData) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.updateUserData(passCode, newUserData);
  }
  
  public java.lang.String getUserProp(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserProp(passCode);
  }
  
  public java.lang.String getUserRole(java.lang.String passCode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserRole(passCode);
  }
  
  public boolean updateUserProp(java.lang.String passCode, java.lang.String newUserProp) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.updateUserProp(passCode, newUserProp);
  }
  
  public java.lang.String getUserDataNode(java.lang.String passCode, java.lang.String type, java.lang.String name) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.getUserDataNode(passCode, type, name);
  }
  
  public boolean setUserDataNode(java.lang.String passCode, java.lang.String type, java.lang.String name, java.lang.String newDataNode) throws java.rmi.RemoteException{
    if (userStateServiceSoap == null)
      _initUserStateServiceSoapProxy();
    return userStateServiceSoap.setUserDataNode(passCode, type, name, newDataNode);
  }
  
  
}