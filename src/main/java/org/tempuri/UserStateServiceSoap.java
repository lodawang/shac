/**
 * UserStateServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface UserStateServiceSoap extends java.rmi.Remote {
    public java.lang.String createUserState(java.lang.String userInfo, java.lang.String ip, java.lang.String mac, java.lang.String userId, java.lang.String userName) throws java.rmi.RemoteException;
    public boolean checkUserState(java.lang.String passCode) throws java.rmi.RemoteException;
    public boolean logoutUserState(java.lang.String passCode) throws java.rmi.RemoteException;
    public void setPreOut(java.lang.String passCode) throws java.rmi.RemoteException;
    public void refreshUserState(java.lang.String passCode) throws java.rmi.RemoteException;
    public java.lang.String getUserInfo(java.lang.String passCode) throws java.rmi.RemoteException;
    public org.tempuri.GetUserObjResponseGetUserObjResult getUserObj(java.lang.String passcode) throws java.rmi.RemoteException;
    public java.lang.String getUserObjText(java.lang.String passcode) throws java.rmi.RemoteException;
    public java.lang.String getBOGroupObj(java.lang.String passcode) throws java.rmi.RemoteException;
    public boolean updateUserInfo(java.lang.String passCode, java.lang.String newUserInfo) throws java.rmi.RemoteException;
    public java.lang.String getUserData(java.lang.String passCode) throws java.rmi.RemoteException;
    public boolean updateUserData(java.lang.String passCode, java.lang.String newUserData) throws java.rmi.RemoteException;
    public java.lang.String getUserProp(java.lang.String passCode) throws java.rmi.RemoteException;
    public java.lang.String getUserRole(java.lang.String passCode) throws java.rmi.RemoteException;
    public boolean updateUserProp(java.lang.String passCode, java.lang.String newUserProp) throws java.rmi.RemoteException;
    public java.lang.String getUserDataNode(java.lang.String passCode, java.lang.String type, java.lang.String name) throws java.rmi.RemoteException;
    public boolean setUserDataNode(java.lang.String passCode, java.lang.String type, java.lang.String name, java.lang.String newDataNode) throws java.rmi.RemoteException;
}
